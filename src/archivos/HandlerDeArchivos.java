package archivos;
import entities.Usuario;
import listNodes.NodoLista;
import listNodes.NodoListaAutores;
import listNodes.NodoListasPropias;
import listNodes.NodoListasSeguidas;
import serializables.Cancion;
import serializables.ListaPropia;
import serializables.ListaSeguida;
import sistema.Sistema;
import trees.ArbolUsuarios;
import trees.nodes.NodoCancion;
import trees.nodes.NodoUsuario;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HandlerDeArchivos {
    private static final String pathUsuarios = "./src/work/251506-archusuarios.ser";
    private static final String pathCanciones = "./src/work/251506-archcanciones.ser";
    private static final String pathListasPropias = "./src/work/251506-archlistaspropias.ser";
    private static final String pathListasSeguidas = "./src/work/251506-archlistasseguidas.ser";


    public HandlerDeArchivos() {
    }

    public void crearArchivoVacio(String path) {
        try (FileOutputStream fos = new FileOutputStream(path)) {
            //creo el archivo vacío
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void iniciarArchivo(String path) {
        boolean archivoExiste = Files.exists(Paths.get(path));
        if (!archivoExiste)
            this.crearArchivoVacio(path);

    }


    public void guardarCanciones(NodoListaAutores autores) {
        try (FileOutputStream fileOut = new FileOutputStream(pathCanciones);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {

            NodoListaAutores actual = autores;
            while (actual != null) {
                guardarCancionesDeAutor(out, actual);
                actual = actual.getNext();
            }
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    private void guardarCancionesDeAutor(ObjectOutputStream out, NodoListaAutores autor) {
        try {
            NodoCancion cancionActual = autor.getCatalogoCanciones();
            do {
                Cancion serializable = new Cancion(cancionActual.getValue(), autor.getValue());
                out.writeObject(serializable);
                cancionActual = cancionActual.getNextMismoAutor();
            } while (cancionActual != autor.getCatalogoCanciones());
        } catch (IOException e) {
            //llega al final
        }
    }

    public void guardarUsuariosYListas(ArbolUsuarios usuarios) {
        try (ObjectOutputStream outUsuarios = new ObjectOutputStream(new FileOutputStream(pathUsuarios));
             ObjectOutputStream outListasPropias = new ObjectOutputStream(new FileOutputStream(pathListasPropias));
             ObjectOutputStream outListasSeguidas = new ObjectOutputStream(new FileOutputStream(pathListasSeguidas))) {

            // Inicia el proceso de guardar
            guardarUsuariosEnPreOrden(usuarios.getRoot(), outUsuarios, outListasPropias, outListasSeguidas);

        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    private void guardarUsuariosEnPreOrden(NodoUsuario actualUsuario, ObjectOutputStream outUsuarios,
                                           ObjectOutputStream outListasPropias, ObjectOutputStream outListasSeguidas) {
        if (actualUsuario != null) {
            try {
                outUsuarios.writeObject(actualUsuario.getValue());
                guardarPlaylistsPropias(actualUsuario, outListasPropias);
                guardarPlaylistsSeguidas(actualUsuario, outListasSeguidas);


                guardarUsuariosEnPreOrden(actualUsuario.getLeft(), outUsuarios, outListasPropias, outListasSeguidas);
                guardarUsuariosEnPreOrden(actualUsuario.getRight(), outUsuarios, outListasPropias, outListasSeguidas);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //aca voy a tener un doble recorrido porque recorro primero las listas propias y luego las canciones de cada lista
    private void guardarPlaylistsPropias(NodoUsuario actualUsuario, ObjectOutputStream out) {
        NodoListasPropias listaPropiaActual = actualUsuario.getPlaylistsPropias();
        while (listaPropiaActual != null) {
            NodoLista<NodoCancion> cancionActual = listaPropiaActual.getSublistaCancion();
            while (cancionActual != null) {
                ListaPropia serializable = new ListaPropia(actualUsuario.getValue().getUser(), listaPropiaActual.getValue(), cancionActual.getValue().getValue());
                try {
                    out.writeObject(serializable);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                cancionActual = cancionActual.getNext();
            }
            listaPropiaActual = listaPropiaActual.getNext();
        }
    }

    private void guardarPlaylistsSeguidas(NodoUsuario actualUsuario, ObjectOutputStream out) {
        NodoListasSeguidas listaSeguidaActual = actualUsuario.getPlaylistsSeguidas();
        while (listaSeguidaActual != null) {
            ListaSeguida serializable = new ListaSeguida(actualUsuario.getValue().getUser(), listaSeguidaActual.getUsuarioCreador(), listaSeguidaActual.getValue());
            try {
                out.writeObject(serializable);
            } catch (IOException e) {
                e.printStackTrace();
            }
            listaSeguidaActual = listaSeguidaActual.getNext();
        }
    }


    public void cargarEstructuras(Sistema sistema) {
        iniciarArchivo(pathUsuarios);
        iniciarArchivo(pathCanciones);
        iniciarArchivo(pathListasPropias);
        iniciarArchivo(pathListasSeguidas);

        this.cargarUsuarios(sistema);
        this.cargarCanciones(sistema);
        this.cargarListasPropias(sistema);
        this.cargarListasSeguidas(sistema);

    }

    private void cargarListasSeguidas(Sistema sistema) {
        try (FileInputStream fileIn = new FileInputStream(pathListasSeguidas);
             ObjectInputStream ois = new ObjectInputStream(fileIn)) {
            while (true) {
                ListaSeguida obj = (ListaSeguida) ois.readObject();
                NodoUsuario usuarioLogueado = sistema.buscarUsuario(new Usuario(obj.getNombreUsuario(), ""));
                NodoUsuario usuarioSeguido = sistema.buscarUsuario(new Usuario(obj.getNombreSeguido(), ""));
                sistema.seguirPlaylist(obj.getNombreLista(), usuarioLogueado, usuarioSeguido);
            }
        } catch (EOFException e) {
            //llega al final
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void cargarListasPropias(Sistema sistema) {
        try (FileInputStream fileIn = new FileInputStream(pathListasPropias);
             ObjectInputStream ois = new ObjectInputStream(fileIn)) {
            while (true) {
                ListaPropia obj = (ListaPropia) ois.readObject();
                NodoUsuario usuario = sistema.buscarUsuario(new Usuario(obj.getNombreUsuario(), ""));
                sistema.crearPlaylistPropia(obj.getNombreLista(), usuario);
                sistema.agregarCancionAPlaylistPropia(obj.getNombreLista(), obj.getTituloCancion(), usuario);
            }
        } catch (EOFException e) {
            //llega al final
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void cargarUsuarios(Sistema sistema) {
        try (FileInputStream fileIn = new FileInputStream(pathUsuarios);
             ObjectInputStream ois = new ObjectInputStream(fileIn)) {
            while (true) {
                Usuario obj = (Usuario) ois.readObject();
                sistema.agregarUsuario(obj.getUser(), obj.getPassword());
            }
        } catch (EOFException e) {
            //llega al final
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void cargarCanciones(Sistema sistema) {
        try (FileInputStream fileIn = new FileInputStream(pathCanciones);
             ObjectInputStream ois = new ObjectInputStream(fileIn)) {
            while (true) {
                Cancion obj = (Cancion) ois.readObject();

                sistema.agregarCancion(obj.getTitulo(), obj.getAutor());
            }
        } catch (EOFException e) {
            //llega al final
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /* ---------------- metodos de lectura pero nomas los voy a usar para testear ---------------- */
    public void leerTodosLosDatos() {
        System.out.println("Usuarios:");
        this.mostrarUsuarios();
        System.out.println("Canciones:");
        this.mostrarCanciones();
        System.out.println("Listas propias:");
        this.mostrarListasPropias();
        System.out.println("Listas seguidas:");
        this.mostrarListasSeguidas();
    }

    private void mostrarListasSeguidas() {
        try (FileInputStream fileIn = new FileInputStream(pathListasSeguidas);
             ObjectInputStream ois = new ObjectInputStream(fileIn)) {
            while (true) {
                ListaSeguida obj = (ListaSeguida) ois.readObject();
                System.out.println(obj);
            }
        } catch (EOFException e) {
            //llega al final
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void mostrarListasPropias() {
        try (FileInputStream fileIn = new FileInputStream(pathListasPropias);
             ObjectInputStream ois = new ObjectInputStream(fileIn)) {
            while (true) {
                ListaPropia obj = (ListaPropia) ois.readObject();
                System.out.println(obj);
            }
        } catch (EOFException e) {
            //llega al final
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void mostrarCanciones() {
        try (FileInputStream fileIn = new FileInputStream(pathCanciones);
             ObjectInputStream ois = new ObjectInputStream(fileIn)) {
            while (true) {
                Cancion obj = (Cancion) ois.readObject();
                System.out.println(obj);
            }
        } catch (EOFException e) {
            //llega al final
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void mostrarUsuarios() {
        try (FileInputStream fileIn = new FileInputStream(pathUsuarios);
             ObjectInputStream ois = new ObjectInputStream(fileIn)) {
            while (true) {
                Usuario obj = (Usuario) ois.readObject();
                System.out.println(obj);
            }
        } catch (EOFException e) {
            //llega al final
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
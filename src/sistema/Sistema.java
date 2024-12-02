package sistema;

import archivos.HandlerDeArchivos;
import entities.Usuario;
import listNodes.NodoListaAutores;
import listNodes.NodoListasPropias;
import trees.ArbolCanciones;
import trees.ArbolUsuarios;
import trees.nodes.NodoCancion;
import trees.nodes.NodoUsuario;

import java.io.*;

/*
    * El sistema es el encargado de manejar los usuarios y las canciones.
    * Va a ser el intermediario entre el menu y las estructuras de datos,
    * que en este caso son los arboles de usuarios y canciones.
*/
public class Sistema {
    private ArbolUsuarios usuarios;
    private ArbolCanciones canciones;
    private NodoListaAutores autores;
    private HandlerDeArchivos archivoHandler;

    public Sistema(){
        this.usuarios = new ArbolUsuarios();
        this.canciones = new ArbolCanciones();
        this.archivoHandler = new HandlerDeArchivos();
        this.archivoHandler.cargarEstructuras(this);
    }

    public void agregarUsuario(String nombre, String password){
        this.usuarios.add(nombre, password);
    }

    public NodoUsuario buscarUsuario(Usuario buscado) {
        return this.usuarios.buscar(buscado);
    }

    public void mostarUsuarios() {
        this.usuarios.imprimir();
    }

    public void agregarCancion(String titulo, String autor) {
        NodoCancion cancionAgregada = null;

        if (!titulo.trim().isEmpty() && !autor.trim().isEmpty())
            cancionAgregada = this.canciones.add(titulo);

        if( cancionAgregada != null){
            NodoListaAutores autorAgregado = this.agregarAutor(autor);
            // accediendo al valor del nodoAutor, le voy a setear el siguiente al nodoCancion
            autorAgregado.agregarCancion(autor, cancionAgregada);
        }

    }

    public void mostrarCanciones() {
        this.canciones.imprimir();
    }


    /* --------------------- manejo de playlists ---------------------*/
    public void crearPlaylistPropia(String nombrePlaylistPropia, NodoUsuario usuarioLogueado) {
        usuarioLogueado.crearListaPropia(nombrePlaylistPropia);
    }

    public void agregarCancionAPlaylistPropia(String nombrePlaylist, String nombreCancion, NodoUsuario usuario) {
        NodoCancion cancionBuscada = this.canciones.buscar(nombreCancion);
        if(cancionBuscada != null)
                usuario.agregarCancionAPlaylistPropia(nombrePlaylist, cancionBuscada);
    }

    public void eliminarPlaylistPropia(String nombrePlaylist, NodoUsuario usuarioLogueado) {
        usuarioLogueado.eliminarListaPropia(nombrePlaylist);
    }

    public void seguirPlaylist(String nombrePlaylist, NodoUsuario usuarioLogueado, NodoUsuario usuarioSeguido) {
        NodoListasPropias listaSeguida  = usuarioSeguido.buscarPlaylist(nombrePlaylist); //busca la playlist en las listas propias del usuario seguido
        if (listaSeguida != null)
            usuarioLogueado.seguirPlaylist(nombrePlaylist, usuarioSeguido.getValue().getUser());
    }

    /* ----------------------- logica de lista de autores --------------------*/

    public void mostrarAutores() {
        NodoListaAutores actual = this.autores;
        while(actual != null){
            System.out.println(actual.getValue());

            actual = actual.getNext();
        }
    }

    public void mostrarCancionesPorAutor( String autor ) {
        NodoListaAutores autorBuscado = this.buscarAutor(autor);
        if(autorBuscado != null)
            autorBuscado.mostrarCanciones(); //Lista todas las canciones de ese autor
    }

    private NodoListaAutores agregarAutor(String autor) {
        NodoListaAutores nuevoAutor = new NodoListaAutores(autor);

        if (this.autores == null || this.autores.compareTo(nuevoAutor) >= 0) {
            if (this.autores != null && this.autores.equals(nuevoAutor)) {
                return this.autores; // Autor ya existe, no agregar.
            }
            nuevoAutor.setNext(this.autores);
            this.autores = nuevoAutor;
            return nuevoAutor;
        }

        NodoListaAutores previo = this.autores;
        NodoListaAutores actual = this.autores.getNext();

        while(actual != null && actual.compareTo(nuevoAutor) <= 0){
            if (actual.equals(nuevoAutor))
                return actual; //si ya existe el autor, no lo agrego (no se si es necesario este chequeo
            previo = actual;
            actual = actual.getNext();
        }

        nuevoAutor.setNext(actual);
        previo.setNext(nuevoAutor);
        return nuevoAutor; //retorno nuevoAutor porque aunque ya este en la lista necesito recorrer su lista de canciones
    }

    private NodoListaAutores buscarAutor(String autor) {
        NodoListaAutores autorBuscado = new NodoListaAutores(autor);

        NodoListaAutores actual = this.autores;
        while(actual != null && actual.compareTo(autorBuscado) != 0){
            actual = actual.getNext();
        }

        if (actual != null && actual.compareTo(autorBuscado) == 0){
            return actual;
        }

        return null;
    }

    public void imprimirTodo() {
        archivoHandler.leerTodosLosDatos();
    }

    public void guardarDatosEnArchivo() {
        archivoHandler.guardarUsuariosYListas(this.usuarios);
        archivoHandler.guardarCanciones(this.autores);
    }
}

package sistema;

import entities.Usuario;
import sistema.Sistema;
import trees.nodes.NodoUsuario;

import java.util.Scanner;

public class Menu {
    private static final int SALIDA_SEG_MENU = 7;
    private static final int SALIDA_PRIMER_MENU = 4;
    private Sistema sistema;
    private Scanner scanner;

    public Menu() {
        this.sistema = new Sistema();
        this.scanner = new Scanner(System.in);
    }

    public Sistema getSistema() {
        return this.sistema;
    }

    // ------------------------------ items del menú principal ------------------------------

    public NodoUsuario loguearse() {
        //pedir nombre y password
        System.out.println("Ingrese nombre de usuario");
        String nombre = this.scanner.nextLine();
        System.out.println("Ingrese password");
        String password = this.scanner.nextLine();

        Usuario buscado = new Usuario(nombre, password);
        NodoUsuario encontrado = this.sistema.buscarUsuario(buscado);

        if (encontrado.getValue().getUser().equals(nombre) &&
         encontrado.getValue().getPassword().equals(password))
            return encontrado;

        return null;
    }


    public void crearNuevoUsuario() {
        System.out.println("Ingrese nombre de usuario");
        String nombre = this.scanner.nextLine();
        System.out.println("Ingrese password");
        String password = this.scanner.nextLine();

        if (nombre != null && password.length() < 6) {
            System.out.println("La contraseña debe tener al menos 6 caracteres");
        } else {
            this.sistema.agregarUsuario(nombre, password);
        }
    }

    public void mostrarUsuarios() {
        this.sistema.mostarUsuarios();
    }

    // ------------------------------ items del segundo menú ------------------------------
    public void agregarCancion() {
        System.out.println("Ingrese título de la canción");
        String titulo = this.scanner.nextLine();
        System.out.println("Ingrese autor de la canción");
        String autor = this.scanner.nextLine();

        this.sistema.agregarCancion(titulo, autor);
    }


    public void crearPlaylistPropia(NodoUsuario usuarioLogueado) {
        System.out.println("Ingresa el nombre de la playlist");
        String nombrePlaylistPropia = this.scanner.nextLine();
        sistema.crearPlaylistPropia(nombrePlaylistPropia, usuarioLogueado);
    }


    public void agregarCancionAPlaylistPropia(NodoUsuario usuario) {
        System.out.println("Ingresa el nombre de tu playlist");
        String nombrePlaylist = this.scanner.nextLine();
        System.out.println("Ingresa el nombre de la cancion");
        String nombreCancion = this.scanner.nextLine();
        this.sistema.agregarCancionAPlaylistPropia(nombrePlaylist, nombreCancion, usuario);

    }


    public void agregarCancionPorAutorAPlaylistPropia(NodoUsuario usuarioLogueado) {
        System.out.println("Ingresa el nombre de tu playlist");
        String nombrePlaylist = this.scanner.nextLine();
        System.out.println("Ingresa el nombre del autor");
        String autor = this.scanner.nextLine();

        this.sistema.mostrarCancionesPorAutor(autor);

        System.out.println("Ingresa el nombre de la cancion");
        String nombreCancion = this.scanner.nextLine();
        this.sistema.agregarCancionAPlaylistPropia(nombrePlaylist, nombreCancion, usuarioLogueado);
    }


    public void eliminarPlaylistPropia(NodoUsuario usuarioLogueado) {
        System.out.println("Ingresa el nombre de la playlist a eliminar");
        String nombrePlaylist = this.scanner.nextLine();
        this.sistema.eliminarPlaylistPropia(nombrePlaylist, usuarioLogueado);
    }


    public void seguirUnaPlaylist(NodoUsuario usuarioLogueado) {
        System.out.println("Ingresa el nombre del usuario a seguir");
        String nombreUsuario = this.scanner.nextLine();

        NodoUsuario usuarioSeguido = this.sistema.buscarUsuario(new Usuario(nombreUsuario, ""));

        if (usuarioSeguido != null) {
            usuarioSeguido.mostrarListasPropias();

            System.out.println("Ingresa el nombre de la playlist a seguir");
            String nombrePlaylist = this.scanner.nextLine();
            this.sistema.seguirPlaylist(nombrePlaylist, usuarioLogueado, usuarioSeguido);
        } else {
            System.out.println("Usuario no encontrado");
        }
    }

    // --------------------------- gestion de la ejecución de los menus ---------------------------
    public void ejecutarPrimerMenu() {
        this.displayMainMenu();
        int opcion = this.scanner.nextInt();

        if (opcion == SALIDA_PRIMER_MENU)
            this.sistema.guardarDatosEnArchivo();
        else
            this.handleMainMenuOption(opcion);
    }


    public void ejectuarSegundoMenu(NodoUsuario usuarioLogueado) {
        this.displaySecondMenu();
        int opcion = this.scanner.nextInt();

        if (opcion == SALIDA_SEG_MENU) {
            ejecutarPrimerMenu();
        } else {
            this.handleSecondMenuOption(opcion, usuarioLogueado);
        }
    }


    public void displayMainMenu() {
        System.out.println("1. Login");
        System.out.println("2. Nuevo Usuario");
        System.out.println("3. Ver Usuarios existentes");
        System.out.println("4. Salir");
    }


    public void displaySecondMenu() {
        System.out.println("1. Agregar una canción (Título y el Autor)");
        System.out.println("2. Crear una lista de reproducción propia (nombre de la playlist)");
        System.out.println("3. Agregar una canción por título a una lista propia: (nombre de la playlist y el título de la canción)");
        System.out.println("4. Agregar una canción por autor a una lista de reproducción propia (nombre de la playlist y el nombre del autor)");
        System.out.println("5. Eliminar una lista de reproducción propia (nombre de la playlist, elimina con todas las suscripciones a canciones que tenga)");
        System.out.println("6. Incluir la lista de otro usuario (seguirlo) : (Solicita el nombre del usuario a seguir)");
        System.out.println("7. Salir al menú de primer nivel");
    }


    public void handleMainMenuOption(int opcion) {
        this.scanner.nextLine(); //limpio el buffer porque sino toma el enter anterior que hice al ingresar la opcion
        NodoUsuario usuarioLogueado = null;

        switch (opcion) {
            case 1:
                usuarioLogueado = loguearse();
                break;
            case 2:
                crearNuevoUsuario(); //lo carga en el arbol
                break;
            case 3:
                mostrarUsuarios();  //recorro el arbol
                break;
            default:
                System.out.println("Opción inválida");
        }

        if (usuarioLogueado != null) {
            ejectuarSegundoMenu(usuarioLogueado);
        } else {
            ejecutarPrimerMenu();
        }
    }

    public void handleSecondMenuOption(int opcion, NodoUsuario usuarioLogueado) {
        this.scanner.nextLine(); //limpio el buffer

        switch (opcion) {
            case 1:
                agregarCancion();
                break;
            case 2:
                crearPlaylistPropia(usuarioLogueado);
                break;
            case 3:
                agregarCancionAPlaylistPropia(usuarioLogueado);
                break;
            case 4:
                agregarCancionPorAutorAPlaylistPropia(usuarioLogueado);
                break;
            case 5:
                eliminarPlaylistPropia(usuarioLogueado);
                break;
            case 6:
                seguirUnaPlaylist(usuarioLogueado);
                break;
            default:
                System.out.println("Opción inválida");
        }

        ejectuarSegundoMenu(usuarioLogueado);
    }





}

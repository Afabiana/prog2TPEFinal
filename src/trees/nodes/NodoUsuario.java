package trees.nodes;

import archivos.HandlerDeArchivos;
import entities.Usuario;
import listNodes.NodoLista;
import listNodes.NodoListasPropias;
import listNodes.NodoListasSeguidas;


public class NodoUsuario extends NodoArbol<Usuario> {
    private NodoListasPropias listasPropias;
    private NodoListasSeguidas listaSeguidas;

    public NodoUsuario(String username, String password){
        super(  new Usuario(username, password) );
        this.listaSeguidas = null;
        this.listasPropias = null;
    }

    public NodoUsuario getLeft(){
        return (NodoUsuario) super.getLeft();
    }

    public NodoUsuario getRight(){
        return (NodoUsuario) super.getRight();
    }


    // ------------------- Gestion de listas de reproduccion -------------------

    // creo playlist propia nueva
    public void crearListaPropia(String nombrePlaylist){
        NodoListasPropias nuevaPlaylist = new NodoListasPropias(nombrePlaylist);
        this.listasPropias = (NodoListasPropias) crearPlaylist(this.listasPropias, nuevaPlaylist);
    }

    // creo playlist seguida nueva
    public void seguirPlaylist(String nombrePlaylist, String usuarioCreador){
        NodoListasSeguidas nuevaPlaylist = new NodoListasSeguidas(nombrePlaylist, usuarioCreador);
        this.listaSeguidas = (NodoListasSeguidas) crearPlaylist(this.listaSeguidas, nuevaPlaylist);
    }

    //agregar cancion a playlist propia
    public void agregarCancionAPlaylistPropia(String  nombrePlaylist, NodoCancion cancion){
        NodoListasPropias playlistBuscada = this.buscarPlaylist(nombrePlaylist);
        if (playlistBuscada != null)
            playlistBuscada.agregarCancion(cancion);
    }

    //eliminar playlist propia
    public void eliminarListaPropia(String nombrePlaylist) {
        NodoListasPropias previo = null;
        NodoListasPropias actual = this.listasPropias;

        while(actual != null && !actual.getValue().equals(nombrePlaylist)){
            previo = actual;
            actual = (NodoListasPropias) actual.getNext();
        }

        if (actual != null){
            if (previo == null){ //si previo es null es porque nunca entro al while
                this.listasPropias = (NodoListasPropias) actual.getNext();
            } else {
                previo.setNext(actual.getNext());
            }
        }
    }

    // ------------------- Metodos auxiliares para playlists -------------------

    //metodo de agregar ordenado a las playlists (este metodo me sirve para la insercion de ambas listas)
    private NodoLista<String> crearPlaylist(NodoLista<String> listaRaizActual, NodoLista<String> nuevaPlaylist){

        if (listaRaizActual == null)
            return nuevaPlaylist;

        NodoLista<String> previo = null;
        NodoLista<String> actual = listaRaizActual;

        while(actual != null && actual.compareTo(nuevaPlaylist) <= 0){ //comparacion < 0 significa que voy a parar cuando encuentra un nodo que vaya antes que el actual

            if (actual.getValue().equals(nuevaPlaylist.getValue()))
                return listaRaizActual; //retorno la lista sin cambios

            previo = actual;
            actual = actual.getNext();
        }

        if (previo == null){ //si previo es null es porque nunca entro al while y el actual es menor
            nuevaPlaylist.setNext(this.listasPropias);
            return nuevaPlaylist;
        } else {
            previo.setNext(nuevaPlaylist);
            nuevaPlaylist.setNext(actual);
        }
        return listaRaizActual;
    }


    public NodoListasPropias buscarPlaylist(String nombrePlaylist) {
        NodoListasPropias actual = this.listasPropias;
        while(actual != null){
            if (actual.getValue().equals(nombrePlaylist))
                return actual;
            actual = (NodoListasPropias) actual.getNext();
        }
        return null;
    }

    public void mostrarListasPropias(){
        NodoListasPropias actual = this.listasPropias;
        while(actual != null){
            System.out.println(actual.getValue());
            actual = (NodoListasPropias) actual.getNext();
        }
    }

    // ------------------- Metodos de comparacion e impresion -------------------
    public boolean equals(NodoUsuario nodoUsuario){
        try {
            NodoUsuario nodo = (NodoUsuario) nodoUsuario;
            return this.getValue().equals(nodo.getValue());
        } catch (Exception e){
            return false;
        }
    }

    public String toString(){
        return "username: " + this.getValue().getUser() + ", "+
                "password: "+ this.getValue().getPassword() + ", "+
                "playlists: "+ this.imprimirListas(this.listasPropias) + ", "+
                "playlists seguidas: "+ this.imprimirListas(this.listaSeguidas);

    }

    private String imprimirListas(NodoLista<String> lista){
        if (lista == null)
            return "";
        else
            return lista + "\n" + imprimirListas(lista.getNext());
    }




    public NodoListasPropias getPlaylistsPropias() {
        return this.listasPropias;
    }

    public NodoListasSeguidas getPlaylistsSeguidas() {
        return this.listaSeguidas;
    }
}

package listNodes;

import archivos.HandlerDeArchivos;
import trees.nodes.NodoCancion;

public class NodoListaAutores extends NodoLista<String>{
    private NodoCancion catalogoCanciones; //primer nodo que lleva a la lista circular del arbol

    public NodoListaAutores(String nombre){
        super(nombre);
        this.catalogoCanciones = null;
    }

    public NodoListaAutores(String nombre, NodoCancion catalogoCanciones) {
        super(nombre);
        this.catalogoCanciones = catalogoCanciones;
    }

    @Override
    public NodoListaAutores getNext() {
        return (NodoListaAutores) super.getNext();
    }


    public void agregarCancion(String autor, NodoCancion cancionAgregada) {

        if (this.catalogoCanciones == null){
            this.catalogoCanciones = cancionAgregada;
            this.catalogoCanciones.setNextMismoAutor(this.catalogoCanciones); //tiene un puntero hacia si misma
        }else{
            NodoCancion ultimo = this.catalogoCanciones.getNextMismoAutor();

            while(!ultimo.getNextMismoAutor().equals(this.catalogoCanciones)){ //esta lista no tiene orden, cada cancion nueva se agregara al final
                ultimo = ultimo.getNextMismoAutor();
            }

            ultimo.setNextMismoAutor(cancionAgregada); //agrego ultima la nueva cancion
            cancionAgregada.setNextMismoAutor(this.catalogoCanciones); //hago que el nuevo ultimo, apunte a la cabeza de la lista circular
        }

    }

    public void mostrarCanciones(){
        NodoCancion actual = this.catalogoCanciones;
        do {
            System.out.println(actual.getValue());
            actual = actual.getNextMismoAutor();
        } while (actual != this.catalogoCanciones);
    }

    /*/ metodo para persistir la lista circular de canciones de un autor
    public void guardarCanciones(HandlerDeArchivos archivoHandler) {
        NodoCancion actual = this.catalogoCanciones;
        do {
            archivoHandler.guardarCancion(this.getValue(), actual.getValue());
            actual = actual.getNextMismoAutor();
        } while (actual != this.catalogoCanciones);
    }*/

    @Override
    public boolean equals(Object otro){
        try{
            NodoListaAutores nuevo = (NodoListaAutores) otro;
            return this.getValue().equals(nuevo.getValue()); //comparo por nombre del autor
        }catch( Exception e ) {
            return false;
        }
    }

    public NodoCancion getCatalogoCanciones() {
        return this.catalogoCanciones;
    }
}

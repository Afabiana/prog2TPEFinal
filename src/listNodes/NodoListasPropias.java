package listNodes;

import archivos.HandlerDeArchivos;
import serializables.Cancion;
import trees.nodes.NodoCancion;

public class NodoListasPropias extends NodoLista<String> {
    private NodoLista<NodoCancion> cancionesLista; // nomas guarda un valor de tipo NodoCancion

    public NodoListasPropias(String nombreLista) {
        super(nombreLista);
        this.cancionesLista = null;
    }

    public NodoLista<NodoCancion> getSublistaCancion() {
        return cancionesLista;
    }

    public NodoListasPropias getNext() {
        return (NodoListasPropias) super.getNext();
    }


    // add ordenado para la lista de canciones de la lista propia
    public NodoLista<NodoCancion> agregarCancion(NodoCancion nuevaCancion){
        NodoLista<NodoCancion> nuevaCancionNodo = new NodoLista<>(nuevaCancion);

        if (this.cancionesLista == null ){
            this.cancionesLista = nuevaCancionNodo;
            return nuevaCancionNodo;
        }

        NodoLista<NodoCancion> previo = null;
        NodoLista<NodoCancion> actual = this.cancionesLista;

        while(actual != null && actual.compareTo(nuevaCancionNodo) <= 0){
            if (actual.equals(nuevaCancionNodo))
                return actual;

            previo = actual;
            actual = actual.getNext();
        }


        if (previo == null){
            nuevaCancionNodo.setNext(this.cancionesLista);
            this.cancionesLista = nuevaCancionNodo;
        }else{
            previo.setNext(nuevaCancionNodo);
            previo.getNext().setNext(actual);
        }

        return nuevaCancionNodo;

    }


    public String toString(){
        String resultado = this.getValue() + "[";
        NodoLista<NodoCancion> next = this.cancionesLista;
        while (next != null){
            resultado += next.getValue() + ", ";
            next = next.getNext();
        }
        resultado += "]";
        return resultado;
    }


}

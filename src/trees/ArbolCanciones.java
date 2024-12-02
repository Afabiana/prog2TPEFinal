package trees;

import trees.nodes.NodoCancion;

public class ArbolCanciones extends Arbol<String>{
    public ArbolCanciones(){
        super();
    }


    public NodoCancion add(String tituloCancion){
        NodoCancion nuevoNodo = new NodoCancion(tituloCancion);
        return (NodoCancion) super.add(nuevoNodo);
    }

    // Como igual termine creando una clase para cada arbol, hago el casteo directamente aca
    @Override
    public NodoCancion buscar(String tituloCancion) {
        return (NodoCancion) super.buscar(tituloCancion);
    }

}

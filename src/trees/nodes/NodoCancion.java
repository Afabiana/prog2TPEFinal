package trees.nodes;

public class NodoCancion extends NodoArbol<String> implements Comparable<NodoCancion>{
    private NodoCancion mismoAutor; // referencia a otro nodo del arbol que comparte el mismo autor

    public NodoCancion(String titulo){
        super(titulo);
        this.mismoAutor = null;
    }

    /*
    * retorna el nodo siguiente de la lista circular
    */
    public NodoCancion getNextMismoAutor(){
        return this.mismoAutor;
    }

    public void setNextMismoAutor(NodoCancion next){
        this.mismoAutor = next;
    }


    public String toString(){
        return this.getValue();
    }

    @Override
    public int compareTo(NodoCancion otro) {
        return this.getValue().compareTo(otro.getValue()); //value = titulo
    }

    public boolean equals(Object otro){
        try {
            NodoCancion comparacion = (NodoCancion) otro;
            return this.getValue().equals(comparacion.getValue());
        }catch (Exception e){
            return false;
        }
    }
}
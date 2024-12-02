package listNodes;

public class NodoLista<T extends Comparable<T>> implements Comparable<NodoLista<T>> {
    private T value;
    private NodoLista<T> next;

    public NodoLista(T value){
        this.value = value;
        this.next = null;
    }

    public T getValue(){
        return this.value;
    }
    public NodoLista<T> getNext() {
        return next;
    }
    public void setNext(NodoLista<T> next) {
        this.next = next;
    }

    public int compareTo(NodoLista<T> otro){
        return this.value.compareTo(otro.getValue());
    }

    public String toString(){
        return this.value.toString();
    }

    public boolean equals(Object otro){
        try{
            NodoLista<T> nuevo = (NodoLista<T>) otro;
            return this.value.equals(nuevo.getValue());
        }catch( Exception e ) {
            return false;
        }
    }
}

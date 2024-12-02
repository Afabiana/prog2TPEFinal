package trees.nodes;

public class NodoArbol<T extends Comparable<T>> {

    private T value;
    private NodoArbol<T> left;
    private NodoArbol<T> right;

    public NodoArbol(T value) {
        this.value = value;
        this.left = null;
        this.right = null;
    }

    public NodoArbol<T> getLeft() {
        return left;
    }

    public void setLeft(NodoArbol<T> left) {
        this.left = left;
    }

    public NodoArbol<T> getRight() {
        return right;
    }

    public void setRight(NodoArbol<T> right) {
        this.right = right;
    }

    public T getValue(){
        return this.value;
    }

    public int compareTo(NodoArbol<T> nuevo) {
        return this.getValue().compareTo(nuevo.getValue());
    }
}

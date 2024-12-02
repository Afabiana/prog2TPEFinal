package trees;
import trees.nodes.NodoArbol;

public class Arbol<T extends Comparable<T>>{
    NodoArbol<T> root;

    public Arbol (){
        this.root = null;
    }

    public Arbol(NodoArbol<T> root){
        this.root = root;
    }

    public NodoArbol<T> getRoot(){
        return this.root;
    }

    protected NodoArbol<T> add(NodoArbol<T> nuevo){
        if (this.root == null)
            this.root = nuevo;
        else
            return addRecursivo(root, nuevo);

        return nuevo;
    }

    private NodoArbol<T> addRecursivo(NodoArbol<T> actual, NodoArbol<T> nuevo) {
        int comparacion = actual.compareTo(nuevo);

        if (comparacion == 0)
            return null;

       else if (comparacion > 0 ) { //Si el nodo actual es mayor al nuevo (segun el compareto), entonces voy a explorar su rama izq/menor

            if (actual.getLeft() == null){
                actual.setLeft(nuevo);
                return nuevo;

            } else
               return addRecursivo(actual.getLeft(), nuevo);

       } else {

            if (actual.getRight() == null){
                actual.setRight(nuevo);
                return nuevo;

            } else
               return addRecursivo(actual.getRight(), nuevo);
       }

    }


    public void imprimir(){
        imprimirRecursivo(this.root);
    }

    private void imprimirRecursivo(NodoArbol<T> actual){
        if (actual != null){
            imprimirRecursivo(actual.getLeft());
            System.out.println(actual);
            imprimirRecursivo(actual.getRight());
        }
    }

    public NodoArbol<T> buscar(T value){
        return buscarRecursivo(this.root, value);
    }

    private NodoArbol<T> buscarRecursivo(NodoArbol<T> actual, T value){
        if (actual == null)
            return null;

        int comparacion = actual.getValue().compareTo(value);

        if (comparacion == 0)
            return actual;
        else if (comparacion > 0)
            return buscarRecursivo(actual.getLeft(), value);
        else
            return buscarRecursivo(actual.getRight(), value);

    }


}

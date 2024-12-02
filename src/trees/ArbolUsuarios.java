package trees;
import entities.Usuario;
import trees.nodes.NodoUsuario;
public class ArbolUsuarios extends Arbol<Usuario>{

    public ArbolUsuarios(){
        super();
    }

    public NodoUsuario add(String nombreUsuario, String password) {
        NodoUsuario nuevoNodo = new NodoUsuario(nombreUsuario, password);
        return (NodoUsuario) super.add(nuevoNodo);
    }

    @Override
    public NodoUsuario buscar(Usuario nombreUsuario) {
        return (NodoUsuario) super.buscar(nombreUsuario);
    }

    @Override
    public NodoUsuario getRoot() {
        return (NodoUsuario) super.getRoot();
    }

    /*
    public void guardarEnPreorder(HandlerDeArchivos archivoHandler) {
        guardarEnPreorder(this.getRoot(), archivoHandler);
    }

    private void guardarEnPreorder(NodoUsuario actual, HandlerDeArchivos archivoHandler) {
        if (actual != null) {
            //por cada usuario visitado, guardo tambien sus listas propias y seguidas
            archivoHandler.guardarUsuario(actual.getValue());
            actual.guardarListasPropias(archivoHandler);
            actual.guardarListasSeguidas(archivoHandler);

            guardarEnPreorder(actual.getLeft(), archivoHandler);
            guardarEnPreorder(actual.getRight(), archivoHandler);
        }
    }*/

}

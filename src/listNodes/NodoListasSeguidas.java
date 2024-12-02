package listNodes;

public class NodoListasSeguidas extends NodoLista<String>{
    private String usuarioCreador; //usuario que creo la lista

    public NodoListasSeguidas(String nombreLista, String username){
        super(nombreLista);
        this.usuarioCreador = username;
    }

    public String getUsuarioCreador() {
        return usuarioCreador;
    }

    public NodoListasSeguidas getNext() {
        return (NodoListasSeguidas) super.getNext();
    }
}

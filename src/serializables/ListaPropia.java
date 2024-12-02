package serializables;

import java.io.Serializable;

public class ListaPropia implements Serializable {
    private static final long serialVersionUID = 2L;
    private String nombreUsuario;
    private String nombreLista;
    private String tituloCancion;

    public ListaPropia(String nombreUsuario, String nombreLista, String tituloCancion) {
        this.nombreUsuario = nombreUsuario;
        this.nombreLista = nombreLista;
        this.tituloCancion = tituloCancion;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getNombreLista() {
        return nombreLista;
    }

    public void setNombreLista(String nombreLista) {
        this.nombreLista = nombreLista;
    }

    public String getTituloCancion() {
        return tituloCancion;
    }

    public void setTituloCancion(String tituloCancion) {
        this.tituloCancion = tituloCancion;
    }

    @Override
    public String toString() {
        return "ListaPropia{" +
                "nombreUsuario='" + nombreUsuario + "' , " +
                "nombreLista='" + nombreLista + "' , " +
                "tituloCancion='" + tituloCancion +
                '}';
    }
}

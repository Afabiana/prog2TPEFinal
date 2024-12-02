package serializables;

import java.io.Serializable;

public class ListaSeguida implements Serializable {
    private static final long serialVersionUID = 3L;
    private String nombreUsuario;
    private String nombreSeguido;
    private String nombreLista;

    public ListaSeguida(String nombreUsuario, String nombreSeguido, String nombreLista) {
        this.nombreUsuario = nombreUsuario;
        this.nombreSeguido = nombreSeguido;
        this.nombreLista = nombreLista;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getNombreSeguido() {
        return nombreSeguido;
    }

    public void setNombreSeguido(String nombreSeguido) {
        this.nombreSeguido = nombreSeguido;
    }

    public String getNombreLista() {
        return nombreLista;
    }

    public void setNombreLista(String nombreLista) {
        this.nombreLista = nombreLista;
    }

    @Override
    public String toString() {
        return "ListaSeguida{" +
                "nombreUsuario='" + nombreUsuario + '\'' +
                ", nombreSeguido='" + nombreSeguido + '\'' +
                ", nombreLista='" + nombreLista + '\'' +
                '}';
    }
}

package serializables;

import java.io.Serializable;

public class Cancion implements Serializable {
    private static final long serialVersionUID = 1L;
    private String titulo;
    private String autor;

    public Cancion(String titulo, String autor) {
        this.titulo = titulo;
        this.autor = autor;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public String getAutor() {
        return this.autor;
    }

    @Override
    public String toString() {
        return "Cancion{" +
                "titulo='" + this.getTitulo() + '\'' +
                ", autor='" + this.getAutor() + '\'' +
                '}';
    }
}

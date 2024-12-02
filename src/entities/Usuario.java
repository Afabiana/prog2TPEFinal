package entities;
import java.io.Serializable;

public class Usuario implements Comparable<Usuario>, Serializable {
    private static final long serialVersionUID = 4L;
    private String user;
    private String password;

    public Usuario(String user, String password) {
        this.user = user;
        this.password = password;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int compareTo(Usuario otro){
        return this.getUser().compareTo(otro.getUser());
    }

    public boolean equals(Object otro){
        try{
            Usuario otroUsuario = (Usuario) otro;
            return this.getUser().equals(otroUsuario.getUser());

        } catch (ClassCastException e){
            return false;
        }
    }

    public String toString(){
        return "Usuario{" +
                    "user='" + this.getUser() + '\'' +
                    ", password='" + this.getPassword() + '\'' +
                '}';
    }
}

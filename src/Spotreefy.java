import sistema.Menu;

public class Spotreefy {
    public static void main (String[] args){

        /*----------------------------- Entrega -----------------------------*/
        Menu menu = new Menu();
        menu.ejecutarPrimerMenu();
        menu.getSistema().imprimirTodo();
    }
}

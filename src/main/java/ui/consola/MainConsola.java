package ui.consola;
import servicio.GestorTareasService;

public class MainConsola {
    public static void main(String[] args){
        GestorTareasService servicio = new GestorTareasService();
        MenuConsola menu = new MenuConsola(servicio);
        menu.iniciar();
    }
}

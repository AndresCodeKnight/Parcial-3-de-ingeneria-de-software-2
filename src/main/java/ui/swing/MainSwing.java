package ui.swing;

import servicio.GestorTareasService;
import javax.swing.SwingUtilities;

public class MainSwing {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GestorTareasService servicio = new GestorTareasService();
            VentanaPrincipal ventana = new VentanaPrincipal(servicio);
            ventana.setVisible(true);
        });
    }
}
package comando;

import modelo.ComponenteTarea;
import java.util.List;

public class CrearTareaCmd implements Comando {
    private final List<ComponenteTarea> listaTareas;
    private final ComponenteTarea tarea;

    public CrearTareaCmd(List<ComponenteTarea> listaTareas, ComponenteTarea tarea) {
        this.listaTareas = listaTareas;
        this.tarea = tarea;
    }

    @Override
    public void ejecutar() {
        listaTareas.add(tarea);
    }

    @Override
    public void deshacer() {
        listaTareas.remove(tarea);
    }
}

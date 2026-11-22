package comando;

import modelo.ComponenteTarea;
import java.util.List;

public class EliminarTareaCmd implements Comando {
    private final List<ComponenteTarea> listaTareas;
    private final ComponenteTarea tarea;
    private int indiceOriginal;

    public EliminarTareaCmd(List<ComponenteTarea> listaTareas, ComponenteTarea tarea) {
        this.listaTareas = listaTareas;
        this.tarea = tarea;
    }

    @Override
    public void ejecutar() {
        indiceOriginal = listaTareas.indexOf(tarea);
        listaTareas.remove(tarea);
    }

    @Override
    public void deshacer() {
        if (indiceOriginal >= 0 && indiceOriginal <= listaTareas.size()) {
            listaTareas.add(indiceOriginal, tarea);
        } else {
            listaTareas.add(tarea);
        }
    }
}

package comando;

import modelo.TareaSimple;
import estado.EstadoTarea;

public class CambiarEstadoCmd implements Comando {
    private final TareaSimple tarea;
    private final EstadoTarea estadoAnterior;
    private final Runnable transicion;

    public CambiarEstadoCmd(TareaSimple tarea, Runnable transicion) {
        this.tarea = tarea;
        this.estadoAnterior = tarea.getEstadoObj();
        this.transicion = transicion;
    }

    @Override
    public void ejecutar() {
        transicion.run();
    }

    @Override
    public void deshacer() {
        tarea.setEstado(estadoAnterior);
    }
}
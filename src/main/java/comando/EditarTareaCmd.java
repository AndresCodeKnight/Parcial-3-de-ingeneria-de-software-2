package comando;

import modelo.ComponenteTarea;
import modelo.Prioridad;
import java.time.LocalDate;

public class EditarTareaCmd implements Comando {
    private final ComponenteTarea tarea;
    private final String tituloAnterior;
    private final Prioridad prioridadAnterior;
    private final LocalDate fechaAnterior;
    private final String responsableAnterior;

    private final String nuevoTitulo;
    private final Prioridad nuevaPrioridad;
    private final LocalDate nuevaFecha;
    private final String nuevoResponsable;

    public EditarTareaCmd(ComponenteTarea tarea,
                          String nuevoTitulo,
                          Prioridad nuevaPrioridad,
                          LocalDate nuevaFecha,
                          String nuevoResponsable) {
        this.tarea = tarea;
        this.tituloAnterior = tarea.getTitulo();
        this.prioridadAnterior = tarea.getPrioridad();
        this.fechaAnterior = tarea.getFechaVencimiento();
        this.responsableAnterior = tarea.getResponsable();

        this.nuevoTitulo = nuevoTitulo;
        this.nuevaPrioridad = nuevaPrioridad;
        this.nuevaFecha = nuevaFecha;
        this.nuevoResponsable = nuevoResponsable;
    }

    @Override
    public void ejecutar() {
        tarea.setTitulo(nuevoTitulo);
        tarea.setPrioridad(nuevaPrioridad);
        tarea.setFechaVencimiento(nuevaFecha);
        tarea.setResponsable(nuevoResponsable);
    }

    @Override
    public void deshacer() {
        tarea.setTitulo(tituloAnterior);
        tarea.setPrioridad(prioridadAnterior);
        tarea.setFechaVencimiento(fechaAnterior);
        tarea.setResponsable(responsableAnterior);
    }
}

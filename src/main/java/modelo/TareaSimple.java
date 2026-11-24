package modelo;

import estado.EstadoTarea;
import estado.EstadoPendiente;
import java.time.LocalDate;

public class TareaSimple extends ComponenteTarea {
    private EstadoTarea estado;

    public TareaSimple(String id, String titulo, Prioridad prioridad,
            LocalDate fechaVencimiento, String responsable, EstadoTarea estado) {
        super(id, titulo, prioridad, fechaVencimiento, responsable);
        this.estado = (estado != null) ? estado : new EstadoPendiente();
    }

    public TareaSimple(String id, String titulo, Prioridad prioridad,
            LocalDate fechaVencimiento, String responsable) {
        this(id, titulo, prioridad, fechaVencimiento, responsable, new EstadoPendiente());
    }

    public EstadoTarea getEstadoObj() {return estado;}

    public void setEstado(EstadoTarea estado) {
        if (estado == null) {
            throw new IllegalArgumentException("El estado de la tarea no puede ser nulo.");
        }
        this.estado = estado;
    }

    @Override
    public String getEstadoActual() {return estado.getNombre();}
}

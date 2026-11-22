package modelo;

import java.time.LocalDate;

public abstract class ComponenteTarea {
    protected String id;
    protected String titulo;
    protected Prioridad prioridad;
    protected LocalDate fechaVencimiento;
    protected String responsable;

    public String getId() { return id; }
    public String getTitulo() { return titulo; }
    public Prioridad getPrioridad() { return prioridad; }
    public LocalDate getFechaVencimiento() { return fechaVencimiento; }
    public String getResponsable() { return responsable; }

    public abstract String getEstadoActual();
}

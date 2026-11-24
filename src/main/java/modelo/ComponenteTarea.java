package modelo;

import java.time.LocalDate;

public abstract class ComponenteTarea {
    protected String id;
    protected String titulo;
    protected Prioridad prioridad;
    protected LocalDate fechaVencimiento;
    protected String responsable;
    private TareaCompuesta padre;

    public ComponenteTarea(String id, String titulo, Prioridad prioridad,
            LocalDate fechaVencimiento, String responsable) {
        if (id == null || id.isBlank()) {throw new IllegalArgumentException("El ID es obligatorio.");}
        if (titulo == null || titulo.isBlank()) {throw new IllegalArgumentException("El título es obligatorio.");}
        if (prioridad == null) {throw new IllegalArgumentException("La prioridad es obligatoria.");}
        this.id = id;
        this.titulo = titulo;
        this.prioridad = prioridad;
        this.fechaVencimiento = fechaVencimiento;
        this.responsable = responsable;
    }

    public String getId() {return id;}

    public String getTitulo() {return titulo;}

    public Prioridad getPrioridad() {return prioridad;}

    public LocalDate getFechaVencimiento() {return fechaVencimiento;}

    public String getResponsable() {return responsable;}

    public abstract String getEstadoActual();

    TareaCompuesta getPadre() {return padre;}

    void setPadre(TareaCompuesta padre) {this.padre = padre;}
}

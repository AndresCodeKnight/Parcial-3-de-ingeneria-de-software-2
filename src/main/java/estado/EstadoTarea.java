package estado;

import modelo.TareaSimple;

public interface EstadoTarea {
    String getNombre();
    void iniciar(TareaSimple tarea);
    void enviarARevision(TareaSimple tarea);
    void finalizar(TareaSimple tarea);
    void devolverAPendiente(TareaSimple tarea);
}

package estado;

import modelo.TareaSimple;

public class EstadoPendiente implements EstadoTarea {

    @Override
    public String getNombre() {return "Pendiente";}

    @Override
    public void iniciar(TareaSimple tarea) {tarea.setEstado(new EstadoEnCurso());}

    @Override
    public void enviarARevision(TareaSimple tarea) {
        throw new IllegalStateException("No se puede enviar a revisión una tarea pendiente.");
    }

    @Override
    public void finalizar(TareaSimple tarea) {
        throw new IllegalStateException("No se puede finalizar directamente una tarea pendiente.");
    }

    @Override
    public void devolverAPendiente(TareaSimple tarea) {
        throw new IllegalStateException("La tarea ya se encuentra pendiente.");
    }
}

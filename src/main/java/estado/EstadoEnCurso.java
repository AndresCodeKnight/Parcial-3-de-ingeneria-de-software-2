package estado;

import modelo.TareaSimple;

public class EstadoEnCurso implements EstadoTarea {

    @Override
    public String getNombre() {return "En curso";}

    @Override
    public void iniciar(TareaSimple tarea) {
        throw new IllegalStateException("La tarea ya se encuentra en curso.");
    }

    @Override
    public void enviarARevision(TareaSimple tarea) {tarea.setEstado(new EstadoEnRevision());}

    @Override
    public void finalizar(TareaSimple tarea) {
        throw new IllegalStateException("La tarea debe pasar primero por revisión.");
    }

    @Override
    public void devolverAPendiente(TareaSimple tarea) {
        throw new IllegalStateException("No está permitida la transición de En curso a Pendiente.");
    }
}

package estado;

import modelo.TareaSimple;

public class EstadoEnRevision implements EstadoTarea {

    @Override
    public String getNombre() {return "En revisión";}

    @Override
    public void iniciar(TareaSimple tarea) {tarea.setEstado(new EstadoEnCurso());}

    @Override
    public void enviarARevision(TareaSimple tarea) {
        throw new IllegalStateException("La tarea ya se encuentra en revisión.");
    }

    @Override
    public void finalizar(TareaSimple tarea) {tarea.setEstado(new EstadoTerminada());}

    @Override
    public void devolverAPendiente(TareaSimple tarea) {
        throw new IllegalStateException("No está permitida la transición de En revisión a Pendiente.");
    }
}

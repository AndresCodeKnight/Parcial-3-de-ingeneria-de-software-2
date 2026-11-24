package estado;

import modelo.TareaSimple;

public class EstadoTerminada implements EstadoTarea {

    @Override
    public String getNombre() {return "Terminada";}

    @Override
    public void iniciar(TareaSimple tarea) {
        throw new IllegalStateException("No se puede iniciar una tarea terminada.");
    }

    @Override
    public void enviarARevision(TareaSimple tarea) {
        throw new IllegalStateException("No se puede enviar a revisión una tarea terminada.");
    }

    @Override
    public void finalizar(TareaSimple tarea) {
        throw new IllegalStateException("La tarea ya se encuentra terminada.");
    }

    @Override
    public void devolverAPendiente(TareaSimple tarea) {
        throw new IllegalStateException("No se puede devolver a pendiente una tarea terminada.");
    }
}

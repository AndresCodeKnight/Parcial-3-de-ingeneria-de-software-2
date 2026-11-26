package estado;

import modelo.TareaSimple;
import modelo.Prioridad;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class EstadoTareaTest {
    private TareaSimple crearTarea() {
        return new TareaSimple("T001", "Prueba de tarea", Prioridad.values()[0],
                LocalDate.now(), "Walter");
    }

    @Test
    void tareaNuevaDebeEstarPendiente() {
        TareaSimple tarea = crearTarea();
        assertEquals("Pendiente", tarea.getEstadoActual());
    }

    @Test
    void pendientePuedePasarAEnCurso() {
        TareaSimple tarea = crearTarea();
        tarea.getEstadoObj().iniciar(tarea);
        assertEquals("En curso", tarea.getEstadoActual());
    }

    @Test
    void pendienteNoPuedeFinalizar() {
        TareaSimple tarea = crearTarea();
        assertThrows(IllegalStateException.class, () -> tarea.getEstadoObj().finalizar(tarea));
        assertEquals("Pendiente", tarea.getEstadoActual());
    }

    @Test
    void enCursoPuedePasarARevision() {
        TareaSimple tarea = crearTarea();
        tarea.getEstadoObj().iniciar(tarea);
        tarea.getEstadoObj().enviarARevision(tarea);
        assertEquals("En revisión", tarea.getEstadoActual());
    }

    @Test
    void revisionPuedeVolverAEnCurso() {
        TareaSimple tarea = crearTarea();
        tarea.getEstadoObj().iniciar(tarea);
        tarea.getEstadoObj().enviarARevision(tarea);
        tarea.getEstadoObj().iniciar(tarea);
        assertEquals("En curso", tarea.getEstadoActual());
    }

    @Test
    void revisionPuedeFinalizar() {
        TareaSimple tarea = crearTarea();
        tarea.getEstadoObj().iniciar(tarea);
        tarea.getEstadoObj().enviarARevision(tarea);
        tarea.getEstadoObj().finalizar(tarea);
        assertEquals("Terminada", tarea.getEstadoActual());
    }

    @Test
    void tareaTerminadaNoPuedeCambiarDeEstado() {
        TareaSimple tarea = crearTarea();
        tarea.getEstadoObj().iniciar(tarea);
        tarea.getEstadoObj().enviarARevision(tarea);
        tarea.getEstadoObj().finalizar(tarea);
        assertThrows(IllegalStateException.class, () -> tarea.getEstadoObj().iniciar(tarea));
        assertEquals("Terminada", tarea.getEstadoActual());
    }
}

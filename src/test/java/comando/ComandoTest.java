package comando;

import modelo.Prioridad;
import modelo.TareaSimple;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import servicio.GestorTareasService;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ComandoTest {
    private GestorTareasService servicio;
    private TareaSimple tarea;

    @BeforeEach
    void setUp() {
        servicio = new GestorTareasService();
        tarea = new TareaSimple("T1", "Diseñar UML", Prioridad.ALTA, LocalDate.now().plusDays(2), "Andres");
    }

    @Test
    void agregarDeshacerYRehacerTarea() {
        servicio.agregarTarea(tarea);
        assertEquals(1, servicio.listarTareas().size());

        servicio.deshacer();
        assertEquals(0, servicio.listarTareas().size());

        servicio.rehacer();
        assertEquals(1, servicio.listarTareas().size());
    }

    @Test
    void cambiarEstadoYDeshacer() {
        servicio.agregarTarea(tarea);
        servicio.cambiarEstado("T1", "iniciar");
        assertEquals("En curso", tarea.getEstadoActual());

        servicio.deshacer();
        assertEquals("Pendiente", tarea.getEstadoActual());
    }

    @Test
    void editarTareaYDeshacer() {
        servicio.agregarTarea(tarea);
        servicio.editarTarea("T1", "UML Final", Prioridad.BAJA, LocalDate.now().plusDays(5), "Carlos");
        assertEquals("UML Final", tarea.getTitulo());
        assertEquals(Prioridad.BAJA, tarea.getPrioridad());

        servicio.deshacer();
        assertEquals("Diseñar UML", tarea.getTitulo());
        assertEquals(Prioridad.ALTA, tarea.getPrioridad());
    }
}

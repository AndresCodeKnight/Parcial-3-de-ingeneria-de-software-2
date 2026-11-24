package modelo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TareaCompuestaTest {
    private TareaSimple crearTareaSimple(String id) {
        return new TareaSimple(id, "Tarea " + id, Prioridad.values()[0],
                null, "Responsable");
    }

    private TareaCompuesta crearTareaCompuesta(String id) {
        return new TareaCompuesta(id, "Proyecto " + id, Prioridad.values()[0],
                null, "Responsable");
    }

    @Test
    void debeAgregarSubtareaCorrectamente() {
        TareaCompuesta proyecto = crearTareaCompuesta("P001");
        TareaSimple tarea = crearTareaSimple("T001");
        proyecto.agregarSubtarea(tarea);
        assertEquals(1, proyecto.getSubtareas().size());
        assertSame(tarea, proyecto.getSubtareas().get(0));
    }

    @Test
    void noDebeAgregarUnaSubtareaNula() {
        TareaCompuesta proyecto = crearTareaCompuesta("P001");
        assertThrows(IllegalArgumentException.class, () -> proyecto.agregarSubtarea(null));
    }

    @Test
    void noDebeContenerseASiMisma() {
        TareaCompuesta proyecto = crearTareaCompuesta("P001");
        assertThrows(IllegalArgumentException.class, () -> proyecto.agregarSubtarea(proyecto));
    }

    @Test
    void debeEliminarSubtareaCorrectamente() {
        TareaCompuesta proyecto = crearTareaCompuesta("P001");
        TareaSimple tarea = crearTareaSimple("T001");
        proyecto.agregarSubtarea(tarea);
        assertTrue(proyecto.eliminarSubtarea("T001"));
        assertTrue(proyecto.getSubtareas().isEmpty());
    }

    @Test
    void noDebeEliminarUnaSubtareaInexistente() {
        TareaCompuesta proyecto = crearTareaCompuesta("P001");
        assertFalse(proyecto.eliminarSubtarea("T999"));
    }

    @Test
    void noDebePermitirModificarLaListaDesdeElExterior() {
        TareaCompuesta proyecto = crearTareaCompuesta("P001");
        assertThrows(UnsupportedOperationException.class, () -> proyecto.getSubtareas().add(crearTareaSimple("T001")));
    }

    @Test
    void noDebePermitirDependenciasCirculares() {
        TareaCompuesta padre = crearTareaCompuesta("P001");
        TareaCompuesta hijo = crearTareaCompuesta("P002");
        padre.agregarSubtarea(hijo);
        assertThrows(IllegalArgumentException.class, () -> hijo.agregarSubtarea(padre));
    }

    @Test
    void noDebePermitirIdsDuplicadosEnLaEstructura() {
        TareaCompuesta proyecto = crearTareaCompuesta("P001");
        proyecto.agregarSubtarea(crearTareaSimple("T001"));
        assertThrows(IllegalArgumentException.class, () -> proyecto.agregarSubtarea(crearTareaSimple("T001")));
    }

    @Test
    void noDebePermitirIdsDuplicadosEnNivelesDiferentes() {
        TareaCompuesta proyecto = crearTareaCompuesta("P001");
        TareaCompuesta subproyecto = crearTareaCompuesta("P002");
        subproyecto.agregarSubtarea(crearTareaSimple("T001"));
        proyecto.agregarSubtarea(subproyecto);
        assertThrows(IllegalArgumentException.class, () -> proyecto.agregarSubtarea(crearTareaSimple("T001")));
    }

    @Test
    void unaTareaNoDebePertenecerADosPadres() {
        TareaCompuesta proyecto1 = crearTareaCompuesta("P001");
        TareaCompuesta proyecto2 = crearTareaCompuesta("P002");
        TareaSimple tarea = crearTareaSimple("T001");
        proyecto1.agregarSubtarea(tarea);
        assertThrows(IllegalArgumentException.class, () -> proyecto2.agregarSubtarea(tarea));
        assertEquals(1, proyecto1.getSubtareas().size());
        assertTrue(proyecto2.getSubtareas().isEmpty());
    }

    @Test
    void debePermitirReasignarUnaTareaDespuesDeEliminarla() {
        TareaCompuesta proyecto1 = crearTareaCompuesta("P001");
        TareaCompuesta proyecto2 = crearTareaCompuesta("P002");
        TareaSimple tarea = crearTareaSimple("T001");
        proyecto1.agregarSubtarea(tarea);
        proyecto1.eliminarSubtarea("T001");
        proyecto2.agregarSubtarea(tarea);
        assertTrue(proyecto1.getSubtareas().isEmpty());
        assertSame(tarea, proyecto2.getSubtareas().get(0));
    }

    @Test
    void proyectoVacioDebeTenerCeroProgreso() {
        TareaCompuesta proyecto = crearTareaCompuesta("P001");
        assertEquals(0.0, proyecto.getProgreso(), 0.001);
    }

    @Test
    void debeCalcularCorrectamenteElProgreso() {
        TareaCompuesta proyecto = crearTareaCompuesta("P001");
        TareaSimple tarea1 = crearTareaSimple("T001");
        TareaSimple tarea2 = crearTareaSimple("T002");
        proyecto.agregarSubtarea(tarea1);
        proyecto.agregarSubtarea(tarea2);
        tarea2.getEstadoObj().iniciar(tarea2);
        tarea2.getEstadoObj().enviarARevision(tarea2);
        tarea2.getEstadoObj().finalizar(tarea2);
        assertEquals(50.0, proyecto.getProgreso(), 0.001);
    }

    @Test
    void todasLasTareasTerminadasDebenDarCienPorCiento() {
        TareaCompuesta proyecto = crearTareaCompuesta("P001");
        TareaSimple tarea = crearTareaSimple("T001");
        proyecto.agregarSubtarea(tarea);
        tarea.getEstadoObj().iniciar(tarea);
        tarea.getEstadoObj().enviarARevision(tarea);
        tarea.getEstadoObj().finalizar(tarea);
        assertEquals(100.0, proyecto.getProgreso(), 0.001);
    }

    @Test
    void proyectoVacioDebeEstarPendiente() {
        TareaCompuesta proyecto = crearTareaCompuesta("P001");
        assertEquals("Pendiente", proyecto.getEstadoActual());
    }

    @Test
    void tareasConEstadosDiferentesDebenDarEstadoEnCurso() {
        TareaCompuesta proyecto = crearTareaCompuesta("P001");
        TareaSimple tarea1 = crearTareaSimple("T001");
        TareaSimple tarea2 = crearTareaSimple("T002");
        proyecto.agregarSubtarea(tarea1);
        proyecto.agregarSubtarea(tarea2);
        tarea2.getEstadoObj().iniciar(tarea2);
        assertEquals("En curso", proyecto.getEstadoActual());
    }
}

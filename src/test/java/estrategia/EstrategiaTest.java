package estrategia;

import modelo.ComponenteTarea;
import modelo.Prioridad;
import modelo.TareaSimple;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import servicio.GestorTareasService;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EstrategiaTest {
    private GestorTareasService servicio;

    @BeforeEach
    void setUp() {
        servicio = new GestorTareasService();
        servicio.agregarTarea(new TareaSimple("T1", "Backend", Prioridad.ALTA, LocalDate.now().plusDays(3), "Andres"));
        servicio.agregarTarea(new TareaSimple("T2", "Arte UI", Prioridad.BAJA, LocalDate.now().plusDays(1), "Daniel"));
        servicio.agregarTarea(new TareaSimple("T3", "Pruebas", Prioridad.MEDIA, LocalDate.now().plusDays(2), "Andres"));
    }

    @Test
    void filtrarPorPrioridadYResponsable() {
        List<ComponenteTarea> altas = servicio.consultarTareas(new FiltroPorPrioridad(Prioridad.ALTA), null, true);
        assertEquals(1, altas.size());
        assertEquals("T1", altas.get(0).getId());

        List<ComponenteTarea> deAndres = servicio.consultarTareas(new FiltroPorResponsable("Andres"), null, true);
        assertEquals(2, deAndres.size());
    }

    @Test
    void ordenarPorTituloYFecha() {
        List<ComponenteTarea> porTituloAsc = servicio.consultarTareas(null, new OrdenPorTitulo(), true);
        assertEquals("Arte UI", porTituloAsc.get(0).getTitulo());

        List<ComponenteTarea> porFechaAsc = servicio.consultarTareas(null, new OrdenPorFecha(), true);
        assertEquals("T2", porFechaAsc.get(0).getId());
    }
}

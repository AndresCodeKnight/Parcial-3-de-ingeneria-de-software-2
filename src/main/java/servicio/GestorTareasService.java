package servicio;

import comando.*;
import modelo.*;
import estrategia.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GestorTareasService {
    private final List<ComponenteTarea> tareas = new ArrayList<>();
    private final GestorHistorial historial = new GestorHistorial();

    public void agregarTarea(ComponenteTarea tarea) {
        historial.ejecutarComando(new CrearTareaCmd(tareas, tarea));
    }

    public void eliminarTarea(String idTarea) {
        ComponenteTarea tarea = buscarPorId(idTarea);
        historial.ejecutarComando(new EliminarTareaCmd(tareas, tarea));
    }

    public void cambiarEstado(String idTarea, String accion) {
        ComponenteTarea comp = buscarPorId(idTarea);
        if (!(comp instanceof TareaSimple)) {
            throw new IllegalStateException("El estado de una tarea compuesta depende de sus subtareas.");
        }
        TareaSimple simple = (TareaSimple) comp;
        Runnable transicion = switch (accion.toLowerCase()) {
            case "iniciar" -> () -> simple.getEstadoObj().iniciar(simple);
            case "revision" -> () -> simple.getEstadoObj().enviarARevision(simple);
            case "finalizar" -> () -> simple.getEstadoObj().finalizar(simple);
            case "pendiente" -> () -> simple.getEstadoObj().devolverAPendiente(simple);
            default -> throw new IllegalArgumentException("Acción de estado no válida.");
        };
        historial.ejecutarComando(new CambiarEstadoCmd(simple, transicion));
    }

    public void deshacer() { historial.deshacer(); }
    public void rehacer() { historial.rehacer(); }

    public List<ComponenteTarea> listarTareas() {
        return Collections.unmodifiableList(tareas);
    }

    public List<ComponenteTarea> consultarTareas(EstrategiaFiltro filtro,
                                                 EstrategiaOrdenamiento orden,
                                                 boolean ascendente) {
        List<ComponenteTarea> resultado = new ArrayList<>(tareas);
        if (filtro != null) {
            resultado = filtro.filtrar(resultado);
        }
        if (orden != null) {
            resultado = orden.ordenar(resultado, ascendente);
        }
        return resultado;
    }

    public ComponenteTarea buscarPorId(String id) {
        return tareas.stream()
                .filter(t -> t.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Tarea no encontrada con ID: " + id));
    }
}

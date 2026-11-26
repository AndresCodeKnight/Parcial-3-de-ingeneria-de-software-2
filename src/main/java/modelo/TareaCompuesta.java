package modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TareaCompuesta extends ComponenteTarea {
    private List<ComponenteTarea> subtareas;

    public TareaCompuesta(String id, String titulo, Prioridad prioridad,
            LocalDate fechaVencimiento, String responsable) {
        super(id, titulo, prioridad, fechaVencimiento, responsable);
        this.subtareas = new ArrayList<>();
    }

    public void agregarSubtarea(ComponenteTarea tarea) {
        if (tarea == null) {throw new IllegalArgumentException("La subtarea no puede ser nula.");}
        if (tarea == this) {throw new IllegalArgumentException("Una tarea no puede contenerse a sí misma.");}
        if (tarea instanceof TareaCompuesta compuesta && compuesta.contieneReferencia(this)) {throw new IllegalArgumentException("No se puede crear una dependencia circular.");}
        Set<String> idsExistentes = new HashSet<>();
        registrarIds(this, idsExistentes);
        if (!registrarIds(tarea, idsExistentes)) {
            throw new IllegalArgumentException("Ya existe una tarea con ese ID en la estructura.");
        }
        if (tarea.getPadre() != null) {throw new IllegalArgumentException("La tarea ya pertenece a otra tarea compuesta.");}
        subtareas.add(tarea);
        tarea.setPadre(this);
    }

    private static boolean registrarIds(
            ComponenteTarea tarea, Set<String> ids) {
        if (!ids.add(tarea.getId())) {return false;}
        if (tarea instanceof TareaCompuesta compuesta) {
            for (ComponenteTarea subtarea : compuesta.getSubtareas()) {
                if (!registrarIds(subtarea, ids)) {return false;}
            }
        }
        return true;
    }

    private boolean contieneReferencia(ComponenteTarea objetivo) {
        for (ComponenteTarea tarea : subtareas) {
            if (tarea == objetivo) {return true;}
            if (tarea instanceof TareaCompuesta compuesta && compuesta.contieneReferencia(objetivo)) {return true;}
        }
        return false;
    }

    public boolean eliminarSubtarea(String id) {
        for (int i = 0; i < subtareas.size(); i++) {
            ComponenteTarea tarea = subtareas.get(i);
            if (tarea.getId().equals(id)) {
                subtareas.remove(i);
                tarea.setPadre(null);
                return true;
            }
        }
        return false;
    }

    public List<ComponenteTarea> getSubtareas() {return Collections.unmodifiableList(subtareas);}

    public double getProgreso() {
        int total = contarTareasSimples();
        int terminadas = contarTareasTerminadas();
        if (total == 0) {return 0.0;}
        return (double) terminadas / total * 100;
    }

    private int contarTareasSimples() {
        int total = 0;
        for (ComponenteTarea tarea : subtareas) {
            if (tarea instanceof TareaSimple) {
                total++;
            } else if (tarea instanceof TareaCompuesta compuesta) {
                total += compuesta.contarTareasSimples();
            }
        }
        return total;
    }

    private int contarTareasTerminadas() {
        int terminadas = 0;
        for (ComponenteTarea tarea : subtareas) {
            if (tarea instanceof TareaSimple) {
                if ("Terminada".equals(tarea.getEstadoActual())) {terminadas++;}
            } else if (tarea instanceof TareaCompuesta compuesta) {
                terminadas += compuesta.contarTareasTerminadas();
            }
        }
        return terminadas;
    }

    @Override
    public String getEstadoActual() {
        Set<String> estados = new HashSet<>();
        recopilarEstados(estados);
        if (estados.isEmpty()) {return "Pendiente";}
        if (estados.size() == 1) {return estados.iterator().next();}
        return "En curso";
    }

    private void recopilarEstados(Set<String> estados) {
        for (ComponenteTarea tarea : subtareas) {
            if (tarea instanceof TareaSimple) {estados.add(tarea.getEstadoActual());
            } else if (tarea instanceof TareaCompuesta compuesta) {
                compuesta.recopilarEstados(estados);
            }
        }
    }
}

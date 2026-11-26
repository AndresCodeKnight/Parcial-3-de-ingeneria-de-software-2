package estrategia;

import modelo.ComponenteTarea;
import modelo.Prioridad;
import java.util.List;
import java.util.stream.Collectors;

public class FiltroPorPrioridad implements EstrategiaFiltro {
    private final Prioridad prioridadBuscada;

    public FiltroPorPrioridad(Prioridad prioridadBuscada) {
        this.prioridadBuscada = prioridadBuscada;
    }

    @Override
    public List<ComponenteTarea> filtrar(List<ComponenteTarea> tareas) {
        return tareas.stream()
                .filter(t -> t.getPrioridad() == prioridadBuscada)
                .collect(Collectors.toList());
    }
}

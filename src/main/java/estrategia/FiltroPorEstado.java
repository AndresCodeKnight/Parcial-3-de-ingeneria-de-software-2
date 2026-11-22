package estrategia;

import modelo.ComponenteTarea;
import java.util.List;
import java.util.stream.Collectors;

public class FiltroPorEstado implements EstrategiaFiltro {
    private final String estadoBuscado;

    public FiltroPorEstado(String estadoBuscado) {
        this.estadoBuscado = estadoBuscado;
    }

    @Override
    public List<ComponenteTarea> filtrar(List<ComponenteTarea> tareas) {
        return tareas.stream()
                .filter(t -> t.getEstadoActual().equalsIgnoreCase(estadoBuscado))
                .collect(Collectors.toList());
    }
}
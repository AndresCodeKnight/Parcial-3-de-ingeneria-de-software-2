package estrategia;

import modelo.ComponenteTarea;
import java.util.List;
import java.util.stream.Collectors;

public class FiltroPorResponsable implements EstrategiaFiltro {
    private final String responsable;

    public FiltroPorResponsable(String responsable) {
        this.responsable = responsable;
    }

    @Override
    public List<ComponenteTarea> filtrar(List<ComponenteTarea> tareas) {
        return tareas.stream()
                .filter(t -> t.getResponsable() != null &&
                        t.getResponsable().equalsIgnoreCase(responsable))
                .collect(Collectors.toList());
    }
}
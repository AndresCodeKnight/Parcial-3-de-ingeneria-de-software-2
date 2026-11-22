package estrategia;

import modelo.ComponenteTarea;
import java.util.List;

public interface EstrategiaFiltro {
    List<ComponenteTarea> filtrar(List<ComponenteTarea> tareas);
}
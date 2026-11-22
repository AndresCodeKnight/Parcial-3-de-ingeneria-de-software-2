package estrategia;

import modelo.ComponenteTarea;
import java.util.List;

public interface EstrategiaOrdenamiento {
    List<ComponenteTarea> ordenar(List<ComponenteTarea> tareas, boolean ascendente);
}

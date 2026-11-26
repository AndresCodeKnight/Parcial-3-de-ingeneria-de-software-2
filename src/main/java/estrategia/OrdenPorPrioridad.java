package estrategia;

import modelo.ComponenteTarea;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class OrdenPorPrioridad implements EstrategiaOrdenamiento {
    @Override
    public List<ComponenteTarea> ordenar(List<ComponenteTarea> tareas, boolean ascendente) {
        List<ComponenteTarea> copia = new ArrayList<>(tareas);
        Comparator<ComponenteTarea> comparador = Comparator.comparing(ComponenteTarea::getPrioridad);
        if (!ascendente) comparador = comparador.reversed();
        copia.sort(comparador);
        return copia;
    }
}

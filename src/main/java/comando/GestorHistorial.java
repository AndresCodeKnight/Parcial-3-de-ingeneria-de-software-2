package comando;

import java.util.ArrayDeque;
import java.util.Deque;

public class GestorHistorial {
    private final Deque<Comando> pilaUndo = new ArrayDeque<>();
    private final Deque<Comando> pilaRedo = new ArrayDeque<>();

    public void ejecutarComando(Comando comando) {
        comando.ejecutar();
        pilaUndo.push(comando);
        pilaRedo.clear();
    }

    public void deshacer() {
        if (pilaUndo.isEmpty()) {
            throw new IllegalStateException("No hay acciones para deshacer.");
        }
        Comando comando = pilaUndo.pop();
        comando.deshacer();
        pilaRedo.push(comando);
    }

    public void rehacer() {
        if (pilaRedo.isEmpty()) {
            throw new IllegalStateException("No hay acciones para rehacer.");
        }
        Comando comando = pilaRedo.pop();
        comando.ejecutar();
        pilaUndo.push(comando);
    }

    public boolean puedeDeshacer() { return !pilaUndo.isEmpty(); }
    public boolean puedeRehacer() { return !pilaRedo.isEmpty(); }
}
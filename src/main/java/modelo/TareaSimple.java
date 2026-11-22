package modelo;

import estado.EstadoTarea;

public class TareaSimple extends ComponenteTarea {
    private EstadoTarea estado;

    public EstadoTarea getEstadoObj() { return estado; }
    public void setEstado(EstadoTarea estado) { this.estado = estado; }

    @Override
    public String getEstadoActual() {
        return estado != null ? estado.getNombre() : "Pendiente";
    }
}

# Diagrama de Secuencia - Cambio de Estado y Deshacer (Undo)

Este diagrama ilustra el flujo de ejecución completo al cambiar el estado de una tarea simple (integrando **Command** y **State**) y posteriormente revertir dicha acción mediante **Deshacer (Undo)**.

```mermaid
sequenceDiagram
    autonumber
    actor Usuario
    participant UI as VentanaPrincipal / MenuConsola
    participant Service as GestorTareasService
    participant Historial as GestorHistorial
    participant Cmd as CambiarEstadoCmd
    participant Tarea as TareaSimple
    participant EstadoActual as EstadoPendiente

    Note over Usuario, EstadoActual: Flujo 1: Cambio de estado de Pendiente a En curso
    Usuario->>UI: Selecciona tarea e indica acción "iniciar"
    UI->>Service: cambiarEstado(idTarea, "iniciar")
    Service->>Service: buscarPorId(idTarea)
    Service->>Cmd: new CambiarEstadoCmd(tarea, transicion)
    activate Cmd
    Cmd->>Tarea: getEstadoObj()
    Tarea-->>Cmd: estadoAnterior (EstadoPendiente)
    deactivate Cmd

    Service->>Historial: ejecutarComando(cmd)
    activate Historial
    Historial->>Cmd: ejecutar()
    activate Cmd
    Cmd->>EstadoActual: iniciar(tarea)
    activate EstadoActual
    EstadoActual->>Tarea: setEstado(new EstadoEnCurso())
    deactivate EstadoActual
    deactivate Cmd
    Historial->>Historial: pilaUndo.push(cmd)
    Historial->>Historial: pilaRedo.clear()
    deactivate Historial
    UI-->>Usuario: Actualiza vista (Estado: "En curso")

    Note over Usuario, EstadoActual: Flujo 2: Reversión de la acción (Undo)
    Usuario->>UI: Presiona "Deshacer (Undo)"
    UI->>Service: deshacer()
    Service->>Historial: deshacer()
    activate Historial
    Historial->>Historial: cmd = pilaUndo.pop()
    Historial->>Cmd: deshacer()
    activate Cmd
    Cmd->>Tarea: setEstado(estadoAnterior)
    deactivate Cmd
    Historial->>Historial: pilaRedo.push(cmd)
    deactivate Historial
    UI-->>Usuario: Actualiza vista (Estado restaurado: "Pendiente")
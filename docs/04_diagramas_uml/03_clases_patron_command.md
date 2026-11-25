# Diagrama de Clases - Patrón Command

Este diagrama representa la arquitectura de historial reversible (**Undo / Redo**) implementada mediante el patrón de comportamiento **Command**, indicando los roles sobre las clases reales del sistema **LAD**.

```mermaid
classDiagram
    class GestorTareasService {
        <<Client>>
        -List~ComponenteTarea~ tareas
        -GestorHistorial historial
        +agregarTarea(ComponenteTarea) void
        +eliminarTarea(String) void
        +cambiarEstado(String, String) void
        +deshacer() void
        +rehacer() void
    }

    class GestorHistorial {
        <<Invoker>>
        -Deque~Comando~ pilaUndo
        -Deque~Comando~ pilaRedo
        +ejecutarComando(Comando) void
        +deshacer() void
        +rehacer() void
        +puedeDeshacer() boolean
        +puedeRehacer() boolean
    }

    class Comando {
        <<Command>>
        +ejecutar() void
        +deshacer() void
    }

    class CrearTareaCmd {
        <<ConcreteCommand>>
        -List~ComponenteTarea~ listaTareas
        -ComponenteTarea tarea
        +ejecutar() void
        +deshacer() void
    }

    class EliminarTareaCmd {
        <<ConcreteCommand>>
        -List~ComponenteTarea~ listaTareas
        -ComponenteTarea tarea
        -int indiceOriginal
        +ejecutar() void
        +deshacer() void
    }

    class CambiarEstadoCmd {
        <<ConcreteCommand>>
        -TareaSimple tarea
        -EstadoTarea estadoAnterior
        -Runnable transicion
        +ejecutar() void
        +deshacer() void
    }

    class ComponenteTarea {
        <<Receiver>>
    }

    class TareaSimple {
        <<Receiver>>
    }

    GestorTareasService --> GestorHistorial
    GestorTareasService ..> CrearTareaCmd : instancia
    GestorTareasService ..> EliminarTareaCmd : instancia
    GestorTareasService ..> CambiarEstadoCmd : instancia
    GestorHistorial o-- "0..*" Comando : pilas Undo / Redo
    Comando <|.. CrearTareaCmd
    Comando <|.. EliminarTareaCmd
    Comando <|.. CambiarEstadoCmd
    CrearTareaCmd --> ComponenteTarea
    EliminarTareaCmd --> ComponenteTarea
    CambiarEstadoCmd --> TareaSimple
    ComponenteTarea <|-- TareaSimple
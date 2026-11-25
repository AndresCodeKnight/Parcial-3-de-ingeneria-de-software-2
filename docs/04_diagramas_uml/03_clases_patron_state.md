# Diagrama de Clases - Patrón State

Este diagrama ilustra la implementación del patrón de diseño de comportamiento **State**, señalando los roles correspondientes sobre las clases reales del sistema **LAD**.

```mermaid
classDiagram
    class TareaSimple {
        <<Context>>
        -EstadoTarea estado
        +getEstadoObj() EstadoTarea
        +setEstado(EstadoTarea) void
        +getEstadoActual() String
    }

    class EstadoTarea {
        <<State>>
        +getNombre() String
        +iniciar(TareaSimple) void
        +enviarARevision(TareaSimple) void
        +finalizar(TareaSimple) void
        +devolverAPendiente(TareaSimple) void
    }

    class EstadoPendiente {
        <<ConcreteState>>
        +getNombre() String
        +iniciar(TareaSimple) void
        +enviarARevision(TareaSimple) void
        +finalizar(TareaSimple) void
        +devolverAPendiente(TareaSimple) void
    }

    class EstadoEnCurso {
        <<ConcreteState>>
        +getNombre() String
        +iniciar(TareaSimple) void
        +enviarARevision(TareaSimple) void
        +finalizar(TareaSimple) void
        +devolverAPendiente(TareaSimple) void
    }

    class EstadoEnRevision {
        <<ConcreteState>>
        +getNombre() String
        +iniciar(TareaSimple) void
        +enviarARevision(TareaSimple) void
        +finalizar(TareaSimple) void
        +devolverAPendiente(TareaSimple) void
    }

    class EstadoTerminada {
        <<ConcreteState>>
        +getNombre() String
        +iniciar(TareaSimple) void
        +enviarARevision(TareaSimple) void
        +finalizar(TareaSimple) void
        +devolverAPendiente(TareaSimple) void
    }

    TareaSimple o-- "1" EstadoTarea : estado
    EstadoTarea <|.. EstadoPendiente
    EstadoTarea <|.. EstadoEnCurso
    EstadoTarea <|.. EstadoEnRevision
    EstadoTarea <|.. EstadoTerminada
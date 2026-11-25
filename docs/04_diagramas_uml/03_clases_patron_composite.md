# Diagrama de Clases - Patrón Composite

```mermaid
classDiagram
    class ComponenteTarea {
        <<Component>>
        #String id
        #String titulo
        #Prioridad prioridad
        #LocalDate fechaVencimiento
        #String responsable
        -TareaCompuesta padre
        +getId() String
        +getTitulo() String
        +getPrioridad() Prioridad
        +getFechaVencimiento() LocalDate
        +getResponsable() String
        +getEstadoActual()* String
        ~getPadre() TareaCompuesta
        ~setPadre(TareaCompuesta) void
    }

    class TareaSimple {
        <<Leaf>>
        -EstadoTarea estado
        +getEstadoObj() EstadoTarea
        +setEstado(EstadoTarea) void
        +getEstadoActual() String
    }

    class TareaCompuesta {
        <<Composite>>
        -List~ComponenteTarea~ subtareas
        +agregarSubtarea(ComponenteTarea) void
        +eliminarSubtarea(String) boolean
        +getSubtareas() List~ComponenteTarea~
        +getProgreso() double
        +getEstadoActual() String
        -contarTareasSimples() int
        -contarTareasTerminadas() int
        -recopilarEstados(Set~String~) void
    }

    class Prioridad {
        <<enumeration>>
        BAJA
        MEDIA
        ALTA
    }

    ComponenteTarea --> Prioridad
    ComponenteTarea <|-- TareaSimple
    ComponenteTarea <|-- TareaCompuesta
    TareaCompuesta o-- "0..*" ComponenteTarea : subtareas

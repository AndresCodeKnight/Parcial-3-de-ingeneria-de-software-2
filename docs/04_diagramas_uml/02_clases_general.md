# Diagrama de Clases General

```mermaid
classDiagram
    class Prioridad {
        <<enumeration>>
        BAJA
        MEDIA
        ALTA
    }

    class MenuConsola {
        -GestorTareasService servicio
        -Scanner scanner
        +iniciar() void
    }

    class VentanaPrincipal {
        -GestorTareasService servicio
        -DefaultTableModel modeloTabla
        -JTable tablaTareas
        -refrescarTabla(boolean) void
    }

    class DialogoTarea {
        -JTextField txtTitulo
        -JComboBox~Prioridad~ cmbPrioridad
        -JTextField txtFecha
        -JTextField txtResponsable
        -ComponenteTarea tareaCreada
        +getTareaCreada() ComponenteTarea
    }

    class GestorTareasService {
        -List~ComponenteTarea~ tareas
        -GestorHistorial historial
        +agregarTarea(ComponenteTarea) void
        +eliminarTarea(String) void
        +cambiarEstado(String, String) void
        +deshacer() void
        +rehacer() void
        +listarTareas() List~ComponenteTarea~
        +consultarTareas(EstrategiaFiltro, EstrategiaOrdenamiento, boolean) List~ComponenteTarea~
        +buscarPorId(String) ComponenteTarea
    }

    class ComponenteTarea {
        <<abstract>>
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
    }

    class TareaSimple {
        -EstadoTarea estado
        +getEstadoObj() EstadoTarea
        +setEstado(EstadoTarea) void
        +getEstadoActual() String
    }

    class TareaCompuesta {
        -List~ComponenteTarea~ subtareas
        +agregarSubtarea(ComponenteTarea) void
        +eliminarSubtarea(String) boolean
        +getSubtareas() List~ComponenteTarea~
        +getProgreso() double
        +getEstadoActual() String
    }

    class EstadoTarea {
        <<interface>>
        +getNombre() String
        +iniciar(TareaSimple) void
        +enviarARevision(TareaSimple) void
        +finalizar(TareaSimple) void
        +devolverAPendiente(TareaSimple) void
    }

    class GestorHistorial {
        -Deque~Comando~ pilaUndo
        -Deque~Comando~ pilaRedo
        +ejecutarComando(Comando) void
        +deshacer() void
        +rehacer() void
        +puedeDeshacer() boolean
        +puedeRehacer() boolean
    }

    class Comando {
        <<interface>>
        +ejecutar() void
        +deshacer() void
    }

    class EstrategiaFiltro {
        <<interface>>
        +filtrar(List~ComponenteTarea~) List~ComponenteTarea~
    }

    class EstrategiaOrdenamiento {
        <<interface>>
        +ordenar(List~ComponenteTarea~, boolean) List~ComponenteTarea~
    }

    MenuConsola --> GestorTareasService
    VentanaPrincipal --> GestorTareasService
    VentanaPrincipal ..> DialogoTarea
    GestorTareasService o-- ComponenteTarea
    GestorTareasService *-- GestorHistorial
    GestorTareasService ..> EstrategiaFiltro
    GestorTareasService ..> EstrategiaOrdenamiento
    ComponenteTarea --> Prioridad
    ComponenteTarea <|-- TareaSimple
    ComponenteTarea <|-- TareaCompuesta
    TareaCompuesta o-- ComponenteTarea
    TareaSimple --> EstadoTarea
    GestorHistorial o-- Comando
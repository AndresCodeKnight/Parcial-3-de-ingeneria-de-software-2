# Diagrama de Clases - Patrón Strategy

Este diagrama muestra el diseño de consultas dinámicas (filtrado y ordenamiento intercambiable) implementado mediante el patrón de comportamiento **Strategy**, señalando los roles sobre las clases reales del sistema **LAD**.

```mermaid
classDiagram
    class GestorTareasService {
        <<Context>>
        -List~ComponenteTarea~ tareas
        +consultarTareas(EstrategiaFiltro, EstrategiaOrdenamiento, boolean) List~ComponenteTarea~
    }

    class EstrategiaFiltro {
        <<Strategy - Filtro>>
        +filtrar(List~ComponenteTarea~) List~ComponenteTarea~
    }

    class FiltroPorEstado {
        <<ConcreteStrategy>>
        -String estadoBuscado
        +filtrar(List~ComponenteTarea~) List~ComponenteTarea~
    }

    class FiltroPorPrioridad {
        <<ConcreteStrategy>>
        -Prioridad prioridadBuscada
        +filtrar(List~ComponenteTarea~) List~ComponenteTarea~
    }

    class FiltroPorResponsable {
        <<ConcreteStrategy>>
        -String responsable
        +filtrar(List~ComponenteTarea~) List~ComponenteTarea~
    }

    class EstrategiaOrdenamiento {
        <<Strategy - Ordenamiento>>
        +ordenar(List~ComponenteTarea~, boolean) List~ComponenteTarea~
    }

    class OrdenPorFecha {
        <<ConcreteStrategy>>
        +ordenar(List~ComponenteTarea~, boolean) List~ComponenteTarea~
    }

    class OrdenPorPrioridad {
        <<ConcreteStrategy>>
        +ordenar(List~ComponenteTarea~, boolean) List~ComponenteTarea~
    }

    class OrdenPorTitulo {
        <<ConcreteStrategy>>
        +ordenar(List~ComponenteTarea~, boolean) List~ComponenteTarea~
    }

    GestorTareasService ..> EstrategiaFiltro
    GestorTareasService ..> EstrategiaOrdenamiento
    EstrategiaFiltro <|.. FiltroPorEstado
    EstrategiaFiltro <|.. FiltroPorPrioridad
    EstrategiaFiltro <|.. FiltroPorResponsable
    EstrategiaOrdenamiento <|.. OrdenPorFecha
    EstrategiaOrdenamiento <|.. OrdenPorPrioridad
    EstrategiaOrdenamiento <|.. OrdenPorTitulo
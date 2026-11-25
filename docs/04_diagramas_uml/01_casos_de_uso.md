# Diagrama de Casos de Uso - Sistema LAD

```mermaid
flowchart LR
    Usuario((Usuario))

    subgraph Sistema_LAD["Sistema de Gestión de Tareas LAD"]
        UC1([RF-01: Crear tarea simple])
        UC2([RF-02: Crear tarea compuesta y subtareas])
        UC3([RF-03: Cambiar estado de tarea])
        UC4([RF-04/05: Deshacer acción - Undo])
        UC5([RF-04/06: Rehacer acción - Redo])
        UC6([RF-07: Filtrar tareas])
        UC7([RF-08: Ordenar tareas])
        UC8([RF-09: Listar y eliminar tareas])
    end

    Usuario --> UC1
    Usuario --> UC2
    Usuario --> UC3
    Usuario --> UC4
    Usuario --> UC5
    Usuario --> UC6
    Usuario --> UC7
    Usuario --> UC8
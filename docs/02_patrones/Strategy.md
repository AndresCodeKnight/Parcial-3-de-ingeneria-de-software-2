## 1. Problema de diseño

El gestor de tareas debe permitir filtrar y ordenar las tareas mediante diferentes criterios dinámicos (estado, prioridad, responsable, fecha de vencimiento o título) tanto en la interfaz de consola como en la interfaz gráfica.

Si toda esta lógica se implementa dentro de `GestorTareasService` mediante múltiples condicionales (`if/else` o `switch`), la clase crecería descontroladamente y agregar un nuevo criterio implicaría modificar el núcleo del sistema.

## 2. Explicación del patrón

El patrón **Strategy (Estrategia)** es un patrón de diseño de comportamiento que define una familia de algoritmos intercambiables, encapsula cada uno en una clase independiente y permite seleccionarlos en tiempo de ejecución sin acoplar el contexto a ninguna implementación concreta.

## 3. Aplicación en el proyecto

Se implementan dos familias de estrategias intercambiables en el paquete `estrategia`:

- **EstrategiaFiltro:** interfaz que define `filtrar(List<ComponenteTarea> tareas)`, implementada por:
    - **FiltroPorEstado:** filtra las tareas según su estado actual (`Pendiente`, `En curso`, `En revisión`, `Terminada`).
    - **FiltroPorPrioridad:** filtra las tareas según su nivel de prioridad (`ALTA`, `MEDIA`, `BAJA`).
    - **FiltroPorResponsable:** filtra las tareas asignadas a un responsable específico.
- **EstrategiaOrdenamiento:** interfaz que define `ordenar(List<ComponenteTarea> tareas, boolean ascendente)`, implementada por:
    - **OrdenPorFecha:** ordena por fecha de vencimiento (colocando al final las tareas sin fecha).
    - **OrdenPorPrioridad:** ordena según el nivel de prioridad.
    - **OrdenPorTitulo:** ordena alfabéticamente ignorando mayúsculas y minúsculas.
- **GestorTareasService:** actúa como contexto y aplica dinámicamente las estrategias recibidas en el método `consultarTareas(...)`.

## 4. Correspondencia con los elementos del patrón

| Elemento de Strategy | Aplicación en el proyecto |
|---|---|
| Strategy | `EstrategiaFiltro` y `EstrategiaOrdenamiento` |
| ConcreteStrategy (Filtros) | `FiltroPorEstado`, `FiltroPorPrioridad`, `FiltroPorResponsable` |
| ConcreteStrategy (Ordenamiento) | `OrdenPorFecha`, `OrdenPorPrioridad`, `OrdenPorTitulo` |
| Context | `GestorTareasService` |

## 5. Alternativas consideradas

Una alternativa descartada fue implementar todos los criterios de búsqueda y ordenamiento directamente en `GestorTareasService` mediante condicionales anidados. Esa solución concentraría múltiples responsabilidades en el servicio y obligaría a modificarlo cada vez que surgiera un nuevo filtro. Se eligió **Strategy** para aislar cada regla en una clase cohesiva y reutilizable desde ambas interfaces.

## 6. Ventajas y desventajas

### Ventajas
- Permite incorporar nuevos criterios de filtrado u ordenamiento sin tocar `GestorTareasService`.
- Elimina condicionales complejos en el servicio y en las vistas.
- Permite probar cada algoritmo de forma aislada mediante pruebas unitarias.

### Desventajas
- Aumenta la cantidad de clases e interfaces en el proyecto.
- El cliente o la interfaz debe instanciar la estrategia adecuada según la selección del usuario.

## 7. Relación con los principios SOLID

- **Principio de responsabilidad única (SRP):** cada estrategia implementa un único criterio de filtrado u ordenamiento.
- **Principio abierto/cerrado (OCP):** el sistema está abierto a nuevas estrategias de consulta sin modificar el código existente.
- **Principio de inversión de dependencias (DIP):** `GestorTareasService` depende exclusivamente de las interfaces `EstrategiaFiltro` y `EstrategiaOrdenamiento`.
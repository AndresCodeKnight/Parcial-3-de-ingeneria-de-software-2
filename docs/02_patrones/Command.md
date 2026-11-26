## 1. Problema de diseño

El gestor de tareas debe permitir que el usuario deshaga la última acción realizada y, posteriormente, pueda rehacerla.

Entre las acciones que requieren esta funcionalidad se encuentran:

- Crear una tarea.
- Modificar información de una tarea.
- Cambiar el estado de una tarea.
- Eliminar una tarea.

Si estas operaciones se ejecutan directamente desde la interfaz o desde la lógica principal del sistema, implementar su reversión resulta complejo, ya que sería necesario conservar información sobre las acciones anteriores y sus valores originales para restaurarlas correctamente.

**Consecuencias de un diseño inadecuado:**

- Acoplamiento entre la interfaz y las operaciones del sistema.
- Dificultad para recuperar el estado anterior de una tarea.
- Duplicación de lógica para deshacer y rehacer acciones.
- Mayor complejidad al incorporar nuevas operaciones reversibles.

## 2. Explicación del patrón

El patrón **Command** es un patrón de diseño de comportamiento que permite encapsular una solicitud como un objeto independiente. Cada comando representa una operación específica y contiene la información necesaria para ejecutarla (`ejecutar()`) y revertirla (`deshacer()`).

El sistema conserva un historial de comandos ejecutados mediante dos pilas, permitiendo implementar las funcionalidades de deshacer (Undo) y rehacer (Redo).

## 3. Aplicación en el proyecto

Se implementan las siguientes clases en los paquetes `comando` y `servicio`:

- **Comando:** interfaz que define las operaciones `ejecutar()` y `deshacer()`.
- **CrearTareaCmd:** encapsula la creación de una tarea y permite revertirla eliminándola de la lista.
- **EditarTareaCmd:** representa la modificación de los datos de una tarea y conserva sus valores anteriores para restaurarlos.
- **CambiarEstadoCmd:** encapsula el cambio de estado de una tarea simple y conserva el estado previo para revertirlo.
- **EliminarTareaCmd:** representa la eliminación de una tarea y conserva su índice original para restaurarla en la misma posición.
- **GestorHistorial:** administra las pilas `pilaUndo` y `pilaRedo` de comandos ejecutados y deshechos.
- **GestorTareasService:** actúa como coordinador que construye los comandos solicitados por la interfaz y delega su ejecución al historial.

## 4. Correspondencia con los elementos del patrón

| Elemento de Command | Aplicación en el proyecto |
|---|---|
| Command | `Comando` |
| ConcreteCommand | `CrearTareaCmd`, `EditarTareaCmd`, `CambiarEstadoCmd`, `EliminarTareaCmd` |
| Invoker / Command History | `GestorHistorial` y `GestorTareasService` |
| Receiver | `ComponenteTarea`, `TareaSimple` y la colección de tareas en `GestorTareasService` |
| Client | `GestorTareasService` (instancia los comandos ante peticiones de `MenuConsola` o `VentanaPrincipal`) |

## 5. Funcionamiento de deshacer y rehacer

1. El usuario solicita una operación desde la consola o Swing.
2. `GestorTareasService` crea el comando correspondiente con los datos necesarios.
3. `GestorHistorial` invoca `ejecutar()`, almacena el comando en `pilaUndo` y limpia `pilaRedo`.
4. Si el usuario selecciona **Deshacer**, `GestorHistorial` extrae el último comando de `pilaUndo`, invoca `deshacer()` y lo mueve a `pilaRedo`.
5. Si el usuario selecciona **Rehacer**, recupera el comando de `pilaRedo`, vuelve a ejecutar `ejecutar()` y lo retorna a `pilaUndo`.

## 6. Alternativas consideradas

Una alternativa descartada fue guardar copias completas (snapshots) de toda la lista de tareas antes de cada operación. Esto generaría un consumo innecesario de memoria al clonar estructuras jerárquicas de tareas compuestas cada vez que se cambia un atributo mínimo. Se seleccionó **Command** porque almacena únicamente el delta o estado previo puntual de la operación realizada.

## 7. Ventajas y desventajas

### Ventajas
- Permite implementar deshacer y rehacer de forma limpia y centralizada.
- Desacopla las interfaces (Consola y Swing) de la ejecución interna de las operaciones.
- Facilita agregar nuevos comandos reversibles sin alterar `GestorHistorial`.

### Desventajas
- Incrementa el número de clases en el paquete `comando`.
- Cada comando concreto debe capturar cuidadosamente el estado previo antes de mutar el objeto.

## 8. Relación con los principios SOLID

- **Principio de responsabilidad única (SRP):** cada clase de comando encapsula una única operación y su lógica inversa.
- **Principio abierto/cerrado (OCP):** se pueden añadir nuevos comandos implementando `Comando` sin modificar `GestorHistorial`.
- **Principio de inversión de dependencias (DIP):** `GestorHistorial` depende únicamente de la abstracción `Comando` y no de las implementaciones concretas.
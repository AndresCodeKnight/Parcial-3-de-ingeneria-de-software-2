## 1. Problema de diseño

El gestor de tareas debe permitir que el usuario deshaga la última acción realizada y, posteriormente, pueda rehacerla.

Entre las acciones que pueden requerir esta funcionalidad se encuentran:

- Crear una tarea.
- Modificar información de una tarea.
- Cambiar el estado de una tarea.
- Cambiar la prioridad de una tarea.
- Eliminar una tarea.

Si estas operaciones se ejecutan directamente desde la interfaz o desde la lógica principal del sistema, implementar su reversión puede resultar complejo.

Además, sería necesario conservar información sobre las acciones anteriores y sus valores originales para poder restaurarlas correctamente.

**Consecuencias de un diseño inadecuado:**

- Acoplamiento entre la interfaz y las operaciones del sistema.
- Dificultad para recuperar el estado anterior de una tarea.
- Duplicación de lógica para deshacer y rehacer acciones.
- Mayor complejidad al incorporar nuevas operaciones reversibles.

## 2. Explicación del patrón

El patrón **Command** es un patrón de diseño de comportamiento que permite encapsular una solicitud como un objeto independiente.

Cada comando representa una operación específica y contiene la información necesaria para ejecutarla.

En este proyecto, cada acción reversible se representará mediante un comando que tendrá operaciones para ejecutarse y deshacerse.

El sistema conservará un historial de comandos ejecutados, lo que permitirá implementar las funcionalidades de deshacer y rehacer.

## 3. Aplicación en el proyecto

Se propone utilizar las siguientes clases:

- **Comando:** interfaz que define las operaciones ejecutar() y deshacer().
- **ComandoCrearTarea:** encapsula la creación de una tarea y permite revertirla.
- **ComandoModificarTarea:** representa la modificación de los datos de una tarea y conserva los valores anteriores para restaurarlos.
- **ComandoCambiarEstado:** encapsula el cambio de estado y permite recuperar el estado anterior.
- **ComandoEliminarTarea:** representa la eliminación de una tarea y conserva la información necesaria para restaurarla.
- **HistorialComandos:** administra las pilas de comandos ejecutados y deshechos.
- **GestorTareas:** participa en la ejecución de las operaciones sobre las tareas.
- **Interfaz de usuario:** solicita la ejecución de comandos y permite activar las operaciones de deshacer y rehacer.

Para implementar el historial se utilizarán dos pilas:

- **Pila de deshacer:** almacena los comandos ejecutados que pueden revertirse.
- **Pila de rehacer:** almacena los comandos que fueron deshechos y pueden ejecutarse nuevamente.

Cuando se ejecuta un nuevo comando, este se agrega a la pila de deshacer y se limpia la pila de rehacer, ya que la secuencia anterior deja de representar el historial vigente.

## 4. Correspondencia con los elementos del patrón

| Elemento de Command | Aplicación en el proyecto |
|---|---|
| Command | Comando |
| ConcreteCommand | ComandoCrearTarea |
| ConcreteCommand | ComandoModificarTarea |
| ConcreteCommand | ComandoCambiarEstado |
| ConcreteCommand | ComandoEliminarTarea |
| Invoker | Interfaz de usuario |
| Receiver | GestorTareas y objetos del dominio sobre los que actúan los comandos |
| Client | Componente que construye los comandos y solicita su ejecución |
| Command History | HistorialComandos |

## 5. Funcionamiento de deshacer y rehacer

El funcionamiento general será el siguiente:

1. El usuario solicita una operación.
2. Se crea el comando correspondiente con la información necesaria.
3. El comando ejecuta la operación.
4. El comando se almacena en la pila de deshacer.
5. Si el usuario selecciona Deshacer, se extrae el último comando ejecutado y se invoca su operación deshacer().
6. El comando pasa a la pila de rehacer.
7. Si el usuario selecciona Rehacer, se recupera el comando correspondiente y se ejecuta nuevamente.

Cuando se ejecuta una nueva operación después de haber deshecho otras, se limpia la pila de rehacer.

## 6. Alternativas consideradas

Una alternativa sería almacenar copias completas del sistema después de cada operación para recuperar estados anteriores.

Sin embargo, esto puede generar un consumo innecesario de memoria y requiere administrar las copias del estado completo de las tareas.

Se selecciona Command porque permite encapsular cada operación de manera independiente y conservar únicamente la información necesaria para revertirla y repetirla.

## 7. Ventajas y desventajas

### Ventajas

- Permite implementar deshacer y rehacer.
- Desacopla la solicitud de una operación de su ejecución.
- Encapsula cada acción en un objeto independiente.
- Facilita incorporar nuevas operaciones reversibles.
- Permite mantener un historial organizado de acciones.

### Desventajas

- Incrementa la cantidad de clases.
- Cada comando debe conservar correctamente la información necesaria para revertir su operación.
- Es necesario administrar cuidadosamente las pilas de deshacer y rehacer.
- Las operaciones complejas, especialmente las que afectan tareas compuestas, requieren una estrategia de reversión bien definida.

## 8. Relación con los principios SOLID

- **Principio de responsabilidad única (SRP):** cada comando representa una operación específica.
- **Principio abierto/cerrado (OCP):** permite incorporar nuevos tipos de comandos sin modificar la estructura general del historial.
- **Principio de inversión de dependencias (DIP):** el historial trabaja con la abstracción Comando y no necesita depender de cada comando concreto.

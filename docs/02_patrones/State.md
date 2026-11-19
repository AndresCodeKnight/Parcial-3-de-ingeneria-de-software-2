## 1. Problema de diseño

El gestor de tareas debe manejar cuatro estados: Pendiente, En curso, En revisión y Terminada.

Cada tarea puede cambiar de estado durante su ciclo de vida, pero no todas las transiciones deben estar permitidas. Por ejemplo, una tarea Pendiente puede pasar a En curso, pero no necesariamente directamente a Terminada.

Si las reglas de transición se implementan mediante múltiples condicionales dentro de una única clase, la lógica puede crecer y resultar difícil de mantener cuando se modifiquen los comportamientos de los estados.

**Consecuencias de un diseño inadecuado:**

- Acumulación de condicionales.
- Dificultad para controlar las transiciones permitidas.
- Mayor acoplamiento entre la tarea y sus reglas de comportamiento.
- Complejidad para incorporar nuevos estados.

## 2. Explicación del patrón

El patrón **State** es un patrón de diseño de comportamiento que permite modificar el comportamiento de un objeto dependiendo de su estado interno.

En lugar de concentrar todas las reglas en una sola clase, cada estado se representa mediante una implementación independiente de una abstracción común.

De esta manera, el objeto delega las operaciones relacionadas con su estado actual y puede cambiar su comportamiento cuando cambia de estado.

## 3. Aplicación en el proyecto

Se propone utilizar las siguientes clases:

- **EstadoTarea:** interfaz que define las operaciones relacionadas con el comportamiento y las transiciones de una tarea.
- **EstadoPendiente:** representa una tarea que todavía no ha comenzado.
- **EstadoEnCurso:** representa una tarea que se encuentra en desarrollo.
- **EstadoEnRevision:** representa una tarea cuyo avance está siendo revisado.
- **EstadoTerminada:** representa una tarea que ha finalizado.
- **Tarea:** mantiene una referencia al estado actual y delega en él las operaciones correspondientes.

Cada implementación de EstadoTarea establecerá las transiciones permitidas desde su estado actual.

Las transiciones propuestas inicialmente son:

| Estado actual | Transición permitida |
|---|---|
| Pendiente | En curso |
| En curso | En revisión |
| En revisión | En curso o Terminada |
| Terminada | Ninguna |

Una transición no permitida deberá ser rechazada por el sistema, conservando el estado actual de la tarea.

## 4. Correspondencia con los elementos del patrón

| Elemento de State | Aplicación en el proyecto |
|---|---|
| State | EstadoTarea |
| ConcreteState | EstadoPendiente |
| ConcreteState | EstadoEnCurso |
| ConcreteState | EstadoEnRevision |
| ConcreteState | EstadoTerminada |
| Context | Tarea |

## 5. Alternativas consideradas

Una alternativa sería utilizar un atributo de tipo enumerado y controlar todas las transiciones mediante estructuras condicionales dentro de la clase Tarea.

Aunque esta solución puede funcionar para un sistema pequeño, concentra las reglas de comportamiento en una misma clase.

Se selecciona State porque permite separar las reglas de cada estado y facilita el mantenimiento y la posible incorporación de nuevos comportamientos.

## 6. Ventajas y desventajas

### Ventajas

- Organiza las reglas de transición de manera independiente.
- Reduce la cantidad de condicionales en la clase principal.
- Facilita la incorporación de nuevos estados.
- Permite controlar las transiciones permitidas.
- Favorece las pruebas individuales de cada comportamiento.

### Desventajas

- Incrementa la cantidad de clases.
- Requiere administrar correctamente el cambio entre estados.
- Puede resultar excesivo cuando el objeto tiene muy pocos estados y comportamientos.

## 7. Relación con los principios SOLID

- **Principio de responsabilidad única (SRP):** cada clase de estado administra las reglas correspondientes a un estado específico.
- **Principio abierto/cerrado (OCP):** permite incorporar nuevos estados sin concentrar todas las modificaciones en la clase Tarea.
- **Principio de inversión de dependencias (DIP):** la tarea depende de la abstracción EstadoTarea y no directamente de cada implementación concreta.

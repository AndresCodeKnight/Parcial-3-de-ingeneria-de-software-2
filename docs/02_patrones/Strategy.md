## 1. Problema de diseño

El gestor de tareas debe permitir ordenar las tareas mediante diferentes criterios, como fecha de vencimiento, prioridad, estado o nombre.

Si toda esta lógica se implementa dentro de una única clase mediante múltiples condicionales, el código puede crecer y volverse difícil de mantener. Además, agregar nuevos criterios de ordenamiento implicaría modificar continuamente la misma clase.

Por esta razón, se necesita una solución que permita intercambiar los criterios de ordenamiento sin modificar la lógica principal del gestor.

## 2. Explicación del patrón

El patrón **Strategy (Estrategia)** es un patrón de diseño de comportamiento que permite definir diferentes algoritmos para realizar una misma operación y utilizarlos de manera intercambiable.

En este proyecto, cada estrategia representa una forma distinta de ordenar las tareas. El gestor puede utilizar cualquiera de ellas sin necesitar conocer los detalles internos de su implementación.

## 3. Aplicación en el proyecto

Se propone utilizar las siguientes clases:

- **EstrategiaOrdenamiento:** interfaz que define la operación común para ordenar una colección de tareas.
- **OrdenarPorFecha:** implementación que organiza las tareas según su fecha de vencimiento.
- **OrdenarPorPrioridad:** implementación que organiza las tareas según su nivel de prioridad.
- **OrdenarPorEstado:** implementación que organiza las tareas según su estado.
- **OrdenarPorNombre:** implementación que organiza las tareas alfabéticamente.
- **GestorTareas:** clase que utiliza una estrategia para obtener las tareas ordenadas.

La estrategia podrá seleccionarse según el criterio solicitado por el usuario.

## 4. Correspondencia con los elementos del patrón

| Elemento de Strategy | Aplicación en el proyecto |
|---|---|
| Strategy | EstrategiaOrdenamiento |
| ConcreteStrategy | OrdenarPorFecha |
| ConcreteStrategy | OrdenarPorPrioridad |
| ConcreteStrategy | OrdenarPorEstado |
| ConcreteStrategy | OrdenarPorNombre |
| Context | GestorTareas |

## 5. Alternativas consideradas

Una alternativa sería implementar todos los criterios de ordenamiento directamente en la clase GestorTareas mediante condicionales.

Sin embargo, esta alternativa concentraría varias responsabilidades en una sola clase y haría más difícil incorporar nuevos criterios.

Se selecciona Strategy porque permite separar cada algoritmo, facilita su reutilización y reduce el acoplamiento entre el gestor y las operaciones de ordenamiento.

## 6. Ventajas y desventajas

### Ventajas
- Permite agregar nuevos criterios de ordenamiento sin modificar la lógica principal.
- Reduce la cantidad de condicionales.
- Facilita las pruebas individuales de cada estrategia.
- Favorece la reutilización y el mantenimiento del código.

### Desventajas
- Aumenta la cantidad de clases e interfaces.
- Requiere seleccionar una estrategia antes de ejecutar el ordenamiento.
- Puede resultar innecesario si el sistema solamente tuviera un criterio de ordenamiento.

## 7. Relación con los principios SOLID

- **Principio de responsabilidad única (SRP):** cada estrategia se encarga de un único criterio de ordenamiento.
- **Principio abierto/cerrado (OCP):** es posible incorporar nuevos criterios sin modificar las estrategias existentes ni la lógica principal del gestor.
- **Principio de inversión de dependencias (DIP):** el gestor utiliza la abstracción EstrategiaOrdenamiento en lugar de depender directamente de implementaciones concretas.

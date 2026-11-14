## 1. Problema de diseño

En el sistema LAD se manejarán diferentes tipos de tareas, principalmente tareas simples y tareas compuestas por subtareas.

Si la creación de estos objetos se realiza directamente desde la clase principal del gestor, mediante múltiples estructuras condicionales, se produciría un acoplamiento entre la lógica de administración y las clases concretas de las tareas.

Por ejemplo, si posteriormente se necesita incorporar otro tipo de tarea, sería necesario modificar la lógica central de creación, aumentando la complejidad y dificultando el mantenimiento.

## 2. Descripción del patrón

Factory Method es un patrón de diseño creacional que permite delegar la creación de objetos en métodos especializados, evitando que la clase principal dependa directamente de todas las implementaciones concretas.

En LAD, este patrón permitirá centralizar el proceso de creación de tareas simples y compuestas, manteniendo separada la lógica de construcción de la lógica de administración.

## 3. Implementación en LAD

Se propone implementar las siguientes clases:

- ComponenteTarea: interfaz común para los diferentes tipos de tareas.
- TareaSimple: representa una tarea individual.
- TareaCompuesta: representa una tarea que contiene subtareas.
- CreadorTarea: clase abstracta que define el método de creación.
- CreadorTareaSimple: implementa la creación de tareas simples.
- CreadorTareaCompuesta: implementa la creación de tareas compuestas.

El usuario solicitará el tipo de tarea que desea crear y el creador correspondiente se encargará de construir el objeto.

La creación de las tareas se realizará de manera independiente a las operaciones posteriores de modificación, consulta y eliminación.

## 4. Correspondencia con los roles del patrón

| Rol del patrón Factory Method | Clase en LAD |
|---|---|
| Product | ComponenteTarea |
| ConcreteProduct | TareaSimple |
| ConcreteProduct | TareaCompuesta |
| Creator | CreadorTarea |
| ConcreteCreator | CreadorTareaSimple |
| ConcreteCreator | CreadorTareaCompuesta |

La interfaz ComponenteTarea define el tipo de objeto que se construirá, mientras que los creadores concretos implementan el método encargado de producir cada variante.

## 5. Alternativas consideradas

Una alternativa sería utilizar directamente el constructor de cada clase desde el gestor principal, implementando condiciones para decidir qué tipo de tarea crear.

Sin embargo, esto generaría una dependencia directa entre el gestor y las clases concretas, dificultando la incorporación de nuevas variantes.

Se selecciona Factory Method porque permite separar la responsabilidad de creación y facilita extender el sistema mediante nuevos creadores concretos.

## 6. Ventajas y desventajas

**Ventajas:**

- Separa la creación de objetos de su utilización.
- Reduce el acoplamiento entre el gestor y las clases concretas.
- Facilita incorporar nuevos tipos de tareas.
- Centraliza las reglas de construcción.

**Desventajas:**

- Incrementa la cantidad de clases del proyecto.
- Puede resultar excesivo si solamente existen dos tipos de objetos y su creación es muy sencilla.
- Requiere mantener las relaciones entre creadores y productos.

## 7. Relación con los principios SOLID

- **SRP (Responsabilidad única):** los creadores se encargan específicamente de construir los objetos.
- **OCP (Abierto/cerrado):** permite incorporar nuevas variantes mediante nuevos creadores sin modificar innecesariamente los existentes.
- **DIP (Inversión de dependencias):** el código cliente puede trabajar con la abstracción `ComponenteTarea` en lugar de depender de implementaciones concretas.

Factory Method permite que LAD mantenga un proceso de creación organizado, flexible y preparado para futuras ampliaciones.

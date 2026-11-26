## 1. Problema de diseño

El gestor de tareas debe permitir crear tareas individuales y tareas compuestas por varias subtareas.

Por ejemplo, un proyecto puede contener una tarea principal llamada "Desarrollar aplicación", que a su vez incluya subtareas como "Diseñar interfaz", "Implementar funcionalidades" y "Realizar pruebas".

Si el sistema maneja las tareas simples y compuestas mediante estructuras y operaciones completamente diferentes, será necesario implementar lógica adicional para consultar, visualizar y administrar cada tipo de tarea.

Por esta razón, se necesita una estructura que permita tratar de manera uniforme tanto las tareas individuales como las tareas que contienen otras tareas.

## 2. Explicación del patrón

El patrón Composite es un patrón de diseño estructural que permite organizar objetos en estructuras jerárquicas de tipo árbol.

Su propósito es que los objetos individuales y los grupos de objetos puedan utilizarse mediante una misma abstracción.

En este proyecto, permite representar una tarea simple como un elemento individual y una tarea compuesta como un elemento que contiene otras tareas, manteniendo una interfaz común para ambos tipos.

## 3. Aplicación en el proyecto

Se propone utilizar las siguientes clases:

- **modelo.ComponenteTarea:** interfaz común que define las operaciones básicas que pueden realizar las tareas.
- **TareaSimple:** representa una tarea individual que no contiene subtareas.
- **TareaCompuesta:** representa una tarea que puede contener otras tareas simples o compuestas.

La clase TareaCompuesta podrá almacenar una colección de objetos de tipo modelo.ComponenteTarea. De esta manera, podrá contener tanto tareas simples como otras tareas compuestas.

Esta organización permite construir jerarquías de tareas sin establecer un límite fijo de niveles.

## 4. Correspondencia con los elementos del patrón

| Elemento de Composite | Aplicación en el proyecto |
|---|---|
| Component | modelo.ComponenteTarea |
| Leaf | TareaSimple |
| Composite | TareaCompuesta |
| Children | Colección de componentes contenida en TareaCompuesta |

## 5. Alternativas consideradas

Una alternativa sería crear clases independientes para las tareas simples y compuestas, implementando operaciones diferentes para cada una.

Sin embargo, esto obligaría al gestor a distinguir constantemente entre ambos tipos de tareas y aumentaría la complejidad al recorrer estructuras jerárquicas.

Se selecciona Composite porque permite trabajar con una abstracción común y simplifica la administración de estructuras que contienen diferentes niveles de tareas.

## 6. Ventajas y desventajas

### Ventajas

- Permite representar tareas simples y compuestas mediante una misma abstracción.
- Facilita la creación de jerarquías de tareas.
- Permite agregar subtareas sin establecer una cantidad fija de niveles.
- Simplifica el recorrido y la visualización de la estructura.
- Facilita la extensión del modelo de tareas.

### Desventajas

- Aumenta la cantidad de clases y la complejidad inicial del diseño.
- Es necesario controlar que no se generen relaciones inválidas, como ciclos en la jerarquía.
- Algunas operaciones pueden tener comportamientos diferentes dependiendo de si se trata de una tarea simple o compuesta.

## 7. Relación con los principios SOLID

- **Principio de responsabilidad única (SRP):** TareaSimple representa una tarea individual, mientras que TareaCompuesta administra la composición de otras tareas.
- **Principio abierto/cerrado (OCP):** permite incorporar nuevos tipos de componentes sin modificar necesariamente la estructura principal.
- **Principio de sustitución de Liskov (LSP):** ambos tipos de tareas pueden utilizarse mediante la abstracción modelo.ComponenteTarea, respetando el comportamiento definido por ella.
- **Principio de inversión de dependencias (DIP):** TareaCompuesta trabaja con la abstracción modelo.ComponenteTarea y no depende exclusivamente de la clase TareaSimple.

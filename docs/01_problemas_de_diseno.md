El Gestor de Tareas y Proyectos presenta diferentes situaciones que requieren decisiones de diseño para evitar un sistema rígido, con responsabilidades mezcladas y difícil de ampliar.

Se identifican tres problemas principales, cada uno relacionado con un patrón de diseño que permite resolverlo.

## 1. Administración de tareas simples y compuestas

**Problema identificado:**

El sistema debe permitir manejar tareas individuales y tareas compuestas por múltiples subtareas. Un diseño ingenuo podría implementar estructuras y operaciones diferentes para cada tipo de tarea, obligando al gestor a distinguir constantemente entre ellas.

Esto dificultaría la administración de jerarquías, especialmente cuando una tarea compuesta contiene otras tareas compuestas.

**Consecuencia de un diseño inadecuado:**

- Duplicación de código.
- Mayor complejidad al recorrer y visualizar tareas.
- Dificultad para ampliar la estructura jerárquica.

**Patrón seleccionado: Composite**

Permite representar tareas simples y compuestas mediante una abstracción común, facilitando su organización en estructuras jerárquicas.

## 2. Creación de diferentes tipos de tareas

**Problema identificado:**

El sistema necesita crear distintos tipos de tareas o plantillas predefinidas (como Tarea Estándar, Tarea Urgente con prioridad alta automática o Proyecto Base con subtareas iniciales), cada una con configuraciones y valores por defecto diferentes al momento de instanciarse. Si la creación se concentra en una única clase mediante múltiples condicionales y constructores, esta clase acumularía responsabilidades y quedaría fuertemente acoplada a las implementaciones concretas.

**Consecuencia de un diseño inadecuado:**

- Acoplamiento entre el proceso de creación y los tipos concretos de tareas.
- Dificultad para incorporar nuevas variantes.
- Modificaciones frecuentes en la lógica de creación.

**Patrón seleccionado: Factory Method**

Permite delegar la creación de los objetos en creadores especializados, manteniendo una abstracción común para los productos generados.

## 3. Diferentes criterios de ordenamiento de tareas

**Problema identificado:**

El gestor debe permitir organizar las tareas mediante diferentes criterios, como prioridad, fecha de vencimiento, estado o nombre.

Si todos los algoritmos de ordenamiento se implementan directamente en una sola clase, utilizando múltiples condicionales, agregar o modificar criterios puede afectar la lógica existente.

**Consecuencia de un diseño inadecuado:**

- Crecimiento innecesario de una misma clase.
- Dificultad para incorporar nuevos criterios.
- Menor facilidad para probar y mantener cada algoritmo.

**Patrón seleccionado: Strategy.**

Permite encapsular cada criterio de ordenamiento como una estrategia independiente, de modo que el gestor pueda utilizar diferentes algoritmos mediante una misma abstracción.

## 4. Relación general entre problemas y patrones

| Problema de diseño | Patrón seleccionado | Categoría |
|---|---|---|
| Organización de tareas y subtareas | Composite | Estructural |
| Creación de tipos de tareas | Factory Method | Creacional |
| Diferentes criterios de ordenamiento | Strategy | Comportamiento |

## 5. Consideraciones finales

La selección de estos patrones busca que el sistema tenga una estructura modular, con responsabilidades diferenciadas y posibilidades de extensión.

Cada patrón responde a una necesidad concreta del gestor y no se incorpora únicamente por cumplir una cantidad determinada de patrones de diseño.

Su implementación deberá mantener la coherencia con los requisitos funcionales y no funcionales, así como con la separación entre la lógica del negocio y las interfaces de usuario.

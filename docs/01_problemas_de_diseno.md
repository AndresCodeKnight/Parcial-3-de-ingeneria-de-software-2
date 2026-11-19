El Gestor de Tareas y Proyectos (LAD) presenta cuatro situaciones clave que requieren decisiones de diseño para evitar un sistema rígido, con responsabilidades mezcladas y difícil de ampliar:

## 1. Administración de tareas simples y compuestas

**Problema identificado:**

El sistema debe manejar tareas individuales y tareas compuestas por múltiples subtareas (que a su vez pueden contener otras subtareas). Un diseño ingenuo implementaría estructuras y operaciones separadas para cada tipo, obligando al gestor y a las vistas a distinguir constantemente entre ellas mediante condicionales.
Esto dificultaría la administración de jerarquías, especialmente cuando una tarea compuesta contiene otras tareas compuestas.

**Consecuencia de un diseño inadecuado:**

- Duplicación de código.
- Mayor complejidad al recorrer o calcular el estado de jerarquías.
- Dificultad para ampliar la estructura jerárquica.

**Patrón seleccionado: Composite**

Permite tratar tanto a las tareas simples como a las compuestas bajo una abstracción común (ComponenteTarea), unificando su visualización y consulta de estado.

## 2. Creación de diferentes tipos de tareas

**Problema identificado:**

Cada tarea pasa por cuatro estados (Pendiente, En curso, En revisión y Terminada) con reglas estrictas sobre qué transiciones están permitidas. Implementar esto con un enum y un bloque gigante de switch o if-else dentro de la clase Tarea haría que la clase crezca sin control y viole el principio Abierto/Cerrado (Open/Closed).

**Consecuencia de un diseño inadecuado:**

- Lógica condicional compleja y acoplada al modelo de datos
- Alto riesgo de errores al modificar una regla de flujo
- Dificultad para agregar un nuevo estado (por ejemplo, Bloqueada) sin alterar código existente.

**Patrón seleccionado: State**

Encapsula cada estado en una clase independiente que define explícitamente qué transiciones permite y cuáles rechaza mediante excepciones controladas.

## 3. Registro y reversión de acciones del usuario (Undo / Redo)

**Problema identificado:**

El usuario necesita deshacer y rehacer operaciones (crear, editar, cambiar estado o eliminar tareas) tanto desde la consola como desde la interfaz gráfica. Si la lógica para revertir acciones se escribe dentro de los botones de Swing o en el menú de consola, se duplicaría el código y se acoplaría el historial a la presentación.

**Consecuencia de un diseño inadecuado:**

-Duplicación de lógica inversa entre consola y Swing
-Alto consumo de memoria si se clona todo el sistema en cada cambio  
-Dificultad para incorporar nuevas acciones reversibles.

**Patrón seleccionado: Command.**

Encapsula cada acción del usuario como un objeto con métodos ejecutar() y deshacer(), gestionados centralmente mediante pilas de historial (Undo y Redo) 

## 4. Diferentes criterios de filtrado y ordenamiento

**Problema identificado:**

El sistema debe filtrar y ordenar las tareas por múltiples criterios (prioridad, fecha de vencimiento, estado, responsable o título) en orden ascendente o descendente. Concentrar todos los algoritmos de consulta en el gestor llenaría la clase de métodos combinatorios difíciles de mantener.

**Consecuencia de un diseño inadecuado:**

-Crecimiento desmedido de la clase gestora
-Dificultad para agregar nuevos filtros 
-Ordenamientos en tiempo de ejecución y baja cohesión.

**Patrón seleccionado: Strategy.**

Encapsula cada criterio de filtrado y ordenamiento en estrategias intercambiables bajo una interfaz común.

## 5. Relación general entre problemas y patrones

| Problema de diseño | Patrón seleccionado | Categoría |
|---|---|---|
| Organización de tareas y subtareas | Composite | Estructural |
| Reglas de transición entre estados | State | Comportamiento |
| Historial para deshacer y rehacer (Undo/Redo) | Command | Comportamiento |
| Criterios dinámicos de filtrado y ordenamiento | Strategy | Comportamiento |

## 6. Consideraciones finales

La selección de estos patrones busca que el sistema tenga una estructura modular, con responsabilidades diferenciadas y posibilidades de extensión.

Cada patrón responde a una necesidad concreta del gestor y no se incorpora únicamente por cumplir una cantidad determinada de patrones de diseño.

Su implementación deberá mantener la coherencia con los requisitos funcionales y no funcionales, así como con la separación entre la lógica del negocio y las interfaces de usuario.

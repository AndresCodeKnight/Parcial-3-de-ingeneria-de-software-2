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

Permite tratar tanto a las tareas simples como a las compuestas bajo una abstracción común (modelo.ComponenteTarea), unificando su visualización y consulta de estado.

## 2. Control de estados y transiciones

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

La implementación de los 4 patrones permite establecer una estructura modular y organizada para el sistema, distribuyendo mejor las responsabilidades, y facilitando el mantenimiento y ampliación de las funcionalidades.

Cada patrón se incorporó para atender una necesidad específica del proyecto: Composite facilita la organización de elementos mediante estructuras jerárquicas; State permite gestionar los cambios de comportamiento según el estado del sistema; Command encapsula las acciones para ejecutarlas y administrarlas de manera independiente; y Strategy permite intercambiar distintas formas de realizar un comportamiento sin modificar la estructura principal.

En conjunto estos patrones contribuyen a reducir el acoplamiento entre los componentes, favorecer la reutilización del código y mejorar la flexibilidad del sistema. Su aplicación mantiene la coherencia con los RF y RNF del proyecto, respetando la separación entre la lógica del negocio y las interfaces de usuario.

Por tanto, su utilización responde a necesidades reales del diseño y funcionamiento, y no directamente al cumplimiento de una cantidad determinada de patrones de diseño.

**Alcance del sistema**
LAD será una aplicación desarrollada en Java que permitirá organizar y administrar tareas personales o de proyectos mediante una interfaz de consola y una interfaz gráfica sencilla.
La aplicación permitirá crear tareas simples y tareas compuestas por subtareas, gestionar sus estados mediante reglas de transición definidas, así como ordenar y filtrar las tareas según diferentes criterios.
El alcance del sistema se concentrará en:
- Gestión de tareas simples y compuestas: permitirá crear tareas individuales y tareas que contengan subtareas, facilitando la organización de actividades complejas en unidades más pequeñas.
- Control de estados de tareas: permitirá administrar los estados de las tareas (pendiente, en curso, en revisión y terminada), estableciendo reglas que determinen cuáles transiciones son válidas y evitando cambios de estado no permitidos.
- Creación de tipos y plantillas de tareas: Permitirá instanciar tareas individuales (como tarea estándar o tarea urgente con prioridad alta preconfigurada) y tareas compuestas mediante fábricas especializadas, encapsulando sus configuraciones iniciales.
- Ordenamiento y filtrado de tareas: permitirá organizar y consultar las tareas según criterios como prioridad, fecha de vencimiento, estado o responsable, facilitando su búsqueda y seguimiento.
El sistema no incluirá funcionalidades de deshacer/rehacer acciones (Undo/Redo), notificaciones externas, integración con servicios en la nube ni gestión avanzada de usuarios, dado que estas funcionalidades opcionales se dejan fuera para centrar la arquitectura en la creación configurable, la jerarquía de tareas y las estrategias dinámicas de consulta dentro del tiempo establecido para el proyecto.

**Requerimientos funcionales**
- RF-01: El sistema debe permitir al usuario crear tareas individuales especificando título, descripción opcional, fecha de vencimiento y prioridad
- RF-02: El sistema debe permitir descomponer una tarea en subtareas
- RF-03: El sistema debe soportar exactamente cuatro estados para cada tarea: Pendiente, En curso, En revisión y Terminada.
- RF-04: El sistema debe permitir crear tareas a partir de tipos o plantillas predefinidas (ej. Tarea Estándar, Tarea Urgente, Proyecto Base) aplicando automáticamente sus valores y reglas iniciales.
- RF-05: El sistema debe permitir listar, editar y eliminar tareas existentes.
- RF-06: El sistema debe permitir al usuario aplicar filtros sobre el listado de tareas.
- RF-07: El sistema debe permitir ordenar la lista de tareas en orden ascendente y descendente.
  
**Requerimientos no funcionales**
- RNF-01 (Núcleo Desacoplado): La lógica del negocio (modelo de tareas, fábricas de creación, estados y criterios de consulta) debe estar completamente desacoplada de las capas de presentación.
- RNF-02 (Aplicación de Patrones de Diseño): La arquitectura debe implementar de forma explícita tres patrones de diseño pertenecientes a distintas categorías para resolver problemas de flexibilidad y acoplamiento: Composite (Estructural) para tareas y subtareas, Factory Method (Creacional) para la creación de tipos/plantillas de tareas, y Strategy (Comportamiento) para los criterios intercambiables de filtrado y ordenamiento.
- RNF-03: Interfaz:El sistema de mostrar una interfaz gráfica sencilla (Java Swing)

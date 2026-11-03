**Alcance del sistema**
LAD será una aplicación desarrollada en Java que permitirá organizar y administrar tareas personales o de proyectos mediante una interfaz de consola y una interfaz gráfica sencilla.
La aplicación permitirá crear tareas simples y tareas compuestas por subtareas, gestionar sus estados mediante reglas de transición definidas, deshacer y rehacer acciones realizadas por el usuario; así como ordenar y filtrar las tareas según diferentes criterios.
El alcance del sistema se concentrará en:
- Gestión de tareas simples y compuestas: permitirá crear tareas individuales y tareas que contengan subtareas, facilitando la organización de actividades complejas en unidades más pequeñas.
- Control de estados de tareas: permitirá administrar los estados de las tareas (pendiente, en curso, en revisión y terminada), estableciendo reglas que determinen cuáles transiciones son válidas y evitando cambios de estado no permitidos.
- Deshacer y rehacer acciones: permitirá revertir y recuperar acciones realizadas por el usuario, como la creación, modificación o eliminación de tareas.
- Ordenamiento y filtrado de tareas: permitirá organizar y consultar las tareas según criterios como prioridad, fecha de vencimiento, estado o responsable, facilitando su búsqueda y seguimiento.
El sistema no incluirá funcionalidades de notificaciones externas, integración con servicios en la nube ni gestión avanzada de usuarios, dado que estas no forman parte del alcance seleccionado.

**Requerimientos funcionales**


**Requerimientos no funcionales**
- RNF-01: Núcleo Desacoplado (Core Independiente): La lógica del negocio (modelo de tareas, validación de estados, historial) debe estar completamente desacoplada de la capa de presentación.
- RNF-02: Aplicación de Patrones de Diseño: La arquitectura debe implementar de forma explícita al menos tres patrones de diseño (creacionales, estructurales o de comportamiento) que resuelvan problemas específicos de flexibilidad y acoplamiento (ej. patrón Composite para tareas/subtareas, State para estados y Command/Memento para deshacer/rehacer).
- RNF-03: Interfaz:El sistema de mostrar una interfaz gráfica sencilla (Java Swing)

**Integrantes del grupo:** Andrés Felipe Plazas Rodriguez,Walter Leonardo Míñope Chaves ,Daniel Alejandro Romero Murcia

**Alcance del sistema**
LAD será una aplicación desarrollada en Java que permitirá organizar y administrar tareas personales o de proyectos mediante una interfaz de consola y una interfaz gráfica sencilla.
La aplicación permitirá crear tareas simples y tareas compuestas por subtareas, gestionar sus estados mediante reglas de transición definidas, así como ordenar y filtrar las tareas según diferentes criterios.
El alcance del sistema se concentrará en:
- Gestión de tareas simples y compuestas: permitirá crear tareas individuales y tareas que contengan subtareas, facilitando la organización de actividades complejas en unidades más pequeñas.
- Control de estados de tareas: permitirá administrar los estados de las tareas (pendiente, en curso, en revisión y terminada), estableciendo reglas que determinen cuáles transiciones son válidas y evitando cambios de estado no permitidos.
- Deshacer y rehacer acciones (Undo/Redo): Permite registrar, revertir y recuperar acciones realizadas por el usuario, como la creación, modificación, cambio de estado o eliminación de tareas.
- Ordenamiento y filtrado de tareas: permitirá organizar y consultar las tareas según criterios como prioridad, fecha de vencimiento, estado o responsable, facilitando su búsqueda y seguimiento.
El sistema no incluirá notificaciones externas, integración con servicios en la nube ni gestión avanzada de usuarios, dado que estas funcionalidades opcionales se dejan fuera para mantener el enfoque arquitectónico en los patrones de diseño seleccionados dentro del tiempo establecido para el proyecto.

**Requerimientos funcionales**
- RF-01: El sistema debe permitir al usuario crear tareas individuales especificando título, descripción opcional, fecha de vencimiento, prioridad y responsable.
- RF-02: El sistema debe permitir descomponer una tarea compuesta en múltiples subtareas (simples o compuestas) y consultar su progreso general.
- RF-03: El sistema debe soportar exactamente cuatro estados para cada tarea (Pendiente, En curso, En revisión y Terminada), validando y bloqueando cualquier transición no permitida.
- RF-04: El sistema debe registrar en un historial las acciones ejecutadas por el usuario sobre las tareas.
- RF-05: El sistema debe permitir revertir la última acción ejecutada por el usuario (Undo).
- RF-06: El sistema debe permitir aplicar nuevamente una acción que fue deshecha (Redo).
- RF-07: El sistema debe permitir al usuario aplicar filtros dinámicos sobre el listado de tareas (por estado, prioridad o responsable).
- RF-08: El sistema debe permitir ordenar la lista de tareas en orden ascendente y descendente según diferentes criterios (fecha de vencimiento, prioridad, estado o título).
- RF-09: El sistema debe permitir listar, editar y eliminar tareas existentes.
  
**Requerimientos no funcionales**
- RNF-01 (Núcleo Desacoplado): La lógica del negocio (modelo de tareas, validación de estados, historial de comandos y criterios de consulta) debe estar completamente desacoplada de las capas de presentación.
- RNF-02 (Aplicación de Patrones de Diseño): La arquitectura debe implementar de forma explícita cuatro patrones de diseño pertenecientes a distintas categorías para resolver problemas reales de flexibilidad y acoplamiento: Composite (Estructural), State (Comportamiento), Command (Comportamiento) y Strategy (Comportamiento) para los criterios intercambiables de filtrado y ordenamiento.
- RNF-03 (Interfaces de Usuario): El sistema debe ofrecer una interfaz ejecutable por consola y una interfaz gráfica sencilla desarrollada en Java Swing, ambas operando sobre el mismo núcleo de negocio.
- RNF-04 (Calidad y Pruebas): Las clases del núcleo donde se implementen los patrones de diseño deben contar con pruebas unitarias automatizadas (JUnit) que validen su correcto funcionamiento.

  ## Instrucciones de compilación y ejecución

1. **Requisitos:** Java JDK 17 o superior y JUnit 5 (Jupiter) configurado en el proyecto.
2. **Ejecutar interfaz por Consola:** Ejecutar el método `main` de la clase `src/main/java/ui/consola/MainConsola.java`.
3. **Ejecutar interfaz gráfica (Java Swing):** Ejecutar el método `main` de la clase `src/main/java/ui/swing/MainSwing.java`.
4. **Ejecutar pruebas unitarias:** Hacer clic derecho sobre la carpeta `src/test/java` y seleccionar **Run 'All Tests'** (28 pruebas automatizadas).

## Declaración de uso de herramientas de apoyo

Durante el desarrollo del proyecto se emplearon herramientas de asistencia basadas en inteligencia artificial como apoyo para la revisión de estructura de pruebas unitarias en JUnit 5, verificación de consistencia entre diagramas UML/documentación y código fuente, y validación de casos borde en los patrones de diseño. Toda la arquitectura, lógica de negocio e integración fue revisada, comprendida y adaptada por los integrantes del equipo.

## Conclusiones

- La aplicación de los patrones **Composite, State, Command y Strategy** permitió construir un núcleo de dominio (`GestorTareasService`) desacoplado de las capas de presentación, haciendo posible operar exactamente la misma lógica desde Consola y Java Swing sin duplicar código.
- El uso de **Command** junto con dos pilas de historial (`Undo` y `Redo`) resolvió de forma limpia la reversión de acciones sin necesidad de clonar el estado completo del sistema en cada operación.
- La separación de responsabilidades bajo los principios **SOLID** facilita extender el sistema (por ejemplo, añadiendo nuevos criterios de filtrado u ordenamiento) sin modificar las clases centrales ya probadas.

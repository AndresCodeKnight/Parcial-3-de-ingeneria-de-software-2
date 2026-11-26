Integrante 1: Modelo Jerárquico, Estados y Documentación de Diseño
Código Java (com.lad.core.model, com.lad.core.state y com.lad.core.exception):

Implementar Composite: modelo.ComponenteTarea, TareaSimple, TareaCompuesta y modelo.Prioridad.

Implementar State: interfaz EstadoTarea y las 4 clases (Pendiente, EnCurso, EnRevision, Terminada) con sus reglas de transición.

Crear las excepciones de dominio (TransicionInvalidaException, TareaNoEncontradaException, HistorialVacioException).

Pruebas Unitarias (test/java/com/lad/core/):

Pruebas JUnit para Composite (jerarquía y cálculo de estado de tareas compuestas) y State (transiciones válidas y bloqueo de inválidas).

Documentación (docs/02_patrones/):

Completar los archivos Composite.md y State.md (roles, alternativa descartada, ventajas/desventajas y relación con SOLID).

Integrante 2: Historial (Undo/Redo), Consultas Dinámicas y Servicio Central
Código Java (com.lad.core.command, com.lad.core.strategy y com.lad.core.service):

Implementar Command: interfaz Comando, GestorHistorial (pilas Undo y Redo) y los comandos (CrearTareaCmd, CambiarEstadoCmd, EditarTareaCmd, EliminarTareaCmd).

Implementar Strategy: interfaces EstrategiaFiltro y EstrategiaOrdenamiento junto con sus clases concretas (por estado, prioridad, responsable, fecha y título).

Implementar GestorTareasService (el coordinador único que conecta todo el núcleo).

Pruebas Unitarias (test/java/com/lad/core/):

Pruebas JUnit para Command (ejecutar, deshacer y rehacer acciones) y Strategy (filtros y orden ascendente/descendente).

Documentación (docs/02_patrones/):

Completar los archivos Command.md y Strategy.md (roles, alternativa descartada, ventajas/desventajas y relación con SOLID).

Integrante 3: Interfaces (Consola + Swing), Diagramas UML y README
Código Java (com.lad.ui.console y com.lad.ui.swing):

Implementar la interfaz por Consola (MainConsola, MenuConsola) consumiendo únicamente GestorTareasService.

Implementar la interfaz gráfica en Java Swing (MainSwing, VentanaPrincipal, DialogoTarea) conectada al mismo GestorTareasService (con botones para crear, cambiar estado, filtrar/ordenar y Deshacer/Rehacer).

Diagramas UML (docs/04_diagramas_uml/):

Diagrama de Casos de Uso.

Diagrama de Clases General y un Diagrama de Clases por cada patrón (con los roles señalados sobre las clases reales).

Diagrama de Secuencia del flujo principal (por ejemplo, cambio de estado o deshacer acción).

Entrega final (README.md y conclusiones):

Redactar las instrucciones de compilación/ejecución, lista de funcionalidades, declaración de uso de herramientas de apoyo (si aplica, según reglas de integridad académica) y la sección final de conclusiones del documen

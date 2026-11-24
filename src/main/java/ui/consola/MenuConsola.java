package ui.consola;
import estrategia.*;
import modelo.*;
import servicio.GestorTareasService;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class MenuConsola {
    private final GestorTareasService servicio;
    private final Scanner scanner;

    public MenuConsola(GestorTareasService servicio){
        this.servicio = servicio;
        this.scanner = new Scanner(System.in);
    }
    public void iniciar(){
        int opcion = -1;
        while (opcion != 0){
            mostrarMenu();
            try{
                opcion = Integer.parseInt(scanner.nextLine().trim());
                ejecutarOpcion (opcion);
            }catch (NumberFormatException e){
                System.out.println("Error, Ingrese un número válido");
            }catch (IllegalArgumentException | IllegalStateException e){
                System.out.println(e.getMessage());
            }
            System.out.println("Saliendo de LAD...");
        }
    }
    private void mostrarMenu(){
        System.out.println("\n================ GESTOR DE TAREAS LAD ================");
        System.out.println("1. Crear tarea simple");
        System.out.println("2. Crear tarea compuesta");
        System.out.println("3. Agregar subtarea a tarea compuesta");
        System.out.println("4. Listar todas las tareas");
        System.out.println("5. Filtrar y ordenar tareas");
        System.out.println("6. Cambiar estado de tarea simple");
        System.out.println("7. Eliminar tarea");
        System.out.println("8. Deshacer (Undo)");
        System.out.println("9. Rehacer (Redo)");
        System.out.println("0. Salir");
        System.out.print("Seleccione una opción: ");
    }
    private void ejecutarOpcion(int opcion){
        switch (opcion){
            case 1 -> crearTareaSimple();
            case 2 -> crearTareaCompuesta();
            case 3 -> agregarSubtarea();
            case 4 -> imprimirTareas(servicio.listarTareas());
            case 5 -> consultarTareas();
            case 6 -> cambiarEstado();
            case 7 -> eliminarTarea();
            case 8 -> {
                servicio.deshacer();
                System.out.println("[OK] Acción deshecha.");
            }
            case 9 -> {
                servicio.rehacer();
                System.out.println("[OK] Acción rehecha.");
            }
            case 0 -> {}
            default -> System.out.println("[Error] Opción fuera de rango.");
        }
    }
    private String generarId(){
        return UUID.randomUUID().toString().substring(0, 6).toUpperCase();
    }
    private void crearTareaSimple() {
        System.out.print("Título: ");
        String titulo = scanner.nextLine();
        Prioridad prio = leerPrioridad();
        LocalDate fecha = leerFecha();
        System.out.print("Responsable: ");
        String resp = scanner.nextLine();

        String id = generarId();
        servicio.agregarTarea(new TareaSimple(id, titulo, prio, fecha, resp));
        System.out.println("[OK] Tarea simple creada con ID: " + id);
    }
    private void crearTareaCompuesta() {
        System.out.print("Título de tarea compuesta: ");
        String titulo = scanner.nextLine();
        Prioridad prio = leerPrioridad();
        LocalDate fecha = leerFecha();
        System.out.print("Responsable: ");
        String resp = scanner.nextLine();

        String id = generarId();
        servicio.agregarTarea(new TareaCompuesta(id, titulo, prio, fecha, resp));
        System.out.println("[OK] Tarea compuesta creada con ID: " + id);
    }
    private void agregarSubtarea() {
        System.out.print("ID de la tarea compuesta padre: ");
        String idPadre = scanner.nextLine().trim();
        ComponenteTarea padre = servicio.buscarPorId(idPadre);
        if (!(padre instanceof TareaCompuesta compuesta)) {
            throw new IllegalArgumentException("El ID indicado no corresponde a una tarea compuesta.");
        }

        System.out.print("Tipo de subtarea (1: Simple, 2: Compuesta): ");
        String tipo = scanner.nextLine().trim();
        System.out.print("Título subtarea: ");
        String titulo = scanner.nextLine();
        Prioridad prio = leerPrioridad();
        LocalDate fecha = leerFecha();
        System.out.print("Responsable: ");
        String resp = scanner.nextLine();

        String idSub = generarId();
        ComponenteTarea sub = "2".equals(tipo)
                ? new TareaCompuesta(idSub, titulo, prio, fecha, resp)
                : new TareaSimple(idSub, titulo, prio, fecha, resp);

        compuesta.agregarSubtarea(sub);
        System.out.println("[OK] Subtarea agregada con ID: " + idSub);
    }
    private void consultarTareas() {
        System.out.println("Filtro (0: Ninguno, 1: Estado, 2: Prioridad, 3: Responsable): ");
        String opFiltro = scanner.nextLine().trim();
        EstrategiaFiltro filtro = switch (opFiltro) {
            case "1" -> {
                System.out.print("Estado (Pendiente / En curso / En revisión / Terminada): ");
                yield new FiltroPorEstado(scanner.nextLine().trim());
            }
            case "2" -> new FiltroPorPrioridad(leerPrioridad());
            case "3" -> {
                System.out.print("Responsable: ");
                yield new FiltroPorResponsable(scanner.nextLine().trim());
            }
            default -> null;
        };

        System.out.println("Orden (0: Ninguno, 1: Fecha, 2: Prioridad, 3: Título, 4: Estado): ");
        String opOrden = scanner.nextLine().trim();
        EstrategiaOrdenamiento orden = switch (opOrden) {
            case "1" -> new OrdenPorFecha();
            case "2" -> new OrdenPorPrioridad();
            case "3" -> new OrdenPorTitulo();
            case "4" -> (tareas, asc) -> {
                Comparator<ComponenteTarea> comp = Comparator.comparing(ComponenteTarea::getEstadoActual);
                return tareas.stream().sorted(asc ? comp : comp.reversed()).toList();
            };
            default -> null;
        };

        boolean asc = true;
        if (orden != null) {
            System.out.print("Dirección (1: Ascendente, 2: Descendente): ");
            asc = !"2".equals(scanner.nextLine().trim());
        }

        imprimirTareas(servicio.consultarTareas(filtro, orden, asc));
    }
    private void cambiarEstado() {
        System.out.print("ID de la tarea simple: ");
        String id = scanner.nextLine().trim();
        System.out.print("Acción (iniciar / revision / finalizar / pendiente): ");
        String accion = scanner.nextLine().trim();
        servicio.cambiarEstado(id, accion);
        System.out.println("[OK] Estado actualizado.");
    }
    private void eliminarTarea() {
        System.out.print("ID de la tarea a eliminar: ");
        String id = scanner.nextLine().trim();
        servicio.eliminarTarea(id);
        System.out.println("[OK] Tarea eliminada.");
    }
    private void imprimirTareas(List<ComponenteTarea> tareas) {
        if (tareas.isEmpty()) {
            System.out.println("No hay tareas registradas.");
            return;
        }
        System.out.println("\n--- LISTADO DE TAREAS ---");
        for (ComponenteTarea t : tareas) {
            imprimirRecursivo(t, "");
        }
    }
    private void imprimirRecursivo(ComponenteTarea t, String prefijo) {
        String extra = (t instanceof TareaCompuesta tc)
                ? String.format(" [Compuesta - Progreso: %.1f%%]", tc.getProgreso())
                : " [Simple]";
        System.out.printf("%s[%s] %s | Estado: %s | Prioridad: %s | Vence: %s | Resp: %s%s%n",
                prefijo, t.getId(), t.getTitulo(), t.getEstadoActual(),
                t.getPrioridad(), t.getFechaVencimiento(), t.getResponsable(), extra);

        if (t instanceof TareaCompuesta tc) {
            for (ComponenteTarea sub : tc.getSubtareas()) {
                imprimirRecursivo(sub, prefijo + "   └─ ");
            }
        }
    }
    private Prioridad leerPrioridad() {
        System.out.print("Prioridad (BAJA / MEDIA / ALTA): ");
        try {
            return Prioridad.valueOf(scanner.nextLine().trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Prioridad no reconocida, se asigna MEDIA por defecto.");
            return Prioridad.MEDIA;
        }
    }
    private LocalDate leerFecha() {
        System.out.print("Fecha vencimiento (AAAA-MM-DD, Enter para omitir): ");
        String texto = scanner.nextLine().trim();
        if (texto.isEmpty()) return null;
        try {
            return LocalDate.parse(texto);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Formato de fecha inválido. Use AAAA-MM-DD.");
        }
    }
}

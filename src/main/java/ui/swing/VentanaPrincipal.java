package ui.swing;

import estrategia.*;
import modelo.*;
import servicio.GestorTareasService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Comparator;
import java.util.List;

public class VentanaPrincipal extends JFrame {
    private final GestorTareasService servicio;
    private final DefaultTableModel modeloTabla;
    private final JTable tablaTareas;

    private final JComboBox<String> cmbFiltroTipo = new JComboBox<>(new String[]{"Todos", "Estado", "Prioridad", "Responsable"});
    private final JTextField txtFiltroValor = new JTextField(12);
    private final JComboBox<String> cmbOrden = new JComboBox<>(new String[]{"Sin orden", "Fecha", "Prioridad", "Título", "Estado"});
    private final JCheckBox chkAscendente = new JCheckBox("Ascendente", true);

    public VentanaPrincipal(GestorTareasService servicio) {
        this.servicio = servicio;
        setTitle("LAD - Gestor de Tareas y Proyectos");
        setSize(920, 520);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(8, 8));

        // Panel Superior: Filtros y Ordenamiento (Strategy)
        JPanel panelFiltros = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelFiltros.setBorder(BorderFactory.createTitledBorder("Consulta Dinámica (Strategy)"));
        JButton btnAplicarConsulta = new JButton("Aplicar");
        JButton btnLimpiarConsulta = new JButton("Ver Todo");

        panelFiltros.add(new JLabel("Filtrar por:"));
        panelFiltros.add(cmbFiltroTipo);
        panelFiltros.add(txtFiltroValor);
        panelFiltros.add(new JLabel("Ordenar por:"));
        panelFiltros.add(cmbOrden);
        panelFiltros.add(chkAscendente);
        panelFiltros.add(btnAplicarConsulta);
        panelFiltros.add(btnLimpiarConsulta);

        // Tabla Central
        String[] columnas = {"ID", "Tipo", "Título", "Estado", "Prioridad", "Vencimiento", "Responsable", "Progreso"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tablaTareas = new JTable(modeloTabla);
        JScrollPane scrollTabla = new JScrollPane(tablaTareas);

        // Panel Inferior: Acciones (Composite, State, Command)
        JPanel panelAcciones = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 8));
        JButton btnNueva = new JButton("+ Nueva Tarea");
        JButton btnSubtarea = new JButton("+ Subtarea");
        JButton btnIniciar = new JButton("Iniciar");
        JButton btnRevision = new JButton("A Revisión");
        JButton btnFinalizar = new JButton("Finalizar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnUndo = new JButton("Deshacer (Undo)");
        JButton btnRedo = new JButton("Rehacer (Redo)");

        panelAcciones.add(btnNueva);
        panelAcciones.add(btnSubtarea);
        panelAcciones.add(btnIniciar);
        panelAcciones.add(btnRevision);
        panelAcciones.add(btnFinalizar);
        panelAcciones.add(btnEliminar);
        panelAcciones.add(btnUndo);
        panelAcciones.add(btnRedo);

        add(panelFiltros, BorderLayout.NORTH);
        add(scrollTabla, BorderLayout.CENTER);
        add(panelAcciones, BorderLayout.SOUTH);

        // Eventos
        btnNueva.addActionListener(e -> accionNuevaTarea());
        btnSubtarea.addActionListener(e -> accionAgregarSubtarea());
        btnIniciar.addActionListener(e -> accionCambiarEstado("iniciar"));
        btnRevision.addActionListener(e -> accionCambiarEstado("revision"));
        btnFinalizar.addActionListener(e -> accionCambiarEstado("finalizar"));
        btnEliminar.addActionListener(e -> accionEliminar());
        btnUndo.addActionListener(e -> ejecutarSeguro(() -> servicio.deshacer()));
        btnRedo.addActionListener(e -> ejecutarSeguro(() -> servicio.rehacer()));
        btnAplicarConsulta.addActionListener(e -> refrescarTabla(true));
        btnLimpiarConsulta.addActionListener(e -> {
            cmbFiltroTipo.setSelectedIndex(0);
            txtFiltroValor.setText("");
            cmbOrden.setSelectedIndex(0);
            refrescarTabla(false);
        });

        refrescarTabla(false);
    }

    private void accionNuevaTarea() {
        DialogoTarea dialogo = new DialogoTarea(this, true);
        dialogo.setVisible(true);
        if (dialogo.getTareaCreada() != null) {
            ejecutarSeguro(() -> servicio.agregarTarea(dialogo.getTareaCreada()));
        }
    }

    private void accionAgregarSubtarea() {
        String idSel = obtenerIdSeleccionado();
        if (idSel == null) return;
        ejecutarSeguro(() -> {
            ComponenteTarea padre = servicio.buscarPorId(idSel);
            if (!(padre instanceof TareaCompuesta compuesta)) {
                throw new IllegalArgumentException("Seleccione una tarea compuesta para agregar subtareas.");
            }
            DialogoTarea dialogo = new DialogoTarea(this, true);
            dialogo.setVisible(true);
            if (dialogo.getTareaCreada() != null) {
                compuesta.agregarSubtarea(dialogo.getTareaCreada());
            }
        });
    }

    private void accionCambiarEstado(String accion) {
        String idSel = obtenerIdSeleccionado();
        if (idSel == null) return;
        ejecutarSeguro(() -> servicio.cambiarEstado(idSel, accion));
    }

    private void accionEliminar() {
        String idSel = obtenerIdSeleccionado();
        if (idSel == null) return;
        ejecutarSeguro(() -> servicio.eliminarTarea(idSel));
    }

    private String obtenerIdSeleccionado() {
        int fila = tablaTareas.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una tarea de la tabla.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return null;
        }
        return modeloTabla.getValueAt(fila, 0).toString();
    }

    private void ejecutarSeguro(Runnable r) {
        try {
            r.run();
            refrescarTabla(false);
        } catch (IllegalArgumentException | IllegalStateException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Operación no permitida", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void refrescarTabla(boolean aplicarFiltros) {
        modeloTabla.setRowCount(0);
        List<ComponenteTarea> lista;
        if (!aplicarFiltros) {
            lista = servicio.listarTareas();
        } else {
            EstrategiaFiltro filtro = construirFiltro();
            EstrategiaOrdenamiento orden = construirOrden();
            lista = servicio.consultarTareas(filtro, orden, chkAscendente.isSelected());
        }
        for (ComponenteTarea t : lista) {
            agregarFilaRecursiva(t, "");
        }
    }

    private void agregarFilaRecursiva(ComponenteTarea t, String indent) {
        boolean esComp = t instanceof TareaCompuesta;
        String tipo = esComp ? "Compuesta" : "Simple";
        String progreso = esComp
                ? String.format("%.1f%%", ((TareaCompuesta) t).getProgreso())
                : ("Terminada".equals(t.getEstadoActual()) ? "100%" : "0%");

        modeloTabla.addRow(new Object[]{
                t.getId(),
                tipo,
                indent + t.getTitulo(),
                t.getEstadoActual(),
                t.getPrioridad(),
                t.getFechaVencimiento() != null ? t.getFechaVencimiento() : "-",
                t.getResponsable() != null ? t.getResponsable() : "-",
                progreso
        });

        if (esComp) {
            for (ComponenteTarea sub : ((TareaCompuesta) t).getSubtareas()) {
                agregarFilaRecursiva(sub, indent + "   ↳ ");
            }
        }
    }

    private EstrategiaFiltro construirFiltro() {
        String tipo = (String) cmbFiltroTipo.getSelectedItem();
        String valor = txtFiltroValor.getText().trim();
        if ("Todos".equals(tipo) || valor.isEmpty()) return null;

        return switch (tipo) {
            case "Estado" -> new FiltroPorEstado(valor);
            case "Prioridad" -> new FiltroPorPrioridad(Prioridad.valueOf(valor.toUpperCase()));
            case "Responsable" -> new FiltroPorResponsable(valor);
            default -> null;
        };
    }

    private EstrategiaOrdenamiento construirOrden() {
        return switch ((String) cmbOrden.getSelectedItem()) {
            case "Fecha" -> new OrdenPorFecha();
            case "Prioridad" -> new OrdenPorPrioridad();
            case "Título" -> new OrdenPorTitulo();
            case "Estado" -> (tareas, asc) -> {
                Comparator<ComponenteTarea> comp = Comparator.comparing(ComponenteTarea::getEstadoActual);
                return tareas.stream().sorted(asc ? comp : comp.reversed()).toList();
            };
            default -> null;
        };
    }
}
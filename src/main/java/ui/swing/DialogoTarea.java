package ui.swing;

import modelo.*;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.UUID;

public class DialogoTarea extends JDialog {
    private final JTextField txtTitulo = new JTextField(20);
    private final JComboBox<Prioridad> cmbPrioridad = new JComboBox<>(Prioridad.values());
    private final JTextField txtFecha = new JTextField(10);
    private final JTextField txtResponsable = new JTextField(15);
    private final JComboBox<String> cmbTipo = new JComboBox<>(new String[]{"Tarea Simple", "Tarea Compuesta"});
    private ComponenteTarea tareaCreada;

    public DialogoTarea(Frame owner, boolean permitirCompuesta) {
        super(owner, "Nueva Tarea", true);
        setLayout(new BorderLayout(10, 10));
        setSize(380, 270);
        setLocationRelativeTo(owner);

        JPanel panelCampos = new JPanel(new GridLayout(5, 2, 8, 8));
        panelCampos.setBorder(BorderFactory.createEmptyBorder(12, 12, 5, 12));

        panelCampos.add(new JLabel("Tipo:"));
        cmbTipo.setEnabled(permitirCompuesta);
        panelCampos.add(cmbTipo);

        panelCampos.add(new JLabel("Título (*):"));
        panelCampos.add(txtTitulo);

        panelCampos.add(new JLabel("Prioridad:"));
        panelCampos.add(cmbPrioridad);

        panelCampos.add(new JLabel("Vencimiento (AAAA-MM-DD):"));
        panelCampos.add(txtFecha);

        panelCampos.add(new JLabel("Responsable:"));
        panelCampos.add(txtResponsable);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");

        btnGuardar.addActionListener(e -> guardar());
        btnCancelar.addActionListener(e -> dispose());

        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);

        add(panelCampos, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
    }

    private void guardar() {
        try {
            String id = UUID.randomUUID().toString().substring(0, 6).toUpperCase();
            String titulo = txtTitulo.getText().trim();
            Prioridad prio = (Prioridad) cmbPrioridad.getSelectedItem();
            String resp = txtResponsable.getText().trim();
            String fechaTxt = txtFecha.getText().trim();
            LocalDate fecha = fechaTxt.isEmpty() ? null : LocalDate.parse(fechaTxt);

            if (cmbTipo.getSelectedIndex() == 1) {
                tareaCreada = new TareaCompuesta(id, titulo, prio, fecha, resp);
            } else {
                tareaCreada = new TareaSimple(id, titulo, prio, fecha, resp);
            }
            dispose();
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Formato de fecha inválido (use AAAA-MM-DD).", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Validación", JOptionPane.WARNING_MESSAGE);
        }
    }

    public ComponenteTarea getTareaCreada() {
        return tareaCreada;
    }
}
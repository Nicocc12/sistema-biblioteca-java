import javax.swing.*;
import java.awt.*;
import java.util.List;

public class BibliotecaGUI extends JFrame {
    private BibliotecaController controller = new BibliotecaController();
    private JTextArea textAreaMateriales = new JTextArea(15, 40);
    private JTextArea textAreaPrestamos = new JTextArea(10, 30);
    private JTextArea textAreaSocios = new JTextArea(15, 30); // NUEVO: Atributo para socios

    public BibliotecaGUI() {
        setTitle("Biblioteca Saber y Futuro");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        crearComponentes();
    }

    private void crearComponentes() {
        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Materiales", crearPanelMateriales());
        tabs.addTab("Socios", crearPanelSocios());
        tabs.addTab("Prestamos", crearPanelPrestamos());
        add(tabs);
    }

    // PANEL MATERIALES (sin cambios)
    private JPanel crearPanelMateriales() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // FORMULARIO
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Agregar Material"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Componentes
        JTextField codigoField = new JTextField(15);
        JTextField tituloField = new JTextField(15);
        JTextField anioField = new JTextField(15);
        JComboBox<String> tipoCombo = new JComboBox<>(new String[]{"Libro", "Revista"});

        // Campos especificos para Libro
        JTextField autorField = new JTextField(15);
        JTextField generoField = new JTextField(15);

        // Campos especificos para Revista
        JTextField nombreRevistaField = new JTextField(15);
        JTextField numeroField = new JTextField(15);

        // Panel dinamico para campos especificos
        JPanel camposEspecificosPanel = new JPanel(new CardLayout());
        JPanel panelLibro = new JPanel(new GridBagLayout());
        JPanel panelRevista = new JPanel(new GridBagLayout());

        // Configurar panel de Libro
        GridBagConstraints gbcLibro = new GridBagConstraints();
        gbcLibro.insets = new Insets(5, 5, 5, 5);
        gbcLibro.anchor = GridBagConstraints.WEST;
        gbcLibro.fill = GridBagConstraints.HORIZONTAL;

        gbcLibro.gridx = 0; gbcLibro.gridy = 0;
        panelLibro.add(new JLabel("Autor:"), gbcLibro);
        gbcLibro.gridx = 1;
        panelLibro.add(autorField, gbcLibro);

        gbcLibro.gridx = 0; gbcLibro.gridy = 1;
        panelLibro.add(new JLabel("Genero:"), gbcLibro);
        gbcLibro.gridx = 1;
        panelLibro.add(generoField, gbcLibro);

        // Configurar panel de Revista
        GridBagConstraints gbcRevista = new GridBagConstraints();
        gbcRevista.insets = new Insets(5, 5, 5, 5);
        gbcRevista.anchor = GridBagConstraints.WEST;
        gbcRevista.fill = GridBagConstraints.HORIZONTAL;

        gbcRevista.gridx = 0; gbcRevista.gridy = 0;
        panelRevista.add(new JLabel("Nombre Revista:"), gbcRevista);
        gbcRevista.gridx = 1;
        panelRevista.add(nombreRevistaField, gbcRevista);

        gbcRevista.gridx = 0; gbcRevista.gridy = 1;
        panelRevista.add(new JLabel("Numero:"), gbcRevista);
        gbcRevista.gridx = 1;
        panelRevista.add(numeroField, gbcRevista);

        camposEspecificosPanel.add(panelLibro, "Libro");
        camposEspecificosPanel.add(panelRevista, "Revista");

        // Listener para cambiar campos dinamicamente
        tipoCombo.addActionListener(e -> {
            CardLayout cl = (CardLayout)(camposEspecificosPanel.getLayout());
            cl.show(camposEspecificosPanel, (String)tipoCombo.getSelectedItem());
        });

        // Campos comunes
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Codigo:"), gbc);
        gbc.gridx = 1;
        formPanel.add(codigoField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Titulo:"), gbc);
        gbc.gridx = 1;
        formPanel.add(tituloField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Año:"), gbc);
        gbc.gridx = 1;
        formPanel.add(anioField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Tipo:"), gbc);
        gbc.gridx = 1;
        formPanel.add(tipoCombo, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        gbc.gridwidth = 2;
        formPanel.add(camposEspecificosPanel, gbc);

        // Boton Agregar
        gbc.gridx = 0; gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton btnAgregar = new JButton("Agregar Material");
        formPanel.add(btnAgregar, gbc);

        // Area de texto
        textAreaMateriales.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textAreaMateriales);

        // Botones de operaciones
        JPanel botonesPanel = new JPanel(new FlowLayout());
        JButton btnModificarMaterial = new JButton("Modificar Material");
        JButton btnEliminarMaterial = new JButton("Eliminar Material");

        botonesPanel.add(btnModificarMaterial);
        botonesPanel.add(btnEliminarMaterial);

        // Acciones
        btnAgregar.addActionListener(e -> agregarMaterial(
                codigoField, tituloField, anioField, tipoCombo,
                autorField, generoField, nombreRevistaField, numeroField
        ));
        btnModificarMaterial.addActionListener(e -> modificarMaterialDialog());
        btnEliminarMaterial.addActionListener(e -> eliminarMaterialDialog());

        // Organizar panel
        panel.add(formPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(botonesPanel, BorderLayout.SOUTH);

        // Mostrar materiales automaticamente al cargar
        listarMateriales();

        return panel;
    }

    // PANEL SOCIOS
    private JPanel crearPanelSocios() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Formulario
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Agregar Socio"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField dniField = new JTextField(15);
        JTextField nombreField = new JTextField(15);
        JTextField apellidoField = new JTextField(15);

        // DNI
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("DNI:"), gbc);
        gbc.gridx = 1;
        formPanel.add(dniField, gbc);

        // Nombre
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1;
        formPanel.add(nombreField, gbc);

        // Apellido
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Apellido:"), gbc);
        gbc.gridx = 1;
        formPanel.add(apellidoField, gbc);

        // Boton
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton btnAgregar = new JButton("Agregar Socio");
        formPanel.add(btnAgregar, gbc);

        // Area de texto
        textAreaSocios.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textAreaSocios);

        // Acciones
        btnAgregar.addActionListener(e -> agregarSocio(dniField, nombreField, apellidoField));

        panel.add(formPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        // ELIMINADO: panel.add(btnVerSocios, BorderLayout.SOUTH);

        // Mostrar socios automáticamente al cargar
        listarSocios();

        return panel;
    }

    // PANEL PRESTAMOS (sin cambios)
    private JPanel crearPanelPrestamos() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel de operaciones
        JPanel operacionesPanel = new JPanel(new GridLayout(2, 1, 10, 10));

        // Seccion Prestamo
        JPanel prestamoPanel = new JPanel(new GridBagLayout());
        prestamoPanel.setBorder(BorderFactory.createTitledBorder("Realizar Prestamo"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        JTextField libroPrestamoField = new JTextField(15);
        JTextField socioPrestamoField = new JTextField(15);

        gbc.gridx = 0; gbc.gridy = 0;
        prestamoPanel.add(new JLabel("Codigo Libro:"), gbc);
        gbc.gridx = 1;
        prestamoPanel.add(libroPrestamoField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        prestamoPanel.add(new JLabel("DNI Socio:"), gbc);
        gbc.gridx = 1;
        prestamoPanel.add(socioPrestamoField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton btnPrestar = new JButton("Prestar Libro");
        prestamoPanel.add(btnPrestar, gbc);

        // Seccion Devolucion
        JPanel devolucionPanel = new JPanel(new GridBagLayout());
        devolucionPanel.setBorder(BorderFactory.createTitledBorder("Registrar Devolucion"));

        JTextField libroDevolucionField = new JTextField(15);

        gbc.gridx = 0; gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        devolucionPanel.add(new JLabel("Codigo Libro:"), gbc);
        gbc.gridx = 1;
        devolucionPanel.add(libroDevolucionField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton btnDevolver = new JButton("Devolver Libro");
        devolucionPanel.add(btnDevolver, gbc);

        operacionesPanel.add(prestamoPanel);
        operacionesPanel.add(devolucionPanel);

        // Area de texto
        textAreaPrestamos.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textAreaPrestamos);

        // Acciones
        btnPrestar.addActionListener(e -> prestarLibro(libroPrestamoField, socioPrestamoField));
        btnDevolver.addActionListener(e -> devolverLibro(libroDevolucionField));

        panel.add(operacionesPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Mostrar prestamos activos automaticamente al cargar
        listarPrestamosActivos();

        return panel;
    }

    // ========== METODOS PARA MATERIALES ==========

    private void agregarMaterial(JTextField codigoField, JTextField tituloField, JTextField anioField,
                                 JComboBox<String> tipoCombo, JTextField autorField, JTextField generoField,
                                 JTextField nombreRevistaField, JTextField numeroField) {
        try {
            String codigo = codigoField.getText();
            String titulo = tituloField.getText();
            String anioText = anioField.getText();
            String tipo = (String) tipoCombo.getSelectedItem();

            if (codigo.isEmpty() || titulo.isEmpty() || anioText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Complete todos los campos comunes");
                return;
            }

            int anio;
            try {
                anio = Integer.parseInt(anioText);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "El año debe ser un numero valido");
                return;
            }

            if (tipo.equals("Libro")) {
                String autor = autorField.getText();
                String genero = generoField.getText();

                if (autor.isEmpty() || genero.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Complete todos los campos del libro");
                    return;
                }

                controller.altaLibro(codigo, titulo, anio, autor, genero);
                JOptionPane.showMessageDialog(this, "Libro agregado!");

            } else {
                String nombre = nombreRevistaField.getText();
                String numeroText = numeroField.getText();

                if (nombre.isEmpty() || numeroText.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Complete todos los campos de la revista");
                    return;
                }

                int numero;
                try {
                    numero = Integer.parseInt(numeroText);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "El numero debe ser un numero valido");
                    return;
                }

                controller.altaRevista(codigo, titulo, anio, nombre, numero);
                JOptionPane.showMessageDialog(this, "Revista agregada!");
            }

            // Limpiar campos
            codigoField.setText("");
            tituloField.setText("");
            anioField.setText("");
            autorField.setText("");
            generoField.setText("");
            nombreRevistaField.setText("");
            numeroField.setText("");

            listarMateriales();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ========== METODO MODIFICAR ==========

    private void modificarMaterialDialog() {
        try {
            String codigo = JOptionPane.showInputDialog(this, "Ingrese codigo del material a modificar:");
            if (codigo == null || codigo.trim().isEmpty()) return;

            // Buscar el material
            Material material = null;
            for (Material m : controller.getMateriales()) {
                if (m.getCodigo().equals(codigo)) {
                    material = m;
                    break;
                }
            }

            if (material == null) {
                JOptionPane.showMessageDialog(this, "Material no encontrado");
                return;
            }

            if (material instanceof Libro) {
                Libro libro = (Libro) material;

                JTextField tituloField = new JTextField(libro.getTitulo());
                JTextField anioField = new JTextField(String.valueOf(libro.getAnio()));
                JTextField autorField = new JTextField(libro.getAutor());
                JTextField generoField = new JTextField(libro.getGenero());

                JPanel panel = new JPanel(new GridLayout(0, 1));
                panel.add(new JLabel("Tktulo:"));
                panel.add(tituloField);
                panel.add(new JLabel("Año:"));
                panel.add(anioField);
                panel.add(new JLabel("Autor:"));
                panel.add(autorField);
                panel.add(new JLabel("Genero:"));
                panel.add(generoField);

                int result = JOptionPane.showConfirmDialog(this, panel, "Modificar Libro",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                if (result == JOptionPane.OK_OPTION) {
                    // Validar campos
                    if (tituloField.getText().isEmpty() || anioField.getText().isEmpty() ||
                            autorField.getText().isEmpty() || generoField.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Complete todos los campos");
                        return;
                    }

                    int anio;
                    try {
                        anio = Integer.parseInt(anioField.getText());
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "El año debe ser un numero");
                        return;
                    }

                    controller.modificarLibro(codigo, tituloField.getText(), anio,
                            autorField.getText(), generoField.getText());
                    JOptionPane.showMessageDialog(this, "Libro modificado!");
                    listarMateriales();
                }
            } else if (material instanceof Revista) {
                Revista revista = (Revista) material;

                JTextField tituloField = new JTextField(revista.getTitulo());
                JTextField anioField = new JTextField(String.valueOf(revista.getAnio()));
                JTextField nombreField = new JTextField(revista.getNombre());
                JTextField numeroField = new JTextField(String.valueOf(revista.getNumero()));

                JPanel panel = new JPanel(new GridLayout(0, 1));
                panel.add(new JLabel("Titulo:"));
                panel.add(tituloField);
                panel.add(new JLabel("Año:"));
                panel.add(anioField);
                panel.add(new JLabel("Nombre:"));
                panel.add(nombreField);
                panel.add(new JLabel("Numero:"));
                panel.add(numeroField);

                int result = JOptionPane.showConfirmDialog(this, panel, "Modificar Revista",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                if (result == JOptionPane.OK_OPTION) {
                    // Validar campos
                    if (tituloField.getText().isEmpty() || anioField.getText().isEmpty() ||
                            nombreField.getText().isEmpty() || numeroField.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Complete todos los campos");
                        return;
                    }

                    int anio, numero;
                    try {
                        anio = Integer.parseInt(anioField.getText());
                        numero = Integer.parseInt(numeroField.getText());
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "El año y el numero deben ser numeros validos");
                        return;
                    }

                    controller.modificarRevista(codigo, tituloField.getText(), anio,
                            nombreField.getText(), numero);
                    JOptionPane.showMessageDialog(this, "Revista modificada!");
                    listarMateriales();
                }
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarMaterialDialog() {
        try {
            String codigo = JOptionPane.showInputDialog(this, "Ingrese codigo del material a eliminar:");
            if (codigo == null || codigo.trim().isEmpty()) return;

            int confirm = JOptionPane.showConfirmDialog(this,
                    "esta seguro de eliminar el material " + codigo + "?",
                    "Confirmar Eliminacion", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                controller.bajaMaterial(codigo);
                JOptionPane.showMessageDialog(this, "Material eliminado!");
                listarMateriales();
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ========== METODO PARA LISTAR MATERIALES ==========

    private void listarMateriales() {
        StringBuilder sb = new StringBuilder("=== TODOS LOS MATERIALES ===\n\n");
        for (Material m : controller.getMateriales()) {
            sb.append(m.getTipo()).append(" - Codigo: ").append(m.getCodigo())
                    .append(" - Titulo: ").append(m.getTitulo())
                    .append(" - Año: ").append(m.getAnio())
                    .append(" - Estado: ").append(m.getEstado());

            if (m instanceof Libro) {
                Libro libro = (Libro) m;
                sb.append(" - Autor: ").append(libro.getAutor())
                        .append(" - Genero: ").append(libro.getGenero());
            } else if (m instanceof Revista) {
                Revista revista = (Revista) m;
                sb.append(" - Nombre: ").append(revista.getNombre())
                        .append(" - Numero: ").append(revista.getNumero());
            }
            sb.append("\n");
        }
        textAreaMateriales.setText(sb.toString());
    }

    // ========== METODOS PARA SOCIOS - MODIFICADOS ==========

    private void agregarSocio(JTextField dniField, JTextField nombreField, JTextField apellidoField) {
        try {
            String dni = dniField.getText();
            String nombre = nombreField.getText();
            String apellido = apellidoField.getText();

            if (dni.isEmpty() || nombre.isEmpty() || apellido.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Complete todos los campos");
                return;
            }

            controller.altaSocio(dni, nombre, apellido);
            JOptionPane.showMessageDialog(this, "Socio agregado!");
            dniField.setText("");
            nombreField.setText("");
            apellidoField.setText("");
            listarSocios(); // Actualizar automáticamente

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ========== METODO PARA LISTAR SOCIOS (automático) ==========

    private void listarSocios() {
        StringBuilder sb = new StringBuilder("=== SOCIOS ===\n\n");
        for (Socio s : controller.getSocios()) {
            sb.append(s.getDni()).append(" - ").append(s.getNombre())
                    .append(" ").append(s.getApellido()).append("\n");
        }
        textAreaSocios.setText(sb.toString());
    }

    // ========== METODOS PARA PRESTAMOS ==========

    private void prestarLibro(JTextField libroField, JTextField socioField) {
        try {
            String codigoLibro = libroField.getText();
            String dniSocio = socioField.getText();

            controller.prestarLibro(codigoLibro, dniSocio);
            JOptionPane.showMessageDialog(this, "Prestamo realizado!");
            libroField.setText("");
            socioField.setText("");
            listarPrestamosActivos();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void devolverLibro(JTextField libroField) {
        try {
            String codigoLibro = libroField.getText();
            controller.devolverLibro(codigoLibro);
            JOptionPane.showMessageDialog(this, "Devolucion registrada!");
            libroField.setText("");
            listarPrestamosActivos();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ========== METODO PARA LISTAR PRESTAMOS ACTIVOS ==========

    private void listarPrestamosActivos() {
        StringBuilder sb = new StringBuilder("=== PRESTAMOS ACTIVOS ===\n\n");
        for (Prestamo p : controller.getPrestamosActivos()) {
            sb.append("Libro: ").append(p.getLibro().getTitulo())
                    .append(" - Socio: ").append(p.getSocio().getNombre())
                    .append(" ").append(p.getSocio().getApellido()).append("\n");
        }
        textAreaPrestamos.setText(sb.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new BibliotecaGUI().setVisible(true);
        });
    }
}
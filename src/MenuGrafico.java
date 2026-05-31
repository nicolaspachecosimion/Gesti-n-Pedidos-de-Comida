import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class MenuGrafico {

    public static void iniciarVentana(GestionPedidos gestor) {

        JFrame ventana = new JFrame("Restaurante - Panel de Control");
        ventana.setSize(600, 400);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Panel principal
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        ventana.setContentPane(panel);

        // Título
        JLabel label = new JLabel("SISTEMA DE GESTIÓN DE PEDIDOS");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(label, BorderLayout.NORTH);

        // Panel Central para los botones
        JPanel panelCentral = new JPanel();
        panel.add(panelCentral, BorderLayout.CENTER);

        // Botones
        JButton btnCargar = new JButton("Cargar Archivo");
        JButton btnGuardar = new JButton("Guardar Archivo");
        JButton btnAtender = new JButton("Atender Cliente (Formulario)");
        JButton btnConsola = new JButton("Usar Consola Clásica");

        // Añadimos los botones al centro
        panelCentral.add(btnCargar);
        panelCentral.add(btnGuardar);
        panelCentral.add(btnAtender);
        panelCentral.add(btnConsola);


        // Cargar Archivo
        btnCargar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser(".");
                int opcion = fc.showOpenDialog(ventana); // Abre la ventana de Windows

                if (opcion == JFileChooser.APPROVE_OPTION) {
                    File archivo = fc.getSelectedFile(); // Cogemos el archivo que el usuario ha clickado
                    // Llamamos a nuestro mét odo de fusionar pasándole la ruta del archivo
                    gestor.recuperarDatos(archivo.getAbsolutePath());
                    JOptionPane.showMessageDialog(ventana, "¡Datos cargados y fusionados con éxito!");
                }
            }
        });

        // Guardar Archivo
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser(".");
                int opcion = fc.showSaveDialog(ventana);

                if (opcion == JFileChooser.APPROVE_OPTION) {
                    File archivo = fc.getSelectedFile();
                    gestor.guardarDatos(archivo.getAbsolutePath());
                    JOptionPane.showMessageDialog(ventana, "¡Partida guardada correctamente!");
                }
            }
        });

        // Usar la consola
        btnConsola.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ventana.dispose();
                gestor.iniciar();
            }
        });

        // --- EL FORMULARIO DE BUSQUEDA DE CLIENTE ---
        btnAtender.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 1. Creamos la ventana secundaria para el formulario
                JFrame ventanaFormulario = new JFrame("Formulario de Entrada");
                ventanaFormulario.setSize(400, 180);

                // Usamos BorderLayout con márgenes para que quede limpio
                JPanel panelForm = new JPanel(new BorderLayout(10, 10));
                ventanaFormulario.setContentPane(panelForm);

                // Parte de arriba: Instrucción
                JLabel lblPrompt = new JLabel("Introduce el teléfono del cliente a atender:");
                lblPrompt.setHorizontalAlignment(SwingConstants.CENTER);
                lblPrompt.setFont(new Font("Arial", Font.PLAIN, 14));
                panelForm.add(lblPrompt, BorderLayout.NORTH);

                // Parte del centro: Cuadro de texto para escribir
                JTextField txtTelefono = new JTextField();
                txtTelefono.setFont(new Font("Arial", Font.PLAIN, 16));
                panelForm.add(txtTelefono, BorderLayout.CENTER);

                // Parte de abajo: Botón de acción
                JButton btnBuscar = new JButton("Buscar y abrir carrito");
                panelForm.add(btnBuscar, BorderLayout.SOUTH);

                // Evento al pulsar "Buscar y abrir carrito"
                btnBuscar.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent ae) {
                        String telefonoIntroducido = txtTelefono.getText();

                        // Buscamos el cliente usando el nuevo método del gestor
                        Cliente clienteEncontrado = gestor.buscarCliente(telefonoIntroducido);

                        if (clienteEncontrado == null) {
                            // Ventana de aviso de Error si no existe
                            JOptionPane.showMessageDialog(ventanaFormulario,
                                    "ERROR: No existe ningún cliente con ese teléfono.",
                                    "Cliente No Encontrado",
                                    JOptionPane.ERROR_MESSAGE);
                        } else {
                            // Ventana de aviso de Éxito mostrando los datos del cliente
                            JOptionPane.showMessageDialog(ventanaFormulario,
                                    "¡Cliente localizado con éxito!\n" +
                                            "Nombre: " + clienteEncontrado.getNombre() + " " + clienteEncontrado.getApellidos() + "\n\n" +
                                            "Abriendo el sistema de pedidos en consola...",
                                    "Cliente Encontrado",
                                    JOptionPane.INFORMATION_MESSAGE);

                            // Cerramos la ventana del formulario y el menú de control gráfico
                            ventanaFormulario.dispose();
                            ventana.dispose();

                            // Lanzamos directamente la toma de pedidos en consola para ese cliente
                            abrirVentanaPedido(gestor, clienteEncontrado);
                        }
                    }
                });

                // Centramos la ventanita del formulario respecto a la principal
                ventanaFormulario.setLocationRelativeTo(ventana);
                ventanaFormulario.setVisible(true);
            }
        });

        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);
    }

    /**
     * Ventana gráfica que simula un TPV o carrito de la compra.
     */
    public static void abrirVentanaPedido(GestionPedidos gestor, Cliente cliente) {
        JFrame ventanaPedido = new JFrame("Atendiendo a: " + cliente.getNombre());
        ventanaPedido.setSize(500, 450);
        ventanaPedido.setLayout(new BorderLayout(10, 10));

        // 1. Iniciamos el pedido real en la memoria
        Pedido nuevoPedido = cliente.realizarPedido();

        // 2. El Ticket Central (JTextArea)
        JTextArea areaTicket = new JTextArea();
        areaTicket.setFont(new Font("Monospaced", Font.PLAIN, 14)); // Fuente tipo máquina de escribir
        areaTicket.setEditable(false); // Para que el usuario no escriba encima
        areaTicket.setText(nuevoPedido.toString()); // Pintamos el ticket vacío inicial

        // Le ponemos un scroll por si el cliente pide muchísimas cosas
        JScrollPane scrollTicket = new JScrollPane(areaTicket);
        ventanaPedido.add(scrollTicket, BorderLayout.CENTER);

        // 3. Panel Inferior (Desplegable y Botones)
        JPanel panelAbajo = new JPanel();

        // Creamos el menú desplegable y lo rellenamos con la carta del gestor
        JComboBox<String> comboComida = new JComboBox<>();
        for (Producto p : gestor.getCartaProductos()) {
            comboComida.addItem(p.getNombre() + " - " + p.getPrecio() + "€");
        }

        JButton btnAnadir = new JButton("Añadir al pedido");
        JButton btnPagar = new JButton("Ir a Pagar");

        panelAbajo.add(comboComida);
        panelAbajo.add(btnAnadir);
        panelAbajo.add(btnPagar);

        ventanaPedido.add(panelAbajo, BorderLayout.SOUTH);

        // --- EVENTOS DEL CARRITO ---

        // Al pulsar "Añadir al pedido"
        btnAnadir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Miramos qué posición ha elegido en el desplegable
                int indiceSeleccionado = comboComida.getSelectedIndex();
                // Cogemos ese producto exacto de la carta
                Producto pElegido = gestor.getCartaProductos().get(indiceSeleccionado);

                // Lo añadimos al pedido y actualizamos el texto del ticket
                nuevoPedido.agregarProducto(pElegido);
                areaTicket.setText(nuevoPedido.toString());
            }
        });

        // Al pulsar "Ir a Pagar"
        btnPagar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ventanaPedido.dispose(); // Cerramos la ventana del ticket
                System.out.println("\n--- PASANDO A LA PASARELA DE PAGO ---");
                // Saltamos a la consola para usar tu mét odo de pago
                abrirVentanaPago(nuevoPedido, cliente);
            }
        });

        ventanaPedido.setLocationRelativeTo(null);
        ventanaPedido.setVisible(true);
    }

    /**
     * Ventana para gestionar el cobro final gráficamente.
     */
    public static void abrirVentanaPago(Pedido pedido, Cliente cliente) {
        JFrame ventanaPago = new JFrame("Pasarela de Pago");
        ventanaPago.setSize(350, 250);
        ventanaPago.setLayout(new BorderLayout(10, 10));

        // Letrero con el importe total
        JLabel lblImporte = new JLabel("IMPORTE A PAGAR: " + pedido.getImporteTotal() + " €");
        lblImporte.setHorizontalAlignment(SwingConstants.CENTER);
        lblImporte.setFont(new Font("Arial", Font.BOLD, 18));
        ventanaPago.add(lblImporte, BorderLayout.NORTH);

        // Panel con 3 botones para los métodos de pago
        JPanel panelBotones = new JPanel(new GridLayout(3, 1, 10, 10));
        JButton btnEfectivo = new JButton("1. Pagar en Efectivo");
        JButton btnTarjeta = new JButton("2. Pagar con Tarjeta");
        JButton btnCuenta = new JButton("3. Pagar con Cuenta");

        panelBotones.add(btnEfectivo);
        panelBotones.add(btnTarjeta);
        panelBotones.add(btnCuenta);
        ventanaPago.add(panelBotones, BorderLayout.CENTER);

        // --- EVENTOS DE PAGO ---

        // Pago en Efectivo
        btnEfectivo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Sacamos una ventanita para pedir el dinero
                String input = JOptionPane.showInputDialog(ventanaPago, "Introduce la cantidad a entregar (ej: 20.50):");
                if (input != null && !input.isEmpty()) {
                    try {
                        float cantidad = Float.parseFloat(input.replace(",", "."));
                        boolean exito = pedido.pagar(Pedido.PAGO_EFECTIVO, "", cantidad);
                        finalizarPagoGrafico(exito, pedido, cliente, ventanaPago);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(ventanaPago, "ERROR: Cantidad numérica no válida.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        // Pago con Tarjeta
        btnTarjeta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tarjeta = JOptionPane.showInputDialog(ventanaPago, "Introduce el número de tarjeta:");
                if (tarjeta != null && !tarjeta.isEmpty()) {
                    boolean exito = pedido.pagar(Pedido.PAGO_TARJETA, tarjeta, 0);
                    finalizarPagoGrafico(exito, pedido, cliente, ventanaPago);
                }
            }
        });

        // Pago con Cuenta
        btnCuenta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cuenta = JOptionPane.showInputDialog(ventanaPago, "Introduce el número de cuenta:");
                if (cuenta != null && !cuenta.isEmpty()) {
                    boolean exito = pedido.pagar(Pedido.PAGO_CUENTA, cuenta, 0);
                    finalizarPagoGrafico(exito, pedido, cliente, ventanaPago);
                }
            }
        });

        ventanaPago.setLocationRelativeTo(null);
        ventanaPago.setVisible(true);
    }

    /**
     * Mét odo auxiliar para mostrar el resultado del pago y guardar el historial.
     */
    private static void finalizarPagoGrafico(boolean exito, Pedido pedido, Cliente cliente, JFrame ventana) {
        if (exito) {
            JOptionPane.showMessageDialog(ventana,
                    "OPERACIÓN REALIZADA CON ÉXITO.\n" +
                            "PEDIDO: " + pedido.getPago().getCodigoPago() + "\n" +
                            "ESTADO: PAGADO",
                    "Pago Completado", JOptionPane.INFORMATION_MESSAGE);

            cliente.agregarPedido(pedido); // Guardamos en el historial
            ventana.dispose(); // Cerramos la ventana de pago
        } else {
            JOptionPane.showMessageDialog(ventana, "La operación de pago no se pudo completar (revisa el saldo entregado).", "Pago Fallido", JOptionPane.ERROR_MESSAGE);
        }
    }
}

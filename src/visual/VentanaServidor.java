package visual;

import clienteServidor.*;

import javax.swing.*;

/**
 * Created by Ricardo on 10/5/16.
 */
public class VentanaServidor extends JFrame implements clienteServidor.ProcesoFrame{

    private JLabel lblId;
    public JTextField txtId;

    public JTextArea txtEventos;
    private JScrollPane scroll;

    private JButton btnCerrar;

    private Servidor servidor;
    private int id;

    private int puertoEntrada;
    private int puertoSalida;

    public VentanaServidor(int id, int puertoEntrada, int puertoSalida) {

        super("Servidor Magico");

        this.id = id;
        this.puertoEntrada = puertoEntrada;
        this.puertoSalida = puertoSalida;

        System.out.println("Entrada: " + puertoEntrada);
        System.out.println("Salida: " + puertoSalida);

        setLayout(null);

        lblId = new JLabel("ID:");
        lblId.setBounds(200, 10, 50, 30);
        add(lblId);

        txtId = new JTextField();
        txtId.setEditable(false);
        txtId.setBounds(230, 10, 50, 30);
        add(txtId);

        txtEventos = new JTextArea();
        txtEventos.setEditable(false);
        txtEventos.setBounds(10, 50, 480, 200);
        add(txtEventos);

        scroll = new JScrollPane(txtEventos);
        scroll.setBounds(10, 50, 480, 200);
        add(scroll);

        btnCerrar = new JButton("Cerrar");
        btnCerrar.setBounds(400, 10, 80, 30);
        add(btnCerrar);

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(500, 300);
        setVisible(true);

        servidor = new Servidor(txtId, txtEventos, btnCerrar, id, puertoEntrada, puertoSalida);

        Thread hilo = new Thread(servidor);
        hilo.start();

    }

    public int getPuertoEntrada() {
        return puertoEntrada;
    }

    public int getPuertoSalida() {
        return puertoSalida;
    }

    public void setPuertoSalida(int puertoSalida) {
        this.puertoSalida = puertoSalida;
    }
}

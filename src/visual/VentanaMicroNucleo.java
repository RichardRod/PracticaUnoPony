package visual;

import clienteServidor.Cliente;
import clienteServidor.MicroNucleo;
import clienteServidor.Servidor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaMicroNucleo extends JFrame implements ActionListener{

    private JLabel lblMaquinaDestino;
    private JLabel lblProceso;

    private static JTextField txtMaquinaDestino;
    private static JTextField txtProceso;

    private JTextArea txtEventos;
    private JScrollPane scrollEventos;

    private JButton btnCliente;
    private JButton btnServidor;

    private MicroNucleo microNucleo;


    public VentanaMicroNucleo() {

        super("Practica 1: Cliente Servidor");

        setLayout(null);

        lblMaquinaDestino = new JLabel("Maquina Destino:");
        lblMaquinaDestino.setBounds(10, 10, 200, 30);
        add(lblMaquinaDestino);

        txtMaquinaDestino = new JTextField();
        txtMaquinaDestino.setBounds(120, 10, 100, 30);
        add(txtMaquinaDestino);

        lblProceso = new JLabel("Proceso:");
        lblProceso.setBounds(250, 10, 200, 30);
        add(lblProceso);

        txtProceso = new JTextField();
        txtProceso.setBounds(310, 10, 50, 30);
        add(txtProceso);

        txtEventos = new JTextArea();
        txtEventos.setBounds(10, 50, 480, 300);
        add(txtEventos);

        scrollEventos = new JScrollPane(txtEventos);
        scrollEventos.setBounds(10, 50, 480, 300);
        add(scrollEventos);

        btnCliente = new JButton("Cliente");
        btnCliente.addActionListener(this);
        btnCliente.setBounds(150, 380, 80, 30);
        add(btnCliente);

        btnServidor = new JButton("Servidor");
        btnServidor.addActionListener(this);
        btnServidor.setBounds(250, 380, 80, 30);
        add(btnServidor);

        microNucleo = new MicroNucleo();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 450);
        setResizable(false);
        setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == btnCliente) {


            Cliente cliente = new Cliente(microNucleo.listaProcesos.size());
            imprimirMensjae("Inicio de proceso Cliente: " + microNucleo.listaProcesos.size());
            microNucleo.crearProceso(cliente);

        }
        else if (e.getSource() == btnServidor) {

            Servidor servidor = new Servidor(microNucleo.listaProcesos.size());
            imprimirMensjae("Inicio Proceso Servidor: " + microNucleo.listaProcesos.size());
            microNucleo.crearProceso(servidor);

        }

    }

    private void imprimirMensjae(String mensaje) {
        txtEventos.append(mensaje + "\n");
    }

    public static String obtenerDireccionIP() {

        if(txtMaquinaDestino.getText().length() != 0) {
            return txtMaquinaDestino.getText();
        } else {
            return "127.0.0.1";
        }
    }

    public static int obtenerProcesoDestino() {

        if(txtProceso.getText().length() != 0) {
            return Integer.parseInt(txtProceso.getText());
        } else {
            return -1;
        }
    }
}

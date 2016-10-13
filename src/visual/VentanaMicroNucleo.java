package visual;

import clienteServidor.MicroNucleo;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;

public class VentanaMicroNucleo extends JFrame{

    private JLabel lblMaquinaDestino;
    private JLabel lblProceso;

    private static JTextField txtMaquinaDestino;
    private static JTextField txtProceso;

    private JTextArea txtEventos;
    private JScrollPane scrollEventos;

    private JButton btnCliente;
    private JButton btnServidor;


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
        btnCliente.setBounds(150, 380, 80, 30);
        add(btnCliente);

        btnServidor = new JButton("Servidor");
        btnServidor.setBounds(250, 380, 80, 30);
        add(btnServidor);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 450);
        setResizable(false);
        setVisible(true);

        Thread hiloMicroNucleo = new Thread(new MicroNucleo(txtEventos, btnCliente, btnServidor, txtMaquinaDestino, txtProceso));
        hiloMicroNucleo.start();
    }

}

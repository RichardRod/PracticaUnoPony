package visual;

import clienteServidor.MicroNucleo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
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

    private JTable tablaProcesos;
    private JScrollPane desplazamientoTabla;
    private JButton btnEliminar;


    public VentanaMicroNucleo() {

        super("Practica 1: Cliente Servidor");

        setLayout(null);

        tablaProcesos = new JTable(new DefaultTableModel(new Object[][]{},
                new String[] {
                    "ID", "Tipo"
                }));
        tablaProcesos.setBounds(400, 50, 300, 300);
        add(tablaProcesos);

        desplazamientoTabla = new JScrollPane(tablaProcesos);
        desplazamientoTabla.setBounds(400, 50, 300, 300);
        add(desplazamientoTabla);

        btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(500, 370, 80, 30);
        add(btnEliminar);

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
        txtEventos.setBounds(10, 50, 380, 300);
        txtEventos.setEditable(false);
        add(txtEventos);

        scrollEventos = new JScrollPane(txtEventos);
        scrollEventos.setBounds(10, 50, 380, 300);
        add(scrollEventos);

        btnCliente = new JButton("Cliente");
        btnCliente.setBounds(150, 370, 80, 30);
        add(btnCliente);

        btnServidor = new JButton("Servidor");
        btnServidor.setBounds(250, 370, 80, 30);
        add(btnServidor);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(715, 450);
        setResizable(false);
        setVisible(true);

        Thread hiloMicroNucleo = new Thread(new MicroNucleo(txtEventos, btnCliente, btnServidor, txtMaquinaDestino, txtProceso, tablaProcesos, btnEliminar));
        hiloMicroNucleo.start();
    }

}

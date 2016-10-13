package visual;

import clienteServidor.Cliente;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Ricardo on 10/5/16.
 */
public class VentanaCliente extends JFrame implements ActionListener{

    private JLabel lblId;
    public JTextField txtId;

    public JTextArea txtEventos;
    private JScrollPane scrollEventos;

    private JLabel lblOperacion;
    private JComboBox comboOpciones;
    private String[] opciones = {"", "Suma", "Resta", "Multiplicacion", "Division", "Potencia"};

    private JButton btnSolicitar;
    private JButton btnCerrar;

    private JLabel lblOpUno;
    private JTextField txtUno;
    private JLabel lblOpDos;
    private JTextField txtDos;

    private int id;

    //puertos
    int puertoEntrada;
    int puertoSalida;


    public VentanaCliente(int id, int puertoEntrada, int puertoSalida) {

        super("Cliente");

        setLayout(null);

        this.id = id;
        this.puertoEntrada = puertoEntrada;
        this.puertoSalida = puertoSalida;

        lblId = new JLabel("ID:");
        lblId.setBounds(200, 10, 50, 30);
        add(lblId);

        txtId = new JTextField();
        txtId.setEditable(false);
        txtId.setBounds(230, 10, 50, 30);
        add(txtId);

        txtEventos = new JTextArea();
        txtEventos.setEditable(false);
        txtEventos.setBounds(10, 50, 280, 200);
        add(txtEventos);

        scrollEventos = new JScrollPane(txtEventos);
        scrollEventos.setBounds(10, 50, 280, 200);
        add(scrollEventos);

        lblOperacion = new JLabel("Seleccione Operacion");
        lblOperacion.setBounds(300, 50, 200, 30);
        add(lblOperacion);

        comboOpciones = new JComboBox(opciones);
        comboOpciones.setBounds(300, 100, 180, 30);
        add(comboOpciones);

        btnSolicitar = new JButton("Solicitar");
        btnSolicitar.setBounds(300, 220, 80, 30);
        add(btnSolicitar);

        btnCerrar = new JButton("Cerrar");
        btnCerrar.setBounds(400, 220, 80, 30);
        add(btnCerrar);

        lblOpUno = new JLabel("Op 1:");
        lblOpUno.setBounds(300, 130, 50, 30);
        add(lblOpUno);

        txtUno = new JTextField();
        txtUno.setBounds(350, 130, 130, 30);
        add(txtUno);

        lblOpDos = new JLabel("Op 2:");
        lblOpDos.setBounds(300, 160, 50, 30);
        add(lblOpDos);

        txtDos = new JTextField();
        txtDos.setBounds(350, 160, 130, 30);
        add(txtDos);

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(500, 300);
        setVisible(true);




        Thread hiloCliente = new Thread(new Cliente(id, txtId, txtEventos, txtUno, txtDos, btnSolicitar, comboOpciones, puertoEntrada, puertoSalida));
        hiloCliente.start();


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



    @Override
    public void actionPerformed(ActionEvent e) {

    }

}

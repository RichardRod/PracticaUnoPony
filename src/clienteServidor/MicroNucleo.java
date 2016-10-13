package clienteServidor;

import visual.VentanaCliente;
import visual.VentanaServidor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Ricardo on 10/5/16.
 */
public class MicroNucleo implements Runnable, ActionListener {

    public static int PUERTO_CLIENTE_1 = 8150;
    public static int PUERTO_CLIENTE_2 = 8153;

    public static int PUERTO_SERVIDOR_1 = 8151;
    public static int PUERTO_SERVIDOR_2 = 8152;

    int contadorProcesos = 1;

    private static JTextField txtMaquinaDestino;
    private static JTextField txtProceso;

    private JTextArea txtEventos;

    private JButton btnCliente;
    private JButton btnServidor;

    private static Hashtable listaClientes;
    private static Hashtable listaServidores;

    VentanaCliente cliente;
    VentanaServidor servidor;

    public MicroNucleo(JTextArea txtEventos, JButton btnCliente, JButton btnServidor, JTextField txtMaquinaDestino, JTextField txtProceso) {

        this.txtEventos = txtEventos;

        this.btnCliente = btnCliente;
        this.btnCliente.addActionListener(this);

        this.btnServidor = btnServidor;
        this.btnServidor.addActionListener(this);

        MicroNucleo.txtMaquinaDestino = txtMaquinaDestino;
        MicroNucleo.txtProceso = txtProceso;

        listaClientes = new Hashtable<>();
        listaServidores = new Hashtable<>();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == btnCliente) {

            if(listaClientes.size() < 16) {

                int puertoEntrada = setPuertoEntrada();

                System.out.println("MicroNucleo.java: " + puertoEntrada);

                cliente = new VentanaCliente(contadorProcesos, puertoEntrada, setPuertoSalida());

                listaClientes.put(contadorProcesos, cliente);
                contadorProcesos++;


            } else {
                JOptionPane.showMessageDialog(null, "No puedes agregar mas Clientes");
            }
        }
        else if (e.getSource() == btnServidor) {

            if(listaServidores.size() < 16) {
                System.out.println("Registra servidor: " + contadorProcesos);

                servidor = new VentanaServidor(contadorProcesos, setPuertoEntrada(), setPuertoSalida());


                listaServidores.put(contadorProcesos, servidor);

                contadorProcesos++;


            } else {
                JOptionPane.showMessageDialog(null, "No puedes agregar mas Servidores");
            }
        }
    }

    @Override
    public void run() {

        byte mensaje[] = new byte[1024];

        int puercoEntrada = MicroNucleo.PUERTO_CLIENTE_1;

        ServerSocket servidor;
        Socket cliente;

        try {

            servidor = new ServerSocket(puercoEntrada);

            while (true)
            {
                cliente = servidor.accept();
                DataInputStream stream = new DataInputStream(cliente.getInputStream());

                stream.read(mensaje);

                cliente.close();



            }

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex.toString());
        }//fin de try-catch


    }

    public static void enviarMensaje(int procesoDestino, byte[] mensaje) {

        int puerco;
        String host = MicroNucleo.obtenerDireccionIP();

        System.out.println("Proceso destino: "+ procesoDestino);
        System.out.println(listaClientes.keys().toString());
        System.out.println(listaServidores.keys().toString());

        System.out.println("TAM: " + listaServidores.size());

        if (listaClientes.get(procesoDestino) != null) {
            puerco = ((VentanaCliente)listaClientes.get(procesoDestino)).getPuertoEntrada();
            System.out.println("Busca cliente DESTINO: " + procesoDestino + " " + "puerto: " + puerco);

        } else if (listaServidores.get(procesoDestino) != null) {
            puerco = ((VentanaServidor)listaServidores.get(procesoDestino)).getPuertoEntrada();
            System.out.println("Busca servidor");

        } else {
            puerco = -1;
            //enviar TY
        }

        try {

            System.out.println("Puerco: " + puerco);
            Socket cliente = new Socket(host, puerco);
            DataOutputStream flujoEnvio = new DataOutputStream(cliente.getOutputStream());
            flujoEnvio.write(mensaje);
            cliente.close();

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
        }
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

    public int setPuertoEntrada() {


        return (8080 + contadorProcesos);
    }

    public int setPuertoSalida() {

        return (8112 + contadorProcesos);
    }
}

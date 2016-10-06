package clienteServidor;

import visual.VentanaServidor;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Ricardo on 10/5/16.
 */
public class Servidor implements Runnable{

    private JTextField txtId;
    private JTextArea txtEventos;
    private JButton btnCerrar;
    private int id;

    public Servidor(JTextField txtId, JTextArea txtEventos, JButton btnCerrar, int id) {
        this.txtId = txtId;
        this.txtEventos = txtEventos;
        this.btnCerrar = btnCerrar;
        this.id = id;
    }


    @Override
    public void run() {

        System.out.println("Hilo iniciado");
        txtId.setText(String.valueOf(id));

        int puertoEntrada = 8080;

        ServerSocket servidor;
        Socket cliente;

        txtEventos.setText("Socket iniciado en el puerto: " + puertoEntrada + "\n");

        try
        {
            servidor = new ServerSocket(puertoEntrada);

            while (true)
            {
                cliente = servidor.accept();
                DataInputStream stream = new DataInputStream(cliente.getInputStream());

                byte mensaje[] = new byte[1024];

                stream.read(mensaje);

                for(byte b: mensaje)
                {
                    txtEventos.append("Emisor: " + cliente.getInetAddress().getHostAddress() + ": " + (char)b + "\n");
                }

                cliente.close();
            }

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex.toString());
        }
    }
}//fin de la clase

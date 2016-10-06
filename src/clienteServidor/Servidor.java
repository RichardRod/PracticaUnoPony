package clienteServidor;

import visual.VentanaServidor;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * Created by Ricardo on 10/5/16.
 */
public class Servidor extends Proceso {

    VentanaServidor ventana;


    public Servidor(int id) {

        super(id);
        ventana = new VentanaServidor();



        iniciarHilo();


    }

    private void iniciarHilo() {
        new Thread(this).start();
    }

    @Override
    public void run() {

        System.out.println("Hilo iniciado");

        ventana.txtId.setText(String.valueOf(getID()));


        for (int i = 0; i < 100000; i++) {
            ventana.txtEventos.setText("vaselina");
        }

        DatagramSocket socket;
        DatagramPacket packet;

        int puerto = 8080;

        byte[] buffer = new byte[1024];

        ventana.txtEventos.setText("Socket iniciado en el puerto: " + puerto + "\n");

        try
        {
            socket = new DatagramSocket(puerto);

            packet = new DatagramPacket(buffer, buffer.length);

            while (true)
            {
                socket.receive(packet);
                System.out.println("Bucle");


            }
        } catch (IOException ex) {

        }

    }
}//fin de la clase

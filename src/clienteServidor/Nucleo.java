package clienteServidor;

import visual.VentanaMicroNucleo;

import javax.swing.*;
import java.io.IOException;
import java.net.*;

/**
 * Created by Ricardo on 10/6/16.
 */
public class Nucleo extends MicroNucleo {





    public static void enviar(int destino, byte[] mensaje) {

        DatagramSocket socket;
        DatagramPacket packet;

        int puerto = 8080;



        byte[] buffer = mensaje;

        String ip = VentanaMicroNucleo.obtenerDireccionIP();

        try
        {
            socket = new DatagramSocket();

            packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(ip), puerto);
            socket.send(packet);


            socket.close();

        }catch(SocketException e){
            JOptionPane.showMessageDialog(null, "Error iniciando socket: "+e.getMessage());
        }catch(UnknownHostException e){
            JOptionPane.showMessageDialog(null, "UnknownHostException: "+e.getMessage());
        }catch(IOException e){
            JOptionPane.showMessageDialog(null, "IOException: "+e.getMessage());
        }



    }
}

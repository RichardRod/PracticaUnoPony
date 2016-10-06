package clienteServidor;

import visual.VentanaCliente;
import visual.VentanaServidor;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Ricardo on 10/5/16.
 */
public class MicroNucleo implements Runnable{

    public boolean terminado;

    public List<Proceso> listaProcesos;

    public MicroNucleo() {

        listaProcesos = new LinkedList<>();
    }


    @Override
    public void run() {

        while (terminado != true) {





        }

    }

}

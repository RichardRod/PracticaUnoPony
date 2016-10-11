package clienteServidor;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.IOException;
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

    byte mensaje[] = new byte[1024];

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

                stream.read(mensaje);

                for(byte b: mensaje)
                {
                    txtEventos.append("Emisor: " + cliente.getInetAddress().getHostAddress() + ": " + (char)b + "\n");
                }

                cliente.close();

                desempacar();
            }

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex.toString());
        }
    }

    private void desempacar() {

        //[0][1] campoOrigen
        //[2][3] campoDestino
        //[4][5] CODOP
        //[6] .. [1023] datos relativos a la operacion

        byte codop = mensaje[4];

        realizarOperacion(codop);

        for (int i = 6; i < mensaje.length; i++) {



        }






    }

    private void realizarOperacion(byte codop)
    {
        switch (codop)
        {
            case 1: //suma

                break;

            case 2: //resta

                break;

            case 3: //multiplicacion

                break;
        }
    }


}//fin de la clase

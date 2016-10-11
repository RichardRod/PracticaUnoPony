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

        txtEventos.append("Inicio de proceso\n");

        try
        {
            servidor = new ServerSocket(puertoEntrada);

            while (true)
            {
                cliente = servidor.accept();
                DataInputStream stream = new DataInputStream(cliente.getInputStream());

                stream.read(mensaje);

                txtEventos.append("Mensaje Recibido de: " + mensaje[0] + "\n");
                txtEventos.append("Operacion a Realizar: " + opACadena(mensaje[4]) + "\n");


                desempacar(mensaje);
                for(byte b: mensaje)
                {



                    //txtEventos.append("IP Emisora: " + cliente.getInetAddress().getHostAddress() + ": " + b + "\n");
                }

                cliente.close();


            }

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex.toString());
        }
    }

    private void desempacar(byte[] cuajoEmpacado) {

        //[0][1] campoOrigen
        //[2][3] campoDestino
        //[4][5] CODOP
        //[6] .. [1023] datos relativos a la operacion

        byte codop = cuajoEmpacado[4];
        double op1 = 0;
        double op2 = 0;


        String auxUno = "";
        String auxDos = "";



        boolean banderita = false;
        for (int i = 6; i < cuajoEmpacado.length; i++) {




            if (cuajoEmpacado[i] == 45) {
                banderita = true;
                i++;
            }

            if (banderita == false) {
                auxUno += (char)cuajoEmpacado[i];
            } else {
                auxDos += (char)cuajoEmpacado[i];
            }
        }

        //System.out.println("P1: " + auxUno + " P2: " + auxDos);

        op1 = Double.parseDouble(auxUno);
        op2 = Double.parseDouble(auxDos);


        txtEventos.append(String.valueOf(realizarOperacion(codop, op1, op2)) + "\n");





    }

    private double realizarOperacion(byte codop, double op1, double op2)
    {
        double resultado = 0.0;

        switch (codop)
        {
            case 1: //suma
                resultado = op1 + op2;
                break;

            case 2: //resta
                resultado = op1 + op2;
                break;

            case 3: //multiplicacion
                resultado = op1 + op2;
                break;
        }

        return resultado;
    }

    private String opACadena(byte op) {

        String retorno = "";

        switch (op)
        {
            case 1:
                retorno = "Suma";
                break;

            case 2:
                retorno = "Resta";
                break;

            case 3:
                retorno = "Multiplicacion";
                break;
        }

        return retorno;
    }


}//fin de la clase

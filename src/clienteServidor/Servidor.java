package clienteServidor;

import visual.VentanaMicroNucleo;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Ricardo on 10/5/16.
 */
public class Servidor extends Proceso implements Runnable{

    private JTextField txtId;
    private JTextArea txtEventos;
    private JButton btnCerrar;
    private int id;
    private double respuestaOperacion;

    byte mensaje[] = new byte[1024];

    public Servidor(JTextField txtId, JTextArea txtEventos, JButton btnCerrar, int id, int puertoEntrada, int puertoSalida) {

        super(puertoEntrada, puertoSalida);

        this.txtId = txtId;
        this.txtEventos = txtEventos;
        this.btnCerrar = btnCerrar;
        this.id = id;
    }


    @Override
    public void run() {

        System.out.println("Hilo iniciado");
        txtId.setText(String.valueOf(id));

        int puerco = getPuertoEntrada();

        ServerSocket servidor;
        Socket cliente;

        txtEventos.append("Inicio de proceso\n");

        try
        {
            servidor = new ServerSocket(puerco);

            while (true)
            {
                cliente = servidor.accept();
                DataInputStream stream = new DataInputStream(cliente.getInputStream());

                stream.read(mensaje);

                txtEventos.append("Mensaje Recibido del cliente: " + mensaje[0] + ".\n");
                txtEventos.append("Operacion a Realizar: " + opACadena(mensaje[4]) + ".\n");

                desempacar(mensaje);



                enviarRespuesta(empacarMensaje()); //puede haber pedos

                cliente.close();


            }//fin de while

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex.toString());
        }//fin de try-catch

    }//fin del metodo run

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

        op1 = Double.parseDouble(auxUno);
        op2 = Double.parseDouble(auxDos);


        respuestaOperacion = realizarOperacion(codop, op1, op2);
        txtEventos.append("Resultado: " + String.valueOf(respuestaOperacion) + ".\n");
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
                resultado = op1 - op2;
                break;

            case 3: //multiplicacion
                resultado = op1 * op2;
                break;

            case 4:
                resultado = op1 / op2;
                break;

            case 5:
                resultado = Math.pow(op1, op2);
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

            case 4:
                retorno = "Division";
                break;

            case 5:
                retorno = "Potencia";
                break;
        }

        return retorno;
    }

    private byte[] empacarMensaje() {

        //[0][1] Id servidor
        //[2][3] Id cliente solicitante
        //[4]..[1023] datos relativos a la respuesta

        String respuesta = String.valueOf(respuestaOperacion);

        byte[] cuajoEmpacado = new byte[1024];

        cuajoEmpacado[0] = (byte) id;
        cuajoEmpacado[2] = mensaje[0];

        for (int i = 0; i < respuesta.length(); i++) {
            cuajoEmpacado[i + 4] = (byte) respuesta.charAt(i);
        }

        return cuajoEmpacado;
    }

    private void enviarRespuesta(byte mensaje[]) {

        /*String host = MicroNucleo.obtenerDireccionIP();

        int puerco = getPuertoSalida();

        try {

            Socket cliente = new Socket(host, puerco);
            DataOutputStream flujo = new DataOutputStream(cliente.getOutputStream());

            flujo.write(mensaje);
            flujo.close();

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
        }*/

        System.out.println("Cliente destino: " + mensaje[2]);
        MicroNucleo.enviarMensaje(mensaje[2], mensaje);
    }

}//fin de la clase

package clienteServidor;

import visual.VentanaCliente;
import visual.VentanaMicroNucleo;
import visual.VentanaServidor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Ricardo on 10/5/16.
 */
public class Cliente extends Proceso implements ActionListener, Runnable{

    private JButton btnSolicitar;
    private JComboBox comboOpciones;
    private JTextField txtId;
    private JTextArea txtEventos;
    private JTextField operandoUno;
    private JTextField operandoDos;

    private String op1, op2;

    private int identificador;

    String operacion;

    private VentanaMicroNucleo ventanaMicroNucleo;

    public Cliente(int identificador, JTextField txtId, JTextArea txtEventos, JTextField operandoUno, JTextField operandoDos, JButton btnSolicitar, JComboBox comboOpciones, int puertoEntrada, int puertoSalida) {

        super(puertoEntrada, puertoSalida);

        System.out.println("Cliente.java: " + getPuertoEntrada());

        this.txtId = txtId;
        this.identificador = identificador;

        this.txtEventos = txtEventos;
        this.operandoUno = operandoUno;
        this.operandoDos = operandoDos;

        this.btnSolicitar = btnSolicitar;
        this.btnSolicitar.addActionListener(this);

        this.comboOpciones = comboOpciones;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnSolicitar) {

            if(operandoUno.getText().length() == 0 || operandoDos.getText().length() == 0 || comboOpciones.getSelectedIndex() == 0) {
                JOptionPane.showMessageDialog(null, "Llena los campos");
            } else {
                establecerCODOP();
                setOperandos();
                enviarMensaje();
            }
        }
    }

    private void setOperandos() {
        this.op1 = operandoUno.getText();
        this.op2 = operandoDos.getText();
    }

    private void establecerCODOP() {

        switch (comboOpciones.getSelectedIndex())
        {
            case 1:
                this.operacion = "1";
                System.out.println(operacion);
                break;

            case 2:
                this.operacion = "2";
                System.out.println(operacion);
                break;

            case 3:
                this.operacion = "3";
                System.out.println(operacion);
                break;

            case 4:
                this.operacion = "4";
                break;

            case 5:
                this.operacion = "5";
                break;
        }

    }

    private void enviarMensaje() {

        byte[] mensaje = new byte[1024];

        /*int puerco = getPuertoSalida();
        String host = MicroNucleo.obtenerDireccionIP();

        try {
            //socket para enviar datos
            Socket cliente = new Socket(host, puerco);
            //flujo para el envio de datos
            DataOutputStream flujoEnvio = new DataOutputStream(cliente.getOutputStream());






            flujoEnvio.write(mensaje);
            cliente.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
        }*/

        empacarMensaje(mensaje);

        MicroNucleo.enviarMensaje(MicroNucleo.obtenerProcesoDestino(), mensaje);
    }

    private void empacarMensaje(byte[] mensaje) {

        //[0][1] campoOrigen
        //[2][3] campoDestino
        //[4][5] CODOP
        //[6] .. [1023] datos relativos a la operacion

        //1 campo de origen
        mensaje[0] = (byte) identificador;

        //2 campo destino
        mensaje[2] = (byte) MicroNucleo.obtenerProcesoDestino();

        //3 codigo de operacion
        mensaje[4] = (byte) comboOpciones.getSelectedIndex();

        //4 datos relativos a la operacion
        //op1 y op2
        for (int i = 0; i < op1.length(); i++) {
            mensaje[i + 6] = (byte) op1.charAt(i);
        }

        mensaje[6 + op1.length()] = (byte) '-';

        for (int i = 0; i < op2.length(); i++) {
            mensaje[(i + 6) + (op1.length()) + 1] = (byte) op2.charAt(i);
        }

    }

    private String opACadena(String op) {

        String retorno = "";

        switch (op)
        {
            case "1":
                retorno = "Suma";
                break;

            case "2":
                retorno = "Resta";
                break;

            case "3":
                retorno = "Multiplicacion";
                break;

            case "4":
                retorno = "Division";
                break;

            case "5":
                retorno = "Potencia";
                break;
        }

        return retorno;
    }

    @Override
    public void run() {

        System.out.println("Cliente Runneable");

        establecerIdentificador();

        txtEventos.append("Inicio de Proceso.\n");

        int puerco = getPuertoEntrada();
        ServerSocket servidor;
        Socket cliente;

        byte[] respuestaServidor = new byte[1024];

        try {
            servidor = new ServerSocket(puerco);

            while (true) {

                cliente = servidor.accept();
                DataInputStream stream = new DataInputStream(cliente.getInputStream());

                txtEventos.append("Invocando a Receive().\n");
                stream.read(respuestaServidor);

                txtEventos.append("Desempacando Respuesta del servidor...\n");
                desempacar(respuestaServidor);

                cliente.close();

            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex.toString());
        }//fin de try-catch
    }

    private void desempacar(byte mensaje[]) {

        System.out.println("Cliente.java");
        String respuesta = "";

        for (int i = 4; i < mensaje.length; i++) {
            respuesta += (char)mensaje[i];
        }

        txtEventos.append("Respuesta recibida por el servidor: " + mensaje[0] + "\n");
        txtEventos.append("Resultado: " + respuesta);
    }

    private void establecerIdentificador() {
        txtId.setText(String.valueOf(identificador));
    }
}

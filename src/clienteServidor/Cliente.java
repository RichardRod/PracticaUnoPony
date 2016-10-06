package clienteServidor;

import visual.VentanaCliente;
import visual.VentanaMicroNucleo;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by Ricardo on 10/5/16.
 */
public class Cliente implements ActionListener{

    private JButton btnCerrar;
    private JComboBox comboOpciones;


    private byte[] mensaje;
    public VentanaCliente ventana;


    String operacion;

    public Cliente(int i, JTextArea txtEventos, String operacion, String operandoUno, String operandoDos, JButton btnCerrar, JComboBox comboOpciones) {



        this.btnCerrar = btnCerrar;
        this.btnCerrar.addActionListener(this);

        this.comboOpciones = comboOpciones;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnCerrar) {

            establecerCODOP();
            enviarMensaje();

        }
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
        }

    }

    private void enviarMensaje() {
        int puertoEntrada = 8080;
        String host = VentanaMicroNucleo.obtenerDireccionIP();

        try {
            //socket para enviar datos
            Socket cliente = new Socket(host, puertoEntrada);
            //flujo para el envio de datos
            DataOutputStream flujoEnvio = new DataOutputStream(cliente.getOutputStream());


            byte[] mensaje = new byte[1024];
            mensaje[0] = 'j';
            mensaje[1] = 'i';
            mensaje[2] = 'j';
            mensaje[3] = 'i';
            mensaje[4] = 'j';
            mensaje[5] = 'i';
            mensaje[6] = 'j';
            mensaje[7] = 'i';

            flujoEnvio.write(mensaje);
            cliente.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
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
        }

        return retorno;
    }
}

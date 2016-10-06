package clienteServidor;

import visual.VentanaCliente;

/**
 * Created by Ricardo on 10/5/16.
 */
public class Cliente extends Proceso {


    private byte[] mensaje;
    public VentanaCliente ventana;




    public Cliente(int id) {

        super(id);

        ventana = new VentanaCliente();

        ventana.txtId.setText(String.valueOf(getID()));
        ventana.txtEventos.append("Inicio de proceso\n");


    }


}

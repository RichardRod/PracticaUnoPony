package clienteServidor;

import javax.swing.*;

/**
 * Created by Ricardo on 10/5/16.
 */
public class Proceso {

    //atributos
    private int puertoEntrada;
    private int puertoSalida;

    public Proceso(int puertoEntrada, int puertoSalida) {
        this.puertoEntrada = puertoEntrada;
        this.puertoSalida = puertoSalida;
    }

    public void setPuertoEntrada(int puertoEntrada) {
        this.puertoEntrada = puertoEntrada;
    }

    public void setPuertoSalida(int puertoSalida) {
        this.puertoSalida = puertoSalida;
    }

    public int getPuertoEntrada() {
        return puertoEntrada;
    }

    public int getPuertoSalida() {
        return puertoSalida;
    }
}

package clienteServidor;

import javax.swing.*;

/**
 * Created by Ricardo on 10/5/16.
 */
public class Proceso extends Thread {

    private int ID;


    public void setID(int ID) {
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }

    public Proceso(int id) {
        this.ID = id;

    }
}

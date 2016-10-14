package clienteServidor;

import visual.VentanaCliente;
import visual.VentanaServidor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Ricardo on 10/5/16.
 */
public class MicroNucleo implements Runnable, ActionListener {

    public static int PUERTO_CLIENTE_1 = 8150;
    public static int PUERTO_CLIENTE_2 = 8153;

    public static int PUERTO_SERVIDOR_1 = 8151;
    public static int PUERTO_SERVIDOR_2 = 8152;

    int contadorProcesos = 1;

    private static JTextField txtMaquinaDestino;
    private static JTextField txtProceso;

    private JTextArea txtEventos;

    private JButton btnCliente;
    private JButton btnServidor;

    private static Hashtable listaClientes;
    private static Hashtable listaServidores;

    VentanaCliente cliente;
    VentanaServidor servidor;

    private static JTable tablaProcesos;
    private JButton btnEliminar;

    public MicroNucleo(JTextArea txtEventos, JButton btnCliente, JButton btnServidor, JTextField txtMaquinaDestino, JTextField txtProceso, JTable tablaProcesos, JButton btnEliminar) {

        this.txtEventos = txtEventos;

        this.btnCliente = btnCliente;
        this.btnCliente.addActionListener(this);

        this.btnServidor = btnServidor;
        this.btnServidor.addActionListener(this);

        MicroNucleo.txtMaquinaDestino = txtMaquinaDestino;
        MicroNucleo.txtProceso = txtProceso;

        listaClientes = new Hashtable<>();
        listaServidores = new Hashtable<>();

        MicroNucleo.tablaProcesos = tablaProcesos;

        this.btnEliminar = btnEliminar;
        this.btnEliminar.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == btnCliente) {

            if(listaClientes.size() < 16) {

                int puertoEntrada = setPuertoEntrada();

                System.out.println("MicroNucleo.java: " + puertoEntrada);

                cliente = new VentanaCliente(contadorProcesos, puertoEntrada, setPuertoSalida());

                listaClientes.put(contadorProcesos, cliente);

                registrarProceso(contadorProcesos, "Cliente");
                contadorProcesos++;


            } else {
                JOptionPane.showMessageDialog(null, "No puedes agregar mas Clientes");
            }
        }
        else if (e.getSource() == btnServidor) {

            if(listaServidores.size() < 16) {
                System.out.println("Registra servidor: " + contadorProcesos);

                servidor = new VentanaServidor(contadorProcesos, setPuertoEntrada(), setPuertoSalida());

                listaServidores.put(contadorProcesos, servidor);

                registrarProceso(contadorProcesos, "Servidor");

                contadorProcesos++;


            } else {
                JOptionPane.showMessageDialog(null, "No puedes agregar mas Servidores");
            }
        } else if (e.getSource() == btnEliminar) {
            eliminarProceso();
        }
    }

    private static void eliminarProceso() {

        int indice = tablaProcesos.getSelectedRow();

        DefaultTableModel model = (DefaultTableModel) tablaProcesos.getModel();
        int id = (int)model.getValueAt(indice, 0);

        System.out.println("Casteo alv: " + id);

        boolean banderazo = false;
        String tipo = "";

        Enumeration<Integer> enumeracionClientes = listaClientes.keys();
        Enumeration<Integer> enumeracionServidores = listaServidores.keys();

        while (enumeracionClientes.hasMoreElements()) {

            if(id == enumeracionClientes.nextElement()) {
                banderazo = true;
                System.out.println("Encontrado cliente: " + indice);
                tipo = "cliente";
            }
        }

        while (enumeracionServidores.hasMoreElements()) {
            if(id == enumeracionServidores.nextElement()) {
                banderazo = true;
                System.out.println("Encontrado servidor: " + indice);
                tipo = "servidor";
            }
        }

        System.out.println("Indice: " + indice + " id: " + id);

        if(banderazo == true) {
            //proceso de dar cran

            switch (tipo) {
                case "cliente":
                    MicroNucleo.listaClientes.remove(id);

                    //recorrer el cuajo
                    ((DefaultTableModel) tablaProcesos.getModel()).removeRow(indice);
                    tablaProcesos.addNotify();
                    break;

                case "servidor":
                    MicroNucleo.listaServidores.remove(id);

                    //recorrer el cuajo
                    ((DefaultTableModel) tablaProcesos.getModel()).removeRow(indice);
                    tablaProcesos.addNotify();
                    break;
            }
        }
    }

    public static void eliminarProcesoVentana(int id, String tipo, Proceso procesoATronar) {

        System.out.println("Eliminar: " + id);

        DefaultTableModel model = (DefaultTableModel) tablaProcesos.getModel();
        //int aux = (int)model.getValueAt(indice, 0);

        for (int i = 0; i < tablaProcesos.getRowCount(); i++) {

            if (id == (int)model.getValueAt(i, 0)) {
                model.removeRow(i);
            }

        }

        tablaProcesos.addNotify();


        switch (tipo) {
            case "cliente":
                listaClientes.remove(id);
                ((Cliente) procesoATronar).tronar();
                break;

            case "servidor":
                listaServidores.remove(id);
                ((Servidor) procesoATronar).tronar();
                break;
        }

    }

    @Override
    public void run() {

        byte mensaje[] = new byte[1024];

        int puercoEntrada = MicroNucleo.PUERTO_CLIENTE_1;

        ServerSocket servidor;
        Socket cliente;

        try {

            servidor = new ServerSocket(puercoEntrada);

            while (true)
            {
                cliente = servidor.accept();
                DataInputStream stream = new DataInputStream(cliente.getInputStream());

                stream.read(mensaje);

                cliente.close();



            }

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex.toString());
        }//fin de try-catch


    }

    public static void enviarMensaje(int procesoDestino, byte[] mensaje) {

        int puerco;
        String host = MicroNucleo.obtenerDireccionIP();

        System.out.println("Proceso destino: "+ procesoDestino);
        System.out.println(listaClientes.keys().toString());
        System.out.println(listaServidores.keys().toString());

        System.out.println("TAM: " + listaServidores.size());

        if (listaClientes.get(procesoDestino) != null) {
            puerco = ((VentanaCliente)listaClientes.get(procesoDestino)).getPuertoEntrada();
            System.out.println("Busca cliente DESTINO: " + procesoDestino + " " + "puerto: " + puerco);

        } else if (listaServidores.get(procesoDestino) != null) {
            puerco = ((VentanaServidor)listaServidores.get(procesoDestino)).getPuertoEntrada();
            System.out.println("Busca servidor");

        } else {
            puerco = -1;
            //enviar TY
        }

        try {

            System.out.println("Puerco: " + puerco);
            Socket cliente = new Socket(host, puerco);
            DataOutputStream flujoEnvio = new DataOutputStream(cliente.getOutputStream());
            flujoEnvio.write(mensaje);
            cliente.close();

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static String obtenerDireccionIP() {

        if(txtMaquinaDestino.getText().length() != 0) {
            return txtMaquinaDestino.getText();
        } else {
            return "127.0.0.1";
        }
    }

    public static int obtenerProcesoDestino() {

        if(txtProceso.getText().length() != 0) {
            return Integer.parseInt(txtProceso.getText());
        } else {
            return -1;
        }
    }

    public int setPuertoEntrada() {


        return (8080 + contadorProcesos);
    }

    public int setPuertoSalida() {

        return (8112 + contadorProcesos);
    }

    public void registrarProceso(int id, String tipo) {

        DefaultTableModel modelo = (DefaultTableModel) tablaProcesos.getModel();

        txtEventos.append("Registrando proceso: " + id + " : " + tipo + "\n");
        Object[] fila = new Object[2];

        fila[0] = id;
        fila[1] = tipo;

        modelo.addRow(fila);
        tablaProcesos.setModel(modelo);
    }
}

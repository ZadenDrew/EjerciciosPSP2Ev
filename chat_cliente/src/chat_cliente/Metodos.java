package chat_cliente;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author andrea
 */
public class Metodos {

    public static Socket clienteSocket;
    OutputStream os;
    InputStream is;
/**
 * 
 * @param ip
 * @param porto 
 */
    public void conectar(String ip, int porto) {
        try {

            System.out.println("Creando socket cliente");
            clienteSocket = new Socket();
            System.out.println("Estableciendo la conexión");

            InetSocketAddress addr = new InetSocketAddress("ip",porto);
            clienteSocket.connect(addr);
            is = clienteSocket.getInputStream();
            os = clienteSocket.getOutputStream();
            //Iniciamos hilo para recibir mensajes
            RecibirMensaje hilo = new RecibirMensaje(is);
            hilo.start();
        } catch (IOException ex) {
            System.out.println("Error al iniciar la conexión, cerrando aplicación.");
            System.exit(0);
        }

    }
/**
 * 
 * @param nick
 * @param mensaje 
 */
    public void enviarMens(String nick, String mensaje) {
        try {
            //crearConexion(puerto);
            String mensajeEnviado = nick + ": " + mensaje;
            System.out.println(mensaje);
            os.write(mensajeEnviado.getBytes());
            System.out.println("Mensaje enviado.");
        } catch (IOException ex) {
            System.out.println("Error: " + ex);
        }
    }
/**
 * 
 * @param txtMensaje
 * @param campoChat 
 */
    public void recibirMensaje(JTextField txtMensaje, JTextArea campoChat) {
        try {
            byte[] mensajeRecibido = new byte[120];
            is.read(mensajeRecibido);
            String nuevoMensaje = new String(mensajeRecibido);
            campoChat.setText(campoChat.getText() + "\n" + nuevoMensaje);
            txtMensaje.setText("");

        } catch (IOException ex) {
            Logger.getLogger(Metodos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void cerrarConexion() {
        try {

            os.close();
            is.close();
            //Cerramos el socket
            System.out.println("Cerrando el socket cliente");
            clienteSocket.close();

            System.out.println("Terminado");
        } catch (IOException ex) {
            Logger.getLogger(Metodos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


}

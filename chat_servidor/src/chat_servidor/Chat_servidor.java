package chat_servidor;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.JOptionPane;


/**
 *
 * @author andrea
 */
public class Chat_servidor {

    /**
     * @param args the command line arguments
     */
    static ArrayList<Cliente> clientes = new ArrayList();

    public static void main(String[] args) {
         int porto = Integer.parseInt(JOptionPane.showInputDialog("Puerto?"));
       
        try {
            // Se crea el socket del servidor:
            System.out.println("Creando socket servidor");
            ServerSocket serverSocket = new ServerSocket(porto);

            // El servidor está operativo hasta que cambie el valor del boolean:
            while (true) {
                if (clientes.size() <= 3) {
                    //El socket del servidor se queda escuchando en la direccion deseada.
                    //En cuanto reciba una conexion se crea el objeto Socket
                    System.out.println("Aceptando conexiones");
                    Socket newSocket = serverSocket.accept();
                    InputStream is = newSocket.getInputStream();
                    OutputStream os = newSocket.getOutputStream();
                    // Se crea un Thread:
                    Cliente cliente = new Cliente(newSocket, os, is);
                    cliente.start();
                    clientes.forEach((elemento) -> {
                        elemento.enviarMensaje("Servidor: un usuario se ha unido.");
                    });
                    clientes.add(cliente);
                    if (clientes.size() == 2) {
                        System.out.println("Servidor lleno.");
                    }
                }
            }
        } catch (IOException ex) {
            System.out.println("Error al recibir conexiones");
        }
    }
}

/**
 * Hilo para cada cliente del servidor.
 */
class Cliente extends Thread {

    private Socket socket;
    private InputStream is;
    private OutputStream os;

    /**
     * Recibimos el socket de conexión con el cliente y abrimos las conexiones
     * de entrada y salida.
     *
     * @param socket socket de conexión con el cliente
     * @throws IOException
     */
    public Cliente(Socket socket, OutputStream os, InputStream is) throws IOException {
        this.socket = socket;
        this.os = os;
        this.is = is;
        System.out.println("Conexión recibida");
    }

    @Override
    public void run() {
        while (true) {
            try {
                // Se lee el mensaje recibido:
                byte[] mensajeRecibido = new byte[120];
                is.read(mensajeRecibido);
                System.out.println(new String(mensajeRecibido));
                // Se forma un array de strings para manejar los datos individualmente:
                String[] cadena = new String(mensajeRecibido).split(": ");
                if (cadena[1].contains("/bye")) {
                    System.out.println("El usuario se ha desconectado.");
                    Chat_servidor.clientes.remove(this);
                    System.out.println("Hay " + Chat_servidor.clientes.size() + " usuarios conectados");
                    Chat_servidor.clientes.forEach((elemento) -> {
                        elemento.enviarMensaje("Un usuario se ha desconectado.");
                    });
                    if (Chat_servidor.clientes.isEmpty()) {
                        System.out.println("Ningún cliente conectado.");
                    }
                    os.close();
                    is.close();
                    stop();
                }
                // Se trata el resultado y se envía al cliente:
                String mensajeEnviado = cadena[0] + ": " + cadena[1];
                Chat_servidor.clientes.forEach((elemento) -> {
                    elemento.enviarMensaje(mensajeEnviado);
                });
                System.out.println(mensajeEnviado);
                System.out.println("Mensaje enviado");

            } catch (IOException ex) {
                System.out.println("Error de conexión");
            }
        }
    }

    public void enviarMensaje(String mensaje) {
        try {
            os.write(mensaje.getBytes());
        } catch (IOException ex) {
            System.out.println("Error al enviar mensaje.");
            try {
                os.close();
            } catch (IOException ex1) {
                System.out.println("Error. Envío de mensajes deshabilitado.");
            }
        }
    }
}
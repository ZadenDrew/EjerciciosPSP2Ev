package servidorsocketsstream;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author andrea
 */
public class ServidorSocketsStream {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            System.out.println("Creando socket servidor");

            ServerSocket serverSocket = new ServerSocket();

            System.out.println("Realizando el bind");

            InetSocketAddress addr = new InetSocketAddress("locahost", 6666);
            serverSocket.bind(addr);

            System.out.println("Aceptando conexiones");

            Socket newSocket = serverSocket.accept();

            System.out.println("Conexi�n recibida");

            InputStream is = newSocket.getInputStream();
            OutputStream os = newSocket.getOutputStream();

            for (int i = 0; i <= 3; i++) {
                byte[] mensaje = new byte[25];
                is.read(mensaje);
                System.out.println("Mensaje recibido: " + new String(mensaje));
                System.out.println("Enviando mensaje " + i);
                String mensajeEnviado = "Mensaje desde el servidor";
                os.write(mensajeEnviado.getBytes());
                System.out.println("Mensaje enviado");
            }

            System.out.println("Cerrando el nuevo socket");

            newSocket.close();

            System.out.println("Cerrando el socket servidor");

            serverSocket.close();

            System.out.println("Terminado");

        } catch (IOException e) {
        }
    }

}

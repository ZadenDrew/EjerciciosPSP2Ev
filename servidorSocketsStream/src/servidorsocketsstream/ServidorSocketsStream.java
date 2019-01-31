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
            byte[] mensajeRe = new byte[20];
            int leer = is.read(mensajeRe);

            //Mostramos el mensaje
            System.out.println("Cadena recibida: " + new String(mensajeRe));

            String cadrecibida = new String(mensajeRe);
            String[] cadena = cadrecibida.split(" ");
            int suma = 0;

            for (int i = 0; i < cadena.length - 1; i++) {
                String nu = cadena[i];
                int num = Integer.valueOf(nu);
                suma = suma + num;
            }
            System.out.println("La suma es : " + String.valueOf(suma));

            OutputStream output = newSocket.getOutputStream();
            String mensaje = "La suma es : " + suma;
            output.write(mensaje.getBytes());
            System.out.println("Mensaje enviado");

            System.out.println("Cerrando el nuevo socket");

            newSocket.close();

            System.out.println("Cerrando el socket servidor");

            serverSocket.close();

            System.out.println("Terminado");

        } catch (IOException e) {
        }
    }

}

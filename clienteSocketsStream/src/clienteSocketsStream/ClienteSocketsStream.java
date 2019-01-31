package clienteSocketsStream;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 *
 * @author andrea
 */
public class ClienteSocketsStream {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        boolean recibido = false;
        try {
            System.out.println("Creando socket cliente");
            Socket clienteSocket = new Socket();
            System.out.println("Estableciendo la conexi�n");

            InetSocketAddress addr = new InetSocketAddress("locahost", 666);
            clienteSocket.connect(addr);

            InputStream is = clienteSocket.getInputStream();
            OutputStream output = clienteSocket.getOutputStream();
            String mensaje = "1 8 4 6 3";
            output.write(mensaje.getBytes());

            recibido = true;
            while (recibido == true) {
                InputStream input = clienteSocket.getInputStream();
                System.out.println("Conexión recibida");

                byte[] mensajeRe = new byte[20];
                int leer = input.read(mensajeRe);
                System.out.println(new String(mensajeRe));

                if (leer == -1) {
                    recibido = false;
                }
            }

            System.out.println("Cerrando el socket cliente");

            clienteSocket.close();

            System.out.println("Terminado");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

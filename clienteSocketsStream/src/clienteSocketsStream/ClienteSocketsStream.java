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
        try {
            System.out.println("Creando socket cliente");
            Socket clienteSocket = new Socket();
            System.out.println("Estableciendo la conexi�n");

            InetSocketAddress addr = new InetSocketAddress("locahost", 666);
            clienteSocket.connect(addr);

            InputStream is = clienteSocket.getInputStream();
            OutputStream os = clienteSocket.getOutputStream();
            for (int i = 0; i <= 3; i++) {
                System.out.println("Enviando mensaje " + i);
                String mensajeEnviado = "Mensaje desde el cliente";
                os.write(mensajeEnviado.getBytes());
                System.out.println("Mensaje enviado");
                byte[] mensajeRecibido = new byte[25];
                is.read(mensajeRecibido);
                System.out.println("Mensaje recibido: " + new String(mensajeRecibido));
            }
            

            System.out.println("Cerrando el socket cliente");

            clienteSocket.close();

            System.out.println("Terminado");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

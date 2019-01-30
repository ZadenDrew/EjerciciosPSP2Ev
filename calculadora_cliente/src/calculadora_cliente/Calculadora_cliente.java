package calculadora_cliente;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import javax.swing.JOptionPane;

/**
 *
 * @author andrea
 */
public class Calculadora_cliente {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        boolean recibido = false;
        String mensaje = null;
        // Se crea el socket del cliente:
        System.out.println("Creando socket cliente");
        Socket clienteSocket = new Socket();

        // Se estable la dirección del socket:
        System.out.println("Estableciendo la conexión");
        InetSocketAddress addr = new InetSocketAddress("localhost", 6666);
        clienteSocket.connect(addr);

        InputStream is = clienteSocket.getInputStream();
        OutputStream os = clienteSocket.getOutputStream();

        mensaje = JOptionPane.showInputDialog("Operación a realizar:\nSumar: n1 + n2\nRestar: n1 - n2\nMultiplicar: n1 * n2\nDividir: n1 / n2\nRaiz Cuadrada: n1^2\nIntroduzca espacio entre operando y operadores");
        mensaje = mensaje + " ";
        os.write(mensaje.getBytes());
        System.out.println("Mensaje enviado.");

        byte[] mensajeRecibido = new byte[25];
        is.read(mensajeRecibido);
        System.out.println("Mensaje recibido: " + new String(mensajeRecibido));

        //Cerramos el socket
        System.out.println("Cerrando el socket cliente");
        clienteSocket.close();

        System.out.println("Terminado");
    }

}

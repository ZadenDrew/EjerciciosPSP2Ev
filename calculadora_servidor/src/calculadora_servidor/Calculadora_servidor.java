package calculadora_servidor;

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
public class Calculadora_servidor {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        //Creamos el socket del servidor:
        System.out.println("Creando socket servidor");
        ServerSocket serverSocket = new ServerSocket();

        //Hacemos que el socket del servidor escuche en la direcion deseada
        System.out.println("Realizando el bind");
        InetSocketAddress addr = new InetSocketAddress("localhost", 6666);
        serverSocket.bind(addr);

        //El socket del servidor se queda escuchando en la direccion deseada.
        //En cuenato reciba una conexion se crea el objeto Socket
        System.out.println("Aceptando conexiones");
        Socket newSocket = serverSocket.accept();

        //Se crea un stream que recibira los datos que envie el cliente
        InputStream is = newSocket.getInputStream();
        OutputStream os = newSocket.getOutputStream();
        System.out.println("Conexión recibida");

        // Se lee el mensaje recibido:
        byte[] mensajeRecibido = new byte[25];
        is.read(mensajeRecibido);
        System.out.println("Mensaje recibido: " + new String(mensajeRecibido));

        // Se forma un array de strings para manejar los datos individualmente:
        String[] cadena = new String(mensajeRecibido).split(" ");
        int resultado =0 ;

        switch (cadena[1]) {
            
            // Si el elemento encontrado es +:
            case "+":
                resultado = sumar(Integer.valueOf(cadena[0]), Integer.valueOf(cadena[2]));
             
                break;
            // Si el elemento encontrado es -:
            case "-":
                resultado = restar(Integer.valueOf(cadena[0]), Integer.valueOf(cadena[2]));
                break;
            // Si el elemento encontrado es *:
            case "*":
                resultado = multiplicar(Integer.valueOf(cadena[0]), Integer.valueOf(cadena[2]));
                break;
            // Si el elemento encontrado es /:
            case "/":
                resultado = dividir(Integer.valueOf(cadena[0]), Integer.valueOf(cadena[2]));
                break;
            // Si el elemento encontrado es ^2:
            case "^2":
                resultado = raizCuadrada(Integer.valueOf(cadena[0]));
                break;
        }

        // Se trata el resultado y se envía al cliente:
        System.out.println("Enviando mensaje: " + resultado);
        String mensajeEnviado = String.valueOf(resultado);
        os.write(mensajeEnviado.getBytes());
        System.out.println("Mensaje enviado");

        // Se cierra el nuevo socket:
        System.out.println("Cerrando el nuevo socket");
        newSocket.close();
        // Se cierra el socket Servidor:
        System.out.println("Cerrando el socket servidor");
        serverSocket.close();

        System.out.println("Terminado");
    }

    /**
     * Operación que suma los dos números recibidos.
     *
     * @param num1 El primer número que será sumada.
     * @param num2 El segundo número que será sumado.
     * @return El resultado de la operación.
     */
    public static int sumar(int num1, int num2) {
        System.out.println("Sumando: " + num1 + " + " + num2);
        return num1 + num2;
    }

    /**
     * Operación que resta los dos números recibidos.
     *
     * @param num1 El primer número que será restado.
     * @param num2 El segundo número que será restado.
     * @return El resultado de la operación.
     */
    public static int restar(int num1, int num2) {
        System.out.println("Restando: " + num1 + " - " + num2);
        return num1 - num2;
    }

    /**
     * Operación que multiplica los dos números recibidos.
     *
     * @param num1 El primer número que será multiplicado.
     * @param num2 El segundo número que será multiplicado.
     * @return El resultado de la operación.
     */
    public static int multiplicar(int num1, int num2) {
        System.out.println("Multiplicando: " + num1 + " * " + num2);
        return num1 * num2;
    }

    /**
     * Operación que divide los dos números recibidos.
     *
     * @param num1 El primer número que será dividido.
     * @param num2 El segundo número que será dividido.
     * @return El resultado de la operación.
     */
    public static int dividir(int num1, int num2) {
        System.out.println("Dividiendo: " + num1 + " / " + num2);
        return num1 / num2;
    }

    /**
     * Operación que hace la raiz cuadrada de un número:
     *
     * @param num1 El primer número que será operado.
     * @return El resultado de la operación.
     */
    public static int raizCuadrada(int num1) {
        System.out.println("Raiz cuadrada: " + num1 + "^2");
        return num1 * num1;
    }

}

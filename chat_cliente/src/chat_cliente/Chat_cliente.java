package chat_cliente;

import java.io.InputStream;


/**
 *
 * @author andrea
 */
public class Chat_cliente {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Chat chat = new Chat();
        chat.setVisible(true);
    }

}

class RecibirMensaje extends Thread {

    InputStream is;
    Chat chat = new Chat();
/**
 * 
 * @param is 
 */
    
   
    public RecibirMensaje(InputStream is) {
        this.is = is;
    }

    @Override
    public void run() {

        while (true) {
            Chat.met.recibirMensaje(chat.getCampoTexto(), chat.getCampoChat());
        }

    }
}

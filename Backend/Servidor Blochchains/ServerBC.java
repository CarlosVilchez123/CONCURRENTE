import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import Blockchain.*;

public class ServerBC {
    TcpServerBC mTcpClient;
    Scanner sc;
    Block block;
    static int difficulty = 10;

    void init() throws NoSuchAlgorithmException {

        //chain = new Chain(10);



        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        mTcpClient = new TcpServerBC("192.168.18.175",
                                new TcpServerBC.OnMessageReceived() {
                                    @Override
                                    public void messageReceived(String message) throws NoSuchAlgorithmException {
                                        mensajeRecibido(message);
                                    }
                                });
                        mTcpClient.run();
                    }
                }).start();

        String salir = "n";
        sc = new Scanner(System.in);
        while (!salir.equals("s")) {
            salir = sc.nextLine();
        }
    }

    void mensajeRecibido(String message) throws NoSuchAlgorithmException {

        /* if (message.equals("nodos")) {
            System.out.println(message);
        } */
        String[] arrOfStr = message.split(" ", 4);
        block = new Block(arrOfStr[0], arrOfStr[1], Integer.parseInt(arrOfStr[2]));
        //block.setFlag(Integer.parseInt(arrOfStr[2]));
        block.mine(difficulty);
        System.out.println(block.toFormat());

        ClienteEnvia(block.toFormat());
    }

    void ClienteEnvia(String message) {
        mTcpClient.enviarMensaje(message);
    }
}

import java.io.FileNotFoundException;
import java.util.Scanner;

public class Server {

    TcpServer mTcpServer;
    Scanner sc;

    void init() throws FileNotFoundException {
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        mTcpServer = new TcpServer(
                                new TcpServer.OnMessageReceived() {

                                    @Override
                                    public void messageReceived(String message) {
                                        synchronized (this) {
                                            ServidorRecibe(message);
                                        }
                                    }
                                });
                        mTcpServer.run();
                    }
                }).start();
        String salir = "n";
        sc = new Scanner(System.in);
        System.out.println("Servidor Ejecutandose");

        System.out.println("-----------------------------------------------");
        while (!salir.equals("s")) {
            salir = sc.nextLine();
            ServidorEnviaNodos(salir);
        }
    }

    void ServidorEnviaNodos(String envia) throws FileNotFoundException {
        if (envia.equals("nodos")) {
            System.out.println("Se esta enviando a los nodos");
            mTcpServer.enviarMensajeTcp(envia);
        }
    }

    void ServidorRecibe(String llego) {

    }
}

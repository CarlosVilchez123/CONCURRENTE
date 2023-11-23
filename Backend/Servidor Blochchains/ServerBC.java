import java.util.Scanner;

public class ServerBC {
    TcpServerBC mTcpClient;
    Scanner sc;

    void init() {

        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        mTcpClient = new TcpServerBC("192.168.100.25",
                                new TcpServerBC.OnMessageReceived() {
                                    @Override
                                    public void messageReceived(String message) {
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

    void mensajeRecibido(String message) {

        if (message.equals("distribuye-1")) {

        }
    }

    void ClienteEnvia(String message) {
    }
}

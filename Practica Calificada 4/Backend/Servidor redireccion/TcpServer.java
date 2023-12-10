import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpServer {

    static int numNodos = 2;
    static boolean cont_tcpServer = true;
    private String message;
    private static final int SERVERPORT = 4444;
    private OnMessageReceived messageListener = null;
    int numClient = 0;
    private boolean running = false;
    ServerHilos[] sendClient = new ServerHilos[100];

    PrintWriter mOut;
    BufferedReader in;

    ServerSocket socket;

    public TcpServer(OnMessageReceived messageListener) {
        this.messageListener = messageListener;
    }

    public void enviarMensajeTcp(String mensaje) {
        for (int i = 1; i <= numNodos; i++) {
            sendClient[i].enviarMensaje(mensaje);
            if (i == 1) {
                sendClient[i].enviarMensaje("TU ERES EL NODO MAESTRO");
            }
        }
    }

    public OnMessageReceived getMessageListener() {
        return this.messageListener;
    }

    public void run() {
        running = true;
        try {
            System.out.println("Conectando Servidor");
            socket = new ServerSocket(SERVERPORT);

            while (running) {
                Socket client = socket.accept();
                numClient++;
                sendClient[numClient] = new ServerHilos(client, this, numClient, sendClient);
                Thread t = new Thread(sendClient[numClient]);
                t.start();
                if (numClient <= numNodos) {
                    System.out.println("SE CONECTO EL SERVIDOR NUMERO " + numClient);
                } else {
                    System.out.println("Se recibio un cliente: " + (numClient - numNodos));
                }
            }
        } catch (Exception e) {
            System.out.println("error" + e.getMessage());
        } finally {

        }
    }

    public int getNumClient() {
        return numClient;
    }

    // Interfaz hecha para poder recibir mensajes
    public interface OnMessageReceived {
        public void messageReceived(String message);
    }
}
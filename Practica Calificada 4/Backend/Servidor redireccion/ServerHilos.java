
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerHilos extends Thread {
    private Socket client;
    private TcpServer tcpServer;
    private int clientID;
    private boolean running = false;
    private TcpServer.OnMessageReceived messageListener = null;

    public PrintWriter mOut;
    public BufferedReader in;
    public String message;

    ServerHilos[] clients;

    public ServerHilos(Socket client, TcpServer tcpServer, int clientID, ServerHilos[] clients) {
        this.client = client;
        this.tcpServer = tcpServer;
        this.clientID = clientID;
        this.clients = clients;
    }

    public void run() {
        running = true;

        try {
            try {
                boolean SoyContador = false;
                // esta parte es para que se muestre en pantalla los mensaje al cliente; si no
                // es eso me avisas armijo xD;
                mOut = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())), true);

                // el servidor del juego va a escuchar las peticiones enviadas por el cliente
                messageListener = tcpServer.getMessageListener();

                // leemos los datos de entrada desde un socket.
                in = new BufferedReader(new InputStreamReader(client.getInputStream()));

                while (running) {
                    // lee el mensaje enviado por el cliente
                    message = in.readLine();

                    // el server game recive el mensaje
                    if (message != null && messageListener != null) {
                        messageListener.messageReceived(message);

                    }
                    message = null;
                }

                System.out.println("CLIENT" + "S: Received Message: '" + message + "'");
            } catch (Exception e) {
                System.out.println("Error del servdior :,v: " + e);
            } finally {
                client.close();
            }
        } catch (Exception e) {
            System.out.println("Error del cliente u.u: " + e);
        }
    }

    public void enviarMensaje(String message) {
        if (mOut != null && !mOut.checkError()) {
            mOut.println(message);
            mOut.flush();
        }

    }
}
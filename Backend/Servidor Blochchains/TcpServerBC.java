import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;

public class TcpServerBC {
    private String serverMessagej;
    public String SERVERIP;
    public static final int SERVERPORT = 4444;
    private OnMessageReceived mMessageListener = null;
    private boolean mRun = false;

    PrintWriter out;
    BufferedReader in;

    public TcpServerBC(String ip, OnMessageReceived listener) {
        SERVERIP = ip;
        mMessageListener = listener;
    }

    public void enviarMensaje(String message) {
        if (out != null && !out.checkError()){
            out.println(message);
            out.flush();
        }
    }

    public void run() {
        mRun = true;
        try {
            InetAddress serverAddr = InetAddress.getByName(SERVERIP);
            System.out.println("TCP Client"+ "C: Conectando...");
            Socket socket = new Socket(serverAddr, SERVERPORT);
            try {
               
                out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                System.out.println("Client"+ "C: Sent.");
                System.out.println("Client"+ "C: Done.");
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                System.out.println("llego supongo");
                while (mRun) {
                    serverMessagej = in.readLine();
                    if (serverMessagej != null && mMessageListener != null) {
                        mMessageListener.messageReceived(serverMessagej);
                    }
                    serverMessagej = null;
                }
            } catch (Exception e) {
                System.out.println("Error del servidor " + e.getMessage());

            } finally {
                socket.close();
            }
        } catch (Exception e) {
            System.out.println("Error del cliente " + e.getMessage());
        }
    }

    public interface OnMessageReceived {
        public void messageReceived(String message) throws NoSuchAlgorithmException;
    }
}
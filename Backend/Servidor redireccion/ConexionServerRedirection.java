import java.io.FileNotFoundException;
import java.security.NoSuchAlgorithmException;

public class ConexionServerRedirection {
    public static void main(String[] args) throws FileNotFoundException, NoSuchAlgorithmException {
        Server server = new Server();
        server.init();
    }
}

import java.io.FileNotFoundException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import Blockchain.*;

public class Server {

    TcpServer mTcpServer;
    Scanner sc;
    Chain chain;
    Block block;

    void initializeChain() throws NoSuchAlgorithmException{
        chain = new Chain(10);
        
        for(int i = 0; i < 10; i++){
            chain.addToPool(Integer.toString(i));
        }
    }

    void init() throws FileNotFoundException, NoSuchAlgorithmException {
        initializeChain();
        
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        mTcpServer = new TcpServer(
                                new TcpServer.OnMessageReceived() {

                                    @Override
                                    public void messageReceived(String message) throws NoSuchAlgorithmException {
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
            String popedItem = chain.popPool();
            String originHash = chain.originHash();
            mTcpServer.enviarMensajeTcp(popedItem + " " + originHash);
        }
    }

    void ServidorRecibe(String llego) throws NoSuchAlgorithmException {
        System.out.println(llego);
        String[] arrOfStr = llego.split(" ", 4);
        block = new Block(arrOfStr[1].substring(1), Integer.parseInt(arrOfStr[2]), arrOfStr[0], arrOfStr[3]);
        chain.addToChain(block);

        String popedItem = chain.popPool();
        String previousHash = chain.getBlocks().get(chain.getBlocks().size() - 1).getHash();

        mTcpServer.enviarMensajeTcp(popedItem + " " + previousHash);
        //ServidorEnviaNodos(chain.getBlocks().get(chain.getBlocks().size() - 1).toFormat());
    }
}

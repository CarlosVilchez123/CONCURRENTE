package Blockchain;
//import java.nio.charset.StandardCharsets;
//import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
//import GetHash;

public class Main {

    public static void main(String[] args) throws NoSuchAlgorithmException {
        // sha256;
        Chain chain = new Chain(10);
        for (int i = 0; i < 5; i++){
            String data = Integer.toString(i);
            chain.addToPool(data);
            chain.mine();
        }
        /* try {
            // Example usage:
            //sha256.update("previous_hash_value".getBytes(StandardCharsets.UTF_8)); // Replace with actual value
            GetHash getHash = new GetHash("previous_hash_value");

            String previousHash = getHash.hexDigest();

            String data = "block_data";
            int difficulty = 10; // Replace with desired difficulty level

            Block block = new Block(data, previousHash);
            block.mine(difficulty);

            System.out.println("Mined Block Previous Hash: " + block.getPreviousHash()); 
            System.out.println("Mined Block Data: " + block.getData());
            System.out.println("Mined block nonce: " + block.getNonce());
            System.out.println("Mined Block Hash: " + block.getGetHash().hexDigest()); 
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }    */     
    }
}

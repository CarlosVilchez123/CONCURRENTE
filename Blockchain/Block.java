package Blockchain;

/* import java.nio.charset.StandardCharsets;
import java.security.MessageDigest; */
import java.security.NoSuchAlgorithmException;

public class Block {
    //private MessageDigest sha256;
    private GetHash getHash;
    private String previousHash;
    private int nonce;
    private String data;

    public Block(String data, String previousHash) {
        /* try {
            this.sha256 = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } */
        this.previousHash = previousHash;
        this.nonce = 0;
        this.data = data;
    }

    

    public void mine(int difficulty) throws NoSuchAlgorithmException {
        getHash = new GetHash(toString());
        //hexString = "a123";
        while (new java.math.BigInteger(getHash.hexDigest(), 16).compareTo(new java.math.BigInteger("2").pow(256 - difficulty)) > 0) {
            nonce++;
            getHash = new GetHash(toString());
        }
    }

    /* private void updateHash() {
        sha256.update(toString().getBytes(StandardCharsets.UTF_8));
    } */

    public GetHash getGetHash(){
        return getHash;
    }

    @Override
    public String toString() {
        return previousHash + data + nonce;
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
        // Example usage:
        String previousHash = "previous_hash_value"; // Replace with actual value
        String data = "block_data";
        int difficulty = 4; // Replace with desired difficulty level

        Block block = new Block(data, previousHash);
        block.mine(difficulty);

        System.out.println("Mined Block: " + block.toString());
    }



    public String getPreviousHash() {
        return previousHash;
    }



    public int getNonce() {
        return nonce;
    }



    public String getData() {
        return data;
    }


    public String getHash() throws NoSuchAlgorithmException{
        return getHash.hexDigest();
    }
}

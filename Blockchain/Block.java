package Blockchain;

/* import java.nio.charset.StandardCharsets;
import java.security.MessageDigest; */
import java.security.NoSuchAlgorithmException;

public class Block {
    //private MessageDigest sha256;
    static int jump = 2;
    private int nClient;
    private String hash;
    private GetHash getHash;
    private String previousHash;
    private int nonce;
    private String data;
    private int flag = 0;

    public Block(String data, String previousHash, int nClient) {
        /* try {
            this.sha256 = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } */
        this.previousHash = previousHash;
        this.nonce = nClient;
        this.data = data;
        this.nClient = nClient;
        //this.jump = 2;
    }

    public Block(String data, int nonce, String previousHash, String hash){
        this.previousHash = previousHash;
        this.nonce = nonce;
        this.data = data;
        this.hash = hash;
        //this.jump = 2;
    }

    public void mine(int difficulty) throws NoSuchAlgorithmException {
        getHash = new GetHash(toString());
        //hexString = "a123";
        while (new java.math.BigInteger(getHash.hexDigest(), 16).compareTo(new java.math.BigInteger("2").pow(256 - difficulty)) > 0) {
            if (flag == 1) break;
            nonce = nonce + jump;
            getHash = new GetHash(toString());
        }
        hash = getHash.hexDigest();
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

    public String toFormat(){
        return previousHash + " " + nClient + data + " " + nonce + " " + hash;
    }
    /* public static void main(String[] args) throws NoSuchAlgorithmException {
        // Example usage:
        String previousHash = "previous_hash_value"; // Replace with actual value
        String data = "block_data";
        int difficulty = 4; // Replace with desired difficulty level

        Block block = new Block(data, previousHash);
        block.mine(difficulty);

        System.out.println("Mined Block: " + block.toString());
    } */



    public String getPreviousHash() {
        return previousHash;
    }



    public int getNonce() {
        return nonce;
    }



    public String getData() {
        return data;
    }


    public String getHash(){
        return hash;
    }

    public void setFlag(int flag){
        this.flag = flag;
    }
}

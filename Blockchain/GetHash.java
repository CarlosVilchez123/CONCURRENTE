package Blockchain;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class GetHash{
    String input;
    //String hexString;

    public GetHash(String input){
        this.input = input;
    }

    public String hexDigest() throws NoSuchAlgorithmException{
        MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
        sha256.update(input.getBytes(StandardCharsets.UTF_8));
        byte[] hash = sha256.digest();

        StringBuilder hexString = new StringBuilder();
        for(byte b : hash){
            String hex = Integer.toHexString(0xff & b);
            if(hex.length() == 1){
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
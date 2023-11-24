package Blockchain;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
//import java.util.LinkedList;
import java.util.List;
//import java.util.Queue;

public class Chain {
    private int difficulty;
    private List<Block> blocks;
    private List<String> pool;
    private Block origin;


    public Chain(int difficulty) throws NoSuchAlgorithmException {
            this.difficulty = difficulty;
            this.blocks = new ArrayList<>();
            this.pool = new ArrayList<>();
            createOriginBlock();
    }

    private void createOriginBlock() throws NoSuchAlgorithmException{
        GetHash getHash = new GetHash(" ");
        String emptyStringHash = getHash.hexDigest();
        origin = new Block("Origin", emptyStringHash, 2);
        origin.mine(difficulty);
        blocks.add(origin);
    }

    private boolean proofOfWork(Block block) throws NoSuchAlgorithmException{
        String blockHash = block.getHash();
        String input = block.getPreviousHash() + block.getData() + block.getNonce();
        GetHash getHash = new GetHash(input);
        String calculatedHash = getHash.hexDigest();

        boolean flag = blockHash.equals(calculatedHash) && new java.math.BigInteger(getHash.hexDigest(), 16).compareTo(new java.math.BigInteger("2").pow(256 - difficulty)) <= 0 && block.getPreviousHash().equals(blocks.get(blocks.size() - 1).getHash());
        //System.out.println(flag);
        return flag;
    }

    public void addToChain(Block block) throws NoSuchAlgorithmException{
        if(proofOfWork(block)){
            blocks.add(block);
        }
    }

    public void addToPool(String data){
        pool.add(data);
    }

    /* public void mine() throws NoSuchAlgorithmException {
        if (!pool.isEmpty()) {
            String data = pool.remove(0);
            Block block = new Block(data, blocks.get(blocks.size() - 1).getHash());
            block.mine(difficulty);
            addToChain(block);

            System.out.println("\n\n==============================");
            System.out.println("Hash:\t\t" + block.getHash());
            System.out.println("Previous Hash:\t\t" + block.getPreviousHash());
            System.out.println("Nonce: \t\t" + block.getNonce());
            System.out.println("Data\t\t" + block.getData());
            System.out.println("\n\n==============================");
        }
    } */

    public String popPool(){
        return pool.remove(0);
    }

    public String originHash(){
        return origin.getHash();
    }

    public List<Block> getBlocks(){
        return blocks;
    }
}
import java.util.Date;

/**
 * Created by MEHMET.UGURLU on 20.06.2018.
 */
public class Block {
    public String hash="";
    public String previousHash;
    private String data;
    private long timeStamp;
    private int nonce;


    public Block(String data, String previousHash) {
        this.data = data;
        this.previousHash = previousHash;
        this.timeStamp = new Date().getTime();
    }


    public String hashYap() {
        String calculatedhash = SHA256Util.uygulaSha256(
                previousHash +
                        Long.toString(timeStamp) +
                        data + nonce

        );
        return calculatedhash;
    }


    public void minerBlock(int difficulty) {
        String target = new String(new char[difficulty]).replace('\0', '0');
        while(!hash.startsWith(target)) {
            nonce ++;
            this.hash = hashYap();
            this.nonce=nonce;
        }
    }

}

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by MEHMET.UGURLU on 20.06.2018.
 */
public class Main {
    static ArrayList<Block> blockChain = new ArrayList<>();
    public static int difficulty = 5; //zorluk derecesini 5 veriyoruz

    public static void main(String[] arg) {
        String[] ilkTransactions = {"illk block oluşturma"};

        Block genesisBlock = new Block(Arrays.toString(ilkTransactions), "0");//ilk block oldugu icin previos hashine "0" veriyoruz
        blockChain.add(genesisBlock);//blockChaine genesis block ekliyoruz
        blockChain.get(0).minerBlock(difficulty);//blocku minerrini yapıyoruz
        System.out.println("block genesiz hashi : " + genesisBlock.hash);

        String[] transactions2 = {"Mehmet 10BTC  Vika gönderdi", "Samet 20 BTC 3sdey cüzdanına gönderdi"};
        Block block2 = new Block(Arrays.toString(transactions2), genesisBlock.hash);
        blockChain.add(block2);
        blockChain.get(1).minerBlock(difficulty);
        System.out.println("block 2 hash: " + block2.hash);
        Boolean ikinciBlockSonrasiKontrol = isCheckChainValid();// her minerden sonra blockcahinin valid olup olmadigina bakiyoruz
        System.out.println("ikinci block sonrasi kontrol=" + ikinciBlockSonrasiKontrol);

        String[] transactions3 = {"Koray 8 BTC Fatihe gönderdi.", "Talat 40 BTC Ahmete gönderdi", "Numan 89 BTC Ayşe gönderdi."};
        Block block3 = new Block(Arrays.toString(transactions3), block2.hash);
        blockChain.add(block3);
        blockChain.get(2).minerBlock(difficulty);
        System.out.println("block 3 hash : " + block3.hash);
        Boolean ucuncuBlockSonrasiKontrol = isCheckChainValid(); // her minerden sonra blockcahinin valid olup olmadigina bakiyoruz
        System.out.println("üçüncü block sonrasi kontrol=" + ucuncuBlockSonrasiKontrol);

        //block zincirini bozup tekrar kontrol edelim.
        System.out.println("DENEME AMAÇLI BLOCK CHAIN BOZUYORUZ.HATA MESAJI BEKLIYORUZ.");
        block2.hash = "sdfsafsdf";
        Boolean sonuc = isCheckChainValid();
        System.out.println("block kontrol:" + sonuc);//false bekliyoruz.çünkü blockChaini bozduk.


    }


    public static Boolean isCheckChainValid() {
        Block cblock;
        Block pBlock;
        String hashTarget = new String(new char[difficulty]).replace('\0', '0');


        for (int i = 1; i < blockChain.size(); i++) {
            cblock = blockChain.get(i);
            pBlock = blockChain.get(i - 1);

            if (!cblock.hash.equals(cblock.hashYap())) {
                System.out.println("Block hashleri eşit değil");
                return false;
            }

            if (!pBlock.hash.equals(cblock.previousHash)) {
                System.out.println("Previous block  Hashi blocka eşit değil");
                return false;
            }


            if (!cblock.hash.substring(0, difficulty).equals(hashTarget)) {
                System.out.println("Verilen block mine yapilamadi.Hatali.");
                return false;
            }
        }
        return true;
    }


}

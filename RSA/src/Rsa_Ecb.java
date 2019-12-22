import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ct104 on 2019/12/21.
 */
public class Rsa_Ecb {
    public Rsa_Ecb() { }
    public static void rsa_Ecb_Encrypt(String plainpath, String chiperpath) {
        List<BigInteger>plainlist = IOTools.readPlain(plainpath);
        List<BigInteger>chiperlist = new ArrayList<BigInteger>();
        for(BigInteger plain : plainlist) {
            chiperlist.add(Rsa.rsa_Encrypt(plain));
        }
        IOTools.writeChiper(chiperlist, chiperpath);
    }
    public static void rsa_Ecb_Decrypt(String chiperpath,String decryptPath) {
        List<BigInteger>chiperlist = IOTools.readChiper(chiperpath);
        List<BigInteger>decryptplainlist = new ArrayList<BigInteger>();
        for (BigInteger chiper: chiperlist) {
            BigInteger a = Rsa.rsa_Decrypt(chiper);
            decryptplainlist.add(a);
        }
        IOTools.writeDecryptedPlain(decryptplainlist, decryptPath);
    }
//    public static void main(String args[]) {
//        String path1 = "D:\\1.txt";
//        String path2 = "D:\\2.txt";
//        String path3 = "D:\\3.txt";
//        String path4 = "D:\\4.txt";
//        String path5 = "D:\\5.txt";
//        Rsa_Ecb.rsa_Ecb_Encrypt(path1, path2);
//        //IOTools.writeKey(path4);
//        //IOTools.readKey(path4);
//        Rsa_Ecb.rsa_Ecb_Decrypt(path2, path3);
//    }
}

/**
 * Created by ct104 on 2019/12/20.
 */
import java.math.BigInteger;
import java.util.Random;

public class Rsa {
    private static int Key_Len = 2048;
    private static Key key = new Key();
    private static BigInteger p;
    private static BigInteger q;
    private static BigInteger phin;
    public Rsa() {
    }
    public static void key_Gen() {
        key.e = BigInteger.valueOf(65537);
        prime_Gen();
        key.n = p.multiply(q);
        phin = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        key.d = mod_Inverse(key.e, phin);
    }
    public static Key getKey() {
        return key;
    }
    private static void prime_Gen() {
        Random rand = new Random();
        p = BigInteger.probablePrime(Key_Len / 2, rand);
        q = BigInteger.probablePrime(Key_Len / 2, rand);
    }
    private static BigInteger mod_Inverse(BigInteger a, BigInteger m) {
        return a.modInverse(m);
    }
    public static BigInteger rsa_Encrypt(BigInteger plain) {
        return plain.modPow(key.e, key.n);
    }
    public static BigInteger rsa_Decrypt(BigInteger chiper) {
        return chiper.modPow(key.d, key.n);
    }
}
class Key{
    public BigInteger n, e, d;
    public Key(){}
}

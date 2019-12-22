import java.io.*;
import java.math.BigInteger;
import java.util.*;

/**
 * Created by ct104 on 2019/12/21.
 */
public class IOTools {
    public IOTools(){ }
    public static FileInputStream createInputStream(String path) {
        try {
            return new FileInputStream(new File(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static FileOutputStream createOutputStream(String path) {
        try {
            File file = new File(path);
//            if (!file.exists())
//                file.createNewFile();
            return new FileOutputStream(file);
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static void fill(byte [] word, byte [] temp, int count) {
        if (count == 117) {
            word[0] = (byte)0x00;
            word[1] = 0x01;
            Arrays.fill(word, 2, 10, (byte) 0xff);
            word[10] = 0x00;
            System.arraycopy(temp, 0, word, 11, 117);
        }else {
            word[0] = 0x02;
            Arrays.fill(word, 1, 127 - count, (byte)0xff);
            word[127 - count] = 0x00;
            System.arraycopy(temp, 0, word, 128 - count,count);
        }
    }
    public static byte [] divide(byte [] word) {
        if (word[0] == 1 && word[1] == (byte) 0xff){
            byte [] s = new byte[117];
            System.arraycopy(word, word.length - 117, s, 0, 117);
            return s;
        }else {
            byte [] temp = new byte[128];
            int count = 0, flag = 0;
            for (int i = 0; i < 128; i++) {
                if (flag == 1) {
                    temp[count] = word[i];
                    count++;
                }
                if (word[i] == 0x00) {
                    flag = 1;
                }
            }
            byte [] ava = new byte[count];
            System.arraycopy(temp, 0, ava, 0, count);
            return ava;
        }
    }
    public static List<BigInteger> readPlain(String path){
        try {
            FileInputStream fis = createInputStream(path);
            List<BigInteger> plainlist = new ArrayList<BigInteger>();
            byte[] word = new byte[128];
            byte[] temp = new byte[117];
            int count = 0;
            int a = 0;
            while ((count = fis.read(temp)) != -1) {
                fill(word, temp, count);
                BigInteger plain = bytesToBig(word);
                plainlist.add(plain);
                Arrays.fill(word, (byte)0);
            }
            fis.close();
            return plainlist;
        }catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<BigInteger>();
    }

    public static List<BigInteger> readChiper(String path){
        try {
            FileInputStream fis = createInputStream(path);
            List<BigInteger> plainlist = new ArrayList<BigInteger>();
            byte[] word = new byte[256];
            int count = 0;
            while ((count = fis.read(word)) != -1) {
                BigInteger plain = bytesToBig(word);
                plainlist.add(plain);
                Arrays.fill(word, (byte)0);
            }
            fis.close();
            return plainlist;
        }catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<BigInteger>();
    }
    public static void writeChiper(List<BigInteger>chiperlist, String path) {
        try {
            FileOutputStream fos = createOutputStream(path);
            for (BigInteger chiper : chiperlist) {
                byte [] word = bigToBytes(chiper);
                fos.write(word);
            }
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void writeDecryptedPlain(List<BigInteger>plainlist, String path) {
        try {
            FileOutputStream fos = createOutputStream(path);
            for (BigInteger chiper : plainlist) {
                int a = 0;
                byte [] word = bigToBytes(chiper);
                fos.write(divide(word));
            }
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void writeKey(String path) {
        try {
            File file = new File(path);
            if (file.exists())
                file.delete();
            file.createNewFile();
            FileWriter fw = new FileWriter(file);
            Key key = Rsa.getKey();
            fw.write(key.e+"\n");
            fw.write(key.n+"\n");
            fw.write(key.d+"\n");
            fw.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void readKey(String path) {
        try {
            File file = new File(path);
            FileReader fr = new FileReader(file);
            Scanner in = new Scanner(fr);
            Key key = Rsa.getKey();
            key.e = in.nextBigInteger();
            key.n = in.nextBigInteger();
            key.d = in.nextBigInteger();
            in.close();
            fr.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void printKey() {
        Key key = Rsa.getKey();
        System.out.println("e:" + key.e);
        System.out.println("n:" + key.n);
        System.out.println("d:" + key.d);
    }
    // convert 8 bytes to BigInteger
    public static BigInteger bytesToBig(byte [] a) {
        return new BigInteger(1,a);
    }

    public static byte[] bigToBytes(BigInteger a) {
        byte[] array = a.toByteArray();
        if (array[0] == 0) {
            byte[] tmp = new byte[array.length - 1];
            System.arraycopy(array, 1, tmp, 0, tmp.length);
            return tmp;
        }
        else return array;
    }
//    public static void main(String args[]) {
//        IOTools iot = new IOTools();
//        String path = "D:\\1.txt";
//        String path2 = "D:\\2.txt";
//        File file = new File(path2);
//        if (!file.exists()) {
//            try {
//                file.createNewFile();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        try {
//            FileOutputStream fos = new FileOutputStream(file);
//            List<BigInteger>plainlist = iot.readPlain(path);
//            System.out.println("list size:" + plainlist.size());
//            for (BigInteger plain:plainlist) {
//                System.out.println(plain);
//                byte [] array = iot.bigToBytes(plain);
//                fos.write(array);
////                for (byte s : array) {
////                    System.out.print(s+" ");
////                }
////                System.out.println();
//            }
//            fos.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}

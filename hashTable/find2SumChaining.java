package hashTable;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Iterator;

public class find2SumChaining {
    private DoubleHashing<BigInteger, Integer> hashTable;
    private BigInteger[] allNums = new BigInteger[100];

    public find2SumChaining(String filename, int size) throws IOException {
        //hashTable = new ChainingHT<BigInteger, Integer>(size);
        hashTable = new DoubleHashing<BigInteger, Integer>(size);
        String line;
        BufferedReader reader = new BufferedReader(new FileReader(filename));

        int i = 0;
        while ((line = reader.readLine()) != null) {
            BigInteger value = new BigInteger(line);
            allNums[i++] = value;
            hashTable.add(value, hashTable.getOrDefault(value, 0) + 1);
        }

        reader.close();
    }

    private boolean find2Sum(int t) {
        for (BigInteger bgInt : allNums) {
            Object val1 = hashTable.get(bgInt);
            Object val2 = hashTable.get(BigInteger.valueOf(t).subtract(bgInt));
            if (val1 != null && val2 != null) {
                if ((int) val1 == 1 && (int) val2 == 1) {
                    System.out.println("Sum: " + t + " : " + bgInt);
                    return true;
                }
            }
        }
        return false;
    }

    public int totalSum(int min, int max) {
        int count = 0;
        for (int i = min; i <= max; i++) {
            if (find2Sum(i)) {
                count ++;
            }
        }
        return count;
    }

    public void printHT() {
        hashTable.printTH();
    }



    public static void main(String[] args) throws IOException {
        //find2SumChaining f2sFull = new find2SumChaining("./2sum.txt",524309);
        //System.out.println(f2sFull.totalSum(-10000, 10000));
        //f2sFull.printHT();
        find2SumChaining f2sTest = new find2SumChaining("./2sum-test.txt", 97);
        f2sTest.printHT();
        //System.out.println(f2sTest.totalSum(-50, 50));
    }

}

package hashTable;

import edu.princeton.cs.algs4.In;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class find2SumNaive {
    private int[] allNums;

    public find2SumNaive(String filename) {
        allNums = new int[100];
        try {
            String line;
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            int i = 0;
            while ((line = reader.readLine()) != null) {
                allNums[i++] = Integer.parseInt(line);
            }
            reader.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public int naive2sum() {
        // Implement naive hashing for the integers
        Map<Integer, Integer> nums = new HashMap<>();
        int num = 0;

        for (int i:allNums) {
            nums.put(i, nums.getOrDefault(i, 0)+1);
        }

        for (int i = -50; i <= 50; i++) {
            if (_naive2sum(i, nums)) {
                num++;
            }
        }
        return num;
    }

    private boolean _naive2sum(int t, Map<Integer, Integer> nums) {
        for (int a : allNums) {
            if (nums.containsKey(a) && nums.get(a) == 1) {
                if (nums.containsKey(t - a) && nums.get(t - a) == 1) {
                    System.out.println("sum: " + t +" : " + a + " " + (t-a));
                    return true;
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        find2SumNaive f2s = new find2SumNaive("/Users/MyStudy/DataStructure/proj1/DS1/src/hashTable/2sum.txt");
        System.out.println(f2s.naive2sum());
    }

}

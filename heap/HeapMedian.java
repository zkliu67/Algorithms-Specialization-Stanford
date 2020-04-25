package heap;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.Queue;

public class HeapMedian {
    private heapMin minHeap;
    private heapMax maxHeap;

    public HeapMedian(int length) {
        minHeap = new heapMin(length / 2);
        maxHeap = new heapMax(length / 2);
    }

    public int getMedian(int x) {
        if (maxHeap.isEmpty() && minHeap.isEmpty()) {
            minHeap.add(x);
        } else {
            if (x > minHeap.getMax()) {
                maxHeap.add(x);
            } else {
                minHeap.add(x);
            }
        }
        return _getMedian();
    }

    private int _getMedian() {
        _reArrange();
        if (maxHeap.getSize() == minHeap.getSize()) {
            return (maxHeap.getMin() + minHeap.getMax()) / 2;
        } else {
            return maxHeap.getSize() > minHeap.getSize() ? maxHeap.getMin() : minHeap.getMax();
        }
    }

    private void _reArrange() {
        if (maxHeap.getSize() - minHeap.getSize() > 1) {
            minHeap.add(maxHeap.remove());
        } else if (minHeap.getSize() - maxHeap.getSize() > 1) {
            maxHeap.add(minHeap.remove());
        }
    }

    private Queue<Integer> readFile(String filename) {
        Queue<Integer> q = new LinkedList<Integer>();
        try {
            String entry;
            BufferedReader reader = new BufferedReader(new FileReader(filename));

            while ((entry = reader.readLine()) != null) {
                q.add(Integer.parseInt(entry));
            }
            reader.close();
            return q;
        } catch (Exception e) {
            return null;
        }
    }

    public int result(String filename) {
        Queue<Integer> q = readFile(filename);
        int sum = 0;
        if (q == null) {return Integer.MIN_VALUE;}
        while (!q.isEmpty()) {
            sum += getMedian(q.poll());
        }
        return sum % (minHeap.getSize() + maxHeap.getSize());
    }

    public static void main(String[] args) {
        HeapMedian heapMedian = new HeapMedian(10002);
        System.out.println(heapMedian.result("./median.txt"));

    }

}

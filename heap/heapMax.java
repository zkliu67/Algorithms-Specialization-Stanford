package heap;


/***
 * This file implements the HEAP data structure with integer array;
 * With the following functionalities:
 * 1. Dynamically increase the size of the heap array
 * 2. Dynamically insert new element into the array, with heap property holded.
 * 3. Dynamically delete the min value of the array, and rebuild the array.
 * 4. Return the smallest value of the array.
 */


public class heapMax implements Heap {
    private int[] heap;
    private int size;
    private int maxSize;

    public heapMax(int max) {
        heap = new int[max];
        heap[0] = Integer.MAX_VALUE;
        maxSize = max;
    }

    public void add(int x) {
        if (size++ > maxSize) { resize(); }
        heap[size - 1] = x;
        int index = size - 1;

        while(heap[index] > heap[parent(index)]) {
            swap(index, parent(index));
            index = parent(index);
        }
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int getMax() {return heap[0];}
    public int getMin() {
        if (size > 0) {
            return heap[size - 1];
        }
        return Integer.MIN_VALUE;
    }
    public int getSize() {return size;}
    public int get(int pos) {return heap[pos];}

    public int remove() {
        int min = heap[size - 1];
        heap[size - 1] = 0;
        size --;
        return min;
    }

    public int pop() {
        int max = heap[0];
        // To maintain the heap property
        size --;
        heap[0] = heap[size];
        heap[size] = 0;
        maxHeapify(0);

        return max;
    }

    public void maxHeapify(int pos) {
        if (isLeaf(pos)) {return;}

        if (heap[pos] < heap[leftChild(pos)] || heap[pos] < rightChild(pos)) {
            if (heap[leftChild(pos)] > heap[rightChild(pos)]) {
                swap(pos, leftChild(pos));
                maxHeapify(leftChild(pos));
            } else {
                swap(pos, rightChild(pos));
                maxHeapify(rightChild(pos));
            }
        }
    }

    public boolean isLeaf(int pos) {
        return (leftChild(pos) >= size && rightChild(pos) >= size);
    }

    private int parent(int pos) {
        return (pos - 1)/2;
    }

    private int leftChild(int pos) {
        return pos*2+1;
    }

    private int rightChild(int pos) {
        return (pos+1)*2;
    }

    private void swap(int i, int j) {
        int temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    private void resize() {
        int[] newHeap = new int[maxSize*2];
        System.arraycopy(heap, 0, newHeap, 0, heap.length);
        heap = newHeap;
        maxSize = maxSize*2;
    }

    public void printArray() {
        for (int value : heap) {
            System.out.print(value + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        heapMax heap = new heapMax(10);
        int[] test = new int[] {1, 4, 6, 2, 7, 0, 8, 10};

        for (int value:test) {
            heap.add(value);
        }
        //heap.pop();
        heap.printArray();

    }

}

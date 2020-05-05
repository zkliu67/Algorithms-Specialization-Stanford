package MST;

import heap.Heap;

import java.util.ArrayList;
public class MinHeap {

    private HeapNode[] heapNodes;
    private int capacity;
    private int currSize;
    private int[] indexes;

    public MinHeap(int cap) {
        heapNodes = new HeapNode[cap + 1];
        capacity = cap;
        HeapNode node = new HeapNode(0, Integer.MIN_VALUE);
        heapNodes[0] = node;

        indexes = new int[cap+1];
        currSize = 0;
    }

    public boolean isEmpty() {
        return currSize == 0;
    }

    public int getHeapSize() {return currSize;}

    public void Insert(HeapNode node) {
        currSize ++;
        int idx = currSize;
        heapNodes[idx] = node;


        indexes[node.getVertex()] = idx;
        bubbleUp(idx);
    }

    private void bubbleUp(int idx) {
        int currIdx = idx;
        int parentIdx = idx / 2;

        while (currIdx > 0 && heapNodes[parentIdx].compareTo(heapNodes[currIdx]) > 0) {

            swap(parentIdx, currIdx);
            currIdx = parentIdx;
            parentIdx = currIdx / 2;
        }
    }

    private void swap(int i, int j) {
        HeapNode n1 = heapNodes[i];
        HeapNode n2 = heapNodes[j];
        System.out.println("idx: " + i + " smaller: " + j);
        indexes[n1.getVertex()] = j;
        indexes[n2.getVertex()] = i;

        heapNodes[i] = heapNodes[j];
        heapNodes[j] = n1;
    }

    public HeapNode ExtractMin() {
        HeapNode min = heapNodes[1];
        HeapNode lastNode = heapNodes[currSize];
        heapNodes[currSize] = null;
        currSize -- ;

        indexes[lastNode.getVertex()] = 1;
        heapNodes[1] = lastNode;
        bubbleDown(1);
        return min;
    }

    private void bubbleDown(int idx) {
        int smallest = idx;
        int leftChildIdx = 2 * idx;
        int rightChildIdx = 2 * idx + 1;

        if (leftChildIdx > getHeapSize() && rightChildIdx > getHeapSize()) {
            return;
        }

        if (heapNodes[smallest].compareTo(heapNodes[leftChildIdx]) > 0 ||
                heapNodes[smallest].compareTo(heapNodes[rightChildIdx]) > 0 ) {

            if (heapNodes[leftChildIdx] == null) {
                smallest = rightChildIdx;
            } else if (heapNodes[rightChildIdx] == null) {
                smallest = leftChildIdx;
            } else {
                smallest = heapNodes[leftChildIdx].compareTo(heapNodes[rightChildIdx]) < 0 ? leftChildIdx : rightChildIdx;
            }

        }

        if (smallest != idx) {
            swap(idx, smallest);
            bubbleDown(smallest);
        }
    }

    public void updateKey(int vertex, int newKey) {
        int pos = indexes[vertex];
        heapNodes[pos].updateKey(newKey);
        bubbleUp(pos);
    }
}

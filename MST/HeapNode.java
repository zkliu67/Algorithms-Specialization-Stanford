package MST;

public class HeapNode implements Comparable<HeapNode> {
    private int vertex;
    private int key;

    public HeapNode(int v) {
        vertex = v;
        key = Integer.MAX_VALUE;
    }

    public HeapNode(int v, int k) {
        vertex = v;
        key = k;
    }

    public int getVertex() {return vertex;}
    public int getKey() {return key;}

    public void updateKey(int newKey) {key = newKey;}

    @Override
    public int compareTo(HeapNode o) {
        if (o != null) {
            return Integer.compare(this.getKey(), o.getKey());
        } else {
            return 0;
        }
    }

    public String toString() {
        return "vertex: " + getVertex() + " key: " + getKey();
    }

}

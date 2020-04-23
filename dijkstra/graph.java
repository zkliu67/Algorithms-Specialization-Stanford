package dijkstra;

public class graph {
    private class node {
        private int key;
        private int dist;

        private node(int k) {
            key = k;
            dist = Integer.MAX_VALUE;
        }

        private int getKey() {return key;}
        private int getDist() {return dist;}
        private void updateDist(int newDist) {dist = newDist;}
    }

    private class edge {
        private int node;
        private int length;

        private edge(int n, int l) {
            node = n;
            length = l;
        }

        private int getNode() {return node;}
        private int getLength() {return length;}
    }

}

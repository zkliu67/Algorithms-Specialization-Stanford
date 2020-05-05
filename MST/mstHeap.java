package MST;
import heap.Heap;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class mstHeap {
    /**
     * Intitialize the structure of graph:
     * start vertex: {end vertex...}
     */
    private class Edge {
        private int start;
        private int end;
        private int cost;

        public Edge(int v1, int v2, int c) {
            start = v1;
            end = v2;
            cost = c;
        }

        public String toString() {
            return "v1: " + start + ", v2: " + end + ", c: " + cost;
        }
    }

    private class ResultSet {
        private int parent;
        private int cost;
    }

    private Map<Integer, ArrayList<Edge>> graph;
    private int numVertices;
    private int numEdges;

    public mstHeap() {
        graph = new HashMap<Integer, ArrayList<Edge>>();
        numVertices = 0;
        numEdges = 0;
    }

    public void loadGraph(String filename) throws IOException {

        String line = null;
        BufferedReader reader = new BufferedReader(new FileReader(filename));

        String[] strs = reader.readLine().split(" ");
        numVertices = Integer.parseInt(strs[0]);
        numEdges = Integer.parseInt(strs[1]);

        while ((line = reader.readLine()) != null) {
            Scanner scanner = new Scanner(line);
            int[] edge = new int[3];
            int i = 0;
            while (scanner.hasNextInt()) {
                edge[i++] = scanner.nextInt();
            }

            addVertex(edge[0], edge[1], edge[2]);
            addVertex(edge[1], edge[0], edge[2]);
        }
        reader.close();
    }

    private void addVertex(int start, int end, int cost) {
        Edge edge = new Edge(start, end, cost);

        if (graph.containsKey(start)) {
            graph.get(start).add(edge);
        } else {
            ArrayList<Edge> adjacency = new ArrayList<>();
            adjacency.add(edge);
            graph.put(start, adjacency);
        }
    }

    public void primMST() {

        HeapNode[] heapNodes = new HeapNode[numVertices+1]; // store all vertices as heap nodes
        boolean[] inHeap = new boolean[numVertices+1]; // initialize all vertices as not in MST
        ResultSet[] resultSet = new ResultSet[numVertices+1]; // store the resultant MST with its parent and value
        int[] keys = new int[numVertices+1]; // Store key values of each heapNodes

        // Initialize all vertices as HeapNodes and store information for fast access by index.
        graph.forEach((k, v) -> {
            heapNodes[k] = new HeapNode(k);
            resultSet[k] = new ResultSet();
            resultSet[k].parent = -1;
            inHeap[k] = true;
            keys[k] = Integer.MAX_VALUE;
        });

        // Update the beginning vertex with key value = 0
        heapNodes[1].updateKey(0);

        // Initialize the minHeap with key value
        MinHeap minHeap = new MinHeap(numVertices);
        for (HeapNode node : heapNodes) {
            if (node != null) {
                minHeap.Insert(node);
            }
        }

        while (!minHeap.isEmpty()) {
            HeapNode minEdge = minHeap.ExtractMin();
            int vertex = minEdge.getVertex();
            // Remove the min Vertex from V-X
            inHeap[vertex] = false;

            for (Edge edge : graph.get(vertex)) {
                if (inHeap[edge.end]) {
                    int end = edge.end;
                    int newKey = edge.cost;
                    // check if the key of adjacency vertex exists and update it with the
                    // smaller value.
                    if (keys[end] > newKey) {
                        decreaseKey(minHeap, end, newKey);
                        resultSet[end].parent = vertex;
                        resultSet[end].cost = newKey;
                        keys[end] = newKey;
                    }
                }
            }
        }
        printMST(resultSet);
    }

    private void decreaseKey(MinHeap minHeap, int vertex, int newKey) {
        minHeap.updateKey(vertex, newKey);
    }

    private void printMST(ResultSet[] resultSet) {
        int totalMinWeight = 0;
        for (int i = 0; i < resultSet.length; i++) {
            if (resultSet[i] != null) {
                //System.out.println("Edge: " + i + " - " + resultSet[i].parent +
                //        " weight: " + resultSet[i].cost);
                totalMinWeight += resultSet[i].cost;
            }
        }

        System.out.println("total min weight: " + totalMinWeight);
    }

    public static void main(String[] args) throws IOException {
        mstHeap MST= new mstHeap();
        MST.loadGraph("./MST.txt");
        MST.primMST();
    }

}


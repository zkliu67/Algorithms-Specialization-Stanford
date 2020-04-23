import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Dijkstra {

    /***
     * Constructe Class for Graph.
     * With Object node and edge.
     */
    private static class Node {
        private int key;
        private int dist;
        private int visited;

        private Node(int k) {
            key = k;
            visited = 0;
            dist = Integer.MAX_VALUE;
        }

        public int getKey() {return key;}
        public int getDist() {return dist;}

        public void visit() {visited ++;}

        public void updateDist(int newDist) {
            this.dist = newDist;
        }

        public boolean equals(Object n) {
            if (!(n instanceof Node)) {return false;}

            Node other = (Node) n;
            return this.key == other.key && this.visited == other.visited;
        }

        public int hashCode() {return
                new HashCodeBuilder(17, 31).append(key).append(dist).toHashCode();}

        public String toString() {return "Key: " + getKey();}
    }

    private class Edge {
        private Node node;
        private int length;

        private Edge(Node n, int l) {
            node = n;
            length = l;
        }

        public Node getNode() {
            return node;
        }

        public int getLength() {
            return length;
        }

        public boolean equals(Object e) {
            if (!(e instanceof Edge)) {return false;}
            Edge other = (Edge) e;

            return (other.getNode().equals(this.getNode()) && other.getLength() == this.getLength());
        }

        public int hashCode() {
            return node.hashCode() +
                    new HashCodeBuilder(17, 31).append(getLength()).toHashCode();
        }

        public String toString() {
            return node.toString() + ", length: " + getLength();
        }
    }

    private Map<Node, ArrayList<Edge>> graphMap;
    private int numVertices;

    /***
     * Constructor with external file;
     * Utilize Adjency List for graph representation.
     * @param filename
     * @throws IOException
     */
    public Dijkstra(String filename) throws IOException {
        graphMap = new HashMap<Node, ArrayList<Edge>>();
        numVertices = 0;

        try {
            String line;
            BufferedReader reader = new BufferedReader(new FileReader(filename));

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\t");
                if (parts.length == 1) {
                    graphMap.put(new Node(Integer.parseInt(parts[0])), new ArrayList<Edge>());
                    numVertices ++;
                }
                else if (parts.length >= 2) {
                    Node vertex = new Node(Integer.parseInt(parts[0]));
                    ArrayList<Edge> edges = new ArrayList<>();

                    for (int i = 1; i < parts.length; i++) {
                        String[] edge = parts[i].split(",");
                        if (edge.length >=2) {
                            Node n = new Node(Integer.parseInt(edge[0]));
                            Edge e = new Edge(n, Integer.parseInt(edge[1]));
                            edges.add(e);
                        }
                    }
                    graphMap.put(vertex, edges);
                    numVertices ++;

                }
                else {
                    System.out.println("Error Import line: " + line);
                }

            }
            System.out.println("Graph ready!");
            reader.close();
        } catch (Exception e) {
            throw new IOException(e);
        }
    }

    private Set<Node> getVertices() {
        return new HashSet<Node>(graphMap.keySet());
    }

    /***
     * Implementation of Dijkstra Algorithm
     * Storing the greedy dijkstra score with heap (Priority Queue)
     */
    public List<Node> dijkstra(Node start, Node goal) {

        if (!getVertices().contains(start) || !getVertices().contains(goal)) {return null;}

        int[] dists = dist();
        PriorityQueue<Node> pq = new PriorityQueue<Node>((n1, n2) -> dists[n1.getKey()] - dists[n2.getKey()]);
        List<Node> visited = new ArrayList<>();
        Map<Node, Node> parentMap = new HashMap<>();

        //start.updateDist(0);
        dists[start.getKey()] = 0;
        pq.add(start);

        while(!(pq.isEmpty())) {
            Node curr = pq.poll();
            if (!visited.contains(curr)) {
                visited.add(curr);
                if (curr.getKey() == goal.getKey()) {
                    System.out.println(dists[curr.getKey()]);
                    LinkedList<Node> shortestPath = new LinkedList<>();
                    return _reversePath(shortestPath, parentMap, curr);
                }
                if (graphMap.get(curr) != null) {
                    for (Edge edge:graphMap.get(curr)) {
                        Node n = edge.getNode();
                        int newLength = edge.getLength() + dists[curr.getKey()];

                        if (!parentMap.containsKey(n)) {
                            parentMap.put(n, curr);
                        }

                        if (newLength < dists[n.getKey()]) {
                            //n.updateDist(newLength);
                            dists[n.getKey()] = newLength;
                            parentMap.put(n, curr);
                        }
                        pq.add(n);
                    }
                }
            }
        }
        return null;
    }
    private int[] dist() {
        int[] dists = new int[numVertices + 1];
        Arrays.fill(dists, Integer.MAX_VALUE);
        return dists;
    }

    private List<Node> _reversePath(LinkedList<Node> path, Map<Node, Node> parent, Node goal) {
        if (parent.containsKey(goal)) {
            path.addFirst(goal);
            return _reversePath(path, parent, parent.get(goal));
        }
        path.addFirst(goal);
        return new ArrayList<Node>(path);
    }

    public static void main(String[] args) throws IOException {
        Dijkstra graph = new Dijkstra("dijkstra-test.txt");
        Node start = new Node(1);
        Node goal = new Node(4);
        System.out.println(graph.dijkstra(start, goal));
    }
}

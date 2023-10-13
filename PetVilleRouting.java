import java.util.*;

class Node {
    String name;
    double x, y;

    public Node(String name, double x, double y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }
}

class Edge {
    Node source, destination;
    double weight;

    public Edge(Node source, Node destination) {
        this.source = source;
        this.destination = destination;
        this.weight = calculateDistance(source, destination);
    }

    private double calculateDistance(Node node1, Node node2) {
        return Math.sqrt(Math.pow(node1.x - node2.x, 2) + Math.pow(node1.y - node2.y, 2));
    }
}

class Graph {
    Map<String, Node> nodes = new HashMap<>();
    List<Edge> edges = new ArrayList<>();

    public void addNode(String name, double x, double y) {
        nodes.put(name, new Node(name, x, y));
    }

    public void addEdge(String sourceName, String destinationName) {
        Node source = nodes.get(sourceName);
        Node destination = nodes.get(destinationName);
        edges.add(new Edge(source, destination));
    }
}

class Dijkstra {
    public static Map<Node, Double> findShortestPaths(Graph graph, String sourceName) {
        Map<Node, Double> distance = new HashMap<>();
        PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingDouble(distance::get));

        Node source = graph.nodes.get(sourceName);
        distance.put(source, 0.0);
        queue.offer(source);

        while (!queue.isEmpty()) {
            Node current = queue.poll();

            for (Edge edge : graph.edges) {
                if (edge.source == current) {
                    Node neighbor = edge.destination;
                    double newDist = distance.get(current) + edge.weight;

                    if (!distance.containsKey(neighbor) || newDist < distance.get(neighbor)) {
                        distance.put(neighbor, newDist);
                        queue.offer(neighbor);
                    }
                }
            }
        }

        return distance;
    }
}

public class PetVilleRouting {
    public static void main(String[] args) {
        Graph petvilleGraph = new Graph();

        // Tambahkan node peternakan
        petvilleGraph.addNode("PeternakanA", 0, 0);
        petvilleGraph.addNode("PeternakanB", 2, 1);
        petvilleGraph.addNode("PeternakanC", 3, -2);

        // Tambahkan node toko
        petvilleGraph.addNode("TokoX", 6, 0);
        petvilleGraph.addNode("TokoY", 5, -3);

        // Tambahkan edge
        petvilleGraph.addEdge("PeternakanA", "TokoY");
        petvilleGraph.addEdge("PeternakanB", "TokoY");
        petvilleGraph.addEdge("PeternakanC", "TokoY");

        // Tentukan sumber dan tujuan
        String sourcePeternakan = "PeternakanA";
        String targetToko = "TokoY";

        // Temukan rute terpendek
        Map<Node, Double> shortestPaths = Dijkstra.findShortestPaths(petvilleGraph, sourcePeternakan);

        // Tampilkan hasil
        System.out.println("Rute terpendek dari " + sourcePeternakan + " ke " + targetToko + ":");
        for (Node node : shortestPaths.keySet()) {
            System.out.println(node.name + ": " + shortestPaths.get(node));
        }
    }
}

package src.routing;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Node implements Comparable<Node> {

    private final String name;
    private Integer distance = Integer.MAX_VALUE;
    private List<Node> shortestPath = new LinkedList<>();
    private java.util.Map<Node, Integer> adjacentNodes = new HashMap<>();

    public Node(String name)

    public void addAdjacentnode(Node node, int weight) {
        adjacentNodes.put(node, weight);
    }

    @Override
    public int compareTo(Node node) {
        return Integer.compare(this.distance, node.getDistance());
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public void setShortestPath(List<Node> shortestPath) {
        this.shortestPath = shortestPath;
    }
    
    public List<Node> getShortestPath() {
        return shortestPath;
    }

    public java.util.Map<Node, Integer> getAdjacentNodes() {
        return adjacentNodes;
    }
}

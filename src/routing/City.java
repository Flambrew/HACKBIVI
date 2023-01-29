package src.routing;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class City implements Comparable<City> {

    private final String name;
    private Integer distance = Integer.MAX_VALUE;
    private List<City> shortestPath = new LinkedList<>();
    private java.util.Map<City, Integer> adjacentNodes = new HashMap<>();

    public void addAdjacentCity(City node, int weight) {
        adjacentNodes.put(node, weight);
    }

    public City(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(City node) {
        return Integer.compare(this.distance, node.getDistance());
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public void setShortestPath(List<City> shortestPath) {
        this.shortestPath = shortestPath;
    }

    public List<City> getShortestPath() {
        return shortestPath;
    }

    public java.util.Map<City, Integer> getAdjacentNodes() {
        return adjacentNodes;
    }
}

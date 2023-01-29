package src.routing;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Stream;

public class AStar {
    public void calculateShortestPath(City source) {
        source.setDistance(0);
        Set<City> settledNodes = new HashSet<>();
        Queue<City> unsettledNodes = new PriorityQueue<>(Collections.singleton(source));
        while (!unsettledNodes.isEmpty()) {
            City currentNode = unsettledNodes.poll();
            currentNode.getAdjacentNodes().entrySet().stream().filter(entry -> !settledNodes.contains(entry.getKey()))
                    .forEach(entry -> {
                        evaluateDistanceAndPath(entry.getKey(), entry.getValue(), currentNode);
                        unsettledNodes.add(entry.getKey());
                    });
            settledNodes.add(currentNode);
        }
    }

    private void evaluateDistanceAndPath(City adjacentNode, Integer edgeWeight, City sourceNode) {
        Integer newDistance = sourceNode.getDistance() + edgeWeight;
        if (newDistance < adjacentNode.getDistance()) {
            adjacentNode.setDistance(newDistance);
            adjacentNode.setShortestPath(Arrays.asList(
                    Stream.concat(sourceNode.getShortestPath().stream(), Stream.of(sourceNode)).toArray(City[]::new)));
        }
    }
}
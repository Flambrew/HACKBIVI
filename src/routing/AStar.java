package src.routing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class AStar {
    public static void Astar(Map map, Location start, Location end) {
        int[] dists = new int[map.getNames().size()]
        for (int i : dists) 
            i = Integer.MAX_VALUE;

        

    }


    public void calculateShortestPath(Node source) {
        source.setDistance(0);
        Set<Node> settlesNodes = new HashSet<>();
        Queue<Node> unsettledNodes = new PriorityQueue<>(Collections.singleton(source));
        while (!unsettledNodes.isEmpty()) {
            Node currentNode = unsettledNodes.poll();
            currentNode.getAdjacentNodes().entrySet().stream().filter(entry -> !settledNodes.contains(entry.getKey())).forEach(entry -> {
                evaluateDistanceAndPath
            });
        }
    }







    public double findPotential(double x, double y, double ex, double ey) {
        return Math.sqrt(Math.pow(x - ex, 2) + Math.pow(y - ey, 2));
    }
}

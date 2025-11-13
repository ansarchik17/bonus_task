package org.example;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("!Design and analysis of algorithms bonus task");
        System.out.println();
        List<Edge> edges = Arrays.asList(
                new Edge(0, 1, 2),
                new Edge(0, 3, 6),
                new Edge(1, 2, 3),
                new Edge(1, 3, 8),
                new Edge(1, 4, 5),
                new Edge(2, 4, 7),
                new Edge(3, 4, 9)
        );

        Graph graph = new Graph(5, edges);

        List<Edge> mst = graph.buildMST();
        graph.displayMST("Initial MST:");

        int edgeToRemoveIndex = -1;
        for (int i = 0; i < mst.size(); i++) {
            if (mst.get(i).w == 3) {
                edgeToRemoveIndex = i;
                break;
            }
        }

        if (edgeToRemoveIndex == -1) {
            System.out.println("No edge with weight 3 found in MST!");
            return;
        }

        Edge removedEdge = graph.removeEdge(edgeToRemoveIndex);

        Set<Integer>[] components = graph.findComponents(removedEdge.u, removedEdge.v);
        System.out.println("Component 1: " + components[0]);
        System.out.println("Component 2: " + components[1] + "\n");

        Edge replacement = graph.findReplacementEdge(components);
        if (replacement != null) {
            graph.addEdgeToMST(replacement);
            graph.displayMST("New MST after reconnection:");
        } else {
            System.out.println("No valid replacement edge found.");
        }

        System.out.println("Task completed well!");
    }
}
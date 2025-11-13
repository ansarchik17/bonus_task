package org.example;

import java.util.*;

/**
 * Main class demonstrating MST edge removal and reconnection.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("=".repeat(50));
        System.out.println("  DAA BONUS TASK: EDGE REMOVAL FROM MST");
        System.out.println("=".repeat(50) + "\n");

        // Define the graph: 5 vertices, 7 edges
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

        // Step 1: Build and display initial MST
        List<Edge> mst = graph.buildMST();
        graph.displayMST("INITIAL MST:");

        // Find edge with weight 3 to remove
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

        // Step 2: Remove the edge
        Edge removedEdge = graph.removeEdge(edgeToRemoveIndex);

        // Step 3: Find components
        Set<Integer>[] components = graph.findComponents(removedEdge.u, removedEdge.v);
        System.out.println("COMPONENT 1: " + components[0]);
        System.out.println("COMPONENT 2: " + components[1] + "\n");

        // Step 4: Find replacement edge
        Edge replacement = graph.findReplacementEdge(components);
        if (replacement != null) {
            graph.addEdgeToMST(replacement);
            graph.displayMST("NEW MST AFTER RECONNECTION:");
        } else {
            System.out.println("No valid replacement edge found.");
        }

        System.out.println("=".repeat(50));
        System.out.println("  TASK COMPLETED");
        System.out.println("=".repeat(50));
    }
}
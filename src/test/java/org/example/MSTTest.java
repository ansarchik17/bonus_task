package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

class MSTTest {

    @Test
    void testMSTConstruction() {
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

        // MST should have V-1 = 4 edges
        assertEquals(4, mst.size(), "MST should have 4 edges for 5 vertices");

        // Calculate total weight
        int totalWeight = mst.stream().mapToInt(e -> e.w).sum();
        assertEquals(16, totalWeight, "MST total weight should be 16");
    }

    @Test
    void testEdgeRemovalAndReplacement() {
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
        graph.buildMST();

        // Find the edge with weight 3 (1-2)
        List<Edge> mst = graph.getCurrentMST();
        int edgeIndex = -1;
        for (int i = 0; i < mst.size(); i++) {
            if (mst.get(i).w == 3) {
                edgeIndex = i;
                break;
            }
        }

        assertTrue(edgeIndex != -1, "Should find edge with weight 3");

        // Remove the edge
        Edge removed = graph.removeEdge(edgeIndex);
        assertEquals(3, removed.w, "Removed edge should have weight 3");

        // Find components
        var components = graph.findComponents(removed.u, removed.v);
        assertTrue(components[0].size() > 0, "Component 1 should not be empty");
        assertTrue(components[1].size() > 0, "Component 2 should not be empty");
        assertEquals(5, components[0].size() + components[1].size(), "All vertices should be in components");

        // Find replacement edge
        Edge replacement = graph.findReplacementEdge(components);
        assertNotNull(replacement, "Should find a replacement edge");

        // The replacement should be edge 2-4 with weight 7 (NOT the removed edge 1-2:3)
        assertEquals(7, replacement.w, "Replacement edge weight should be 7");
        assertTrue(
                (replacement.u == 2 && replacement.v == 4) || (replacement.u == 4 && replacement.v == 2),
                "Replacement edge should connect vertices 2 and 4"
        );

        // Add replacement edge
        graph.addEdgeToMST(replacement);
        List<Edge> newMst = graph.getCurrentMST();
        assertEquals(4, newMst.size(), "MST should still have 4 edges");

        int newWeight = newMst.stream().mapToInt(e -> e.w).sum();
        assertEquals(20, newWeight, "New MST weight should be 20 (2+5+6+7)");
    }
}
package org.example;

import java.util.*;

public class Graph {
    private final int V;                    // Number of vertices
    private final List<Edge> allEdges;      // All original edges
    private List<Edge> mst;                 // Current MST edges
    private DisjointSet uf;                 // Union-Find for connectivity
    private Edge removedEdge;               // Track the removed edge

    public Graph(int V, List<Edge> edges) {
        this.V = V;
        this.allEdges = new ArrayList<>(edges);
        this.mst = new ArrayList<>();
        this.uf = new DisjointSet(V);
        this.removedEdge = null;
    }

    /**
     * Builds MST using Kruskal's algorithm.
     */
    public List<Edge> buildMST() {
        // Reset MST and Union-Find
        mst.clear();
        this.uf = new DisjointSet(V);
        this.removedEdge = null;

        // Sort edges by weight
        List<Edge> sortedEdges = new ArrayList<>(allEdges);
        Collections.sort(sortedEdges);

        // Build MST
        for (Edge edge : sortedEdges) {
            if (uf.union(edge.u, edge.v)) {
                mst.add(edge);
                if (mst.size() == V - 1) break; // MST complete
            }
        }

        return new ArrayList<>(mst);
    }

    /**
     * Displays current MST.
     */
    public void displayMST(String title) {
        System.out.println(title);
        int totalWeight = 0;
        for (Edge edge : mst) {
            System.out.println("  " + edge);
            totalWeight += edge.w;
        }
        System.out.println("  Total weight: " + totalWeight);
        System.out.println("  Number of edges: " + mst.size() + "\n");
    }

    /**
     * Removes edge from MST by index and returns it.
     */
    public Edge removeEdge(int index) {
        if (index < 0 || index >= mst.size()) {
            throw new IllegalArgumentException("Invalid edge index: " + index);
        }
        removedEdge = mst.remove(index);
        System.out.println("Removed edge: " + removedEdge + "\n");
        return removedEdge;
    }

    /**
     * Finds two components after edge removal.
     */
    public Set<Integer>[] findComponents(int u, int v) {
        // Build adjacency list from current MST
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < V; i++) {
            adj.add(new ArrayList<>());
        }
        for (Edge edge : mst) {
            adj.get(edge.u).add(edge.v);
            adj.get(edge.v).add(edge.u);
        }

        boolean[] visited = new boolean[V];
        Set<Integer> component1 = new HashSet<>();
        Set<Integer> component2 = new HashSet<>();

        // Perform DFS from node u
        dfs(adj, u, visited, component1);

        // Find first unvisited node for second component
        int startNode2 = -1;
        for (int i = 0; i < V; i++) {
            if (!visited[i]) {
                startNode2 = i;
                break;
            }
        }

        if (startNode2 != -1) {
            dfs(adj, startNode2, visited, component2);
        }

        System.out.println("Component 1: " + component1);
        System.out.println("Component 2: " + component2);

        @SuppressWarnings("unchecked")
        Set<Integer>[] components = new Set[]{component1, component2};
        return components;
    }

    private void dfs(List<List<Integer>> adj, int node, boolean[] visited, Set<Integer> component) {
        Stack<Integer> stack = new Stack<>();
        stack.push(node);

        while (!stack.isEmpty()) {
            int current = stack.pop();
            if (!visited[current]) {
                visited[current] = true;
                component.add(current);
                for (int neighbor : adj.get(current)) {
                    if (!visited[neighbor]) {
                        stack.push(neighbor);
                    }
                }
            }
        }
    }

    /**
     * Finds the minimum weight edge that reconnects the two components.
     * Excludes edges that are in current MST and the removed edge.
     */
    public Edge findReplacementEdge(Set<Integer>[] components) {
        Set<Integer> comp1 = components[0];
        Set<Integer> comp2 = components[1];

        Edge bestEdge = null;
        int minWeight = Integer.MAX_VALUE;

        System.out.println("Looking for replacement edges...");

        // Check all original edges
        for (Edge edge : allEdges) {
            // Skip edges that are in current MST
            if (isEdgeInMST(edge)) {
                System.out.println("  Skipping (in MST): " + edge);
                continue;
            }

            // Skip the removed edge specifically
            if (removedEdge != null && isSameEdge(edge, removedEdge)) {
                System.out.println("  Skipping (removed): " + edge);
                continue;
            }

            // Check if edge connects the two components
            boolean uInComp1 = comp1.contains(edge.u);
            boolean vInComp1 = comp1.contains(edge.v);
            boolean uInComp2 = comp2.contains(edge.u);
            boolean vInComp2 = comp2.contains(edge.v);

            // Edge connects components if one endpoint in comp1 and other in comp2
            boolean connectsComponents = (uInComp1 && vInComp2) || (uInComp2 && vInComp1);

            if (connectsComponents) {
                System.out.println("  Candidate: " + edge + " (weight: " + edge.w + ")");
                if (edge.w < minWeight) {
                    minWeight = edge.w;
                    bestEdge = edge;
                    System.out.println("    -> New best candidate!");
                }
            }
        }

        System.out.println("Best replacement edge: " + bestEdge);
        return bestEdge;
    }

    /**
     * Checks if an edge is in the current MST
     */
    private boolean isEdgeInMST(Edge edge) {
        for (Edge mstEdge : mst) {
            if (isSameEdge(edge, mstEdge)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if two edges are the same (undirected)
     */
    private boolean isSameEdge(Edge e1, Edge e2) {
        return (e1.u == e2.u && e1.v == e2.v && e1.w == e2.w) ||
                (e1.u == e2.v && e1.v == e2.u && e1.w == e2.w);
    }

    /**
     * Adds a new edge to the MST.
     */
    public void addEdgeToMST(Edge edge) {
        if (edge != null) {
            mst.add(edge);
            System.out.println("Added replacement edge: " + edge + "\n");
        }
    }

    public List<Edge> getCurrentMST() {
        return new ArrayList<>(mst);
    }

    public int getMSTWeight() {
        return mst.stream().mapToInt(e -> e.w).sum();
    }
}
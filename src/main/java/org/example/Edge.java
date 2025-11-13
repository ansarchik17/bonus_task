package org.example;

import java.util.Objects;

public class Edge implements Comparable<Edge> {
    public final int u;  // source vertex
    public final int v;  // destination vertex
    public final int w;  // weight

    public Edge(int u, int v, int w) {
        this.u = u;
        this.v = v;
        this.w = w;
    }

    @Override
    public int compareTo(Edge other) {
        return Integer.compare(this.w, other.w);
    }

    @Override
    public String toString() {
        return "(" + u + "—" + v + ": " + w + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Edge edge = (Edge) obj;
        return u == edge.u && v == edge.v && w == edge.w;
    }

    @Override
    public int hashCode() {
        return Objects.hash(u, v, w);
    }
}
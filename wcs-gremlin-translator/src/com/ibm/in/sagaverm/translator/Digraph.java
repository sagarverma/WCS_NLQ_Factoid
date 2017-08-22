package com.ibm.in.sagaverm.translator;

import java.util.*;

public class Digraph<V> {

    public static class Edge<V>{
        private V vertex;
        private String edgeLabel;

        public Edge(V v, String eL){
            vertex = v; edgeLabel = eL;
        }

        public V getVertex() {
            return vertex;
        }

        public String getEdgeLabel() {
            return edgeLabel;
        }

        @Override
        public String toString() {
            return "Edge [vertex=" + vertex + ", edgeLabel=" + edgeLabel + "]";
        }

    }

    /**
     * A Map is used to map each vertex to its list of adjacent vertices.
     */

    Map<V, List<Edge<V>>> neighbors = new HashMap<V, List<Edge<V>>>();

    /**
     * String representation of graph.
     */
    public String toString() {
        StringBuffer s = new StringBuffer();
        for (V v : neighbors.keySet())
            s.append("\n    " + v + " -> " + neighbors.get(v));
        return s.toString();
    }

    /**
     * Add a vertex to the graph. Nothing happens if vertex is already in graph.
     */
    public void add(V vertex) {
        if (neighbors.containsKey(vertex))
            return;
        neighbors.put(vertex, new ArrayList<Edge<V>>());
    }

    public int getNumberOfEdges(){
        int sum = 0;
        for(List<Edge<V>> outBounds : neighbors.values()){
            sum += outBounds.size();
        }
        return sum;
    }

    /**
     * True iff graph contains vertex.
     */
    public boolean contains(V vertex) {
        return neighbors.containsKey(vertex);
    }

    /**
     * Add an edge to the graph; if either vertex does not exist, it's added.
     * This implementation allows the creation of multi-edges and self-loops.
     */
    public void add(V from, V to, String edgeLabel) {
        this.add(from);
        this.add(to);
        neighbors.get(from).add(new Edge<V>(to, edgeLabel));
    }

    public int outDegree(int vertex) {
        return neighbors.get(vertex).size();
    }

    public int inDegree(V vertex) {
       return inboundNeighbors(vertex).size();
    }

    public List<V> outboundNeighbors(V vertex) {
        List<V> list = new ArrayList<V>();
        for(Edge<V> e: neighbors.get(vertex))
            list.add(e.vertex);
        return list;
    }

    public List<V> inboundNeighbors(V inboundVertex) {
        List<V> inList = new ArrayList<V>();
        for (V to : neighbors.keySet()) {
            for (Edge e : neighbors.get(to))
                if (e.vertex.equals(inboundVertex))
                    inList.add(to);
        }
        return inList;
    }

    public boolean isEdge(V from, V to) {
      for(Edge<V> e :  neighbors.get(from)){
          if(e.vertex.equals(to))
              return true;
      }
      return false;
    }

    public String getEdgeLabel(V from, V to) {
        for(Edge<V> e :  neighbors.get(from)){
            if(e.vertex.equals(to))
                return e.edgeLabel;
        }
        return null;
    }

}
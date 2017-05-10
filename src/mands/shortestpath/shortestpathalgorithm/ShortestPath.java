package mands.shortestpath.shortestpathalgorithm;

import mands.exceptions.InvalidDataException;
import mands.shortestpath.buildlinks.TransportLink;
import mands.shortestpath.loadxml.LoadNetworkLinks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.TreeMap;
import java.util.Map;
import java.util.NavigableSet;
import java.util.TreeSet;

/**
 * ShortestPath receives the network and build a graph
 * Dijkstra's Algorithm is run on the graph to find the shortest path between two facilities
 *
 */
public class ShortestPath {

    private static ArrayList path = new ArrayList();
    private static String startVertex;
    private static String endVertex;
    private static int totalDistance;
    private HashMap<String, Vertex> graph;
    private TreeMap<Integer, TransportLink> network;

    private void setStartFacility(String startFacilityIn) throws InvalidDataException {
        if (startFacilityIn == null || startFacilityIn.isEmpty()) {
            throw new InvalidDataException(startFacilityIn == null ? "ShortestPath.setStartFacility(string); string is null"
                    : "ShortestPath.setStartFacility(string); string is empty");
        }
        startVertex = startFacilityIn;
    }

    private void setEndFacility(String endFacilityIn) throws InvalidDataException {
        if (endFacilityIn == null || endFacilityIn.isEmpty()) {
            throw new InvalidDataException(endFacilityIn == null ? "ShortestPath.setEndFacility(string); string is null"
                    : "ShortestPath.setEndFacility(string); string is empty");
        }
        endVertex = endFacilityIn;
    }

    public ShortestPath(String startFacilityIn, String endFacilityIn) throws InvalidDataException {
        setStartFacility(startFacilityIn);
        setEndFacility(endFacilityIn);
        findShortestPath();
    }

    private class Vertex implements Comparable<Vertex>{

        private String currentVertex;
        private int distance = Integer.MAX_VALUE; // set to infinity
        private Vertex previousVertex = null;
        private HashMap<Vertex, Integer> adjacentVertices = new HashMap<>();

        private Vertex(String currentVertexIn){
            currentVertex = currentVertexIn;
        }

        private void buildPath() throws InvalidDataException {

            if (this == previousVertex){
                path.add(currentVertex);
            }

            else if (previousVertex == null){
                throw new InvalidDataException("ShortestPath.Vertex.buildPath; facility " + currentVertex + " could not be reached in the network");
            }

            else{
                if (Objects.equals(currentVertex, endVertex)){
                    totalDistance = distance;
                }

                previousVertex.buildPath();
                path.add(currentVertex);
            }
        }

        public int compareTo(Vertex vertex){
            if (distance == vertex.distance) {
                return currentVertex.compareTo(vertex.currentVertex);
            }

            return Integer.compare(distance, vertex.distance);
        }

        public String toString(){return currentVertex;}
    }

    //Create the transportation network for Dijkstra's by populating the graph
    private ShortestPath(TreeMap<Integer, TransportLink> links){

        graph = new HashMap<>();

        for (Map.Entry<Integer, TransportLink> entry : links.entrySet()) {
            TransportLink value = entry.getValue();
            if (!graph.containsKey(value.getFacility()))
                graph.put(value.getFacility(), new Vertex(value.getFacility()));
            if (!graph.containsKey(value.getNeighbor()))
                graph.put(value.getNeighbor(), new Vertex(value.getNeighbor()));
            graph.get(value.getFacility()).adjacentVertices.put(graph.get(value.getNeighbor()), value.getDistance());
        }
    }

    private void dijkstrasAlgorithm(String startVertex) throws InvalidDataException {
        if (!graph.containsKey(startVertex)){
            throw new InvalidDataException("ShortestPath.dijkstrasAlgorithm; The network does not contain the start facility " + startVertex);
        }

        Vertex start = graph.get(startVertex);
        NavigableSet<Vertex> queue = new TreeSet<>();

        for (Vertex vertex : graph.values()){
            vertex.previousVertex = vertex == start ? start : null;
            vertex.distance = vertex == start ? 0 : Integer.MAX_VALUE;
            queue.add(vertex);
        }

        dijkstrasAlgorithm(queue);
    }

    //Heap for Dijkstra's
    private void dijkstrasAlgorithm(NavigableSet<Vertex> queue){

        Vertex u, v;

        while (!queue.isEmpty()){
            u = queue.pollFirst();

            //look at distances to each adjacent vertex
            for (HashMap.Entry<Vertex, Integer> a : u.adjacentVertices.entrySet()){
                v = a.getKey();

                int newDistance = u.distance + a.getValue();
                if (newDistance < v.distance){

                    queue.remove(v);
                    v.distance = newDistance;
                    v.previousVertex = u;
                    queue.add(v);
                }
            }
        }
    }

    private void buildPath(String endVertex) throws InvalidDataException {
        if (!graph.containsKey(endVertex)){
            throw new InvalidDataException("ShortestPath.buildPath; The network does not contain the end facility " + endVertex);
        }

        graph.get(endVertex).buildPath();
    }

    private void findShortestPath() throws InvalidDataException {
        network = LoadNetworkLinks.getInstance().getNetwork();
        ShortestPath graph = new ShortestPath(network);
        graph.dijkstrasAlgorithm(startVertex);
        graph.buildPath(endVertex);
    }

    public ArrayList getPath(){
        return path;
    }

    public static int getDistance(){
        return totalDistance;
    }
}



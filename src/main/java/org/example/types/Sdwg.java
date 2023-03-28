package org.example.types;

import org.example.interfaces.GenericIteratorInterface;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import org.jgrapht.traverse.BreadthFirstIterator;
import org.jgrapht.traverse.DepthFirstIterator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Sdwg implements GenericIteratorInterface {

    private SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> g;

    @Override
    public void readGraph(String fileInputPath) {
        BufferedReader reader;
        g = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
        try {
            reader = new BufferedReader(new FileReader(fileInputPath));
            String line = reader.readLine();

            while (line != null) {
                String[] tmp = line.split(" ");
                List<String> lineValues = new ArrayList<>(Arrays.asList(tmp));
                String vertex1 = lineValues.get(0);
                String vertex2 = lineValues.get(1);
                int edgeWeight = Integer.parseInt(lineValues.get(2));
                if (!g.containsVertex(vertex1)) {
                    g.addVertex(vertex1);
                }
                if (!g.containsVertex(vertex2)) {
                    g.addVertex(vertex2);
                }
                DefaultWeightedEdge e = g.addEdge(vertex1, vertex2);
                g.setEdgeWeight(e, edgeWeight);

                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(g);
    }

    @Override
    public void getBFS(String startingVertex) {
        System.out.println();
        BreadthFirstIterator b = new BreadthFirstIterator(g, startingVertex);
        System.out.println("BFS of directed, weighted graph (ignoring weights):");
        while (b.hasNext()) {
            String parent = b.next().toString();
            System.out.print(parent + " ");
        }
    }

    @Override
    public void getDFS(String startingVertex) {
        System.out.println();
        DepthFirstIterator d = new DepthFirstIterator(g, startingVertex);
        System.out.println("DFS of directed, weighted graph (ignoring weights):");
        while (d.hasNext()) {
            String parent = d.next().toString();
            System.out.print(parent + " ");
        }
    }

    @Override
    public void getLexBFS() {
    }

    @Override
    public void getAstar(String sourceStarVertex, String sinkStarVertex) {
        DijkstraShortestPath a = new DijkstraShortestPath(g);
        System.out.printf("Using A*, shortest path between %s and %s is:%n", sourceStarVertex, sinkStarVertex);
        System.out.println(a.getPath(sourceStarVertex, sinkStarVertex));
        System.out.printf("Using A*, weight of shortest path between %s and %s is:%n", sourceStarVertex, sinkStarVertex);
        System.out.println(a.getPathWeight(sourceStarVertex, sinkStarVertex));
    }

    @Override
    public void getAstarAllShortestPaths(String sourceStarVertex) {
    }

    @Override
    public void getDijkstra(String sourceVertex, String sinkVertex) {
        DijkstraShortestPath dj = new DijkstraShortestPath(g);
        System.out.printf("Using Djikstra, shortest path between %s and %s is:%n", sourceVertex, sinkVertex);
        System.out.println(dj.getPath(sourceVertex, sinkVertex).getEdgeList());

        System.out.printf("Using Djikstra, weight of the shortest path between %s and %s is:%n%n", sourceVertex, sinkVertex);
        System.out.println(dj.getPathWeight(sourceVertex, sinkVertex));
    }
}

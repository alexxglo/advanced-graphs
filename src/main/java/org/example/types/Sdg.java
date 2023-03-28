package org.example.types;

import org.example.interfaces.GenericIteratorInterface;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.*;
import org.jgrapht.traverse.BreadthFirstIterator;
import org.jgrapht.traverse.DepthFirstIterator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Sdg implements GenericIteratorInterface {
    SimpleDirectedGraph<String, DefaultEdge> g;
    @Override
    public void readGraph(String fileInputPath) {
        BufferedReader reader;
        g = new SimpleDirectedGraph<>(DefaultEdge.class);
        try {
            reader = new BufferedReader(new FileReader(fileInputPath));
            String line = reader.readLine();

            while (line != null) {
                String[] tmp = line.split(" ");
                List<String> lineValues = new ArrayList<>(Arrays.asList(tmp));
                String vertex1 = lineValues.get(0);
                String vertex2 = lineValues.get(1);
                if (!g.containsVertex(vertex1)) {
                    g.addVertex(vertex1);
                }
                if (!g.containsVertex(vertex2)) {
                    g.addVertex(vertex2);
                }
                g.addEdge(vertex1, vertex2);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getBFS(String startingVertex) {
        System.out.println();
        BreadthFirstIterator b = new BreadthFirstIterator(g, startingVertex);
        System.out.println("BFS of directed graph:");
        while (b.hasNext()) {
            String parent = b.next().toString();
            System.out.print(parent + " ");
        }
    }

    @Override
    public void getDFS(String startingVertex) {
        System.out.println();
        DepthFirstIterator d = new DepthFirstIterator(g, startingVertex);
        System.out.println("DFS of directed graph:");
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
    }

    @Override
    public void getAstarAllShortestPaths(String sourceStarVertex) {
        DijkstraShortestPath djs = new DijkstraShortestPath(g);
        System.out.printf("Using A* algorithm, all paths to %s and are:%n", sourceStarVertex);
        System.out.println(djs.getPaths(sourceStarVertex).getGraph());
    }

    @Override
    public void getDijkstra(String sourceVertex, String sinkVertex) {
        DijkstraShortestPath dj = new DijkstraShortestPath(g);
        System.out.printf("Using Djikstra, shortest path between %s and %s is:%n", sourceVertex, sinkVertex);
        System.out.println(dj.getPath(sourceVertex, sinkVertex).getGraph());
    }
}

package org.example;

import org.example.interfaces.GenericIteratorInterface;
import org.example.types.GenericGraph;
import org.example.types.Sdg;
import org.example.types.Sdwg;
import org.example.types.Swg;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        GenericGraph g = new GenericGraph();
        g.readGraph("src/main/resources/gg/facebook_combined.txt");
        String demoVertex = "1";

        g.showGraphFunctionalities(demoVertex);
        g.writeEdgeSet("src/main/resources/gg/generated_edge_set.txt");
        g.getMaxDegree();
        g.getMaxDegreeOfVertex();
        g.removeEdge(demoVertex, "88");
        g.removeVertex(demoVertex);
        g.createAdjacencyMatrix("src/main/resources/gg/adjacency_matrix.txt");
        g.contraction(demoVertex, "48");

        Scanner scanner = new Scanner(System.in);
        System.out.print("Weighted graph value:");
        boolean weightedGraph = scanner.nextBoolean();
        System.out.print("Directed graph value:");
        boolean directedGraph = scanner.nextBoolean();

        if (weightedGraph && directedGraph) {
            System.out.println("You have chosen Simple Directed Weighted Graph");
            Sdwg sdwg = new Sdwg();
            sdwg.readGraph("src/main/resources/sdwg/sdwg_example_graph.txt");
            callGraphTranversalMethods(sdwg, true, true, scanner);
        }
        else if (weightedGraph) {
            System.out.println("You have chosen Simple Undirected Weighted Graph");
            Swg swg = new Swg();
            swg.readGraph("src/main/resources/swg/swg_example_graph.txt");
            callGraphTranversalMethods(swg, false, true, scanner);
        }
        else if (directedGraph) {
            System.out.println("You have chosen Simple Directed Graph");
            Sdg sdg = new Sdg();
            sdg.readGraph("src/main/resources/sdg/basic_graph.txt");
            callGraphTranversalMethods(sdg, true, false, scanner);
        }
        else {
            System.out.println("You have chosen Simple Undirected Graph");
            GenericGraph gg = new GenericGraph();
            gg.readGraph("src/main/resources/gg/facebook_combined.txt");
            callGraphTranversalMethods(gg, false, false, scanner);
        }
    }
    public static void callGraphTranversalMethods(GenericIteratorInterface g, boolean directedGraph, boolean weightedGraph, Scanner scanner) {
        g.getBFS("3");
        g.getDFS("2");
        if(!directedGraph) {
            g.getLexBFS();
        }
        System.out.println();
        System.out.print("For dijkstra shortest path, input source vertex: ");
        String sourceVertex = scanner.next();
        System.out.print("For dijkstra shortest path, input sink vertex: ");
        String sinkVertex = scanner.next();
        g.getDijkstra(sourceVertex, sinkVertex);

        System.out.print("For A* shortest path, input source vertex: ");
        String sourceStarVertex = scanner.next();
        System.out.print("For A* shortest path, input sink vertex: ");
        String sinkStarVertex = scanner.next();


        g.getAstar(sourceStarVertex, sinkStarVertex);
        g.getAstarAllShortestPaths(sourceStarVertex);


    }
}
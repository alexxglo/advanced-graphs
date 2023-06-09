package org.example.types;

import org.apache.commons.lang3.StringUtils;
import org.example.interfaces.GenericIteratorInterface;
import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.event.TraversalListenerAdapter;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.nio.matrix.MatrixExporter;
import org.jgrapht.traverse.BreadthFirstIterator;
import org.jgrapht.traverse.DepthFirstIterator;
import org.jgrapht.traverse.LexBreadthFirstIterator;

import java.io.*;
import java.util.*;


public class GenericGraph implements GenericIteratorInterface {
    private Graph<String, DefaultEdge> g;

    public void readGraph(String inputFilePath) {
        BufferedReader reader;
        g = new SimpleGraph<>(DefaultEdge.class);
        try {
            reader = new BufferedReader(new FileReader(inputFilePath));
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

    public void showGraphFunctionalities(String demoVertex) {
        System.out.printf("Vecinii varfului %s: %n", demoVertex);

        System.out.println(Graphs.neighborListOf(g, demoVertex));

        System.out.println("Numarul de varfuri ale grafului: " + g.vertexSet().size());

        System.out.println("Numarul de muchii ale grafului: " + g.edgeSet().size());

        System.out.println(String.format("Gradul varfului %s: ", demoVertex) + g.degreeOf(demoVertex));

    }

    public String getMaxDegree() {
        Map<String, Integer> mappedDegreesOfVertexes = new HashMap<>();
        for (String vertex : g.vertexSet()) {
            mappedDegreesOfVertexes.put(vertex, g.degreeOf(vertex));
        }
        return mappedDegreesOfVertexes.entrySet().stream().max((vertex1, vertex2) -> vertex1.getValue() > vertex2.getValue() ? 1 : -1).get().getKey();
    }

    public void getMaxDegreeOfVertex() {
        String maxDegreeVertex = getMaxDegree();
        System.out.printf("Gradul maxim este %s cu gradul %d%n", maxDegreeVertex, g.degreeOf(maxDegreeVertex));
    }

    public void removeVertex(String vertex) {
        Graph<String, DefaultEdge> gClone = new SimpleGraph<>(DefaultEdge.class);
        Graphs.addGraph(gClone, g);
        System.out.printf("Removing vertex %s%n", vertex);
        gClone.removeVertex(vertex);
        writeModifiedEdgeSet(gClone, "src/main/resources/gg/edge_set_removed_vertex.txt");
    }

    public void removeEdge(String vertex, String vertex2) {
        Graph<String, DefaultEdge> gClone = new SimpleGraph<>(DefaultEdge.class);
        Graphs.addGraph(gClone, g);
        System.out.printf("Removing edge (%s : %s)%n%n", "0", vertex2);
        gClone.removeEdge(vertex, vertex2);
        writeModifiedEdgeSet(gClone, "src/main/resources/gg/edge_set_removed_edge.txt");
    }

    public void writeModifiedEdgeSet(Graph<String, DefaultEdge> grp, String outputFilePath) {
        try {
            FileWriter fileWriter = new FileWriter(outputFilePath);
            Set<DefaultEdge> edgeSet = grp.edgeSet();
            fileWriter.write("Lista de muchii: \n");
            for (DefaultEdge e : edgeSet) {
                fileWriter.write(e + "\n");
            }
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void writeEdgeSet(String outputFilePath) {
        try {
            FileWriter fileWriter = new FileWriter(outputFilePath);
            Set<DefaultEdge> edgeSet = g.edgeSet();
            fileWriter.write("Lista de muchii: \n");
            for (DefaultEdge e : edgeSet) {
                fileWriter.write(e + "\n");
            }
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void createAdjacencyMatrix(String filePath) {
        MatrixExporter matrixExporter = new MatrixExporter();
        matrixExporter.setFormat(MatrixExporter.Format.SPARSE_ADJACENCY_MATRIX);
        matrixExporter.exportGraph(g, new File(filePath));
    }

    public void contraction(String initialVertex, String targetVertex) {
        Graph<String, DefaultEdge> gContracted = new SimpleGraph<>(DefaultEdge.class);
        Graphs.addGraph(gContracted, g);
        List<DefaultEdge> initialVertexEdgeList = gContracted.edgeSet().stream().filter(v -> v.toString().startsWith("(" + initialVertex + " : ")).toList();
        List<String> connectedVertices = new ArrayList<>();
        for (DefaultEdge e : initialVertexEdgeList) {
            String connectedVertex = StringUtils.chop(e.toString().split("\\(1 : ")[1]);
            connectedVertices.add(connectedVertex);
            gContracted.removeEdge(e);
        }
        gContracted.removeVertex(initialVertex);
        for (String v : connectedVertices) {
            if (!gContracted.containsEdge(v, targetVertex) && !Objects.equals(targetVertex, v)) {
                gContracted.addEdge(targetVertex, v);
            }
        }
        writeModifiedEdgeSet(gContracted, "src/main/resources/gg/contracted_" + initialVertex + "to" + targetVertex + "_edge_set");
    }

    @Override
    public void getBFS(String startingVertex) {
        System.out.println();
        BreadthFirstIterator b = new BreadthFirstIterator(g, startingVertex);
        System.out.println("BFS of undirected graph:");
        while (b.hasNext()) {
            String parent = b.next().toString();
            System.out.print(parent + " ");
        }
    }

    @Override
    public void getDFS(String startingVertex) {
        System.out.println();
        DepthFirstIterator d = new DepthFirstIterator(g, startingVertex);
        System.out.println("DFS of undirected graph:");
        while (d.hasNext()) {
            String parent = d.next().toString();
            System.out.print(parent + " ");
        }
    }

    @Override
    public void getLexBFS() {
        System.out.println();
        LexBreadthFirstIterator l = new LexBreadthFirstIterator(g);
        System.out.println("LexBFS of undirected graph:");
        while (l.hasNext()) {
            String parent = l.next().toString();
            System.out.print(parent + " ");
        }
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
        System.out.printf("Using A* algorithm, all paths between %s and are:%n", sourceStarVertex);
        System.out.println(djs.getPaths(sourceStarVertex).getGraph());
    }

    @Override
    public void getDijkstra(String sourceVertex, String sinkVertex) {
        DijkstraShortestPath dj = new DijkstraShortestPath(g);
        System.out.printf("Using Djikstra, shortest path between %s and %s is:%n", sourceVertex, sinkVertex);
        System.out.println(dj.getPath(sourceVertex, sinkVertex).getEdgeList());
    }
}
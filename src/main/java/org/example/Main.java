package org.example;

public class Main {
    public static void main(String[] args) {

        GenericGraph g = new GenericGraph("src/main/resources/facebook_combined.txt");
        String demoVertex = "1";

        g.showGraphFunctionalities(demoVertex);
        g.writeEdgeSet("src/main/resources/generated_edge_set.txt");
        g.getMaxDegree();
        g.getMaxDegreeOfVertex();
        g.removeEdge(demoVertex, "88");
        g.removeVertex(demoVertex);
        g.createAdjacencyMatrix("src/main/resources/adjacency_matrix.txt");
        g.contraction(demoVertex, "48");
    }
}
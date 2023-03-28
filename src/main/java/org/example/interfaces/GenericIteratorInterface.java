package org.example.interfaces;

public interface GenericIteratorInterface {

    void readGraph(String fileInputPath);
    void getBFS(String startingVertex);

    void getDFS(String startingVertex);

    void getLexBFS();

    void getAstar(String sourceStarVertex, String sinkStarVertex);

    void getAstarAllShortestPaths(String sourceStarVertex);;
    void getDijkstra(String sourceVertex, String sinkVertex);
}

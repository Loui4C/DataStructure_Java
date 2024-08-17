package com.louischow.recite;

/***************Dijkstra迪杰斯特拉邻接矩阵实现***********************/
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

class Graph2 {
    private int vertexCount;
    private int[][] adjacencyMatrix;

    public Graph2(int vertexCount) {
        this.vertexCount = vertexCount;
        adjacencyMatrix = new int[vertexCount][vertexCount];
        for (int i = 0; i < vertexCount; i++) {
            Arrays.fill(adjacencyMatrix[i], Integer.MAX_VALUE);
        }
    }

    public void addEdge(int start, int end, int weight) {
        adjacencyMatrix[start][end] = weight;
        adjacencyMatrix[end][start] = weight; // For undirected graph
    }

    /*************************从未访问的顶点中选择距离起点最近的顶点********************************/
    private int getClosestVertex(int[] distances, boolean[] visited) {
        int minDistance = Integer.MAX_VALUE;
        int minVertex = -1;//最小距离的顶点

        for (int i = 0; i < distances.length; i++) {
            if (!visited[i] && distances[i] < minDistance) {//检查顶点 i 的距离是否小于当前 minDistance
                minDistance = distances[i];
                minVertex = i;
            }
        }

        return minVertex;
    }

    public Map<Integer, Integer> dijkstra(int startVertex) {
        int[] distances = new int[vertexCount];//存储从起点到每个顶点的最短距离
        boolean[] visited = new boolean[vertexCount];

        Arrays.fill(distances, Integer.MAX_VALUE);
        distances[startVertex] = 0;

        for (int i = 0; i < vertexCount; i++) {
            int u = getClosestVertex(distances, visited);//从未访问的顶点中选择距离起点最近的顶点 u
            if (u == -1) break;//没找到
            visited[u] = true;
        //迭代所有顶点 v，通过u更新从起点到顶点 v 的距离。
            for (int v = 0; v < vertexCount; v++) {
                if (!visited[v] && adjacencyMatrix[u][v] != Integer.MAX_VALUE && distances[u] + adjacencyMatrix[u][v] < distances[v]) {
                    distances[v] = distances[u] + adjacencyMatrix[u][v];
                }
            }
        }

        Map<Integer, Integer> distanceMap = new HashMap<>();//存储顶点和从起点到每个顶点的最短距离。
        for (int i = 0; i < vertexCount; i++) {
            distanceMap.put(i, distances[i]);
        }

        return distanceMap;
    }


    public static void main(String[] args) {
        Graph2 graph = new Graph2(5);
        graph.addEdge(0, 1, 10);
        graph.addEdge(0, 4, 3);
        graph.addEdge(1, 2, 2);
        graph.addEdge(1, 4, 4);
        graph.addEdge(2, 3, 9);
        graph.addEdge(3, 2, 7);
        graph.addEdge(4, 1, 1);
        graph.addEdge(4, 2, 8);
        graph.addEdge(4, 3, 2);

        System.out.println("Dijkstra's shortest paths from vertex 0:");
        Map<Integer, Integer> distances = graph.dijkstra(0);
        for (Map.Entry<Integer, Integer> entry : distances.entrySet()) {
            System.out.println("Vertex " + entry.getKey() + " -> Distance " + entry.getValue());
        }
    }
}
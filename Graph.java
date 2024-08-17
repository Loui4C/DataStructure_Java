package com.louischow.recite;

import java.util.LinkedList;
import java.util.Queue;
/*******************无向图，无权值*************************/
public class Graph {
    private int[][] adjacencyMatrix;//邻接矩阵，adjacencyMatrix[i][j] = 0 表示顶点 i 和顶点 j 之间没有边。
    private int vertexCount;//顶点数

    public Graph(int vertexCount) {
        this.vertexCount = vertexCount;
        adjacencyMatrix = new int[vertexCount][vertexCount];
    }

    public void addEdge(int i, int j) {
        if (i >= 0 && i < vertexCount && j >= 0 && j < vertexCount) {
            adjacencyMatrix[i][j] = 1;
            adjacencyMatrix[j][i] = 1; // For undirected graph
        }
    }

    public void removeEdge(int i, int j) {
        if (i >= 0 && i < vertexCount && j >= 0 && j < vertexCount) {
            adjacencyMatrix[i][j] = 0;
            adjacencyMatrix[j][i] = 0; // For undirected graph
        }
    }

    public boolean hasEdge(int i, int j) {
        if (i >= 0 && i < vertexCount && j >= 0 && j < vertexCount) {
            return adjacencyMatrix[i][j] == 1;
        }
        return false;
    }

    public void printGraph() {
        for (int i = 0; i < vertexCount; i++) {
            for (int j = 0; j < vertexCount; j++) {
                System.out.print(adjacencyMatrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    // 深度优先搜索
    public void dfs(int startVertex) {
        boolean[] visited = new boolean[vertexCount];
        dfsHelper(startVertex, visited);
    }

    private void dfsHelper(int vertex, boolean[] visited) {
        visited[vertex] = true;
        System.out.print(vertex + " ");// 访问当前顶点
        // 遍历所有顶点，寻找邻接且未访问的顶点
        for (int i = 0; i < vertexCount; i++) {
            if (adjacencyMatrix[vertex][i] == 1 && !visited[i]) {
                dfsHelper(i, visited);// 满足条件，递归调用，继续深度优先搜索
            }
            // 不需要显式的回溯，这一列如果都不符号条件，递归函数自然返回到上一个调用点
        }
    }

    // 广度优先搜索
    public void bfs(int startVertex) {
        boolean[] visited = new boolean[vertexCount];
        Queue<Integer> queue = new LinkedList<>();
        visited[startVertex] = true;
        queue.add(startVertex);

        while (!queue.isEmpty()) {
            int vertex = queue.poll();
            System.out.print(vertex + " ");

            for (int i = 0; i < vertexCount; i++) {
                if (adjacencyMatrix[vertex][i] == 1 && !visited[i]) {
                    visited[i] = true;
                    queue.add(i);
                }
            }
        }
    }

    public static void main(String[] args) {
        Graph graph = new Graph(5);
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 2);
        graph.addEdge(1, 3);
        graph.addEdge(2, 4);

        System.out.println("DFS starting from vertex 0:");
        graph.dfs(0);

        System.out.println("\nBFS starting from vertex 0:");
        graph.bfs(0);
    }
}

package com.louischow.recite;

import java.util.*;

class Edge {
    int start, end, weight;

    Edge(int start, int end, int weight) {
        this.start = start;//起点
        this.end = end;//终点
        this.weight = weight;//权重
    }
}
/**********************加权图，无向，邻接表*****************/
class Graph1 {
    private int vertexCount;//顶点数
    private List<Edge> edges;
    private List<List<Edge>> adjacencyList;//邻接表

    public Graph1(int vertexCount) {
        this.vertexCount = vertexCount;
        edges = new ArrayList<>();
        adjacencyList = new ArrayList<>(vertexCount);
        for (int i = 0; i < vertexCount; i++) {
            adjacencyList.add(new ArrayList<>());
        }
    }
    //更新图Graph1的属性
    public void addEdge(int start, int end, int weight) {
        Edge edge = new Edge(start, end, weight);
        edges.add(edge);
        adjacencyList.get(start).add(edge);
        adjacencyList.get(end).add(new Edge(end, start, weight)); // 无向图
    }

    //addEdgesToPQ的作用是将一个顶点的所有未访问的邻接边添加到优先队列中
    private void addEdgesToPQ(int vertex, PriorityQueue<Edge> pq, boolean[] visited) {
        visited[vertex] = true;
        for (Edge edge : adjacencyList.get(vertex)) {
            //每次从一个已访问的顶点出发，将其所有未访问的邻接边加入优先队列
            if (!visited[edge.end]) {//因此可以用终点判断一条边是否被访问过
                pq.add(edge);//未访问过就加入优先队列待用，优先队列poll的时候会直接将权值最小的出队
            }
        }
    }

    /*************最小生成树Prim&Kruskal（Minimum Spanning Tree, MST）*************/
    public List<Edge> primMST(int startVertex) {

        if (startVertex < 0 || startVertex >= vertexCount) {
            throw new IllegalArgumentException("Invalid start vertex");
        }

        // 用于标记顶点是否已被包含在 MST 中
        boolean[] visited = new boolean[vertexCount];
        // 优先队列，用于存储候选边，按边的权重排序
        PriorityQueue<Edge> pq = new PriorityQueue<>(Comparator.comparingInt(e -> e.weight));
        // 存储最终的最小生成树的边
        List<Edge> mst = new ArrayList<>();

        // 从指定的起点开始，初始化优先队列
        addEdgesToPQ(startVertex, pq, visited);

        // 当优先队列不为空且 MST 中的边数小于顶点数减一时，继续循环
        while (!pq.isEmpty() && mst.size() < vertexCount - 1) {
            // 从优先队列中取出权重最小的边
            Edge edge = pq.poll();
            // 如果该边的终点顶点还未被访问
            if (!visited[edge.end]) {
                // 标记终点顶点已被访问
                visited[edge.end] = true;
                // 将该边添加到 MST 中
                mst.add(edge);
                // 将该顶点的所有未访问的邻接边加入优先队列
                addEdgesToPQ(edge.end, pq, visited);
            }
        }
        return mst;
    }


    public List<Edge> kruskalMST() {
        //先把edges里的所有边按权重排序
        Collections.sort(edges, Comparator.comparingInt(e -> e.weight));
        DisjointSet ds = new DisjointSet(vertexCount);
        // 存储最终的最小生成树的边
        List<Edge> mst = new ArrayList<>();

        for (Edge edge : edges) {
            // 检查这条边的两个端点是否同一个祖宗，不是就不会够成环
            if (ds.find(edge.start) != ds.find(edge.end)) {
                //合并
                ds.union(edge.start, edge.end);
                mst.add(edge);
            }
            if (mst.size() == vertexCount - 1) break;
        }
        return mst;
    }

    //打印最小生成树
    private static void printMST(List<Edge> mst) {
        int totalWeight = 0;
        for (Edge edge : mst) {
            System.out.println(edge.start + " - " + edge.end + " (" + edge.weight + ")");
            totalWeight += edge.weight;
        }
        System.out.println("Total weight: " + totalWeight);
    }

    public static void main(String[] args) {
        Graph1 graph = new Graph1(5);
        graph.addEdge(0, 1, 2);
        graph.addEdge(0, 2, 3);
        graph.addEdge(1, 2, 1);
        graph.addEdge(1, 3, 4);
        graph.addEdge(2, 4, 5);
        graph.addEdge(3, 4, 2);

        System.out.println("Prim's MST:");
        printMST(graph.primMST(0));

        System.out.println("\nKruskal's MST:");
        printMST(graph.kruskalMST());
    }
}
//并查集，用于检测环路。
class DisjointSet {
    private int[] parent, rank;//rank是秩，也就是每颗子树的大小

    public DisjointSet(int size) {
        parent = new int[size];
        rank = new int[size];
        for (int i = 0; i < size; i++) {
            parent[i] = i;
            rank[i] = 0;
        }
    }

    public int find(int u) {
        if (parent[u] != u) {
            parent[u] = find(parent[u]);//路径压缩
        }
        return parent[u];
    }

    public void union(int u, int v) {//按秩求合，将小树并到大树上
        int rootU = find(u);
        int rootV = find(v);
        if (rootU != rootV) {
            if (rank[rootU] > rank[rootV]) {
                parent[rootV] = rootU;
            } else if (rank[rootU] < rank[rootV]) {
                parent[rootU] = rootV;
            } else {
                parent[rootV] = rootU;
                rank[rootU]++;
            }
        }
    }
}

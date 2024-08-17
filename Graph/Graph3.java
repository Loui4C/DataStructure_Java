package com.louischow.recite;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Graph3 {
    private int vertices; // 顶点数
    private int[][] adjMatrix; // 邻接矩阵

    // 构造函数，初始化顶点数和邻接矩阵
    public Graph3(int vertices) {
        this.vertices = vertices;
        adjMatrix = new int[vertices][vertices];
    }

    // 添加边，从 start 顶点到 end 顶点
    public void addEdge(int start, int end) {
        adjMatrix[start][end] = 1;
    }

    // 拓扑排序
    public List<Integer> topologicalSort() {
        boolean[] visited = new boolean[vertices]; // 记录每个顶点是否被访问过
        Stack<Integer> stack = new Stack<>(); // 存储排序结果的栈

        // 对每个顶点进行 DFS
        for (int i = 0; i < vertices; i++) {
            if (!visited[i]) {
                topologicalSortUtil(i, visited, stack);
            }
        }

        // 将栈中的元素弹出，形成拓扑排序的结果
        List<Integer> result = new ArrayList<>();
        while (!stack.isEmpty()) {
            result.add(stack.pop());
        }
        return result;
    }

    // 拓扑排序的辅助函数，使用深度优先搜索（DFS）
    private void topologicalSortUtil(int v, boolean[] visited, Stack<Integer> stack) {
        visited[v] = true; // 标记当前顶点为已访问

        // 递归访问所有相邻的未被访问的顶点
        for (int i = 0; i < vertices; i++) {
            if (adjMatrix[v][i] == 1 && !visited[i]) {
                topologicalSortUtil(i, visited, stack);
            }
        }
        stack.push(v); // 当前顶点处理完毕，推入栈中
    }

    // 测试主函数
    public static void main(String[] args) {
        Graph3 graph = new Graph3(6);
        graph.addEdge(5, 2);
        graph.addEdge(5, 0);
        graph.addEdge(4, 0);
        graph.addEdge(4, 1);
        graph.addEdge(2, 3);
        graph.addEdge(3, 1);

        System.out.println("拓扑排序的结果是：");
        List<Integer> result = graph.topologicalSort();
        for (int vertex : result) {
            System.out.print(vertex + " ");
        }
    }
}

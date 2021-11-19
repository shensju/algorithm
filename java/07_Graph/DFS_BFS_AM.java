package com.shensju.graph;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @Author: shensju
 * @Date: 2021/11/17 20:37
 */
public class DFS_BFS_AM {

    private static class MGraph<E> {

        private E[] vertexes; // 顶点表
        private int[][] edges; // 邻接矩阵，可看做边表
        private int numVertexes; // 图中当前顶点数
        private int numEdges; // 图中当前边数

        private static final int INFINITY = 65535; // 顶点之间不存在边
        private boolean[] visited; // 顶点是否被访问

        public MGraph() {

        }

        public MGraph(E[] vertexes) {
            numVertexes = vertexes.length;
            numEdges = 0;
            this.vertexes = vertexes;
            visited = new boolean[this.numVertexes];
            // 初始化邻接矩阵
            this.edges = new int[numVertexes][numVertexes];
            for (int i = 0; i < numVertexes; i++) {
                for (int j = 0; j < numVertexes; j++) {
                    if (i == j)
                        edges[i][j] = 0;
                    else
                        edges[i][j] = INFINITY;
                }
            }
        }

        /**
         * 如果是无向图，则对邻接矩阵进行对称赋值处理
         * @return
         */
        public boolean symmetry() {
            for (int i = 0; i < numVertexes; i++) {
                for (int j = i; j < numVertexes; j++)
                    edges[j][i] = edges[i][j];
            }
            return true;
        }

        /**
         * 返回索引位置为index的顶点
         * @param index
         * @return
         * @throws IllegalArgumentException
         */
        public E getVertexOfIndex(int index) throws IllegalArgumentException {
            if (index < 0 || index >= numVertexes)
                throw new IllegalArgumentException("GetVertexOfIndex failed! Illegal index, index should be [0, " + numVertexes + ").");
            return vertexes[index];
        }

        /**
         * 返回索引位置为index1和index2的边表权值
         * @param index1
         * @param index2
         * @return
         */
        public int getWeightOfEdge(int index1, int index2) throws IllegalArgumentException {
            if (index1 < 0 || index1 >= numVertexes || index2 < 0 || index2 >= numVertexes)
                throw new IllegalArgumentException("GetWeightOfEdge failed! Illegal index, index should be [0, " + numVertexes + ").");
            return edges[index1][index2];
        }

        /**
         * 对索引位置为index1和index2的边表赋权值
         * @param index1
         * @param index2
         * @param weight
         */
        public void insertWeightOfEdge(int index1, int index2, int weight) throws IllegalArgumentException {
            if (index1 < 0 || index1 >= numVertexes || index2 < 0 || index2 >= numVertexes)
                throw new IllegalArgumentException("InsertWeightOfEdge failed! Illegal index, index should be [0, " + numVertexes + ").");
            edges[index1][index2] = weight;
            numEdges++;
        }

        /**
         * 返回图的顶点数
         * @return
         */
        public int getNumVertexes() {
            return numVertexes;
        }

        /**
         * 返回图的边数
         * @return
         */
        public int getNumEdges() {
            return numEdges;
        }

        /**
         * 返回下标为index的顶点的第一个邻接顶点下标
         * 如果返回-1，表示当前顶点的邻接顶点不存在
         * @param index
         * @return
         */
        public int getFirstNeighbor(int index) throws IllegalArgumentException {
            if (index < 0 || index >= numVertexes)
                throw new IllegalArgumentException("GetFirstNeighbor failed! Illegal index, index should be [0, " + numVertexes + ").");
            for (int j = 0; j < numVertexes; j++) {
                if (edges[index][j] > 0 && edges[index][j] < INFINITY)
                    return j;
            }
            return -1;
        }

        /**
         * 返回下标为index1的顶点在下标为index2的邻接顶点的下一个邻接顶点的下标
         * 如果返回-1，表示当前顶点的下一个邻接顶点不存在
         * @param index1
         * @param index2
         * @return
         * @throws IllegalArgumentException
         */
        public int getNextNeighbor(int index1, int index2) throws IllegalArgumentException {
            if (index1 < 0 || index1 >= numVertexes || index2 < 0 || index2 >= numVertexes)
                throw new IllegalArgumentException("GetNextNeighbor failed! Illegal index, index should be [0, " + numVertexes + ").");
            for (int j = index2 + 1; j < numVertexes; j++) {
                if (edges[index1][j] > 0 && edges[index1][j] < INFINITY)
                    return j;
            }
            return -1;
        }

        /**
         * 邻接矩阵的深度遍历操作
         * @param graph
         */
        public void DFSTraverse(MGraph<E> graph) {
            int numVertexes = graph.getNumVertexes();
            // 初始所有顶点状态都是未访问过状态
            for (int i = 0; i < numVertexes; i++)
                visited[i] = false;
            for (int i = 0; i < numVertexes; i++)
                if (!visited[i])
                    // 对未访问过的顶点调用DFS1，若是连通图，只会执行一次
                    DFS(graph, i);
        }

        /**
         * 邻接矩阵的深度优先递归算法
         * @param graph
         * @param i
         */
        public void DFS(MGraph<E> graph, int i) {
            // 标记顶点状态为已访问
            visited[i] = true;
            // 打印顶点，也可以其它操作
            System.out.print(graph.getVertexOfIndex(i) + " ");
            int neighbor = graph.getFirstNeighbor(i);
            while (neighbor != -1) {
                if (!visited[neighbor])
                    // 对未访问的邻接顶点递归调用
                    DFS(graph, neighbor);
                neighbor = graph.getNextNeighbor(i, neighbor);
            }
        }

        /**
         * 邻接矩阵的广度遍历算法
         * @param graph
         */
        public void BFSTraverse(MGraph<E> graph) {
            int u, w;
            int numVertexes = graph.getNumVertexes();
            // 辅助队列
            Queue<Integer> queue = new LinkedList<>();
            // 初始所有顶点状态都是未访问过状态
            for (int i = 0; i < numVertexes; i++)
                visited[i] = false;
            for (int i = 0; i < numVertexes; i++) {
                if (!visited[i]) {
                    // 标记顶点状态为已访问
                    visited[i] = true;
                    // 打印顶点，也可以其它操作
                    System.out.print(graph.getVertexOfIndex(i) + " ");
                    // 将顶点下标入队
                    queue.offer(i);
                    while (!queue.isEmpty()) {
                        u = queue.poll();
                        w = graph.getFirstNeighbor(u);
                        while (w != -1) {
                            // 对未访问的邻接顶点标记为已访问，进行打印操作，并将顶点下标入队
                            if (!visited[w]) {
                                visited[w] = true;
                                System.out.print(graph.getVertexOfIndex(w) + " ");
                                queue.offer(w);
                            }
                            // 寻找下一个邻接顶点
                            w = graph.getNextNeighbor(u, w);
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        Character[] vexs = new Character[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I'};
        MGraph<Character> graph = new MGraph<>(vexs);
        graph.insertWeightOfEdge(0, 1, 1);
        graph.insertWeightOfEdge(0, 5, 1);
        graph.insertWeightOfEdge(1, 2, 1);
        graph.insertWeightOfEdge(1, 6, 1);
        graph.insertWeightOfEdge(1, 8, 1);
        graph.insertWeightOfEdge(2, 3, 1);
        graph.insertWeightOfEdge(2, 8, 1);
        graph.insertWeightOfEdge(3, 4, 1);
        graph.insertWeightOfEdge(3, 6, 1);
        graph.insertWeightOfEdge(3, 7, 1);
        graph.insertWeightOfEdge(3, 8, 1);
        graph.insertWeightOfEdge(4, 5, 1);
        graph.insertWeightOfEdge(4, 7, 1);
        graph.insertWeightOfEdge(5, 6, 1);
        graph.insertWeightOfEdge(6, 7, 1);

        // 无向图，对邻接矩阵进行对称赋值处理
        graph.symmetry();

        System.out.println("无向图，采用邻接矩阵存储结构，进行深度遍历：");
        graph.DFSTraverse(graph);
        System.out.println();
        System.out.println("无向图，采用邻接矩阵存储结构，进行广度遍历：");
        graph.BFSTraverse(graph);

        /**
         * 无向图，采用邻接矩阵存储结构，进行深度遍历：
         * A B C D E F G H I
         * 无向图，采用邻接矩阵存储结构，进行广度遍历：
         * A B F C G I E D H
         */
    }
}


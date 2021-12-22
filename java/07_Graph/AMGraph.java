package com.shensju.graph;

import java.util.*;

/**
 * @Author: shensju
 * @Date: 2021/11/24 21:48
 * 以邻接矩阵作为存储结构，实现的算法如下：
 * 1）图的深度优先遍历（Depth First Search）；
 * 2）图的广度优先遍历（Breadth First Search）；
 * 3）普里姆（Prim）算法，生成连通图的最小生成树；
 * 4）克鲁斯卡尔（Kruskal）算法，生成连通图的最小生成树；
 * 5）迪杰斯特拉（Dijkstra）算法，求单源点的最短路径；
 * 6）弗洛伊德（Floyd）算法，求所有顶点到所有顶点的最短路径；
 */
public class AMGraph<E> {

    private E[] vertexes; // 顶点表
    private int[][] edges; // 邻接矩阵，可看做边表
    private int numVertexes; // 图中顶点数
    private int numEdges; // 图中边数

    private static final int INFINITY = 65535; // 顶点之间不存在边
    private boolean[] visited; // 标记顶点是否被访问

    public AMGraph() {

    }

    public AMGraph(E[] vertexes) {
        numVertexes = vertexes.length;
        numEdges = 0;
        this.vertexes = vertexes;
        visited = new boolean[numVertexes];
        this.edges = new int[numVertexes][numVertexes]; // 初始化邻接矩阵
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
            throw new IllegalArgumentException("GetVertexOfIndex failed! Illegal index, index should be in [0, " + numVertexes + ").");
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
            throw new IllegalArgumentException("GetWeightOfEdge failed! Illegal index, index should be in [0, " + numVertexes + ").");
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
            throw new IllegalArgumentException("InsertWeightOfEdge failed! Illegal index, index should be in [0, " + numVertexes + ").");
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
            throw new IllegalArgumentException("GetFirstNeighbor failed! Illegal index, index should be in [0, " + numVertexes + ").");
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
     * 邻接矩阵的深度优先递归算法
     * @param i
     */
    public void dfs(int i) {
        visited[i] = true; // 标记顶点状态为已访问
        System.out.print(vertexes[i] + " "); // 打印顶点，也可以其它操作
        int neighbor = getFirstNeighbor(i);
        while (neighbor != -1) {
            if (!visited[neighbor])
                dfs(neighbor); // 对未访问的邻接顶点递归调用
            neighbor = getNextNeighbor(i, neighbor);
        }
    }

    /**
     * 邻接矩阵的深度优先遍历操作
     */
    public void dfsTraverse() {
        for (int i = 0; i < numVertexes; i++)
            visited[i] = false; // 初始所有顶点状态都是未访问过状态
        for (int i = 0; i < numVertexes; i++)
            if (!visited[i])
                dfs(i); // 对未访问过的顶点调用dfs，若是连通图，只会执行一次
    }

    /**
     * 邻接矩阵的广度优先遍历算法
     */
    public void bfsTraverse() {
        Queue<Integer> queue = new LinkedList<>(); // 辅助队列
        for (int i = 0; i < numVertexes; i++)
            visited[i] = false; // 初始所有顶点状态都是未访问过状态
        for (int i = 0; i < numVertexes; i++) {
            if (!visited[i]) {
                visited[i] = true; // 标记顶点状态为已访问
                System.out.print(vertexes[i] + " "); // 打印顶点，也可以其它操作
                queue.offer(i); // 将顶点下标入队
                while (!queue.isEmpty()) {
                    int u = queue.poll();
                    int v = getFirstNeighbor(u);
                    while (v != -1) {
                        if (!visited[v]) { // 对未访问的邻接顶点标记为已访问，进行打印操作，并将顶点下标入队
                            visited[v] = true;
                            System.out.print(vertexes[v] + " ");
                            queue.offer(v);
                        }
                        v = getNextNeighbor(u, v); // 寻找下一个邻接顶点
                    }
                }
            }
        }
    }

    /**
     * 普里姆（Prim）算法生成连通图的最小生成树
     */
    public void miniSpanTreePrim() {
        int[] adjvex = new int[numVertexes]; // 保存相关顶点下标
        int[] lowcost = new int[numVertexes]; // 保存相关顶点间边的权值
        lowcost[0] = 0; // 初始化下标为0的顶点权值为0，表示该顶点加入生成树
        adjvex[0] = 0; // 初始化下标为0的顶点的值为0
        for (int i = 1; i < numVertexes; i++) { // 循环除下标为0的顶点外的全部顶点
            lowcost[i] = edges[0][i]; // 保存下标为0的顶点与其它顶点的权值
            adjvex[i] = 0; // 初始化都为下标为0的顶点的下标
        }
        for (int i = 1; i < numVertexes; i++) { // 循环顶点数减1次，得到生成最小生成树的顶点数减1条边
            int min = INFINITY; // 初始化最小权值为∞，通常设置为不可能的大数字如32767、65535等
            int j = 1;
            int k = 0; // 保存最小权值的顶点下标
            while (j < numVertexes) {
                if (lowcost[j] != 0 && lowcost[j] < min) { // 如果权值不为0且权值小于min
                    min = lowcost[j]; // 让当前权值成为最小值
                    k = j; // 将当前最小值的下标存入k
                }
                j++;
            }
            System.out.println("(" + vertexes[adjvex[k]] + ", " + vertexes[k] + ")"); // 打印当前顶点边中权值最小的边
            lowcost[k] = 0; // 将当前顶点的权值设置为0，表示此顶点已经完成任务
            // 更新lowcost和adjvex数组
            for (j = 1; j < numVertexes; j++) {
                if (lowcost[j] != 0 && edges[k][j] < lowcost[j]) { // 如果下标为k顶点各边权值小于此前这些顶点未被加入生成树权值
                    lowcost[j] = edges[k][j]; // 将较小的权值存入lowcost相应位置
                    adjvex[j] = k; // 将下标为k的顶点存入adjvex
                }
            }
        }
    }

    /** 定义边集 **/
    static class Edge implements Comparable<Edge> {
        private int begin; // 边的起点下标
        private int end; // 边的终点下标
        private int weight; // 边的权值

        public Edge() {

        }

        public Edge(int begin, int end, int weight) {
            this.begin = begin;
            this.end = end;
            this.weight = weight;
        }

        @Override
        public int compareTo(Edge edge) {
            return this.weight - edge.weight;
        }

        @Override
        public String toString() {
            return "Edge{" +
                    "begin=" + begin +
                    ", end=" + end +
                    ", weight=" + weight +
                    '}';
        }
    }

    /**
     * 查找连线顶点的尾部下标
     * @param parent
     * @param f
     * @return
     */
    private int find(int[] parent, int f) {
        while (parent[f] > 0)
            f = parent[f];
        return f;
    }

    /**
     * 克鲁斯卡尔（Kruskal）算法生成连通图的最小生成树
     */
    public void miniSpanTreeKruskal() {
        int[] parent = new int[numVertexes]; // 用来判断边与边是否形成环路，初始值为0

        // 将邻接矩阵转化为边集数组，并按权值由小到大进行排序
        int k = 0;
        Edge[] edgeArray = new Edge[numEdges]; // 定义边集数组
        for (int i = 0; i < numVertexes - 1; i++) {
            for (int j = i + 1; j < numVertexes; j++) {
                if (edges[i][j] < INFINITY) {
                    edgeArray[k] = new Edge(i, j, edges[i][j]);
                    k++;
                }
            }
        }
        Arrays.sort(edgeArray); // 递增排序
        for (int i = 0; i < numEdges; i++) { // 循环每一条边
            int n = find(parent, edgeArray[i].begin);
            int m = find(parent, edgeArray[i].end);
            // 假如n与m不等，说明此边没有与现有的生成树形成环路
            if (n != m) {
                parent[n] = m; // 将此边的结尾顶点的下标放入下标为此边的起始顶点的下标的parent数组中，表示此顶点已经在生成树集合中
                System.out.println("(" + vertexes[edgeArray[i].begin] + ", "
                        + vertexes[edgeArray[i].end] + ") " + edgeArray[i].weight); // 打印当前顶点边中权值最小的边
            }
        }
    }

    /**
     * 迪杰斯特拉（Dijkstra）算法，求单源点的最短路径
     * @param vIndex 源点下标
     * @param pathIndex 存储最短路径下标的数组
     * @param weightSum 存储源点到各顶点最短路径的权值和的数组
     */
    public void shortestPathDijkstra(int vIndex, int[] pathIndex, int[] weightSum) {
        boolean[] flag = new boolean[numVertexes]; // 初始化为false，表示全部顶点是处于未知最短路径状态
        for (int i = 0; i < numVertexes; i++) {
            weightSum[i] = edges[vIndex][i]; // 将与源点有连线的顶点加上权值
            pathIndex[i] = vIndex; // 初始化最短路径下标为源点下标
        }
        flag[vIndex] = true; // 源点到源点不需要求最短路径
        for (int i = 1; i < numVertexes; i++) { // 每循环一次，求得源点到某个顶点的最短路径
            int min = INFINITY; // 默认当前所知离源点的权值和为∞，通常设置为不可能的大数字如32767、65535等
            int k = -1; // 假定离源点最近的顶点下标为-1
            for (int j = 0; j < numVertexes; j++) { // 寻找离源点最近的顶点
                if (!flag[j] && weightSum[j] < min) {
                    k = j;
                    min = weightSum[j];
                }
            }
            flag[k] = true; // 将目前找到的最近的顶点状态置为true
            for (int j = 0; j < numVertexes; j++) { // 修正源点到各顶点的当前最短路径下标以及权值和
                if (!flag[j] && min + edges[k][j] < weightSum[j]) { // 如果经过k顶点的路径比现在这条路径的长度短的话
                    weightSum[j] = min + edges[k][j]; // 修改当前路径的权值和
                    pathIndex[j] = k; // 修改前驱顶点下标
                }
            }
        }
    }

    /**
     * 弗洛伊德（Floyd）算法，求所有顶点到所有顶点的最短路径
     * @param pathIndex 对应顶点的最短路径的前驱矩阵
     * @param weightSum 顶点到顶点的最短路径权值和的矩阵
     */
    public void shortestPathFloyd(int[][] pathIndex, int[][] weightSum) {
        for (int i = 0; i < numVertexes; i++) { // 初始化
            for (int j = 0; j < numVertexes; j++) {
                weightSum[i][j] = edges[i][j];
                pathIndex[i][j] = j;
            }
        }
        for (int k = 0; k < numVertexes; k++) {
            for (int i = 0; i < numVertexes; i++) {
                for (int j = 0; j < numVertexes; j++) {
                    if (weightSum[i][j] > weightSum[i][k] + weightSum[k][j]) { // 如果经过下标为k顶点路径比原两点间路径更短
                        weightSum[i][j] = weightSum[i][k] + weightSum[k][j]; // 将当前两点间权值设为更小的一个
                        pathIndex[i][j] = pathIndex[i][k]; // 路径设置为经过下标为k的顶点
                    }
                }
            }
        }
    }

    /**
     * 根据Floyd算法求出的前驱矩阵，打印任意两个顶点间的最短路径，权值和
     * @param pathIndex 对应顶点的最短路径的前驱矩阵
     * @param weightSum 顶点到顶点的最短路径权值和的矩阵
     * @param start 源点
     * @param end 终点
     */
    public void shortestPathFloydPrint(int[][] pathIndex, int[][] weightSum, int start, int end) {
        System.out.println(vertexes[start] + " - " + vertexes[end] + " weight : " + weightSum[start][end]);
        System.out.print(vertexes[start] + " - " + vertexes[end] + " path : ");
        int k = pathIndex[start][end]; // 获得第一个路径顶点下标
        System.out.print(vertexes[start]); // 打印源点
        while (k != end) {
            System.out.print(" -> " + vertexes[k]); // 打印路径顶点
            k = pathIndex[k][end]; // 获得下一个路径顶点下标
        }
        System.out.println(" -> " + vertexes[end]); // 打印终点
    }

    /**
     * 测试：图的深度优先遍历算法，图的广度优先遍历算法
     */
    public static void testTraverse() {
        Character[] vertexes = new Character[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I'};
        AMGraph<Character> amGraph = new AMGraph<>(vertexes);
        amGraph.insertWeightOfEdge(0, 1, 1);
        amGraph.insertWeightOfEdge(0, 5, 1);
        amGraph.insertWeightOfEdge(1, 2, 1);
        amGraph.insertWeightOfEdge(1, 6, 1);
        amGraph.insertWeightOfEdge(1, 8, 1);
        amGraph.insertWeightOfEdge(2, 3, 1);
        amGraph.insertWeightOfEdge(2, 8, 1);
        amGraph.insertWeightOfEdge(3, 4, 1);
        amGraph.insertWeightOfEdge(3, 6, 1);
        amGraph.insertWeightOfEdge(3, 7, 1);
        amGraph.insertWeightOfEdge(3, 8, 1);
        amGraph.insertWeightOfEdge(4, 5, 1);
        amGraph.insertWeightOfEdge(4, 7, 1);
        amGraph.insertWeightOfEdge(5, 6, 1);
        amGraph.insertWeightOfEdge(6, 7, 1);

        amGraph.symmetry(); // 无向图，对邻接矩阵进行对称赋值处理

        System.out.println("无向图，采用邻接矩阵存储结构，进行深度遍历：");
        amGraph.dfsTraverse();
        System.out.println();
        System.out.println("无向图，采用邻接矩阵存储结构，进行广度遍历：");
        amGraph.bfsTraverse();

        /**
         * 无向图，采用邻接矩阵存储结构，进行深度遍历：
         * A B C D E F G H I
         * 无向图，采用邻接矩阵存储结构，进行广度遍历：
         * A B F C G I E D H
         */
    }

    /**
     * 测试：普里姆（Prim）算法生成连通图的最小生成树
     */
    public static void testMiniSpanTreePrim() {
        String[] vertexes = new String[]{"V0", "V1", "V2", "V3", "V4", "V5", "V6", "V7", "V8"};
        AMGraph<String> amGraph = new AMGraph<>(vertexes);
        amGraph.insertWeightOfEdge(0, 1, 10);
        amGraph.insertWeightOfEdge(0, 5, 11);
        amGraph.insertWeightOfEdge(1, 2, 18);
        amGraph.insertWeightOfEdge(1, 8, 12);
        amGraph.insertWeightOfEdge(1, 6, 16);
        amGraph.insertWeightOfEdge(2, 8, 8);
        amGraph.insertWeightOfEdge(2, 3, 22);
        amGraph.insertWeightOfEdge(3, 8, 21);
        amGraph.insertWeightOfEdge(3, 6, 24);
        amGraph.insertWeightOfEdge(3, 7, 16);
        amGraph.insertWeightOfEdge(3, 4, 20);
        amGraph.insertWeightOfEdge(4, 7, 7);
        amGraph.insertWeightOfEdge(4, 5, 26);
        amGraph.insertWeightOfEdge(5, 6, 17);
        amGraph.insertWeightOfEdge(6, 7, 19);

        amGraph.symmetry(); // 无向图，对邻接矩阵进行对称赋值处理

        amGraph.miniSpanTreePrim();

        /**
         * (V0, V1)
         * (V0, V5)
         * (V1, V8)
         * (V8, V2)
         * (V1, V6)
         * (V6, V7)
         * (V7, V4)
         * (V7, V3)
         */
    }

    /**
     * 测试：克鲁斯卡尔（Kruskal）算法生成连通图的最小生成树
     */
    public static void testMiniSpanTreeKruskal() {
        String[] vertexes = new String[]{"V0", "V1", "V2", "V3", "V4", "V5", "V6", "V7", "V8"};
        AMGraph<String> amGraph = new AMGraph<>(vertexes);
        amGraph.insertWeightOfEdge(0, 1, 10);
        amGraph.insertWeightOfEdge(0, 5, 11);
        amGraph.insertWeightOfEdge(1, 2, 18);
        amGraph.insertWeightOfEdge(1, 8, 12);
        amGraph.insertWeightOfEdge(1, 6, 16);
        amGraph.insertWeightOfEdge(2, 8, 8);
        amGraph.insertWeightOfEdge(2, 3, 22);
        amGraph.insertWeightOfEdge(3, 8, 21);
        amGraph.insertWeightOfEdge(3, 6, 24);
        amGraph.insertWeightOfEdge(3, 7, 16);
        amGraph.insertWeightOfEdge(3, 4, 20);
        amGraph.insertWeightOfEdge(4, 7, 7);
        amGraph.insertWeightOfEdge(4, 5, 26);
        amGraph.insertWeightOfEdge(5, 6, 17);
        amGraph.insertWeightOfEdge(6, 7, 19);

        amGraph.symmetry(); // 无向图，对邻接矩阵进行对称赋值处理

        amGraph.miniSpanTreeKruskal();

        /**
         * (V4, V7) 7
         * (V2, V8) 8
         * (V0, V1) 10
         * (V0, V5) 11
         * (V1, V8) 12
         * (V1, V6) 16
         * (V3, V7) 16
         * (V6, V7) 19
         */
    }

    /**
     * 测试：迪杰斯特拉（Dijkstra）算法，求单源点的最短路径
     */
    public static void testShortestPathDijkstra() {
        String[] vertexes = new String[]{"V0", "V1", "V2", "V3", "V4", "V5", "V6", "V7", "V8"};
        AMGraph<String> amGraph = new AMGraph<>(vertexes);
        amGraph.insertWeightOfEdge(0, 1, 1);
        amGraph.insertWeightOfEdge(0, 2, 5);
        amGraph.insertWeightOfEdge(1, 2, 3);
        amGraph.insertWeightOfEdge(1, 3, 7);
        amGraph.insertWeightOfEdge(1, 4, 5);
        amGraph.insertWeightOfEdge(2, 4, 1);
        amGraph.insertWeightOfEdge(2, 5, 7);
        amGraph.insertWeightOfEdge(3, 4, 2);
        amGraph.insertWeightOfEdge(3, 6, 3);
        amGraph.insertWeightOfEdge(4, 5, 3);
        amGraph.insertWeightOfEdge(4, 6, 6);
        amGraph.insertWeightOfEdge(4, 7, 9);
        amGraph.insertWeightOfEdge(5, 7, 5);
        amGraph.insertWeightOfEdge(6, 7, 2);
        amGraph.insertWeightOfEdge(6, 8, 7);
        amGraph.insertWeightOfEdge(7, 8, 4);

        amGraph.symmetry(); // 无向图，对邻接矩阵进行对称赋值处理

        int[] pathIndex = new int[amGraph.getNumVertexes()];
        int[] weightSum = new int[amGraph.getNumVertexes()];
        amGraph.shortestPathDijkstra(0, pathIndex, weightSum); // 假定源点下标为0

        System.out.println("最短路径下标的数组为：" + Arrays.toString(pathIndex));
        System.out.println("源点" + amGraph.getVertexOfIndex(0) + "到各顶点的最短路径带权长度和为");
        for (int i = 0; i < amGraph.getNumVertexes(); i++) {
            System.out.println(amGraph.getVertexOfIndex(0) + " - " + amGraph.getVertexOfIndex(i) + " : " + weightSum[i]);
        }

        /**
         * 最短路径下标的数组为：[0, 0, 1, 4, 2, 4, 3, 6, 7]
         * 源点V0到各顶点的最短路径带权长度和为
         * V0 - V0 : 0
         * V0 - V1 : 1
         * V0 - V2 : 4
         * V0 - V3 : 7
         * V0 - V4 : 5
         * V0 - V5 : 8
         * V0 - V6 : 10
         * V0 - V7 : 12
         * V0 - V8 : 16
         */
    }

    /**
     * 测试：弗洛伊德（Floyd）算法，求所有顶点到所有顶点的最短路径
     */
    public static void testShortestPathFloyd() {
        String[] vertexes = new String[]{"V0", "V1", "V2", "V3", "V4", "V5", "V6", "V7", "V8"};
        AMGraph<String> amGraph = new AMGraph<>(vertexes);
        amGraph.insertWeightOfEdge(0, 1, 1);
        amGraph.insertWeightOfEdge(0, 2, 5);
        amGraph.insertWeightOfEdge(1, 2, 3);
        amGraph.insertWeightOfEdge(1, 3, 7);
        amGraph.insertWeightOfEdge(1, 4, 5);
        amGraph.insertWeightOfEdge(2, 4, 1);
        amGraph.insertWeightOfEdge(2, 5, 7);
        amGraph.insertWeightOfEdge(3, 4, 2);
        amGraph.insertWeightOfEdge(3, 6, 3);
        amGraph.insertWeightOfEdge(4, 5, 3);
        amGraph.insertWeightOfEdge(4, 6, 6);
        amGraph.insertWeightOfEdge(4, 7, 9);
        amGraph.insertWeightOfEdge(5, 7, 5);
        amGraph.insertWeightOfEdge(6, 7, 2);
        amGraph.insertWeightOfEdge(6, 8, 7);
        amGraph.insertWeightOfEdge(7, 8, 4);

        amGraph.symmetry(); // 无向图，对邻接矩阵进行对称赋值处理

        int[][] pathIndex = new int[amGraph.getNumVertexes()][amGraph.getNumVertexes()];
        int[][] weightSum = new int[amGraph.getNumVertexes()][amGraph.getNumVertexes()];

        amGraph.shortestPathFloyd(pathIndex, weightSum);
        // 求顶点V0到顶点V8的最短路径，权值和
        amGraph.shortestPathFloydPrint(pathIndex, weightSum, 0, 8);

        /**
         * V0 - V8 weight : 16
         * V0 - V8 path : V0 -> V1 -> V2 -> V4 -> V3 -> V6 -> V7 -> V8
         */
    }
}


package com.shensju.graph;

import java.util.*;

/**
 * @Author: shensju
 * @Date: 2021/11/24 21:53
 * 以邻接表作为存储结构，实现的算法如下
 * 1）图的深度优先遍历（Depth First Search）；
 * 2）图的广度优先遍历（Breadth First Search）；
 * 3）拓扑排序（TopologicalSort）算法，求有向图的拓扑排序；
 * 4）关键路径（CriticalPath）算法，求带权有向图的关键路径；
 */
public class ALGraph<E> {

    /** 定义边结构 **/
    static class EdgeNode {
        private int adjvex; // 邻接点域，存储顶点对应的下标
        private int weight; // 存储权值
        private EdgeNode next; // 链域，指向下一个邻接点

        public EdgeNode() {

        }

        public EdgeNode(int adjvex, int weight, EdgeNode next) {
            this.adjvex = adjvex;
            this.weight = weight;
            this.next = next;
        }
    }

    /** 定义顶点结构 **/
    static class VertexNode<E> {
        private E data; // 顶点域，存储顶点信息
        private EdgeNode firstedge; // 边表头指针
        private boolean visited; // 标记顶点是否被访问
        private int in; // 顶点入度

        public VertexNode() {
            this.data = null;
            this.firstedge = null;
            this.visited = false;
        }

        public VertexNode(E data, EdgeNode firstedge) {
            this.data = data;
            this.firstedge = firstedge;
            this.visited = false;
        }
    }

    private List<VertexNode<E>> adjList; // 顶点表
    private int numVertexes; // 图中顶点数
    private int numEdges; // 图中边数

    private static final int INFINITY = 65535; // 顶点之间不存在边

    public ALGraph() {

    }

    /**
     * 根据图的邻接矩阵信息，初始化邻接表
     * @param amGraph
     */
    public ALGraph(AMGraph amGraph) {
        this.numVertexes = amGraph.getNumVertexes(); // 顶点数
        this.numEdges = amGraph.getNumEdges(); // 边数
        // 读入顶点信息，建立顶点表
        this.adjList = new ArrayList<>();
        for (int i = 0; i < this.numVertexes; i++) {
            VertexNode<E> vertexNode = new VertexNode<>();
            vertexNode.data = (E) amGraph.getVertexOfIndex(i);
            this.adjList.add(vertexNode);
        }
        // 读入边信息，建立边表
        for (int i = 0; i < this.numVertexes; i++) {
            for (int j = 0; j < this.numVertexes; j++) {
                int weight = amGraph.getWeightOfEdge(i, j);
                if (weight != 0 && weight != INFINITY) { // 头插法
                    EdgeNode edgeNode = this.adjList.get(i).firstedge;
                    EdgeNode newEdgeNode = new EdgeNode(j, weight, edgeNode);
                    this.adjList.get(i).firstedge = newEdgeNode;
                    this.adjList.get(j).in++; // 顶点入度+1
                }
//                if (weight != 0 && weight != INFINITY) { // 尾插法
//                    EdgeNode edgeNode = this.adjList.get(i).firstedge;
//                    EdgeNode newEdgeNode = new EdgeNode(j, weight, null);
//                    if (edgeNode == null) {
//                        this.adjList.get(i).firstedge = newEdgeNode;
//                    } else {
//                        while (edgeNode.next != null)
//                            edgeNode = edgeNode.next;
//                        edgeNode.next = newEdgeNode;
//                    }
//                    this.adjList.get(j).in++; // 顶点入度+1
//                }
            }
        }
    }

    /**
     * 返回图的顶点表集合
     * @return
     */
    public List<VertexNode<E>> getAdjList() {
        return this.adjList;
    }

    /**
     * 返回图的顶点数
     * @return
     */
    public int getNumVertexes() {
        return this.numVertexes;
    }

    /**
     * 返回图的边数
     * @return
     */
    public int getNumEdges() {
        return this.numEdges;
    }

    /**
     * 邻接表的深度优先递归算法
     * @param i
     */
    private void dfs(int i) {
        adjList.get(i).visited = true; // 标记顶点状态为已访问
        System.out.print(adjList.get(i).data + " "); // 打印顶点，也可以其它操作
        EdgeNode edgeNode = adjList.get(i).firstedge;
        while (edgeNode != null) {
            if (!adjList.get(edgeNode.adjvex).visited)
                dfs(edgeNode.adjvex); // 对未访问的邻接顶点递归调用
            edgeNode = edgeNode.next;
        }
    }

    /**
     * 邻接表的深度优先遍历操作
     */
    public void dfsTraverse() {
        for (int i = 0; i < numVertexes; i++)
            adjList.get(i).visited = false; // 初始所有顶点状态都是未访问过状态
        for (int i = 0; i < numVertexes; i++)
            if (!adjList.get(i).visited)
                dfs(i); // 对未访问过的顶点调用dfs，若是连通图，只会执行一次
    }

    /**
     * 邻接表的广度优先遍历算法
     */
    public void bfsTraverse() {
        EdgeNode edgeNode = null;
        Queue<Integer> queue = new LinkedList<>(); // 辅助队列
        for (int i = 0; i < numVertexes; i++)
            adjList.get(i).visited = false; // 初始所有顶点状态都是未访问过状态
        for (int i = 0; i < numVertexes; i++) {
            if (!adjList.get(i).visited) {
                adjList.get(i).visited = true; // 标记顶点状态为已访问
                System.out.print(adjList.get(i).data + " "); // 打印顶点，也可以其它操作
                queue.offer(i); // 将顶点下标入队
                while (!queue.isEmpty()) {
                    int u = queue.poll();
                    edgeNode = adjList.get(u).firstedge;
                    while (edgeNode != null) {
                        if (!adjList.get(edgeNode.adjvex).visited) { // 对未访问的邻接顶点标记为已访问，进行打印操作，并将顶点下标入队
                            adjList.get(edgeNode.adjvex).visited = true;
                            System.out.print(adjList.get(edgeNode.adjvex).data + " ");
                            queue.offer(edgeNode.adjvex);
                        }
                        edgeNode = edgeNode.next; // 寻找下一个邻接顶点
                    }
                }
            }
        }
    }

    /**
     * 拓扑排序
     * @return 若图没有回路，则输出拓扑排序序列并返回true；若有回路则返回false
     */
    public boolean topologicalSort() {
        int count = 0; // 统计输出顶点的个数
        Stack<Integer> stack = new Stack<>(); // 存储入度为0的顶点的下标
        for (int i = 0; i < numVertexes; i++)
            if (adjList.get(i).in == 0) // 将入度为0的顶点的下标入栈
                stack.push(i);
        StringBuilder stringBuilder = new StringBuilder();
        while (!stack.isEmpty()) {
            int u = stack.pop();
            stringBuilder.append(adjList.get(u).data + " -> ");
            count++;
            EdgeNode e = adjList.get(u).firstedge;
            for (; e != null; e = e.next) {
                int v = e.adjvex;
                if (--adjList.get(v).in == 0) // 将入度为0的顶点的邻接顶点的入度减1，如果减1后为0，则将顶点下标入栈
                    stack.push(v);
            }
        }
        if (count == numVertexes) {
            System.out.println("当前图的拓扑排序为：" + stringBuilder.toString() + " NULL");
            return true;
        }
        return false;
    }

    /**
     * 拓扑排序，用于关键路径计算
     * @param etv 事件最早发生时间数组
     * @param stack2 存储拓扑序列的栈
     * @return 若返回true则存在拓扑序列，若返回false则不存在拓扑序列
     */
    public boolean topologicalSort(int[] etv, Stack<Integer> stack2) {
        int count = 0; // 统计输出顶点的个数
        Stack<Integer> stack1 = new Stack<>(); // 存储入度为0的顶点的下标
        for (int i = 0; i < numVertexes; i++)
            if (adjList.get(i).in == 0) // 将入度为0的顶点的下标入栈
                stack1.push(i);
        System.out.print("TopologicalSort : "); // 开始打印拓扑序列
        while (!stack1.isEmpty()) {
            int u = stack1.pop();
            System.out.print(adjList.get(u).data + " -> ");
            count++;
            stack2.push(u); // 将弹出的顶点序号压入拓扑序列的栈
            EdgeNode e = adjList.get(u).firstedge;
            for (; e != null; e = e.next) {
                int v = e.adjvex;
                if (--adjList.get(v).in == 0) // 将入度为0的顶点的邻接顶点的入度减1，如果减1后为0，则将顶点下标入栈
                    stack1.push(v);
                if (etv[u] + e.weight > etv[v]) // 求各顶点事件最早发生时间的值
                    etv[v] = etv[u] + e.weight;
            }
        }
        System.out.println("NULL");
        if (count == numVertexes)
            return true;
        return false;
    }

    /**
     * 求关键路径，并输出带权有向图的各项关键活动
     */
    public void criticalPath() {
        int[] etv = new int[numVertexes]; // 默认初始值为0
        Stack<Integer> stack2 = new Stack<>(); // 用于存储拓扑序列
        boolean flag = topologicalSort(etv, stack2);
        if (!flag) {
            System.out.println("拓扑排序失败！该带权有向图不存在拓扑序列！");
        } else {
            System.out.print("etv : "); // 开始打印etv
            for (int i = 0; i < etv.length - 1; i++)
                System.out.print(etv[i] + " -> ");
            System.out.println(etv[etv.length - 1]);
            int[] ltv = new int[numVertexes];
            for (int i = 0; i < numVertexes; i++) // 初始化ltv
                ltv[i] = etv[numVertexes - 1];
            while (!stack2.isEmpty()) {
                int top = stack2.pop();
                for (EdgeNode e = adjList.get(top).firstedge; e != null; e = e.next) { // 求各顶点事件的最迟发生时间的值
                    int k = e.adjvex;
                    if (ltv[k] - e.weight < ltv[top])
                        ltv[top] = ltv[k] - e.weight;
                }
            }
            System.out.print("ltv : "); // 开始打印ltv
            for (int i = 0; i < ltv.length - 1; i++)
                System.out.print(ltv[i] + " -> ");
            System.out.println(ltv[ltv.length - 1]);
            for (int j = 0; j < numVertexes; j++) { // 求ete，lte和关键活动
                for (EdgeNode e = adjList.get(j).firstedge; e != null; e = e.next) {
                    int k = e.adjvex;
                    int ete = etv[j]; // 活动最早发生时间
                    int lte = ltv[k] - e.weight; // 活动最迟发生时间
                    if (ete == lte) { // 若相等，说明该活动是在关键路径上的关键活动
                        System.out.println("<" + adjList.get(j).data + ", " + adjList.get(k).data + "> weight : " + e.weight);
                    }
                }
            }
        }
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

        ALGraph<Character> alGraph = new ALGraph<>(amGraph);

        System.out.println("无向图，采用邻接表存储结构，进行深度遍历：");
        alGraph.dfsTraverse();
        System.out.println();
        System.out.println("无向图，采用邻接表存储结构，进行广度遍历：");
        alGraph.bfsTraverse();

        /**
         * 头插法建立的邻接表，遍历结果：
         *   无向图，采用邻接表存储结构，进行深度遍历：
         *   A F G H E D I C B
         *   无向图，采用邻接表存储结构，进行广度遍历：
         *   A F B G E I C H D
         * 尾插法建立的邻接表，遍历结果：
         *   无向图，采用邻接表存储结构，进行深度遍历：
         *   A B C D E F G H I
         *   无向图，采用邻接表存储结构，进行广度遍历：
         *   A B F C G I E D H
         */
    }

    /**
     * 测试：拓扑排序
     */
    public static void testTopologicalSort() {
        String[] vertexes = new String[]{"V0", "V1", "V2", "V3", "V4", "V5", "V6", "V7", "V8", "V9", "V10", "V11", "V12", "V13"};
        AMGraph<String> amGraph = new AMGraph<>(vertexes);
        amGraph.insertWeightOfEdge(0, 4, 1);
        amGraph.insertWeightOfEdge(0, 5, 1);
        amGraph.insertWeightOfEdge(0, 11, 1);
        amGraph.insertWeightOfEdge(1, 2, 1);
        amGraph.insertWeightOfEdge(1, 4, 1);
        amGraph.insertWeightOfEdge(1, 8, 1);
        amGraph.insertWeightOfEdge(2, 5, 1);
        amGraph.insertWeightOfEdge(2, 6, 1);
        amGraph.insertWeightOfEdge(2, 9, 1);
        amGraph.insertWeightOfEdge(3, 2, 1);
        amGraph.insertWeightOfEdge(3, 13, 1);
        amGraph.insertWeightOfEdge(4, 7, 1);
        amGraph.insertWeightOfEdge(5, 8, 1);
        amGraph.insertWeightOfEdge(5, 12, 1);
        amGraph.insertWeightOfEdge(6, 5, 1);
        amGraph.insertWeightOfEdge(8, 7, 1);
        amGraph.insertWeightOfEdge(9, 10, 1);
        amGraph.insertWeightOfEdge(9, 11, 1);
        amGraph.insertWeightOfEdge(10, 13, 1);
        amGraph.insertWeightOfEdge(12, 9, 1);

        ALGraph<String> alGraph = new ALGraph<>(amGraph);
        System.out.println("该有向图进行拓扑排序是否成功：" + alGraph.topologicalSort());

        /**
         * 头插法建立的邻接表，进行拓扑排序：
         *   当前图的拓扑排序为：V3 -> V1 -> V2 -> V6 -> V0 -> V5 -> V12 -> V9 -> V11 -> V10 -> V13 -> V8 -> V4 -> V7 ->  NULL
         *   该有向图进行拓扑排序是否成功：true
         * 尾插法建立的邻接表，进行拓扑排序：
         *   当前图的拓扑排序为：V3 -> V1 -> V2 -> V6 -> V0 -> V4 -> V5 -> V8 -> V7 -> V12 -> V9 -> V10 -> V13 -> V11 ->  NULL
         *   该有向图进行拓扑排序是否成功：true
         */
    }

    /**
     * 测试：关键路径算法
     */
    public static void testCriticalPath() {
        String[] vertexes = new String[]{"V0", "V1", "V2", "V3", "V4", "V5", "V6", "V7", "V8", "V9"};
        AMGraph<String> amGraph = new AMGraph<>(vertexes);
        amGraph.insertWeightOfEdge(0, 1, 3);
        amGraph.insertWeightOfEdge(0, 2, 4);
        amGraph.insertWeightOfEdge(1, 3, 5);
        amGraph.insertWeightOfEdge(1, 4, 6);
        amGraph.insertWeightOfEdge(2, 3, 8);
        amGraph.insertWeightOfEdge(2, 5, 7);
        amGraph.insertWeightOfEdge(3, 4, 3);
        amGraph.insertWeightOfEdge(4, 6, 9);
        amGraph.insertWeightOfEdge(4, 7, 4);
        amGraph.insertWeightOfEdge(5, 7, 6);
        amGraph.insertWeightOfEdge(6, 9, 2);
        amGraph.insertWeightOfEdge(7, 8, 5);
        amGraph.insertWeightOfEdge(8, 9, 3);

        ALGraph<String> alGraph = new ALGraph<>(amGraph);
        alGraph.criticalPath();

        /**
         * 头插法建立的邻接表：
         *   TopologicalSort : V0 -> V1 -> V2 -> V3 -> V4 -> V6 -> V5 -> V7 -> V8 -> V9 -> NULL
         *   etv : 0 -> 3 -> 4 -> 12 -> 15 -> 11 -> 24 -> 19 -> 24 -> 27
         *   ltv : 0 -> 7 -> 4 -> 12 -> 15 -> 13 -> 25 -> 19 -> 24 -> 27
         *   <V0, V2> weight : 4
         *   <V2, V3> weight : 8
         *   <V3, V4> weight : 3
         *   <V4, V7> weight : 4
         *   <V7, V8> weight : 5
         *   <V8, V9> weight : 3
         * 尾插法建立的邻接表：
         *   TopologicalSort : V0 -> V2 -> V5 -> V1 -> V3 -> V4 -> V7 -> V8 -> V6 -> V9 -> NULL
         *   etv : 0 -> 3 -> 4 -> 12 -> 15 -> 11 -> 24 -> 19 -> 24 -> 27
         *   ltv : 0 -> 7 -> 4 -> 12 -> 15 -> 13 -> 25 -> 19 -> 24 -> 27
         *   <V0, V2> weight : 4
         *   <V2, V3> weight : 8
         *   <V3, V4> weight : 3
         *   <V4, V7> weight : 4
         *   <V7, V8> weight : 5
         *   <V8, V9> weight : 3
         */
    }
}


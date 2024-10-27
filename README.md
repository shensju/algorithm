# 数据结构与算法 DSA

## 数组 Array

- GenericArray 通用数组，支持泛型，支持动态扩容，包含增删改查基本操作
- OrderedArray 有序数组，支持泛型，支持动态扩容，包含增删改查基本操作

## 链表 List

- SinglyLinkedList 单向链表，支持泛型，包含增删改查基本操作
- DoublyLinkedList 双向链表，支持泛型，包含增删改查基本操作

## 图 Graph

1）以邻接矩阵作为存储结构，实现的算法如下：

- 图的深度优先遍历（Depth First Search）
- 图的广度优先遍历（Breadth First Search）
- 普里姆（Prim）算法，生成连通图的最小生成树
- 克鲁斯卡尔（Kruskal）算法，生成连通图的最小生成树
- 迪杰斯特拉（Dijkstra）算法，求单源点的最短路径
- 弗洛伊德（Floyd）算法，求所有顶点到所有顶点的最短路径

2）以邻接表作为存储结构，实现的算法如下：

- 图的深度优先遍历（Depth First Search）
- 图的广度优先遍历（Breadth First Search）
- 拓扑排序（TopologicalSort）算法，求有向图的拓扑排序
- 关键路径（CriticalPath）算法，求带权有向图的关键路径
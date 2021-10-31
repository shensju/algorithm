#include <stdio.h>
#include <stdlib.h>

#define OK 1
#define ERROR 0
#define TRUE 1
#define FALSE 0

#define MAXEDGE 20
#define MAXVEX 20
#define GRAPH_INFINITY 65535

typedef int Status;

typedef int VertexType;    /* 顶点类型应由用户定义 */
typedef int EdgeType;       /* 边上的权值类型应由用户定义 */

/* 邻接矩阵结构 */
typedef struct {
    VertexType vexs[MAXVEX];       /* 顶点表 */
    EdgeType arc[MAXVEX][MAXVEX];  /* 邻接矩阵，可看做边表 */
    int numVertexes, numEdges;     /* 图中当前的顶点数和边数 */
}MGraph;

typedef int Patharc[MAXVEX];         /* 用于存储最短路径下标的数组 */
typedef int ShortPathTable[MAXVEX];  /* 用于存储到各点最短路径的带权长度和 */

/* 构建邻接矩阵 */
void CreateMGraph(MGraph *G) {
    int i, j;

    G -> numEdges = 16;
    G -> numVertexes = 9;

    /* 读入顶点信息，建立顶点表 */
    G -> vexs[0] = 0;
    G -> vexs[1] = 1;
    G -> vexs[2] = 2;
    G -> vexs[3] = 3;
    G -> vexs[4] = 4;
    G -> vexs[5] = 5;
    G -> vexs[6] = 6;
    G -> vexs[7] = 7;
    G -> vexs[8] = 8;

    /* 初始化 */
    for (i = 0; i < G -> numVertexes; i++) {
        for (j = 0; j < G -> numVertexes; j++) {
            if (i == j)
                G -> arc[i][j] = 0;
            else
                G -> arc[i][j] = G -> arc[j][i] =  GRAPH_INFINITY;
        }
    }

    G -> arc[0][1] = 1;
    G -> arc[0][2] = 5;
    G -> arc[1][2] = 3;
    G -> arc[1][3] = 7;
    G -> arc[1][4] = 5;
    G -> arc[2][4] = 1;
    G -> arc[2][5] = 7;
    G -> arc[3][4] = 2;
    G -> arc[3][6] = 3;
    G -> arc[4][5] = 3;
    G -> arc[4][6] = 6;
    G -> arc[4][7] = 9;
    G -> arc[5][7] = 5;
    G -> arc[6][7] = 2;
    G -> arc[6][8] = 7;
    G -> arc[7][8] = 4;
    
    /* 无向图 */
    for (i = 0; i < G -> numVertexes; i++) {
        for (j = i; j < G -> numVertexes; j++) {
            G -> arc[j][i] = G -> arc[i][j];
        }
    }
} 

/* Dijkstra算法，求无向网G的v0顶点到其余顶点v的最短路径P[v]及带权长度和D[v] */
/* P[v]的值为前驱顶点下标，D[v]表示v0到v的最短路径带权长度和 */
void ShortestPath_Dijkstra(MGraph G, int v0, Patharc *P, ShortPathTable *D) {
    int v, w, k, min;
    int final[MAXVEX];  /* final[w]=1，表示求得顶点v0至vw的最短路径 */
    for (v = 0; v < G.numVertexes; v++) {  /* 初始化数据 */
        final[v] = 0;            /* 全部顶点初始化为未知最短路径状态 */
        (*D)[v] = G.arc[v0][v];  /* 将与v0点有连线的顶点加上权值 */
        (*P)[v] = v0;            /* 初始化路径数组P为v0  */
    }
    
    final[v0] = 1;  /* v0至v0不需要求路径 */
    
    /* 开始主循环，每次求得v0到某个v顶点的最短路径 */
    for (v = 1; v < G.numVertexes; v++) {
        min = GRAPH_INFINITY;  /* 当前所知离v0顶点的最短距离 */
        for (w = 0; w < G.numVertexes; w++) {  /* 寻找离v0最近的顶点 */
            if (!final[w] && (*D)[w] < min) {
                k = w;
                min = (*D)[w];  /* w顶点离v0顶点更近 */
            }
        }
        final[k] = 1;  /* 将目前找到的最近的顶点置为1 */
        for (w = 0; w < G.numVertexes; w++) {  /* 修正当前最短路径及距离 */
            /* 如果经过k顶点的路径比现在这条路径的长度短的话 */
            if (!final[w] && min + G.arc[k][w] < (*D)[w]) {
                (*D)[w] = min + G.arc[k][w];  /* 修改当前路径带权长度和 */
                (*P)[w] = k;  /* 修改前驱顶点下标 */
            }
        }
    }
}

int main() {
    
    int i, j, v0;
    MGraph G;
    Patharc P;
    ShortPathTable D;  /* 求某点到其余各点的最短路径带权长度和 */
    v0 = 0;  /* 求下标为v0的顶点到各点的最短路径带权长度和 */
    
    CreateMGraph(&G);
    ShortestPath_Dijkstra(G, v0, &P, &D);
    
    printf("最短路径下标的数组为：\n");
    for (i = 0; i < G.numVertexes; i++) {
        printf("%d ", P[i]);
    }
    printf("\n源点v%d到各顶点的最短路径带权长度和为：\n", G.vexs[v0]); 
    for (i = 0; i < G.numVertexes; i++) {
        printf("v%d - v%d : %d \n", G.vexs[v0], G.vexs[i], D[i]);
    }
    return 0;
    
    /*
        最短路径下标的数组为：
        0 0 1 4 2 4 3 6 7
        源点v0到各顶点的最短路径带权长度和为：
        v0 - v0 : 0
        v0 - v1 : 1
        v0 - v2 : 4
        v0 - v3 : 7
        v0 - v4 : 5
        v0 - v5 : 8
        v0 - v6 : 10
        v0 - v7 : 12
        v0 - v8 : 16
    */
}

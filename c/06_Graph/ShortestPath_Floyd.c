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

typedef int Patharc[MAXVEX][MAXVEX];         /* 用于存储最短路径下标的数组 */
typedef int ShortPathTable[MAXVEX][MAXVEX];  /* 用于存储到各点最短路径的带权长度和 */

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

/* Floyd算法，求无向网G中各顶点v到其余顶点w的最短路径P[v][w]及带权长度和D[v][w] */    
void ShortestPath_Floyd(MGraph G, Patharc *P, ShortPathTable *D) {
    int v, w, k;
    for (v = 0; v < G.numVertexes; v++) {      /* 初始化D与P */
        for (w = 0; w < G.numVertexes; w++) {
            (*D)[v][w] = G.arc[v][w];          /* D[v][w]值即为对应点间的权值 */
            (*P)[v][w] = w;                    /* 初始化P */
        }
    }
    for (k = 0; k < G.numVertexes; k++) {
        for (v = 0; v < G.numVertexes; v++) {
            for (w = 0; w < G.numVertexes; w++) {
                /* 如果经过下标为k顶点路径比原两点间路径更短 */
                if ((*D)[v][w] > (*D)[v][k] + (*D)[k][w]) {
                    (*D)[v][w] = (*D)[v][k] + (*D)[k][w];  /* 将当前两点间权值设为更小的一个 */
                    (*P)[v][w] = (*P)[v][k];               /* 路径设置为经过下标为k的顶点 */
                }
            }
        }
    }
}

/* 打印任意顶点之间的最短路径 */
void PrintShortestPath(MGraph G, Patharc *P, ShortPathTable *D) {
    int v, w, k;
    for (v = 0; v < G.numVertexes; v++) {
        for (w = v + 1; w < G.numVertexes; w++) {
            printf("v%d-v%d weight: %d, ", v, w, (*D)[v][w]);
            k = (*P)[v][w];            /* 获得第一个路径顶点下标 */
            printf("path : %d", v);    /* 打印源点 */
            while (k != w) {           /* 如果路径顶点下标不是终点 */
                printf(" -> %d", k);   /* 打印路径顶点 */
                k = (*P)[k][w];        /* 获得下一个路径顶点下标 */
            }
            printf(" -> %d\n", w);     /* 打印终点 */
        }
        printf("\n");
    }
}

int main() {
    
    int v, w, k;
    MGraph G;
    
    Patharc P;
    ShortPathTable D;  /* 求某点到其余各点的最短路径带权长度和 */
    
    CreateMGraph(&G);
    ShortestPath_Floyd(G, &P, &D);
    
    printf("最短路径带权长度和二维数组D\n");
    for (v = 0; v < G.numVertexes; v++) {
        for (w = 0; w < G.numVertexes; w++)
            printf("%d\t", D[v][w]);
        printf("\n");
    }
    printf("最短路径下标二维数组P\n");
    for (v = 0; v < G.numVertexes; v++) {
        for (w = 0; w < G.numVertexes; w++)
            printf("%d\t", P[v][w]);
        printf("\n");
    }
    printf("任意顶点之间的最短路径\n");
    PrintShortestPath(G, &P, &D);
    
    return 0;
    
    /*
        最短路径带权长度和二维数组D
        0       1       4       7       5       8       10      12      16
        1       0       3       6       4       7       9       11      15
        4       3       0       3       1       4       6       8       12
        7       6       3       0       2       5       3       5       9
        5       4       1       2       0       3       5       7       11
        8       7       4       5       3       0       7       5       9
        10      9       6       3       5       7       0       2       6
        12      11      8       5       7       5       2       0       4
        16      15      12      9       11      9       6       4       0
        最短路径下标二维数组P
        0       1       1       1       1       1       1       1       1
        0       1       2       2       2       2       2       2       2
        1       1       2       4       4       4       4       4       4
        4       4       4       3       4       4       6       6       6
        2       2       2       3       4       5       3       3       3
        4       4       4       4       4       5       7       7       7
        3       3       3       3       3       7       6       7       7
        6       6       6       6       6       5       6       7       8
        7       7       7       7       7       7       7       7       8
        任意顶点之间的最短路径
        v0-v1 weight: 1, path : 0 -> 1
        v0-v2 weight: 4, path : 0 -> 1 -> 2
        v0-v3 weight: 7, path : 0 -> 1 -> 2 -> 4 -> 3
        v0-v4 weight: 5, path : 0 -> 1 -> 2 -> 4
        v0-v5 weight: 8, path : 0 -> 1 -> 2 -> 4 -> 5
        v0-v6 weight: 10, path : 0 -> 1 -> 2 -> 4 -> 3 -> 6
        v0-v7 weight: 12, path : 0 -> 1 -> 2 -> 4 -> 3 -> 6 -> 7
        v0-v8 weight: 16, path : 0 -> 1 -> 2 -> 4 -> 3 -> 6 -> 7 -> 8

        v1-v2 weight: 3, path : 1 -> 2
        v1-v3 weight: 6, path : 1 -> 2 -> 4 -> 3
        v1-v4 weight: 4, path : 1 -> 2 -> 4
        v1-v5 weight: 7, path : 1 -> 2 -> 4 -> 5
        v1-v6 weight: 9, path : 1 -> 2 -> 4 -> 3 -> 6
        v1-v7 weight: 11, path : 1 -> 2 -> 4 -> 3 -> 6 -> 7
        v1-v8 weight: 15, path : 1 -> 2 -> 4 -> 3 -> 6 -> 7 -> 8

        v2-v3 weight: 3, path : 2 -> 4 -> 3
        v2-v4 weight: 1, path : 2 -> 4
        v2-v5 weight: 4, path : 2 -> 4 -> 5
        v2-v6 weight: 6, path : 2 -> 4 -> 3 -> 6
        v2-v7 weight: 8, path : 2 -> 4 -> 3 -> 6 -> 7
        v2-v8 weight: 12, path : 2 -> 4 -> 3 -> 6 -> 7 -> 8

        v3-v4 weight: 2, path : 3 -> 4
        v3-v5 weight: 5, path : 3 -> 4 -> 5
        v3-v6 weight: 3, path : 3 -> 6
        v3-v7 weight: 5, path : 3 -> 6 -> 7
        v3-v8 weight: 9, path : 3 -> 6 -> 7 -> 8

        v4-v5 weight: 3, path : 4 -> 5
        v4-v6 weight: 5, path : 4 -> 3 -> 6
        v4-v7 weight: 7, path : 4 -> 3 -> 6 -> 7
        v4-v8 weight: 11, path : 4 -> 3 -> 6 -> 7 -> 8

        v5-v6 weight: 7, path : 5 -> 7 -> 6
        v5-v7 weight: 5, path : 5 -> 7
        v5-v8 weight: 9, path : 5 -> 7 -> 8

        v6-v7 weight: 2, path : 6 -> 7
        v6-v8 weight: 6, path : 6 -> 7 -> 8

        v7-v8 weight: 4, path : 7 -> 8
    */
}

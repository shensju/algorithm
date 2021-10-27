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

typedef char VertexType;    /* 顶点类型应由用户定义 */
typedef int EdgeType;       /* 边上的权值类型应由用户定义 */

/* 邻接矩阵结构 */
typedef struct {
    VertexType vexs[MAXVEX];       /* 顶点表 */
    EdgeType arc[MAXVEX][MAXVEX];  /* 邻接矩阵，可看做边表 */
    int numVertexes, numEdges;     /* 图中当前的顶点数和边数 */
}MGraph;

/* 构建邻接矩阵 */
void CreateMGraph(MGraph *G) {
    int i, j;
    
    G -> numEdges = 15;
    G -> numVertexes = 9;
    
    /* 读入顶点信息，建立顶点表 */
//    G -> vexs[0] = 'v0';
//    G -> vexs[1] = 'v1';
//    G -> vexs[2] = 'v2';
//    G -> vexs[3] = 'v3';
//    G -> vexs[4] = 'v4';
//    G -> vexs[5] = 'v5';
//    G -> vexs[6] = 'v6';
//    G -> vexs[7] = 'v7';
//    G -> vexs[8] = 'v8';
    
    /* 初始化 */
    for (i = 0; i < G -> numVertexes; i++) {
        for (j = 0; j < G -> numVertexes; j++) {
            if (i == j)
                G -> arc[i][j] = 0;
            else
                G -> arc[i][j] = G -> arc[j][i] =  GRAPH_INFINITY;
        }
    }
	
    /* 读入边信息，建立边表 */
    G -> arc[0][1] = 10;
    G -> arc[0][5] = 11;
    G -> arc[1][2] = 18;
    G -> arc[1][8] = 12;
    G -> arc[1][6] = 16;
    G -> arc[2][8] = 8;
    G -> arc[2][3] = 22;
    G -> arc[3][8] = 21;
    G -> arc[3][6] = 24;
    G -> arc[3][7] = 16;
    G -> arc[3][4] = 20;
    G -> arc[4][7] = 7;
    G -> arc[4][5] = 26;
    G -> arc[5][6] = 17;
    G -> arc[6][7] = 19;
    
    /* 无向图 */
    for (i = 0; i < G -> numVertexes; i++) {
        for (j = i; j < G -> numVertexes; j++) {
            G -> arc[j][i] = G -> arc[i][j];
        }
    }
}

/* Prim算法生成最小生成树 */
void MiniSpanTree_Prim(MGraph G) {
    int min, i, j, k;
    int adjvex[MAXVEX];   /* 保存相关顶点下标 */
    int lowcost[MAXVEX];  /* 保存相关顶点间边的权值 */
    lowcost[0] = 0;       /* 初始化第一个权值为0，即v0加入生成树 */
    adjvex[0] = 0;        /* 初始化第一个顶点下标为0 */
    for (i = 1; i < G.numVertexes; i++) {  /* 循环除下标为0外的全部顶点 */ 
        lowcost[i] = G.arc[0][i];    /* 将v0顶点与之有边的权值存入数组 */
        adjvex[i] = 0;               /* 初始化都为v0的下标 */
    }
    for (i = 1; i < G.numVertexes; i++) {
        min = GRAPH_INFINITY;  /* 初始化最小权值为∞，通常设置为不可能的大数字如32767、65535等 */
        j = 1, k = 0;
        while (j < G.numVertexes) {  /* 循环全部顶点 */
            if (lowcost[j] != 0 && lowcost[j] < min) {  /* 如果权值不为0且权值小于min */
                min = lowcost[j];  /* 让当前权值成为最小值 */
                k = j;  /* 将当前最小值的下标存入k */
            }
            j++;
        }
        printf("(%d, %d)\n", adjvex[k], k);  /* 打印当前顶点边中权值最小的边 */
        lowcost[k] = 0;  /* 将当前顶点的权值设置为0，表示此顶点已经完成任务 */
        for (j = 1; j < G.numVertexes; j++) {  /* 循环所有顶点 */
            if (lowcost[j] != 0 && G.arc[k][j] < lowcost[j]) {  /* 如果下标为k顶点各边权值小于此前这些顶点未被加入生成树权值 */
                lowcost[j] = G.arc[k][j];  /* 将较小的权值存入lowcost相应位置 */
                adjvex[j] = k;  /* 将下标为k的顶点存入adjvex */
            }
        }
    }
}

int main () {
	
    MGraph G;
    CreateMGraph(&G);
    MiniSpanTree_Prim(G);
    
    return 0;
    
    /*
        (0, 1)
        (0, 5)
        (1, 8)
        (8, 2)
        (1, 6)
        (6, 7)
        (7, 4)
        (7, 3)
    */
}

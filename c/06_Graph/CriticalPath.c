#include <stdio.h>
#include <stdlib.h>

#define OK 1
#define ERROR 0
#define TRUE 1
#define FALSE 0

#define MAXEDGE 13
#define MAXVEX 10
#define GRAPH_INFINITY 65535

typedef int Status;
typedef int VertexType;    /* 顶点类型应由用户定义 */
typedef int EdgeType;       /* 边上的权值类型应由用户定义 */

int etv[MAXVEX], ltv[MAXVEX];  /* 事件最早发生时间和最迟发生时间数组 */
int *stack2;    /* 用于存储拓扑序列的栈 */
int top2;	    /* 用于stack2的指针 */

/* 邻接矩阵结构 */
typedef struct {
    VertexType vexs[MAXVEX];       /* 顶点表 */
    EdgeType arc[MAXVEX][MAXVEX];  /* 邻接矩阵，可看做边表 */
    int numVertexes, numEdges;     /* 图中当前的顶点数和边数 */
}MGraph;

/* 邻接表结构****************** */
typedef struct EdgeNode {   /* 边表结点 */
    int adjvex;             /* 邻接点域,存储该顶点对应的下标 */
    EdgeType weight;        /* 用于存储权值，对于非网图可以不需要 */
    struct EdgeNode *next;  /* 链域，指向下一个邻接点 */
}EdgeNode;

typedef struct VertexNode {     /* 顶点表结点 */ 
    int in;                     /* 顶点入度 */ 
    VertexType data;            /* 顶点域，存储顶点信息 */
    EdgeNode *firstedge;        /* 边表头指针 */
}VertexNode, AdjList[MAXVEX];

typedef struct {
    AdjList adjList;
    int numVertexes, numEdges;  /* 图中当前的顶点数和边数 */
}graphAdjList, *GraphAdjList;
/* **************************** */

/* 构建邻接矩阵 */
void CreateMGraph(MGraph *G) {
    int i, j;
    
    G -> numEdges = MAXEDGE;
    G -> numVertexes = MAXVEX;
    
    /* 读入顶点信息，建立顶点表 */
    for (i = 0; i < G -> numVertexes; i++)
        G -> vexs[i] = i;
    
    /* 初始化 */
    for (i = 0; i < G -> numVertexes; i++) {
        for (j = 0; j < G -> numVertexes; j++) {
            if (i == j)
                G -> arc[i][j] = 0;
            else
                G -> arc[i][j] = GRAPH_INFINITY;
        }
    }
    
    /* 有向无环网 */
    G -> arc[0][1] = 3;
    G -> arc[0][2] = 4;
    G -> arc[1][3] = 5;
    G -> arc[1][4] = 6;
    G -> arc[2][3] = 8;
    G -> arc[2][5] = 7;
    G -> arc[3][4] = 3;
    G -> arc[4][6] = 9;
    G -> arc[4][7] = 4;
    G -> arc[5][7] = 6;
    G -> arc[6][9] = 2;
    G -> arc[7][8] = 5;
    G -> arc[8][9] = 3;
}

/* 利用邻接矩阵构建邻接表 */
void CreateALGraph(MGraph G, GraphAdjList *GL) {
    int i, j;
    EdgeNode *e;
    
    *GL = (GraphAdjList) malloc (sizeof(graphAdjList));
    (*GL) -> numVertexes = G.numVertexes;
    (*GL) -> numEdges = G.numEdges;
    
    /* 读入顶点信息，建立顶点表 */
    for (i = 0; i < G.numVertexes; i++) {
        (*GL) -> adjList[i].in = 0;
        (*GL) -> adjList[i].data = G.vexs[i];
        (*GL) -> adjList[i].firstedge = NULL;
	}
    
    /* 读入边信息，建立边表 */
    for (i = 0; i < G.numVertexes; i++) {
        for (j = 0; j < G.numVertexes; j++) {
            if (G.arc[i][j] != 0 && G.arc[i][j] < GRAPH_INFINITY) {
                e = (EdgeNode *) malloc (sizeof(EdgeNode));
                e -> adjvex = j;
                e -> weight = G.arc[i][j];
                e -> next = (*GL) -> adjList[i].firstedge;
                (*GL) -> adjList[i].firstedge = e;
                (*GL) -> adjList[j].in++;
            }
        }
    }
}

/* 拓扑排序，若GL无回路，则输出拓扑排序序列并返回1，若有回路返回0。 */
Status TopologicalSort(GraphAdjList GL) {
    EdgeNode *e;
    int i, k, gettop;
    int top = 0;      /* 用于栈指针下标 */
    int count = 0;    /* 用于统计输出顶点的个数 */ 
    int *stack;       /* 建栈存储入度为0的顶点 */ 
    stack = (int *) malloc (GL -> numVertexes * sizeof(int));
    for (i = 0; i < GL -> numVertexes; i++)
        if (GL -> adjList[i].in == 0)  /* 将入度为0的顶点入栈 */
            stack[++top] = i;
    
	top2 = 0;  /* 初始化为0 */
    for (i = 0; i < GL -> numVertexes; i++)
        etv[i] = 0;  /* 初始化为0 */
    stack2 = (int *) malloc (GL -> numVertexes * sizeof(int));  /* 初始化拓扑序列栈 */
    
    printf("TopologicalSort:\t");
    
    while (top != 0) {
        gettop = stack[top--];
        printf("%d -> ", GL -> adjList[gettop].data);
        count++;  /* 输出i号顶点，并计数 */
        
        stack2[++top2] = gettop;  /* 将弹出的顶点序号压入拓扑序列的栈 */
        
        for (e = GL -> adjList[gettop].firstedge; e; e = e -> next) {
            k = e -> adjvex;
            if (!(--GL -> adjList[k].in))  /* 将i号顶点的邻接点的入度减1，如果减1后为0，则入栈 */
                stack[++top] = k;
            
            if ((etv[gettop] + e -> weight) > etv[k])  /* 求各顶点事件的最早发生时间etv值 */
                etv[k] = etv[gettop] + e -> weight;
        }
    }
    printf("NULL\n");
    if (count < GL -> numVertexes)
        return ERROR;
    else
        return OK;
}

/* 求关键路径，GL为有向网，输出G的各项关键活动 */
void CriticalPath(GraphAdjList GL) {
    EdgeNode *e;
    int i, gettop, k, j;
    int ete, lte;  /* 声明活动最早发生时间和最迟发生时间变量 */
    TopologicalSort(GL);  /* 求拓扑序列，计算数组etv和stack2的值 */
    printf("etv:\t");
    for (i = 0; i < GL -> numVertexes - 1; i++)
        printf("%d -> ", etv[i]);
    printf("%d\n", etv[GL -> numVertexes - 1]);

    for (i = 0; i < GL -> numVertexes; i++)
        ltv[i] = etv[GL -> numVertexes - 1];  /* 初始化ltv */  
    while (top2 != 0) {  /* 计算ltv */
        gettop = stack2[top2--];
        for (e = GL -> adjList[gettop].firstedge; e; e = e -> next) {  /* 求各顶点事件的最迟发生时间ltv值 */
            k = e -> adjvex;
            if (ltv[k] - e -> weight < ltv[gettop])
                ltv[gettop] = ltv[k] - e -> weight;
        }
    }
    printf("ltv:\t");
    for (i = 0; i < GL -> numVertexes - 1; i++)
        printf("%d -> ", ltv[i]);
    printf("%d\n", ltv[GL -> numVertexes - 1]);
    
    for (j = 0; j < GL -> numVertexes; j++) {  /* 求ete,lte和关键活动 */
        for (e = GL -> adjList[j].firstedge; e; e = e -> next) {
            k = e -> adjvex;
            ete = etv[j];  /* 活动最早发生时间 */
            lte = ltv[k] - e -> weight;  /* 活动最迟发生时间 */
            if (ete == lte)  /* 两者相等即在关键路径上 */
                printf("<v%d - v%d> length: %d\n", GL -> adjList[j].data, GL -> adjList[k].data, e -> weight);
        }
    }
}

int main() {
    
    MGraph G;
    GraphAdjList GL;
    CreateMGraph(&G);
    CreateALGraph(G, &GL);
    CriticalPath(GL);
    
    return 0;
    
    /*
        TopologicalSort:        0 -> 1 -> 2 -> 3 -> 4 -> 6 -> 5 -> 7 -> 8 -> 9 -> NULL
        etv:    0 -> 3 -> 4 -> 12 -> 15 -> 11 -> 24 -> 19 -> 24 -> 27
        ltv:    0 -> 7 -> 4 -> 12 -> 15 -> 13 -> 25 -> 19 -> 24 -> 27
        <v0 - v2> length: 4
        <v2 - v3> length: 8
        <v3 - v4> length: 3
        <v4 - v7> length: 4
        <v7 - v8> length: 5
        <v8 - v9> length: 3
    */
}

#include <stdio.h>
#include <stdlib.h>

#define OK 1
#define ERROR 0
#define TRUE 1
#define FALSE 0

#define MAXEDGE 20
#define MAXVEX 14

typedef int Status;

typedef int VertexType;    /* 顶点类型应由用户定义 */
typedef int EdgeType;       /* 边上的权值类型应由用户定义 */

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
    for (i = 0; i < G -> numVertexes; i++)
        for (j = 0; j < G -> numVertexes; j++)
            G -> arc[i][j] = 0;
    
    /* 有向无环图 */
    G -> arc[0][4] = 1;
    G -> arc[0][5] = 1;
    G -> arc[0][11] = 1;
    G -> arc[1][2] = 1;
    G -> arc[1][4] = 1;
    G -> arc[1][8] = 1;
    G -> arc[2][5] = 1;
    G -> arc[2][6] = 1;
    G -> arc[2][9] = 1;
    G -> arc[3][2] = 1;
    G -> arc[3][13] = 1;
    G -> arc[4][7] = 1;
    G -> arc[5][8] = 1;
    G -> arc[5][12] = 1;
    G -> arc[6][5] = 1;
    G -> arc[8][7] = 1;
    G -> arc[9][10] = 1;
    G -> arc[9][11] = 1;
    G -> arc[10][13] = 1;
    G -> arc[12][9] = 1;
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
            if (G.arc[i][j] == 1) {
                e = (EdgeNode *) malloc (sizeof(EdgeNode));
                e -> adjvex = j;
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
    while (top != 0) {
        gettop = stack[top--];
        printf("%d -> ", GL -> adjList[gettop].data);
        count++;  /* 输出i号顶点，并计数 */
        for (e = GL -> adjList[gettop].firstedge; e; e = e -> next) {
            k = e -> adjvex;
            if (!(--GL -> adjList[k].in))  /* 将i号顶点的邻接点的入度减1，如果减1后为0，则入栈 */
                stack[++top] = k;
        }
    }
    printf("NULL\n");
    if (count < GL -> numVertexes)
        return ERROR;
    else
        return OK;
}

int main() {

    MGraph G;
    GraphAdjList GL;
    int result;
    CreateMGraph(&G);
    CreateALGraph(G, &GL);
    result = TopologicalSort(GL);
    
    return 0;
    
    /*
        3 -> 1 -> 2 -> 6 -> 0 -> 4 -> 5 -> 8 -> 7 -> 12 -> 9 -> 10 -> 13 -> 11 -> NULL
    */
}

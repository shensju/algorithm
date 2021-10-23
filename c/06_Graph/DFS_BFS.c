#include <stdio.h>
#include <stdlib.h>

#define OK 1
#define ERROR 0
#define TRUE 1
#define FALSE 0

#define MAXSIZE 9    /* 存储空间初始分配量 */
#define MAXEDGE 15   /* 最大边数，应由用户定义 */
#define MAXVEX 9     /* 最大顶点数，应由用户定义 */

typedef int Status;
typedef int Boolean;

typedef char VertexType;    /* 顶点类型应由用户定义 */
typedef int EdgeType;       /* 边上的权值类型应由用户定义 */

/* 邻接矩阵结构 */
typedef struct {
    VertexType vexs[MAXVEX];       /* 顶点表 */
    EdgeType arc[MAXVEX][MAXVEX];  /* 邻接矩阵，可看做边表 */
    int numVertexes, numEdges;     /* 图中当前的顶点数和边数 */
}MGraph;

/* 邻接表结构****************** */
typedef struct EdgeNode {
    int adjvex;             /* 邻接点域,存储该顶点对应的下标 */
    EdgeType weight;        /* 用于存储权值，对于非网图可以不需要 */
    struct EdgeNode *next;  /* 链域，指向下一个邻接点 */
}EdgeNode;

typedef struct VertexNode {
    VertexType data;            /* 顶点域，存储顶点信息 */
    EdgeNode *firstedge;        /* 边表头指针 */
}VertexNode, AdjList[MAXVEX];

typedef struct {
    AdjList adjList;
    int numVertexes, numEdges;  /* 图中当前的顶点数和边数 */
}GraphAdjList;
/* **************************** */

/* 用到的队列结构与函数********************************** */
/* 循环队列的顺序存储结构 */
typedef struct {
    int data[MAXSIZE];
    int front;         /* 头指针 */
    int rear;          /* 尾指针，若队列不空，指向队列尾元素的下一个位置 */
}Queue;

/* 初始化一个空队列Q */
Status InitQueue(Queue *Q) {
    Q -> front = 0;
    Q -> rear = 0;
    return OK;
}

/* 若队列Q为空队列，则返回TRUE；否则返回FALSE */
Status QueueEmpty(Queue Q) {
    if (Q.front == Q.rear)  /* 队列空的判断 */
        return TRUE;
    else
        return FALSE;
}

/* 若队列未满，则插入元素e为Q新的队尾元素 */
Status EnQueue(Queue *Q, int e) {
    if ((Q -> rear + 1) % MAXSIZE == Q -> front)  /* 队列满的判断 */
        return ERROR;
    Q -> data[Q -> rear] = e;               /* 将元素e赋值给队尾 */
    Q -> rear = (Q -> rear + 1) % MAXSIZE;  /* rear指针向后移一位置，若到最后则转到数组头部 */
    return OK;
}

/* 若队列不空，则删除Q中队头元素，用e返回其值 */
Status DeQueue(Queue *Q, int *e) {
    if (Q -> front == Q -> rear)  /* 队列空的判断 */
        return ERROR;
    *e = Q -> data[Q -> front];               /* 将队头元素赋值给e */
    Q -> front = (Q -> front + 1) % MAXSIZE;  /* front指针向后移一位置，若到最后则转到数组头部 */
    return OK; 
}
/* ****************************************************** */

/* 构建邻接矩阵 */
void CreateMGraph(MGraph *G) {
    int i, j;
    
    G -> numEdges = 15;
    G -> numVertexes = 9; 
    
    /* 读入顶点信息，建立顶点表 */
    G -> vexs[0] = 'A';
    G -> vexs[1] = 'B';
    G -> vexs[2] = 'C';
    G -> vexs[3] = 'D';
    G -> vexs[4] = 'E';
    G -> vexs[5] = 'F';
    G -> vexs[6] = 'G';
    G -> vexs[7] = 'H';
    G -> vexs[8] = 'I';
    
    /* 初始化 */
    for (i = 0; i < G -> numVertexes; i++) {
        for (j = 0; j < G -> numVertexes; j++) {
            G -> arc[i][j] = 0;
        }
    }
    
    /* 读入边信息，建立边表 */
    G -> arc[0][1] = 1;
    G -> arc[0][5] = 1;
    G -> arc[1][2] = 1;
    G -> arc[1][6] = 1;
    G -> arc[1][8] = 1;
    G -> arc[2][3] = 1;
    G -> arc[2][8] = 1;
    G -> arc[3][4] = 1;
    G -> arc[3][6] = 1;
    G -> arc[3][7] = 1;
    G -> arc[3][8] = 1;
    G -> arc[4][5] = 1;
    G -> arc[4][7] = 1;
    G -> arc[5][6] = 1;
    G -> arc[6][7] = 1;
    
    /* 无向图 */
    for (i = 0; i < G -> numVertexes; i++) {
        for (j = i; j < G -> numVertexes; j++) {
            G -> arc[j][i] = G -> arc[i][j];
	}
    }
}

/* 利用邻接矩阵构建邻接表 */
void CreateALGraph(MGraph G, GraphAdjList *GL) {
    int i, j;
    EdgeNode *e;
    
    GL -> numVertexes = G.numVertexes;
    GL -> numEdges = G.numEdges;
    
    /* 读入顶点信息，建立顶点表 */
    for (i = 0; i < G.numVertexes; i++) {
        GL -> adjList[i].data = G.vexs[i];
        GL -> adjList[i].firstedge = NULL;
    }
    
    /* 读入边信息，建立边表 */
    for (i = 0; i < G.numVertexes; i++) {
        for (j = 0; j < G.numVertexes; j++) {
            if (G.arc[i][j] == 1) {
                e = (EdgeNode *) malloc (sizeof(EdgeNode));
                e -> adjvex = j;                             /* 邻接序号为j */
                e -> next = GL -> adjList[i].firstedge;      /* 将e指针指向当前顶点指向的结点 */ 
                GL -> adjList[i].firstedge = e;              /* 将当前顶点的指针指向e */
            }
        }
    }
}

Boolean visited1[MAXSIZE];  /* 访问标志的数组 */

/* 邻接矩阵的深度优先递归算法 */
void DFS1(MGraph G, int i) {
    int j;
    visited1[i] = TRUE;
    printf("%c ", G.vexs[i]);  /* 打印顶点，也可以其它操作 */
    for (j = 0; j < G.numVertexes; j++)
        if (G.arc[i][j] == 1 && !visited1[j])
            DFS1(G, j);  /* 对未访问的邻接顶点递归调用 */
}

/* 邻接矩阵的深度遍历操作 */
void DFSTraverse1(MGraph G) {
    int i;
    for (i = 0; i < G.numVertexes; i++)
        visited1[i] = FALSE;  /* 初始所有顶点状态都是未访问过状态 */
    for (i = 0; i < G.numVertexes; i++)
        if (!visited1[i])  /* 对未访问过的顶点调用DFS1，若是连通图，只会执行一次 */ 
            DFS1(G, i);
}

/* 邻接矩阵的广度遍历算法 */
void BFSTraverse1(MGraph G) {
    int i, j;
    Queue Q;
    for (i = 0; i < G.numVertexes; i++)
        visited1[i] = FALSE;
    InitQueue(&Q);  /* 初始化一辅助用的队列 */
    for (i = 0; i < G.numVertexes; i++) {
        if (!visited1[i]) {
            visited1[i] = TRUE;
            printf("%c ", G.vexs[i]);  /* 打印顶点，也可以其它操作 */
            EnQueue(&Q, i);
            while(!QueueEmpty(Q)) {
                DeQueue(&Q, &i);  /* 将队对元素出队列，赋值给i */
                for (j = 0; j < G.numVertexes; j++) {
                    if (G.arc[i][j] == 1 && !visited1[j]) {  /* 判断其它顶点若与当前顶点存在边且未访问过  */
                        visited1[j] = TRUE;
                        printf("%c ", G.vexs[j]);
                        EnQueue(&Q,j);
                    }
                }
            }
        }
    }
} 

Boolean visited2[MAXSIZE];  /* 访问标志的数组 */

/* 邻接表的深度优先递归算法 */
void DFS2(GraphAdjList *GL, int i) {
    EdgeNode *p;
    visited2[i] = TRUE;
    printf("%c ", GL -> adjList[i].data);  /* 打印顶点,也可以其它操作 */
    p = GL -> adjList[i].firstedge;
    while (p) {
        if (!visited2[p -> adjvex])
            DFS2(GL, p -> adjvex);  /* 对未访问的邻接顶点递归调用 */
        p = p -> next;
    }
}

/* 邻接表的深度遍历操作 */
void DFSTraverse2(GraphAdjList *GL) {
    int i;
    for (i = 0; i < GL -> numVertexes; i++)
        visited2[i] = FALSE;  /* 初始所有顶点状态都是未访问过状态 */
    for (i = 0; i < GL -> numVertexes; i++)
        if (!visited2[i])
            DFS2(GL, i);  /* 对未访问过的顶点调用DFS1，若是连通图，只会执行一次 */ 
}

/* 邻接表的广度遍历算法 */
void BFSTraverse2(GraphAdjList *GL) {
    int i;
    EdgeNode *p;
    Queue Q;
    for (i = 0; i < GL -> numVertexes; i++)
        visited2[i] = FALSE;
    InitQueue(&Q);
    for (i = 0; i < GL -> numVertexes; i++) {
        if (!visited2[i]) {
            visited2[i] = TRUE;
            printf("%c ", GL -> adjList[i].data);  /* 打印顶点，也可以其它操作 */
            EnQueue(&Q, i);
            while (!QueueEmpty(Q)) {
                DeQueue(&Q, &i);  /* 将队对元素出队列，赋值给i */
                p = GL -> adjList[i].firstedge;  /* 找到当前顶点的边表链表头指针 */
                while (p) {
                    if (!visited2[p -> adjvex]) {  /* 若此顶点未被访问 */
                        visited2[p -> adjvex] = TRUE;
                        printf("%c ", GL -> adjList[p -> adjvex].data);
                        EnQueue(&Q, p -> adjvex);  /* 将此顶点入队列 */
                    }
                    p = p -> next;  /* 指针指向下一个邻接点 */
                }
            }
        }
    }
}

int main() {
    MGraph G;
    CreateMGraph(&G);
    printf("无向图，采用邻接矩阵存储结构，进行深度遍历：");
    DFSTraverse1(G);
    printf("\n无向图，采用邻接矩阵存储结构，进行广度遍历：");
    BFSTraverse1(G);
    
    GraphAdjList GL;
    CreateALGraph(G, &GL);
    printf("\n无向图，采用邻接表存储结构，进行深度遍历：");
    DFSTraverse2(&GL);
    printf("\n无向图，采用邻接表存储结构，进行广度遍历：");
    BFSTraverse2(&GL);
    
    return 0;
    
    /*
        无向图，采用邻接矩阵存储结构，进行深度遍历：A B C D E F G H I
        无向图，采用邻接矩阵存储结构，进行广度遍历：A B F C G I E D H
        无向图，采用邻接表存储结构，进行深度遍历：A F G H E D I C B
        无向图，采用邻接表存储结构，进行广度遍历：A F B G E I C H D
    */
}

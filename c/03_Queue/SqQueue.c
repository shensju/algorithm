#include <stdio.h>
#include <stdlib.h>

#define OK 1
#define ERROR 0
#define TRUE 1
#define FALSE 0
#define MAXSIZE 20            /* 存储空间初始分配量 */

typedef int Status;
typedef int QElemType;        /* QElemType类型根据实际情况而定，这里假设为int */

typedef struct {
    QElemType data[MAXSIZE];
    int front;    	      /* 头指针 */
    int rear;		      /* 尾指针，若队列不空，指向队列尾元素的下一个位置 */
}SqQueue;

Status visit(QElemType c) {
    printf("%d ", c);
    return OK;
}

/* 初始化一个空队列Q */
Status InitQueue(SqQueue *Q) {
    Q -> front = 0;
    Q -> rear = 0;
    return OK;
}

/* 将Q清为空队列 */
Status ClearQueue(SqQueue *Q) {
    Q -> front = Q -> rear = 0;
    return OK;
}

/* 若队列Q为空队列,则返回TRUE,否则返回FALSE */
Status QueueEmpty(SqQueue Q) {
    if (Q.front == Q.rear)  /* 队列空的标志 */
        return TRUE;
    else
        return FALSE;
}

/* 返回Q的元素个数，也就是队列的当前长度 */
int QueueLength(SqQueue Q) {
    return (Q.rear - Q.front + MAXSIZE) % MAXSIZE;
}

/* 若队列不空,则用e返回Q的队头元素,并返回OK,否则返回ERROR */
Status GetHead(SqQueue Q, QElemType *e) {
    if (Q.front == Q.rear)
        return ERROR;
    *e = Q.data[Q.front];
    return OK;
}

/* 若队列未满，则插入元素e为Q新的队尾元素 */
Status EnQueue(SqQueue *Q, QElemType e) {
    if ((Q -> rear + 1) % MAXSIZE == Q -> front)  /* 队列满 */
        return ERROR;
    Q -> data[Q -> rear] = e;                     /* 将元素e赋值给队尾 */ 
    Q -> rear = (Q -> rear + 1) % MAXSIZE;        /* rear指针向后移一位置，若到最后则转到数组头部 */
    return OK;
}

/* 若队列不空，则删除Q中队头元素，用e返回其值 */
Status DeQueue(SqQueue *Q, QElemType *e) {
    if (Q -> front == Q -> rear)                  /* 队列空 */
        return ERROR;
    *e = Q -> data[Q -> front];                   /* 将队头元素赋值给e */ 
    Q -> front = (Q -> front + 1) % MAXSIZE;      /* front指针向后移一位置，若到最后则转到数组头部 */ 
    return OK;
}

/* 从队头到队尾依次对队列Q中每个元素输出 */
Status QueueTraverse(SqQueue Q) {
    int i;
    i = Q.front;
    while ((i + Q.front) != Q.rear) {
        visit(Q.data[i]);
        i = (i + 1) % MAXSIZE;
    }
    printf("\n");
    return OK;
}

int main() {
    Status j;
    int i = 0, l;
    QElemType d;
    SqQueue Q;
    InitQueue(&Q);
    printf("初始化队列后，队列空否？%d(1:空 0:否)\n", QueueEmpty(Q));
    printf("请输入整型队列元素(不超过%d个)，-1为提前结束符: ", MAXSIZE - 1);
    do {
	d = i + 100;
	if (d == -1)
	    break;
	i++;
	EnQueue(&Q, d);
    } while(i < MAXSIZE - 1);
    printf("队列长度为: %d\n", QueueLength(Q));
    printf("现在队列空否？%u(1:空 0:否)\n", QueueEmpty(Q));
    printf("连续%d次由队头删除元素，队尾插入元素:\n", MAXSIZE);
    for (l = 1; l <= MAXSIZE; l++) {
	DeQueue(&Q, &d);
	printf("删除的元素是%d，插入的元素:%d \n", d, l+1000);
	d = l + 1000;
	EnQueue(&Q, d);
    }
    l = QueueLength(Q);
    printf("现在队列中的元素为: \n");
    QueueTraverse(Q);
    printf("共向队尾插入了%d个元素\n", i + MAXSIZE);
    if (l - 2 > 0)
        printf("现在由队头删除%d个元素:\n", l-2);
    while (QueueLength(Q) > 2) {
	DeQueue(&Q, &d);
	printf("删除的元素值为%d\n", d);
    }
    j = GetHead(Q, &d);
    if (j)
        printf("现在队头元素为: %d\n", d);
    ClearQueue(&Q);
    printf("清空队列后，队列空否？%u(1:空 0:否)\n", QueueEmpty(Q));
    return 0;

    /*
        初始化队列后，队列空否？1(1:空 0:否)
	请输入整型队列元素(不超过19个)，-1为提前结束符: 队列长度为: 19
	现在队列空否？0(1:空 0:否)
	连续20次由队头删除元素，队尾插入元素:
	删除的元素是100，插入的元素:1001
	删除的元素是101，插入的元素:1002
	删除的元素是102，插入的元素:1003
	删除的元素是103，插入的元素:1004
	删除的元素是104，插入的元素:1005
	删除的元素是105，插入的元素:1006
	删除的元素是106，插入的元素:1007
	删除的元素是107，插入的元素:1008
	删除的元素是108，插入的元素:1009
	删除的元素是109，插入的元素:1010
	删除的元素是110，插入的元素:1011
	删除的元素是111，插入的元素:1012
	删除的元素是112，插入的元素:1013
	删除的元素是113，插入的元素:1014
	删除的元素是114，插入的元素:1015
	删除的元素是115，插入的元素:1016
	删除的元素是116，插入的元素:1017
	删除的元素是117，插入的元素:1018
	删除的元素是118，插入的元素:1019
	删除的元素是1001，插入的元素:1020
	现在队列中的元素为:
	1002 1003 1004 1005 1006 1007 1008 1009 1010 1011 1012 1013 1014 1015 1016 1017 1018 1019 1020
	共向队尾插入了39个元素
	现在由队头删除17个元素:
	删除的元素值为1002
	删除的元素值为1003
	删除的元素值为1004
	删除的元素值为1005
	删除的元素值为1006
	删除的元素值为1007
	删除的元素值为1008
	删除的元素值为1009
	删除的元素值为1010
	删除的元素值为1011
	删除的元素值为1012
	删除的元素值为1013
	删除的元素值为1014
	删除的元素值为1015
	删除的元素值为1016
	删除的元素值为1017
	删除的元素值为1018
	现在队头元素为: 1019
	清空队列后，队列空否？1(1:空 0:否)
    */
}

#include <stdio.h>
#include <stdlib.h>

#define OK 1
#define ERROR 0
#define TRUE 1
#define FALSE 0

typedef int Status;             /* Status是函数的类型,其值是函数结果状态代码，如OK等 */
typedef int ElemType;           /* ElemType类型根据实际情况而定，这里假设为int */

typedef struct {                /* 线性表的单链表存储结构 */
    ElemType data;
    struct Node *next;
}Node;
typedef struct Node *LinkList;  /* 定义LinkList */

Status visit(ElemType c) {
    printf("%d ", c);
    return OK;
}

/* 初始化链式线性表 */
Status InitList(LinkList *L) {
	*L = (LinkList) malloc (sizeof(Node)); /* 产生头结点,并使L指向此头结点 */
	if (!(*L)) /* 存储分配失败 */
        return ERROR;
    (*L) -> next = NULL; /* 指针域为空 */
    return OK;
}

/* 初始条件：链式线性表L已存在。操作结果：若L为空表，则返回TRUE，否则返回FALSE */
Status ListEmpty(LinkList L) {
	if (L -> next)
	    return FALSE;
	else
	    return TRUE;
}

/* 初始条件：链式线性表L已存在。操作结果：将L重置为空表 */
Status ClearList(LinkList *L) {
	LinkList p, q;
	p = (*L) -> next;       /*  p指向第一个结点 */
	while (p) {             /*  没到表尾 */
		q = p -> next;
		free(p);
		p = q;
	}
	(*L) -> next = NULL;    /* 头结点指针域为空 */
	return OK;
}

/* 初始条件：链式线性表L已存在。操作结果：返回L中数据元素个数 */
int ListLength(LinkList L) {
	int i = 0;
	LinkList p = L -> next; /* p指向第一个结点 */
	while (p) {
		i++;
		p = p -> next;
	}
	return i;
}

/* 初始条件：链式线性表L已存在，1≤i ≤ListLength(L) */
/* 操作结果：用e返回L中第i个数据元素的值 */
Status GetElem(LinkList L, int i, ElemType *e) {
	int j;
	LinkList p;             /* 声明一指针p */
	p = L -> next;          /* 让p指向链表L的第一个结点 */
	j = 1;                  /* j为计数器 */ 
	while (p && j < i) {    /* p不为空或者计数器j还没有等于i时，循环继续 */
		p = p -> next;
		++j; 
	}
	if (!p || j > i)
	    return ERROR;       /* 第i个元素不存在 */
	*e = p -> data;         /* 取第i个元素的数据 */
	return OK;
}

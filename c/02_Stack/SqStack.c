#include "stdio.h"
#include "stdlib.h"

#define OK 1
#define ERROR 0
#define TRUE 1
#define FALSE 0
#define MAXSIZE 20          /* 存储空间初始分配量 */

typedef int Status;
typedef int SElemType;      /* SElemType类型根据实际情况而定，这里假设为int */

typedef struct {
    SElemType data[MAXSIZE];
    int top;  /* 用于栈顶指针 */
}SqStack;


Status visit(SElemType c) {
    printf("%d ",c);
    return OK;
}

/* 构造一个空栈S */
Status InitStack(SqStack *S) {
    /* S.data=(SElemType *)malloc(MAXSIZE*sizeof(SElemType)); */
    S -> top = -1;
    return OK;
}

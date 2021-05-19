#include <stdio.h>
#include <stdlib.h>

#define OK 1
#define ERROR 0
#define TRUE 1
#define FALSE 0

#define MAXSIZE 100        /* 存储空间初始分配量 */

typedef int Status;
typedef char TElemType;    /* 树结点的数据类型，目前暂定为整型 */

/* 用于构造二叉树********************************** */
int treeIndex = 1;
typedef char String[MAXSIZE];  /* 0号单元存放串的长度 */
String str;

Status StrAssign(String T, char *chars) { 
    int i;
    if(strlen(chars) > MAXSIZE)
        return ERROR;
    else { 
        T[0] = strlen(chars);
        for(i = 1; i <= T[0]; i++)
            T[i] = *(chars + i - 1);
        return OK;
    }
}

TElemType Nil = ' ';  /* 字符型以空格符为空 */
/* ************************************************ */

/* 二叉树的二叉链表结点结构定义 */
typedef struct BiTNode {                /* 结点结构 */ 
    TElemType data;                     /* 结点数据 */ 
    struct BiTNode *lchild, *rchild;    /* 左右孩子指针 */
}BiTNode, *BiTree;


Status visit(TElemType e) {
    printf("%c ", e);
    return OK;
}

/* 构造空二叉树T */
Status InitBiTree(BiTree *T) {
    *T = NULL;
    return OK;
}

/* 按前序输入二叉树中结点的值（一个字符） */
/* #表示空树，构造二叉链表表示二叉树T */
void CreateBiTree(BiTree *T) {
    TElemType ch;
    ch = str[treeIndex++];
    
    if (ch == '#')
        *T = NULL;
    else {
        *T = (BiTree) malloc (sizeof(BiTNode));
        if (!*T)
            exit(OVERFLOW);
        (*T) -> data = ch;  /* 生成根结点 */
        CreateBiTree(&(*T) -> lchild);  /* 构造左子树 */
        CreateBiTree(&(*T) -> rchild);  /* 构造右子树 */
    }
}

/* 初始条件: 二叉树T存在。操作结果: 销毁二叉树T */
void DestroyBiTree(BiTree *T) {
    if(*T) {
        if((*T) -> lchild)  /* 有左孩子 */
            DestroyBiTree(&(*T) -> lchild);  /* 销毁左孩子子树 */
        if((*T) -> rchild)  /* 有右孩子 */
            DestroyBiTree(&(*T) -> rchild);  /* 销毁右孩子子树 */
        free(*T);  /* 释放根结点 */
        *T = NULL;  /* 空指针赋0 */
    }
}

/* 初始条件: 二叉树T存在 */
/* 操作结果: 若T为空二叉树,则返回TRUE,否则FALSE */
Status BiTreeEmpty(BiTree T) {
    if (T)
        return FALSE;
    else
        return TRUE;
}

/* 初始条件: 二叉树T存在。操作结果: 返回T的深度 */
int BiTreeDepth(BiTree T) {
    int i, j;
    if (!T)
        return 0;
    if (T -> lchild)
        i = BiTreeDepth(T -> lchild);
    else
        i = 0;
    if (T -> rchild)
        j = BiTreeDepth(T -> rchild);
    else
        j = 0;
    return i > j ? i + 1 : j + 1;
}

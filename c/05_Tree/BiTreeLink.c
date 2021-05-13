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

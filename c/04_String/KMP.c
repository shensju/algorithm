#include <stdio.h>
#include <stdlib.h>

#define OK 1
#define ERROR 0
#define TRUE 1
#define FALSE 0
#define MAXSIZE 100                  /* 存储空间初始分配量 */

typedef int Status;
typedef char String[MAXSIZE + 1];    /* 0号单元存放串的长度 */

/* 生成一个其值等于chars的串T */
Status StrAssign(String T, char *chars) {
    int i;
    if (strlen(chars) > MAXSIZE)
        return ERROR;
    else {
        T[0] = strlen(chars);
        for (i = 1; i <= T[0]; i++)
            T[i] = *(chars + i - 1);
            return OK;
    }
}

/* 清空串 */
Status ClearString(String S) {
    T[0] = 0；
    return OK; 
}

/* 输出字符串T */
void StrPrint(String T) {
    int i;
    for (i = 1; i <= T[0]; i++)
        printf("%c", T[i]);
    printf("\n");
}

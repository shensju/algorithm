#include <stdio.h>
#include <stdlib.h>

#define OK 1
#define ERROR 0
#define TRUE 1
#define FALSE 0

#define SUCCESS 1
#define UNSUCCESS 0
#define HASHSIZE 12    /* 定义散列表长为数组的长度 */
#define NULLKEY -32768

typedef int Status;

typedef struct {
    int *elem;  /* 数据元素存储基址，动态分配数组 */
    int count;  /* 当前数据元素个数 */
}HashTable;

int m = 0;  /* 散列表表长，全局变量 */

/* 初始化散列表 */
Status InitHashTable(HashTable *H) {
    int i;
    m = HASHSIZE;
    H -> count = m;
    H -> elem = (int *) malloc (m * sizeof(int));
    for (i = 0; i < m; i++)
        H -> elem[i] = NULLKEY;
    return OK;
}

/* 散列函数 */
int Hash(int key)  {
    return key % m;  /* 除留余数法 */ 
}

/* 插入关键字进散列表 */
void InsertHash(HashTable *H, int key) {
    int addr = Hash(key);               /* 求散列地址 */
    while (H -> elem[addr] != NULLKEY)  /* 如果不为空，则冲突 */
        addr = (addr + 1) % m;          /* 开放定址法的线性探测 */
    H -> elem[addr] = key;              /* 直到有空位后插入关键字 */
}

/* 散列表查找关键字 */
Status SearchHash(HashTable H, int key, int *addr) {
    *addr = Hash(key);                                       /* 求散列地址 */
    while (H.elem[*addr] != key) {                           /* 如果不相等，则继续查找 */
        *addr = (*addr + 1) % m;                             /* 开放定址法的线性探测 */
        if (H.elem[*addr] == NULLKEY || *addr == Hash(key))  /* 如果为空，或者循环回到原点 */
            return UNSUCCESS;                                /* 则说明关键字不存在 */
    }
    return SUCCESS;
}

int main() {

    int arr[HASHSIZE] = {12, 67, 56, 16, 25, 37, 22, 29, 15, 47, 48, 34};
    HashTable H;
    InitHashTable(&H);
    int i = 0;
    for (; i < m; i++)
        InsertHash(&H, arr[i]);

    int key = 18, p;
    int result = SearchHash(H, key, &p);
    if (result)
        printf("查找 %d 的地址为：%d \n", key, p);
    else
        printf("查找 %d 失败！\n", key);
    
    for (i = 0; i < m; i++) {
        key = arr[i];
        SearchHash(H, key, &p);
        printf("查找 %d 的地址为：%d \n", key, p);
    }
    
    return 0;
    
    /*
        查找 18 失败！
        查找 12 的地址为：0
        查找 67 的地址为：7
        查找 56 的地址为：8
        查找 16 的地址为：4
        查找 25 的地址为：1
        查找 37 的地址为：2
        查找 22 的地址为：10
        查找 29 的地址为：5
        查找 15 的地址为：3
        查找 47 的地址为：11
        查找 48 的地址为：6
        查找 34 的地址为：9
    */
} 

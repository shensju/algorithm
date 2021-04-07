package com.shensju.stack;

/**
 * @Author: shensju
 * @Date: 2021/4/7 19:48
 * 顺序栈，使用数组进行实现
 */
public class SqStack<E> {

    private E[] data; // 用数组存储元素
    private int top; // 栈顶指针

    /**
     * 有参构造函数
     * 构造一个容量大小为capacity的空栈
     */
    public SqStack(int capacity) {
        data = (E[])new Object[capacity];
        top = -1;
    }

    /**
     * 无参构造函数
     * 构造一个容量大小为10的空栈
     */
    public SqStack() {
        this(10);
    }

    /**
     * 获取栈中的元素个数
     */
    public int size() {
       return top == -1 ? 0 : top + 1;
    }

    /**
     * 判断栈是否为空
     * 若栈为空，则返回true；
     * 若栈非空，则返回false；
     */
    public boolean isEmpty() {
        if (top == -1)
            return true;
        else
            return false;
    }

    /**
     * 获取栈顶元素
     * 若栈为空，则返回null；
     * 若栈非空，则返回栈顶元素；
     */
    public E getTop() {
        if (top == -1)
            return null;
        else
            return data[top];
    }

    /**
     * 元素入栈
     * 若栈已满，则返回false；
     * 若栈未满，则返回true，栈顶指针加一，元素入栈；
     */
    public boolean push(E e) {
        if (top == data.length - 1)
            return false;
        data[++top] = e;
        return true;
    }

    /**
     * 元素出栈
     * 若栈为空，则返回null；
     * 若栈非空，则返回栈顶元素，同时栈顶指针减一；
     */
    public E pop() {
        if (top == -1)
            return null;
        return data[top--];
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append("当前栈中元素自栈底到栈顶依次为：");
        for (int i = 0; i <= top; i++) {
            res.append(data[i]);
            if (i != top) res.append(", ");
        }
        return res.toString();
    }
}

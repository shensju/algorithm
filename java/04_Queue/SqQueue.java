package com.shensju.queue;

/**
 * @Author: shensju
 * @Date: 2021/4/16 22:07
 * 循环队列，使用数组进行实现
 */
public class SqQueue<E> {

    private E[] data; // 用数组存储元素
    private int front; // 头指针
    private int rear; // 尾指针，若队列不空，指向队列尾元素的下一个元素

    /**
     * 无参构造函数
     * 默认构造一个容量大小为10的空队列
     */
    public SqQueue() {
        this(10);
    }

    /**
     * 有参构造函数
     * 构造一个容量大小为capacity的空队列
     */
    public SqQueue(int capacity) {
        data = (E[]) new Object[capacity];
        front = rear = 0;
    }

    /**
     * 获取队列中的元素个数
     */
    public int size() {
        return (rear - front + data.length) % data.length;
    }

    /**
     * 判断队列是否为空
     */
    public boolean isEmpty() {
        return front == rear ? true : false;
    }

    /**
     * 获取队首元素
     */
    public E getHead() {
        if (front == rear)
            throw new IllegalArgumentException("Get failed. Queue is empty.");
        return data[front];
    }

    /**
     * 入队操作
     */
    public boolean add(E e) {
        if ((rear + 1) % data.length == front)
            throw new IllegalArgumentException("Add failed. Queue is full.");
        data[rear] = e;
        rear = (rear + 1) % data.length;
        return true;
    }

    /**
     * 出队操作
     */
    public E remove() {
        if (front == rear)
            throw new IllegalArgumentException("Remove failed. Queue is empty.");
        E e = data[front];
        front = (front + 1) % data.length;
        return e;
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append("当前队列中元素自队首到队尾依次为：");
        for (int i = front; i != rear;) {
            res.append(data[i]);
            if (i != rear - 1) res.append(", ");
            i = (++i % data.length);
        }
        return res.toString();
    }
}

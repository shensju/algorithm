package com.shensju.queue;

/**
 * @Author: shensju
 * @Date: 2021/4/17 18:05
 * 链队列
 */
public class LinkQueue<E> {

    /**
     * 内部结点类定义
     */
    private static class Node<E> {
        private E data;
        private Node<E> next;

        public Node() {
            data = null;
            next = null;
        }

        public Node(E data, Node<E> next) {
            this.data = data;
            this.next = next;
        }

        @Override
        public String toString() {
            return data.toString();
        }
    }

    // 队头指针
    private Node<E> front;

    // 队尾指针
    private Node<E> rear;

    // 队列中元素个数
    private int size;

    /**
     * 无参构造函数
     * 初始，队头和队尾指针指向头结点，队列中元素个数为0
     */
    public LinkQueue() {
        Node<E> dummyNode = new Node<>();
        front = rear = dummyNode;
        size = 0;
    }

    /**
     * 获取队列中元素个数
     */
    public int size() {
        return size;
    }

    /**
     * 判断队列是否为空
     */
    public boolean isEmpty() {
        return size == 0 ? true : false;
    }

    /**
     * 获取队头元素
     * 若队列为空，则返回null
     * 若队列不为空，则返回队头元素
     */
    public E getHead() {
        if (size == 0)
            return null;
        return front.next.data;
    }

    /**
     * 元素入队
     */
    public boolean add(E e) {
        Node<E> newNode = new Node<>(e, null);
        rear.next = newNode;
        rear = newNode;
        size++;
        return true;
    }

    /**
     * 元素出队
     * 若队列为空，则返回null
     * 若队列不为空，则返回出队元素
     */
    public E remove() {
        if (size == 0)
            return null;
        Node<E> delNode = front.next;
        front.next = delNode.next;
        delNode.next = null;
	size--;
        return delNode.data;
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append("当前队列中元素自队头到队尾依次为：");
        Node<E> p = front.next;
        while (p != null) {
            res.append(p.data + " -> ");
            p = p.next;
        }
        res.append("NULL");
        return res.toString();
    }
}

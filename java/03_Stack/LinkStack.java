package com.shensju.stack;

/**
 * @Author: shensju
 * @Date: 2021/4/8 21:50
 * 链栈，使用单链表进行实现
 */
public class LinkStack<E> {

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

    // 栈顶指针
    private Node<E> top;

    // 栈中元素个数
    private int size;

    /**
     * 无参构造函数
     * 初始，栈顶指针为空，栈中元素个数为0
     */
    public LinkStack() {
        top = null;
        size = 0;
    }

    /**
     * 获取栈中元素个数
     */
    public int size() {
        return size;
    }

    /**
     * 判断栈是否为空
     * 若栈为空，则返回true；
     * 若栈不为空，则返回false；
     */
    public boolean isEmpty() {
        return size == 0 ? true : false;
    }

    /**
     * 元素入栈
     */
    public void push(E e) {
        Node<E> newNode = new Node<>(e, top);
        top = newNode;
        size++;
    }

    /**
     * 元素出栈
     * 若栈为空，则返回null；
     * 若栈不为空，则返回栈顶元素；
     */
    public E pop() {
        if (top == null)
            return null;
        E e = top.data;
        top = top.next;
        size--;
        return e;
    }

    /**
     * 获取栈顶元素
     * 若栈为空，则返回null；
     * 若栈不为空，则返回栈顶元素；
     */
    public E getTop() {
        if (top == null)
            return null;
        return top.data;
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        Node<E> p = top;
        res.append("当前栈中元素自栈顶到栈底依次为：");
        while (p != null) {
            res.append(p.data + " -> ");
            p = p.next;
        }
        res.append("NULL");
        return res.toString();
    }
}

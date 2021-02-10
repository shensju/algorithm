package com.shensju.list;

/**
 * @Author: shensju
 * @Date: 2021/2/3 21:58
 */
public class LinkedList<E> {

    /**
     * 链表内部结点类
     */
    private class Node {
        public E e;
        public Node next;

        public Node(E e, Node next) {
            this.e = e;
            this.next = next;
        }

        public Node(E e) {
            this(e, null);
        }

        public Node() {
            this(null, null);
        }

        @Override
        public String toString() {
            return e.toString();
        }
    }

    private Node dummyHead;
    private int size;

    public LinkedList() {
        dummyHead = new Node(null, null);
        size = 0;
    }

    /**
     * @return 链表中的元素个数
     */
    public int getSize() {
        return size;
    }

    /**
     * @return 判断链表是否为空，若为空返回true，否则返回false
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * 在链表的index（0-based）位置添加新的元素
     * 在链表中不是一个常用的操作，练习用 : )
     * @param index
     * @param e
     */
    public void add(int index, E e) {
        if (index < 0 || index > size)
            throw new IllegalArgumentException("Add failed. Illegal index.");
        Node prev = dummyHead;
        for (int i = 0; i < index; i++)
            prev = prev.next;
        prev.next = new Node(e, prev.next);
        size++;
    }

    /**
     * 在链表头添加新的元素
     * @param e
     */
    public void addFirst(E e) {
        add(0, e);
    }

    /**
     * 在链表末尾添加新的元素
     * @param e
     */
    public void addLast(E e) {
        add(size, e);
    }
}

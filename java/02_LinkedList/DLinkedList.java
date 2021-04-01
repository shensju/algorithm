package com.shensju.list;

/**
 * @Author: shensju
 * @Date: 2021/4/1 22:23
 */
public class DLinkedList<E> {

    /**
     * 双向链表的结点类定义
     */
    private static class Node<E> {
        E data;
        Node<E> next;
        Node<E> prev;

        public Node(Node<E> prev, E data, Node<E> next) {
            this.data = data;
            this.next = next;
            this.prev = prev;
        }
    }

    // 双向链表的首结点
    private Node<E> first;

    // 双向链表的尾结点
    private Node<E> last;

    // 双向链表的结点数
    private int size;

    public DLinkedList() {
        first = null;
        last = first;
        size = 0;
    }

    /**
     * 默认在链表尾部插入新结点
     * @param e
     * @return
     */
    public boolean add(E e) {
        linkLast(e);
        return true;
    }

    /**
     * 向链表开头插入新结点
     * @param e
     */
    public void addFirst(E e) {
        linkFirst(e);
    }

    /**
     * 向链表结尾插入新结点
     * @param e
     */
    public void addLast(E e) {
        linkLast(e);
    }

    /**
     * 将新结点链接到链表开头处
     * @param e
     */
    private void linkFirst(E e) {
        Node<E> f = first;
        Node<E> newNode = new Node<>(null, e, f);
        if (f == null) {
            last = newNode;
            first = last;
        } else {
            f.prev = newNode;
            first = newNode;
        }
        size++;
    }

    /**
     * 将新结点链接到链表结尾处
     * @param e
     */
    private void linkLast(E e) {
        Node<E> l = last;
        Node<E> newNode = new Node<>(l, e, null);
        if (l == null) {
            first = newNode;
            last = first;
        } else {
            l.next = newNode;
            last = newNode;
        }
        size++;
    }

}


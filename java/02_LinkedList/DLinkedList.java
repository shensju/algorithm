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

        @Override
        public String toString() {
            return data.toString();
        }
    }

    // 双向链表的首结点
    private Node<E> first;

    // 双向链表的尾结点
    private Node<E> last;

    // 双向链表的结点数
    private int size;

    /**
     * 初始化双向链表
     */
    public DLinkedList() {
        first = null;
        last = first;
        size = 0;
    }

    /**
     * 链表中的结点个数
     * @return
     */
    public int size() {
        return size;
    }

    /**
     * 判断链表是否为空，若为空返回true，否则返回false
     * @return
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * 将新结点插入到链表的第index[0, size]个位置
     * @param index
     * @param e
     */
    public void add(int index, E e) {
        if (index < 0 || index > size)
            throw new IllegalArgumentException("Add failed. Illegal index.");
        if (index == size)
            linkLast(e);
        else
            linkBefore(e, node(index));
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

    /**
     * 将新结点插入到指定的结点前面
     * @param e
     * @param succ
     */
    public void linkBefore(E e, Node<E> succ) {
        Node<E> p = succ.prev;
        Node<E> newNode = new Node<>(p, e, succ);
        succ.prev = newNode;
        if (p == null)
            first = newNode;
        else
            p.next = newNode;
        size++;
    }

    /**
     * 返回链表的第index[0, size - 1]个结点
     * @param index
     * @return
     */
    private Node<E> node(int index) {
        if (index < 0 || index >= size)
            throw new IllegalArgumentException("Get node failed. Illegal index.");
        if (index < (size >> 1)) {
            Node<E> target = first;
            for (int i = 0; i < index; i++)
                target = target.next;
            return target;
        } else {
            Node<E> target = last;
            for (int i = size - 1; i > index; i--)
                target = target.prev;
            return target;
        }
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        Node<E> cur = first;
        while (cur != null) {
            res.append(cur + "->");
            cur = cur.next;
        }
        res.append("NULL");
        return res.toString();
    }

    public static void main(String[] args) {
        DLinkedList<Integer> list = new DLinkedList<>();
        for (int i = 0; i < 5; i++) {
            list.add(i);
            System.out.println(list);
        }
        list.add(0, 10);
        System.out.println(list);
        list.add(6, 11);
        System.out.println(list);
        list.add(3, 20);
        System.out.println(list);
    }
}

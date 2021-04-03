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
    private void linkBefore(E e, Node<E> succ) {
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

    /**
     * 删除第index个位置的结点，返回被删除结点的值
     * @param index
     * @return
     */
    public E remove(int index) {
        if (index < 0 || index >= size)
            throw new IllegalArgumentException("Remove failed. Illegal index.");
        return unlink(node(index));
    }

    /**
     * 默认从链表的开头删除结点，返回被删除结点的值
     * @return
     */
    public E remove() {
        return removeFirst();
    }

    /**
     * 删除链表的首结点，返回被删除结点的值
     * @return
     */
    public E removeFirst() {
        Node<E> f = first;
        if (f == null)
            throw new IllegalArgumentException("Remove failed. First node is null.");
        return unlinkFirst(f);
    }

    /**
     * 删除链表的尾结点，返回被删除结点的值
     * @return
     */
    public E removeLast() {
        Node<E> l = last;
        if (l == null)
            throw new IllegalArgumentException("Remove failed. Last node is null.");
        return unlinkLast(l);
    }

    /**
     * 删除链表的指定结点，返回被删除结点的值
     * @param x
     * @return
     */
    private E unlink(Node<E> x) {
        E e = x.data;
        Node<E> next = x.next;
        Node<E> prev = x.prev;
        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            x.prev = null;
        }
        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            x.next = null;
        }
        x.data = null;
        size--;
        return e;
    }

    /**
     * 删除链表的首结点，返回被删除结点的值
     * @param f
     * @return
     */
    private E unlinkFirst(Node<E> f) {
        E e = f.data;
        Node<E> next = f.next;
        f.data = null;
        f.next = null;
        first = next;
        if (next == null)
            last = null;
        else
            next.prev = null;
        size--;
        return e;
    }

    /**
     * 删除链表的尾结点，返回被删除结点的值
     * @param l
     * @return
     */
    private E unlinkLast(Node<E> l) {
        E e = l.data;
        Node<E> prev = l.prev;
        l.data = null;
        l.prev = null;
        last = prev;
        if (prev == null)
            first = null;
        else
            prev.next = null;
        size--;
        return e;
    }

    /**
     * 获取链表第index个位置的结点的值
     * @param index
     * @return
     */
    public E get(int index) {
        if (index < 0 || index >= size)
            throw new IllegalArgumentException("Get failed. Illegal index.");
        return node(index).data;
    }

    /**
     * 获取链表首结点的值
     * @return
     */
    public E getFirst() {
        Node<E> f = first;
        if (f == null)
            throw new IllegalArgumentException("Get failed. First node is not existed.");
        return f.data;
    }

    /**
     * 获取链表尾结点的值
     * @return
     */
    public E getLast() {
        Node<E> l = last;
        if (l == null)
            throw new IllegalArgumentException("Get failed. Last node is not existed.");
        return l.data;
    }

    /**
     * 查找链表中是否存在值为e的结点，若存在返回true，否则返回false
     * @param e
     * @return
     */
    public boolean contains(E e) {
        return find(e) != -1;
    }

    /**
     * 查找链表中是否存在值为e的结点，若存在返回结点的索引位置，否则返回-1
     * @param e
     * @return
     */
    public int find(E e) {
        int index = 0;
        if (e == null) {
            for (Node<E> cur = first; cur != null; cur = cur.next) {
                if (cur.data == null)
                    return index;
                index++;
            }
        } else {
            for (Node<E> cur = first; cur != null; cur = cur.next) {
                if (e.equals(cur.data))
                    return index;
                index++;
            }
        }
        return -1;
    }

    /**
     * 修改链表第index个位置的结点的值为e，返回结点的旧值
     * @param index
     * @param e
     * @return
     */
    public E set(int index, E e) {
        if (index < 0 || index >= size)
            throw new IllegalArgumentException("Set failed. Illegal index.");
        Node<E> target = node(index);
        E oldValue = target.data;
        target.data = e;
        return oldValue;
    }

    /**
     * 清空链表
     */
    public void clear() {
        for (Node<E> cur = first; cur != null;) {
            Node<E> next = cur.next;
            cur.data = null;
            cur.next = null;
            if (next != null)
                next.prev = null;
            cur = next;
        }
        first = null;
        last = null;
        size = 0;
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
}

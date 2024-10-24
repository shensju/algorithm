package indi.shensju.list;

/**
 * @author shensju
 * @date 2024/10/21 22:33
 * 单向链表，支持泛型，包含增删改查基本操作
 */
public class SinglyLinkedList<E> {
    /**
     * 链表内部结点类
     */
    private class Node<E> {
        public E item;
        public Node<E> next;

        public Node(E element, Node<E> next) {
            this.item = element;
            this.next = next;
        }

        public Node(E element) {
            this(element, null);
        }

        public Node() {
            this(null, null);
        }

        @Override
        public String toString() {
            return item.toString();
        }
    }

    private Node<E> dummyHead; // 虚拟头结点
    private int size;

    public SinglyLinkedList() {
        dummyHead = new Node(null, null);
        size = 0;
    }

    /**
     * @return 链表中的元素个数
     */
    public int size() {
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
     * @param element
     */
    public void add(int index, E element) {
        if (index < 0 || index > size)
            throw new IllegalArgumentException("Add failed. Illegal index.");
        Node<E> prev = dummyHead;
        for (int i = 0; i < index; i++)
            prev = prev.next;
        prev.next = new Node(element, prev.next);
        size++;
    }

    /**
     * 在链表头添加新的元素
     * @param element
     */
    public void addFirst(E element) {
        add(0, element);
    }

    /**
     * 在链表末尾添加新的元素
     * @param element
     */
    public void addLast(E element) {
        add(size, element);
    }

    /**
     * 获得链表的第index（0-based）个位置的元素
     * 在链表中不是一个常用的操作，练习用: )
     * @param index
     * @return
     */
    public E get(int index) {
        if (index < 0 || index >= size)
            throw new IllegalArgumentException("Get failed. Illegal index.");
        Node<E> curr = dummyHead.next;
        for (int i = 0; i < index; i++)
            curr = curr.next;
        return curr.item;
    }

    /**
     * 获得链表的第一个元素
     * @return
     */
    public E getFirst() {
        return get(0);
    }

    /**
     * 获得链表的最后一个元素
     * @return
     */
    public E getLast() {
        return get(size - 1);
    }

    /**
     * 修改链表的第index（0-based）个位置的元素为element
     * 在链表中不是一个常用的操作，练习用: )
     * @param index
     * @param element
     */
    public void set(int index, E element) {
        if (index < 0 || index >= size)
            throw new IllegalArgumentException("Set failed. Illegal index.");
        Node<E> curr = dummyHead.next;
        for (int i = 0; i < index; i++)
            curr = curr.next;
        curr.item = element;
    }

    /**
     * 查找链表中是否有元素element
     * @param element
     * @return
     */
    public boolean contains(E element) {
        Node<E> curr = dummyHead.next;
        while (curr != null) {
            if (curr.item.equals(element))
                return true;
            curr = curr.next;
        }
        return false;
    }

    /**
     * 删除值等于给定值的结点
     * @param element
     * @return
     */
    public boolean removeElement(E element) {
        Node<E> curr = dummyHead;
        while (curr.next != null) {
            if (curr.next.item.equals(element)) {
                Node<E> target = curr.next;
                curr.next = target.next;
                target.next = null;
                size--;
                return true;
            }
            curr = curr.next;
        }
        return false;
    }

    /**
     * 从链表中删除index（0-based）位置的元素，返回被删除的元素
     * 在链表中不是一个常用的操作，练习用: )
     * @param index
     * @return
     */
    public E remove(int index) {
        if (index < 0 || index >= size)
            throw new IllegalArgumentException("Remove failed. Illegal index.");
        Node<E> prev = dummyHead;
        for (int i = 0; i < index; i++) {
            prev = prev.next;
        }
        Node<E> retNode = prev.next;
        prev.next = retNode.next;
        retNode.next = null;
        size--;
        return retNode.item;
    }

    /**
     * 从链表中删除第一个元素，返回被删除的元素
     * @return
     */
    public E removeFirst() {
        return remove(0);
    }

    /**
     * 从链表中删除最后一个元素，返回被删除的元素
     * @return
     */
    public E removeLast() {
        return remove(size - 1);
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        Node curr = dummyHead.next;
        while (curr != null) {
            res.append(curr + "->");
            curr = curr.next;
        }
        res.append("NULL");
        return res.toString();
    }
}

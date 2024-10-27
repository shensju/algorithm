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
    private static class Node<E> {
        private E item;
        private Node<E> next;

        public Node(E element, Node<E> next) {
            this.item = element;
            this.next = next;
        }

        @Override
        public String toString() {
            return item.toString();
        }
    }

    private Node<E> dummyHead; // 虚拟头结点
    private int size; // 结点数

    public SinglyLinkedList() {
        dummyHead = new Node(null, null);
        size = 0;
    }

    /**
     * @return 链表的结点数
     */
    public int size() {
        return size;
    }

    /**
     * 判断链表是否为空链表
     * @return 若为空返回true，否则返回false
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * 默认在链表尾部插入新结点
     * @param element
     */
    public void add(E element) {
        addLast(element);
    }

    /**
     * 将新结点插入到链表索引为index[0, size]的位置
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
     * 向链表头部插入新结点
     * @param element
     */
    public void addFirst(E element) {
        add(0, element);
    }

    /**
     * 向链表尾部插入新结点
     * @param element
     */
    public void addLast(E element) {
        add(size, element);
    }

    /**
     * 获取链表索引为index[0,size)的结点元素
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
     * 获取链表的头结点元素
     * @return
     */
    public E getFirst() {
        return get(0);
    }

    /**
     * 获取链表的尾结点元素
     * @return
     */
    public E getLast() {
        return get(size - 1);
    }

    /**
     * 判断链表中是否存在给定的元素
     * @param element
     * @return 若存在返回true，否则返回false
     */
    public boolean contains(E element) {
        return find(element) != -1;
    }

    /**
     * 查找链表中是否存在给定元素的结点
     * @param element
     * @return 若存在返回结点的索引位置，否则返回-1
     */
    public int find(E element) {
        int index = 0;
        if (element == null) {
            for (Node<E> curr = dummyHead.next; curr != null; curr = curr.next) {
                if (curr.item == null)
                    return index;
                index++;
            }
        } else {
            for (Node<E> curr = dummyHead.next; curr != null; curr = curr.next) {
                if (element.equals(curr.item))
                    return index;
                index++;
            }
        }
        return -1;
    }

    /**
     * 修改链表索引为index[0,size)的结点元素，并返回修改前的元素
     * @param index
     * @param element
     */
    public E set(int index, E element) {
        if (index < 0 || index >= size)
            throw new IllegalArgumentException("Set failed. Illegal index.");
        Node<E> curr = dummyHead.next;
        for (int i = 0; i < index; i++)
            curr = curr.next;
        E oldVal = curr.item;
        curr.item = element;
        return oldVal;
    }

    /**
     * 默认删除链表的头结点，并返回被删除的元素
     * @return
     */
    public E remove() {
        return removeFirst();
    }

    /**
     * 删除链表索引为index[0,size)的结点，并返回被删除的元素
     * @param index
     * @return
     */
    public E remove(int index) {
        if (index < 0 || index >= size)
            throw new IllegalArgumentException("Remove failed. Illegal index.");
        Node<E> prev = dummyHead;
        for (int i = 0; i < index; i++)
            prev = prev.next;
        Node<E> retNode = prev.next;
        prev.next = retNode.next;
        retNode.next = null;
        size--;
        return retNode.item;
    }

    /**
     * 删除链表的头结点，并返回被删除的元素
     * @return
     */
    public E removeFirst() {
        return remove(0);
    }

    /**
     * 删除链表的尾结点，并返回被删除的元素
     * @return
     */
    public E removeLast() {
        return remove(size - 1);
    }

    /**
     * 删除链表中等于给定元素的结点，如有多个则删除第一个结点
     * @param element
     * @return
     */
    public boolean remove(E element) {
        Node<E> curr = dummyHead;
        if (element == null) {
            while (curr.next != null) {
                if (curr.next.item == null) {
                    Node<E> target = curr.next;
                    curr.next = target.next;
                    target.next = null;
                    size--;
                    return true;
                }
                curr = curr.next;
            }
        } else {
            while (curr.next != null) {
                if (element.equals(curr.next.item)) {
                    Node<E> target = curr.next;
                    curr.next = target.next;
                    target.next = null;
                    size--;
                    return true;
                }
                curr = curr.next;
            }
        }
        return false;
    }

    /**
     * 清空链表
     */
    public void clear() {
        Node<E> curr = dummyHead.next;
        while (curr != null) {
            Node<E> next = curr.next;
            curr.item = null;
            curr.next = null;
            curr = next;
        }
        dummyHead.next = null;
        size = 0;
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

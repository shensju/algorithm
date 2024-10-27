package indi.shensju.list;

/**
 * @author shensju
 * @date 2024/10/24 23:15
 * 双向链表，支持泛型，包含增删改查基本操作
 */
public class DoublyLinkedList<E> {
    /**
     * 链表内部结点类
     */
    private static class Node<E> {
        private E item;
        private Node<E> prev;
        private Node<E> next;

        public Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.prev = prev;
            this.next = next;
        }

        @Override
        public String toString() {
            return item.toString();
        }
    }

    private Node<E> first; // 头结点
    private Node<E> last; // 尾结点
    private int size = 0; // 结点数

    public DoublyLinkedList() {

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
     * 将新结点链接到链表开头处
     * @param element
     */
    private void linkFirst(E element) {
        Node<E> f = first;
        Node<E> newNode = new Node<>(null, element, f);
        first = newNode;
        if (f == null)
            last = newNode;
        else
            f.prev = newNode;
        size++;
    }

    /**
     * 将新结点链接到链表结尾处
     * @param element
     */
    private void linkLast(E element) {
        Node<E> l = last;
        Node<E> newNode = new Node<>(l, element, null);
        last = newNode;
        if (l == null)
            first = newNode;
        else
            l.next = newNode;
        size++;
    }

    /**
     * 将新结点插入到指定的结点前面
     * @param element
     * @param succ
     */
    private void linkBefore(E element, Node<E> succ) {
        Node<E> pred = succ.prev;
        Node<E> newNode = new Node<>(pred, element, succ);
        succ.prev = newNode;
        if (pred == null)
            first = newNode;
        else
            pred.next = newNode;
        size++;
    }

    /**
     * 获取链表索引为index[0, size)的结点
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
     * 向链表头部插入新结点
     * @param element
     */
    public void addFirst(E element) {
        linkFirst(element);
    }

    /**
     * 向链表尾部插入新结点
     * @param element
     */
    public void addLast(E element) {
        linkLast(element);
    }

    /**
     * 默认在链表尾部插入新结点
     * @param element
     */
    public void add(E element) {
        linkLast(element);
    }

    /**
     * 将新结点插入到链表索引为index[0, size]的位置
     * @param index
     * @param element
     */
    public void add(int index, E element) {
        if (index < 0 || index > size)
            throw new IllegalArgumentException("Add failed. Illegal index.");
        if (index == size)
            linkLast(element);
        else
            linkBefore(element, node(index));
    }

    /**
     * 获取链表索引为index[0,size)的结点元素
     * @param index
     * @return
     */
    public E get(int index) {
        if (index < 0 || index >= size)
            throw new IllegalArgumentException("Get failed. Illegal index.");
        return node(index).item;
    }

    /**
     * 获取链表的头结点元素
     * @return
     */
    public E getFirst() {
        Node<E> f = first;
        if (f == null)
            throw new IllegalArgumentException("Get failed. First node is not existed.");
        return f.item;
    }

    /**
     * 获取链表的尾结点元素
     * @return
     */
    public E getLast() {
        Node<E> l = last;
        if (l == null)
            throw new IllegalArgumentException("Get failed. Last node is not existed.");
        return l.item;
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
            for (Node<E> curr = first; curr != null; curr = curr.next) {
                if (curr.item == null)
                    return index;
                index++;
            }
        } else {
            for (Node<E> curr = first; curr != null; curr = curr.next) {
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
     * @return
     */
    public E set(int index, E element) {
        if (index < 0 || index >= size)
            throw new IllegalArgumentException("Set failed. Illegal index.");
        Node<E> curr = node(index);
        E oldVal = curr.item;
        curr.item = element;
        return oldVal;
    }

    /**
     * 删除链表的头结点，并返回被删除的元素
     * @return
     */
    public E removeFirst() {
        Node<E> f = first;
        if (f == null)
            throw new IllegalArgumentException("Remove failed. First node is null.");
        return unlinkFirst(f);
    }

    /**
     * 删除链表的尾结点，并返回被删除的元素
     * @return
     */
    public E removeLast() {
        Node<E> l = last;
        if (l == null)
            throw new IllegalArgumentException("Remove failed. Last node is null.");
        return unlinkLast(l);
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
        return unlink(node(index));
    }

    /**
     * 删除链表中等于给定元素的结点，如有多个则删除第一个结点
     * @param element
     * @return
     */
    public boolean remove(E element) {
        Node<E> curr = first;
        if (element == null) {
            while (curr != null) {
                if (curr.item == null) {
                    unlink(curr);
                    return true;
                }
                curr = curr.next;
            }
        } else {
            while (curr != null) {
                if (element.equals(curr.item)) {
                    unlink(curr);
                    return true;
                }
                curr = curr.next;
            }
        }
        return false;
    }

    /**
     * 删除链表的头结点，并返回被删除的元素
     * @param f
     * @return
     */
    private E unlinkFirst(Node<E> f) {
        E element = f.item;
        Node<E> next = f.next;
        f.item = null;
        f.next = null;
        first = next;
        if (next == null)
            last = null;
        else
            next.prev = null;
        size--;
        return element;
    }

    /**
     * 删除链表的尾结点，并返回被删除的元素
     * @param l
     * @return
     */
    private E unlinkLast(Node<E> l) {
        E element = l.item;
        Node<E> prev = l.prev;
        l.item = null;
        l.prev = null;
        last = prev;
        if (prev == null)
            first = null;
        else
            prev.next = null;
        size--;
        return element;
    }

    /**
     * 删除链表的指定结点，并返回被删除的元素
     * @param target
     * @return
     */
    private E unlink(Node<E> target) {
        E element = target.item;
        Node<E> prev = target.prev;
        Node<E> next = target.next;
        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            target.prev = null;
        }
        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            target.next = null;
        }
        target.item = null;
        size--;
        return element;
    }

    /**
     * 清空链表
     */
    public void clear() {
        for (Node<E> curr = first; curr != null;) {
            Node<E> next = curr.next;
            curr.item = null;
            curr.next = null;
            curr.prev = null;
            curr = next;
        }
        first = last = null;
        size = 0;
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        Node<E> curr = first;
        while (curr != null) {
            if (curr.next != null)
                res.append(curr + "<->");
            else
                res.append(curr + "->");
            curr = curr.next;
        }
        res.append("NULL");
        return res.toString();
    }
}

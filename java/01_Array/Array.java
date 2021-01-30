package com.shensju.array;

/**
 * @Author: shensju
 * @Date: 2021/1/27 22:58
 */
public class Array<E> {

    private E[] data;
    private int size;

    /**
     * 构造一个容量为capacity的数组
     * @param capacity
     */
    public Array(int capacity) {
        data = (E[]) new Object[capacity];
        size = 0;
    }

    /**
     * 默认构造一个容量为10的数组
     */
    public Array() {
        this(10);
    }

    /**
     * 获取数组的元素个数
     * @return
     */
    public int getSize() {
        return size;
    }

    /**
     * 获取数组的容量
     * @return
     */
    public int getCapacity() {
        return data.length;
    }

    /**
     * 判断数组是否为空
     * @return
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * 在index索引位置上插入一个新元素
     * @param index
     * @param e
     */
    public void add(int index, E e) {
        if (index < 0 || index > size)
            throw new IllegalArgumentException("Add failed. Require index >= 0 and index <= size.");
        if (size == data.length)
            resize(2 * data.length);
        for (int i = size - 1; i >= index; i--)
            data[i + 1] = data[i];
        data[index] = e;
        size++;
    }

    /**
     * 在数组的所有元素后面添加新元素
     * @param e
     */
    public void addLast(E e) {
        add(size, e);
    }

    /**
     * 在数组的所有元素前面添加新元素
     * @param e
     */
    public void addFirst(E e) {
        add(0, e);
    }

    /**
     * 获取index索引位置的元素
     * @param index
     * @return
     */
    public E get(int index) {
        if (index < 0 || index >= size)
            throw new IllegalArgumentException("Get failed. Index is illegal.");
        return data[index];
    }

    /**
     * 修改index索引位置的元素为e
     * @param index
     * @param e
     */
    public void set(int index, E e) {
        if (index < 0 || index >= size)
            throw new IllegalArgumentException("Set failed. Index is illegal.");
        data[index] = e;
    }

    /**
     * 查找数组中是否有元素e
     * @param e
     * @return
     */
    public boolean contains(E e) {
        for (int i = 0; i < size; i++) {
            if (data[i].equals(e))
                return true;
        }
        return false;
    }

    /**
     * 查找元素e在数组中的索引，如果有多个元素e，返回最前面元素的索引位置；如果不存在，则返回-1
     * @param e
     * @return
     */
    public int find(E e) {
        for (int i = 0; i < size; i++) {
            if (data[i].equals(e))
                return i;
        }
        return -1;
    }

    /**
     * 从数组中删除index位置的元素，返回被删除的元素
     * @param index
     * @return
     */
    public E remove(int index) {
        if (index < 0 || index >= size)
            throw new IllegalArgumentException("Remove failed. Index is illegal.");
        E returnData = data[index];
        for (int i = index + 1; i < size; i++)
            data[i - 1] = data[i];
        size--;
        data[size] = null; // loitering objects != memory leak

        if (size == data.length / 4 && data.length / 2 != 0) // 避免震荡复杂度
            resize(data.length / 2);
        return returnData;
    }

    /**
     * 从数组中删除第一个元素，返回被删除的元素
     * @return
     */
    public E removeFirst() {
        return remove(0);
    }

    /**
     * 从数组中删除最后一个元素，返回被删除的元素
     * @return
     */
    public E removeLast() {
        return remove(size - 1);
    }

    /**
     * 从数组中删除元素e，如果有多个元素，只删除索引位置最前面的元素
     * @param e
     */
    public void removeElement(E e) {
        int index = find(e);
        if (index != -1)
            remove(index);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(String.format("Array: size = %d, capacity = %d\n", size, data.length));
        result.append("[");
        for (int i = 0; i < size; i++) {
            result.append(data[i]);
            if (i != size - 1)
                result.append(", ");
        }
        result.append("]");
        return result.toString();
    }

    /**
     * 动态调整数组的容量大小
     * @param newCapacity
     */
    private void resize(int newCapacity) {
        E[] newData = (E[]) new Object[newCapacity];
        for (int i = 0; i < size; i++)
            newData[i] = data[i];
        data = newData;
    }
}

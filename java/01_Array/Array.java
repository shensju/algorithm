/**
 * @Author: shensju
 * @Date: 2021/1/27 22:58
 */
public class Array {

    private int[] data;
    private int size;

    /**
     * 有参构造函数，传入数组的容量capacity，构造数组
     * @param capacity  申请的数组容量
     */
    public Array(int capacity) {
        data = new int[capacity];
        size = 0;
    }

    /**
     * 无参构造函数，默认构造一个容量为10的数组
     */
    public Array() {
        this(10);
    }

    /**
     * 获取数组的元素个数
     * @return  数组的元素个数
     */
    public int getSize() {
        return size;
    }

    /**
     * 获取数组的容量
     * @return  数组的容量
     */
    public int getCapacity() {
        return data.length;
    }

    /**
     * 判断数组是否为空
     * @return  true表示为空，false表示非空
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * 在数组的所有元素后面添加新元素
     * @param e  需要添加的元素
     */
    public void addLast(int e) {
        add(size, e);
    }

    /**
     * 在数组的所有元素前面添加新元素
     * @param e  需要添加的元素
     */
    public void addFirst(int e) {
        add(0, e);
    }

    /**
     * 在index索引位置上插入一个新元素
     * @param index  要插入的数组位置
     * @param e  需要添加的元素
     */
    public void add(int index, int e) {
        if (size == data.length)
            throw new IllegalArgumentException("Add failed. Array is full.");
        if (index < 0 || index > size)
            throw new IllegalArgumentException("Add failed. Require index >= 0 and index <= size.");
        for (int i = size - 1; i >= index; i--)
            data[i + 1] = data[i];
        data[index] = e;
        size++;
    }

    /**
     * 获取index索引位置的元素
     * @param index
     * @return
     */
    public int get(int index) {
        if (index < 0 || index >= size)
            throw new IllegalArgumentException("Get failed. Index is illegal.");
        return data[index];
    }

    /**
     * 修改index索引位置的元素为e
     * @param index
     * @param e
     */
    public void set(int index, int e) {
        if (index < 0 || index >= size)
            throw new IllegalArgumentException("Set failed. Index is illegal.");
        data[index] = e;
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


}

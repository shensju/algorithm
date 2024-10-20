package indi.shensju.array;

/**
 * @author shensju
 * @date 2024/10/19 11:43
 */
public class GenericArrayTest {
    public static void main(String[] args) {
        GenericArray<Integer> arr = new GenericArray<>(); // 数组初始容量大小为10个元素
        for (int i = 0; i < 10; i++)
            arr.addLast(i);
        System.out.println(arr);

        arr.add(1, 100);
        System.out.println(arr);

        arr.addFirst(-1);
        System.out.println(arr);

        arr.remove(2);
        System.out.println(arr);

        arr.removeElement(4);
        System.out.println(arr);

        arr.removeFirst();
        System.out.println(arr);
    }
}

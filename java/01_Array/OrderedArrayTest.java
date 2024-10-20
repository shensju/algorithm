package indi.shensju.array;

/**
 * @author shensju
 * @date 2024/10/20 10:10
 */
public class OrderedArrayTest {
    public static void main(String[] args) {
        OrderedArray<Integer> arr = new OrderedArray<>();
        for (int i = 10; i > 0; i--) {
            arr.add(i);
        }
        System.out.println(arr);

        arr.add(0);
        System.out.println(arr);

        arr.removeElement(5);
        System.out.println(arr);

        System.out.println("arr.contains(4) = " + arr.contains(4));

        System.out.println("arr.get(5) = " + arr.get(5));
        arr.set(5, 20);
        System.out.println(arr);

        arr.add(5);
        System.out.println(arr);

        arr.add(7);
        arr.add(19);
        System.out.println(arr);
    }
}

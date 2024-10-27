package indi.shensju.list;

/**
 * @author shensju
 * @date 2024/10/27 22:08
 */
public class DoublyLinkedListTest {
    public static void main(String[] args) {
        DoublyLinkedList<String> list = new DoublyLinkedList<>();
        System.out.println("size : " + list.size() + ", list : " + list);
        list.addFirst("Beijing");
        list.addLast("Shanghai");
        list.add("Shenzhen");
        list.add(2, "Guangzhou");
        System.out.println("size : " + list.size() + ", list : " + list);
        System.out.println("list.get(2) = " + list.get(2));
        list.add(2, "Hangzhou");
        System.out.println("size : " + list.size() + ", list : " + list);
        list.set(2, "Suzhou");
        System.out.println("size : " + list.size() + ", list : " + list);
        System.out.println("list.contains(\"Hangzhou\") = " + list.contains("Hangzhou"));
        list.add(3, "Hangzhou");
        System.out.println("size : " + list.size() + ", list : " + list);
        System.out.println("list.remove(2) = " + list.remove(2));
        System.out.println("size : " + list.size() + ", list : " + list);
        System.out.println("list.remove(\"Hangzhou\") = " + list.remove("Hangzhou"));
        System.out.println("size : " + list.size() + ", list : " + list);
        System.out.println("list.remove(null) = " + list.remove(null));
        System.out.println("size : " + list.size() + ", list : " + list);
        list.clear();
        System.out.println("size : " + list.size() + ", list : " + list);
    }
}

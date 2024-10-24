package indi.shensju.list;

/**
 * @author shensju
 * @date 2024/10/24 22:53
 */
public class SinglyLinkedListTest {
    public static void main(String[] args) {
        SinglyLinkedList<String> list = new SinglyLinkedList<>();
        System.out.println("size : " + list.size() + ", list : " + list);
        list.addFirst("Beijing");
        list.addLast("Shanghai");
        list.addLast("Guangzhou");
        list.addLast("Shenzhen");
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
        System.out.println("list.removeElement(\"Hangzhou\") = " + list.removeElement("Hangzhou"));
        System.out.println("size : " + list.size() + ", list : " + list);
    }
}

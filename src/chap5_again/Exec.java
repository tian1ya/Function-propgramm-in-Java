package chap5_again;

import static chap5_again.List.list;

public class Exec {

    public static void main(String[] args) {
        List<Integer> empty = list();
        List<Integer> list1 = list(1);
        List<Integer> list2 = list(1, 2, 3, 4);

        System.out.println(list2.toString());

        System.out.println(list2.drop(1));

        System.out.println(list2.dropWhile(x -> x == 1));

        System.out.println(list2.reverse());

        System.out.println(list2.init());

        System.out.println(List.foldRight(list2, 0, x->y->x+y));
        System.out.println(List.foldRight(list2, 1, x->y->x*y));
        System.out.println(list2.length());

        System.out.println(list2.foldLeft(0, x->y->x+y));
        System.out.println(list2.foldLeft(1, x->y->x*y));

        System.out.println(List.sumViafoldLeft(list2));
        System.out.println(List.multiViafoldLeft(list2));
        System.out.println(List.lengthViafoldLeft(list2));

        System.out.println(List.concat(list1, list2));

        System.out.println(List.triple(list2));

        System.out.println(list2.map(x -> 2 * x));
    }
}

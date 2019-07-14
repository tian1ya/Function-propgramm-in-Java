package common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CollectionUtilities {
    public static <T> List<T> list(){
        return Collections.emptyList();
    }

    public static <T> List<T> list(T t){
        return Collections.singletonList(t);
    }

    public static <T> List<T> list(List<T> t){
        return Collections.unmodifiableList(new ArrayList<>(t));
    }

    public static <T> List<T> list(T ...t){
        return Collections.unmodifiableList(Arrays.asList(t));
    }

    public static <T> T head(List<T> list){
        if (list.size() == 0){
            throw new IllegalStateException("list must not be empty");
        }
        return copy(list).get(0);
    }

    public static <T> List<T> tail(List<T> list){
        if (list.size() == 0){
            throw new IllegalStateException("list must not be empty");
        }
        List<T> newList = copy(list);
        newList.remove(0);
        return Collections.unmodifiableList(newList);
    }

    public static <T> List<T> append(List<T> list, T t){
        if (list.size() == 0){
            throw new IllegalStateException("list must not be empty");
        }
        List<T> newList = copy(list);
        newList.add(t);
        return Collections.unmodifiableList(newList);
    }

    public static <T> List<T> copy(List<T> list){
        if (list.size() == 0){
            throw new IllegalStateException("list must not be empty");
        }
        return Collections.unmodifiableList(new ArrayList<>(list));
    }

    /**
     *  折叠操作：需要一个初始值，和一个函数，该初始值使用于函数，然后函数的输出，继续在作为函数的输入
     */

    public static <T, U> U fold(List<T> list, U identity, Function<U, Function<T, U>> f){
        U result = identity;
        for (T t:list) {
            result = f.apply(result).apply(t);
        }
        return result;
    }

    public static <T, U> U foldRight(List<T> list, U identity, Function<T, Function<U, U>> f){
        U result = identity;
        for (T t:list) {
            result = f.apply(t).apply(result);
        }
        return result;
    }


//    =================================================================================================
    public static String addSI(String a, Integer b){
        return "( " + a + " + " + b + " )";
    }

    public static String addSI(Integer a, String b){
        return "( " + a + " + " + b + " )";
    }
    public static void main(String[] args) {
        // 这里加 List<Integer> 而不是 List<>， 否则 fold 无法推断类型
        List<Integer> list = Arrays.asList(1, 2, 4, 5);
        Integer result = fold(list, 0, x -> y -> x + y);
        System.out.println(result);

        String resultLeft = fold(list, "0", x -> y -> addSI(x, y));
        System.out.println(resultLeft);
        String resultRight = foldRight(list, "0", x -> y -> addSI(x, y));
        System.out.println(resultRight);

    }
}

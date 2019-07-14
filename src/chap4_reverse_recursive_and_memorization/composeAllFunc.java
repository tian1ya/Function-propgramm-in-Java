package chap4_reverse_recursive_and_memorization;

import common.CollectionUtilities;
import common.Function;

import java.util.List;

import static common.CollectionUtilities.list;

public class composeAllFunc {

    static <T>Function<T, T> composeAll(List<Function<T, T>> functions){
        return CollectionUtilities.foldRight(functions, Function.identity(), x->y->x.compose(y));
    }

    public static void main(String[] args) {
        Function<String, String> f1 = x -> "(a" +x + ")";
        Function<String, String> f2 = x -> "{a" +x + "}";
        Function<String, String> f3 = x -> "[a" +x + "]";

        System.out.println((composeAll(list(f1, f2, f3)).apply("x")));
    }
}

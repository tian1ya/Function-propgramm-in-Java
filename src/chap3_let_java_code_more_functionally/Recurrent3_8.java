package chap3_let_java_code_more_functionally;

import common.Function;

import java.util.List;

import static common.CollectionUtilities.head;
import static common.CollectionUtilities.tail;

public class Recurrent3_8 {
    // 递归
    public static <T,U> U foldRight(List<T> ts, U identity, Function<T, Function<U, U>> f){
        return ts.isEmpty()
                    ? identity
                    : f.apply(head(ts)).apply(foldRight(tail(ts), identity, f));
    }
}

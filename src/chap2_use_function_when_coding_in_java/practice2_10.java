package chap2_use_function_when_coding_in_java;

import common.Function;

public class practice2_10 {
    /**
     *  柯里化 输入两个数，然后输出这两个数构成的 Tuple
     *
     *  现确定类型和返回类型 <A, B> Function<A, Function<B, Tuple>>
     */

    public static <A, B> Function<A, Function<B, Tuple>> f(){
        return a -> b -> new Tuple(a, b);
    }

    public static void main(String[] args) {
        System.out.println(f().apply(1).apply(4));
    }

}

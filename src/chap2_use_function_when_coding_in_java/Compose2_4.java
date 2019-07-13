package chap2_use_function_when_coding_in_java;


import common.Function;

public class Compose2_4 {
    public static Function<
                Function<Integer, Integer>,
                Function<Function<Integer, Integer>, Function<Integer, Integer>>> compose = x -> y -> z -> x.apply(y.apply(z));

    public static Function<Integer, Integer> triple = x -> x * 3;
    public static Function<Integer, Integer> add = x -> x + 3;


    public static void main(String[] args) {
        System.out.println(compose.apply(triple).apply(add).apply(3));
//        不使用 apply 是并不能成功的
//        System.out.println(Compose2_4.compose(triple, add).apply(3));
    }
}

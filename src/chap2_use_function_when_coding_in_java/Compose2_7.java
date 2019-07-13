package chap2_use_function_when_coding_in_java;


import common.Function;

public class Compose2_7 {
    public static <A,B,C> Function<B, C> partialDoubleVar(A a, Function<A, Function<B, C>> f){
        return f.apply(a);
    }

    public static void main(String[] args) {
        Function<Double, Function<Double, Double>> app = a -> b -> a + b;
        Double apply = partialDoubleVar(2.0, app).apply(3.0);
        System.out.println(apply);
    }
}

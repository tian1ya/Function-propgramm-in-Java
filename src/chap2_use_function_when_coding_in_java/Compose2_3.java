package chap2_use_function_when_coding_in_java;


import common.Function;

public class Compose2_3 {
    public static Function<Integer, Function<Integer, Integer>> add = x -> y -> x + y;

    public static void main(String[] args) {
        System.out.println(add.apply(2).apply(3));
    }
}

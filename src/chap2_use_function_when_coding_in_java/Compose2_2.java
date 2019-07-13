package chap2_use_function_when_coding_in_java;

import common.Function;

public class Compose2_2 {

    public static Function<Integer, Integer> compose(Function<Integer, Integer> f1, Function<Integer, Integer> f2){
        //ambda 表达式，替换匿名类
       return args -> f1.apply(f2.apply(args));
    }
}

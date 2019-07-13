package chap2_use_function_when_coding_in_java;

import common.Function;

public class Compose2_1 {

    public static Function<Integer, Integer> compose(Function<Integer, Integer> f1, Function<Integer, Integer> f2){
        return new Function<Integer, Integer>() {
            @Override
            public Integer apply(Integer args) {
                return f1.apply(f2.apply(args));
            }
        };
    }
}

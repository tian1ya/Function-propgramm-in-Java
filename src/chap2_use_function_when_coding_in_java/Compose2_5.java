package chap2_use_function_when_coding_in_java;


import common.Function;

public class Compose2_5{
    public static <T, U, V> Function<Function<U, V>, Function<Function<T, U>, Function<T, V>>> higherAndThen(){
        // 这里特别注意T U V 的书写步骤
        // 首先 Function<Function<U, V>,   Function<Function<T, U>, Function<T, V>>>
        //              F1是第一个函数参数， F2是第二个函数参数，第三个F3是整体的函数输入到输出类型的定义
        // 应该先注意输入和输出的类型映射，也就是 F3, 输入T， 输出V，
        // 第一次复合使用 F2 函数，也就是接受 T -> U, 然后
        // 然后将F2 的输出作为F1 的输入，也就是完成 U -> V
        // 得到结果，也就是 T -> V

        // 如果你使用 higherAndThen().apply(triple).apply(add).apply(3)
        // 那么会编译失败，Java 并不会上述的 类型推到
        // 所以给 T, U,V 全部都给了 Object 类型，但是你给的 triple， add 参数的类型都是Interger 的，编译失败
        //
        // 那么需要显示的给出当前需要的 类型
//        Compose2_5.<Integer, Integer, Integer>higherAndThenv2().apply(triple).apply(add).apply(3)
        return x -> y -> z -> x.apply(y.apply(z));

    }

    public static <T, U, V> Function<Function<T, U>, Function<Function<U, V>, Function<T, V>>> higherAndThenv2(){
        return x -> y -> z -> y.apply(x.apply(z));
    }

    public static Function<Integer, Integer> triple = x -> x * 3;
    public static Function<Integer, Integer> add = x -> x + 3;


    public static void main(String[] args) {
//        System.out.println(higherAndThenv2().apply(triple).apply(add).apply(3));
        System.out.println(Compose2_5.<Integer, Integer, Integer>higherAndThenv2().apply(triple).apply(add).apply(3));

    }
}

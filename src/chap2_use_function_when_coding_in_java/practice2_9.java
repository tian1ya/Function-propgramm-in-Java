package chap2_use_function_when_coding_in_java;

import common.Function;

public class practice2_9 {
    /**
     *  <A, B, C, D> String Func(A a, B b, C c, D d){
     *      return String.format("%s, %s, %s, %s", a, b, c,d)
     *  }
     */

   public static <A, B, C, D> Function<A, Function<B, Function<C, Function<D, String>>>> f(){
       return a -> b -> c -> d -> String.format("%s, %s, %s, %s", d, c, b, a);
   }
// 第一次完成的时候，只在返回类型中加入了 A, B,C,D 的四个参数， 但是这只是输入的参数，最终还需要一个输出的参数
// 先确定类型和输出函数，然后再写函数体

    public static void main(String[] args) {
        String apply = f().apply("a").apply("b").apply("c").apply("d");
        System.out.println(apply);
    }

}

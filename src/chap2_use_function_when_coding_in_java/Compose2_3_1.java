package chap2_use_function_when_coding_in_java;



public class Compose2_3_1 {
    public static BinaryOperation add = x -> y -> x + y;
    public static BinaryOperation multi = x -> y -> x * y;

    // 这里使用 apply 看着确实很难受，在Scala 中可以使用 add(2)(3)
    public static void main(String[] args) {
        System.out.println(add.apply(2).apply(3));
    }
}

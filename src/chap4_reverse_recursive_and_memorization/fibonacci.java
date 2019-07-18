package chap4_reverse_recursive_and_memorization;

import static chap4_reverse_recursive_and_memorization.TailCall.ret;
import static chap4_reverse_recursive_and_memorization.TailCall.sus;

public class fibonacci {

    static Integer fibonacciV1(Integer n){
        if (n == 0 || n == 1){
            return n;
        }
        return fibonacciV1(n-1) + fibonacciV1(n-1);
    }
    // 第一次写的函数式这样的，但是这确实最最最致命的函数，因为每一个 递归回产生两个 f 函数，

    // 使用 尾递归

    static Integer fibonacciV2(Integer n, Integer acc1, Integer acc2){
        if (n == 0){
            return 0;
        }else if (n==1){
            return acc1 + acc2;
        }
        return fibonacciV2(n-1, acc2, acc2 + acc1);
    }
    // 以上的计算，解决V1 的问题，但是还会有栈溢出的问题
    static TailCall<Integer> fibonacciV3(Integer n, Integer acc1, Integer acc2){
        if (n == 0){
            return ret(0);
        }else if (n==1){
            return ret(acc1  + acc2);
        }else {
            return sus(() -> fibonacciV3(n-1, acc2, acc1 + acc2));
        }
    }

    public static void main(String[] args) {
        // 实现方法几乎是一样的但是呢，并没有出现书中介绍的结果

        // 栈会溢出版本
//        System.out.println(fibonacciV2(5000, 1, 0));

        // 栈不会溢出版本
        // 完全不懂了  ？？？？？？？？
        System.out.println(fibonacciV3(5000, 1, 0).eval());
    }
}

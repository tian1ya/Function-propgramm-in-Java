package chap2_use_function_when_coding_in_java;


import common.Function;

public class Compose2_6 {
    Double ratiGloableVar = 0.9;
    public void aMethod(){
        Double rati = 0.9;
//      这里会编译出错，需要将 rati 声明为 final 的， ratio 是局部变量
//        Function<Double, Double> salary = price -> price + price + rati;
    }

    public void aMethodGloable(){
        // 这里是全局的变量，所以是可以编程成功的，
        Function<Double, Double> salary = price -> price + price + ratiGloableVar;
    }


}

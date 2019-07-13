package chap2_use_function_when_coding_in_java;

import common.Function;

class TaxPrice{
    private Double ratio;

    public TaxPrice(Double ratio) {
        this.ratio = ratio;
    }

    public Double price(Double price){
        return (1 + ratio) * price;
    }
}


public class partialApplicationAndCurry {

    public static Function<Double, Function<Double, Double>> totalPrice = ratio -> salary -> (1+ratio) * salary;


    public static void main(String[] args) {

        // 面向对象计算
        TaxPrice taxPrice = new TaxPrice(0.1);
        Double price = taxPrice.price(100.0);

        // 柯里化方式


        Function<Double, Double> totalPriceFunc = totalPrice.apply(0.1);
        System.out.println(price + " " + totalPriceFunc.apply(100.0));

    }
}

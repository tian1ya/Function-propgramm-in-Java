package chap1_what_is_functional_programm;

import common.Tuple;

import java.util.Collections;
import java.util.List;

public class DonutShop {

    public static Tuple<Donut, Payment> buyDonut(CreditCart creditCart){
        Donut donut = new Donut();

        // 这里会有副作用，因为一般在支付的时候，信用卡的支付，会调用银行，检查信用卡是否授权等，会有比较强的依赖
        // 而且测试的时候，还需要mock 这些依赖类
//        creditCart.Charge(Donut.price);
        // 解除依赖，那么应该做的就是，这个函数应该是返回待买的甜甜圈和表示支付的东西  也就是 PayMent 类
        // 有了支付的东西，需要将支付东西和甜甜圈一起返回回去，构建一个新的类，将二者包起来
        // 也就是 Purchase 类
//        return donut;

        // V2 这里只能购买一份
        return new Tuple<>(new Donut(), new Payment(creditCart, Donut.price));
    }

    public static Tuple<List<Donut>, Payment> buyDonuts(int quantity, CreditCart creditCart){
        return new Tuple<>(Collections.nCopies(quantity, new Donut()), new Payment(creditCart, Donut.price * quantity));
    }


}

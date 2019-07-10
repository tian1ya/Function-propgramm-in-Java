package chap1_what_is_functional_programm;

import java.util.List;

public class TestScript {

    public static void test_buy_donuts(){
        CreditCart creditCart = new CreditCart();
        Tuple<List<Donut>, Payment> purchase = DonutShop.buyDonuts(5, creditCart);

        System.out.println(Donut.price * 5 == purchase._2.amount);
        System.out.println(creditCart == purchase._2.creditCart);
    }

    public static void main(String[] args) {
        test_buy_donuts();
    }
}

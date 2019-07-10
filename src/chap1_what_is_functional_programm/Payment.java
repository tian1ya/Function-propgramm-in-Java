package chap1_what_is_functional_programm;

import java.util.List;

public class Payment {

    /**
     * 表示支付的这个东西
     * 1. 需要支付的金额
     * 2. 信用卡
     */

    public final CreditCart creditCart;
    public final Double amount;

    public Payment(CreditCart creditCart, Double amount) {
        this.creditCart = creditCart;
        this.amount = amount;
    }

    // 一次可以购买多个
    public Payment combine(Payment payment){
        if (creditCart.equals(payment.creditCart)){
            return new Payment(creditCart, amount + payment.amount);
        }else {
            throw new IllegalStateException("card don't match");
        }
    }
}

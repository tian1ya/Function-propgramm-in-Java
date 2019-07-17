package chap4_reverse_recursive_and_memorization;

import common.Function;

import java.util.List;
import java.util.function.Supplier;

import static chap4_reverse_recursive_and_memorization.TailCall.ret;
import static chap4_reverse_recursive_and_memorization.TailCall.sus;
import static common.CollectionUtilities.head;
import static common.CollectionUtilities.tail;


abstract class TailCall<T>{

    // 开始调用下一个
    public abstract TailCall<T> resume();
    // 返回结果
    public abstract T eval();
    // 决定是 suspend 还是 return
    public abstract boolean isSuspend();

    static class Return<T> extends TailCall<T>{
        private final T t;
        public Return(T t) {
            this.t = t;
        }

        @Override
        public TailCall<T> resume() {
            throw new IllegalStateException("return has no resume");
        }

        @Override
        public T eval() {
            return t;
        }

        @Override
        public boolean isSuspend() {
            return false;
        }
    }

    static class Suspend<T> extends TailCall<T>{

        public Suspend(Supplier<TailCall<T>> resume) {
            this.resume = resume;
        }

        private final Supplier<TailCall<T>> resume;

        @Override
        public TailCall<T> resume() {
            return resume.get();
        }

        @Override
        public T eval() {
            TailCall<T> integerTailCall = this;
            while (integerTailCall.isSuspend()){
                integerTailCall = integerTailCall.resume();
            }
            return integerTailCall.eval();
        }

        @Override
        public boolean isSuspend() {
            return true;
        }
    }

    public static <T> Return<T> ret(T t){
        return new Return<>(t);
    }

    public static <T> Suspend<T> sus(Supplier<TailCall<T>> s){
        return new Suspend<T>(s);
    }
}



public class recurrent {
    static Integer sumV1(List<Integer> list){
        return list.isEmpty()
                ? 0
                : head(list) + sumV1(tail(list));
    }

    static Integer sumV2(List<Integer> list, int acc){
        return list.isEmpty()
                ? acc
                : sumV2(tail(list), acc + head(list));
    }



    static TailCall<Integer> sumV3(int x, int y){
        return y == 0
                ? new TailCall.Return<>(x)
                : new TailCall.Suspend<>(() -> sumV3(x+1,y-1));
    }

    static TailCall<Integer> sumV4(int x, int y){
        return y == 0
                ? ret(x)
                : sus(() -> sumV4(x + 1, y - 1));
    }



    public static void main(String[] args) {
        TailCall<Integer> integerTailCall = sumV3(3, 100);
        while (integerTailCall.isSuspend()){
           integerTailCall = integerTailCall.resume();
        }
        System.out.println(integerTailCall.eval());
        System.out.println(sumV3(3, 100).eval());
        System.out.println(sumV4(3,100).eval());
    }

}

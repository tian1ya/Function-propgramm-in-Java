package chap5_again;

import java.util.function.Supplier;

abstract class TailCall<T>{

    public abstract TailCall<T> resume();
    public abstract T eval();
    public abstract boolean isSuspend();

    static class Return<T> extends TailCall<T> {
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

    static class Suspend<T> extends TailCall<T> {

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

    public static <T> TailCall.Return<T> ret(T t){
        return new TailCall.Return<>(t);
    }

    public static <T> Suspend<T> sus(Supplier<TailCall<T>> s){
        return new TailCall.Suspend<T>(s);
    }
}
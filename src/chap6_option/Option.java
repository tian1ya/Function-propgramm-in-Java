package chap6_option;

public abstract class Option<A> {
    private static Option none = new None();
    public abstract A getOrElse();

    private static class None<A> extends Option<A>{

        private None() { }

        @Override
        public A getOrElse() {
            throw new IllegalStateException("get call on None");
        }

        @Override
        public String toString() {
            return "None";
        }
    }

    private static class Some<A> extends Option<A> {

        private final A value;

        private Some(A value) {
            this.value = value;
        }

        @Override
        public A getOrElse() {
            return value;
        }

        @Override
        public String toString() {
            return "Some{" +
                    "value=" + value +
                    '}';
        }
    }

    public static <A> Option<A> some(A value){
        return new Some<>(value);
    }

    public static <A> Option<A> none(){
        // 返回单例
        return none;
    }

}

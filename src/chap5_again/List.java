package chap5_again;

import common.Function;

import static chap5_again.TailCall.ret;
import static chap5_again.TailCall.sus;

public abstract class List<A> {
    public abstract A head();
    public abstract List<A> tails();
    public abstract boolean isEmpty();
    public static final List NIL=new Nil();
    public abstract List<A> setHead(A head);
    public abstract String toString();

    private List(){}

    private static class Nil<A> extends List<A>{

        @Override
        public A head() {
            throw new IllegalStateException("call an empty list");
        }

        @Override
        public List<A> tails() {
            throw new IllegalStateException("call an empty list");
        }

        @Override
        public boolean isEmpty() {
            return true;
        }

        @Override
        public List<A> setHead(A head) {
            return null;
        }

        @Override
        public String toString() {
            return "Nil";
        }
    }

    private static class Cons<A> extends List<A>{

        private A head;
        private List<A> tails;

        public Cons(A head, List<A> tails) {
            this.head = head;
            this.tails = tails;
        }

        @Override
        public A head() {
            return head;
        }

        @Override
        public List<A> tails() {
            return tails;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public List<A> setHead(A head) {
            return new Cons<>(head, this.tails);
        }

        @Override
        public String toString() {
            return String.format("%sNil", toString_(new StringBuffer(), this).eval());
        }

        private TailCall<StringBuffer> toString_(StringBuffer acc, List<A> list) {
            return list.isEmpty()
                    ? ret(acc)
                    : sus(() -> toString_(acc.append(list.head()).append(", "), list.tails()));
        }
    }

    public static <A> List<A> list(){
        return new Nil<>();
    }

    public static <A> List<A> list(A ... a){
        List<A> ini = list();
        for (int i = a.length-1; i >= 0; i--) {
            ini = new Cons<>(a[i], ini);
        }
        return ini;
    }

    public List<A> cons(A head){
        return new Cons<>(head, this);
    }

    public static <A> List<A> setHead(List<A> list, A head){
        if (list.isEmpty()){
            throw new IllegalStateException("call an empty list");
        }else {
            return list.setHead(head);
        }
    }

    public List<A> drop(Integer n){
        return drop_(this,n).eval();
    }

    private TailCall<List<A>> drop_(List<A> list, Integer n){
        return list.isEmpty() || n<= 0
                ? ret(list)
                : sus(() -> drop_(list.tails(), n-1));
    }

    public List<A> dropWhile(Function<A, Boolean> f){
        return dropWhile_(this, f).eval();
    }

    private TailCall<List<A>> dropWhile_(List<A> list, Function<A, Boolean> f){
        return list.isEmpty() || !f.apply(list.head())
                ? ret(list)
                : sus(() -> dropWhile_(list.tails(), f));
    }

    public List<A> reverse(){
        return reverse_(list(), this).eval();
    }

    private TailCall<List<A>> reverse_(List<A> acc, List<A> list){
        return list.isEmpty()
                ? ret(acc)
                : sus(() -> reverse_(new Cons<>(list.head(), acc), list.tails()));
    }

    public List<A> init(){
        return this.reverse().tails().reverse();
    }

    public static <A, B> B foldRight(List<A> list, B n, Function<A, Function<B, B>> f){
        return list.isEmpty()
                ? n
                : foldRight(list.tails(), f.apply(list.head()).apply(n), f);
    }

    public int length(){
        return foldRight(this, 0, x -> y -> y + 1);
    }

    public <B> B foldLeft(B identity, Function<B, Function<A, B>> f){
        return foldLeft_(identity, this, f).eval();
    }

    private <B> TailCall<B> foldLeft_(B identity, List<A> list, Function<B, Function<A, B>> f){
        return list.isEmpty()
                ? ret(identity)
                : sus(() -> foldLeft_(f.apply(identity).apply(list.head()), list.tails(), f));
    }

    public <B> B foldRightV2(B identity, Function<B, Function<A, B>> f){
        return foldLeft_(identity, this.reverse(), f).eval();
    }

    private <B> TailCall<B> foldRightV2_(B identity, List<A> list, Function<B, Function<A, B>> f){
        return list.isEmpty()
                ? ret(identity)
                : sus(() -> foldLeft_(f.apply(identity).apply(list.head()), list.tails(), f));
    }






    public static Integer sumViafoldLeft(List<Integer> list){
        return list.foldLeft(0, x -> y -> x + y);
    }

    public static Integer multiViafoldLeft(List<Integer> list){
        return list.foldLeft(1, x -> y -> x * y);
    }

    public static Integer lengthViafoldLeft(List<Integer> list){
        return list.foldLeft(0, x -> y -> x + 1);
    }

    public static <A> List<A> reverseViafoldLeft(List<A> list){
        return list.foldLeft(list(), x->y->x.cons(y));
    }

    public static <A> List<A> concat(List<A> list1, List<A> list2){
        return list1.foldLeft(list2, x->y->x.cons(y));
    }

    public static List<Integer> triple(List<Integer> list){
        return list.reverse().foldLeft(list(), x -> y -> new Cons<>(y*3, x));
    }

    public <B> List<B> map(Function<A, B> f){
        return foldRightV2(list(), x -> y -> new Cons<>(f.apply(y), x));
    }

//    public <B> List<B> filter(Function<B, Boolean> f){
//        return foldRightV2(list(), x -> y -> f.apply(x) ? new Cons<>(x, y) : y);
//    }

}

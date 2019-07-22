package chap5_data_processing_by_list_structure;


import common.Function;

import java.util.function.Supplier;


abstract class TailCall<T>{

    // 开始调用下一个
    public abstract TailCall<T> resume();
    // 返回结果
    public abstract T eval();
    // 决定是 suspend 还是 return
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

public abstract class List<A> {

    public abstract A head();
    public abstract List<A> tails();
    public abstract boolean isEmpty();
    public abstract List<A> setHeader(A newHeader);
    public static final List NIL = new Nil();

    private static class Nil<A> extends List<A> {
        @Override
        public String toString() {
            return "NIL";
        }

        @Override
        public A head() {
            throw new IllegalStateException("head called an empty list");
        }

        @Override
        public List<A> tails() {
            throw new IllegalStateException("head called an empty list");
        }

        @Override
        public boolean isEmpty() {
            return true;
        }

        @Override
        public List<A> setHeader(A newHeader) {
            throw new IllegalStateException("head called an empty list");
        }
    }

    /*
        子类式私有的，可以直接通过类名就能调用
     */
    private static class Cons<A> extends List<A>{

        public static <T> StringBuffer toString(StringBuffer stringBuffer, List<T> acc) {
            return acc.isEmpty()
                    ? stringBuffer
                    : toString(stringBuffer.append(acc.head()), acc.tails());


        }

        private final A head;
        private final List<A> tail;

        public Cons(A head, List<A> tail) {
            this.head = head;
            this.tail = tail;
        }

        @Override
        public A head() {
            return this.head;
        }

        @Override
        public List<A> tails() {
            return this.tail;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }



        public static <A> List<A> list(){
            return new Nil<>();
        }

        public static <A> List<A> list(A ... as){
            List<A> list = list();
            for (A a: as) {
                list = new Cons<>(a, list);
            }
            return list;
        }

        // 在 list 头部增加一个元素
        public List<A> cons(A a){
            return new Cons<A>(a, this);
        }

        // 替换新值
        // 第一直观的实现方式，如下，但是呢，函数式编程中，当出现 if -else 就应该意识到这并不是最好的实现方式，
        // 应该有什么方式可以替换的
        // 在 list 中新添加 setHeader 这么一个 接口
//        public static <A> List<A> setHeader(A newHeader, List<A> list){
//            if (list.isEmpty()){
//                throw new IllegalStateException("list must not be empty");
//            }else {
//                return new Cons<>(newHeader, list.tails());
//            }
//        }
        @Override
        public List<A> setHeader(A newHeader) {
            return new Cons<>(newHeader, tails());
        }

        public static <A> List<A> drop(Integer n, List<A> cons){

            return n == 0 || cons.isEmpty()
                    ? cons
                    : drop(n-1, cons.tails());
        }
        //  满足第一个条件的首元素，丢弃，但是这里的首元素是从list 的尾开始到头的，因为插数据的时候，是先进后处
        public static <A> List<A> dropWhile(List<A> list, Function<A, Boolean> f){
            return !list.isEmpty() && f.apply(list.head())
                    ? dropWhile(list.tails(), f)
                    : list;
        }

        
        // 神奇
        public static <A> List<A> concat(List<A> list1, List<A> list2){
            return list1.isEmpty()
                    ? list2
                    : new Cons(list1.head(), concat(list1.tails(), list2));
        }
        // drop tails one
        public List<A> init(){
            return reverse_(list(), reverse().tails());
        }

        public List<A> reverse(){
            return reverse_(list(), this);
        }

        private List<A> reverse_(List<A> acc, List<A> acc2){
            return acc2.isEmpty()
                    ? acc
                    : reverse_(new Cons<>(acc2.head(), acc), acc2.tails());
        }

        public static Integer sum(List<Integer> ls){
            return ls.isEmpty()
                    ? 0
                    : ls.head() + sum(ls.tails());
        }

        public static Double product(List<Double> ls){
            return ls.isEmpty()
                    ? 1.0
                    : ls.head() * product(ls.tails());
        }

        // 右折叠，也就是 初始值是在 list 的右侧，也就是最后一个才加入到计算        
        public static <A, B> B foldRight(List<A> list, B identity, Function<A, Function<B, B>> f){
            return list.isEmpty()
                    ? identity
                    : f.apply(list.head()).apply(foldRight(list.tails(), identity, f));
        }


        public static <A> List<A> concatViaFoldRight(List<A> list1, List<A> list2){
            return foldRight(list1, list2, x->y->new Cons<>(x, y));
        }


        // 左折叠，也就是 初始值是在 list 的左侧，也就是第一个就加入到计算 
        public static <B, A> B foldLeft(B identity, List<A> list, Function<B, Function<A, B>> f){
            return list.isEmpty()
                    ? identity
                    : foldLeft(f.apply(identity).apply(list.head()), list.tails(), f);
        }

        public static <B, A> B foldLeftV2(B identity, List<A> list, Function<B, Function<A, B>> f){
            return foldLeftV2_(identity, list,  f).eval();
        }
        public static <B, A> TailCall<B> foldLeftV2_(B identity, List<A> list, Function<B, Function<A, B>> f){
            return list.isEmpty()
                    ? TailCall.ret(identity)
                    : TailCall.sus(() -> foldLeftV2_(f.apply(identity).apply(list.head()), list.tails(), f));
        }

        public static <A> List<A> concatViafoldLeft(List<A> list1, List<A> list2){
            return foldLeft(list1, list2, x->y->new Cons<>(y, x));
        }

        public static <A> List<A> concatViafoldLeftV2(List<A> list1, List<A> list2){
            return foldLeftV2(list1, list2, x->y->new Cons<>(y, x));
        }

    }

    public static void main(String[] args) {
        List<Object> list = Cons.list();
        List<Integer> list1 = Cons.list(1,2);
        List<Integer> list2 = Cons.list(2, 2, 3, 2);

        System.out.println(List.Cons.toString(new StringBuffer(), list2));
        List<Integer> drop = Cons.drop(1, list2);
        System.out.println(List.Cons.toString(new StringBuffer(), drop));

        List<Integer> x1 = Cons.dropWhile(list2, x -> x.equals(2));
        System.out.println(List.Cons.toString(new StringBuffer(), x1));


        List<Integer> x2 = Cons.concat(list2, list1);
        System.out.println(List.Cons.toString(new StringBuffer(), x2));

        System.out.println("================================");
        Cons<Integer> forInit = new Cons<>(10, list2);
        System.out.println(List.Cons.toString(new StringBuffer(), forInit));
        System.out.println(List.Cons.toString(new StringBuffer(), forInit.init()));

        System.out.println("================================");
        Function<Integer, Function<Integer, Integer>> sum = x -> y -> x + y;
        Function<Integer, Function<Integer, Integer>> prd = x -> y -> x * y;
        Function<Integer, Function<Integer, Integer>> len = x -> y -> y + 1;
        System.out.println(List.Cons.foldRight(list2, 0, sum));
        System.out.println(List.Cons.foldRight(list2, 1, prd));
        System.out.println(List.Cons.foldRight(list2, 0, len));


        System.out.println("================================");
        // 注意这里 len 的左右折叠的不同写法，在右折叠中，第二个参数是 identity，所以是 y+1
        // 而在左折叠中 identity 是在 第一个参数中，所以是 x+1
        Function<Integer, Function<Integer, Integer>> lenLeft = x -> y -> x + 1;
        System.out.println(Cons.foldLeft(0, list2, sum));
        System.out.println(Cons.foldLeft(1, list2, prd));
        System.out.println(Cons.foldLeft(0, list2, lenLeft));

        System.out.println(Cons.foldLeftV2(0, list2, sum));
        System.out.println(Cons.foldLeftV2(1, list2, prd));
        System.out.println(Cons.foldLeftV2(0, list2, lenLeft));

        List<Integer> integerListR = Cons.concatViaFoldRight(list1, list2);
        System.out.println(List.Cons.toString(new StringBuffer(), integerListR));

        List<Integer> integerListl = Cons.concatViafoldLeft(list1, list2);
        System.out.println(List.Cons.toString(new StringBuffer(), integerListl));

        List<Integer> integerListlV2 = Cons.concatViafoldLeft(list1, list2);
        System.out.println(List.Cons.toString(new StringBuffer(), integerListlV2));

    }
}

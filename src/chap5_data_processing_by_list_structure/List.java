package chap5_data_processing_by_list_structure;



public abstract class List<A> {

    public abstract A head();
    public abstract List<A> tails();
    public abstract boolean isEmpty();
    public abstract List<A> setHeader(A newHeader);
    public static final List NIL = new Nil();

    private static class Nil<A> extends List<A> {
        @Override
        public String toString() {
            return "[NIL]";
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
        @Override
        public String toString() {
            return "[" + " head= " + head + ", tail=" + tail + ']';
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
    }

    public static void main(String[] args) {
        List<Object> list = Cons.list();
        List<Integer> list1 = Cons.list(1);
        List<Integer> list2 = Cons.list(1, 2, 3, 3);
    }
}

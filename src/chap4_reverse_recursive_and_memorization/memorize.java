package chap4_reverse_recursive_and_memorization;

import common.Function;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class memorize {

    static class Tuple3<T, U, V> {
        private T _1;
        private U _2;
        private V _3;

        public Tuple3(T _1, U _2, V _3) {
            this._1 = _1;
            this._2 = _2;
            this._3 = _3;
        }

        @Override
        public String toString() {
            return "Tuple3{" +
                    "_1=" + _1 +
                    ", _2=" + _2 +
                    ", _3=" + _3 +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Tuple3<?, ?, ?> tuple3 = (Tuple3<?, ?, ?>) o;
            return Objects.equals(_1, tuple3._1) &&
                    Objects.equals(_2, tuple3._2) &&
                    Objects.equals(_3, tuple3._3);
        }

        @Override
        public int hashCode() {
            return Objects.hash(_1, _2, _3);
        }
    }

    static class Doubler{
        private static Map<Integer, Integer> cache = new ConcurrentHashMap<>();
        public static Integer doubleValue(Integer value){
            return cache.computeIfAbsent(value, x -> x * 2);
        }
        public static Function<Integer, Integer> doubleValue = x -> cache.computeIfAbsent(x, y -> y * 2);
    }


    static class Memoizer<U, T>{
        private final Map<U, T> cache = new ConcurrentHashMap<>();

        public static <U, T> Function<U, T> memorizer(Function<U, T> f){
            return new Memoizer<U, T>().doMemorize(f);
        }

        private Function<U, T> doMemorize(Function<U, T> f){
            return input -> cache.computeIfAbsent(input, f::apply);
        }

        // 多参数
       static Function<Integer, Function<Integer, Integer>> multi = Memoizer.memorizer(x -> Memoizer.memorizer(y -> x + y));
        static Function<Tuple3<Integer, Integer, Integer>, Integer> tUPLEmulti = x -> x._1 + x._2 + x._3;
    }




    public static void main(String[] args) {
        System.out.println(Doubler.doubleValue(3));
        System.out.println(Doubler.doubleValue.apply(3));


        Function<Integer, Integer> f = x -> x* 2;

//        如果是下面的写法类型推断会出错，
//        System.out.println(Memoizer.memorizer(f = x -> x* 2).apply(3));
        System.out.println(Memoizer.memorizer(f).apply(3));

        System.out.println(Memoizer.multi.apply(3).apply(4));
    }
}

package common;

import org.relaxng.datatype.DatatypeException;

public interface Function<T, U> {
    U apply(T args);

    default <V> Function<V, U> compose(Function<V, T> f){
        return x -> apply(f.apply(x));
    }

    default <V> Function<T, V> andThen(Function<U, V> f){
        return x -> f.apply(apply(x));
    }

    static  <T> Function<T, T> identity(){
        return t ->t;
    }

    static <T, V, U> Function<V, U> compose(Function<T, U> f, Function<V, T> g){
        return x -> f.apply(g.apply(x));
    }

    static <T, V, U> Function<T, U> andThen(Function<T, V> f, Function<V, U> g){
        return x -> g.apply(f.apply(x));
    }

    static <T, V, U> Function<Function<T, U>,
                              Function<Function<U, V>,
                                       Function<T, V>>> compose(){
//        return x -> y -> y.compose(x);
        return x -> y -> z -> y.apply(x.apply(z));
    }

    static <T, V, U> Function<Function<T, U>,
                              Function<Function<V, T>,
                                        Function<V, U>>> andThen(){
        return x -> y -> y.andThen(x);
//        return x -> y -> z -> x.apply(y.apply(z));
    }

    static <T, V, U> Function<Function<T, U>,
                              Function<Function<U,V>,
                                        Function<T, V>>> higherAndThen(){
        return x -> y -> z -> y.apply(x.apply(z));
    }

    static <T, V, U> Function<Function<T, U>,
                              Function<Function<U,V>,
                                        Function<T, V>>> higherCompose(){
        return x -> y -> z -> y.apply(x.apply(z));
    }

}

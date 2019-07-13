package chap2_use_function_when_coding_in_java;

public class Tuple<T, U>{
    public T _1;
    public U _2;

    public Tuple(T _1, U _2) {
        this._1 = _1;
        this._2 = _2;
    }

    @Override
    public String toString() {
        return "{" +
                "_1=" + _1 +
                ", _2=" + _2 +
                '}';
    }
}

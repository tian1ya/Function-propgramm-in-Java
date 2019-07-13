package chap3_let_java_code_more_functionally.commandToFunctional;

import common.Effect;
import common.Executable;
import common.Function;

import java.util.regex.Pattern;

/**
 * 验证逻辑拆出来
 */

interface ResultV5<T> {
    void bind(Effect<T> success, Effect<T> failed);
    public static <T> ResultV5<T> failure(T msg){
        return new Failure<T>(msg);
    }

    public static <T> ResultV5<T> success(T msg){
        return new Success<T>(msg);
    }
}

class Success<T> implements ResultV5<T>{

    private T msg;
    public Success(T msg) {
        this.msg = msg;
    }

    @Override
    public void bind(Effect<T> success, Effect<T> failed) {
        success.apply(msg);
    }
}

class Failure<T> implements ResultV5<T>{

    private T msg;
    public Failure(T msg) {
        this.msg = msg;
    }

    @Override
    public void bind(Effect<T> success, Effect<T> failed) {
        failed.apply(msg);
    }
}

public class V5 {
    static final Pattern emailPattern = Pattern.compile("^([a-z0-9A-Z])+@([a-z0-9A-Z])$");

    static Function<String, ResultV5> emailChecker = s ->
        s == null ? new Failure("mail is null")
                  : s.isEmpty()
                        ? new Failure("mail is empty")
                        : emailPattern.matcher(s).matches()
                            ? new Success(s + " is valid")
                            : new Failure("mail " +s + " is invalid");

    static void testMail(){
        emailChecker.apply("").bind(success, failure);
        emailChecker.apply(null).bind(success, failure);
        emailChecker.apply("3@c").bind(success, failure);
        emailChecker.apply("3@com.nihao").bind(success, failure);
    }

    static Effect<String> success = s -> System.out.println(s + " success");
    static Effect<String> failure = f -> System.out.println(f + " fauled");
    public static void main(String[] args) {
        testMail();
    }
}




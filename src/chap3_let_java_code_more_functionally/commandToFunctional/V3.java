package chap3_let_java_code_more_functionally.commandToFunctional;

import common.Function;

import java.util.regex.Pattern;

/**
 * 处理更多的异常结果 定义一个 ResultV3 的结果组件
 */

interface ResultV3 {
    public class Success implements ResultV3 {}
    public class failed implements ResultV3 {
        private final String errorMsg;

        public failed(String errorMsg) {
            this.errorMsg = errorMsg;
        }

        public String getErrorMsg() {
            return errorMsg;
        }
    }
}
public class V3 {
    static final Pattern emailPattern = Pattern.compile("^([a-z0-9A-Z])+@([a-z0-9A-Z])$");

    static Function<String, ResultV3> emailChecker = s -> {
        if (s == null){
            return new ResultV3.failed("mail is null");
        }else if (s.isEmpty()){
            return new ResultV3.failed("mail is empty");
        }else if (emailPattern.matcher(s).matches()){
            return new ResultV3.Success();
        }else {
            return new ResultV3.failed("mail " +s + " is invalid");
        }
    };

    static void validate(String mail){
        ResultV3 result = emailChecker.apply(mail);
        if (result instanceof ResultV3.failed){
            logErrorMsg(mail);
        }else {
            sendVerificationMail(mail);
        }
    }

    static void testMail(){
      validate("");
      validate(null);
      validate("3@c");
      validate("3@com.nihao");
    }

    private static void logErrorMsg(String mail) {
        System.out.println("error mail logged " + mail);
    }

    private static void sendVerificationMail(String mail) {
        System.out.println(" verification mail send to " + mail);
    }

    public static void main(String[] args) {
        testMail();
    }
}
/**
 *  这里的一个新的问题来了：
 *      1. 使用 instanceOf 是在式太丑了
 *      2. validate 方法并没有返回，你无法测试里面的逻辑
 *
 *  所以现在就来解决第2个问题，
 *  那就是使用 validate 返回一个指令，然后返回之后，我再去执行，而使用 almbda 正好可以很好的解决
 */



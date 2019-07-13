package chap3_let_java_code_more_functionally.commandToFunctional;

import common.Executable;
import common.Function;

import java.util.regex.Pattern;

/**
 * 处理更多的异常结果 定义一个 ResultV3 的结果组件
 */

interface ResultV4 {
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

public class V4 {
    static final Pattern emailPattern = Pattern.compile("^([a-z0-9A-Z])+@([a-z0-9A-Z])$");

    static Function<String, ResultV3> emailChecker = s ->
        s == null ? new ResultV3.failed("mail is null")
                  : s.isEmpty()
                        ? new ResultV3.failed("mail is empty")
                        : emailPattern.matcher(s).matches()
                            ? new ResultV3.Success()
                            : new ResultV3.failed("mail " +s + " is invalid");

    static Executable validate(String mail){
        ResultV3 result = emailChecker.apply(mail);
        return result instanceof ResultV3.failed ?
                ()-> logErrorMsg(mail):
                () -> sendVerificationMail(mail);
    }

    static void testMail(){
        validate("").exec();
        validate(null).exec();
        validate("3@c").exec();
        validate("3@com.nihao").exec();
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
 *  1 在碰到 if else 的时候，看是否能够使用 三目运算符号替换
 *
 *  代码中还有 instanceOf
 *  将验证逻辑也拆离出来
 */




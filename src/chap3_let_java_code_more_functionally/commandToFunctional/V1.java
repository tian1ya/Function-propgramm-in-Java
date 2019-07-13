package chap3_let_java_code_more_functionally.commandToFunctional;

import java.util.regex.Pattern;


public class V1 {
    final Pattern emailPattern = Pattern.compile("^([a-z0-9A-Z])+@([a-z0-9A-Z])$");

    void testMail(String mail){
        if (emailPattern.matcher(mail).matches()){
            sendVerificationMail(mail);
        }else{
            logErrorMsg(mail);
        }
    }

    private void logErrorMsg(String mail) {
        System.out.println("error mail logged " + mail);
    }

    private void sendVerificationMail(String mail) {
        System.out.println(" verification mail send to " + mail);
    }

    public static void main(String[] args) {
        V1 v1 = new V1();
        v1.testMail("7@q");
        v1.testMail("7@q.com");
    }
}
/**
 *  首先这里并不是函数式的风格，在这个 emailPattern 式没有必须要定义为 final的
 *
 *  在 testMail 方法中，定义了两部分，一个数对数据的处理，和方法的作用。
 *
 *  所以接下来首先就是，将计算和作用分开，
 */

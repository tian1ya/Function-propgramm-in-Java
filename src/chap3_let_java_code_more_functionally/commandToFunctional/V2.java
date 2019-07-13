package chap3_let_java_code_more_functionally.commandToFunctional;

import common.Function;

import java.util.regex.Pattern;

/**
 * testMail 中的计算和作用分开
 *
 * 定义 emailChecker 这样就可以测试数据处理部分，
 */

public class V2 {
    final Pattern emailPattern = Pattern.compile("^([a-z0-9A-Z])+@([a-z0-9A-Z])$");

    final Function<String, Boolean> emailChecker = s -> emailPattern.matcher(s).matches();

    void testMail(String mail){
        if (emailChecker.apply(mail)){
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
        V2 v1 = new V2();
        v1.testMail("7@q");
        v1.testMail("7@q.com");
    }
}

/**
 *  然后这里还有不够完美的地方，其实这也不是和命令式或者函数式有关
 *
 *  上面的代码处理邮件格式不合适并不够，如果碰见空，怎么处理，如果 null 又怎么去处理等等问题
 */


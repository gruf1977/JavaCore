package lesson3;


import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class Password {

    public static boolean validPassword(String password) {
        boolean res = false;
        Pattern p = Pattern.compile("^.{8,20}$");
        Matcher m = p.matcher(password);
        if (m.matches()) {
            Pattern p1 = Pattern.compile("^.*[A-Z]+.*$");
            Matcher m1 = p1.matcher(password);
            if (m1.matches()) {
                Pattern p2 = Pattern.compile("^.*[a-z]+.*$");
                Matcher m2 = p2.matcher(password);
                if (m2.matches()) {
                    Pattern p3 = Pattern.compile("^.*[0-9]+.*$");
                    Matcher m3 = p3.matcher(password);
                    if (m3.matches()) {
                        Pattern p4 = Pattern.compile("^.*[^<>{}\"/|;:.,~!?@#$%^=&*\\]\\\\()\\[¿§«»ω⊙¤°℃℉€¥£¢¡®©0-9_+]+.*$");
                        Matcher m4 = p4.matcher(password);
                        if (m4.matches()) {
                            res = true;
                        }

                    }
                }
            }
        }
        return res;
    }


    public static void main(String[] args) {
        if (validPassword("Sfg1sdfgsdsd")) {
            System.out.println("Пароль прошел проверку");
        } else{
        System.out.println("Пароль не прошел проверку");
        }

    }
}


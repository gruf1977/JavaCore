package lesson3;


public class Password {

    public static void validPassword(String password) {
        if (password.length() < 20 || 8 > password.length()) {
            boolean proverka = false;
            boolean resSpec1 = false;
            boolean resProp2 = false;
            boolean resNumber3 = false;
            String str = password;


            char[] arrChar = new char[]{'Q', 'W', 'E', 'R', 'T', 'Y', 'U', 'I', 'O', 'P', 'A',
                    'S', 'D', 'F', 'G', 'H', 'J', 'K', 'L', 'Z', 'X', 'C', 'V', 'B', 'N', 'M'};
            char[] arrNumber = new char[]{'1', '2', '3', '4', '5', '6', '7', '8', '9'};
            char[] arrSpecSimb = new char[]{'!', '@', '#', '$', '%', '^', '&', '*'};
            char[] result = str.toCharArray();

            for (Character o : result) {
                boolean resSpec = false;
                boolean resProp = false;

                for (Character m : arrSpecSimb) {
                    if (m == o) {
                        resSpec = true;
                        resSpec1 = true;
                        break;
                    }
                }
                if (!resSpec) {
                    for (Character m : arrChar) {
                        if (m == o) {
                            resProp = true;
                            resProp2 = true;
                            break;
                        }
                    }
                }
                if (!resSpec || !resProp) {
                    for (Character m : arrNumber) {
                        if (m == o) {

                            resNumber3 = true;
                            break;
                        }

                    }
                }
                if (resNumber3 && resProp2 && resSpec1) {
                    proverka = true;

                    break;

                }


            }


            if (proverka)
                System.out.println("Пароль прошел проверку");
            else System.out.println("Пароль не прошел проверку");


        }
    }


    public static void main(String[] args) {
    validPassword("Sfg@1");
}
}



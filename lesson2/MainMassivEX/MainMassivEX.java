package MainMassivEX;

class MyArrayDataException extends Exception {


    public MyArrayDataException(String msg, String elmas, int i, int j) {

        super(msg + "Значение = " + elmas + " [" + i + "," + j + "]");

    }
}

class MyArraySizeException extends Exception {





    public MyArraySizeException(String msg) {
        super(msg);

    }
}

class Massiv {

    public static String[][] getMassiv(String[][] matric) throws MyArraySizeException {

        int lenj = 0;

        for (int i = 0; i < matric.length; i++) {


            if (matric[i].length == 4) {
                lenj++;
            }


        }

        if (!(matric.length == 4 && lenj == 4)) {
            throw new MyArraySizeException("Массив не соответсвует указанным параметрам 4х4");
        }
        String[][] res = matric;

        return res;
    }


    public static int getSum(String[][] matric) throws MyArrayDataException {

        int res = 0;
        int el = 0;

        for (int i = 0; i <= 3; i++) {
            for (int j = 0; j <= 3; j++) {


                try {
                    el = Integer.parseInt(matric[i][j]);

                } catch (NumberFormatException e) {
                    // System.err.println("Элемент не возможно преобразовать. " + matric[i][j] + " [ " +   i + "," + j+ "]");
                    throw new MyArrayDataException("Элемент не возможно преобразовать. ", matric[i][j], i, j);
                    //el=0;

                }
                res += el;

            }

        }


        return res;
    }

}


class MyArraySizeEx {
    public static String[][] res;

    public static void main(String[] args) {

        String[][] massiv = {{"5", "5", "0", "n"}, {"0", "0", "0", "b"}, {"1", "1", "1", "1"}, {"1", "1", "1", "1"}};

        printmatric(massiv);


        try {

            res = Massiv.getMassiv(massiv);


        } catch (MyArraySizeException e) {
            e.printStackTrace();
        }


        try {
            System.out.println("Сумма без учета неправильных элементов: " + Massiv.getSum(massiv));
        } catch (MyArrayDataException e) {
            e.printStackTrace();
        }

    }

    public static void printmatric(String[][] matric) {
        for (int i = 0; i < matric.length; i++) {
            for (int j = 0; j < matric[i].length; j++) {
                System.out.print(matric[i][j] + " ");

            }
            System.out.println();
        }

    }

}
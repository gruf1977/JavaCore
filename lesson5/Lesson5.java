public class Lesson5 {
    public static void main(String[] args) {

        final int size = 10000000;
        final int h = 8; // здесь ставим количество потоков

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                method1(size);

            }
        });
        t1.start();

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {

                method2(size, h);
            }
        });
        t2.start();
    }

    public static void method1(int size){

        float[] arr;
        arr = new float[size];

        for (int i = 0;  i<arr.length; i++) {
            arr[i] = 1f;

        }

        long a = System.currentTimeMillis();

        for (int i = 0; i<arr.length; i++) {
            arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }

        System.out.println("END 1 " + "time : " + (System.currentTimeMillis()-a));
    }

    public static void method2(int size, int h) {

        float[] arr = new float[size];

        for (int i = 0;  i<arr.length; i++) {
            arr[i] = 1f;

        }

        long a = System.currentTimeMillis();
        int sizeh = size / h;

        Thread[] mass = new Thread[h];
        for (int j = 0; j < h; j++) {


           int finalJ = j;

            mass[j] = new Thread(new Runnable() {
                @Override
                public void run() {
                   // calck(sizeh, arr, finalJ);


                    float[] ar = new float[sizeh];
                    System.arraycopy(arr, sizeh * finalJ, ar, 0, sizeh);
                    //System.out.println("Длина массива ар " + ar.length);
                    for (int i = 0; i < ar.length; i++) {
                        ar[i] = (float) (ar[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));

                    }
                    System.arraycopy(ar, 0, arr, sizeh * finalJ, sizeh);

                }
            });

            mass[j].start();

            try {
                mass[j].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            /* Раскоментировать для проверки что массив посчитался

            for (int i = 0;  i<arr.length; i++) {
                for (int f = 0; f < 20; f++) {
                    System.out.print(arr[i] + " ");

                }
                System.out.println();

            }*/
        }

        System.out.println("END 2 " + "time : " + (System.currentTimeMillis() - a) + " Количество потоков = " + h);

    }
/*
    public static void calck(int sizeh, float[] arr, int j){

        float[] ar = new float[sizeh];
        System.arraycopy(arr, sizeh * j, ar, 0, sizeh);
        System.out.println("Длина массива ар " + ar.length);
        for (int i = 0; i < ar.length; i++) {
            ar[i] = (float) (ar[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        System.arraycopy(ar, 0, arr, sizeh * j, sizeh);


    }*/

}



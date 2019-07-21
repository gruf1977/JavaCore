package lesson3;

import java.util.*;

public class ArrayWords {

    public static void main(String[] args) {
        List<String> arrayList = new ArrayList<>();
        arrayList.add("Абрикос");
        arrayList.add("Ананас");
        arrayList.add("Арбуз");
        arrayList.add("Дыня");
        arrayList.add("Персик");
        arrayList.add("Ананас");
        arrayList.add("Яблоко");
        arrayList.add("Виноград");
        arrayList.add("Слива");
        arrayList.add("Ананас");
        arrayList.add("Груша");
        arrayList.add("Слива");
        arrayList.add("Морковь");
        arrayList.add("Огурец");
        arrayList.add("Яблоко");
        arrayList.add("Банан");
        arrayList.add("Алыча");
        arrayList.add("Черника");
        arrayList.add("Клубника");
        arrayList.add("Груша");
        System.out.println("Первоночальный список : ");
       System.out.println(arrayList);

        System.out.println("Список уникальных слов : ");
        Set<String> unicfruit = new HashSet<>();

       for (String o: arrayList) {

            unicfruit.add(o);
        }
        System.out.println(unicfruit);

        System.out.println("Сколько раз встречается каждое слово : ");

        for (String o : unicfruit) {
            int kolvo = 0;
            for (String m : arrayList) {
                if (m.equals(o)) {
                    kolvo++;
                }
            }
            System.out.print(o +  " : " + kolvo +", ");

        }

    }

}

package lesson3.hm;


import java.util.HashMap;

import java.util.Map;


public class spravochtelefon {

    static HashMap<Integer, String> spravochnik = new HashMap<>(); //

    public spravochtelefon(String name, int number) {
        spravochnik.put(number, name);

    }

    public static void add(String name, int Number) {
        spravochnik.put(Number, name);

    }

    public static void get(String name) {
        System.out.println("С фамилией " + name + " найдены следующие номера телефонов :");
        System.out.println();
        System.out.println("---------------------------");
        for (Map.Entry<Integer, String> o : spravochnik.entrySet()) {
            if (o.getValue() == name) {
                System.out.println(o.getKey() + ": " + o.getValue());
            }
        }

        System.out.println("---------------------------");

    }


    public static void main(String[] args) {


        add("Петров", 895011292);
        add("Сидоров", 3697899);
        add("Петров", 45852365);
        add("Петров", 77988465);
        add("Иванов", 77965465);
        add("Козлов", 779454865);
        add("Егоров", 77946865);


        get("Петров");


    }
}




package lesson3.list;

import java.util.*;

public class telefon {
    String name;
    int number;
    static List<telefon> users = new ArrayList<>();

    public telefon(String name, int number) {
        this.name = name;
        this.number = number;

    }

    public static void add(String name, int Number){

        users.add(new telefon(name, Number));
    }

    public static void get(String name){
        System.out.println("С фамилией " + name + " найдены следующие номера телефонов :");
        System.out.println();



        for (telefon o: users) {
            if (o.name == name) {
                System.out.println(o.name + " : " + o.number);
            }
        }

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

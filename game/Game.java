import java.util.ArrayList;
import java.util.Random;

abstract class Hero {

    protected int health;
    protected String name;
    protected int damage;
    protected int addHeal;
    protected int maxhealth;

    public Hero(int health, String name, int damage, int addHeal) {
        this.health = health;
        this.name = name;
        this.damage = damage;
        this.addHeal = addHeal;
        this.maxhealth = health;
    }

    abstract void hit(Hero hero);

    abstract void healing(Hero hero);

    void causeDamage(int damage) {
        if(health < 0) {
            System.out.println("Герой уже мертвый!");
        } else {
            health -= damage;
        }

    }

    public int getHealth() {
        return health;
    }

    void addHealth(int health) {
        this.health += health;
    }

    void info() {

        System.out.println(name + " " + (health < 0 ? "Герой мертвый" : health) + " " + damage);
    }
}

class Warrior extends Hero {

    public Warrior(int health, String type, int damage, int addHeal) {
        super(health, type, damage, addHeal);
    }

    @Override
    void hit(Hero hero) {
        if (hero != this) {
            if(health < 0) {
                System.out.println("Герой погиб и бить не может!");
            } else {
                hero.causeDamage(damage);
            }
            System.out.println(this.name + " нанес урон " + hero.name);
        }
    }

    @Override
    void healing(Hero hero) {
        System.out.println("Войны не умеют лечить!");
    }
}

class Assasin extends Hero {

    int cricitalHit;
    Random random = new Random();

    public Assasin(int health, String name, int damage, int addHeal) {
        super(health, name, damage, addHeal);
        this.cricitalHit = random.nextInt(20);
    }

    @Override
    void hit(Hero hero) {
        if (hero != this) {
            if(health < 0) {
                System.out.println("Герой погиб и бить не может!");
            } else {
                hero.causeDamage(damage + cricitalHit);
            }
            System.out.println(this.name + " нанес урон " + hero.name);
        }
    }

    @Override
    void healing(Hero hero) {
        System.out.println("Убийцы не умеют лечить!");
    }
}

class Doctor extends Hero {

    public Doctor(int health, String name, int damage, int addHeal) {
        super(health, name, damage, addHeal);
    }

    @Override
    void hit(Hero hero) {
        System.out.println("Доктор не может бить!");
    }

    @Override
    void healing(Hero hero) {
        System.out.println("------");
       // System.out.println(hero.name + " Здоровье было: " + hero.health);

        if (hero.health>=0 && (hero.health+addHeal)< hero.maxhealth){ //лечим только тех кто еще не умер т.е. здороье больше нуля
          hero.addHealth(addHeal);
        } else if ((hero.health+addHeal)>=hero.maxhealth){ //лечим только до максимального здоровья (не лечим здоровых)

                hero.health = hero.maxhealth;



        }

        //System.out.println(hero.name + " Здоровье стало: " + hero.health);
    }
}


class Game {
    public static void main(String[] args) {

        Random randomStep = new Random();

        int round = 10;
        ArrayList<Hero> team1 = new ArrayList<>();
        team1.add(new Warrior(250, "Тигрил", 50, 0));
        team1.add(new Assasin(150, "Акали", 70, 0));
        team1.add(new Doctor(120, "Жанна", 0, 60));

        ArrayList<Hero> team2 = new ArrayList<>();

        team2.add(new Warrior(290, "Минотавр", 60, 0));
        team2.add(new Assasin(160, "Джинкс", 90, 0));
        team2.add(new Doctor(110, "Зои", 0, 80));

        boolean tm;
        while (team1.size() > 0 && team2.size() > 0) {
          //  System.out.println("Количество участников team1 = " + team1.size() + " Количество участников team2 = " + team2.size());

            int i,s;
            tm = randomStep.nextBoolean();
            if (tm) {


                i = randomStep.nextInt(team1.size());


                if (team1.get(i) instanceof Doctor) {
                    s = randomStep.nextInt(team1.size());
                    System.out.println("S = " + s);

                    team1.get(i).healing(team1.get(s));
                    System.out.println(team1.get(i).name + " Полечил: " + team1.get(s).name);


                } else {
                    if (team2.size() > 0) {
                        int inprot = randomStep.nextInt(team2.size()); // индекс противника
                        team1.get(i).hit(team2.get(inprot));
                        if (team2.get(inprot).health < 0) {
                            System.out.println(team2.get(inprot).name + " Мертв Здоровье : " + team2.get(inprot).health);
                            team2.remove(inprot);
                        }
                    } else {
                        break;
                    }
                }


            } else {

                i = randomStep.nextInt(team2.size());

                if (team2.get(i) instanceof Doctor) {
                    s = randomStep.nextInt(team2.size());

                    team2.get(i).healing(team2.get(s));
                    System.out.println(team2.get(i).name + " Полечил: " + team2.get(s).name);

                } else {
                    if (team1.size() > 0) {
                        int inprot = randomStep.nextInt(team1.size()); // индекс противника
                        team2.get(i).hit(team1.get(inprot));
                        if (team1.get(inprot).health < 0) {
                            System.out.println(team1.get(inprot).name + " Мертв Здоровье : " + team1.get(inprot).health);
                            team1.remove(inprot);
                        }
                    } else {

                        break;
                    }
                }
                //}
            }

        }

        System.out.println("--------__________-------");
        System.out.println("Победила команда =" + ((team1.size()>0)?"Team1":"Team2"));
        System.out.println("--------победители-------");
        for (Hero t:(team1.size()>0)?team1:team2) {
            t.info();
        }

    }



}

public class DayOfWeekMain {
     public static int dayHour=8; //количесвто часов в рабочем дне

public enum DayOfWeek{
    MONDAY("Понедельник", 5*dayHour),
    TUESDAY("Вторник", 4*dayHour),
    WEDNESDAY("Среда",3*dayHour),
    THURSDAY("Четверг", 2*dayHour),
    FRIDAY("Пятница", 1*dayHour),
    SATURDAY("Суббота", 0),
    SUNDAY("Воскресенье", 0);
    public String getDayOfWeek(){
        return this.russianTitle;
    }
    private String russianTitle;
    private int lefHour;


    DayOfWeek(String russianTitle, int lefHour) {
        this.russianTitle = russianTitle;
        this.lefHour = lefHour;
    }





    public int getLefHour() {
        return lefHour;
    }


public static String getWorkingHours(String day){
    String  result = "Нет такого дня недели";

    switch(day){
        case "MONDAY":
            result = DayOfWeek.MONDAY.russianTitle + " осталось " + DayOfWeek.MONDAY.lefHour + " часов ";
            break;
        case "TUESDAY":
            result = DayOfWeek.TUESDAY.russianTitle + " осталось " + DayOfWeek.TUESDAY.lefHour + " часов ";
            break;
        case "WEDNESDAY":
            result = DayOfWeek.WEDNESDAY.russianTitle + " осталось " + DayOfWeek.WEDNESDAY.lefHour + " часов ";
            break;
        case "THURSDAY":
            result = DayOfWeek.THURSDAY.russianTitle + " осталось " + DayOfWeek.THURSDAY.lefHour + " часов ";

            break;
        case "FRIDAY":
            result = DayOfWeek.FRIDAY.russianTitle + " осталось " + DayOfWeek.FRIDAY.lefHour + " часов ";
            break;
        case "SATURDAY":
            result = DayOfWeek.SATURDAY.russianTitle + " Выходной день " + DayOfWeek.SATURDAY.lefHour + " часов ";
            break;
        case "SUNDAY":
            result = DayOfWeek.SUNDAY.russianTitle + " Выходной день " + DayOfWeek.SUNDAY.lefHour;
            break;

    }
    return result;
}
}


    public static void main(final String[] args) {

        System.out.println(DayOfWeek.getWorkingHours("SATURDAY"));
    }
}

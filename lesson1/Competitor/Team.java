package Lesson_1.Marathon.Competitor;


public class Team {

    public String name;
    public Competitor[] ushastniki;

    public Team(String _name, Competitor... _animals) {

        this.name = _name;

        this.ushastniki = _animals;
            //System.out.println(_animals[i]);



    }
public void showResults(){
for (Competitor c : ushastniki) {
    c.info();
}
    }


}






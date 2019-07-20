package Lesson_1.Marathon;
/* первый урок*/
import Lesson_1.Marathon.Competitor.Cat;
import Lesson_1.Marathon.Competitor.Dog;
import Lesson_1.Marathon.Competitor.Human;
import Lesson_1.Marathon.Competitor.Team;
import Lesson_1.Marathon.Obstacle.Course;
import Lesson_1.Marathon.Obstacle.Cross;
import Lesson_1.Marathon.Obstacle.Wall;

public class Main {



    public static void main(String[] args) {
        Course c = new Course(new Cross(80), new Wall(6), new Wall(1), new Cross(3000));
        Team team = new Team("Динамо", new Human("Боб"), new Cat("Барсик"), new Dog("Бобик"), new Cat("Пушок"));


        Marafon.Start(team, c);

        team.showResults();




    }
}
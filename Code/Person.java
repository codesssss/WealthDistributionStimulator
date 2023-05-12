import java.util.ArrayList;

/**
 * @Author Fangzhou Wang
 * @Date 2023/5/11 18:18
 **/
public class Person {
    public int wealth;
    public int metabolism;
    public int lifeExpectancy;
    public int vision;
    public int age;
    public int column;
    public int row;
    public int direction;

    public Person(){
        randomGeneratePersonInformation();
        age = 0;
        column = (int)(Math.random()*Params.COLUMN_MAX);
        row = (int)(Math.random()*Params.ROW_MAX);
        direction = 0;
    }

    public void randomGeneratePersonInformation() {
        age = 0;
        lifeExpectancy = Params.randomInt(Params.LIFE_EXPECTANCY_MAX, Params.LIFE_EXPECTANCY_MIN);
        metabolism = Params.randomInt(1, Params.METABOLISM_MAX);
        wealth = Params.randomInt(metabolism, 50);
        vision = Params.randomInt(1, Params.VISION_MAX);
    }

    public void updatePersonInfo(){
        this.wealth -= metabolism;
        age++;
        if(age > lifeExpectancy || wealth < 0){
            randomGeneratePersonInformation();
        }

    }

    public void move(int direction){


    }

    public void collectGrain(int num){
        this.wealth += num;
    }
    public void reproduce(){

    }
}
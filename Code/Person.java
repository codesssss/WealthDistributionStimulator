import java.util.ArrayList;

/**
 * @Author Fangzhou Wang
 * @Date 2023/5/11 18:18
 **/
public class Person extends Thread {
    public int wealth;
    public int metabolism;
    public int lifeExpectancy;
    public int vision;
    public int age;
    public int column;
    public int row;

    public Person(){
        initPersonInformation();
        age = 0;
        column = (int)(Math.random()*Params.COLUMN_MAX);
        row = (int)(Math.random()*Params.ROW_MAX);
    }
    public void initPersonInformation() {
        age = 0;

        lifeExpectancy = randomInt(Params.LIFE_EXPECTANCY_MAX, Params.LIFE_EXPECTANCY_MIN);
        metabolism = randomInt(1, Params.METABOLISM_MAX);
        wealth = randomInt(metabolism, 50);
        vision = randomInt(1, Params.VISION_MAX);
    }
    public int randomInt( int min, int max) {
        // Generates a random int between min and max including both
        int range = max-min;
        return (int)(Math.random() * (range+1) + min);
    }

    @Override
    public void run() {
        move();
        collectGrain();
        reproduce();
    }

    public void move(){
        age++;
    }
    public int getVision(){
        return vision;
    }

    public void collectGrain(){


    }
    public void reproduce(){
        wealth = wealth-metabolism;
    }
}
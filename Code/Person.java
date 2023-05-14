import java.util.ArrayList;

/**
 * @Author Fangzhou Wang
 * @Date 2023/5/11 18:18
 **/
public class Person {
    private int wealth;
    private int metabolism;
    private int lifeExpectancy;
    private int vision;
    private int age;
    private int column;
    private int row;
    private int direction;

    private wealthClass myWealthClass;
    public enum wealthClass {// 0 for poor; 1 for middle class; 2 for rich
        POOR,
        MIDDLE,
        RICH
    }

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

    public void updateNewPersonInfo(){
        this.wealth -= metabolism;
        age++;
        if(age > lifeExpectancy || wealth < 0){
            randomGeneratePersonInformation();
        }

    }

    public void collectWealth(int newWealth){
        this.wealth += newWealth;
    }

    public void updateClass(int mostWealth){
        if(this.wealth < (int)( mostWealth / 3 ) ){

            this.myWealthClass = wealthClass.POOR;
        }
        else if ( this.wealth >= (int)(mostWealth / 3 ) && this.wealth < (int)(mostWealth / 3 * 2 )){
            this.myWealthClass = wealthClass.MIDDLE;
        }
        else {
            this.myWealthClass = wealthClass.RICH;
        }

    }

    public void setColumn(int newColumn){
        this.column = newColumn;
    }

    public void setRow(int newRow){
        this.row = newRow;
    }

    public int getColumn(){
        return column;
    }

    public int getRow(){
        return row;
    }

    public void collectGrain(int num){
        this.wealth += num;
    }
    public void reproduce(){

    }

    public int getWealth() {
        return wealth;
    }

    public void setWealth(int wealth) {
        this.wealth = wealth;
    }

    public int getMetabolism() {
        return metabolism;
    }

    public void setMetabolism(int metabolism) {
        this.metabolism = metabolism;
    }

    public int getLifeExpectancy() {
        return lifeExpectancy;
    }

    public void setLifeExpectancy(int lifeExpectancy) {
        this.lifeExpectancy = lifeExpectancy;
    }

    public int getVision() {
        return vision;
    }

    public void setVision(int vision) {
        this.vision = vision;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }
}
import java.util.ArrayList;

/**
 * @Author Fangzhou Wang Haoyu Liu Xuhang Shi
 * @Date 2023/5/11 18:18
 **/

/**
 * The definition of Person and random create a person information
 */
public class Person {
    private double wealth;
    private int metabolism;
    private int lifeExpectancy;
    private int vision;
    private int age;
    private int column;
    private int row;

    private wealthClass myWealthClass;
    public enum wealthClass {// 0 for poor; 1 for middle class; 2 for rich
        POOR,
        MIDDLE,
        RICH
    }

    public Person(){
        initializePerson();
        age = 0;
        column = Params.randomInt(0, Params.COLUMN_MAX);
        row = Params.randomInt(0, Params.ROW_MAX);
    }

    /**
     * Init Person information
     */
    private void initializePerson() {
        age = 0;
        lifeExpectancy = Params.randomInt(Params.LIFE_EXPECTANCY_MIN, Params.LIFE_EXPECTANCY_MAX);
        metabolism = Params.randomInt(1, Params.METABOLISM_MAX);
        wealth = Params.randomInt(metabolism, Params.PERSON_INIT_WEALTH_MAX);
        vision = Params.randomInt(1, Params.VISION_MAX);
    }

    /**
     * Updates the person's information for each round of the simulation.
     *
     * @param mostWealth the highest wealth among all people in the simulation
     */
    public void updatePersonInfo(double mostWealth){
        this.wealth -= metabolism;
        age++;
        if(wealth < 0){
            initializePerson();
        }else if (wealth > 0 && age > lifeExpectancy){
            reproduce();
        }
        updateClass(mostWealth);
    }

    /**
     * Reproduces a new person by inheriting wealth from the parent.
     */
    public void reproduce(){
        double fatherWealth = this.wealth * Params.INHERITANCE_PROPORTIONS;
        initializePerson();
        wealth += fatherWealth;

    }

    /**
     * Collects wealth from a patch and adds it to the person's wealth.
     *
     * @param wealthToAdd the wealth to add
     */
    public void collectWealth(double wealthToAdd){
        this.wealth += wealthToAdd;
    }

    /**
     * Updates the wealth class of the person based on the highest wealth in the simulation.
     *
     * @param mostWealth the highest wealth among all people in the simulation
     */

    public void updateClass(double mostWealth){
        if(this.wealth <= ( mostWealth / 3 ) ){

            this.myWealthClass = wealthClass.POOR;
        }
        else if ( this.wealth > (mostWealth / 3 ) && this.wealth <= (mostWealth / 3 * 2 )){
            this.myWealthClass = wealthClass.MIDDLE;
        }
        else {
            this.myWealthClass = wealthClass.RICH;
        }

    }

    // Getters and setters for all private fields...

    public void setPosition(int newRow, int newColumn){
        this.row = newRow;
        this.column = newColumn;
    }

    public int getColumn(){
        return column;
    }

    public void setColumn(int column){
        this.column = column;
    }

    public int getRow(){
        return row;
    }

    public void setRow(int row){
        this.row = row;
    }

    public double getWealth() {
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

    public wealthClass  getWealthClass(){
        return myWealthClass;
    }


}

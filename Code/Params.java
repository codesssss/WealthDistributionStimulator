/**
 * @Author Fangzhou Wang
 * @Date 2023/5/11 18:18
 **/
public class Params {

    /**
     * Person Set
     */

    // Maximum life expectancy
    public static final int LIFE_EXPECTANCY_MAX = 83;

    // Minimum life expectancy
    public static final int LIFE_EXPECTANCY_MIN = 1;

    // Maximum vision
    public static final int VISION_MAX = 5;

    // Maximum metabolism
    public static final int METABOLISM_MAX = 15;

    // Maximum initial wealth for a person
    public static final int PERSON_INIT_WEALTH_MAX = 25;

    /**
     * Patches Set
     */

    // Maximum grain capacity
    public static final double GRAIN_CAPACITY_MAX = 100;

    // Maximum number of columns in the grid
    public static final int COLUMN_MAX = 50;

    // Maximum number of rows in the grid
    public static final int ROW_MAX = 50;

    // Percentage of best patches in the grid
    // Between 0 and 1
    public static final double PERCENTAGE_OF_BEST_PATCHES = 0.1;

    // Percentage of diffusion from one patch to its neighbors
    // Between 0 and 1
    public static final double PERCENTAGE_OF_DIFFUSION = 0.25;

    // Number of grain grown per round
    public static final int NUM_GRAIN_GROWN = 4;

    // Boolean indicating whether to use a diffusive initialization model
    //If it is false. It would use random_model to init Patch
    public static final boolean DIFFUSE_INIT_MODEL = true;

    /**
     * Simulation Set
     */

    // Number of rounds in the simulation
    public static final int ROUND_NUM = 1000;

    //Grow grains every GRAIN_GROWTH_INTERVAL
    // Minimum is 1
    public static final int GRAIN_GROWTH_INTERVAL = 1;

    // Population size
    public static final int POPULATION = 250;

    // Proportions of inheritance between parents and offspring
    // Between 0 and 1
    public static final double INHERITANCE_PROPORTIONS = 0.5;


    /**
     * Generates a random integer between the specified min and max (inclusive).
     *
     * @param min the minimum value
     * @param max the maximum value
     * @return a random integer within the specified range
     */
    public static int randomInt(int min, int max) {
        // Generates a random int between min and max, including both
        int range = max - min;
        return (int) (Math.random() * (range + 1) + min);
    }
}

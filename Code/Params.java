/**
 * @Author Fangzhou Wang
 * @Date 2023/5/11 18:18
 **/
public class Params {
    public static final int LIFE_EXPECTANCY_MAX = 83;

    public static final int LIFE_EXPECTANCY_MIN = 1;

    public static final int VISION_MAX = 5;

    public static final int  METABOLISM_MAX = 15;

    public static final double GRAIN_CAPACITY_MAX = 100;

    public static final int POPULATION = 250;

    public static final int COLUMN_MAX = 50;

    public static final int ROW_MAX = 50;

    public static final int PERSON_INIT_WEALTH_MAX = 25;

    public static final double PERCENTAGE_OF_BEST_PATCHES = 0.1;

    public static final int NUM_GRAIN_GROWN = 4;

    public static final double PERCENTAGE_OF_DIFFUSION = 0.25;

    public static final int ROUND_NUM = 1000;

    public static final double INHERITANCE_PROPORTIONS = 0.5;

    public static final boolean DIFFUSE_INIT_MODEL = true;




    public static int randomInt( int min, int max) {
        // Generates a random int between min and max including both
        int range = max-min;
        return (int)(Math.random() * (range+1) + min);
    }






}

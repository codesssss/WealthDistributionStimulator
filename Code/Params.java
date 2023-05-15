/**
 * @Author Fangzhou Wang
 * @Date 2023/5/11 18:18
 **/
public class Params {
    public static final int LIFE_EXPECTANCY_MAX = 100;

    public static final int LIFE_EXPECTANCY_MIN = 1;

    public static final int VISION_MAX = 5;

    public static final int  METABOLISM_MAX = 10;

    public static final double NEW_GRAIN_MAX = 20;

    public static final double GRAIN_CAPACITY_MAX = 100;

    public static final int POPULATION = 10;

    public static final int COLUMN_MAX = 20;

    public static final int ROW_MAX = 20;

    public static final int UP = 0;

    public static final int LEFT = 90;

    public static final int DOWN = 180;

    public static final int RIGHT = 270;

    public static final double PERCENTAGE_OF_BEST_PATCHES = 0.1;

    public static final int NUM_GRAIN_GROWN = 4;

    public static final double PERCENTAGE_OF_DIFFUSION = 0.25;

    public static final int ROUND_NUM = 1000;




    public static int randomInt( int min, int max) {
        // Generates a random int between min and max including both
        int range = max-min;
        return (int)(Math.random() * (range+1) + min);
    }






}

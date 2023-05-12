/**
 * @Author Fangzhou Wang
 * @Date 2023/5/11 18:18
 **/
public class Patch{
    Simulation simulation;
    int grainCapacity;

    public Patch() {
        grainCapacity = 0;
    }

    public void growGrain(){
        grainCapacity = (int) ( Math.random() * Params.NEW_GRAIN_MAX);
    }


}
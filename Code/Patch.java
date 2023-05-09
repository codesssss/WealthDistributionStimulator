/**
 * @Author Fangzhou Wang
 * @Date 2023/5/11 18:18
 **/
public class Patch extends Thread{
    int grainCapacity;

    public Patch() {
        grainCapacity = 0;
    }
    public void growGrain(){
        grainCapacity = (int) ( Math.random() * Params.NEW_GRAIN_MAX);
    }

    @Override
    public void run() {
        super.run();
        growGrain();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
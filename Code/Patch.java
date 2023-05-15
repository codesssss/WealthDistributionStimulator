/**
 * @Author Fangzhou Wang
 * @Date 2023/5/15 18:07
 **/
public class Patch {
    private int maxGrainHere;
    private int grainHere;

    public Patch(){
        this.maxGrainHere = 0;
        this.grainHere = 0;
    }


    public int getMaxGrainHere() {
        return maxGrainHere;
    }

    public void setMaxGrainHere(int maxGrainHere) {
        this.maxGrainHere = maxGrainHere;
    }

    public int getGrainHere() {
        return grainHere;
    }

    public void setGrainHere(int grainHere) {
        this.grainHere = grainHere;
    }
}

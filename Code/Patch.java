/**
 * @Author Fangzhou Wang
 * @Date 2023/5/15 18:07
 **/
public class Patch {
    private double maxGrainHere;
    private double grainHere;

    public Patch(){
        this.maxGrainHere = 0;
        this.grainHere = 0;
    }


    public double getMaxGrainHere() {
        return maxGrainHere;
    }

    public void setMaxGrainHere(double maxGrainHere) {
        this.maxGrainHere = maxGrainHere;
    }

    public double getGrainHere() {
        return grainHere;
    }

    public void setGrainHere(double grainHere) {
        this.grainHere = grainHere;
    }
}

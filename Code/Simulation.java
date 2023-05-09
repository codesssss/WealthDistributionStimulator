import java.util.ArrayList;

/**
 * @Author Fangzhou Wang
 * @Date 2023/5/11 18:18
 **/
public class Simulation {
    ArrayList<ArrayList<Integer>> Land;
    int todayCollectNum = 0;

    public Simulation(){
        Land = new ArrayList<>();
        initializeGrid();

    }

    public void initializeGrid(){

    }
//    public void initializePeople(){
//
//    }
    public void update(){

    }
    public void increaseTodayCollect(){
        todayCollectNum++;
    }

    public int getDirection(){
        return 90;
    }

    public int consumeGrain(int row, int column){

        int grainNum = Land.get(row).get(column);
        Land.get(row).set(column,0);
        return grainNum;

    }

    public void calculateLorenzCurve(){

    }
    public void calculateGiniIndex(){

    }

}
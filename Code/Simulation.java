import java.util.ArrayList;

/**
 * @Author Fangzhou Wang
 * @Date 2023/5/11 18:18
 **/
public class Simulation {
    ArrayList<ArrayList<Integer>> land;
    int todayCollectNum = 0;
    ArrayList<Person> people;

    public Simulation() {
        land = new ArrayList<>();
        initializeGrid();
    }

    public void initializeGrid() {
        ArrayList<Integer> temp = new ArrayList<>();
        for (int i = 0; i < Params.ROW_MAX; i++) {
            for (int j = 0; j < Params.COLUMN_MAX; j++) {
                temp.add((int) (Math.random() * Params.GRAIN_CAPACITY_MAX));
            }
            land.add(temp);
            temp.clear();
        }
    }

    //    public void initializePeople(){
//
//    }
    public void update() {

    }

    synchronized public void increaseTodayCollect() {
        todayCollectNum++;
    }

    public int getDirection(int row, int column, int vision) {
        return 90;
    }

    public int consumeGrain(int row, int column) {

        int grainNum = land.get(row).get(column);
        land.get(row).set(column, 0);
        return grainNum;

    }

    public void calculateLorenzCurve() {

    }

    public void calculateGiniIndex() {

    }

}
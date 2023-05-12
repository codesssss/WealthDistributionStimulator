import java.util.ArrayList;

/**
 * @Author Fangzhou Wang
 * @Date 2023/5/11 18:18
 **/
public class Patch{
    ArrayList<Person> people;
    ArrayList<ArrayList<Integer>> land;
    int grainCapacity;
    public Patch() {

        this.land = new ArrayList<>();
        initializeGrid();
        this.people = new ArrayList<>();
        initializePeople();

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
    public void initializePeople(){
        for(int i=0; i < Params.POPULATION;i++){
            Person temp = new Person();
            people.add(temp);
        }
    }

    public int consumeGrain(int row, int column) {

        int grainNum = land.get(row).get(column);
        land.get(row).set(column, 0);
        return grainNum;

    }



    public void growGrain(){
        grainCapacity = (int) ( Math.random() * Params.NEW_GRAIN_MAX);
    }


}
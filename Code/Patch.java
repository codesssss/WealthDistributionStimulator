import java.util.ArrayList;

/**
 * @Author Fangzhou Wang
 * @Date 2023/5/11 18:18
 **/
public class Patch{
    ArrayList<Person> people;
    ArrayList<ArrayList<Integer>> land;



    public Patch() {

        this.land = new ArrayList<>();
        initializeGrid();
        this.people = new ArrayList<>();
        initializePeople();

    }

    public void  move(Person person){
        int totalUp = 0;
        int totalRight = 0;
        int totalDown = 0;
        int totalLeft = 0;
        int row = person.getRow();
        int column = person.getColumn();
        int vision = person.vision;
        for(int i = 0; i < vision+1 ; i++ ){
            if(column + i < Params.COLUMN_MAX && column < Params.COLUMN_MAX){
                totalRight += land.get(row).get(column + i);
            }
            if(column - i > 0  && row < Params.ROW_MAX){
                totalLeft += land.get(row ).get(column - i);
            }
            if(row - i > 0 && column < Params.COLUMN_MAX){
                totalUp += land.get(row - i).get(column);
            }
            if(row + i < Params.ROW_MAX && column < Params.COLUMN_MAX) {
                totalDown += land.get(row + i).get(column);
            }
        }
        if (totalRight >= totalLeft && totalRight >= totalUp && totalRight >= totalDown) {

            person.collectWealth(land.get(row).get(column + 1));
            land.get(row).set(column + 1,0);
            person.setColumn(column + 1);

        }
        if (totalLeft >= totalUp && totalLeft >= totalDown) {
            person.collectWealth(land.get(row).get(column - 1));
            land.get(row).set(column - 1,0);
            person.setColumn(column - 1);
        }
        if (totalUp >= totalDown) {
            person.collectWealth(land.get(row - 1).get(column));
            land.get(row - 1).set(column,0);
            person.setRow(row - 1);
        }
        else {
            person.collectWealth(land.get(row + 1).get(column));
            land.get(row + 1).set(column,0);
            person.setRow(row + 1);
        }

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
        int grainCapacity = 0;
        int newGrainCapacity = 0 ;
        for(int i = 0 ;i < Params.ROW_MAX; i++){
            for(int j = 0; j < Params.COLUMN_MAX; j++){
                grainCapacity = this.land.get(i).get(j);
                if (grainCapacity<Params.GRAIN_CAPACITY_MAX){
                    newGrainCapacity = (int) ( Math.random() * (Params.NEW_GRAIN_MAX - grainCapacity));
                    this.land.get(i).set(j,newGrainCapacity);
                }
            }

        }
    }


}
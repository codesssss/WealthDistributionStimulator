import java.util.ArrayList;

/**
 * @Author Fangzhou Wang
 * @Date 2023/5/11 18:18
 **/
public class Patch{
    private ArrayList<Person> people;
    private ArrayList<ArrayList<Integer>> land;

    public Patch() {
        this.land = new ArrayList<>();
        initializeGrid();
        this.people = new ArrayList<>();
        initializePeople();
    }

    private boolean isWithinBounds(int index, int max) {
        return index >= 0 && index < max;
    }

    public synchronized void move(Person person){
        int[] totalDirection = new int[4];
        int[] dx = {0, 0, -1, 1};
        int[] dy = {1, -1, 0, 0};
        int row = person.getRow();
        int column = person.getColumn();
        int vision = person.getVision();

        for(int i = 0; i <= vision; i++){
            for(int direction = 0; direction < 4; direction++){
                int newRow = row + dx[direction] * i;
                int newColumn = column + dy[direction] * i;
                if(isWithinBounds(newRow, Params.ROW_MAX) && isWithinBounds(newColumn, Params.COLUMN_MAX)){
                    totalDirection[direction] += land.get(newRow).get(newColumn);
                }
            }
        }

        int maxDirection = 0;
        for(int i = 1; i < 4; i++){
            if(totalDirection[i] > totalDirection[maxDirection]){
                maxDirection = i;
            }
        }

        int newRow = row + dx[maxDirection];
        int newColumn = column + dy[maxDirection];
        if(isWithinBounds(newRow, Params.ROW_MAX) && isWithinBounds(newColumn, Params.COLUMN_MAX)){
            person.collectWealth(consumeGrain(newRow, newColumn));
            person.setPosition(newRow,newColumn);
        }
    }

    public int findTheBiggestWealth(){
        int maxWealth = 0;
        for(int i =0 ; i < Params.POPULATION ; i++){
          maxWealth = Math.max(maxWealth, people.get(i).getWealth());
        }
        return  maxWealth;
    }

    public void initializeGrid() {
        for (int i = 0; i < Params.ROW_MAX; i++) {
            ArrayList<Integer> temp = new ArrayList<>();
            for (int j = 0; j < Params.COLUMN_MAX; j++) {
                temp.add(Params.randomInt(0, (int) Params.GRAIN_CAPACITY_MAX));
            }
            land.add(temp);
        }
    }

    public void initializePeople(){
        for(int i=0; i < Params.POPULATION;i++){
            Person temp = new Person();
            people.add(temp);
        }
    }

    public synchronized int consumeGrain(int row, int column) {
        int grainNum = this.land.get(row).get(column);
        this.land.get(row).set(column, 0);
        return grainNum;
    }

    public synchronized void growGrain(){
        for(int i = 0 ;i < Params.ROW_MAX; i++){
            for(int j = 0; j < Params.COLUMN_MAX; j++){
                int grainCapacity = this.land.get(i).get(j);
                if (grainCapacity<Params.GRAIN_CAPACITY_MAX){
                    int newGrainCapacity = Params.randomInt(0,(int)(Params.GRAIN_CAPACITY_MAX - grainCapacity));
                    this.land.get(i).set(j,newGrainCapacity + grainCapacity);
                }
            }
        }
    }

    public ArrayList<Person> getPeople() {
        return people;
    }

    public ArrayList<ArrayList<Integer>> getLand() {
        return land;
    }
}

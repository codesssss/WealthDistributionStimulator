import java.util.ArrayList;

/**
 * @Author Fangzhou Wang
 * @Date 2023/5/11 18:18
 **/
public class Land{
    private ArrayList<Person> people;
    private ArrayList<ArrayList<Patch>> patches;



    public Land() {
        this.patches = new ArrayList<>();
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
                    totalDirection[direction] += patches.get(newRow).get(newColumn).getGrainHere();
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

    public void initializeGrid(){

        for (int i = 0; i < Params.ROW_MAX; i++) {
            ArrayList<Patch> tempList = new ArrayList<>();
            Patch temp = new Patch();
            for (int j = 0; j < Params.COLUMN_MAX; j++) {
                if(Math.random() < Params.PERCENTAGE_OF_BEST_PATCHES) {
                    temp.setMaxGrainHere((int) Params.GRAIN_CAPACITY_MAX);
                    temp.setGrainHere((int)Params.GRAIN_CAPACITY_MAX);
                    tempList.add(temp);
                }else {
                    temp.setMaxGrainHere(0);
                    temp.setGrainHere(0);
                    tempList.add(temp);
                }
            }
            tempList.clear();
            patches.add(tempList);
        }

        for (int x = 0; x < 5; x++) {
            for (int i = 0; i < Params.ROW_MAX; i++) {
                for (int j = 0; j < Params.COLUMN_MAX; j++) {
                    if (getPatchesGrainHere(i,j) != 0) {
                        setPatchesGrainHere(i,j,getPatchesMaxGrainHere(i,j));
                        diffuseGrains(i, j, Params.PERCENTAGE_OF_DIFFUSION);
                    }
                }
            }
        }

        for (int x = 0; x < 10; x++) {
            for (int i = 0; i < Params.ROW_MAX; i++) {
                for (int j = 0; j < Params.COLUMN_MAX; j++) {
                    diffuseGrains(i, j, Params.PERCENTAGE_OF_DIFFUSION);
                }
            }
        }

        for(int i = 0 ; i < Params.ROW_MAX ; i++){
            for(int j = 0 ; j < Params.COLUMN_MAX ; j++){
                setPatchesMaxGrainHere(i,j,getPatchesGrainHere(i,j));
            }
        }

    }
    public void diffuseGrains(int row,int column,double diffusePercent){
        int grains = (int)(getPatchesGrainHere(row,column) * (diffusePercent/100));
        setPatchesMaxGrainHere(row,column,getPatchesMaxGrainHere(row,column) -grains);
        int diffusionGrains = (int)(grains/8);
        setPatchesGrainHere((row+1),column,diffusionGrains);
        setPatchesGrainHere(row,(column+1),diffusionGrains);
        setPatchesGrainHere((row+1),(column+1),diffusionGrains);
        setPatchesGrainHere((row-1),column,diffusionGrains);
        setPatchesGrainHere(row,(column-1),diffusionGrains);
        setPatchesGrainHere((row-1),(column-1),diffusionGrains);
        setPatchesGrainHere((row-1),(column+1),diffusionGrains);
        setPatchesGrainHere((row+1),(column-1),diffusionGrains);
    }



    public void initializePeople(){
        for(int i=0; i < Params.POPULATION;i++){
            Person temp = new Person();
            people.add(temp);
        }
    }

    public synchronized int consumeGrain(int row, int column) {
        int grainNum = getPatchesGrainHere(row, column);
        setPatchesGrainHere(row,column,0);
        return grainNum;
    }

    public synchronized void growGrain(){
        for(int i = 0 ;i < Params.ROW_MAX; i++){
            for(int j = 0; j < Params.COLUMN_MAX; j++){
                int grainCapacity = getPatchesGrainHere(i, j);
                if (grainCapacity<Params.GRAIN_CAPACITY_MAX){
                    int newGrainCapacity = Params.randomInt(0,(int)(Params.GRAIN_CAPACITY_MAX - grainCapacity));
                    this.patches.get(i).get(j).setGrainHere(newGrainCapacity + grainCapacity);
                }
            }
        }
    }

    public ArrayList<Person> getPeople() {
        return people;
    }

    public ArrayList<ArrayList<Patch>> getPatches() {
        return patches;
    }
    
    public int getPatchesGrainHere(int row,int column){
        return (int)patches.get(row).get(column).getGrainHere();
    }
    public void setPatchesGrainHere(int row, int column, int grainNum){
        if (row < Params.ROW_MAX && row >= 0 && column <Params.COLUMN_MAX && column >= 0) {
            patches.get(row).get(column).setGrainHere(grainNum);
        }
    }
    public int getPatchesMaxGrainHere(int row,int column){
        return (int)patches.get(row).get(column).getMaxGrainHere();
    }
    public void setPatchesMaxGrainHere(int row, int column, int grainNum){
        if (row < Params.ROW_MAX && row >= 0 && column <Params.COLUMN_MAX && column >= 0) {
            patches.get(row).get(column).setMaxGrainHere(grainNum);
        }
    }
}

import java.util.ArrayList;

/**
 * @Author Fangzhou Wang
 * @Date 2023/5/11 18:18
 **/
public class Land{
    public ArrayList<Person> people;
    public ArrayList<ArrayList<Patch>> patches;



    public Land() {
        this.patches = new ArrayList<ArrayList<Patch>>();
        initializeGrid();
        this.people = new ArrayList<Person>();
        initializePeople();
    }

    public void simulation(){
        int maxWealth;
        for(Person person : people){
            move(person);
        }
        maxWealth = findTheBiggestWealth();
        for (Person person : people){
            person.updatePersonInfo(maxWealth);
        }
        growGrain();
    }

    private boolean isWithinBounds(int index, int max) {
        return index >= 0 && index < max;
    }

    public void move(Person person){
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
                    totalDirection[direction] += getPatchesGrainHere(newRow,newColumn);
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
            ArrayList<Patch> tempList = new ArrayList<Patch>();
            for (int j = 0; j < Params.COLUMN_MAX; j++) {
                Patch temp = new Patch();
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
        int totalNeighbors = 8;
        boolean rowFlag = false;
        if(row == Params.ROW_MAX || row == 0){
            totalNeighbors = 6;
            rowFlag = true;
        }
        if (column == Params.COLUMN_MAX || column == 0){
            totalNeighbors =6;
            if(rowFlag){
                totalNeighbors = 8;
            }
        }
        int diffusionGrains = (int)(grains/totalNeighbors);
        for(int i = -1; i < 2 ; i++){
            for(int j = -1; j < 2 ; j++) {
                if (i == 0 && j == 0) {
                }else {
                    setPatchesGrainHere((row + i), (column + j), diffusionGrains);
                }
            }
        }
    }

    public void initializePeople(){
        for(int i=0; i < Params.POPULATION;i++){
            Person temp = new Person();
            people.add(temp);
        }
    }

    public int consumeGrain(int row, int column) {
        int grainNum = getPatchesGrainHere(row, column);
        setPatchesGrainHere(row,column,0);
        return grainNum;
    }

    public void growGrain(){
        for(int i = 0 ;i < Params.ROW_MAX; i++){
            for(int j = 0; j < Params.COLUMN_MAX; j++){
                int grainCapacity = getPatchesGrainHere(i, j);
                if (grainCapacity < getPatchesMaxGrainHere(i,j) - Params.NUM_GRAIN_GROWN){
                    setPatchesGrainHere(i,j,(grainCapacity + Params.NUM_GRAIN_GROWN));
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
            this.patches.get(row).get(column).setMaxGrainHere(grainNum);
        }
    }
}

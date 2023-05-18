import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import static java.util.Collections.sort;

/**
 * @Author Fangzhou Wang Haoyu Liu Xuhang Shi
 * @Date 2023/5/11 18:18
 **/

/**
 * Maintain People and Patches
 * Used for process simulation
 */
public class Land {
    public ArrayList<Person> people;
    public ArrayList<ArrayList<Patch>> patches;
    private int poorNum;
    private int middleNum;
    private int richNum;
    private int growRound;


    public Land() {
        this.poorNum = 0;
        this.middleNum = 0;
        this.richNum = 0;
        this.growRound = Params.GRAIN_GROWTH_INTERVAL-1;
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        this.patches = new ArrayList<ArrayList<Patch>>();
        initializePatches();
        this.people = new ArrayList<Person>();
        initializePeople();
    }

    /**
     * Runs the simulation.
     */
    public void simulation() {
        openCSV();
        for (int i = 0; i < Params.ROUND_NUM; i++) {
            double maxWealth;
            for (Person person : people) {
                move(person);
            }
            maxWealth = findTheBiggestWealth();
            for (Person person : people) {
                person.updatePersonInfo(maxWealth);
            }
            if(growRound == 0) {
                growGrain();
            }else{
                growRound--;
            }
            countDifferentWealthClass();
            addWealthClassNumInCSV();
            addGiniCoefficientInCSV(calculateGiniCoefficient());
        }
        wealthSummary();
        System.out.println("Simulation End.");
    }

    /**
     * Checks if the given index is within the valid bounds.
     *
     * @param index the index to check
     * @param max   the maximum value
     * @return true if the index is within bounds, false otherwise
     */
    private boolean isWithinBounds(int index, int max) {
        return index >= 0 && index < max;
    }

    /**
     * Moves a person in a random direction based on the grain distribution around them.
     *
     * @param person the person to move
     */
    public void move(Person person) {
        int[] totalDirection = new int[4];
        int[] dx = {0, 0, -1, 1};
        int[] dy = {1, -1, 0, 0};
        int row = person.getRow();
        int column = person.getColumn();
        int vision = person.getVision();

        for (int i = 0; i <= vision; i++) {
            for (int direction = 0; direction < 4; direction++) {
                int newRow = row + dx[direction] * i;
                int newColumn = column + dy[direction] * i;
                if (isWithinBounds(newRow, Params.ROW_MAX) && isWithinBounds(newColumn, Params.COLUMN_MAX)) {
                    totalDirection[direction] += getPatchesGrainHere(newRow, newColumn);
                }
            }
        }

        int maxDirection = 0;
        for (int i = 1; i < 4; i++) {
            if (totalDirection[i] > totalDirection[maxDirection]) {
                maxDirection = i;
            }
        }

        int newRow = row + dx[maxDirection];
        int newColumn = column + dy[maxDirection];
        if (isWithinBounds(newRow, Params.ROW_MAX) && isWithinBounds(newColumn, Params.COLUMN_MAX)) {
            person.collectWealth(consumeGrain(newRow, newColumn));
            person.setPosition(newRow, newColumn);
        }
    }

    /**
     * Finds the biggest wealth among all people in the simulation
     *
     * @return the biggest wealth
     */
    public double findTheBiggestWealth() {
        double maxWealth = 0;
        for (int i = 0; i < Params.POPULATION; i++) {
            maxWealth = Math.max(maxWealth, people.get(i).getWealth());
        }
        return maxWealth;
    }

    /**
     * Initializes the grid of patches with grain using diffusion method.
     */
    public void diffuseInitModel() {

        for (int i = 0; i < Params.ROW_MAX; i++) {
            ArrayList<Patch> tempList = new ArrayList<Patch>();
            for (int j = 0; j < Params.COLUMN_MAX; j++) {
                Patch temp = new Patch();
                if (Math.random() <= Params.PERCENTAGE_OF_BEST_PATCHES) {
                    temp.setMaxGrainHere(Params.GRAIN_CAPACITY_MAX);
                    temp.setGrainHere(Params.GRAIN_CAPACITY_MAX);
                    tempList.add(temp);
                } else {
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
                    if (getPatchesMaxGrainHere(i, j) != 0) {
                        setPatchesGrainHere(i, j, getPatchesMaxGrainHere(i, j));
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

        for (int i = 0; i < Params.ROW_MAX; i++) {
            for (int j = 0; j < Params.COLUMN_MAX; j++) {
                setPatchesMaxGrainHere(i, j, getPatchesGrainHere(i, j));
            }
        }
    }

    /**
     * Initializes the grid of patches with random grain values.
     */
    public void randomInitModel() {
        for (int i = 0; i < Params.ROW_MAX; i++) {
            ArrayList<Patch> tempList = new ArrayList<Patch>();
            for (int j = 0; j < Params.COLUMN_MAX; j++) {
                Patch temp = new Patch();
                double grainNum = Params.randomInt(0, (int) Params.GRAIN_CAPACITY_MAX);
                temp.setMaxGrainHere(grainNum);
                temp.setGrainHere(grainNum);
                tempList.add(temp);
            }
            patches.add(tempList);
        }

    }

    /**
     * Initializes the patches based on the chosen initialization model.
     */
    public void initializePatches() {
        if(Params.DIFFUSE_INIT_MODEL) {
            diffuseInitModel();
        }else {
            randomInitModel();
        }
        System.out.println("Finish initializeGrid");
    }

    /**
     * Distribute grains to surrounding blocks according to diffuse percent
     * @param row
     * @param column
     * @param diffusePercent
     */
    public void diffuseGrains(int row, int column, double diffusePercent) {
        double grains = (getPatchesGrainHere(row, column) * (diffusePercent));
        setPatchesMaxGrainHere(row, column, getPatchesMaxGrainHere(row, column) - grains);
        int totalNeighbors = 8;
        boolean rowFlag = false;
        if (row == Params.ROW_MAX || row == 0) {
            totalNeighbors = 6;
            rowFlag = true;
        }
        if (column == Params.COLUMN_MAX || column == 0) {
            totalNeighbors = 6;
            if (rowFlag) {
                totalNeighbors = 8;
            }
        }
        double diffusionGrains = (grains / totalNeighbors);
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (i == 0 && j == 0) {
                } else {
                    setPatchesGrainHere((row + i), (column + j), diffusionGrains);
                }
            }
        }
    }

    /**
     * Initializes the list of people in the simulation.
     */
    public void initializePeople() {
        for (int i = 0; i < Params.POPULATION; i++) {
            Person temp = new Person();
            people.add(temp);
        }
        System.out.println("Finish initializePeople");
    }

    /**
     * Get all grains on the patch
     * @param row
     * @param column
     * @return THe number that patch used to have
     */
    public double consumeGrain(int row, int column) {
        double grainNum = getPatchesGrainHere(row, column);
        setPatchesGrainHere(row, column, 0);
        return grainNum;
    }

    /**
     * Grow grains in the simulation
     */
    public void growGrain() {
        for (int i = 0; i < Params.ROW_MAX; i++) {
            for (int j = 0; j < Params.COLUMN_MAX; j++) {
                double grainCapacity = getPatchesGrainHere(i, j);
                if (grainCapacity < getPatchesMaxGrainHere(i, j) - Params.NUM_GRAIN_GROWN) {
                    setPatchesGrainHere(i, j, (grainCapacity + Params.NUM_GRAIN_GROWN));
                } else {
                    setPatchesGrainHere(i, j, Math.abs(grainCapacity - Params.NUM_GRAIN_GROWN));
                }
            }
        }
    }

    /**
     * Count different wealth class number
     */
    public void countDifferentWealthClass() {
        richNum = 0;
        poorNum = 0;
        middleNum = 0;
        for (Person person : people) {
            switch (person.getWealthClass()) {
                case RICH -> this.richNum++;
                case MIDDLE -> this.middleNum++;
                case POOR -> this.poorNum++;
            }
        }
    }

    /**
     * Summary and document all wealth data in CSV file
     */
    public void wealthSummary(){
        ArrayList<Double> allWealth = new ArrayList<>();
        for(Person person:people){
            double wealth =0 ;
            wealth = person.getWealth();
            allWealth.add(wealth);
        }
        sort(allWealth);
        for(double i:allWealth){
            addWealthNumInCSV(i);
        }
    }

    /**
     * Open CSV file and add the headline.
     */
    public void openCSV() {
        String csvFilePath1 = "WealthClassNum.csv";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFilePath1, false))) {
            String dataRow = "Poor,Middle,Rich";
            writer.write(dataRow);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String csvFilePath2 = "WealthNum.csv";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFilePath2, false))) {
            String dataRow = "Wealth,Population";
            writer.write(dataRow);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String csvFilePath = "gini.csv";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFilePath, false))) {
            String dataRow = "Gini";
            writer.write(dataRow);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a line to the WealthClassNum.CSV file with the wealth class information.
     */
    public void addWealthClassNumInCSV() {
        String csvFilePath = "WealthClassNum.csv";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFilePath, true))) {
            String dataRow = "" + this.poorNum + "," + this.middleNum + "," + this.richNum;
            writer.write(dataRow);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Adds a line to the WealthNumCSV file with the wealth information.
     */
    public void addWealthNumInCSV(double wealth){
        String csvFilePath2 = "WealthNum.csv";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFilePath2, true))) {
            String dataRow = ""+wealth+","+Params.POPULATION;
            writer.write(dataRow);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Open Gini CSV file and add the headline.
     */
    public void openGiniCSV() {
        String csvFilePath = "gini.csv";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFilePath, false))) {
            String dataRow = "Gini_Coefficient";
            writer.write(dataRow);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a line to the gini.csv file with the Gini coefficient information.
     * @param giniCoefficient The Gini coefficient to add.
     */
    public void addGiniCoefficientInCSV(double giniCoefficient) {
        String csvFilePath = "gini.csv";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFilePath, true))) {
            String dataRow = "" + giniCoefficient;
            writer.write(dataRow);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Calculates the Gini coefficient for the current population.
     *
     * @return the Gini coefficient
     */
    public double calculateGiniCoefficient() {
        ArrayList<Double> wealth = new ArrayList<>();
        for (Person person : people) {
            wealth.add(person.getWealth());
        }

        sort(wealth);
        int length = wealth.size();
        double cumulativeWealth = 0;
        double cumulativeBase = 0;
        for (int i = 1; i <= length; i++) {
            cumulativeWealth += wealth.get(i - 1);
            cumulativeBase += i;
        }

        double B = cumulativeWealth / cumulativeBase;
        double GiniCoefficient = 1 + 1.0 / length - 2 * B;
        return GiniCoefficient;
    }



    /**
     * Get patch grain number by row and column
     * @param row
     * @param column
     * @return grain number
     */
    public double getPatchesGrainHere(int row, int column) {
        return patches.get(row).get(column).getGrainHere();
    }

    /**
     * Set patch grain number by row and column
     * @param row
     * @param column
     * @param grainNum
     */
    public void setPatchesGrainHere(int row, int column, double grainNum) {
        if (row < Params.ROW_MAX && row >= 0 && column < Params.COLUMN_MAX && column >= 0) {
            patches.get(row).get(column).setGrainHere(grainNum);
        }
    }

    /**
     * Get patch max grain number by row and column
     * @param row
     * @param column
     * @return max grain number
     */
    public double getPatchesMaxGrainHere(int row, int column) {
        return patches.get(row).get(column).getMaxGrainHere();
    }

    /**
     * Set patch max grain number by row and column
     * @param row
     * @param column
     * @param grainNum
     */
    public void setPatchesMaxGrainHere(int row, int column, double grainNum) {
        if (row < Params.ROW_MAX && row >= 0 && column < Params.COLUMN_MAX && column >= 0) {
            this.patches.get(row).get(column).setMaxGrainHere(grainNum);
        }
    }

}

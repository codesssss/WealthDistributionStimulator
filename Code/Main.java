import java.util.ArrayList;

/**
 * @Author Fangzhou Wang
 * @Date 2023/5/11 18:18
 **/
public class Main {

    public static void main(String[] args) {
        Land land = new Land();
        for(int i = 0 ; i < Params.ROUND_NUM ;i++){
            land.simulation();
        }
    }
}
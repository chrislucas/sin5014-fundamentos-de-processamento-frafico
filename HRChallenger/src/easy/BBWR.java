package easy;

/**
 * Created by C_Luc on 13/07/2017.
 * https://www.hackerrank.com/challenges/breaking-best-and-worst-records
 */
public class BBWR {

    public static int []  solver(int [] array, int sz) {
        int [] rs = new int[2];
        int max = array[0];
        int min = array[0];
        int cMax = 0;
        int cMin = 0;

        for(int i=1; i<sz; i++) {
            if(max < array[i]) {
                cMax++;
                max = array[i];
            }
            if(min > array[i]) {
                cMin--;
                min = array[i];
            }
        }

        return rs;
    }


}

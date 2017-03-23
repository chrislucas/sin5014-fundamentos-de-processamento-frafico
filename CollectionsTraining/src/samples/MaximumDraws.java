package samples;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by r028367 on 21/03/2017.
 * https://www.hackerrank.com/domains/algorithms/warmup/difficulty:easy/page:1
 */
public class MaximumDraws {
    public static void main(String[] args) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            int cases = Integer.parseInt(bufferedReader.readLine());
            while(cases>0) {
                System.out.println(Integer.parseInt(bufferedReader.readLine())+1);
                cases--;
            }
        } catch (Exception e) {}
    }
}

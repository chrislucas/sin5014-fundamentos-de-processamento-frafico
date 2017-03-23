package samples;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * Created by r028367 on 22/03/2017.
 * https://www.hackerrank.com/domains/algorithms/warmup/difficulty:easy/page:1
 */
public class MinMaxSum {


    public static void main(String[] args) {
        int nums [] = new int[5];
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            StringTokenizer tk = new StringTokenizer(bufferedReader.readLine(), " ");
            long min = 0, max = 0, sum = 0;
            for(int i=0; tk.hasMoreTokens(); i++) {
                long n = Long.parseLong(tk.nextToken());
                sum += n;
                if(i == 0) {
                    min = max = n;
                }
                else {
                    if(n < min)
                        min = n;
                    if(n > max)
                        max = n;
                }
            }
            System.out.printf("%d %d", sum - max, sum - min);

        } catch (Exception e) {}
    }
}

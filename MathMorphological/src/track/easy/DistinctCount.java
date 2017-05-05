package track.easy;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by r028367 on 24/04/2017.
 * https://www.hackerearth.com/tracks/pledge-2015-easy/distinct-count/
 * DONE
 */
public class DistinctCount {
    public static void main(String[] args) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(System.out), true);
        try {
            int cases = Integer.parseInt(bufferedReader.readLine());
            while(cases>0) {
                StringTokenizer tk = new StringTokenizer(bufferedReader.readLine(), " ");
                int N = Integer.parseInt(tk.nextToken());
                int X = Integer.parseInt(tk.nextToken());
                tk = new StringTokenizer(bufferedReader.readLine(), " ");
                int nums [] = new int[N];
                int LIMIT = 0;
                Set<Integer> set = new HashSet<>();
                int C = tk.countTokens();
                for(int i=0; i<C; i++) {
                    int V = Integer.parseInt(tk.nextToken());
                    if(!set.contains(V)) {
                        LIMIT++;
                        set.add(V);
                    }
                }
                printWriter.printf("%s\n", LIMIT == X ? "Good" : LIMIT < X ? "Bad" : "Average");
                cases--;
            }
        } catch (Exception ex) {}
    }
}

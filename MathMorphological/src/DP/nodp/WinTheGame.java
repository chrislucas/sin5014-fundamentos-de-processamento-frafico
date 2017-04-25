package DP.nodp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.StringTokenizer;

/**
 * Created by r028367 on 25/04/2017.
 * https://www.hackerearth.com/practice/algorithms/dynamic-programming/introduction-to-dynamic-programming-1/practice-problems/algorithm/win-the-game/
 */
public class WinTheGame {
    public static void main(String[] args) {
        NumberFormat numberFormat = new DecimalFormat("0.000000");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            int cases = Integer.parseInt(bufferedReader.readLine());
            while(cases > 0) {
                StringTokenizer tokenizer = new StringTokenizer(bufferedReader.readLine(), " ");
                double R = Double.parseDouble(tokenizer.nextToken());
                double G = Double.parseDouble(tokenizer.nextToken());
                double ANS = 0.0;
                if(G == 0.0 || R == 0.0) {
                    ANS = 1.0;
                }

                cases--;
            }
        } catch (Exception e) {}
    }
}

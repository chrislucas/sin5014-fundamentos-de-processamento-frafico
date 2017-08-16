package geometry;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class CuttingPaperSquare {

    static long solve(int n, int m){
        // Complete this function
        if( (n-1) == 0 ||  (m-1) == 0 )
            return (n-1) + (m-1);
        long min = Math.min((n-1), (m-1));
        long max = Math.max((n-1), (m-1));
        return min + (max * (min+1));
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int m = in.nextInt();
        long result = solve(n, m);
        System.out.println(result);
    }
}

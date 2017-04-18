package DP;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by r028367 on 18/04/2017.
 */
public class ModFact {

    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            long t = Long.parseLong(reader.readLine());
            long [] T = new long[100007];
            T[0] = T[1] = 1;
            while(t>0) {
                int n = Integer.parseInt(reader.readLine());
                long mod = 1000000007;

/*
                long acc = n;
                for(long g=n-1; g>1; g--) {
                    acc = ((acc % mod) * (g % mod)) % mod;
                }
*/
                T[n] = n < 2 ? T[n] : ( (n % mod) * (T[n-1] % mod) ) % mod;
                System.out.println(T[n]);
                --t;
            }

        } catch (Exception e) {}
    }
}

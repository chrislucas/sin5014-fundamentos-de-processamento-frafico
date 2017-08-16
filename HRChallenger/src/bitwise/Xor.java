package bitwise;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by r028367 on 03/04/2017.
 * https://www.hackerearth.com/practice/basic-programming/bit-manipulation/basics-of-bit-manipulation/practice-problems/algorithm/xor-is-mad-77/?utm_campaign=reactivation14&amp;utm_medium=email&amp;utm_source=recommendation&amp;utm_content=practiceanalytics/
 *
 * https://accu.org/index.php/journals/1915
 * https://www.cs.umd.edu/class/sum2003/cmsc311/Notes/BitOp/xor.html
 *
 * https://www.hackerrank.com/challenges/xor-and-sum
 * https://www.hackerrank.com/challenges/binomial-distribution-1
 */
public class Xor {

    private static void pattern(int n) {
        int acc = 0;
        for(int i=1; i<n; i++) {
            if( (i ^ n) == i + n) {
                acc++;
            }
        }
        System.out.println(acc);
    }


    private static void pattern (int m, int n) {
        System.out.println(m + n);
        System.out.println( (m ^ n)  + (m & n) );
    }

    // http://www.exploringbinary.com/ten-ways-to-check-if-an-integer-is-a-power-of-two-in-c/
    private static boolean isPowerOf2(int n) {
        return (n & (n - 1)) == 0;
    }

    public static int countUnsetBits(int n) {
        int acc = 0;
        while(n > 0) {
            if( (n & 1) == 0)
                acc++;
            n >>= 1;
        }
        return 1 << acc;
    }

    public static void main(String[] args) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            String cases = bufferedReader.readLine();
            int c = Integer.parseInt(cases);
            while(c > 0) {
                int n = Integer.parseInt(bufferedReader.readLine());

                /**
                 * Para um numero T que é potencia de 2
                 * ele possui T - 1 numeros com a propriedade i ^ T == i + T
                 * todos os numeros de 1 ate T - 1 quando aplicamos o operador XOR
                 * tem o mesmo retultado
                 *
                 * os numeros que sao uma unidade menores que uma potencia de 2
                 * tem resultado 0. Exemplo 1, 3, 7, 15
                 *
                 * */

                if(isPowerOf2(n)) {
                    System.out.println(n-1);
                }
                // 1,3,7,15,31
                else if(isPowerOf2(n+1)) {
                    System.out.println(0);
                }

                else {
                    System.out.println(countUnsetBits(n) - 1);
                    //pattern(n);
                }
                c--;
            }
        } catch (IOException ioex) {}
    }
}

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Base64;
import java.util.IdentityHashMap;
import java.util.StringTokenizer;

/**
 * Created by r028367 on 29/05/2017.
 *
 * https://www.hackerrank.com/challenges/russian-peasant-exponentiation?utm_campaign=challenge-recommendation&utm_medium=email&utm_source=24-hour-campaign
 * http://lafstern.org/matt/col3.pdf
 *
 * http://alunosonline.uol.com.br/matematica/potenciacao-numeros-complexos-na-forma-trigonometrica.html
 * http://www.matematicadidatica.com.br/PotenciacaoNumerosComplexos.aspx
 * http://mundoeducacao.bol.uol.com.br/matematica/potencia-i.htm
 * https://jkogler.wordpress.com/2008/06/05/numeros-complexos-e-exponenciais-complexas/
 *
 *
 * outros algoritmos
 * https://www.embeddedrelated.com/showarticle/760.php
 *
 * https://en.wikipedia.org/wiki/Imaginary_unit
 *
 * Proximo
 * https://www.hackerrank.com/challenges/bus-station?h_r=next-challenge&h_v=zen
 */
//  Russian Peasant Algorithm
public class RPE {

    public static long multmod(long a, long b, long m) {
        return ((a % m) * (b % m)) % m;
    }

    // http://lafstern.org/matt/col3.pdf
    public static long exp(long base, long exp) {
        while( (exp & 1) == 0) {
            base *= base;
            exp = exp >> 1;
        }
        long p = base;
        exp = exp >> 1;
        while(exp > 0) {
            base *= base;
            if( (exp & 1) == 1 )
                p *= base;
            exp = exp >> 1;
        }
        return p;
    }

    public static long exp2(long base, long exp) {
        long p = 1;
        while (exp > 0) {
            if( (exp & 1) == 1 ) {
                p *= base;
            }
            base *= base;
            exp = exp >> 1;
        }
        return p;
    }

    public static long expmod(long base, long exp, long mod) {
        long p = 1;
        while (exp > 0) {
            if( (exp & 1) == 1 ) {
                p = multmod(p, base, mod);
            }
            base = multmod(base, base, mod);
            exp = exp >> 1;
        }
        return p;
    }


    static class ComplexNumber {
        long r, i;

        public ComplexNumber(long r, long i) {
            this.r = r;
            this.i = i;
        }

        public static long multmod(long a, long b, long m) {
            return ((a % m) * (b % m)) % m;
        }

        public static long minusmod(long a, long b, long m) {
            return ((a % m) - (b % m)) % m;
        }

        public static long plusmod(long a, long b, long m) {
            return ((a % m) + (b % m)) % m;
        }

        public static ComplexNumber multmod(ComplexNumber A, ComplexNumber B, long mod) {
            //long real = ( multmod(A.r, B.r, mod) - multmod(A.i, B.i, mod) ) % mod
                //,img = ( multmod(A.r, B.i, mod) + multmod(A.i, B.r, mod) ) % mod;

            /*
            *
            * (a + bi) + (c + di) = (a + c) + (b + d)i
            * (a + bi) - (c + di) = (a - c) + (b - d)i
            * (a + bi) * (c + di) = (ac - bd) + (ad + bc)i
            * */
            // (a + bi) * (c + di) = (ac - bd) + (ad + bc)i
            long real = minusmod( multmod(A.r, B.r, mod), multmod(A.i, B.i, mod), mod)
                ,img  = plusmod( multmod(A.r, B.i, mod), multmod(A.i, B.r, mod), mod);
            ComplexNumber cn = new ComplexNumber(real, img);
            return cn;
        }

        public static ComplexNumber modexp(ComplexNumber base, long exp, long m) {
            ComplexNumber temp = new ComplexNumber(1, 0);
            while (exp > 0) {
                if( (exp & 1) == 1) {
                    temp = multmod(temp, base, m);
                }
                base = ComplexNumber.multmod(base, base, m);
                exp >>= 1;
            }
            if(temp.r < 0)
                temp.r += m;
            if(temp.i < 0)
                temp.i += m;
            return temp;
        }
    }


     /*casos de testes
     *
     *
3
2 0 9 1000
0 1 5 10
8 2 10 1000000000
     *
     * */
    public static void solver() {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            int cases = Integer.parseInt(bufferedReader.readLine());
            while(cases > 0) {
                StringTokenizer tokenizer = new StringTokenizer(bufferedReader.readLine(), " ");
                long a, b, k, m;
                a = Long.parseLong(tokenizer.nextToken());
                b = Long.parseLong(tokenizer.nextToken());
                k = Long.parseLong(tokenizer.nextToken());
                m = Long.parseLong(tokenizer.nextToken());
                ComplexNumber res = ComplexNumber.modexp(new ComplexNumber(a, b), k, m);
                System.out.printf("%d %d\n", res.r, res.i);
                cases--;
            }
        } catch (IOException ieox) {}
    }


    public static void main(String[] args) {
        solver();
        //System.out.println(exp(3, 12));;
        //System.out.println(exp2(3, 12));;
        /**
         * http://comnuan.com/cmnn02/cmnn02008/
         * */
        //System.out.println(expmod( 35, 1578,25897));;
    }

}

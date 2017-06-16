/**
 * Created by r028367 on 12/06/2017.
 */
public class ExperimentalModExp {

    // http://www.geeksforgeeks.org/how-to-avoid-overflow-in-modular-multiplication/
    public static long expmod2(long base, long exp, long mod) {
        base %= mod;
        long p = 0;
        while(exp>0) {
            if((exp & 1) == 1) {
                p = (p+base)%mod;
            }
            base = (base * 2) % mod;
            exp >>= 1;
        }
        return p % mod;
    }


    public static long expmod3(long base, long exp, long m) {
        base %= m;
        exp %= m;
        long p = 0;
        while(exp>0) {
            if((exp & 1) == 0) {
                p = ((m-p) > base) ? p+base : p+base-m;
            }
            exp >>= 1;
            if(exp == 0) {
                base =  ((m-base) > base) ? base+base : base+base-m;
            }
        }
        return p;
    }

    static long  mulmod( long a,  long b,  long m) {
        long r = 0;
        a %= m;
        b %= m;
        while (b > 0) {
            if ( (b & 1) == 1)  r = ((m-r) > a) ? r+a : r+a-m;    /* r = (r + a) % m */
            b >>= 1;
            if (b == 0)      a = ((m-a) > a) ? a+a : a+a-m;    /* a = (a + a) % m */
        }
        return r;
    }
}

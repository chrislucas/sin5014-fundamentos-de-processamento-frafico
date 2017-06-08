/**
 * Created by r028367 on 29/05/2017.
 *
 * http://www.geeksforgeeks.org/find-maximum-subset-xor-given-set/
 * http://www.geeksforgeeks.org/given-a-set-find-xor-of-the-xors-of-all-subsets/
 * https://www.hackerearth.com/pt-br/tracks/pledge-2015-easy/panda-and-xor/
 */
public class Maxxor {

    public static void subset(int n) {
        for(int i=0; i<(1<<n); i++) {
            for(int j=n-1; j>=0; j--) {
                System.out.printf("%s", ( (1<<j) & i ) > 0 ? "1" : "0");
            }
            System.out.println("");
        }
    }

    public static void subset2(int n) {

        int [] subset = new int[n];
        for(int i=0; i<n; i++)
            subset[i] = (i+1);

        for(int i=0; i<(1<<n); i++) {
            for(int j=n-1; j>=0; j--) {
                //System.out.printf("%s", ( (1<<j) & i ) > 0 ? "1" : "0");
                if( ( (1<<j) & i ) > 0)
                    System.out.printf("%d ",subset[j]);
            }
            System.out.println("");
        }
    }


    public static void main(String[] args) {
        subset2(4);
    }

}

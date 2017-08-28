package geometry.medium;

/**
 * Created by C_Luc on 28/08/2017.
 * http://www.mast.queensu.ca/~math418/m418oh/m418oh05.pdf
 */
public class ExtendedEuclidianMatrixForm {

    // ax+by=gdc(a,b)
    public static long [][] e(long a, long b) {
        long matrix [][] = {
            {1,0,a}
            ,{0,1,b}
        };

        return matrix;
    }

}

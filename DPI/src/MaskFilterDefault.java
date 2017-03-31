/**
 * Created by r028367 on 27/03/2017.
 */
public class MaskFilterDefault {

    public static int [][] identity = {
         {0,0,0}
        ,{0,1,0}
        ,{0,0,0}
    };

    public static class MaskToRGB {
        public static int [][] passaAlta = {
             {-1,-1,-1}
            ,{-1,8,-1}
            ,{-1,-1,-1}
        };

        public static int [][] laplacian = {
             {0,-1,0}
            ,{-1,4,-1}
            ,{0,-1,0}
        };

    }

    public static int [][] passaAlta = {
         {1,1,1}
        ,{1,8,1}
        ,{1,1,1}
    };

    public static int [][] laplacian = {
         {0,1,0}
        ,{1,4,1}
        ,{0,1,0}
    };


    public static int [][] gradientBorderHorizontal = {
        {-1,-1}
        ,{1,1}
    };

    public static int [][] gradientBorderVertical= {
         {-1,1}
        ,{-1,1}
    };

    public static int [][] sobelHorizontal = {
         {1,0,-1}
        ,{2,0,-2}
        ,{1,0,-1}
    };


    public static int [][] sobelVertical = {
        {1,2,1}
        ,{0,0,0}
        ,{-1,-2,-1}
    };


    public static int [][] prewittHorizontal = {
        {-1,0,1}
        ,{-1,0,1}
        ,{-1,0,1}
    };


    public static int [][] prewittVertical = {
        {1,2,1}
        ,{0,0,0}
        ,{-1,-1,-1}
    };

    public static int [][] meanFilter45 = {
         {5,3,6}
        ,{2,1,9}
        ,{8,4,7}
    };

    public static int [][] meanFilter9 = {
         {1,1,1}
        ,{1,1,1}
        ,{1,1,1}
    };

    public static int [][] meanFilter16 = {
         {1,2,1}
        ,{2,4,2}
        ,{1,2,1}
    };


    public static int mean(int [][] mat) {
        int li = mat.length, lj = mat[0].length;
        int acc = 0;
        for(int i=0; i<li; i++)
            for(int j=0; j<lj; j++)
                acc += mat[i][j];
        return acc / (li * lj);
    }



}

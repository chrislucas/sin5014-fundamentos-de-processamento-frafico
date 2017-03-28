/**
 * Created by r028367 on 27/03/2017.
 */
public class MaskFilterDefault {

    public static int [][] identity = {
         {0,0,0}
        ,{0,1,0}
        ,{0,0,0}
    };

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

    public static int [][] meanFilter1 = {
         {5,3,6}
        ,{2,1,9}
        ,{8,4,7}
    };

    public static int [][] meanFilter2 = {
         {1,1,1}
        ,{1,1,1}
        ,{1,1,1}
    };





}

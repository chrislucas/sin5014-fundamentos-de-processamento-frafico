package com.br.editor.utils;

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
        /**
         * Remove ruidos
         * */
        public final static int [][] passaAlta = {
             {-1,-1,-1}
            ,{-1,8,-1}
            ,{-1,-1,-1}
        };

        public final static int [][] laplacian = {
             {0,-1,0}
            ,{-1,4,-1}
            ,{0,-1,0}
        };

        public final static int [][] sharpen = {
             {0,-1,0}
            ,{-1,5,-1}
            ,{0,-1,0}
        };

        public final static int [][] boxBlur = {
             {1,1,1}
            ,{1,1,1}
            ,{1,1,1}
        };

        public final static int [][] gaussianBlur3 = {
             {1,2,1}
            ,{2,4,2}
            ,{1,2,1}
        };

        public final static int [][] gaussianBlur5 = {
             {1,4,6,4,1}
            ,{4,16,24,16,4}
            ,{6,24,36,24,6}
            ,{4,16,24,16,4}
            ,{1,4,6,4,1}
        };

    }

    public static class MaskBorderDetector {
        public static int [][] horizontalGradient = {
             {-1,-1}
            ,{1,1}
        };

        public static int [][] verticalGradient = {
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

    public static class MaskLineDetection{
        public static int [][] maskHorinzontal = {
                {-1,-1,-1}
                ,{2,2,2}
                ,{-1,-1,-1}
        };

        public static int [][] maskVertical = {
                {-1,2,-1}
                ,{-1,2,-1}
                ,{-1,2,-1}
        };


        public static int [][] maskP45 = {
                {-1,-1,2}
                ,{-1,2,-1}
                ,{2,-1,-1}
        };

        public static int [][] maskM45 = {
                {2,-1,-1}
                ,{-1,2,-1}
                ,{-1,-1,2}
        };
    }




    public static int mean(int [][] mat) {
        int li = mat.length, lj = mat[0].length;
        int acc = 0;
        for(int i=0; i<li; i++)
            for(int j=0; j<lj; j++)
                acc += mat[i][j];
        return acc / (li * lj);
    }



}

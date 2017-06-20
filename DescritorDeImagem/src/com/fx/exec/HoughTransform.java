package com.fx.exec;

/**
 * Created by C_Luc on 19/06/2017.
 */
public class HoughTransform {


    static double p [];


    public static int [][] bImage1 = {
         {1,1,1,1,1,1,1,1,1,1}
        ,{1,1,1,1,1,1,1,1,1,0}
        ,{1,1,1,1,1,1,1,1,1,0}
        ,{0,0,1,1,1,1,1,1,1,0}
        ,{0,0,1,1,1,1,1,1,1,0}
        ,{0,0,1,1,1,1,1,1,1,0}
        ,{0,0,1,1,1,1,1,1,1,0}
        ,{0,0,1,1,1,1,0,0,0,0}
        ,{0,0,0,0,0,0,0,0,0,0}
    };

    public static int imgWidth, imgHeight;

    public static double polarLineEquation(int x, int y, int angle) {
        double cos = Math.cos(Math.toRadians(angle));
        double sin = Math.sin(Math.toRadians(angle));
        return x * cos + y * sin;
    }

    public static void sample1() {
        imgWidth    = bImage1.length;
        imgHeight   = bImage1[0].length;
        p = new double[imgHeight * imgHeight];
        for (int i = 0; i <imgHeight ; i++) {
            for (int j = 0; j <imgWidth ; j++) {
                for(int angle=0; angle<=180; angle++) {

                }
            }
        }
    }


    public static void main(String[] args) {

    }

}

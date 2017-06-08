package com.fx;

import oracle.jrockit.jfr.JFR;

import javax.swing.*;

/**
 * Created by C_Luc on 05/06/2017.
 */
public class ColorModel {


    JFrame frame;
    JLabel colorOne, colorTwo;
    JTextField textRed, textGreen, textBlue;
    JTextField textA, textB, textV;


    public final static double YIQTableConversion [][] = {
         {0.299, 0.587, 0.114}
        ,{0.596, -0.257, -0.321}
        ,{0.212, -0.523, 0.311}
    };

    public static final double RGBTableConversion [][] = {
            {1.0, 0.956, 0.621}
            ,{1.0, -0.272, -0.647}
            ,{1.0, -1.106, 1.703}
    };

    /**
     * para funcionar divida os valores de r, g, b por 255
     * para que
     * 0 <= r,g,b <= 1
     * */
    public static double [][] fromRGBToYIQ(double rgb [][]) {
        double [][] yiq = new double[][] {
             {0.0}
            ,{0.0}
            ,{0.0}
        };
        for (int i = 0; i <3 ; i++) {
            for (int j = 0; j <1; j++) {
                for (int k = 0; k <3 ; k++) {
                    yiq[i][0] +=  YIQTableConversion[i][k] * rgb[k][j];
                }
            }
        }

        return yiq;
    }


    public static double [][] fromYIQToRGB(double yiq [][]) {
        double [][] rgb = new double[][] {
             {0.0}
            ,{0.0}
            ,{0.0}
        };
        for (int i = 0; i <3 ; i++) {
            for (int j = 0; j <1; j++) {
                for (int k = 0; k <3 ; k++) {
                    rgb[i][0] +=  RGBTableConversion[i][k] * yiq[k][j];
                }
            }
        }

        return rgb;
    }


    private static double maxRGB(double rgb []) {
        double max = Math.max(Math.max(rgb[0], rgb[1]), rgb[2]);
        return max;
    }

    public static double [] fromRGBToCMYK(double rgb []) {
        double r = rgb[0] / 255.0, g = rgb[1] / 255.0, b = rgb[2] / 255.0;
        double k = 1.0 - maxRGB(new double [] {r, g, b});
        //System.out.println(k);
        double cmy [] = {
             (1.0-r-k) / (1-k)
            ,(1.0-g-k) / (1-k)
            ,(1.0-b-k) / (1-k)
        };
        return cmy;
    }


    public static double [] fromRGBToCMYK2(double rgb []) {
        double cmyk [] = {255.0 - rgb[0], 255.0 - rgb[1], 255.0 - rgb[2]};
        return cmyk;
    }


    public static double [] fromCMYKToRGB(double cmyk []) {
        double rgb [] = new double[4];
        rgb[0] = 255 * (1-cmyk[0]) * (1-cmyk[3]);
        rgb[1] = 255 * (1-cmyk[1]) * (1-cmyk[3]);
        rgb[2] = 255 * (1-cmyk[2]) * (1-cmyk[3]);
        rgb[3] = cmyk[3];
        return rgb;
    }



    public static void buildUI() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ColorModel colorModel = new ColorModel();
                colorModel.frame = new JFrame("Modelo de cor");

            }
        });
    }

    public static void main(String[] args) {
        test();
    }

    private static void test() {
        double rgb [][] = new double[][]  {
             {32.0 / 255.0}
            ,{65.0 / 255.0}
            ,{32.0 / 255.0}
        };

        double yiq [][] = fromRGBToYIQ(rgb);
        System.out.printf("%.6f %.6f %.6f\n", yiq[0][0], yiq[1][0], yiq[2][0]);
        double rgb2[][] = fromYIQToRGB(yiq);
        System.out.printf("%.6f %.6f %.6f\n", rgb2[0][0] * 255.0, rgb2[1][0] * 255.0, rgb2[2][0] * 255.0);
        double rgb3 [] = {0, 255, 215};
        double cmy [] = fromRGBToCMYK(rgb3);
        System.out.printf("%.6f %.6f %.6f\n", cmy[0], cmy[1], cmy[2]);
        double cmy2 [] = fromRGBToCMYK2(rgb3);
        System.out.printf("%.6f %.6f %.6f\n", cmy2[0], cmy2[1], cmy2[2]);
        double cmy3 [] = {cmy[0], cmy[1], cmy[2], 0.0};
        rgb3  = fromCMYKToRGB(cmy3);
        System.out.printf("%.6f %.6f %.6f\n", rgb3[0], rgb3[1], rgb3[2]);
    }

}

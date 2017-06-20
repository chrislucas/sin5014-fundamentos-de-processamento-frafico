package com.fx.exec;

/**
 * Created by C_Luc on 18/06/2017.
 */
public class MorphologicalOp {

    /**
     *
     * https://www.hackerrank.com/challenges/dip-morphological-operations-erosion
     * 6
     * https://www.hackerrank.com/challenges/dip-morphological-operations-dilation
     * 48 no meu algoritmo, porem tem alguns algoritmos que o resultado eh 59, porque
     * eles mudaram os pixels do topo da imagem (estrnaho)
     *
     * https://www.hackerrank.com/challenges/dip-morphological-operations-opening
     * 20, resposta bateu \0/
     *
     * */

    public static int [][] m1 = {
         {0,0,0,0,0,0,0,0,0,0}
        ,{0,1,1,1,1,1,1,1,0,0}
        ,{0,0,0,0,1,1,1,1,0,0}
        ,{0,0,0,0,1,1,1,1,0,0}
        ,{0,0,0,1,1,1,1,1,0,0}
        ,{0,0,0,0,1,1,1,1,0,0}
        ,{0,0,0,1,1,0,0,0,0,0}
        ,{0,0,0,0,0,0,0,0,0,0}
        ,{0,0,0,0,0,0,0,0,0,0}
    };

    public static int [][] m3 = {
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

    // https://en.wikipedia.org/wiki/Erosion_(morphology)
    public static int [][]m2 = {
         {1,1,1,1,1,1,1,1,1,1,1,1,1}
        ,{1,1,1,1,1,1,0,1,1,1,1,1,1}
        ,{1,1,1,1,1,1,1,1,1,1,1,1,1}
        ,{1,1,1,1,1,1,1,1,1,1,1,1,1}
        ,{1,1,1,1,1,1,1,1,1,1,1,1,1}
        ,{1,1,1,1,1,1,1,1,1,1,1,1,1}
        ,{1,1,1,1,1,1,1,1,1,1,1,1,1}
        ,{1,1,1,1,1,1,1,1,1,1,1,1,1}
        ,{1,1,1,1,1,1,1,1,1,1,1,1,1}
        ,{1,1,1,1,1,1,1,1,1,1,1,1,1}
        ,{1,1,1,1,1,1,1,1,1,1,1,1,1}
        ,{1,1,1,1,1,1,1,1,1,1,1,1,1}
        ,{1,1,1,1,1,1,1,1,1,1,1,1,1}
    };

    public static int [][] template1 = {
         {1,1,1}
        ,{1,1,1}
        ,{1,1,1}
    };


    public static int[][] morphologicalOpInBinaryImg(int [][] binaryImage
            , int [][] template, int operation) {

        int h = binaryImage.length;
        int w = binaryImage[0].length;

        int [][] copy = new int[h][w];
        for (int i = 0; i <h ; i++) {
            for (int j = 0; j <w ; j++) {
                copy[i][j] = binaryImage[i][j];
            }
        }

        int ht = template.length;
        int wt = template[0].length;
        // pixels de interesse
        int ph = ht/2;
        int pw = wt/2;

        // loop na imagem
        for (int i = 0; i<=h-ht; i++) {
            for (int j = 0; j<=w-wt; j++) {
                // loop no template
                boolean answer = false;
                for (int k = 0; k <ht ; k++) {
                    boolean find = false;
                    for (int l = 0; l <wt ; l++) {
                        int pImg = binaryImage[i+k][j+l];
                        int pTem = template[k][l];
                        switch (operation) {
                            case 1:
                                if(pImg == 0 && pTem == 1) {
                                    find = answer = true;
                                    // mudar a posicao de interesse da imagem de 1 para 0
                                    copy[i+ph][j+pw] = 0;
                                }
                                break;
                            case 2:
                                if(pImg == 1 && pTem == 1) {
                                    find =answer = true;
                                    // mudar a posicao de interesse da imagem
                                    copy[i+ph][j+pw] = 1;
                                }
                                break;
                        }
                        if(find)
                            break;
                    }
                    if(answer) {
                        break;
                    }
                }
            }
        }
        return copy;
    }


    public static int [][] opOpen() {
        /**
         * aplica-se a operacao de erosao, e na matriz
         * resultante a operacao de dilatacao
         * */

        int result1 [][] = morphologicalOpInBinaryImg(m1, template1, 1);
        int result2 [][] = morphologicalOpInBinaryImg(result1, template1, 2);
        return result2;
    }

    public static int[][] opClose() {
        /**
         * aplica=se a operacao de dilatacao, e na
         * matriz resultante a operacao de erosacao
         * */
        int result1 [][] = morphologicalOpInBinaryImg(m1, template1, 2);
        int result2 [][] = morphologicalOpInBinaryImg(result1, template1, 1);
        return result2;
    }


    private static int count1(int [][] matrix) {
        int h = matrix.length;
        int w = matrix[0].length;
        int c = 0;
        for (int i = 0; i <h ; i++) {
            for (int j = 0; j <w ; j++) {
                if(matrix[i][j] == 1)
                    c++;
                System.out.printf("%d", matrix[i][j]);
            }
            System.out.println("");
        }
        System.out.println("");
        return c;
    }

    private static void test() {
        //System.out.println(count1(morphologicalOpInBinaryImg(m1, template1, 2)));
        System.out.println(count1(morphologicalOpInBinaryImg(m3, template1, 1)));
    }

    public static void testOpOpen() {
        System.out.println(count1(opOpen()));;
    }

    public static void testOpClose() {
        System.out.println(count1(opClose()));;
    }

    public static void main(String[] args) {
        test();
        //testOpOpen();
        //testOpClose();
    }

}

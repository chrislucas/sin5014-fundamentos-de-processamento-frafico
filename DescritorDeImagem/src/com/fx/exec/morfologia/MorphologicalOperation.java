package com.fx.exec.morfologia;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Created by C_Luc on 17/06/2017.
 *
 */
public class MorphologicalOperation {


    static class Template {
        public static int [][] t1 = {
             {0,1,0}
            ,{1,1,1}
            ,{0,1,0}
        };

        public static int [][] t2 = {
             {1,1,1}
            ,{1,1,1}
            ,{1,1,1}
        };
    }

    static class BinaryImage {
        static int matrix1 [][] = {
             {0,0,0,0,0,0}
            ,{0,0,1,1,1,0}
            ,{0,1,1,1,1,1}
            ,{0,1,1,0,1,1}
            ,{0,1,1,0,1,1}
            ,{0,1,1,1,1,1}
            ,{0,1,1,1,1,1}
        };

        static int matrix2 [][] = {

        };
    }



    private static void print(int [][] matrix) {
        int h = matrix.length;
        int w = matrix[0].length;
        for (int i = 0; i <h ; i++) {
            for (int j = 0; j <w ; j++) {
                System.out.printf("%d", matrix[i][j]);
            }
            System.out.println("");
        }
        System.out.println("");
    }

    public static void test1() {
        int [][] cpMatrix = morphologicalOpInBinaryImg(BinaryImage.matrix1, Template.t1, 1);
        print(BinaryImage.matrix1);
        print(cpMatrix);
    }

    public static void test2() {
        int [][] cpMatrix = morphologicalOpInBinaryImg(BinaryImage.matrix1, Template.t1, 2);
        print(BinaryImage.matrix1);
        print(cpMatrix);
    }

    /**
     * int operation = tipo de operacao morfologica
     * 1 - erosao
     * 2 - dilatacao
     * */

    public static int[][] morphologicalOpInBinaryImg(int [][] binaryImage, int [][] template, int operation) {

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
                    for (int l = 0; l <wt ; l++) {
                        int pImg = binaryImage[i+k][j+l];
                        int pTem = template[k][l];
                        boolean find = false;
                        switch (operation) {
                            case 1:
                                /**
                                 *
                                 * Operacao de erosao
                                 * Nos 2 loops internos, estou percorrenco uma submatriz denominada de mascara.
                                 * E faço a seguinte pergunta.
                                 * Para percorrer essa submatriz partimos da posicao (i, j) ateh a posicao (k, l)
                                 * Na matriz binarizada vamos de (i, j) ate (i+k, j+l)
                                 *
                                 * Assim fazemos a seguinte pergunta: Na posicao (k, l) da subsmatriz guarda o valor 1
                                 * se sim, verificamos se a matriz na posicao (i+k, j+l) guarda o valor 0 (pertence ao fundo), se sim
                                 * o pixel central da submatriz que eh a posicao (i+ph, j+pw) muda para 0, ou seja o
                                 * pixel de interesse na matriz binarizada que era 1 representado um objeto muda para 0
                                 * (representando pixel de fundo)
                                 * */
                                if(pImg == 0 && pTem == 1) {
                                    find = answer = true;
                                    // mudar a posicao de interesse da imagem de 1 para 0
                                    copy[i+ph][j+pw] = 0;
                                }
                                break;
                            case 2:
                                /**
                                 * Operacao de dilatacao
                                 *
                                 * Nessa operacao, usamos uma submatriz chamada de 'template' que se sobrepoe a matriz
                                 * da imagem binarizada, igualmente na opeacao de erosao. Porem olhamos para as posicoes
                                 * da matriz binarizada de outra forma. Se na matriz template na posicao (k, l)
                                 * tivermos o valor 1 e na posicao da matriz binarizada (i+k, j+l) tivermos o valor 0
                                 * o pixel de interesse na posicao (i+ph, j+pw) torna-se 1 (pixel pertencente ao objeto)
                                 *
                                 *
                                 * */
                                if(pImg == 1 && pTem == 1) {
                                    find = answer = true;
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

    public static boolean operation(int pixelImage, int pixelTemplate
            , int matrix[][], int operation, int i, int j) {

        return false;
    }

    public static int [][] toBinaryMatrix(BufferedImage bufferedImage) {
        int w = bufferedImage.getWidth();
        int h = bufferedImage.getHeight();
        int binaryImage [][] = new int[h][w];
        for (int i = 0; i <h ; i++) {
            for (int j = 0; j <w ; j++) {
                int c = bufferedImage.getRGB(j, i);
                Color color = new Color(c);
                int m = (color.getRed() + color.getGreen() + color.getBlue()) / 3;
                m = m > 0 ? 1 : 0;
                binaryImage[i][j] = m;
            }
        }
        return binaryImage;
    }

    public static BufferedImage openImage(String pathname) {
        BufferedImage bufferedImage =  null;
        try {
            bufferedImage = ImageIO.read(new File(pathname));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return bufferedImage;
    }

    public static void main(String[] args) {
        test2();
    }

}

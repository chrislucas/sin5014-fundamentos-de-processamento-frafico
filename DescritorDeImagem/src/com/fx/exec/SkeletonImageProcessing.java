package com.fx;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by C_Luc on 05/06/2017.
 */
public class SkeletonImageProcessing {


    public static BufferedImage bufferedImage;
    public static int [][] binaryImage;
    public static ArrayList<int[][]> markedToErase;


    public static int [][] pathMask3x3 = {
         {-1, 0}    // p2
        ,{-1, 1}    // p3
        ,{0, 1}     // p4
        ,{1, 1}     // p5
        ,{1, 0}    // p6
        ,{1, -1}    // p7
        ,{0, -1}    // p8
        ,{-1, -1}    // p9
    };


    /**
     * Algoritmo muito especifico para uma vizinhance de 8
     * */
    public static ArrayList<int [][]> processMask() {

        int wImage = binaryImage[0].length;
        int hImage = binaryImage.length;

        markedToErase = new ArrayList<>();

        /**
         * O algoritmo tem como objetivo percorrer a matriz
         * de pixels, e a partir do pixel de interesse executar
         * alguns testes para saber se um pixel pode ser apagado ou nao.
         * O pixel de interesse eh o central, partindo de uma submatriz 3x3.
         * Nosso laco deve ter um limite que garanta que avaliaremos o pixels certor.
         * Para isso, devemos ir ate 1 pixel antes da borda
         *
         * */
        // passo 1
        for (int i = 0; i<hImage-2; i++) {
            for (int j = 0; j<wImage-2; j++) {
                // pixel central da submatriz 3z3
                int cI = i+1, cJ = j+2;
                if(binaryImage[cI][cJ] == 255) {
                    int N = methodN(cI, cJ);
                    int S = methodS(cI, cJ);
                    int C = stepC(cI, cJ);
                    int D = stepD(cI, cJ);
                    if( (N >= 2 && N <= 6) && S == 1 && C == 0 && D == 0) {
                        markedToErase.add(new int[][] { {cI, cJ} });
                    }
                }
            }
        }
        removePixels();
        markedToErase = new ArrayList<>();
        // passo 2
        for (int i=0; i<hImage-2; i++) {
            for (int j=0; j < wImage-2; j++) {
                // pixel central da submatriz 3z3
                int cI = i+1, cJ = j+2;
                if(binaryImage[cI][cJ] == 255) {
                    int C = stepC(cI, cJ);
                    int D = stepD(cI, cJ);
                    if(C == 0 && D == 0) {
                        markedToErase.add(new int[][] { {cI, cJ} });
                    }
                }
            }
        }
        removePixels();
        return markedToErase;
    }

    private static int methodN(int i, int j) {
        int counter = 0;        // quantidade de pixels que nao sao de fundo
        /**
         * Contorna a vizinhaca de 8 de um pixel da imagem
         * seguinto um caminho espiral, partindo do centro
         * de uma mascara 3x3 [1,1]
         *
         * */
        for(int c=0; c<pathMask3x3.length; c++) {
            int x = pathMask3x3[c][0];
            int y = pathMask3x3[c][1];
            int newX = x+i, newY = y+j;
            // se o pixel vizinho nao for um pixel de fundo
            if(binaryImage[newX][newY] != 0) {
                counter++;
            }
        }
        return counter;
    }

    private static int methodS(int i, int j) {
        int counter = 0;
        //int limit = pathMask3x3.length / 2;
        //for(int acc=0; acc<limit; acc+=2)
        /**
         * nao eh a melhor forma, mas sao 4:30 AM
         * */
        // p2 - p3
        if( binaryImage[i-1][j] == 0 && binaryImage[i-1][j+1] == 1)
            counter++;
        // p4 - p5
        if( binaryImage[i][j+1] == 0 && binaryImage[i+1][j+1] == 1)
            counter++;
        // p6 - p7
        if( binaryImage[i+1][j] == 0 && binaryImage[i+1][j-1] == 1)
            counter++;

        // p8 - p9
        if( binaryImage[i][j-1] == 0 && binaryImage[i-1][j-1] == 1)
            counter++;

        // p9 - p2
        if( binaryImage[i-1][j-1] == 0 && binaryImage[i-1][j] == 1)
            counter++;

        return counter;
    }

    private static int stepC(int i, int j) {
        int a = binaryImage[i-1][j];
        int b = binaryImage[i][j+1];
        int c = binaryImage[i+1][j];
        return a * b * c;
    }

    private static int stepD(int i, int j) {
        int a = binaryImage[i][j+1];
        int b = binaryImage[i+1][j];
        int c = binaryImage[i][j-1];
        return a * b * c;
    }

    private static void removePixels() {
        for (int [][] pos : markedToErase) {
            int x = pos[0][0], y = pos[0][1];
            binaryImage[x][y] = 0;
        }
        return;
    }


    /**
     * O algoritmo funciona para imagens binarizadas, cujo fundo
     * eh preto e a imagem eh branca. Como as imagens de testes
     * sao o inverso disso, vou inverter a cor dos pixels para
     * nao ter que mudar o algoritmo. Depois de processado
     * rodo o processo de inversao novamente, para poder
     * desenhar a imagem de saida
     *
     * */
    public static void invertBinMatrix() {
        int width = binaryImage[0].length;
        int height = binaryImage.length;
        for (int i = 0; i <height ; i++) {
            for (int j = 0; j <width ; j++) {
                binaryImage[i][j] = binaryImage[i][j] == 0 ? 255 : 0;
            }
        }
        return;
    }


    private static void toMatrix() {
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        binaryImage = new int[height][width];
        for (int i = 0; i <height ; i++) {
            for (int j = 0; j <width ; j++) {
                int c = bufferedImage.getRGB(j, i);
                Color color = new Color(c);
                int m = (color.getRed() + color.getGreen() + color.getBlue()) / 3;
                m = m > 255 ? 255 : m < 0 ? 0 : m;
                binaryImage[i][j] = m;
            }
        }
        return;
    }

    public static BufferedImage newImage() {
        int w = bufferedImage.getWidth();
        int h = bufferedImage.getHeight();
        BufferedImage buffer = new BufferedImage(w, h, BufferedImage.TYPE_3BYTE_BGR);
        for (int i = 0; i <h ; i++) {
            for (int j = 0; j <w ; j++) {
                int c = binaryImage[i][j];
                Color color = new Color(c, c, c);
                buffer.setRGB(j, i, color.getRGB());
            }
        }
        return buffer;
    }


    public static final boolean createImage(BufferedImage buffer, String pathfile, String format) {
        File outputFile = new File(pathfile);
        String path = outputFile.getParent();
        File folder = new File(path);
        boolean create = false;
        if( ! folder.exists() ) {
            folder.mkdir();
        }
        try {
            create = ImageIO.write(buffer, format, outputFile);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return create;
    }




    public static void main(String[] args) {
        try {

            String prefix = "exerc3a";

            bufferedImage = ImageIO.read(new File("images/"+prefix+".bmp"));

            toMatrix();
            // inverter imagem para que ela fique com o fundo preto
            invertBinMatrix();
            createImage(newImage(), "images/"+prefix+"_invertida.bmp", "bmp");

            while (true) {
               ArrayList<int [][]> pixelsToRemove =  processMask();
               if(pixelsToRemove.size() == 0)
                   break;
            }

            // novamente o processo de inversao, pois a imagem original tem fundo branco
            // e o desenho eh preso
            invertBinMatrix();


            String filename = "images/"+prefix+"_processada.bmp";
            if( createImage(newImage(), filename, "bmp") ) {
                System.out.println("Imagem criada");
            }
            else {
                System.out.println("Não foi criada a imagem");
            }
            //buffer = ImageIO.read(new File(filename));

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


}

package com.br.editor.utils.exercicies;

import com.br.editor.utils.FiltersToRGB;
import com.br.editor.utils.MaskFilterDefault;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by C.Lucas on 27/05/2017.
 */
public class MaskTestLineDetector {

    public static int testLineDetection(int [][] mask, BufferedImage buffer) {
        int counter = 0;
        int heightImage = buffer.getHeight(), widthImage = buffer.getWidth();
        int matrixPixels [][] = new int[heightImage][widthImage];
        for(int i=0; i<heightImage; i++){
            for (int j=0; j<widthImage; j++) {
                int c = buffer.getRGB(j, i);
                matrixPixels[i][j] = c;
            }
        }
        int limitI = mask.length, limitJ = mask[0].length;
        // posicao da matrix que presenta o PIXEL de interesse
        int xCenterMask = limitI/2, yCenterMask = limitJ/2;
        int meanMatrix = MaskFilterDefault.mean(mask);
        for(int i=0; i<heightImage-limitJ+1; i++) {
            for (int j=0; j<widthImage-limitI+1; j++) {
                int accR = 0, accG = 0, accB = 0;
                for (int x=0; x<limitI; x++) {
                    for (int y=0; y<limitJ; y++) {
                        int newX = i+x, newY = j+y;
                        int c = matrixPixels[newX][newY];
                        Color color = new Color(c);
                        int r = color.getRed(),g = color.getGreen(), b = color.getBlue();
                        accR += r * mask[x][y];
                        accG += g * mask[x][y];
                        accB += b * mask[x][y];
                    }
                }
                accR = accR > 255 ? 255 : accR < 0 ? 0 : accR;
                accB = accB > 255 ? 255 : accB < 0 ? 0 : accB;
                accG = accG > 255 ? 255 : accG < 0 ? 0 : accG;
                buffer.setRGB(j+xCenterMask, i+yCenterMask, new Color(accR, accG, accB).getRGB());
            }
        }

        //System.out.printf("%d %d\n", buffer.getWidth(), buffer.getHeight());
        for(int i=0; i<heightImage; i++) {
            for(int j=0; j<widthImage; j++) {
                int c = buffer.getRGB(j, i);
                Color color = new Color(c);
                if(color.equals(Color.WHITE)) {
                    counter++;
                }
            }
        }
        return counter;
    }


    private static int[] test(BufferedImage bufferedImage) {
        ArrayList<int[][]> masks = MaskFilterDefault.MaskLineDetection.masks;
        int counter[] = {0, 0};
        for(int i=0; i<masks.size(); i++) {
            int [][] mask = masks.get(i);
            BufferedImage bf = FiltersToRGB.copy(bufferedImage);
            int rs  = testLineDetection(mask, bf);
            System.out.printf("%d %d\n", rs, i);
            if(rs > counter[0]) {
                counter[0] = rs;
                counter[1] = i;
            }
            FiltersToRGB.createImage(bf, String.format("images/exercise8/resultado/imagem0%d.jpg", i), "jpg");
        }
        System.out.printf("Melhora mascara: %d %d\n", counter[0], counter[1]);
        return counter;
    }

    private static void open() {
        try {
            /**
             * exercise8/meusexemplos/misto.png"
             * images/exercise8/horizontal.bmp
             * */
            BufferedImage bufferedImage = ImageIO.read(new File("images/exercise8/inclinada2.bmp"));
            System.out.println("Executando");
            test(bufferedImage);
            System.out.println("Fim");
        } catch (IOException ioex) {
            System.out.println(ioex.getMessage());
        }
    }

    /**
     * TODD percorrer uma imagem como uma matriz
     * */
    public static void teste() {
        int [] values = {0,1,2,3,4,5,6,7,8,9};
        int len = values.length;
        int width = len/5;
        for(int i=0; i<len; i++) {
            int w = i%width, h=i/width;
            String fmt = i != 0 && w == 0 ? "\n(%d,%d) " : "(%d,%d) ";
            System.out.printf(fmt, h, w);
        }
    }

    public static void main(String[] args) {
        //teste();
        open();
    }


}

package com.br.editor.utils;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

/**
 * Created by r028367 on 31/03/2017.
 */
public class FiltersToRGB {

    private BufferedImage bufferedImage;
    private int widthImage, heightImage;

    public int[][] getMatrixPixels() {
        return matrixPixels;
    }

    private int [][] matrixPixels;
    private CallbackApplyFilter callbackApplyFilter;

    private final void createInstance(BufferedImage bufferedImage) {
        this.bufferedImage  = bufferedImage;
        this.widthImage     = bufferedImage.getWidth();
        this.heightImage    = bufferedImage.getHeight();
        this.matrixPixels   = new int[heightImage][widthImage];
        for(int i=0; i<heightImage; i++){
            for (int j=0; j<widthImage; j++) {
                int c = bufferedImage.getRGB(j, i);
                //Color color = new Color(c);
                //int r = color.getRed(), g = color.getGreen(), b = color.getBlue();
                matrixPixels[i][j] = c;
            }
        }
    }

    public void redefineBufferedImage(BufferedImage bufferedImage) {
        createInstance(bufferedImage);
    }

    public FiltersToRGB(BufferedImage bufferedImage) {
        createInstance(bufferedImage);
    }

    public FiltersToRGB(BufferedImage bufferedImage, CallbackApplyFilter callbackApplyFilter) {
        createInstance(bufferedImage);
        this.callbackApplyFilter = callbackApplyFilter;
    }

    public BufferedImage applyMask(int [][] mask, String filename, String format) {
        BufferedImage buffer = bufferedImage;
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
                /*
                accR /= meanMatrix;
                accG /= meanMatrix;
                accB /= meanMatrix;
                */
                accR = accR > 255 ? 255 : accR < 0 ? 0 : accR;
                accB = accB > 255 ? 255 : accB < 0 ? 0 : accB;
                accG = accG > 255 ? 255 : accG < 0 ? 0 : accG;
                buffer.setRGB(j+xCenterMask, i+yCenterMask, new Color(accR, accG, accB).getRGB());
            }
        }
        //FiltersToGrayScale.createImage(buffer, String.format("images/after_%s.%s", filename,  format));
        System.out.println("Imagem criada apos o filtro");
        return buffer;
    }

    private final void apply(int mask [][], String filename, String format) {
        if(callbackApplyFilter != null)
            callbackApplyFilter.before(bufferedImage);
        BufferedImage buffer = applyMask(mask, filename, format);
        if(callbackApplyFilter != null)
            callbackApplyFilter.after(buffer);
        bufferedImage = buffer;
    }

    public BufferedImage applyMeanOp(int typeOfMask, String filename, String format) {
        int mask[][];
        switch (typeOfMask) {
            case 1:
                mask = MaskFilterDefault.MaskToRGB.boxBlur;
                break;
            case 2:
                mask = MaskFilterDefault.MaskToRGB.gaussianBlur3;
                break;
            case 3:
                mask = MaskFilterDefault.MaskToRGB.gaussianBlur5;
                break;
            default:
                mask = MaskFilterDefault.MaskToRGB.boxBlur;
        }

        int meanMatrix = MaskFilterDefault.mean(mask);

        BufferedImage buffer = bufferedImage;
        int limitI = mask.length, limitJ = mask[0].length;
        // posicao da matrix que presenta o PIXEL de interesse
        int xCenterMask = limitI/2, yCenterMask = limitJ/2;

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
                accR /= meanMatrix;
                accG /= meanMatrix;
                accB /= meanMatrix;
                accR = accR > 255 ? 255 : accR < 0 ? 0 : accR;
                accB = accB > 255 ? 255 : accB < 0 ? 0 : accB;
                accG = accG > 255 ? 255 : accG < 0 ? 0 : accG;
                buffer.setRGB(j+xCenterMask, i+yCenterMask, new Color(accR, accG, accB).getRGB());
            }
        }
        //FiltersToGrayScale.createImage(buffer, String.format("images/after_%s.%s", filename,  format));
        System.out.println("Imagem criada apos o filtro");
        return buffer;
    }

    public final ActionListener laplacianFilter = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            apply(MaskFilterDefault.MaskToRGB.laplacian, "filtro_laplacian", "jpg");
        }
    };

    public final ActionListener passaAltaFilter = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            apply(MaskFilterDefault.MaskToRGB.passaAlta, "filtro_laplacian", "jpg");
        }
    };

    public final ActionListener mean9 = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            apply(MaskFilterDefault.meanFilter9, "filtro_mean9", "jpg");
        }
    };

    public final ActionListener mean16 = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            apply(MaskFilterDefault.meanFilter16, "filtro_mean16", "jpg");
        }
    };


    public final ActionListener gaussianBlue3 = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(callbackApplyFilter != null)
                callbackApplyFilter.before(bufferedImage);
            bufferedImage = applyMeanOp(2, "filtro_gaussiano3por3", "jpg");
            if(callbackApplyFilter != null)
                callbackApplyFilter.after(bufferedImage);
        }
    };

    public BufferedImage matrixToBuffer(int matrix[][]) {
        int w = matrix[0].length, h = matrix.length;
        BufferedImage buffer = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        for(int i=0; i<h; i++) {
            for (int j = 0; j < w; j++) {
                int c = matrix[i][j];
                Color color = new Color(c,c,c);
                buffer.setRGB(j, i, color.getRGB());
            }
        }
        return  buffer;
    }

    public int [][] matrixGrayScale(int [][] matrix) {
        int w = matrix[0].length, h = matrix.length;
        int [][] mat = new int[h][w];
        for(int i=0; i<h; i++) {
            for(int j=0; j<w; j++) {
                Color color = new Color(matrix[i][j]);
                int r = color.getRed(), g = color.getGreen(), b = color.getBlue();
                mat[i][j] = (r+b+g)/3;
            }
        }
        return mat;
    }

    public final class EqualizationInGrayScale implements ActionListener {
        private int quantityGrayScaleLevel = 10;
        private int[] histogram;
        private CallbackApplyFilter callbackApplyFilter;
        private int [][] matrixGrayScalePixels;
        public EqualizationInGrayScale(int range, int[] histogram, CallbackApplyFilter callbackApplyFilter) {
            // quantidade de niveis de cinza
            this.quantityGrayScaleLevel = range;
            this.histogram              = histogram;
            this.callbackApplyFilter    = callbackApplyFilter;
            // convertendo a matriz de uma imagem em RGB para uma matriz em nivel de cinza
            this.matrixGrayScalePixels  = matrixGrayScale(matrixPixels);
        }

        public void redefine(int matrixPixels [][]) {
            this.matrixGrayScalePixels  = matrixGrayScale(matrixPixels);
        }

        public int getRange() {
            return  this.quantityGrayScaleLevel;
        }

        public int [] getAccumulateHistogram() {
            int [] accHistogram = new int [histogram.length];
            accHistogram[0] = histogram[0];
            for(int i=1; i<histogram.length; i++) {
                accHistogram[i] = accHistogram[i-1] + histogram[i];
            }
            return accHistogram;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int quantityGrayScaleLevel  = getRange();                       // novo intervalo d cores de (0,10) ao inves (0,255)
            /**
             * Quantidade de pixels
             *
             * */
            int quantityIdealPixels = widthImage * heightImage / quantityGrayScaleLevel;
            int accHistogram [] = getAccumulateHistogram();
            int q[] = new int [accHistogram.length];
            for(int i=0; i<accHistogram.length; i++) {
                int v = accHistogram[i]/quantityIdealPixels - 1;
                q[i] = 0 > v ? 0 : v;
            }

            int [][] newMatrixPixels = new int[heightImage][widthImage];
            for(int i=0; i<heightImage; i++) {
                for (int j = 0; j < widthImage; j++) {
                    newMatrixPixels[i][j] = q[matrixGrayScalePixels[i][j]];
                }
            }
            if(callbackApplyFilter != null) {
                callbackApplyFilter.after(matrixToBuffer(newMatrixPixels));
            }

            matrixGrayScalePixels = newMatrixPixels;
        }
    }
}

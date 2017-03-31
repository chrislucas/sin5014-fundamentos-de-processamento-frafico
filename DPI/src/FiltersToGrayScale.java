import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;

/**
 * Created by C.Lucas on 27/03/2017.
 */
public class FiltersToGrayScale {

    //private int [] pixelsImage;
    private int [][] matrixPixelsGrayScale;
    private int widthImage, heightImage;
    private Calendar caleandar;
    private CallbackApplyFilter callbackApplyFilter;

    public int[][] getMatrixPixelsGrayScale() {
        return matrixPixelsGrayScale;
    }

    public int getWidthImage() {
        return widthImage;
    }

    public int getHeightImage() {
        return heightImage;
    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    private BufferedImage bufferedImage;

    public FiltersToGrayScale(BufferedImage bufferedImage, int w, int h) {
        this.bufferedImage      = bufferedImage;
        //this.pixelsImage      = bufferedImage.getRGB(0, 0, w, h , null, 0, w);
        matrixPixelsGrayScale   = getPixelsInGrayScale(bufferedImage);
        this.widthImage         = w;
        this.heightImage        = h;
        this.caleandar = Calendar.getInstance();
        this.caleandar.setTimeInMillis(System.currentTimeMillis());
    }


    public FiltersToGrayScale(CallbackApplyFilter callbackApplyFilter, BufferedImage bufferedImage, int w, int h) {
        this.bufferedImage      = bufferedImage;
        //this.pixelsImage      = bufferedImage.getRGB(0, 0, w, h , null, 0, w);
        this.matrixPixelsGrayScale   = getPixelsInGrayScale(bufferedImage);
        this.widthImage         = w;
        this.heightImage        = h;
        this.caleandar = Calendar.getInstance();
        this.caleandar.setTimeInMillis(System.currentTimeMillis());
        this.callbackApplyFilter = callbackApplyFilter;
    }

    public static final void createImage(BufferedImage buffer, String pathfile) {
        File outputFile = new File(pathfile);
        if( ! outputFile.exists() ) {
            String path = outputFile.getParent();
            new File(path).mkdirs();
        }
        try {
            ImageIO.write(buffer, "jpg", outputFile);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public final ActionListener filterMean = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(callbackApplyFilter != null)
                callbackApplyFilter.before(bufferedImage);
            int [][] filter = MaskFilterDefault.meanFilter45;
            bufferedImage = applyMask(filter, "meanfilter", "jpg");
            if(callbackApplyFilter != null)
                callbackApplyFilter.after(bufferedImage);
        }
    };

    public final ActionListener filterMedian = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            BufferedImage buffer = new BufferedImage(widthImage, heightImage, BufferedImage.TYPE_INT_RGB);
            int r [] = new int [4];
            int g [] = new int [4];
            int b [] = new int [4];
            Color pixel [] = new Color[4];
            for(int i=0; i<widthImage; i++) {
                for (int j=0; j<heightImage; j++) {
                    if(i > 0) {
                        pixel[0] = new Color(bufferedImage.getRGB(i - 1, j)); // cima
                    }
                    if(j < heightImage) {
                        pixel[1] = new Color(bufferedImage.getRGB(i, j + 1)); // direita
                    }
                    if(j<heightImage) {
                        pixel[2] = new Color(bufferedImage.getRGB(i + 1, j)); // embaixo
                    }
                    if(j>0) {
                        pixel[3] = new Color(bufferedImage.getRGB(i, j - 1)); // esquerda
                    }
                    for(int x=0; x<pixel.length; x++) {
                        r[x] = pixel[x].getRed();
                        g[x] = pixel[x].getGreen();
                        b[x] = pixel[x].getBlue();
                    }
                    Arrays.sort(r);
                    Arrays.sort(g);
                    Arrays.sort(b);
                    buffer.setRGB(i,j,new Color(r[2],g[2],b[2]).getRGB());
                }
            }
            createImage(buffer, "images/median.jpg");
        }
    };

    public final ActionListener filterPassaAlta = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(callbackApplyFilter != null)
                callbackApplyFilter.before(bufferedImage);
            bufferedImage = applyMask(MaskFilterDefault.passaAlta, "passaAltaGrayScale", "jpg");
            if(callbackApplyFilter != null)
                callbackApplyFilter.after(bufferedImage);
        }
    };

    public final ActionListener equalizator = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    };

    public int [][] getPixelsInGrayScale(BufferedImage bufferedImage) {
        int w = bufferedImage.getWidth();
        int h = bufferedImage.getHeight();
        int matrix [][] = new int[h][w];
        for(int i=0; i<h; i++){
            for (int j=0; j<w; j++) {
                int c = bufferedImage.getRGB(j, i);
                Color color = new Color(c);
                int r = color.getRed(), g = color.getGreen(), b = color.getBlue();
                int mean = (r+g+b)/3;
                matrix[i][j] =mean;
                bufferedImage.setRGB(j, i, new Color(mean, mean, mean).getRGB());
            }
        }
        return matrix;
    }


    public BufferedImage applyMeanFilter(String filename) {
        int mask [][] = {
             {1,1,1}
            ,{1,1,1}
            ,{1,1,1}
        };
        createImage(bufferedImage, String.format("images/before_%s", filename));
        BufferedImage buffer = bufferedImage;
        int limitI = mask.length, limitJ = mask[0].length;
        // posicao da matrix que presenta o PIXEL de interesse
        int xCenterMask = limitI/2, yCenterMask = limitJ/2;
        for(int i=0; i<heightImage-limitJ+1; i++) {
            for (int j=0; j<widthImage-limitI+1; j++) {
                int acc = 0;
                for (int x=0; x<limitI; x++) {
                    for (int y=0; y<limitJ; y++) {
                        int newX = i+x, newY = j+y;
                        int colorInBorder = matrixPixelsGrayScale[newX][newY];
                        acc += colorInBorder * mask[x][y];
                    }
                }
                acc /= (limitI * limitJ);
                buffer.setRGB(j+xCenterMask, i+yCenterMask, new Color(acc, acc, acc).getRGB());
            }
        }
        createImage(buffer, String.format("images/after_%s", filename));
        System.out.println("Imagem criada apos o filtro");
        return buffer;
    }


    public BufferedImage applyMask(int [][] mask, String filename, String format) {
        SimpleDateFormat dateFMT = new SimpleDateFormat();
        dateFMT.applyPattern("hh_mm_ss");
        String date = dateFMT.format(caleandar.getTime());
        //createImage(bufferedImage, String.format("images/before_%s_%s.jpg", filename, date));
        BufferedImage buffer = bufferedImage;
        int limitI = mask.length, limitJ = mask[0].length;
        // posicao da matrix que presenta o PIXEL de interesse
        int xCenterMask = limitI/2, yCenterMask = limitJ/2;
        int meanMatrix = MaskFilterDefault.mean(mask);
        for(int i=0; i<heightImage-limitJ+1; i++) {
            for (int j=0; j<widthImage-limitI+1; j++) {
                //int oolor = bufferedImage.getRGB(i, j);
                //int color = pixelsImage[i*widthImage+j];
                int acc = 0;
                for (int x=0; x<limitI; x++) {
                    for (int y=0; y<limitJ; y++) {
                        int newX = i+x, newY = j+y;
                        int colorInBorder = matrixPixelsGrayScale[newX][newY];
                        acc += colorInBorder * mask[x][y];
                    }
                }
                acc /= meanMatrix;
                acc = acc < 0 ? 0 : acc > 255 ? 255 : acc;
                buffer.setRGB(j+xCenterMask, i+yCenterMask, new Color(acc, acc, acc).getRGB());
            }
        }
        //createImage(buffer, String.format("images/after_%s_%s.%s", filename, date, format));
        System.out.println("Imagem criada apos o filtro");
        return buffer;
    }

}

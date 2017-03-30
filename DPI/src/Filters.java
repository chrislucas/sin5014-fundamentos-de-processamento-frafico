import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;

/**
 * Created by C.Lucas on 27/03/2017.
 */
public class Filters {

    //private int [] pixelsImage;
    private int [][] matrixPixelsGrayScale;
    private int widthImage, heightImage;
    private BufferedImage bufferedImage;

    public Filters(BufferedImage bufferedImage, int w, int h) {
        this.bufferedImage      = bufferedImage;
        //this.pixelsImage      = bufferedImage.getRGB(0, 0, w, h , null, 0, w);
        matrixPixelsGrayScale   = getPixelsInGrayScale(bufferedImage);
        this.widthImage         = w;
        this.heightImage        = h;
    }

    private final void createImage(BufferedImage buffer, String pathfile) {
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
            int [][] filter = MaskFilterDefault.laplacian;
            BufferedImage buffer = new BufferedImage(heightImage, widthImage, BufferedImage.TYPE_INT_RGB);
            int limitI = filter.length, limitJ = filter[0].length;

            for(int i=0; i<(heightImage-limitJ)+1; i++) {
                for (int j=0; j<(widthImage-limitI)+1; j++) {
                    //int oolor = bufferedImage.getRGB(i, j);
                    //int color = pixelsImage[i*widthImage+j];
                    int acc = 0;
                    for (int x=0; x<limitI; x++) {
                        for (int y=0; y<limitJ; y++) {
                            int newX = i+x, newY = j+y;
                            int colorInBorder = bufferedImage.getRGB(newX, newY);
                            acc += colorInBorder * filter[x][y];
                        }
                    }
                    buffer.setRGB(i, j, new Color(acc, acc, acc).getRGB());
                }
            }
            createImage(buffer, "images/mean.jpg");
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
                buffer.setRGB(j+1, i+1, new Color(acc, acc, acc).getRGB());
            }
        }
        createImage(buffer, String.format("images/after_%s", filename));
        System.out.println("Imagem criada apos o filtro");
        return buffer;
    }


    public BufferedImage applyMask(int [][] mask, String filename) {

        createImage(bufferedImage, String.format("images/before_%s", filename));
        BufferedImage buffer = bufferedImage;

        int limitI = mask.length, limitJ = mask[0].length;
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
                acc /= (limitI * limitJ);
                acc = acc < 0 ? 0 : acc > 255 ? 255 : acc;
                buffer.setRGB(j+1, i+1, new Color(acc, acc, acc).getRGB());
            }
        }
        createImage(buffer, String.format("images/%s", filename));
        System.out.println("Imagem criada apos o filtro");
        return buffer;
    }

}

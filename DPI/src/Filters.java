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

    private int [] pixelsImage;
    private int widthImage, heightImage;
    private BufferedImage bufferedImage;

    public Filters(BufferedImage bufferedImage, int w, int h) {
        this.bufferedImage  = bufferedImage;
        this.pixelsImage    = bufferedImage.getRGB(0, 0, w, h , null, 0, w);
        this.widthImage     = w;
        this.heightImage    = h;
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

            int filter[][] = {
                 {1,1,1}
                ,{1,1,1}
                ,{1,1,1}
            };

            BufferedImage buffer = new BufferedImage(heightImage, widthImage, BufferedImage.TYPE_INT_RGB);
            for(int i=0; i<widthImage; i++) {
                for (int j=0; j<heightImage; j++) {
                    int color = pixelsImage[heightImage*j+i];
                    for (int x=0; x<filter.length; x++) {
                        for (int y=0; y<filter[0].length; y++) {
                            buffer.setRGB(i, j, color + filter[x][y]);
                        }
                    }
                }
            }
            createImage(buffer, "images/mean.jpg");
        }
    };

    public final ActionListener filterMedian = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            BufferedImage buffer = new BufferedImage(heightImage, widthImage, BufferedImage.TYPE_INT_RGB);
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


    public void operatorMask(int [][] matrix) {

    }

}

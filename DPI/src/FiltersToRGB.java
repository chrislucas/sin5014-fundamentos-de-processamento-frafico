import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;

/**
 * Created by r028367 on 31/03/2017.
 */
public class FiltersToRGB {

    private BufferedImage bufferedImage;
    private int widthImage, heightImage;
    private int [][] matrixPixels;

    public FiltersToRGB(BufferedImage bufferedImage) {
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
        FiltersToGrayScale.createImage(buffer, String.format("images/after_%s.%s", filename,  format));
        System.out.println("Imagem criada apos o filtro");
        return buffer;
    }

}

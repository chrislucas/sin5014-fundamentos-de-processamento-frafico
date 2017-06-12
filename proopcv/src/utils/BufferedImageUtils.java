package utils; /**
 * Created by C.Lucas on 21/05/2017.
 */

import org.opencv.core.CvType;
import org.opencv.core.Mat;

import javax.imageio.ImageIO;
import javax.xml.crypto.Data;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;

/**
 * Classe que une as funcoes de {@link BufferedImage}
 * com as classe do OpenCV
 *
 * Ainda estou estudando para descobrir se essa eh a melhor
 * forma de trabalhar com Opencv
 *
 * */
public class BufferedImageUtils {

    /**
     * RGB
     * */
    public static BufferedImage readImage3Channels(File path) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(path);
        } catch (IOException ioex) {
            System.out.println(ioex.getMessage());
        }
        return image;
    }

    public static Mat toMat3Channels(BufferedImage bufferedImage) {
        Mat mat = null;
        DataBufferByte dataBufferByte = (DataBufferByte) bufferedImage.getRaster().getDataBuffer();
        byte buffer [] = dataBufferByte.getData();
        mat = new Mat(bufferedImage.getHeight(), bufferedImage.getWidth(), CvType.CV_8UC3);
        mat.put(0, 0, buffer);
        return mat;
    }


    public static BufferedImage toBufferedImage(Mat image) {
        int type = BufferedImage.TYPE_BYTE_GRAY;
        if(image.channels() > 1) {
            type = BufferedImage.TYPE_3BYTE_BGR;
        }
        // dimensao da imagem
        int h = image.rows(), w = image.cols();
        // numero de canais da imagem * (dimensao) = Total de bytes ocupados
        int totalBytes = image.channels() * h * w;
        //(int) (image.total() * image.elemSize());
        byte [] buffer = new byte[totalBytes];
        int g = image.get(0, 0, buffer);
        System.out.println(g);
        BufferedImage bufferedImage = new BufferedImage(w, h, type);
        DataBufferByte dataBufferByte = (DataBufferByte) bufferedImage.getRaster().getDataBuffer();
        byte [] pixels = dataBufferByte.getData();
        System.arraycopy(buffer, 0, pixels, 0, buffer.length);
        return bufferedImage;
    }

    public static boolean writer(String filename, String format,  Mat image) {
        BufferedImage bufferedImage = toBufferedImage(image);
        return writer(filename, format, bufferedImage);
    }

    public static boolean writer(String filename, String format, BufferedImage bufferedImage) {
        File outputFile = new File(filename);
        if( ! outputFile.exists() ) {
            String path = outputFile.getParent();
            new File(path).mkdirs();
        }
        try {
            ImageIO.write(bufferedImage, format, outputFile);
            return true;
        } catch (Exception e) {
            String message = String.format("%s\n%s", e.getMessage(), e.getCause());
            System.out.println(message);
            return false;
        }
    }
}

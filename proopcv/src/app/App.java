package app;

import utils.Filters;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 * Created by C_Luc on 08/06/2017.
 */
public class App  {

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }


    public static boolean write(String filename, Mat image) {
        try {
            Imgcodecs.imwrite(filename, image);
            return true;
        } catch (Exception e) {
            String message = String.format("%s\n%s", e.getMessage(), e.getCause());
            System.out.println(message);
            return false;
        }
    }

    public static Mat openImage(String filename) throws Exception {
        Mat image = Imgcodecs.imread(filename);
        if(image.dataAddr() == 0) {
            throw new Exception("Erro ao abrir o arquivo");
        }
        return image;
    }

    public static Mat toGrayScale(Mat source) {
        Range rangeRows = new Range(0, source.rows());
        Range rangeCols = new Range(0, source.cols());
        Mat result      = new Mat(source, rangeRows, rangeCols);
        Imgproc.cvtColor(source, result, Imgproc.COLOR_RGB2GRAY);
        return result;
    }

    public static void openImage() {
        try {
            Mat image = openImage("images/img2.png");
            //Mat imageGrayScale = toGrayScale(image);
            Mat imageBlured = Filters.blur(image/*imageGrayScale*/);
            boolean wasWrite = write("images/img2_blured.png", imageBlured);
            System.out.println(wasWrite ? "Imagem Blur criada" : "Imagem Blur não foi criada");

            Mat gaussianFilter = Filters.gaussian(image/*imageGrayScale*/);
            wasWrite = write("images/img2_gaussian_filter.png", gaussianFilter);
            System.out.println(wasWrite ? "Imagem Gaussiana criada" : "Imagem Gaussiana não foi criada");

            gaussianFilter = Filters.gaussian(
                Filters.createMatrixSameSize(image)
                //, new Size(3, 3)
                ,0.5
                ,0.5
                , Filters.BorderType.BORDER_TRANSPARENT
            );

            wasWrite = write("images/img2_gaussian_filter_2.png", gaussianFilter);
            System.out.println(wasWrite ? "Imagem criada" : "Imagem não foi criada");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    public static void main(String[] args) {
        openImage();
    }

}

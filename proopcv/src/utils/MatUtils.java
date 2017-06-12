package utils;

import org.opencv.core.Mat;
import org.opencv.core.Range;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 * Created by C_Luc on 11/06/2017.
 */
public class MatUtils {


    public static Mat toGrayScale(Mat source) {
        Range rangeRows = new Range(0, source.rows());
        Range rangeCols = new Range(0, source.cols());
        Mat result      = new Mat(source, rangeRows, rangeCols);
        Imgproc.cvtColor(source, result, Imgproc.COLOR_RGB2GRAY);
        return result;
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


    public static Mat read(String filename) {
        Mat image = null;
        try {
            image = Imgcodecs.imread(filename);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return image;
    }

}

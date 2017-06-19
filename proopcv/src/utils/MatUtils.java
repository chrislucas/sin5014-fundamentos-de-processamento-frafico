package utils;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Range;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 * Created by C_Luc on 11/06/2017.
 *
 */
public class MatUtils {

    /**
     * CV_8U, CV_16U, CV_16S, CV_32F or CV_64F.
     * */
    public enum ECvType{

        CV_8U(CvType.CV_8U);

        private int type;

        ECvType(int type) {
            this.type = type;
        }

        public int getType() {
            return type;
        }
    }

    public static Mat toGrayScale(Mat source) {
        Range rangeRows = new Range(0, source.rows());
        Range rangeCols = new Range(0, source.cols());
        Mat result      = new Mat(source, rangeRows, rangeCols);
        Imgproc.cvtColor(source, result, Imgproc.COLOR_RGB2GRAY);
        return result;
    }

    public static Mat toGrayScale(Mat source, ECvType eCvType) {
        Range rangeRows = new Range(0, source.rows());
        Range rangeCols = new Range(0, source.cols());
        Mat result      = new Mat(source.size(), eCvType.getType());
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

    // https://www.tutorialspoint.com/opencv/opencv_storing_images.htm
    private static Mat simpleKernel() {
        Mat kernel = new Mat(3, 3, CvType.CV_8U);
        kernel.setTo(new Scalar(1,1,1));
        kernel.setTo(new Scalar(1,1,1));
        kernel.setTo(new Scalar(1,1,1));
        //System.out.println(kernel.dump());
        return kernel;
    }

}

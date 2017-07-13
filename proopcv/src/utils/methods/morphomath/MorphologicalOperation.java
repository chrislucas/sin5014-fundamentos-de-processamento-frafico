package utils.methods.morphomath;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import utils.MatUtils;

/**
 * Created by C_Luc on 18/06/2017.
 */
public class MorphologicalOperation {


    public static void dilateTest(Mat src, Mat dst, int iterations) {
        Mat kernel = new Mat(3, 3, CvType.CV_8UC1);
        kernel.setTo(new Scalar(0,1,0));
        kernel.setTo(new Scalar(1,1,1));
        kernel.setTo(new Scalar(0,1,0));
        Imgproc.dilate(src, dst, kernel, new Point(-1, -1), iterations);
    }


    public enum ElementShape {
         MORPH_RECT(Imgproc.CV_SHAPE_RECT)
        ,MORPH_CROSS(Imgproc.CV_SHAPE_CROSS)
        ,MORPH_ELIIPSE(Imgproc.CV_SHAPE_ELLIPSE);
        private int shape;

        ElementShape(int shape) {
            this.shape = shape;
        }

        public int getShape() {
            return shape;
        }
    }

    public static void dilate(Mat src, Mat dst, int eSize, ElementShape elementShape) {
        Mat kernel = MatUtils.getKernelFromShapeDefault(eSize, elementShape.getShape());
        Imgproc.dilate(src, dst, kernel);
        //System.out.println(kernel.dump());
    }

    public static void erode(Mat src, Mat dst, int eSize, ElementShape elementShape) {
        Mat kernel = MatUtils.getKernelFromShapeDefault(eSize, elementShape.getShape());
        Imgproc.erode(src, dst, kernel);
    }

    public static void open(Mat src, Mat dst, int eSize, ElementShape elementShape) {
        Mat kernel = MatUtils.getKernelFromShapeDefault(eSize, elementShape.getShape());
        Imgproc.morphologyEx(src, dst, Imgproc.MORPH_OPEN, kernel);
    }

    public static void close(Mat src, Mat dst, int eSize, ElementShape elementShape) {
        Mat kernel = MatUtils.getKernelFromShapeDefault(eSize, elementShape.getShape());
        Imgproc.morphologyEx(src, dst, Imgproc.MORPH_CLOSE, kernel);
    }
}

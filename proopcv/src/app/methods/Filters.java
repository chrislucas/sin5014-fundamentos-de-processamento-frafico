package app.methods;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

/**
 * Created by C_Luc on 11/06/2017.
 */
public class Filters {

    public enum BorderType {
        /**
         * http://docs.opencv.org/java/3.1.0/org/opencv/core/Core.html
         * */
         BORDER_REPLICATE(Core.BORDER_REPLICATE)
        ,BORDER_ISOLATED(Core.BORDER_ISOLATED)
        ,BORDER_WRAP(Core.BORDER_WRAP)
        ,BORDER_CONSTANT(Core.BORDER_CONSTANT)
        ,BORDER_REFLECT(Core.BORDER_REFLECT)
        ,BORDER_REFLECT101(Core.BORDER_REFLECT101)
        ,BORDER_REFLECT_101(Core.BORDER_REFLECT_101)
        ,BORDER_TRANSPARENT(Core.BORDER_TRANSPARENT)
        ,BORDER_DEFAULT(Core.BORDER_DEFAULT);

        private int type;

        BorderType(int type) {
            this.type = type;
        }

        public int getType() {
            return type;
        }
    }

    public static Mat blur(Mat src) {
        int cols = src.width(), rows = src.height();
        //int ksize = cols * rows;
        Mat dst = new Mat(rows, cols, CvType.CV_8SC3);
        // tamanho da mascara de blur
        Size kksize = new Size(6, 6);
        Imgproc.blur(src, dst, kksize);
        return dst;
    }

    public static Mat blur(Mat src, Size mask, Point point, BorderType borderType) {
        int cols = src.width(), rows = src.height();
        //int ksize = cols * rows;
        Mat dst = new Mat(rows, cols, CvType.CV_8SC3);
        Imgproc.blur(src, dst,mask, point, borderType.getType());
        return dst;
    }

    public static Mat createMatrixSameSize(Mat src) {
        int cols = src.width(), rows = src.height();
        //int ksize = cols * rows;
        Mat dst = new Mat(rows, cols, CvType.CV_8SC3);
        return dst;
    }

    /**
     * Filtro gaussiano
     *
     * Filtro de suavizacao de image, para retirar ruidos.
     *
     * */
    public static Mat gaussian(Mat src) {
        Mat dst = createMatrixSameSize(src);
        Size mask = new Size(7, 7);
        double sigmax = 5.5;
        double sigmay = 8.5;
        Imgproc.GaussianBlur(src, dst, mask, sigmax, sigmay);
        return dst;
    }


    public static Mat gaussian(Mat src, double sigmaX, double sigmaY, BorderType borderType) {
        Mat dst = createMatrixSameSize(src);
        Size mask = new Size(3, 3);
        Imgproc.GaussianBlur(src, dst, mask, sigmaX, sigmaY, Core.BORDER_DEFAULT /*borderType.getType()*/);
        return dst;
    }

    public static Mat gaussian(Mat src, Size mask
            , double sigmaX, double sigmaY, BorderType borderType) {
        Mat dst = createMatrixSameSize(src);
        Imgproc.GaussianBlur(src, dst, mask, sigmaX, sigmaY, borderType.getType());
        return dst;
    }

}

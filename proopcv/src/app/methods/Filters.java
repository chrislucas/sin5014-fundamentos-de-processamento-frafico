package app.methods;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

/**
 * Created by C_Luc on 11/06/2017.
 *
 * Classe para estudos, para mapear os filtros de convolucao da biblioteca OpenCV
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

    /**
     *
     *
     * */
    public static Mat blur(Mat src, Point anchor) {
        int cols = src.width(), rows = src.height();
        //int ksize = cols * rows;
        Mat dst = new Mat(rows, cols, CvType.CV_8SC3);
        // tamanho da mascara de blur
        Size kksize = new Size(5, 5);
        Imgproc.blur(src, dst, kksize, anchor);
        return dst;
    }


    public static Mat blur(Mat src, Size mask, Point anchor) {
        int cols = src.width(), rows = src.height();
        //int ksize = cols * rows;
        Mat dst = new Mat(rows, cols, CvType.CV_8SC3);
        Imgproc.blur(src, dst, mask, anchor);
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
     * src - input image; the image can have any number of channels
     * , which are processed independently, but the depth should be CV_8U, CV_16U, CV_16S, CV_32F or CV_64F.
     * */
    public static Mat gaussian(Mat src) {
        Mat dst = createMatrixSameSize(src);
        Size mask = new Size(7, 7);
        double sigmax = 1;
        double sigmay = 1.5;
        /**
         *
         * */
        Imgproc.GaussianBlur(src, dst, mask, sigmax, sigmay,  BorderType.BORDER_DEFAULT.getType() );
        return dst;
    }

    public static Mat gaussian(Mat src, double sigmaX, double sigmaY, BorderType borderType) {
        Mat dst = createMatrixSameSize(src);
        Size mask = new Size(3, 3);
        Imgproc.GaussianBlur(src, dst, mask, sigmaX, sigmaY, borderType.getType());
        return dst;
    }

    public static Mat gaussian(Mat src, double sigmaX, double sigmaY) {
        Mat dst = createMatrixSameSize(src);
        Size mask = new Size(3, 3);
        Imgproc.GaussianBlur(src, dst, mask, sigmaX, sigmaY);
        return dst;
    }

    public static Mat gaussian(Mat src, Size mask
            , double sigmaX, double sigmaY, BorderType borderType) {
        Mat dst = createMatrixSameSize(src);
        Imgproc.GaussianBlur(src, dst, mask, sigmaX, sigmaY, borderType.getType());
        return dst;
    }


    /**
     * Parametros da funcao de filtro bilateral
     *
     *
     * src: Imagem de entrada
     * dst: imagem de saida
     * d: diametro do pixel de vizinhada (coloquei 3 como padrao
     * sigmaColor)
     * sigmaSpace)
     *
     * */

    public static Mat bilateralFilter(Mat src) {
        Mat dst = createMatrixSameSize(src);
        Imgproc.bilateralFilter(src, dst, 3, 0, 0, Core.BORDER_DEFAULT);
        return dst;
    }

    public static Mat bilateralFilter(Mat src, BorderType borderType) {
        Mat dst = createMatrixSameSize(src);
        Imgproc.bilateralFilter(src, dst, 3, 0, 0, borderType.getType());
        return dst;
    }

    /**
     *  src: Imagem de entrada
     *  ksize:  matriz de convolucao
     *
     * */
    public static Mat meadianBlur(Mat src, int ksize) {
        Mat dst = createMatrixSameSize(src);
        Imgproc.medianBlur(src, dst, ksize);
        return dst;
    }


    public static final int LOWER_THRESHOLD = 0;
    public static final int UPPER_THRESHOLD = 50;

    public static Mat CannyDectectionLineDefault(Mat src) {
        Mat dst = createMatrixSameSize(src);
        Imgproc.Canny(src, dst, LOWER_THRESHOLD, UPPER_THRESHOLD);
        return dst;
    }

}

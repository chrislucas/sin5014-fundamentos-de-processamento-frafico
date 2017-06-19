package app;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import utils.MatUtils;

import java.util.ArrayList;


import static java.lang.Math.sqrt;



/**
 * Created by C_Luc on 19/06/2017.
 */
public class FindSquares {


    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static ArrayList<ArrayList<Point>> squares;


    /**
     * Angulo entre os vetores OA e OB
     * sendo O o ponto de origem
     * http://mundoeducacao.bol.uol.com.br/matematica/angulo-entre-dois-vetores.htm
     * http://brasilescola.uol.com.br/matematica/Angulo-entre-dois-vetores.htm
     *
     * */
    public static double cosOAB(Point O, Point A, Point B) {
        double diffXOA = A.x - O.x;
        double diffYOA = A.y - O.y;
        double diffXOB = B.x - O.x;
        double diffYOB = B.y - O.y;

        double s1 = diffXOA+diffXOB;
        double s2 = diffYOA+diffYOB;
        double moduloOA = Math.sqrt(diffXOA*diffXOA + diffYOA*diffYOA);
        double moduloOB = Math.sqrt(diffXOB*diffXOB + diffYOB*diffYOB);

        return  (s1 + s2) / sqrt(moduloOA * moduloOB);

    }

    public static void findSquares(Mat image) {
        Mat cloneImgGrayScale = MatUtils.toGrayScale(image, MatUtils.ECvType.CV_8U);

        Imgproc.blur(cloneImgGrayScale, cloneImgGrayScale, new Size(3, 3));



        Mat cloneImage = image.clone();

        /**
         * Matriz temporaria para armazenar a imagem resultante apos o filtro de Canny
         * */
        Mat matTempDetectedEdges = null;


        int upperThreshold = 100;
        Imgproc.Canny(cloneImgGrayScale, matTempDetectedEdges,0, upperThreshold);
        Mat kernel = new Mat(3, 3 ,CvType.CV_8U);
        kernel.setTo(new Scalar(1,1,1));
        kernel.setTo(new Scalar(1,1,1));
        kernel.setTo(new Scalar(1,1,1));


        Imgproc.dilate(matTempDetectedEdges, matTempDetectedEdges, kernel, new Point(-1, -1), 1);

        ArrayList<MatOfPoint> contours = new ArrayList<>();

        // tentando varios valores para threshold
        for(int lowerThreshold = 10; lowerThreshold < 20; lowerThreshold++) {

            //Imgproc.findContours(matTempDetectedEdges, contours);
        }

    }

    public static void drawContours(Mat image) {

    }


    public static void main(String[] args) {
        squares = new ArrayList<>();
    }

}

package app;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import net.sourceforge.tess4j.util.LoadLibs;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import utils.BufferedImageUtils;
import utils.MatUtils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by C_Luc on 11/06/2017.
 */
public class OcvT4j {

    static{ System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }


    public static Mat getImageGraysScale(String pathfile) {
        BufferedImage bufferedImage = BufferedImageUtils.readImage3Channels(new File(pathfile));
        Mat image = BufferedImageUtils.toMat3Channels(bufferedImage);
        Mat imageGrayScale = MatUtils.toGrayScale(image);
        return imageGrayScale;
    }


    public static void processOCR(BufferedImage bufferedImage) {
        ITesseract iTesseract = new Tesseract();
        iTesseract.setLanguage("eng");
        try {
            File folder = LoadLibs.extractTessResources("tessdata");
            iTesseract.setDatapath(folder.getPath());
            String result = iTesseract.doOCR(bufferedImage);
            System.out.println(result);
        } catch (TesseractException e) {
            System.out.println(e.getMessage());
        }
    }

    public static Mat cannyEdgeFilter(Mat src) {
        double threshold1 = 50, threshold2 = 50;
        Mat dest = new Mat(src.rows(), src.cols(), CvType.CV_8UC3);
        Imgproc.Canny(src, dest, threshold1, threshold2);
        return dest;
    }

    public static void test() {
        BufferedImage bufferedImage = BufferedImageUtils.readImage3Channels(new File("images/edge/img4.png"));
        processOCR(bufferedImage);
    }

    public static void test2() {
        Mat imageGraysScale = getImageGraysScale("images/img2.png");
        Mat imageFiltered = cannyEdgeFilter(imageGraysScale);
        BufferedImageUtils.writer("images/img2_filtered.png", "png", imageFiltered);
        processOCR(BufferedImageUtils.toBufferedImage(imageFiltered));
    }

    public static void test3() {
        BufferedImage bufferedImage = BufferedImageUtils.readImage3Channels(
                new File("images/edge/img2_blurred.png"));
        Mat image = BufferedImageUtils.toMat3Channels(bufferedImage);
        Mat imageGrayScale = MatUtils.toGrayScale(image );

        Mat temp = new Mat();
        /*
        * https://www.tutorialspoint.com/java_dip/grayscale_conversion_opencv.htm
        * It converts an image from one color space to another.
        * */
        Imgproc.cvtColor(image, temp, Imgproc.COLOR_BGRA2RGB);
        Mat blurred = temp.clone();
        Imgproc.medianBlur(temp, blurred, 9);

        Mat imageWithContours = findContours(blurred, temp);
        BufferedImageUtils.writer("images/edge/img4_c.png", "png", imageWithContours);
    }

    public static void main(String[] args) {
        test3();
    }


    private static Mat findContours(Mat image, Mat original) {

        Mat imgGrayScale0 = new Mat(image.size(), CvType.CV_8U);
        Mat imgGrayScale2 = new Mat();
        Mat src = original.clone();

        Mat imageAfterCannyProcess = cannyEdgeFilter(image);

        List<MatOfPoint> contours = new ArrayList<>();

        List<Mat> blurredChannel = new ArrayList<>();
        blurredChannel.add(image);

        List<Mat> grayScale0Channel = new ArrayList<>();
        grayScale0Channel.add(imgGrayScale0);

        MatOfPoint2f approxCurve;

        double maxArea  = 0;
        int maxId       = -1;

        for (int c = 0; c < 3; c++) {
            int ch[] = { c, 0 };
            Core.mixChannels(blurredChannel, grayScale0Channel, new MatOfInt(ch));

            int thresholdLevel = 1;
            for (int t = 0; t < thresholdLevel; t++) {
                if (t == 0) {
                    Imgproc.Canny(imgGrayScale0, imgGrayScale2, 10, 20
                            , 3, true); // true ?
                    Imgproc.dilate(imgGrayScale2, imgGrayScale2, new Mat()
                            , new Point(-1, -1), 1); // 1
                    // ?
                } else {
                    Imgproc.adaptiveThreshold(imgGrayScale0, imgGrayScale2, thresholdLevel,
                            Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C,
                            Imgproc.THRESH_BINARY,
                            (src.width() + src.height()) / 200, t);
                }

                Imgproc.findContours(imgGrayScale0, contours, new Mat(),
                        Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);

                for (MatOfPoint contour : contours) {
                    MatOfPoint2f temp = new MatOfPoint2f(contour.toArray());

                    double area = Imgproc.contourArea(contour);
                    approxCurve = new MatOfPoint2f();
                    Imgproc.approxPolyDP(temp, approxCurve,
                            Imgproc.arcLength(temp, true) * 0.02, true);

                    if (approxCurve.total() == 4 && area >= maxArea) {
                        double maxCosine = 0;

                        List<Point> curves = approxCurve.toList();
                        for (int j = 2; j < 5; j++) {

                            double cosine = Math.abs(angle(curves.get(j % 4),
                                    curves.get(j - 2), curves.get(j - 1)));
                            maxCosine = Math.max(maxCosine, cosine);
                        }

                        if (maxCosine < 0.3) {
                            maxArea = area;
                            maxId = contours.indexOf(contour);
                        }
                    }
                }
            }
        }

        if (maxId >= 0) {
            Rect rect = Imgproc.boundingRect(contours.get(maxId));

            Point A = rect.tl();
            Point B = rect.br();
            System.out.printf("A(%f,%f)\nB(%f,%f)\n", A.x , A.y , B.x, B.y);

            Imgproc.rectangle(src, rect.tl(), rect.br(), new Scalar(255, 0, 0, .8), 4);

            //Imgproc.drawContours(src, contours,  -1, new Scalar(255, 0, 0, .8), 4);

            int mDetectedWidth = rect.width;
            int mDetectedHeight = rect.height;

            System.out.printf("W: %d H: %d\n",mDetectedWidth, mDetectedHeight);

        }
        return src;
    }

    private static double angle(Point p1, Point p2, Point p0) {
        double dx1 = p1.x - p0.x;
        double dy1 = p1.y - p0.y;
        double dx2 = p2.x - p0.x;
        double dy2 = p2.y - p0.y;
        return (dx1 * dx2 + dy1 * dy2)
                / Math.sqrt((dx1 * dx1 + dy1 * dy1) * (dx2 * dx2 + dy2 * dy2)
                + 1e-10);
    }


    private static void T() {
        /*
        Mat h = new Mat();

        Imgproc.findContours(imageAfterCannyProcess, contours, h
                ,Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        Iterator<MatOfPoint> it = contours.iterator();
        while (it.hasNext()) {
            MatOfPoint matOfPoint = it.next();
            double area = Imgproc.contourArea(matOfPoint);
        }
        */
    }

}

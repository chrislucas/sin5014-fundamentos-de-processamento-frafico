package app;

import app.methods.Filters;
import org.opencv.core.*;
import utils.BufferedImageUtils;
import views.ImageViewer;

import javax.swing.text.html.ImageView;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Created by C_Luc on 15/06/2017.
 */
public class BlurMethodOpCv {

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    private static void testBlurMethodOpenCv() {
        BufferedImage image = BufferedImageUtils.readImage3Channels(new File("images/zebra.jpg"));
        if(image != null) {
            Mat matImage = BufferedImageUtils.toMat3Channels(image);
            /**
             * OpenCV Error: Assertion failed (0 <= anchor.x && anchor.x < ksize.width && 0 <= anchor.y && anchor.y < ksize.height)
             * O ponto na matriz de filtragem de filtragem deve ser escolhido  0 <= x < largura da matriz e <= 0 y < altura
             * Esse ponto corresponde a posicao do ponto de interessa na matriz de filtragem
             * Point p = new Point(-1,-1) corresponde ao centro da imagem
             * */
            Point anchor = new Point(3,3);
            Mat imageBlurred = Filters.blur(matImage, anchor);
            BufferedImageUtils.writer("images/img1blurv1.png", "png", imageBlurred);

            Size mask = new Size(10, 10);
            anchor = new Point(5, 5);
            imageBlurred = Filters.blur(matImage, mask, anchor);
            BufferedImageUtils.writer("images/img1blurv2.png", "png", imageBlurred);

            mask = new Size(10, 10);
            anchor = new Point(-1, 5-1);
            imageBlurred = Filters.blur(matImage, mask, anchor, Filters.BorderType.BORDER_REPLICATE);
            BufferedImageUtils.writer("images/img1blurv3.png", "png", imageBlurred);

        }
    }

    private static void testGaussianBlurOpenCv() {
        BufferedImage image = BufferedImageUtils.readImage3Channels(new File("images/zebra.jpg"));
        if(image != null) {
            /**
             * Para usar o filtro gaussiano a matriz deve ser dos seguints tipos
             * CV_8U, CV_16U, CV_16S, CV_32F ou CV_64F.
             * O número de caais não immporta
             * Fonte: http://www.bogotobogo.com/OpenCV/opencv_3_tutorial_imgproc_gausian_median_blur_bilateral_filter_image_smoothing.php
             * */
            Mat imageMat = BufferedImageUtils.toMat(image, CvType.CV_8UC3);
            Mat imageblurred = Filters.gaussian(imageMat);
            BufferedImageUtils.writer("images/zebragaussianblurred1.png", "png", imageblurred);

            double sigmaX = 1.5;
            double sigmaY = 2.8;

            imageblurred = Filters.gaussian(imageMat, sigmaX, sigmaY, Filters.BorderType.BORDER_CONSTANT);
            BufferedImageUtils.writer("images/zebragaussianblurred2.png", "png", imageblurred);

        }
    }

    public static void test() {


        BufferedImage image = BufferedImageUtils.readImage3Channels(new File("images/zebra.jpg"));
        if(image != null) {
            ImageViewer imageView = new ImageViewer();
            final Mat mat = BufferedImageUtils.toMat(image, CvType.CV_8UC3);
            imageView.show(mat, "Teste Blur");
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        int j = 3;
                        for(double i=1; i<=10; i+=0.5, j+=2) {
                            Thread.sleep(200);
                            //Mat imageblurred = Filters.gaussian(mat, i, i/*, Filters.BorderType.BORDER_CONSTANT*/);
                            //Mat imageblurred = Filters.blur(mat, new Size(i, i), new Point(-1, -1), Filters.BorderType.BORDER_CONSTANT);
                            Mat imageblurred = Filters.meadianBlur(mat, j);
                            imageView.updateImage(imageblurred);
                        }
                    } catch (InterruptedException e) {
                        System.out.println(e.getCause());
                    }
                }
            });
            thread.start();
        }

    }


    public static void main(String[] args) {
        test();
    }

}

package app.tests;

import utils.ApplyFilterImpl;
import utils.Filters;
import utils.methods.morphomath.MorphologicalOperation;
import org.opencv.core.*;
import utils.BufferedImageUtils;
import views.ImageViewer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by C_Luc on 19/06/2017.
 */
public class TestFilter {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    private BufferedImage imageBuffer;


    private void testBlurFilter(final Mat mat) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    int j = 3;
                    for(double i=1; i<=10; i+=0.5, j+=2) {
                        Thread.sleep(200);
                        Mat imageblurred = Filters.meadianBlur(mat, j);
                    }
                } catch (InterruptedException e) {
                    System.out.println(e.getCause());
                }
            }
        });
        thread.start();
    }

    public BufferedImageUtils.ApplyCallbackImageViewerFilter applyMedianBlur
            = new BufferedImageUtils.ApplyCallbackImageViewerFilter() {
        @Override
        public Mat executeAndUpdate(ImageViewer imageViewer, Mat src) {
            Mat dst = Filters.blur(src, new Size(3,3), new Point(-1,-1));
            return dst;
        }
    };


    private BufferedImageUtils.ApplyCallbackImageViewerFilter applyCannyFilter
            = new BufferedImageUtils.ApplyCallbackImageViewerFilter() {
        @Override
        public Mat executeAndUpdate(ImageViewer imageViewer, Mat mat) {
            Mat result = Filters.CannyDectectionLineDefault(mat);
            imageViewer.updateImage(result);
            return result;
        }
    };

    private BufferedImageUtils.ApplyCallbackImageViewerFilter applyDilateTest
            = new BufferedImageUtils.ApplyCallbackImageViewerFilter() {
        @Override
        public Mat executeAndUpdate(ImageViewer imageViewer, Mat mat) {
            Mat dst = new Mat();
            MorphologicalOperation.dilateTest(mat, dst,  15);
            imageViewer.updateImage(dst);
            return dst;
        }
    };

    private BufferedImageUtils.ApplyCallbackImageViewerFilter applyErode
            = new BufferedImageUtils.ApplyCallbackImageViewerFilter() {
        @Override
        public Mat executeAndUpdate(ImageViewer imageViewer, Mat mat) {
            Mat dst = new Mat();
            MorphologicalOperation.erode(mat, dst,  3, MorphologicalOperation.ElementShape.MORPH_RECT);
            imageViewer.updateImage(dst);
            return dst;
        }
    };

    private BufferedImageUtils.ApplyCallbackImageViewerFilter applyDilate
            = new BufferedImageUtils.ApplyCallbackImageViewerFilter() {
        @Override
        public Mat executeAndUpdate(ImageViewer imageViewer, Mat mat) {
            Mat dst = new Mat();
            MorphologicalOperation.dilate(mat, dst,  3, MorphologicalOperation.ElementShape.MORPH_RECT);
            imageViewer.updateImage(dst);
            return dst;
        }
    };

    private void applyCannyEdgeDectector() {
        // carregando a imagem
        imageBuffer = BufferedImageUtils.readImage3Channels(new File("images/img2.png"));
        if(imageBuffer != null) {
            // transformando em matriz
            Mat mat = BufferedImageUtils.toMat(imageBuffer, CvType.CV_8UC3);
            // abrindo a imagem num JFrame e passando um filtro como parametro
            BufferedImageUtils.openOnFrame(applyCannyFilter, mat, "Teste callback");
        }
    }


    private void applyDilationOperation() {
        // carregando a imagem
        imageBuffer = BufferedImageUtils.readImage3Channels(new File("images/img2.png"));
        if(imageBuffer != null) {
            // transformando em matriz
            Mat mat = BufferedImageUtils.toMat(imageBuffer, CvType.CV_8UC3);
            BufferedImageUtils.openOnFrame(applyDilateTest, mat, "Teste callback");
        }
    }

    private void applyListOperation() {
        // carregando a imagem
        imageBuffer = BufferedImageUtils.readImage3Channels(new File("images/img2.png"));
        if(imageBuffer != null) {
            // transformando em matriz
            Mat mat = BufferedImageUtils.toMat(imageBuffer, CvType.CV_8UC3);
            List<BufferedImageUtils.ApplyCallbackImageViewerFilter>  callbacks = new ArrayList<>();
            callbacks.add(applyMedianBlur);
            callbacks.add(applyCannyFilter);
            callbacks.add(applyDilateTest);
            BufferedImageUtils.applyCallbacks(callbacks, mat, "Teste callback");
        }
    }

    private void testApplyFilter() {
        imageBuffer = BufferedImageUtils.readImage3Channels(new File("images/img2.png"));
        if(imageBuffer != null) {
            // transformando em matriz
            Mat mat = BufferedImageUtils.toMat(imageBuffer, CvType.CV_8UC3);
            ImageViewer imageViewer = new ImageViewer();
            ApplyFilterImpl applyFilter = new ApplyFilterImpl(imageViewer, mat);
            applyFilter.openFrame("Teste Filtro em cadeia");
            applyFilter.applyDefaultBlur()
                    .applyDefaultCannyDetectEdge();
        }

    }

    private void testErodeOperation() {
        imageBuffer = BufferedImageUtils.readImage3Channels(new File("images/placa2.jpg"));
        if(imageBuffer != null) {
            // transformando em matriz
            Mat mat = BufferedImageUtils.toMat(imageBuffer, CvType.CV_8UC3);
            BufferedImageUtils.openOnFrame(applyErode, mat, "Testando Operacao morfologica Erosão");
        }
    }

    private void testDilateOperation() {
        imageBuffer = BufferedImageUtils.readImage3Channels(new File("images/placa2.jpg"));
        if(imageBuffer != null) {
            // transformando em matriz
            Mat mat = BufferedImageUtils.toMat(imageBuffer, CvType.CV_8UC3);
            BufferedImageUtils.openOnFrame(applyDilate, mat, "Testando Operacao morfologica Erosão");
        }
    }

    public static void main(String[] args) {
        //new TestFilter().applyCannyEdgeDectector();
        //new TestFilter().applyDilationOperation();
        //new TestFilter().applyListOperation();
        //new TestFilter().testApplyFilter();
        //new TestFilter().testErodeOperation();
        new TestFilter().testDilateOperation();
    }

}

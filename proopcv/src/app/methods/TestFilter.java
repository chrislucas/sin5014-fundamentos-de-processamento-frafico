package app.methods;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import utils.BufferedImageUtils;
import views.ImageViewer;

import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Created by C_Luc on 19/06/2017.
 */
public class TestFilter {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    private BufferedImage imageBuffer;
    private Mat mat;

    private BufferedImageUtils.ApplyCallbackImageViewerFilter applyMedianBlur = new BufferedImageUtils.ApplyCallbackImageViewerFilter() {
        @Override
        public void execute(ImageViewer imageViewer) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        int j = 3;
                        for(double i=1; i<=10; i+=0.5, j+=2) {
                            Thread.sleep(200);
                            Mat imageblurred = Filters.meadianBlur(mat, j);
                            imageViewer.updateImage(imageblurred);
                        }
                    } catch (InterruptedException e) {
                        System.out.println(e.getCause());
                    }
                }
            });
            thread.start();
        }
    };


    private BufferedImageUtils.ApplyCallbackImageViewerFilter applyCannyFilter = new BufferedImageUtils.ApplyCallbackImageViewerFilter() {
        @Override
        public void execute(ImageViewer imageViewer) {
            Mat result = Filters.CannyDectectionLineDefault(mat);
            imageViewer.updateImage(result);
        }
    };


    public  void openImageOnFrame() {
        imageBuffer = BufferedImageUtils.readImage3Channels(new File("images/img2.png"));
        if(imageBuffer != null) {
            mat = BufferedImageUtils.toMat(imageBuffer, CvType.CV_8UC3);
            BufferedImageUtils.openOnFrame(applyCannyFilter, mat, "Teste callback");
        }
    }

    public static void main(String[] args) {
        new TestFilter().openImageOnFrame();
    }

}

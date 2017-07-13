package app.tests;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import utils.BufferedImageUtils;
import utils.MatUtils;
import utils.methods.morphomath.FloodFillOperation;
import views.ImageViewer;
import views.UIFloodFill;

import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Created by C_Luc on 22/06/2017.
 */
public class TestFloodFill {

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    private BufferedImage imageBuffer;


    private BufferedImageUtils.ApplyCallbackImageViewerFilter floodFillOp
            = (imageViewer, mat) -> {
                Mat mask = new Mat();
                mask.setTo(new Scalar(0,1,0));
                mask.setTo(new Scalar(1,1,1));
                mask.setTo(new Scalar(0,1,0));
                FloodFillOperation.floodfill(mat, mask, new Scalar(0));
                imageViewer.updateImage(mat);
                return mat;
            };

    private void test1() {
        imageBuffer = BufferedImageUtils.readImage3Channels(new File("images/placa2.jpg"));
        if(imageBuffer != null) {
            // transformando em matriz
            Mat mat = BufferedImageUtils.toMat(imageBuffer, CvType.CV_8UC3);
            BufferedImageUtils.openOnFrame(floodFillOp, mat, "Testando Operacao morfologica Erosão");
        }
    }

    private void test2() {
        imageBuffer = BufferedImageUtils.readImage3Channels(new File("images/placa2.jpg"));
        if(imageBuffer != null) {
            // transformando em matriz
            Mat mat = BufferedImageUtils.toMat(imageBuffer, CvType.CV_8UC3);
            UIFloodFill uiFloodFill = new UIFloodFill(mat, "Flood Fill OpenCv");
            uiFloodFill.init();
        }
    }

    public static void main(String[] args) {
        //new TestFloodFill().test1();
        new TestFloodFill().test2();
    }
}

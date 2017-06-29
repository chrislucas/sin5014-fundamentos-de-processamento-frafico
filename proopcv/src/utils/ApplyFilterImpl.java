package utils;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Size;
import views.ImageViewer;

import java.util.List;

/**
 * Created by C_Luc on 22/06/2017.
 */
public class ApplyFilterImpl extends AbstractApplyFilter {


    public ApplyFilterImpl(ImageViewer imageViewer, Mat original) {
        super(imageViewer, original);
    }

    public ApplyFilterImpl applyListOperation(List<ImageProcess> imageProcesses) {
        for (ImageProcess imageProcess : imageProcesses) {
            imageProcess.execute();
            updateFrame();
        }
        return this;
    }

    public ApplyFilterImpl applyDefaultBlur() {
        Mat mat = new Mat();
        Filters.blur(copy, mat, new Size(3,3), new Point(-1,-1));
        copy = mat.clone();
        updateFrame();
        return this;
    }

    public ApplyFilterImpl applyDefaultCannyDetectEdge() {
        Mat mat = new Mat();
        Filters.CannyDectectionLineDefault(copy, mat);
        copy = mat.clone();
        updateFrame();
        return this;
    }


    public void updateFrame() {
        imageViewer.updateImage(copy);
        return;
    }

}

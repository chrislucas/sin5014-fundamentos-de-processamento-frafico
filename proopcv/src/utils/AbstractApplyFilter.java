package utils;

import org.opencv.core.Mat;
import views.ImageViewer;

/**
 * Created by C_Luc on 22/06/2017.
 */
public abstract class AbstractApplyFilter {
    protected ImageViewer imageViewer;
    protected Mat original, copy;

    public ImageViewer getImageViewer() {
        return imageViewer;
    }

    public Mat getOriginal() {
        return original;
    }

    public Mat getCopy() {
        return copy;
    }

    public AbstractApplyFilter(ImageViewer imageViewer, Mat original) {
        this.imageViewer    = imageViewer;
        this.original       = original;
        this.copy           = original.clone();
    }

    public void openFrame(String title) {
        imageViewer.show(this.original, title);
    }
}

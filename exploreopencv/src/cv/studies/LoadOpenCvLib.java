package cv.studies;

import org.opencv.core.Core;

/**
 * Created by C.Lucas on 21/05/2017.
 */
public abstract class LoadOpenCvLib {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }
}

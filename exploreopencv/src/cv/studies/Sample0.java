package cv.studies;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;

import java.lang.reflect.Field;

/**
 * Created by C.Lucas on 08/05/2017.
 */
public class Sample0 {

    static  {
        /*
        */
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }


    /**
     * CV_8U 8 bits unsigned integers 0 a 255
     * CV_8S 8 bits signed integers -128 a 127
     * CV_16U 16 bits unsigned integers 0 a 65535
     * CV_16U 16 bits signed integers -32768 a 32767
     * CV_32S -2^31 a 2^31-1
     * CV_32F -FLT_MAX a FLT_MAX
     * CV_64F -DBL_MAX a DBL_MAX
     * */
    public static void main(String[] args) {
        System.out.println(Core.VERSION);
        Mat mat = new Mat(5, 10, CvType.CV_8UC1, new Scalar(0));
        Mat row1 = mat.row(1);
        row1.setTo(new Scalar(1));
        Mat col5 = mat.col(5);
        col5.setTo(new Scalar(255));    // 0 <= N <= 255 se N < 0 entao 0 se N > 255 entao 255
        System.out.println(mat.dump());
    }

    public static void init() {
        String model = System.getProperty("sun.arch.data.model");
        // the path the .dll lib location
        String libraryPath = "C:/opencv/build/java/x86/";
        // check for if system is 64 or 32
        if(model.equals("64")) {
            libraryPath = "C:/opencv/build/java/x64/";
        }
        System.setProperty("java.library.path", libraryPath);
        try {
            Field sysPath = ClassLoader.class.getDeclaredField("sys_paths");
            sysPath.setAccessible(true);
            sysPath.set(null, null);
            System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}

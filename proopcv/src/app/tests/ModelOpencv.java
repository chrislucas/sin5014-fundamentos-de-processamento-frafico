package app.tests;

import org.opencv.core.Core;



/**
 * Created by C_Luc on 11/06/2017.
 */
public abstract class ModelOpencv {

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

}

package app.tests;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

import java.util.Random;

/**
 * Created by C_Luc on 22/06/2017.
 */
public class FloodFillFacade {

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }
    public static final int NULL_RANGE      = 0;
    public static final int FIXED_RANGE     = 1;
    public static final int FLOATING_RANGE  = 2;
    private boolean colored     = true;
    private boolean masked      = true;
    private int range           = FIXED_RANGE;
    private Random random       = new Random();
    private int connectivity    = 4;
    private int newMaskVal      = 255;
    private int lowerDiff       = 20;
    private int upperDiff       = 20;

    public int fill(Mat image, Mat mask, int x, int y) {
        Point seedPoint = new Point(x, y);
        int b = random.nextInt(256);
        int g = random.nextInt(256);
        int r = random.nextInt(256);

        Rect rect = new Rect();
        Scalar newVal = isColored() ? new Scalar(b, g, r) :
                new Scalar(0.299 * r + g* 0.587 + 0.114 * b );
        Scalar lowerDifference = new Scalar(lowerDiff,lowerDiff,lowerDiff);
        Scalar upperDifference = new Scalar(upperDiff,upperDiff,upperDiff);
        if(range == NULL_RANGE) {
            lowerDifference = new Scalar(0,0,0);
            upperDifference = new Scalar(0,0,0);
        }
        int val = (range == FIXED_RANGE ? Imgproc.FLOODFILL_FIXED_RANGE : 0) | 0;
        int flags   = connectivity + (newMaskVal << 8) + val;
        int area    = 0;
        area = Imgproc.floodFill(image, masked ? mask : new Mat()
                , seedPoint, newVal, rect, lowerDifference, upperDifference, flags);
        return area;
    }

    public boolean isColored() {
        return colored;
    }

    public void setColored(boolean colored) {
        this.colored = colored;
    }

    public boolean isMasked() {
        return masked;
    }

    public void setMasked(boolean masked) {
        this.masked = masked;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public int getConnectivity() {
        return connectivity;
    }

    public void setConnectivity(int connectivity) {
        this.connectivity = connectivity;
    }

    public int getNewMaskVal() {
        return newMaskVal;
    }

    public void setNewMaskVal(int newMaskVal) {
        this.newMaskVal = newMaskVal;
    }

    public int getLowerDiff() {
        return lowerDiff;
    }

    public void setLowerDiff(int lowerDiff) {
        this.lowerDiff = lowerDiff;
    }

    public int getUpperDiff() {
        return upperDiff;
    }

    public void setUpperDiff(int upperDiff) {
        this.upperDiff = upperDiff;
    }
}

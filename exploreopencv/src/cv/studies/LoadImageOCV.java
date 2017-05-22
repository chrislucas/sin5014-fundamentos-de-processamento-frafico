package cv.studies;

import cv.studies.views.ImageViewer;
import org.opencv.core.Mat;
import org.opencv.core.Range;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import javax.swing.text.html.ImageView;

/**
 * Created by C.Lucas on 21/05/2017.
 */
public class LoadImageOCV extends LoadOpenCvLib {

    public static Mat openFile(String filename) throws Exception {
        Mat image = Imgcodecs.imread(filename);
        if(image.dataAddr() == 0) {
            throw new Exception("Erro ao abrir o arquivo");
        }
        return image;
    }

    public static Mat detectionRectangle(Mat sourceImage) {
        Range rangeRows = new Range(0, sourceImage.rows());
        Range rangeCols = new Range(0, sourceImage.cols());
        Mat finalImage  = new Mat(sourceImage, rangeRows, rangeCols);

        return finalImage;
    }

    public static Mat testThresholdOpenCv(Mat source) {
        Range rangeRows = new Range(0, source.rows());
        Range rangeCols = new Range(0, source.cols());
        Mat result      = new Mat(source, rangeRows, rangeCols);
        Imgproc.boxFilter(source, result, Imgproc.COLOR_GRAY2RGB, new Size(10,10));
        return result;
    }

    public static boolean write(String filename, Mat image) {
        try {
            Imgcodecs.imwrite(filename, image);
            return true;
        } catch (Exception e) {
            String message = String.format("%s\n%s", e.getMessage(), e.getCause());
            System.out.println(message);
            return false;
        }
    }

    public static Mat toGrayScale(Mat source) {
        Range rangeRows = new Range(0, source.rows());
        Range rangeCols = new Range(0, source.cols());
        Mat result      = new Mat(source, rangeRows, rangeCols);
        Imgproc.cvtColor(source, result, Imgproc.COLOR_RGB2GRAY);
        return result;
    }

    public static void loadImageViewer(Mat image) {
        if(image != null) {
            ImageViewer imageViewer = new ImageViewer();
            imageViewer.show(image, "Processamento de images com OPENCV");
        }
    }

    public static void main(String[] args) {
        try
        {
            Mat image = openFile("images/img2.png");
            Mat imageGrayScale = toGrayScale(image);
            loadImageViewer(imageGrayScale);
            //write("images/img2gray.png", imageGrayScale);
            //System.out.println(image.dump());
            detectionRectangle(image);
        } catch (Exception e) {
            System.out.println(e.getCause());
        }

    }

}

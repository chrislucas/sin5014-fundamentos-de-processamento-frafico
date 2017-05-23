package cv.studies.views;


import cv.studies.BufferedImageUtils;
import cv.studies.LoadOpenCvLib;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;

import javax.swing.*;
import java.awt.*;

/**
 * Created by C.Lucas on 22/05/2017.
 */
public class AppCaptureVideo extends LoadOpenCvLib {

    private JLabel imageLabel;
    private JFrame frame;

    public static void main(String[] args) {
        AppCaptureVideo ref = new AppCaptureVideo();
        ref.buildGUI();
        ref.runImageCapture(args);
    }

    private void buildGUI() {
        frame = new JFrame("");
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setSize(400,400);
        imageLabel = new JLabel();
        frame.add(imageLabel);
        frame.setVisible(true);
    }

    private void runImageCapture(String [] args) {
        Mat camera = new Mat();
        Image tempImage;
        VideoCapture videoCapture = new VideoCapture(0);
        videoCapture.set(Videoio.CAP_PROP_FRAME_WIDTH, 320);
        videoCapture.set(Videoio.CAP_PROP_FRAME_HEIGHT, 240);
        if(videoCapture.isOpened()) {
            while (true) {
                videoCapture.read(camera);
                if(!camera.empty()) {
                    tempImage = BufferedImageUtils.toBufferedImage(camera);
                    ImageIcon imageIcon = new ImageIcon(tempImage);
                    imageLabel.setIcon(imageIcon);
                    frame.pack();
                }
                else {
                    System.out.println("Erro na leitura da camera");
                    break;
                }
            }
        }
        else {
            System.out.println("Nao foi possivel abrir a camera");
        }

    }
}

package transformation;

import javax.swing.*;
import java.awt.*;

/**
 * Created by r028367 on 07/04/2017.
 */
public class AppGeoTransformation {

    private JFrame frame;
    private Canvas2D canvas2D;
    private JSlider sliderTransformation;
    private GridBagLayout gridBagLayout;


    public AppGeoTransformation() {
        this.frame = new JFrame();
        this.frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.gridBagLayout = new GridBagLayout();
    }


    public JFrame getFrame() {
        return frame;
    }

    private void defineConstraintLayout() {

    }

    public static void main(String[] args) {

        AppGeoTransformation appGeoTransformation = new AppGeoTransformation();

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = appGeoTransformation.getFrame();
                frame.setSize(new Dimension(600,400));
                frame.pack();
                frame.setVisible(true);
                frame.setLocation(null);
                frame.setResizable(false);
            }
        });
    }
}

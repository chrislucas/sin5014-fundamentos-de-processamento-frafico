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
        this.frame.setLayout(this.gridBagLayout);
        this.sliderTransformation = new JSlider(JSlider.VERTICAL, 0, 360, 0);
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

                GridBagConstraints gridBagConstraints = new GridBagConstraints();

                JFrame frame = appGeoTransformation.getFrame();

                Container container = frame.getContentPane();
                Canvas2D canvas2D = new Canvas2D(400, 400);
                canvas2D.setBorder(BorderFactory.createLineBorder(Color.BLUE));
                canvas2D.setBackground(new Color(250,255,230));

                gridBagConstraints.gridx        = 0;
                gridBagConstraints.gridy        = 0;
                gridBagConstraints.gridwidth    = 2;
                gridBagConstraints.gridheight   = 2;

                //gridBagConstraints.weightx      = 0.10;
                //gridBagConstraints.weighty      = 10;
                //gridBagConstraints.anchor     = GridBagConstraints.FIRST_LINE_START;

                gridBagConstraints.fill         = GridBagConstraints.BOTH;

                container.add(canvas2D, gridBagConstraints);

                gridBagConstraints.gridx            = 1;
                gridBagConstraints.gridy            = 1;
                gridBagConstraints.gridwidth        = 3;
                container.add(appGeoTransformation.sliderTransformation, gridBagConstraints);

                frame.setSize(new Dimension(1280,600));
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
                frame.setResizable(true);
            }
        });
    }
}

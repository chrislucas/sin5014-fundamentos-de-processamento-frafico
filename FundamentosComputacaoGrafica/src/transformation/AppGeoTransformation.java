package transformation;

import javax.swing.*;
import java.awt.*;

/**
 * Created by r028367 on 07/04/2017.
 */
public class AppGeoTransformation {

    private JFrame frame;
    private Canvas2D canvas2D;
    private GridBagLayout gridBagLayout;
    private JMenu mainMenu;

    public AppGeoTransformation() {
        this.frame = new JFrame();
        this.frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.gridBagLayout = new GridBagLayout();
        this.frame.setLayout(this.gridBagLayout);
    }


    public JFrame getFrame() {
        return frame;
    }

    private void defineConstraintLayout() {}

    public static void main(String[] args) {

        AppGeoTransformation appGeoTransformation = new AppGeoTransformation();

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {

                GridBagConstraints gridBagConstraints = new GridBagConstraints();

                JFrame frame = appGeoTransformation.getFrame();
                frame.setTitle("Transformação geométrica");

                Container container = frame.getContentPane();
                Canvas2D canvas2D = new Canvas2D(600, 400);
                canvas2D.setBorder(BorderFactory.createLineBorder(Color.BLUE));
                canvas2D.setBackground(new Color(250,255,230));

                gridBagConstraints.gridx        = 0;
                gridBagConstraints.gridy        = 0;
                gridBagConstraints.gridwidth    = 2;
                gridBagConstraints.gridheight   = 2;

                //gridBagConstraints.weightx    = 1;
                //gridBagConstraints.weighty    = 1;
                //gridBagConstraints.anchor     = GridBagConstraints.FIRST_LINE_START;
                //gridBagConstraints.fill       = GridBagConstraints.BOTH;
                container.add(canvas2D, gridBagConstraints);

                gridBagConstraints.gridx            = 0;
                gridBagConstraints.gridy            = 6;
                gridBagConstraints.gridwidth        = 0;
                gridBagConstraints.gridheight       = 2;
                JSlider jSlider = canvas2D.getSliderTransformation();
                container.add(jSlider, gridBagConstraints);

                frame.pack();
                frame.setSize(new Dimension(800, 600));
                frame.setVisible(true);
                frame.setLocationRelativeTo(null);
                frame.setResizable(true);
            }
        });
    }
}

package transformation;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

/**
 * Created by r028367 on 07/04/2017.
 */
public class Canvas2D extends JPanel {

    private JSlider sliderTransformation;

    public static int MIN   = -180;
    public static int MAX   = 180;
    public static int START = 0;

    public Canvas2D(LayoutManager layout) {
        super(layout);
    }

    private int width, height;
    public Canvas2D(int width, int height) {
        this.width = width;
        this.height = height;
        Dimension dimension = new Dimension(width, height);
        super.setPreferredSize(dimension);
        this.sliderTransformation = new JSlider(JSlider.HORIZONTAL, MIN, MAX, START);
        this.sliderTransformation.addChangeListener(changeListenerSlider);
        this.sliderTransformation.setMajorTickSpacing(15);
        this.sliderTransformation.setMinorTickSpacing(1);
        this.sliderTransformation.setPaintTicks(true);
        this.sliderTransformation.setPaintLabels(true);
        this.sliderTransformation.setPreferredSize(new Dimension(800, 100));

    }

    public final ChangeListener changeListenerSlider = new ChangeListener() {
        @Override
        public void stateChanged(ChangeEvent e) {
            JSlider source = (JSlider)e.getSource();
            int degree = (int)source.getValue();
            System.out.println(degree);
        }
    };

    public JSlider getSliderTransformation() {
        return sliderTransformation;
    }

    @Override
    public Dimension getPreferredSize() {
        Dimension d = super.getPreferredSize();
        return d;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}

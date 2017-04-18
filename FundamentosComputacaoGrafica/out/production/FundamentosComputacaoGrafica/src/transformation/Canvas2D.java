package transformation;

import utils.Transformation;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.List;

/**
 * Created by r028367 on 07/04/2017.
 */




import static  java.lang.Math.*;

public class Canvas2D extends JPanel {

    private JSlider sliderTransformation;

    public static int MIN   = -180;
    public static int MAX   = 180;
    public static int START = 180;

    public Canvas2D(LayoutManager layout) {
        super(layout);
    }

    private int width, height;

    public double [][] defaultMatrixLine;
    public double [][] matrix;

    public Canvas2D(int width, int height) {
        this.width = width;
        this.height = height;
        Dimension dimension = new Dimension(width, height);
        super.setPreferredSize(dimension);
        this.sliderTransformation = new JSlider(JSlider.HORIZONTAL, MIN, MAX, START);
        this.sliderTransformation.addChangeListener(changeListenerSlider);
        this.sliderTransformation.setMajorTickSpacing(15);
        this.sliderTransformation.setMinorTickSpacing(5);
        this.sliderTransformation.setPaintTicks(true);
        this.sliderTransformation.setPaintLabels(true);
        this.sliderTransformation.setPreferredSize(new Dimension(800, 60));
        this.sliderTransformation.setBackground(new Color(255, 255, 255));
        this.sliderTransformation.setBorder(BorderFactory.createLineBorder(Color.BLUE));

        defaultMatrixLine = new double[][]{
             {width/2, height/2}
            ,{500, height/2}
        };

        matrix = new double[][] {
             {width/2, height/2}
            ,{500, height/2}
        };
    }

    private void drawLineDefault(Graphics2D g2, double [][] mat) {
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHints(rh);
        int axisYx1 = (int) mat[0][0];
        int axisYy1 = (int) mat[0][1];
        int axisYx2 = (int) mat[1][0];
        int axisYy2 = (int) mat[1][1];
        Line2D line = new Line2D.Double(axisYx1, axisYy1, axisYx2, axisYy2);
        g2.draw(line);
    }

    public void drawPolygon(Graphics2D g2, java.util.List<Point2D> point2DList) {

    }

    public final ChangeListener changeListenerSlider = new ChangeListener() {
        @Override
        public void stateChanged(ChangeEvent e) {
            JSlider source  = (JSlider)e.getSource();
            int degree      = (int)source.getValue();
            //System.out.printf("%f %f %f %f\n", defaultMatrixLine[0][0],defaultMatrixLine[0][1], defaultMatrixLine[1][0], defaultMatrixLine[1][1]);

            // double A [] = Transformation.rotate2D(degree, defaultMatrixLine[0]);
            double B [] = Transformation.rotate2D(degree, defaultMatrixLine[1]);
            double diffX = defaultMatrixLine[0][0] - B[0];
            double diffY = defaultMatrixLine[0][1] - B[1];
            double distance = Math.sqrt((diffX*diffX) + (diffY*diffY));

            // segunda forma
            double diffX2 = matrix[1][0] - matrix[0][0];
            double diffY2 = matrix[1][1] - matrix[0][1];
            double distance2 = floor(sqrt( (diffX2*diffX2) + (diffY2*diffY2)));
            double x = distance2 * floor(cos(Transformation.toRadian(degree)));
            double y = distance2 * floor(sin(Transformation.toRadian(degree)));
            double [] B2 = {defaultMatrixLine[1][0]+x, defaultMatrixLine[1][1]+y};

            double A[] = {defaultMatrixLine[0][0], defaultMatrixLine[0][1]};
            System.out.printf("Distance: %f %f\nGraus: %d\n %f %f %f %f\n"
                    ,distance
                    ,distance2
                    ,degree
                    ,A[0], A[1], B[0], B[1]);

            matrix = new double[][]{
                 {A[0], A[1]}
                ,{B[0], B[1]}
            };
            revalidate();
            repaint();
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
        drawLineDefault((Graphics2D) g, matrix);
    }
}

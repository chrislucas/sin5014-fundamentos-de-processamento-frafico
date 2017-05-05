package views.samples;

import utils.Transformation;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;

/**
 * Created by C.Lucas on 06/04/2017.
 */
public class DrawLine {


    public class Canvas extends JPanel {
        private int width, height;
        @Override
        public Dimension getPreferredSize() {
            Dimension dimension = super.getPreferredSize();
            return dimension;
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            Graphics2D g2 = (Graphics2D) g;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            super.paintComponent(g);
            this.width  = getWidth();
            this.height = getHeight();

            Graphics2D g2 = (Graphics2D) g;
            RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHints(rh);

            // desenhar o eixo Y
            int axisYx1 = 60;
            int axisYy1 = 10;
            int axisYx2 = 100;
            int axisYy2 = 300;
            Line2D line1 = new Line2D.Double(axisYx1, axisYy1, axisYx2, axisYy2);
            //System.out.printf("Eixo Y: P1(%d, %d), P2 (%d, %d)", axisYx1, axisYy1, axisYx2, axisYy2);
            g2.draw(line1);

            double p1 [] = {axisYx1, axisYy1};
            double p2 [] = {axisYx2, axisYy2};
            double newP1 [] = Transformation.rotate2D(90, p1);
            double newP2 [] = Transformation.rotate2D(90, p2);

            Line2D line2 = new Line2D.Double(newP1[0], newP1[1], newP2[0], newP2[1]);
            g2.draw(line2);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("Rotacionar uma linha");
                frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

                Container container = frame.getContentPane();

                DrawLine.Canvas canvas = new DrawLine().new Canvas();
                canvas.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
                canvas.setBackground(new Color(200,230,150));

                container.add(canvas);

                frame.pack();
                frame.setLocationRelativeTo(null);
                Dimension dimension = new Dimension();
                dimension.setSize(600, 400);
                frame.setSize(dimension);
                frame.setVisible(true);
            }
        });
    }

}

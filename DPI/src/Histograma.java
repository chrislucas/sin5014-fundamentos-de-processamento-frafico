import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.Map;

/**
 * Created by C.Lucas on 24/03/2017.
 */
public class Histograma {


    private static final int MIN_H = 400;
    private static final int MIN_W = 600;

    public class CanvasHistogram extends JPanel {
        private static final int C_MIN_H = (int) (MIN_W * 0.6f);;
        private static final int C_MIN_W = (int) (MIN_H * 0.6f);

        private static final int PADDING = 50;

        private int width, height;

        public CanvasHistogram(LayoutManager layout, boolean isDoubleBuffered) {
            super(layout, isDoubleBuffered);
        }

        public CanvasHistogram(LayoutManager layout) {
            super(layout);
        }

        public CanvasHistogram() {
        }

        public CanvasHistogram(Map<Integer, Integer> histogram) {

        }

        @Override
        public Dimension preferredSize() {
            return new Dimension(C_MIN_W, C_MIN_H);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            this.width  = getWidth();
            this.height = getHeight();

            Graphics2D g2 = (Graphics2D) g;

            // https://docs.oracle.com/javase/tutorial/2d/advanced/quality.html
            RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHints(rh);

            // desenhar o eixo x
            int x1 = PADDING;
            int y1 = 10 + PADDING;
            int x2 = PADDING;
            int y2 = height - PADDING;
            Line2D axisX = new Line2D.Double(x1, y1, x2, y2);
            // desenhar o eixo y
            x1 = PADDING;
            y1 = height - PADDING;
            x2 = width - PADDING;
            y2 = height - PADDING;
            Line2D axisY = new Line2D.Double(x1, y1, x2, y2);
            g2.draw(axisX);
            g2.draw(axisY);
        }

    }

    public static void draw() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame editor = new JFrame("HISTOGRAMA");
                editor.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

                Histograma.CanvasHistogram canvas = new Histograma().new CanvasHistogram();
                Dimension d = new Dimension();
                canvas.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
                canvas.setBackground(Color.WHITE);

                Container container = editor.getContentPane();
                //container.setLayout(new GridBagLayout());
                container.add(canvas);


                editor.pack();
                editor.setLocationRelativeTo(null);
                Dimension dimension = new Dimension();
                dimension.setSize(MIN_W, MIN_H);
                editor.setSize(dimension);

                editor.setVisible(true);
            }
        });
    }
}


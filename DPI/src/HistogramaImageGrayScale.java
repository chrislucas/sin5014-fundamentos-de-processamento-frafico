import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.Map;

/**
 * Created by C.Lucas on 24/03/2017.
 */
public class HistogramaImageGrayScale {


    private static final int MIN_H = 400;
    private static final int MIN_W = 600;

    public class CanvasHistogram extends JPanel {
        private static final int C_MIN_H = (int) (MIN_W * 0.6f);;
        private static final int C_MIN_W = (int) (MIN_H * 0.6f);
        private static final int PADDING = 50;
        private int width, height;

        private Label legendX, legendY;
/*
        public CanvasHistogram(LayoutManager layout, boolean isDoubleBuffered) {
            super(layout, isDoubleBuffered);
        }

        public CanvasHistogram(LayoutManager layout) {
            super(layout);
        }
*/
        // public CanvasHistogram() {}

        private Map<Integer, Integer> histogram;


        public CanvasHistogram(Map<Integer, Integer> histogram) {
            this.histogram = histogram;
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
            int axisYx1 = PADDING;
            int axisYy1 = 10 + PADDING;
            int axisYx2 = PADDING;
            int axisYy2 = height - PADDING;
            Line2D axisY = new Line2D.Double(axisYx1, axisYy1, axisYx2, axisYy2);

            int diffX = (axisYx2 - axisYx1);
            int diffY = (axisYy2 - axisYy1);
            // MID POINT Axis X
            int midXaxiY = (axisYx2 + axisYx1) / 2;
            int midYaxiY = (axisYy2 + axisYy1) / 2;
            int distanceAxisX = (int) Math.sqrt( diffX * diffX + diffY * diffY );

            g2.drawString(String.format("%d", distanceAxisX), midXaxiY - 25, midYaxiY);

            // desenhar o eixo y
            int axisXx1 = PADDING;
            int axisXy1 = height - PADDING;
            int axisXx2 = width - PADDING;
            int axisXy2 = height - PADDING;
            Line2D axisX = new Line2D.Double(axisXx1, axisXy1, axisXx2, axisXy2);

            diffX = (axisXx2 - axisXx1);
            diffY = (axisXy2 - axisXy1);
            int distanceAxisY = (int) Math.sqrt( diffX * diffX + diffY * diffY);
            // MID POINT Axis Y
            int midXaxiX = (axisXx2 + axisXx1) / 2;
            int midYaxiX = (axisXy2 + axisXy1) / 2;

            /*
            int midAxisX = distanceAxisX / 2;
            int midAxisY = distanceAxisY / 2;
            */
            g2.drawString(String.format("%d", distanceAxisY), midXaxiX, midYaxiX + 15);



            g2.draw(axisX);
            g2.draw(axisY);
            // aonde eu comeco a desenhar
            int startX          = PADDING + 10      // da um espacamento da bordar mais 10px depois da linha do euxo Y
                ,startY         = (height - PADDING) - 6  // partindo da linha do eixo X
                ,totalColors    = histogram.size()
                ,widthRectangle = distanceAxisX / totalColors;

            for(Map.Entry<Integer, Integer> data : histogram.entrySet()) {
                int colorGrayScale = data.getKey();
                int value = data.getValue();
                int heightRectangle = (value * 100) / distanceAxisY;
                g2.setColor(new Color(colorGrayScale, colorGrayScale, colorGrayScale));

                /**
                 *
                 * Por padrao o java desenha na janela usando coordenada de dispositivos
                 * ou seja, de cima para baixo e da direita para esquerda.
                 * Assim o plano cartesiano é invertido
                 *
                 * largura do retangulo
                 * widthRectangle - startX
                 *
                 * Altura do retangulo
                 * */

                g2.fillRect(startX, 15 , widthRectangle, heightRectangle);
                //g2.drawRect(startX, startY, widthRectangle, startY - heightRectangle);
                // o proximo retangulo começa o valor de X do ultimo retangulo
                startX += widthRectangle;
                //Rectangle2D rect2D = new Rectangle2D.Double(startX, startY, widthRectangle, heightRectangle);
            }
        }

        @Override
        public void updateUI() {
            super.updateUI();
        }
    }



    public static void draw(Map<Integer, Integer> histogram) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame editor = new JFrame("HISTOGRAMA");
                editor.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

                HistogramaImageGrayScale.CanvasHistogram canvas = new HistogramaImageGrayScale().new CanvasHistogram(histogram);
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


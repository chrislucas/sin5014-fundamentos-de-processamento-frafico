import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.util.Map;

/**
 * Created by C.Lucas on 24/03/2017.
 */
public class HistogramImageGrayScale {

    private static final int MIN_H = 800;
    private static final int MIN_W = 1300;

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
            super.setPreferredSize(new Dimension(C_MIN_W, C_MIN_H));
        }

        /**
         * Substituindo metodo obsoleto
         * http://www.math.uni-hamburg.de/doc/java/tutorial/post1.0/converting/deprecatedAWT.html
         * */
        @Override
        public Dimension getPreferredSize() {
            Dimension dimension = super.getPreferredSize();
            return dimension; //new Dimension(C_MIN_W, C_MIN_H);
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            Graphics2D g2 = (Graphics2D) g;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            this.width  = getWidth();
            this.height = getHeight();

            Graphics2D g2 = (Graphics2D) g;
            g2.drawString(String.format("%d %d", width, height), 10, 30);

            // https://docs.oracle.com/javase/tutorial/2d/advanced/quality.html
            RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHints(rh);

            // desenhar o eixo Y
            int axisYx1 = PADDING;
            int axisYy1 = PADDING;
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

            // desenhar o eixo X
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
            int startX          = PADDING + 10              // da um espacamento da bordar mais 10px depois da linha do euxo Y
                ,totalDistinctColors    = histogram.size()
                ,widthRectangle = distanceAxisX / totalDistinctColors;


            long acc = histogram.keySet().stream().count();
            int sumOfPixels = 0;
            // O pixel que tiver a maior quantidade sera a referencia para desenhar as barras
            int maxQuantityPixel = 0;
            for(Map.Entry<Integer, Integer> data : histogram.entrySet()) {
                if(maxQuantityPixel < data.getValue())
                    maxQuantityPixel = data.getValue();
                sumOfPixels += data.getValue();
            }

            for(Map.Entry<Integer, Integer> data : histogram.entrySet()) {
                int colorGrayScale = data.getKey();
                int quantityPixels = data.getValue();
                // quantidade de pixels proporcional a maior quantidade de Pixel encontradas de uma determinada
                // color
                int heightRectangle = (quantityPixels * 100) / maxQuantityPixel;
                // tamanho do retangulo proporcional ao eixo Y
                heightRectangle = heightRectangle * 100 / distanceAxisY;

                if(heightRectangle < 1)
                    continue;
                /**
                 *
                 * Por padrao o java desenha na janela usando coordenada de dispositivos
                 * ou seja, de cima para baixo e da direita para esquerda.
                 * Assim o plano cartesiano é invertido
                 *
                 * largura do retangulo:
                 * distancia do eixo X divido pelo numero de cores distintas encontrado na imagem
                 * widthRectangle = distanceAxisX / totalDistinctColors;
                 *
                 * Distancia entre os retangulos:
                 *
                 * ponto onde comeca o eixo X + a largura do retangulo
                 *
                 * Altura do Retangulo: 2 formular
                 *
                 * Primeiro calculo a quantidade de Pixel de uma determinado Tom proporcional a quantidade
                 * de pixels que tem na imagem, (o resultado sera em porcentagem)
                 *
                 * Para que a altura nao ultrapasse o eixo Y, uso o resultado da formula 1 para calcular
                 * o valor proporcional da altura do retangulo em relacao ao eixo Y
                 *
                 * Formula 1: H = (quantityPixels * 100) / maxQuantityPixel
                 * Formula 2: H = H * 100 / axisY
                 *
                 * Como desenhar o histograma
                 *
                 * Como dito, o computador desenha da esquerda para direita e de cima para baixo
                 * assim temos uma coordenada invertida (quando comparamos com o plano cartesiano que aprendemos na escola)
                 *
                 * Para desenhar as barras utilizei os medotos fillRect e DrawRect, esses metodos recebem como
                 * arugmento o ponto X e Y de inicio a largura que se pretende ter do retangulo e a altura.
                 *
                 * No final o que o Java faz e usar esses argumentos para delimitar 4 pontos
                 * (startX, startY)
                 *
                 *
                 *
                 *
                 * */
                int startY = height - PADDING;
                // definindo a cor do retangulo do histograma
                Color c = new Color(colorGrayScale, colorGrayScale, colorGrayScale);
                g2.setColor(c);
                /**
                 * Preenchendo o Retangulo
                 * */
                g2.fillRect(startX, startY - heightRectangle, widthRectangle, heightRectangle);
                g2.setColor(Color.BLUE);
                g2.drawRect(startX, startY - heightRectangle, widthRectangle, heightRectangle);
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




    private JFrame editor;



    public void draw(Map<Integer, Integer> histogram) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                editor = new JFrame("HISTOGRAMA");
                editor.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

                HistogramImageGrayScale.CanvasHistogram canvas = new HistogramImageGrayScale().new CanvasHistogram(histogram);
                //Dimension d = new Dimension();
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
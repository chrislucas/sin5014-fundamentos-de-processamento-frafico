import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by C.Lucas on 24/03/2017.
 */
public class App {


    private JFrame mainFrame;
    private JMenuBar menuBar;
    private JMenu menuFile, menuAlgorithms, menuFilters;
    private App ref;

    private Map<Integer, Integer> mapGrayScale;
    /**
     * fonte : https://en.wikipedia.org/wiki/Grayscale#Converting_color_to_grayscale
     * http://stackoverflow.com/questions/17615963/standard-rgb-to-grayscale-conversion
     * */
    private int[][] rgbToGrayScale(int [] pixelsImage, int h, int w) {
        mapGrayScale = new HashMap<>();
        int matrix [][] = new int[h][w];
        for(int i=0; i<h; i++){ // linha
            for(int j=0; j<w; j++) {    // coluna
                // i*w+j = num de linhas x qtd colunas + a qtd de linhas ja processadas da matriz
                Color color = new Color(pixelsImage[i*h+j]);
                int r = color.getRed(), g = color.getGreen(), b = color.getBlue();
                int linearization = (int) (r * 0.2126F + g * 0.7152F + b * 0.0722F);
                matrix[i][j] = linearization;
                boolean containColor = mapGrayScale.containsKey(linearization);
                if(containColor) {
                    mapGrayScale.put(linearization, mapGrayScale.get(linearization).intValue() + 1);
                }
                else {
                    mapGrayScale.put(linearization, 1);
                }
            }
        }
        return matrix;
    }

    private void createImageByMatrix(int [][] matrixPixels) {
        //BufferedImage buffer = new BufferedImage();
        int h = matrixPixels.length, w = matrixPixels[0].length;
        int [] pixels = new int[h * w];
        for(int i=0; i<h; i++)
            for (int j=0; j<w; j++)
                pixels[i*h+j] = matrixPixels[i][j];
        WritableRaster raster = bufferedImage.getRaster();
        raster.setPixels(0, 0, w, h, pixels);
    }

    private JMenu addMenuAlgorithms() {
        menuAlgorithms = new JMenu("Algoritmos");
        JMenuItem itemHistogramGrayScale = new JMenuItem("Histograma");
        itemHistogramGrayScale.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(bufferedImage != null) {
                    //drawMockHistogram();
                    int w = bufferedImage.getWidth();
                    int h = bufferedImage.getHeight();
                    int pixelsImage [] = bufferedImage.getRGB(0, 0, w, h, null, 0, w);
                    int matrix[][] = rgbToGrayScale(pixelsImage, h, w);
                    createImageByMatrix(matrix);
                    HistogramImageGrayScale hgs = new HistogramImageGrayScale();
                    hgs.draw(mapGrayScale);
                    System.out.printf("%d %d", w, h);
                }
                else {

                }
            }
        });

        menuFilters = new JMenu("Filtros");
        JMenuItem itemMenuMean          = new JMenuItem("Média");
        itemMenuMean.addActionListener(Filters.filterMean);
        JMenuItem itemMenuMedian        = new JMenuItem("Mediana");
        itemMenuMedian.addActionListener(Filters.filterMedian);

        JMenuItem itemMenuEqualization  = new JMenuItem("Equalização");
        JMenuItem itemMenuPassaAlata    = new JMenuItem("Passa Alta");

        menuFilters.add(itemMenuMean);
        menuFilters.add(itemMenuMedian);
        menuFilters.add(itemMenuEqualization);
        menuFilters.add(itemMenuPassaAlata);

        JMenu menuBorderAlgorithms = new JMenu("Operadores de Borda");
        menuFilters.add(menuBorderAlgorithms);

        menuAlgorithms.add(itemHistogramGrayScale);
        menuAlgorithms.add(menuFilters);

        return menuAlgorithms;
    }

    private BufferedImage bufferedImage;
    private JFileChooser imageChooser;
    private int width, height;
    private static App.ImageCanvas imageCanvas;

    private JMenu addMenuFile() {
        menuFile = new JMenu("File");
        JMenuItem uploadFileMenu = new JMenuItem("Abrir imagem");
        uploadFileMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int re = imageChooser.showOpenDialog(null);
                if(re == JFileChooser.APPROVE_OPTION) {
                    File file = imageChooser.getSelectedFile();
                    try {
                        bufferedImage = ImageIO.read(file);
                        ImageIcon imageIcon     = new ImageIcon(bufferedImage);
                        JLabel imageContainer   = new JLabel();
                        imageContainer.setIcon(imageIcon);

                        //imageCanvas.add(imageContainer);
                        //imageCanvas.repaint();
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                    }
                }
            }
        });
        menuFile.add(uploadFileMenu);
        return menuFile;
    }




    public class ImageCanvas extends JPanel {

        private int widthCanvas, heightCanvas;

        public ImageCanvas(int widthFrame, int heightFrame) {
            this.widthCanvas = widthFrame * 80 / 100;
            this.heightCanvas = heightFrame * 80 / 100;
            Dimension dimension = new Dimension(widthCanvas, heightCanvas);
            super.setPreferredSize(dimension);
        }

        @Override
        public Dimension getPreferredSize() {
            Dimension d = super.getPreferredSize();
            return d;
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
        }
    }

    public static void main(String[] args) {
        final App ref   = new App();
        ref.mainFrame   = new JFrame("Projeto Fundamentos de Computação gráfica");
        ref.mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        ref.width       = 800;
        ref.height      = 600;
        final Dimension dimension = new Dimension(ref.width, ref.height);

        ref.imageChooser = new JFileChooser();
        ref.imageChooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                return false;
            }

            @Override
            public String getDescription() {
                return null;
            }
        });

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ref.menuBar = new JMenuBar();
                ref.mainFrame.setLocationRelativeTo(null);
                ref.menuBar.add( ref.addMenuFile() );
                ref.menuBar.add( ref.addMenuAlgorithms() );

                imageCanvas = new App().new ImageCanvas(ref.width, ref.height);
                imageCanvas.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
                //imageCanvas.setLocation(ref.width/2, ref.height/2);
                imageCanvas.setBackground(new Color(230,230,230));

                Container container = ref.mainFrame.getContentPane();
                //container.setLayout(new GridBagLayout());
                container.add(imageCanvas);

                ref.mainFrame.setJMenuBar(ref.menuBar);
                ref.mainFrame.pack();

                dimension.setSize(dimension);
                ref.mainFrame.setSize(dimension);
                ref.mainFrame.setVisible(true);
                ref.mainFrame.setResizable(false);
            }
        });
    }


    private static void drawMockHistogram() {
        Map<Integer, Integer> histogram = mockHistogramGrayScale();
        HistogramImageGrayScale hgs = new HistogramImageGrayScale();
        hgs.draw(histogram);
    }


    private static Map<Integer, Integer> mockHistogram() {
        Random r = new Random();
        // int randomNum = rand.nextInt((max - min) + 1) + min;
        int min = 2500, max = 100000;
        int acc, totalPixels = r.nextInt(max - min) + min;
        Map<Integer, Integer> histogram = new HashMap<>();
        acc = totalPixels;
        int repeat = 0;
        int maxColor = 255;
        while(acc > 0 && repeat < 2) {
            int colorPixel = r.nextInt(maxColor);
            int quantityPixelsOnImage = r.nextInt(acc) + 1;
            acc -= quantityPixelsOnImage;
            histogram.put(colorPixel, quantityPixelsOnImage);
        }
        return histogram;
    }

    public static Map<Integer, Integer> mockHistogramGrayScale() {
        Random r = new Random();
        // int randomNum = rand.nextInt((max - min) + 1) + min;
        int min = 2500, max = 100000;
        int acc, totalPixels = r.nextInt(max - min) + min;
        Map<Integer, Integer> histogram = new HashMap<>();
        acc = totalPixels;
        for(int i=0; i<(1<<8) && acc > 1; i++) {
            int quantityPixelsOnImage = r.nextInt(acc - 1) + 1;
            if(quantityPixelsOnImage == 0)
                continue;
            histogram.put(i, quantityPixelsOnImage);
            acc -= quantityPixelsOnImage;
        }
        return histogram;
    }
}

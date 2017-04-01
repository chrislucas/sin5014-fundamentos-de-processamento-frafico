import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
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
    private BufferedImage bufferedImage;
    private int widthImage, heightImage;
    private Filters filters;

    /**
     * fonte : https://en.wikipedia.org/wiki/Grayscale#Converting_color_to_grayscale
     * http://stackoverflow.com/questions/17615963/standard-rgb-to-grayscale-conversion
     * */

    /*
    *
    * Formula para passar por um vetor como se fosse uma matriz
    * 2D
    *
    * i=0 ate h
    * j=0 ate w
    * i * w + j     // a linha atual vezes a quantidade de colunas da linha mas a coluna atual
    *
    * */

    private int luminosityMethod(int r, int g, int b) {
        int l = (int) (r * 0.2126F + g * 0.7152F + b * 0.0722F);
        return l;
    }

    private int ligthenessMethod(int r, int g, int b) {
        return ( Math.max(g,Math.max(r,b)) + Math.min(g,Math.min(r,b)) ) / 2;
    }

    private int averageMethod(int r, int g, int b) {
        return (r+g+b) / 3;
    }

    private int [] rgbToGrayScale(int [] pixelsImage, int h, int w) {
        mapGrayScale = new HashMap<>();
        int matrix [] = new int[h * w];
        for(int i=0; i<h; i++){ // linha
            for(int j=0; j<w; j++) {    // coluna
                Color color = new Color(pixelsImage[i*w+j]);
                int r = color.getRed(), g = color.getGreen(), b = color.getBlue();
                int l = averageMethod(r, g, b);
                matrix[i*w+j] = l;
                boolean containColor = mapGrayScale.containsKey(l);
                if(containColor) {
                    mapGrayScale.put(l, mapGrayScale.get(l).intValue() + 1);
                }
                else {
                    mapGrayScale.put(l, 1);
                }
            }
        }
        return matrix;
    }

    private int getIntFromColor(int r, int g, int b){
        r = (r << 16) & 0x00FF0000; //Shift red 16-bits and mask out other stuff
        g = (g << 8) & 0x0000FF00; //Shift Green 8-bits and mask out other stuff
        b = b & 0x000000FF; //Mask out anything not blue.
        return 0xFF000000 | r | g | b; //0xFF000000 for 100% Alpha. Bitwise OR everything together.
    }

    private BufferedImage brightenImage(int pixelsImage[], int constant, int type) {
        BufferedImage buffer = new BufferedImage(widthImage, heightImage, BufferedImage.TYPE_BYTE_GRAY);
        int newPixelsImage [] = new int[pixelsImage.length];
        for(int i=0; i<heightImage; i++) {
            for (int j = 0; j <widthImage; j++) {
                Color color = new Color(pixelsImage[i * widthImage + j]);
                int  r = color.getRed() + constant
                    ,g = color.getGreen() + constant
                    ,b = color.getBlue() + constant;
                r = r > 255 ? 255 : r < 0 ? 0 : r;
                g = g > 255 ? 255 : g < 0 ? 0 : g;
                b = b > 255 ? 255 : b < 0 ? 0 : b;
                newPixelsImage[i * widthImage + j] = averageMethod(r, g, b);
                int c = newPixelsImage[i * widthImage + j];
                buffer.setRGB(j, i, new Color(r, g, b).getRGB());
            }
        }
        createImageGrayScaleByMatrix(newPixelsImage, type);
        updateCanvas(buffer);
        return buffer;
    }

    private void saveFile(BufferedImage buffer, String pathname) {
        File outputFile = new File(pathname);
        if( ! outputFile.exists() ) {
            String pathfile = outputFile.getParent();
            new File(pathfile).mkdirs();
        }
        try {
            ImageIO.write(buffer, "jpg", outputFile);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private BufferedImage createImageGrayScaleByMatrix(int [] matrixPixels, int type) {
        BufferedImage buffer = new BufferedImage(widthImage, heightImage, type);
        for(int i=0; i<heightImage; i++) {
            for (int j=0; j<widthImage; j++) {
                int color = matrixPixels[i*widthImage+j];
                buffer.setRGB(j, i, new Color(color, color, color).getRGB());
            }
        }
        saveFile(buffer, "images/new_image_gray.jpg");
        return buffer;
    }

    private BufferedImage createImageGrayScaleByMatrix(int [][] matrixPixels) {
        BufferedImage buffer = new BufferedImage(widthImage, heightImage, BufferedImage.TYPE_INT_RGB);
        int h = matrixPixels.length, w = matrixPixels[0].length;
        int [] pixels = new int[h * w];
        for(int i=0; i<h; i++) {
            for (int j=0; j<w; j++) {
                int color = matrixPixels[i][j];
                buffer.setRGB(j, i, new Color(color, color, color).getRGB());
            }
        }
        saveFile(buffer, "images/new_image_matrix.jpg");
        return buffer;
    }

    private void buildMenuAlgorithms() {
        filters = new Filters(bufferedImage, widthImage, heightImage);

        menuFilters = new JMenu("Filtros");
        JMenuItem itemMenuMean          = new JMenuItem("Média");
        itemMenuMean.addActionListener(filters.filterMean);
        JMenuItem itemMenuMedian        = new JMenuItem("Mediana");
        itemMenuMedian.addActionListener(filters.filterMedian);

        JMenuItem itemMenuEqualization  = new JMenuItem("Equalização");
        JMenuItem itemMenuPassaAlata    = new JMenuItem("Passa Alta");

        menuFilters.add(itemMenuMean);
        menuFilters.add(itemMenuMedian);
        menuFilters.add(itemMenuEqualization);
        menuFilters.add(itemMenuPassaAlata);

        JMenu menuBorderAlgorithms = new JMenu("Operadores de Borda");
        menuFilters.add(menuBorderAlgorithms);

        JMenu menuBrightness    = new JMenu("Luminosidade");
        JMenuItem brighten      = new JMenuItem("Clarear");
        brighten.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int w = bufferedImage.getWidth();
                int h = bufferedImage.getHeight();
                int pixelsImage []  = bufferedImage.getRGB(0, 0, w, h, null, 0, w);
                bufferedImage       = brightenImage(pixelsImage, 100, BufferedImage.TYPE_INT_RGB);
            }
        });
        JMenuItem darken        = new JMenuItem("Escurecer");
        darken.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int w = bufferedImage.getWidth();
                int h = bufferedImage.getHeight();
                int pixelsImage []  = bufferedImage.getRGB(0, 0, w, h, null, 0, w);
                bufferedImage = brightenImage(pixelsImage, -100, BufferedImage.TYPE_INT_RGB);
            }
        });

        menuBrightness.add(brighten);
        menuBrightness.add(darken);
        menuAlgorithms.add(menuFilters);
        menuAlgorithms.add(menuBrightness);
    }

    private JMenu addMenuAlgorithms() {
        menuAlgorithms = new JMenu("Algoritmos");
        JMenuItem itemHistogramGrayScale = new JMenuItem("Histograma");
        itemHistogramGrayScale.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(bufferedImage != null) {
                    int w = bufferedImage.getWidth();
                    int h = bufferedImage.getHeight();
                    int pixelsImage []  = bufferedImage.getRGB(0, 0, w, h, null, 0, w);
                    int newImage []     = rgbToGrayScale(pixelsImage, h, w);
                    createImageGrayScaleByMatrix(newImage, BufferedImage.TYPE_INT_RGB);
                    HistogramImageGrayScale hgs = new HistogramImageGrayScale();
                    hgs.draw(mapGrayScale);
                    System.out.printf("%d %d", w, h);
                }
                else {}
            }
        });

        menuAlgorithms.add(itemHistogramGrayScale);
        return menuAlgorithms;
    }


    private JFileChooser imageChooser;
    private int width, height;
    private static App.ImageCanvas imageCanvas;

    private void updateCanvas(BufferedImage bufferedImage) {
        ImageIcon imageIcon  = new ImageIcon(bufferedImage);
        int wp = imageCanvas.getWidth() /* 80 / 100*/;
        int hp = imageCanvas.getHeight() /* 80 / 100 */;
        Image image = imageIcon.getImage().getScaledInstance(wp, hp, 100);
        Graphics g = imageCanvas.getGraphics();
        g.drawImage(image, 1, 1, wp, hp, imageCanvas);
/*
        JLabel imageContainer = new JLabel();
        imageContainer.setIcon(imageIcon);
        //imageCanvas.add(imageContainer);
        imageCanvas.add(new JScrollPane(imageContainer));
        imageCanvas.revalidate();
        imageCanvas.repaint();
*/
    }

    private JMenu addMenuFile() {
        menuFile = new JMenu("Arquivo");
        JMenuItem uploadFileMenu = new JMenuItem("Abrir imagem");
        uploadFileMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int re = imageChooser.showOpenDialog(null);
                if(re == JFileChooser.APPROVE_OPTION) {
                    File file = imageChooser.getSelectedFile();
                    try {
                        bufferedImage = ImageIO.read(file);
                        updateCanvas(bufferedImage);
                        widthImage          = bufferedImage.getWidth();
                        heightImage         = bufferedImage.getHeight();
                        int [] pixelsImage  = bufferedImage.getRGB(0, 0, widthImage, heightImage, null, 0, widthImage);
                        buildMenuAlgorithms();
                        int [] T = rgbToGrayScale(pixelsImage, widthImage, heightImage);
                        BufferedImage newBufferImage = createImageGrayScaleByMatrix(T, BufferedImage.TYPE_INT_RGB);
                        //updateCanvas(newBufferImage);
                        //BufferedImage bi = new BufferedImage(widthImage, heightImage,  BufferedImage.TYPE_BYTE_GRAY);
                        // http://stackoverflow.com/questions/14416107/int-array-to-bufferedimage
                        //WritableRaster wr = bi.getRaster();
                        //wr.setPixels(0,0, widthImage, heightImage, T);

                        newBufferImage = filters.applyMask(MaskFilterDefault.passaAlta3, "passaAlta3.jpg");
                        //newBufferImage = filters.applyMeanFilter("meanFilter.jpg");

                        updateCanvas(newBufferImage);


                        /*
                        int matrix [][] = new int[heightImage][widthImage];
                        for(int i=0; i<heightImage; i++)
                            for (int j=0; j<widthImage; j++)
                                matrix[i][j] = bufferedImage.getRGB(j, i);
                        createImageGrayScaleByMatrix(matrix);
*/
                    }
                    catch (Exception ex) {
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
            this.widthCanvas = widthFrame /* * 80 / 100 */;
            this.heightCanvas = heightFrame /*  * 80 / 100 */;
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
        final App ref = new App();

        ref.mainFrame = new JFrame("Projeto Fundamentos de Computação gráfica");
        ref.mainFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
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
                Dimension dimension = new Dimension(1200, 800);
                GridBagLayout gridBagLayout = new GridBagLayout();
                GridBagConstraints gridBagConstraints = new GridBagConstraints();

                ref.menuBar = new JMenuBar();
                ref.mainFrame.setLocationRelativeTo(null);

                ref.menuBar.add( ref.addMenuFile() );
                ref.menuBar.add( ref.addMenuAlgorithms() );

                imageCanvas = new App().new ImageCanvas(dimension.width, dimension.height);
                //imageCanvas.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
                imageCanvas.setBackground(new Color(250,255,230));

                // gridBagConstraints.insets = new Insets(3, 3, 3, 3);

                gridBagConstraints.gridx        = 0;
                gridBagConstraints.gridy        = 0;

                gridBagConstraints.gridwidth    = 0;
                gridBagConstraints.gridheight   = 0;
                gridBagConstraints.weightx      = 0.10;
                gridBagConstraints.weighty      = 10;

                //gridBagConstraints.anchor     = GridBagConstraints.FIRST_LINE_START;
                gridBagConstraints.fill         = GridBagConstraints.BOTH;
                gridBagLayout.setConstraints(imageCanvas, gridBagConstraints);

                Container container = ref.mainFrame.getContentPane();
                container.setLayout(gridBagLayout);
                container.add(imageCanvas, gridBagConstraints);

                ref.mainFrame.setJMenuBar(ref.menuBar);
                ref.mainFrame.pack();

                ref.mainFrame.setSize(dimension);
                ref.mainFrame.setVisible(true);
/*
                Toolkit tk = Toolkit.getDefaultToolkit();
                int xSize = ((int) tk.getScreenSize().getWidth());
                int ySize = ((int) tk.getScreenSize().getHeight());
*/
                ref.mainFrame.setLocationRelativeTo(null);
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

package com.br.editor;

import com.br.editor.utils.CallbackApplyFilter;
import com.br.editor.utils.FiltersToGrayScale;
import com.br.editor.utils.FiltersToRGB;
import com.br.editor.utils.MaskFilterDefault;

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
public class App implements CallbackApplyFilter {

    private JFrame mainFrame;
    private JMenuBar menuBar;
    private JMenu menuFile, menuAlgorithms, menuFilters;
    private App ref;

    private Map<Integer, Integer> mapGrayScale;
    private BufferedImage bufferedImage;
    private int widthImage, heightImage;
    private FiltersToGrayScale filtersToGrayScale;
    private FiltersToRGB filtersToRGB;
    private FiltersToRGB.EqualizationInGrayScale equalizationInGrayScale;

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

    public Map<Integer, Integer> getHistogram(int [] pixelsImage, int h, int w) {
        Map<Integer, Integer> mapGrayScale = new HashMap<>();
        //int matrix [] = new int[h * w];
        for(int i=0; i<h; i++){ // linha
            for(int j=0; j<w; j++) {    // coluna
                Color color = new Color(pixelsImage[i*w+j]);
                int r = color.getRed(), g = color.getGreen(), b = color.getBlue();
                int l = averageMethod(r, g, b);
                //matrix[i*w+j] = l;
                boolean containColor = mapGrayScale.containsKey(l);
                if(containColor) {
                    mapGrayScale.put(l, mapGrayScale.get(l).intValue() + 1);
                }
                else {
                    mapGrayScale.put(l, 1);
                }
            }
        }
        return mapGrayScale;
    }

    public int[] getMatrixHistogramGrayScale(int [] pixelsImage, int h, int w) {
        int [] histogramGrayScale = new int[256];
        for(int i=0; i<h; i++){ // linha
            for(int j=0; j<w; j++) {    // coluna
                Color color = new Color(pixelsImage[i*w+j]);
                int r = color.getRed(), g = color.getGreen(), b = color.getBlue();
                int l = averageMethod(r, g, b);
                l = l > 255 ? 255 : l < 0 ? 0 : l;
                histogramGrayScale[l] += 1;
            }
        }
        return histogramGrayScale ;
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
        updateImageOnCanvas(buffer);
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

    @Override
    public void before(BufferedImage bufferedImage) {
        System.out.println("BEFORE");
    }

    @Override
    public void after(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
        updateImageOnCanvas(bufferedImage);
    }

    private void buildMenuAlgorithms() {
        filtersToGrayScale = new FiltersToGrayScale(this, bufferedImage, widthImage, heightImage);
        // principal
        menuFilters = new JMenu("Filtros");

        JMenu menuGrayScale = new JMenu("Nivel de cinza");

        JMenuItem itemMeanFilter   = new JMenuItem("Média");
        itemMeanFilter.addActionListener(filtersToGrayScale.filterMean);

        JMenuItem itemPassaAltaFilter = new JMenuItem("Passa Alta");
        itemPassaAltaFilter.addActionListener(filtersToGrayScale.filterPassaAlta);

        menuGrayScale.add(itemMeanFilter);
        menuGrayScale.add(itemPassaAltaFilter);

        JMenu menuBrightness    = new JMenu("Luminosidade");
        JMenuItem brighten      = new JMenuItem("Clarear");
        brighten.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int w = bufferedImage.getWidth();
                int h = bufferedImage.getHeight();
                int pixelsImage []  = bufferedImage.getRGB(0, 0, w, h, null, 0, w);
                bufferedImage = brightenImage(pixelsImage, 100, BufferedImage.TYPE_INT_RGB);
            }
        });
        JMenuItem darken = new JMenuItem("Escurecer");
        darken.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int w = bufferedImage.getWidth();
                int h = bufferedImage.getHeight();
                int pixelsImage []  = bufferedImage.getRGB(0, 0, w, h, null, 0, w);
                bufferedImage = brightenImage(pixelsImage, -100, BufferedImage.TYPE_INT_RGB);
            }
        });

        filtersToRGB = new FiltersToRGB(bufferedImage, this);
        JMenu menuRGB = new JMenu("RGB");
        JMenuItem itemLaplacianFilterRGB = new JMenuItem("Laplaciano");
        itemLaplacianFilterRGB.addActionListener(filtersToRGB.laplacianFilter);
        JMenuItem itemPassaAltaFilterRGB = new JMenuItem("PassaAlta");
        itemPassaAltaFilterRGB.addActionListener(filtersToRGB.passaAltaFilter);
        JMenuItem itemGaussianBlue3 = new JMenuItem("Borrão Gaussiano 3x3");
        itemGaussianBlue3.addActionListener(filtersToRGB.gaussianBlue3);

        JMenuItem itemMean9= new JMenuItem("Média 9");
        itemMean9.addActionListener(filtersToRGB.mean9);

        JMenuItem itemMean16= new JMenuItem("Média 16");
        itemMean16.addActionListener(filtersToRGB.mean16);

        JMenuItem itemMedian= new JMenuItem("Mediana");
        itemMedian.addActionListener(filtersToRGB.medianFilter4);

        JMenuItem itemEqualizationFilterRGB = new JMenuItem("Equalizador");
        int h = bufferedImage.getHeight();
        int w = bufferedImage.getWidth();
        int pixelsImage []  = bufferedImage.getRGB(0, 0, w, h, null, 0, w);

        int [] histogram = getMatrixHistogramGrayScale(pixelsImage, h, w);
        equalizationInGrayScale = filtersToRGB.new EqualizationInGrayScale(255, histogram, this);
        itemEqualizationFilterRGB.addActionListener(equalizationInGrayScale);

        menuRGB.add(itemLaplacianFilterRGB);
        menuRGB.add(itemPassaAltaFilterRGB);
        menuRGB.add(itemGaussianBlue3);
        menuRGB.add(itemEqualizationFilterRGB);
        menuRGB.add(itemMean9);
        menuRGB.add(itemMean16);
        menuRGB.add(itemMedian);

        JMenu menuOpBorderDetector = new JMenu("Operadores detecção de borda");
        JMenuItem itemGradientBorderDectectorHorizontal = new JMenuItem("Gradiente Horizontal");
        itemGradientBorderDectectorHorizontal.addActionListener(filtersToRGB.gradientBorderDetectorHorizontal);

        JMenuItem itemGradientBorderDectectorVertical= new JMenuItem("Gradiente Vertical");
        itemGradientBorderDectectorVertical.addActionListener(filtersToRGB.gradientBorderDetectorVertical);

        menuOpBorderDetector.add(itemGradientBorderDectectorHorizontal);
        menuOpBorderDetector.add(itemGradientBorderDectectorVertical);

        menuRGB.add(menuOpBorderDetector);

        menuFilters.add(menuGrayScale);
        menuFilters.add(menuRGB);

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

                    bufferedImage       = createImageGrayScaleByMatrix(newImage, BufferedImage.TYPE_INT_RGB);
                    updateImageOnCanvas(bufferedImage);

                    HistogramImageGrayScale hgs = new HistogramImageGrayScale();
                    hgs.draw(mapGrayScale);
                    System.out.printf("Dimension(%d, %d)\n", w, h);
                }
                else {}
            }
        });
        menuAlgorithms.add(itemHistogramGrayScale);
        return menuAlgorithms;
    }

    private JFileChooser jFileChooserImage;
    private int width, height;
    private static App.ImageCanvas imageCanvas;
    private JLabel imageContainer;

    public final void updateImageOnCanvas(BufferedImage bufferedImage) {
        ImageIcon imageIcon  = new ImageIcon(bufferedImage);
        /*
        int wp      = imageCanvas.getWidth();
        int hp      = imageCanvas.getHeight();
        Image image = imageIcon.getImage().getScaledInstance(wp, hp, 100);
        Graphics g  = imageCanvas.getGraphics();
        g.drawImage(image, 0, 0, wp, hp, imageCanvas);
        */
        this.imageCanvas.remove(imageContainer);
        this.imageContainer.setIcon(imageIcon);
        this.imageCanvas.add(imageContainer);
        //this.imageCanvas.add(new JScrollPane(imageContainer));
        this.imageCanvas.revalidate();
        this.imageCanvas.repaint();
    }

    public void addImageOnCanvas(BufferedImage bufferedImage) {
        ImageIcon imageIcon     = new ImageIcon(bufferedImage);
        this.imageContainer     = new JLabel();
        this.imageContainer.setIcon(imageIcon);
        this.imageCanvas.add(imageContainer);
        //this.imageCanvas.add(new JScrollPane(imageContainer));
        this.imageCanvas.revalidate();
        this.imageCanvas.repaint();
    }

    public static boolean buildOnceMenuFilter = true;

    private void testRGB(BufferedImage bufferedImage) {
        FiltersToRGB filtersToRGB = new FiltersToRGB(bufferedImage);
        filtersToRGB.applyMask(MaskFilterDefault.MaskToRGB.laplacian, "filtro_laplacian", "jpg");
    }

    private JMenu addMenuFile() {
        menuFile = new JMenu("Arquivo");
        JMenuItem uploadFileMenu = new JMenuItem("Abrir imagem");
        uploadFileMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int re = jFileChooserImage.showOpenDialog(null);
                if(re == JFileChooser.APPROVE_OPTION) {
                    File file = jFileChooserImage.getSelectedFile();
                    try {
                        bufferedImage       = ImageIO.read(file);
                        widthImage          = bufferedImage.getWidth();
                        heightImage         = bufferedImage.getHeight();
                        //int [] pixelsImage  = bufferedImage.getRGB(0, 0, widthImage, heightImage, null, 0, widthImage);
                        if(buildOnceMenuFilter) {
                            buildMenuAlgorithms();
                            buildOnceMenuFilter = false;
                            addImageOnCanvas(bufferedImage);
                        }

                        else {
                            updateImageOnCanvas(bufferedImage);
                            /**
                             *
                             * Se o usuario carregar outra image
                             * redefinir a image dos filtros em tom de cinza
                             * dos filtros em RGB
                             * e da matriz de pixels no algoritmo de equalizacao implementado
                             * na classe de filtros em RGB
                             * */
                            filtersToGrayScale.redefineBufferImage(bufferedImage, widthImage, heightImage);
                            filtersToRGB.redefineBufferedImage(bufferedImage);
                            equalizationInGrayScale.redefine(filtersToRGB.getMatrixPixels());

                        }
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
            this.widthCanvas = widthFrame;
            this.heightCanvas = heightFrame;
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
        ref.jFileChooserImage = new JFileChooser(new File("images/"));
        ref.jFileChooserImage.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                if(f.isDirectory())
                    return true;
                String name = f.getName();
                boolean rs = name.endsWith(".png") || name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".JPG") || name.endsWith(".bmp");
                return rs ;
            }

            @Override
            public String getDescription() {
                return "*.png,*.jpg,,*.JPG,*.jpeg,*.bmp";
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
                imageCanvas.setBorder(BorderFactory.createLineBorder(Color.BLUE));
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

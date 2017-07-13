package views;

import app.tests.FloodFillFacade;
import oracle.jrockit.jfr.JFR;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import utils.BufferedImageUtils;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

/**
 * Created by C_Luc on 23/06/2017.
 */
public class UIFloodFill {
    private static final String colorString = "Color";
    private static final String grayscaleString = "Grayscale";
    private static final String onMaskString = "On";
    private static final String offMaskString = "Off";
    private static final String nullRangeString = "Null";
    private static final String fixedRangeString = "Fixed (seed)";
    private static final String floatingRangeString = "Floating (relative)";
    private static final String fourConnectivityString = "4-Connectivity";
    private static final String eightConnectivityString = "8-Connectivity";
    private JLabel imageView;
    private JLabel maskView;
    private String windowName;
    private Mat originalImage;
    private Mat image;
    private Mat grayImage   = new Mat();
    private Mat mask        = new Mat();


    private String colorMode        = colorString;
    private String maskFlag         = onMaskString;
    private String rangeType        = fixedRangeString;
    private String connectivityMode = fourConnectivityString;
    private FloodFillFacade floodFillFacade = new FloodFillFacade();
    private int lower = 20;
    private int upper = 20;

    public UIFloodFill(Mat image, String windowName) {
        this.windowName = windowName;
        this.image      = image;
        this.originalImage   = image.clone();
        // criando a matriz mascara
        this.mask.create(new Size(image.cols()+2, image.rows()+2), CvType.CV_8UC1);
        // preencher a mascara com 0
        this.mask.setTo(new Scalar(0));
        //System.out.println(mask.dump());
        // convertendo a imagem em cinza
        Imgproc.cvtColor(image, grayImage, Imgproc.COLOR_BGR2GRAY);
        processOperation();
    }

    public void init() {
        setSystenLookAndFeel();
        JFrame frame = build("Flood Fill OpenCV");
        updateView();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }

    private JFrame build(String title) {
        JFrame frame = new JFrame(title);
        frame.setLayout(new GridBagLayout());
        setupColorRadio(frame);
        setupMaskRadio(frame);
        setupRangeRadio(frame);
        setupConnectivityRadio(frame);
        setupLowerSlider(frame);
        setupUpperSlider(frame);
        setupResetButton(frame);
        setupImage(frame);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        return frame;
    }

    private void setSystenLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            printExcpMessage(e);
        } catch (InstantiationException e) {
            printExcpMessage(e);
        } catch (IllegalAccessException e) {
            printExcpMessage(e);
        } catch (UnsupportedLookAndFeelException e) {
            printExcpMessage(e);
        }
    }

    private void printExcpMessage(Exception e) {
        String message = String.format("Error: %s\n%s\n%s", e.getCause()
                , e.getMessage(), e.getLocalizedMessage());
        System.out.println(message);
    }

    private void resetImage() {
        image = originalImage.clone();
        mask.setTo(new Scalar(0));
        Imgproc.cvtColor(originalImage, grayImage,Imgproc.COLOR_BGR2GRAY);
        updateView();
    }

    private void setupResetButton(JFrame frame) {
        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                resetImage();
            }
        });
        resetButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        GridBagConstraints c = new GridBagConstraints();
        //c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 6;

        JLabel warning = new JLabel("Click on the image!", JLabel.CENTER);
        warning.setFont(new Font("Serif", Font.PLAIN, 18));
        frame.add(warning,c);

        c.gridx = 1;
        c.gridy = 6;
        frame.add(resetButton,c);
    }

    private void setupLowerSlider(JFrame frame) {
        JLabel sliderLabel = new JLabel("Lower threshold:", JLabel.RIGHT);
        int minimum = 0;
        int maximum = 255;
        int initial =20;
        JSlider lowerSlider = new JSlider(JSlider.HORIZONTAL,
                minimum, maximum, initial);
        lowerSlider.setMajorTickSpacing(25);
        lowerSlider.setMinorTickSpacing(5);
        lowerSlider.setPaintTicks(true);
        lowerSlider.setPaintLabels(true);
        lowerSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider)e.getSource();
                lower = (int)source.getValue();
                processOperation();
            }
        });

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;

        c.gridx = 0;
        c.gridy = 4;
        frame.add(sliderLabel,c);

        c.gridx = 1;
        c.gridy = 4;
        frame.add(lowerSlider,c);
    }

    private void setupUpperSlider(JFrame frame) {
        JLabel sliderLabel = new JLabel("Upper threshold:", JLabel.RIGHT);
        int minimum = 0;
        int maximum = 255;
        int initial =20;

        JSlider upperSlider = new JSlider(JSlider.HORIZONTAL,
                minimum, maximum, initial);

        upperSlider.setMajorTickSpacing(25);
        upperSlider.setMinorTickSpacing(5);
        upperSlider.setPaintTicks(true);
        upperSlider.setPaintLabels(true);
        upperSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider)e.getSource();
                upper = (int)source.getValue();
                processOperation();
            }
        });


        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;

        c.gridx = 0;
        c.gridy = 5;
        frame.add(sliderLabel,c);

        c.gridx = 1;
        c.gridy = 5;
        frame.add(upperSlider,c);
    }


    private void setupConnectivityRadio(JFrame frame) {
        JRadioButton fourConnected = new JRadioButton(fourConnectivityString);
        fourConnected.setMnemonic(KeyEvent.VK_4);
        fourConnected.setActionCommand(fourConnectivityString);
        fourConnected.setSelected(true);

        JRadioButton eightConnected = new JRadioButton(eightConnectivityString);
        eightConnected.setMnemonic(KeyEvent.VK_L);
        eightConnected.setActionCommand(eightConnectivityString);
        eightConnected.setSelected(false);


        ButtonGroup group = new ButtonGroup();
        group.add(fourConnected);
        group.add(eightConnected);

        ActionListener operationChangeListener = new ActionListener() {

            public void actionPerformed(ActionEvent event) {
                connectivityMode  = event.getActionCommand();
                processOperation();
            }
        };

        fourConnected.addActionListener(operationChangeListener);
        eightConnected.addActionListener(operationChangeListener);

        GridLayout gridRowLayout = new GridLayout(1,0);
        JPanel radioOperationPanel = new JPanel(gridRowLayout);

        JLabel rangeLabel = new JLabel("Connectivity:", JLabel.RIGHT);

        radioOperationPanel.add(fourConnected);
        radioOperationPanel.add(eightConnected);

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;

        c.gridx = 0;
        c.gridy = 3;
        frame.add(rangeLabel,c);

        c.gridx = 1;
        c.gridy = 3;
        frame.add(radioOperationPanel,c);
    }

    private void setupRangeRadio(JFrame frame) {
        JRadioButton nullRangeButton = new JRadioButton(nullRangeString);
        nullRangeButton.setMnemonic(KeyEvent.VK_N);
        nullRangeButton.setActionCommand(nullRangeString);
        nullRangeButton.setSelected(false);

        JRadioButton fixedRangeButton = new JRadioButton(fixedRangeString);
        fixedRangeButton.setMnemonic(KeyEvent.VK_I);
        fixedRangeButton.setActionCommand(fixedRangeString);
        fixedRangeButton.setSelected(true);

        JRadioButton floatingRangeButton = new JRadioButton(floatingRangeString);
        floatingRangeButton.setMnemonic(KeyEvent.VK_L);
        floatingRangeButton.setActionCommand(floatingRangeString);
        floatingRangeButton.setSelected(false);

        ButtonGroup group = new ButtonGroup();
        group.add(nullRangeButton);
        group.add(fixedRangeButton);
        group.add(floatingRangeButton);

        ActionListener operationChangeListener = new ActionListener() {

            public void actionPerformed(ActionEvent event) {
                rangeType  = event.getActionCommand();
                processOperation();
            }
        };

        nullRangeButton.addActionListener(operationChangeListener);
        fixedRangeButton.addActionListener(operationChangeListener);
        floatingRangeButton.addActionListener(operationChangeListener);


        GridLayout gridRowLayout = new GridLayout(1,0);
        JPanel radioOperationPanel = new JPanel(gridRowLayout);

        JLabel rangeLabel = new JLabel("Range:", JLabel.RIGHT);

        radioOperationPanel.add(nullRangeButton);
        radioOperationPanel.add(fixedRangeButton);
        radioOperationPanel.add(floatingRangeButton);

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;

        c.gridx = 0;
        c.gridy = 2;
        frame.add(rangeLabel,c);

        c.gridx = 1;
        c.gridy = 2;
        frame.add(radioOperationPanel,c);
    }

    private void setupMaskRadio(JFrame frame){

        JRadioButton onButton = new JRadioButton(onMaskString);
        onButton.setMnemonic(KeyEvent.VK_O);
        onButton.setActionCommand(onMaskString);
        onButton.setSelected(true);

        JRadioButton offButton = new JRadioButton(offMaskString);
        offButton.setMnemonic(KeyEvent.VK_F);
        offButton.setActionCommand(offMaskString);
        offButton.setSelected(false);


        ButtonGroup group = new ButtonGroup();
        group.add(onButton);
        group.add(offButton);

        ActionListener operationChangeListener = new ActionListener() {

            public void actionPerformed(ActionEvent event) {
                maskFlag = event.getActionCommand();
                processOperation();
            }
        };

        onButton.addActionListener(operationChangeListener);
        offButton.addActionListener(operationChangeListener);

        GridLayout gridRowLayout = new GridLayout(1,0);
        JPanel radioOperationPanel = new JPanel(gridRowLayout);

        JLabel maskLabel = new JLabel("Mask:", JLabel.RIGHT);

        radioOperationPanel.add(onButton);
        radioOperationPanel.add(offButton);


        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;

        c.gridx = 0;
        c.gridy = 1;


        frame.add(maskLabel,c);

        c.gridx = 1;
        c.gridy = 1;

        frame.add(radioOperationPanel,c);

    }

    private void setupColorRadio(JFrame frame) {
        JRadioButton colorButton = new JRadioButton(colorString);
        colorButton.setMnemonic(KeyEvent.VK_C);
        colorButton.setActionCommand(colorString);
        colorButton.setSelected(true);

        JRadioButton grayscaleButton = new JRadioButton(grayscaleString);
        grayscaleButton.setMnemonic(KeyEvent.VK_G);
        grayscaleButton.setActionCommand(grayscaleString);

        ButtonGroup group = new ButtonGroup();
        group.add(colorButton);
        group.add(grayscaleButton);

        ActionListener operationChangeListener = new ActionListener() {

            public void actionPerformed(ActionEvent event) {
                colorMode = event.getActionCommand();
                processOperation();
                resetImage();
            }
        };

        colorButton.addActionListener(operationChangeListener);
        grayscaleButton.addActionListener(operationChangeListener);

        GridLayout gridRowLayout    = new GridLayout(1,0);
        JPanel radioOperationPanel  = new JPanel(gridRowLayout);
        JLabel modeLabel            = new JLabel("Mode:", JLabel.RIGHT);

        radioOperationPanel.add(colorButton);
        radioOperationPanel.add(grayscaleButton);

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;

        c.gridx = 0;
        c.gridy = 0;


        frame.add(modeLabel,c);

        c.gridx = 1;
        c.gridy = 0;

        frame.add(radioOperationPanel,c);

    }

    /**
     * Definindo action listener na imagem original
     * */
    private void setupImage(JFrame frame) {
        imageView   = new JLabel();
        maskView    = new JLabel();
        imageView.addMouseListener(new MouseAdapter()
        {
            public void mousePressed(MouseEvent e)
            {
                if(colorString.equals(colorMode)){
                    floodFillFacade.fill(image, mask, e.getX(), e.getY());
                }
                else{
                    floodFillFacade.fill(grayImage, mask, e.getX(), e.getY());
                }
                updateView();
            }
        });
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 7;
        c.gridwidth = 1;

        frame.add(new JLabel("Image", JLabel.CENTER),c);
        c.gridx = 1;

        frame.add(new JLabel("Mask", JLabel.CENTER),c);
        c.gridy = 8;
        c.gridx = 0;

        frame.add(imageView,c);
        c.gridx = 1;
        frame.add(maskView,c);
    }

    private void setSystemLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }

    private void updateView() {
        Mat output          = grayscaleString.equals(colorMode)?grayImage:image;
        Image outputImage   = BufferedImageUtils.toBufferedImage(output);
        Image maskImage     = BufferedImageUtils.toBufferedImage(mask);
        imageView.setIcon(new ImageIcon(outputImage));
        maskView.setIcon(new ImageIcon(maskImage));
    }

    private void processOperation() {
        floodFillFacade.setColored(colorMode.equals(colorString));
        floodFillFacade.setMasked(maskFlag.equals(onMaskString));
        if(rangeType.equals(nullRangeString)){
            floodFillFacade.setRange(FloodFillFacade.NULL_RANGE);
        }
        else if(rangeType.equals(fixedRangeString)){
            floodFillFacade.setRange(FloodFillFacade.FIXED_RANGE);
        }
        else if(rangeType.equals(floatingRangeString)){
            floodFillFacade.setRange(FloodFillFacade.FLOATING_RANGE);
        }
        floodFillFacade.setConnectivity(fourConnectivityString.equals(connectivityMode)?4:8);
        floodFillFacade.setLowerDiff(lower);
        floodFillFacade.setUpperDiff(upper);
    }

}

package views;

import javax.swing.*;
import java.awt.*;

/**
 * Created by C.Lucas on 21/05/2017.
 */
public class AppMain extends JFrame {

    private Dimension dimension;
    private CanvasImage canvasImage;


    public AppMain(String title, Dimension dimension) throws HeadlessException  {
        super(title);
        this.dimension = dimension;
        this.setSize(dimension);
    }


    public class CanvasImage extends JPanel {
        private int width, height;
        private Dimension dimension;
        public CanvasImage(int w, int h) {
            this.width = w;
            this.height = h;
        }

        @Override
        public Dimension getPreferredSize() {
            this.dimension = super.getPreferredSize();
            return dimension;
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
        }
    }

    public Dimension getDimension() {
        return dimension;
    }

    public void setDimension(Dimension dimension) {
        this.dimension = dimension;
    }

    public static void main(String[] args) {
        Dimension d = new Dimension(600,400);
        final AppMain frameAppMain = new AppMain("Projeto OPENCV", d);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {


                GridBagLayout gridBagLayout = new GridBagLayout();

                int w = frameAppMain.getWidth() / 3, h = frameAppMain.getHeight() / 3;
                CanvasImage canvasImage = frameAppMain.new CanvasImage(w, h);
                canvasImage.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                canvasImage.setBackground(Color.gray);


                Container container = frameAppMain.getContentPane();
                container.add(canvasImage);

                frameAppMain.pack();

                frameAppMain.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                frameAppMain.setLocationRelativeTo(null);
                frameAppMain.setVisible(true);
                frameAppMain.setResizable(true);
            }
        });
    }
}

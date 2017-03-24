import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;

/**
 * Created by r028367 on 22/03/2017.
 */
public class App {

    public static void main(String[] args) {
        open("images/Desert.jpg");
    }

    public static void open(String pathfile) {
        System.out.println(getBasePath(App.class));
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame editor = new JFrame("Processamento Gráfico");
                editor.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                BufferedImage bufferImage = null;
                try {
                    bufferImage = ImageIO.read(new File(pathfile));
                    ImageIcon imageIcon = new ImageIcon(bufferImage);
                    JLabel imageContainer = new JLabel();
                    imageContainer .setIcon(imageIcon);
                    editor.getContentPane().add(imageContainer, BorderLayout.CENTER);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                editor.pack();
                editor.setLocationRelativeTo(null);
                Dimension dimension = new Dimension();
                dimension.setSize(300, 300);
                editor.setSize(dimension);
                //editor.setResizable(false);
                editor.setVisible(true);
            }
        });
    }


    public static final String getBasePath(Class<?> clazz) {
        URL url = clazz.getProtectionDomain().getCodeSource().getLocation();
        return url.getPath();
    }
}

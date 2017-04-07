package transformation;

import javax.swing.*;
import java.awt.*;

/**
 * Created by r028367 on 07/04/2017.
 */
public class Canvas2D extends JPanel {

    public Canvas2D(LayoutManager layout) {
        super(layout);
    }

    private int width, height;
    public Canvas2D(int width, int height) {
        this.width = width;
        this.height = height;
        Dimension dimension = new Dimension(width, height);
        super.setPreferredSize(dimension);
    }

    @Override
    public Dimension getPreferredSize() {
        Dimension d = super.getPreferredSize();
        return d;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}

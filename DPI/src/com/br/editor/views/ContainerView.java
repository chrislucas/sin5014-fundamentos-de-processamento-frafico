package com.br.editor.views;

import javax.swing.*;
import java.awt.*;

/**
 * Created by C.Lucas on 02/04/2017.
 */
public class ContainerView extends JFrame {

    private JPanel jPanel;
    private JFrame mainFrame;
    public ContainerView(JPanel jPanel) throws HeadlessException {
        this.jPanel    = jPanel;
        this.mainFrame = this;
        this.mainFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    public void init() {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                Toolkit tk = Toolkit.getDefaultToolkit();
                int xSize = ((int) tk.getScreenSize().getWidth());
                int ySize = ((int) tk.getScreenSize().getHeight());

                Container container = mainFrame.getContentPane();
                container.add(jPanel);

                mainFrame.pack();
                mainFrame.setSize(xSize, ySize);
                mainFrame.setVisible(true);
                mainFrame.setLocationRelativeTo(null);
                mainFrame.setResizable(false);
            }
        };
        SwingUtilities.invokeLater(r);
    }
}

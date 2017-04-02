package com.br.editor.views;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by C.Lucas on 02/04/2017.
 */
public class CanvasImageResult extends JPanel {

    private BufferedImage bufferedImageBefore,  bufferedImageAfter;
    private JLabel imageAfter, imageBefore, histogramAfter, histogramBefore;

    public CanvasImageResult(LayoutManager layout, boolean isDoubleBuffered, BufferedImage bufferedImageBefore, BufferedImage bufferedImageAfter) {
        super(layout, isDoubleBuffered);
        this.bufferedImageBefore = bufferedImageBefore;
        this.bufferedImageAfter = bufferedImageAfter;
    }

    public CanvasImageResult(LayoutManager layout, BufferedImage bufferedImageBefore, BufferedImage bufferedImageAfter) {
        super(layout);
        this.bufferedImageBefore = bufferedImageBefore;
        this.bufferedImageAfter = bufferedImageAfter;
    }

    public CanvasImageResult(boolean isDoubleBuffered, BufferedImage bufferedImageBefore, BufferedImage bufferedImageAfter) {
        super(isDoubleBuffered);
        this.bufferedImageBefore = bufferedImageBefore;
        this.bufferedImageAfter = bufferedImageAfter;
    }

    public CanvasImageResult(BufferedImage bufferedImageBefore, BufferedImage bufferedImageAfter) {
        this.bufferedImageBefore = bufferedImageBefore;
        this.bufferedImageAfter = bufferedImageAfter;
    }


    @Override
    public Dimension getPreferredSize() {
        return null;
    }
}

package com.br.editor.views.tests;

import com.br.editor.views.CanvasImageResult;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by C.Lucas on 02/04/2017.
 */
public class ExamplelayoutImageCompare {

    private BufferedImage before, after;

    public ExamplelayoutImageCompare(BufferedImage before, BufferedImage after) {
        this.before = before;
        this.after = after;
    }

    private void init() {
        GridBagLayout gridBagLayout = new GridBagLayout();
        CanvasImageResult canvasImageResult = new CanvasImageResult(gridBagLayout, before, after);
    }

}

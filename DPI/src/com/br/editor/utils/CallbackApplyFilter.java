package com.br.editor.utils;

import java.awt.image.BufferedImage;

/**
 * Created by r028367 on 31/03/2017.
 */
public interface CallbackApplyFilter {
    public void before(BufferedImage bufferedImage);
    public void after(BufferedImage bufferedImage);
}

package com.br.editor.views.menus;


import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.util.List;
import java.util.Map;

/**
 * Created by C.Lucas on 14/05/2017.
 */
public abstract class BuilderMenu {

    protected JMenu main;
    protected Map<JMenuItem, List<JMenuItem>> structureMenu;

    public BuilderMenu(JMenu main) {
        this.main = main;
    }

}

package tetris;

import java.awt.BorderLayout;
import javax.swing.JFrame;

public class PanelCreator {
    public Grid createGrid(JFrame tetrisUI) {
        // Plaats het veld op het scherm
        Grid tetrisGrid = new Grid();
        tetrisUI.getContentPane().add(tetrisGrid, BorderLayout.CENTER);
        tetrisUI.setFocusable(true);
        tetrisUI.requestFocusInWindow();
        return tetrisGrid;
    }

    public Sidebar createSidebar(JFrame tetrisUI) {
        System.out.println("hallo van panel creator");
        Sidebar tetrisSidebar = new Sidebar();
        tetrisUI.getContentPane().add(tetrisSidebar.sidePanel, BorderLayout.EAST);
        tetrisSidebar.sidePanel.setFocusable(false);
        return tetrisSidebar;
    }

    public Menu createMenu(JFrame tetrisUI) {
        Menu tetrisMenu = new Menu();
        tetrisUI.setJMenuBar(tetrisMenu.bar);
        return tetrisMenu;
    }
}

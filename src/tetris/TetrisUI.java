/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris;

import javax.swing.JOptionPane.*;
import java.awt.event.KeyEvent.*;
import java.awt.Frame.*;
import javax.swing.*;
import java.awt.event.*;

/**
 *
 * @author vdb
 */
public class TetrisUI {

  private JFrame gameUI;
  private Sidebar tetrisSidebar;
  private PanelCreator panelCreator;
  private Menu tetrisMenu;

  public void GUI() {
    gameUI = new JFrame("Java Tetris Game!");

    gameUI.addWindowListener(new WindowAdapter() {

      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });
    panelCreator = new PanelCreator();
    Grid tetrisGrid;
//                Sidebar tetrisSidebar;

    gameUI.setLocation(100, 100);
    gameUI.setSize(446, 603);
    gameUI.setTitle("Java Tetris Game!!!");

    tetrisMenu = panelCreator.createMenu(gameUI);
    tetrisSidebar = panelCreator.createSidebar(gameUI);
    tetrisGrid = panelCreator.createGrid(gameUI);
    tetrisGrid.addNewBlockEventListener(tetrisSidebar);
    
    tetrisSidebar.addNewStartgameEventListener(tetrisGrid);
    tetrisSidebar.addNewPausegameEventListener(tetrisGrid);
    tetrisSidebar.addNewHighscoreEventListener(tetrisGrid);
//    tetrisMenu.addNewHighscoreEventListener(tetrisGrid);
    
    tetrisGrid.addNewScoreEventListener(tetrisSidebar);
    tetrisGrid.addNewGameoverEventListener(tetrisSidebar);
    

    gameUI.addKeyListener(tetrisGrid);

    gameUI.setVisible(true);
  }

  public static void main(String[] args) {
    TetrisUI tetrisUI = new TetrisUI();
    tetrisUI.start();
  }

  public void start() {
    System.out.println("hallo van Main");
    TetrisUI.this.GUI();
  }
}

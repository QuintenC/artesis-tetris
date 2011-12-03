/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tetris;

import javax.swing.JOptionPane.*;
import java.awt.event.KeyEvent.*;
import java.awt.Frame.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
/**
 *
 * @author vdb
 */
public class TetrisUI {

    public static JFrame gameUI;
    public static Sidebar tetrisSidebar;
    public static PanelCreator panelCreator;

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
                Menu tetrisMenu;



                gameUI.setLocation(100,100);
                gameUI.setSize(446,603);
                gameUI.setTitle("Java Tetris Game!!!");
                gameUI.setVisible(true);

                tetrisGrid = panelCreator.createGrid(gameUI);
                tetrisSidebar = panelCreator.createSidebar(gameUI);
                tetrisMenu = panelCreator.createMenu(gameUI);
                
                gameUI.addKeyListener(tetrisGrid);


//                gameUI.pack();
            }



    public static void main(String[] args) {
        TetrisUI tetrisUI = new TetrisUI();
        tetrisUI.start();
    }

    public void start(){
        SwingUtilities.invokeLater(new Runnable() {
           public void run() {
               System.out.println("hallo");
               TetrisUI.this.GUI();
           }
        });
    }
}


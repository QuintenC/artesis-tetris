/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris;

import javax.swing.*;
import tetris.events.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 *
 * @author vdb
 */
public class Menu extends JFrame {

  JMenuBar bar;

  public Menu() {
    JMenu file = new JMenu("File");
    file.setMnemonic('F');

    JMenuItem AboutTetris = new JMenuItem("About Tetris v1");
    AboutTetris.setMnemonic('A');
    file.add(AboutTetris);

    file.addSeparator();
    JMenuItem minItem = new JMenuItem("Minimize");
    minItem.setMnemonic('M');
    file.add(minItem);

//                        JMenuItem maxItem = new JMenuItem("Maximize");
//                        maxItem.setMnemonic('X');
//                        file.add(maxItem);

    file.addSeparator();

    JMenuItem quitItem = new JMenuItem("Quit Tetris");
    quitItem.setMnemonic('Q');
    file.add(quitItem);

    JMenu game = new JMenu("Game");
    game.setMnemonic('G');

    JMenuItem highscores = new JMenuItem("Highscores");
    highscores.setMnemonic('I');
    game.add(highscores);

    JMenuItem help = new JMenuItem("Help");
    help.setMnemonic('H');
    game.add(help);

    minItem.addActionListener(
            new ActionListener() {

              public void actionPerformed(ActionEvent e) {
                System.out.println("Minimize is pressed");
              }
            });

    highscores.addActionListener(
            new ActionListener() {

              @Override
              public void actionPerformed(ActionEvent e) {
//                this.fireNewHighscoreEvent(new NewHighscoreEvent(this));
              }

//              private void fireNewHighscoreEvent(NewHighscoreEvent evt) {
//                Object[] listeners = listenerList.getListenerList();
//                int listenerCount = listeners.length;
//
//                for (int i = 0; i < listenerCount; i += 2) {
//                  if (listeners[i] == NewHighscoreEventListener.class) {
//                    ((NewHighscoreEventListener) listeners[i + 1]).newHighscoreEventOccurred(evt);
//                  }
//                }
//              }
            });
    quitItem.addActionListener(
            new ActionListener() {

      @Override
              public void actionPerformed(ActionEvent e) {
                System.out.println("Quit is pressed");
                System.exit(0);
              }
            });

    help.addActionListener(
            new ActionListener() {

      @Override
              public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Tetris\n\nControls:"
                        + "\n  Up Arrow: Rotates the block."
                        + "\n  Down Arrow: Drops the block."
                        + "\n  Left and Right arrows: Moves the block left and right."
                        + "\n\nGoal:"
                        + "\n  The goal of the game is to fill horizontal lines with the random blocks that fall down. "
                        + "\n  For every block you put down you will recieve 5 points, "
                        + "\n  if you drop a block you will 2 recieve points for each step it's dropped and "
                        + "\n  for each horizontal line you fill you will recieve 50 points."
                        + "\n\n  With every 200 points you score the level will be increased and the blocks will fall faster",
                        "Help", JOptionPane.INFORMATION_MESSAGE);
              }
            });
    AboutTetris.addActionListener(
            new ActionListener() {

      @Override
              public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Written by Quinten Claes\n\nArtesis Hogeschool Antwerpen"
                        + "\n2IWEA groep 1 \n\nLab Softwareontwikkeling\nOpdracht : Java-toepassing\n\nLast update : Tuesday 12th december, 2011\n                          18:00 PM",
                        "About Tetris v1", JOptionPane.INFORMATION_MESSAGE);
              }
            });

    bar = new JMenuBar();
    setJMenuBar(bar);
    bar.add(file);
    bar.add(game);

  }

//  public void addNewHighscoreEventListener(NewHighscoreEventListener listener) {
//    listenerList.add(NewHighscoreEventListener.class, listener);
//  }
}

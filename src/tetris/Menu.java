/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tetris;

import javax.swing.*;
import javax.swing.JFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author vdb
 */
public class Menu extends JFrame{
    JMenuBar bar;

    public Menu(){
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

                        JMenuItem newgame = new JMenuItem("New game");
                        newgame.setMnemonic('N');
                        game.add(newgame);

                        JMenuItem pausegame = new JMenuItem("Pause game");
                        pausegame.setMnemonic('P');
                        game.add(pausegame);

                        JMenuItem stopgame = new JMenuItem("Stop game");
                        stopgame.setMnemonic('S');
                        game.add(stopgame);

                        game.addSeparator();

                        JMenuItem highscores = new JMenuItem("Highscores");
                        highscores.setMnemonic('I');
                        game.add(highscores);

                        JMenuItem help = new JMenuItem("Help");
                        help.setMnemonic('H');
                        game.add(help);

		minItem.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent e)
				{
					System.out.println("Minimize is pressed");
				}
			}
		);
//		maxItem.addActionListener(
//			new ActionListener(){
//				public void actionPerformed(ActionEvent e)
//				{
//					System.out.println("Maximize is pressed");
//				}
//			}
//		);
                highscores.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent e)
				{
					System.out.println("Highscore is pressed");
				}
			}
		);
		quitItem.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent e)
				{
					System.out.println("Quit is pressed");
                                        System.exit(0);
				}
			}
		);
                newgame.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent e)
				{
					System.out.println("New is pressed");
				}
			}
		);
                pausegame.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent e)
				{
					System.out.println("Pause is pressed");
				}
			}
		);
                stopgame.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent e)
				{
					System.out.println("Stop is pressed");
				}
			}
		);
                help.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent e)
				{
					System.out.println("Help is pressed");
				}
			}
		);
                AboutTetris.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent e)
				{
					System.out.println("About is pressed");
				}
			}
		);

		bar = new JMenuBar();
		setJMenuBar(bar);
		bar.add(file);
                bar.add(game);

    }
}

package tetris;

import java.awt.*;
import javax.swing.*;


public class Sidebar extends JPanel {

    JPanel sidePanel;
    static String block = "Lblock";
    static int lines;
    static int score;

    public Sidebar(){
    System.out.println("new panel created, new block is " + block);
    sidePanel = new JPanel();
    sidePanel.setBackground(Color.gray);
    sidePanel.setPreferredSize(new Dimension(130, 603));
//    sidePanel.setLayout(new GridLayout(8,1));
    sidePanel.setBorder(BorderFactory.createTitledBorder("Tetris"));

    JLabel NextBlock = new JLabel("Volgende Blok: ");
    NextBlock.setFont(new Font("sansserif", Font.BOLD, 15));
    sidePanel.add(NextBlock);

    ImageIcon iconI = new ImageIcon("C:\\Users\\vdb\\Documents\\NetBeansProjects\\Tetris\\src\\tetris\\Iblock.png");
    ImageIcon iconJ = new ImageIcon("C:\\Users\\vdb\\Documents\\NetBeansProjects\\Tetris\\src\\tetris\\Jblock.png");
    ImageIcon iconL = new ImageIcon("C:\\Users\\vdb\\Documents\\NetBeansProjects\\Tetris\\src\\tetris\\Lblock.png");
    ImageIcon iconO = new ImageIcon("C:\\Users\\vdb\\Documents\\NetBeansProjects\\Tetris\\src\\tetris\\Oblock.png");
    ImageIcon iconS = new ImageIcon("C:\\Users\\vdb\\Documents\\NetBeansProjects\\Tetris\\src\\tetris\\Sblock.png");
    ImageIcon iconT = new ImageIcon("C:\\Users\\vdb\\Documents\\NetBeansProjects\\Tetris\\src\\tetris\\Tblock.png");
    ImageIcon iconZ = new ImageIcon("C:\\Users\\vdb\\Documents\\NetBeansProjects\\Tetris\\src\\tetris\\Zblock.png");

    JLabel label = new JLabel();

    // Icoon voor next block instellen
    if (Grid.randomNum == 0){
        label.setIcon(iconI);
    }else if(Grid.randomNum == 1){
        label.setIcon(iconJ);
    }else if(Grid.randomNum == 2){
        label.setIcon(iconL);
    }else if(Grid.randomNum == 3){
        label.setIcon(iconO);
    }else if(Grid.randomNum == 4){
        label.setIcon(iconS);
    }else if(Grid.randomNum == 5){
        label.setIcon(iconT);
    }else if(Grid.randomNum == 6){
        label.setIcon(iconZ);
    }
    sidePanel.add(label);

    JButton startButton = new JButton("Start game");
    startButton.setFont(new Font("sansserif", Font.BOLD, 15));
    startButton.setFocusable(false);
    sidePanel.add(startButton);

    JButton pauseButton = new JButton("Pause game");
    pauseButton.setFont(new Font("sansserif", Font.BOLD, 15));
    pauseButton.setFocusable(false);
    sidePanel.add(pauseButton);

    JButton stopButton = new JButton("Stop game");
    stopButton.setFont(new Font("sansserif", Font.BOLD, 15));
    stopButton.setFocusable(false);
    sidePanel.add(stopButton);

    JLabel Score = new JLabel("Score: " + score);
    Score.setFont(new Font("sansserif", Font.BOLD, 18));
    sidePanel.add(Score);

    JLabel Lines = new JLabel("Lines: " + lines);
    Lines.setFont(new Font("sansserif", Font.BOLD, 18));
    sidePanel.add(Lines);

    JButton highscores = new JButton("Highscores");
    highscores.setFont(new Font("sansserif", Font.BOLD, 15));
    highscores.setFocusable(false);
    sidePanel.add(highscores);
    }

    public void setLines(int line) {
        lines = line;
        
    }
}

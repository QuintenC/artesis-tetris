package tetris;

import java.awt.*;
import javax.swing.*;

public class Sidebar extends JPanel {

  JPanel sidePanel;
  static String block = "Lblock";
  static int lines;
  static int score;

  protected JLabel blockLabel;

  public Sidebar() {
    System.out.println("new panel created, new block is " + block);
    sidePanel = new JPanel();
    sidePanel.setBackground(Color.gray);
    sidePanel.setPreferredSize(new Dimension(130, 603));
//    sidePanel.setLayout(new GridLayout(8,1));
    sidePanel.setBorder(BorderFactory.createTitledBorder("Tetris"));

    JLabel NextBlock = new JLabel("Volgende Blok: ");
    NextBlock.setFont(new Font("sansserif", Font.BOLD, 15));
    sidePanel.add(NextBlock);

    // Generate random block
    JLabel blockLabel = new JLabel();
    Integer initialBlockNr = (int) Math.ceil(Math.random() * 7);
    this.generateRandomBlock(initialBlockNr);
    sidePanel.add(blockLabel);

    // Start Button
    JButton startButton = new JButton("Start game");
    startButton.setFont(new Font("sansserif", Font.BOLD, 15));
    startButton.setFocusable(false);
    sidePanel.add(startButton);

    // Pause Button
    JButton pauseButton = new JButton("Pause game");
    pauseButton.setFont(new Font("sansserif", Font.BOLD, 15));
    pauseButton.setFocusable(false);
    sidePanel.add(pauseButton);

    // Stop
    JButton stopButton = new JButton("Stop game");
    stopButton.setFont(new Font("sansserif", Font.BOLD, 15));
    stopButton.setFocusable(false);
    sidePanel.add(stopButton);

    // Score
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

  /**
   * Een willekeurige nieuwe blok genereren
   */
  public void generateRandomBlock(Integer blockNr) {
    // Icoon voor next block instellen
    ImageIcon icon;
    String imagePath = "C:\\Users\\vdb\\Documents\\NetBeansProjects\\Tetris\\src\\tetris\\";
    switch (blockNr) {
      case 1:
        icon = new ImageIcon(imagePath + "Iblock.png");
        break;
      case 2:
        icon = new ImageIcon(imagePath + "Jblock.png");
        break;
      case 3:
        icon = new ImageIcon(imagePath + "Lblock.png");
        break;
      case 4:
        icon = new ImageIcon(imagePath + "Oblock.png");
        break;
      case 5:
        icon = new ImageIcon(imagePath + "Sblock.png");
        break;
      case 6:
        icon = new ImageIcon(imagePath + "Tblock.png");
        break;
      case 7:
        icon = new ImageIcon(imagePath + "Zblock.png");
        break;
      default:
        // Should not get here
        return;
    }

    blockLabel.setIcon(icon);
  }

}

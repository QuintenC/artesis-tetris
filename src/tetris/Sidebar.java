package tetris;

import java.awt.*;
import javax.swing.*;
import tetris.events.*;

public class Sidebar extends JPanel implements NewBlockEventListener, NewScoreEventListener {

  protected JPanel sidePanel;
  protected JLabel blockLabel;
  protected JLabel Score;
  protected JLabel Lines;
  protected int lines;
  protected int score;

  public Sidebar() {
    sidePanel = new JPanel();
    sidePanel.setBackground(Color.gray);
    sidePanel.setPreferredSize(new Dimension(130, 603));
//    sidePanel.setLayout(new GridLayout(8,1));
    sidePanel.setBorder(BorderFactory.createTitledBorder("Tetris"));

    JLabel NextBlock = new JLabel("Volgende Blok: ");
    NextBlock.setFont(new Font("sansserif", Font.BOLD, 15));
    sidePanel.add(NextBlock);

    // Generate random block
    blockLabel = new JLabel();
    // Integer initialBlockNr = (int) Math.ceil(Math.random() * 7);
    // this.generateRandomBlock(initialBlockNr);
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
    Score = new JLabel("Score: " + score);
    Score.setFont(new Font("sansserif", Font.BOLD, 18));
    sidePanel.add(Score);

    Lines = new JLabel("Lines: " + lines);
    Lines.setFont(new Font("sansserif", Font.BOLD, 18));
    sidePanel.add(Lines);

    JButton highscores = new JButton("Highscores");
    highscores.setFont(new Font("sansserif", Font.BOLD, 15));
    highscores.setFocusable(false);
    sidePanel.add(highscores);
  }

  public void setLines(int input) {
    lines = input;
    Lines.setText("Lines: " + lines);
  }

  public void setScore(int input) {
    score = input;
    Score.setText("Score: " + score);
  }

  /**
   * Block label updaten
   */
  public void setBlockImage(Class blockClass) {
    System.out.println("Sidebar.setBlockImage");
    // Icoon voor next block instellen
    ImageIcon icon;
    String imagePath = "C:\\Users\\vdb\\Documents\\NetBeansProjects\\Tetris\\src\\tetris\\images\\";
    // String imagePath = "src\\tetris\\images";
    String path = imagePath + blockClass.getSimpleName() + ".png";
    System.out.println(path);
    icon = new ImageIcon(path);
    System.out.println(icon.getIconHeight());
    try {
      blockLabel.setIcon(icon);
    } catch (Exception e) {
      System.out.println(e.toString());
      System.out.println(blockLabel);
    }
  }

  @Override
  public void newBlockEventOccurred(NewBlockEvent e) {
    Grid sourceGrid = (Grid) e.getSource();
    this.setBlockImage(sourceGrid.getNextBlockClass());
  }

  @Override
  public void newScoreEventOccurred(NewScoreEvent e) {
    Grid sourceGrid = (Grid) e.getSource();
    this.setScore(sourceGrid.getScore());
    System.out.println("score binnen gekregen is : " + sourceGrid.getScore());
    this.setLines(sourceGrid.getLines());
  }
}

package tetris;

import java.awt.*;
import javax.swing.*;
import tetris.events.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Sidebar extends JPanel implements NewBlockEventListener, NewScoreEventListener, NewGameoverEventListener {

  protected JPanel sidePanel;
  protected JLabel blockLabel;
  protected JLabel Score;
  protected JLabel Lines;
  protected JLabel Level;
  protected int lines;
  protected int score;
  protected int level;
  protected boolean gamestarted;
  protected boolean gameover = false;

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
    ImageIcon icon = new ImageIcon("C:\\Users\\vdb\\Documents\\NetBeansProjects\\Tetris\\src\\tetris\\images\\Noblock.png");
    blockLabel.setIcon(icon);
    sidePanel.add(blockLabel);

    // Start Button
    final JButton startButton = new JButton("Start Game");
    startButton.setFont(new Font("sansserif", Font.BOLD, 15));
    startButton.setFocusable(false);
    sidePanel.add(startButton);

    // Pause Button
    final JButton pauseButton = new JButton("Pause Game");
    pauseButton.setFont(new Font("sansserif", Font.BOLD, 15));
    pauseButton.setFocusable(false);
    sidePanel.add(pauseButton);

    JButton highscores = new JButton("Highscores");
    highscores.setFont(new Font("sansserif", Font.BOLD, 15));
    highscores.setFocusable(false);
    sidePanel.add(highscores);

    // Level
    Level = new JLabel("Level: " + level);
    Level.setFont(new Font("sansserif", Font.BOLD, 18));
    sidePanel.add(Level);

    // Score
    Score = new JLabel("Score: " + score);
    Score.setFont(new Font("sansserif", Font.BOLD, 18));
    sidePanel.add(Score);

    Lines = new JLabel("Lines: " + lines);
    Lines.setFont(new Font("sansserif", Font.BOLD, 18));
    sidePanel.add(Lines);




    startButton.addActionListener(
            new ActionListener() {

              @Override
              public void actionPerformed(ActionEvent e) {
                String text = (String) e.getActionCommand();
                if (text.equals("Start Game")) {
                  startButton.setText("Stop Game");
                  gamestarted = true;
                } else {
                  startButton.setText("Start Game");
                  if(pauseButton.getText().equals("Resume Game")){
                    pauseButton.setText("Pause Game");
                  }
                  gamestarted = false;
                }
                this.fireNewStartgameEvent(new NewStartgameEvent(this));
              }

              private void fireNewStartgameEvent(NewStartgameEvent evt) {
                Object[] listeners = listenerList.getListenerList();
                int listenerCount = listeners.length;

                for (int i = 0; i < listenerCount; i += 2) {
                  if (listeners[i] == NewStartgameEventListener.class) {
                    ((NewStartgameEventListener) listeners[i + 1]).newStartgameEventOccurred(evt);
                  }
                }
              }
            });

    pauseButton.addActionListener(
            new ActionListener() {

              @Override
              public void actionPerformed(ActionEvent e) {
                this.fireNewPausegameEvent(new NewPausegameEvent(this));
                 
                String text = (String) e.getActionCommand();
                if (gamestarted == true && gameover == false) {
                  System.out.println("gameover is " + gameover);
                  if (text.equals("Pause Game")) {
                    pauseButton.setText("Resume Game");
                  } else {
                    pauseButton.setText("Pause Game");
                  }
                }
              }

              private void fireNewPausegameEvent(NewPausegameEvent evt) {
                Object[] listeners = listenerList.getListenerList();
                int listenerCount = listeners.length;

                for (int i = 0; i < listenerCount; i += 2) {
                  if (listeners[i] == NewPausegameEventListener.class) {
                    ((NewPausegameEventListener) listeners[i + 1]).newPausegameEventOccurred(evt);
                  }
                }
              }
            });

         highscores.addActionListener(
            new ActionListener() {

              @Override
              public void actionPerformed(ActionEvent e) {
                this.fireNewHighscoreEvent(new NewHighscoreEvent(this));
              }

              private void fireNewHighscoreEvent(NewHighscoreEvent evt) {
                Object[] listeners = listenerList.getListenerList();
                int listenerCount = listeners.length;

                for (int i = 0; i < listenerCount; i += 2) {
                  if (listeners[i] == NewHighscoreEventListener.class) {
                    ((NewHighscoreEventListener) listeners[i + 1]).newHighscoreEventOccurred(evt);
                  }
                }
              }
            });

  }

  public void setLevel(int input) {
    level = input;
    Level.setText("Level: " + level);
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
    String imagePath = "src\\tetris\\images\\";
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
    this.setLevel(sourceGrid.getLevel());
  }

  @Override
  public void newGameoverEventOccurred(NewGameoverEvent e) {
    Grid sourceGrid = (Grid) e.getSource();
    gameover = sourceGrid.getGameover();
  }

  public void addNewStartgameEventListener(NewStartgameEventListener listener) {
    listenerList.add(NewStartgameEventListener.class, listener);
  }

  public void addNewPausegameEventListener(NewPausegameEventListener listener) {
    listenerList.add(NewPausegameEventListener.class, listener);
  }

  public void addNewHighscoreEventListener(NewHighscoreEventListener listener) {
    listenerList.add(NewHighscoreEventListener.class, listener);
  }

  public void addNewGameoverEventListener(NewGameoverEventListener listener) {
    listenerList.add(NewGameoverEventListener.class, listener);
  }
}

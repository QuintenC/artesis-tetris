package tetris;

import blocks.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.lang.reflect.Constructor;
import tetris.events.*;

public class Grid extends JPanel implements KeyListener, Runnable, NewStartgameEventListener, NewPausegameEventListener {

  // De breedte en hoogte van de tegels (vierkant)
  private final int tileSize = 30;
  // Het aantal horizantale en verticale tegels
  private final int horTiles = 10;
  private final int verTiles = 18;
  // De breedte en hoogte van het veld in pixels
  private final int gridWidth = tileSize * horTiles;
  private final int gridHeight = tileSize * verTiles;
  // De kleuren van het veld, de tegels en muren
  private static final Color tileColor = Color.GRAY;
  private static final Color gridColor = Color.DARK_GRAY;
  private boolean collision = false;
  private boolean clear = true;
  private int defaultBlockX = (int) horTiles / 2 * tileSize;
  private int defaultBlockY = 0;
  private BlockInterface CurrentBlock;
  private BlockInterface NextBlock;
  private ArrayList<BlockInterface> blokken = new ArrayList<BlockInterface>();
  private int score;
  private int levelScore;
  private int lines;
  private int level = 1;
  private int speed;
  private boolean startgame = false;
  private boolean pleaseWait = false;
  private Thread th;
  private Highscores highscore = new Highscores();

  //initialiseert het grid
  public Grid() {
    // Constructor
    this.setSize(gridWidth, gridHeight);
    pleaseWait = true;
    highscore.MakeFile("naam0",2,1,1);
    highscore.AddHighscore("naam1", 2, 1, 1);
    highscore.AddHighscore("naam2", 3, 1, 1);
    highscore.AddHighscore("naam3", 4, 1, 1);
    highscore.AddHighscore("naam4", 5, 1, 1);
    highscore.AddHighscore("naam5", 6, 1, 1);
    highscore.AddHighscore("naam6", 7, 1, 1);
    highscore.AddHighscore("naam7", 8, 1, 1);
    highscore.AddHighscore("naam8", 9, 1, 1);
    highscore.AddHighscore("naam9", 10, 1, 1);
    highscore.AddHighscore("naam10", 11, 1, 1);
    highscore.AddHighscore("naam11", 12, 1, 1);
    highscore.AddHighscore("naam12", 13, 1, 1);



    highscore.SaveFile();
    start();
  }

  final public void start() {
    //starts a new thread
    th = new Thread(this);
    //starts the thread
    th.start();
  }

  @Override
  public void run() {
    while (true) {

      try {
        Thread.sleep(speed);
      } catch (InterruptedException ex) {
        // do nothing
      }

      synchronized (this) {
        while (pleaseWait == true) {
          try {
            wait();
          } catch (Exception e) {
          }
        }
      }
      moveDown();
      UpdateGrid();
      this.repaint();

    }
  }
  //getters

  final public int getgridHeight() {
    return gridHeight;
  }

  final public int getgridWidth() {
    return gridWidth;
  }

  final public Class getNextBlockClass() {
    return NextBlock.getClass();
  }

  final public ArrayList<BlockInterface> getBlokken() {
    return blokken;
  }

  final public int getScore() {
    return score;
  }

  final public int getLines() {
    return lines;
  }

  final public int getLevel() {
    return level;
  }

  final public boolean getGamestarted() {
    return startgame;
  }

  @Override
  public void keyPressed(KeyEvent e) {
    int key = e.getKeyCode();
    if (key == KeyEvent.VK_RIGHT) {
      moveRight();
    } else if (key == KeyEvent.VK_LEFT) {
      moveLeft();
    } else if (key == KeyEvent.VK_UP) {
      rotate();
    } else if (key == KeyEvent.VK_DOWN) {
      while (collision == false) {
        moveDown();
        score += 2;
        this.fireNewScoreEvent(new NewScoreEvent(this));
      }
    }

    UpdateGrid();
    this.repaint();
  }

  @Override
  public void keyTyped(KeyEvent e) {
//        throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void keyReleased(KeyEvent e) {
//        throw new UnsupportedOperationException("Not supported yet.");
  }

  //tekent de tegels van het raster
  private void drawTiles(Graphics g) {
    // Tekent de vierkante vakjes (met een grootte van tileSize * tileSize)
    for (int x = 0; x < horTiles; x++) {
      for (int y = 0; y < verTiles; y++) {
        // De vulkleur:
        g.setColor(gridColor);
        g.fillRect(x * tileSize + 1, y * tileSize + 1, tileSize - 1, tileSize - 1);
      }
    }
  }

  //tekent de lijnen van het raster
  private void drawLines(Graphics g) {
    for (int x = 0; x < horTiles; x++) {
      for (int y = 0; y < verTiles; y++) {
        // De randkleur:
        g.setColor(tileColor);
        g.drawRect(x * tileSize, y * tileSize, tileSize, tileSize);
      }
    }
  }

  //tekent de blok
  private void drawBlocks(Graphics g) {
    for (int i = 0; i < blokken.size(); i++) {
      blokken.get(i).draw(g);
    }
  }

  //tekent alle onderdelen
  @Override
  public void paintComponent(Graphics g) {
    drawTiles(g);
    drawBlocks(g);
    drawLines(g);
  }

  //Beweegt de huidige blok naar rechts indien mogelijk
  private void moveRight() {
    if (blokken.size() > 1) {
      for (int i = 0; i < blokken.size() - 1; i++) {
        for (int u = 1; u <= 4; u++) {
          for (int o = 1; o <= 4; o++) {
            if (CurrentBlock.getX(u) + tileSize == blokken.get(i).getX(o) && CurrentBlock.getY(u) == blokken.get(i).getY(o)) {
//                                System.out.println("check------------------------------------------------------------------------");
              clear = false;
            }
          }
        }
      }
    }
    if (clear == true) {
//                System.out.println("rechts------------------------------------------------------------------------");
      CurrentBlock.moveRight();
    }
    clear = true;
  }

  //Beweegt de huidige blok naar links indien mogelijk
  private void moveLeft() {
    if (blokken.size() > 1) {
      for (int i = 0; i < blokken.size() - 1; i++) {
        for (int u = 1; u <= 4; u++) {
          for (int o = 1; o <= 4; o++) {
            if (CurrentBlock.getX(u) - tileSize == blokken.get(i).getX(o) && CurrentBlock.getY(u) == blokken.get(i).getY(o)) {
//                                System.out.println("check------------------------------------------------------------------------");
              clear = false;
            }
          }
        }
      }
    }
    if (clear == true) {
//                System.out.println("links------------------------------------------------------------------------");
      CurrentBlock.moveLeft();
    }
    clear = true;
  }

  //Beweegt huidige blok naar onder indien mogelijk, indien niet moegelijk zet het collision op true
  private void moveDown() {
    if (blokken.size() > 1) {
      for (int i = 0; i < blokken.size() - 1; i++) {
        for (int u = 1; u <= 4; u++) {
          for (int o = 1; o <= 4; o++) {
            if (CurrentBlock.getX(u) == blokken.get(i).getX(o) && CurrentBlock.getY(u) + tileSize == blokken.get(i).getY(o)
                    || CurrentBlock.getY(u) == 510) {
              System.out.println("check, botsing naar onder----------------------------------------------");
              clear = false;
              collision = true;
            }
          }
        }
      }
    }
    for (int u = 1; u <= 4; u++) {
      if (CurrentBlock.getY(u) == 510) {
        collision = true;
        clear = false;
      }
    }
    if (clear == true) {
      CurrentBlock.moveDown();
    }
    clear = true;
  }

  //Draait de huidige blok indien mogelijk, indien niet mogelijk gebeurt er niets
  private void rotate() {
    String blockClass = CurrentBlock.getClass().getSimpleName();
    if (blockClass.equals("Iblock")) {
      if (blokken.size() > 1) {
        for (int i = 0; i < blokken.size() - 1; i++) {
          for (int o = 1; o <= 4; o++) {
            if (CurrentBlock.getRotatenr() == 1) {
              if ((CurrentBlock.getX2() + 30 == blokken.get(i).getX(o) && CurrentBlock.getY2() - 30 == blokken.get(i).getY(o))
                      || (CurrentBlock.getX3() + 60 == blokken.get(i).getX(o) && CurrentBlock.getY3() - 60 == blokken.get(i).getY(o))
                      || (CurrentBlock.getX4() - 30 == blokken.get(i).getX(o) && CurrentBlock.getY4() + 30 == blokken.get(i).getY(o))) {
                clear = false;
              }
            } else if (CurrentBlock.getRotatenr() == 2) {
              if ((CurrentBlock.getX2() - 30 == blokken.get(i).getX(o) && CurrentBlock.getY2() + 30 == blokken.get(i).getY(o))
                      || (CurrentBlock.getX3() - 60 == blokken.get(i).getX(o) && CurrentBlock.getY3() + 60 == blokken.get(i).getY(o))
                      || (CurrentBlock.getX4() + 30 == blokken.get(i).getX(o) && CurrentBlock.getY4() - 30 == blokken.get(i).getY(o))) {
                clear = false;
              }
            }
          }
        }
      }
    } else if (blockClass.equals("Jblock")) {
      if (blokken.size() > 1) {
        for (int i = 0; i < blokken.size() - 1; i++) {
          for (int o = 1; o <= 4; o++) {
            if (CurrentBlock.getRotatenr() == 1) {
              if ((CurrentBlock.getX2() + 30 == blokken.get(i).getX(o) && CurrentBlock.getY2() - 30 == blokken.get(i).getY(o))
                      || (CurrentBlock.getX3() - 30 == blokken.get(i).getX(o) && CurrentBlock.getY3() + 30 == blokken.get(i).getY(o))
                      || (CurrentBlock.getX4() - 60 == blokken.get(i).getX(o) && CurrentBlock.getY4() == blokken.get(i).getY(o))) {
                clear = false;
              }
            } else if (CurrentBlock.getRotatenr() == 2) {
              if ((CurrentBlock.getX2() + 30 == blokken.get(i).getX(o) && CurrentBlock.getY2() + 30 == blokken.get(i).getY(o))
                      || (CurrentBlock.getX3() - 30 == blokken.get(i).getX(o) && CurrentBlock.getY3() - 30 == blokken.get(i).getY(o))
                      || (CurrentBlock.getX4() == blokken.get(i).getX(o) && CurrentBlock.getY4() - 60 == blokken.get(i).getY(o))) {
                clear = false;
              }
            } else if (CurrentBlock.getRotatenr() == 3) {
              if ((CurrentBlock.getX2() - 30 == blokken.get(i).getX(o) && CurrentBlock.getY2() + 30 == blokken.get(i).getY(o))
                      || (CurrentBlock.getX3() + 30 == blokken.get(i).getX(o) && CurrentBlock.getY3() - 30 == blokken.get(i).getY(o))
                      || (CurrentBlock.getX4() + 60 == blokken.get(i).getX(o) && CurrentBlock.getY4() + 60 == blokken.get(i).getY(o))) {
                clear = false;
              }
            } else if (CurrentBlock.getRotatenr() == 4) {
              if ((CurrentBlock.getX2() - 30 == blokken.get(i).getX(o) && CurrentBlock.getY2() - 30 == blokken.get(i).getY(o))
                      || (CurrentBlock.getX3() + 30 == blokken.get(i).getX(o) && CurrentBlock.getY3() + 30 == blokken.get(i).getY(o))
                      || (CurrentBlock.getX4() == blokken.get(i).getX(o) && CurrentBlock.getY4() + 60 == blokken.get(i).getY(o))) {
                clear = false;
              }
            }
          }
        }
      }
    } else if (blockClass.equals("Lblock")) {
      if (blokken.size() > 1) {
        for (int i = 0; i < blokken.size() - 1; i++) {
          for (int o = 1; o <= 4; o++) {
            if (CurrentBlock.getRotatenr() == 1) {
              if ((CurrentBlock.getX2() + 30 == blokken.get(i).getX(o) && CurrentBlock.getY2() - 30 == blokken.get(i).getY(o))
                      || (CurrentBlock.getX3() - 30 == blokken.get(i).getX(o) && CurrentBlock.getY3() + 30 == blokken.get(i).getY(o))
                      || (CurrentBlock.getX4() == blokken.get(i).getX(o) && CurrentBlock.getY4() - 60 == blokken.get(i).getY(o))) {
                clear = false;
              }
            } else if (CurrentBlock.getRotatenr() == 2) {
              if ((CurrentBlock.getX2() + 30 == blokken.get(i).getX(o) && CurrentBlock.getY2() + 30 == blokken.get(i).getY(o))
                      || (CurrentBlock.getX3() - 30 == blokken.get(i).getX(o) && CurrentBlock.getY3() - 30 == blokken.get(i).getY(o))
                      || (CurrentBlock.getX4() + 60 == blokken.get(i).getX(o) && CurrentBlock.getY4() == blokken.get(i).getY(o))) {
                clear = false;
              }
            } else if (CurrentBlock.getRotatenr() == 3) {
              if ((CurrentBlock.getX2() - 30 == blokken.get(i).getX(o) && CurrentBlock.getY2() + 30 == blokken.get(i).getY(o))
                      || (CurrentBlock.getX3() + 30 == blokken.get(i).getX(o) && CurrentBlock.getY3() - 30 == blokken.get(i).getY(o))
                      || (CurrentBlock.getX4() == blokken.get(i).getX(o) && CurrentBlock.getY4() + 60 == blokken.get(i).getY(o))) {
                clear = false;
              }
            } else if (CurrentBlock.getRotatenr() == 4) {
              if ((CurrentBlock.getX2() - 30 == blokken.get(i).getX(o) && CurrentBlock.getY2() - 30 == blokken.get(i).getY(o))
                      || (CurrentBlock.getX3() + 30 == blokken.get(i).getX(o) && CurrentBlock.getY3() + 30 == blokken.get(i).getY(o))
                      || (CurrentBlock.getX4() - 60 == blokken.get(i).getX(o) && CurrentBlock.getY4() == blokken.get(i).getY(o))) {
                clear = false;
              }
            }
          }
        }
      }
    } else if (blockClass.equals("Oblock")) {
      clear = true;
    } else if (blockClass.equals("Sblock")) {
      if (blokken.size() > 1) {
        for (int i = 0; i < blokken.size() - 1; i++) {
          for (int o = 1; o <= 4; o++) {
            if (CurrentBlock.getRotatenr() == 1) {
              if ((CurrentBlock.getX2() - 30 == blokken.get(i).getX(o) && CurrentBlock.getY2() + 30 == blokken.get(i).getY(o))
                      || (CurrentBlock.getX3() - 30 == blokken.get(i).getX(o) && CurrentBlock.getY3() - 30 == blokken.get(i).getY(o))
                      || (CurrentBlock.getX4() == blokken.get(i).getX(o) && CurrentBlock.getY4() - 60 == blokken.get(i).getY(o))) {
                clear = false;
              }
            } else if (CurrentBlock.getRotatenr() == 2) {
              if ((CurrentBlock.getX2() + 30 == blokken.get(i).getX(o) && CurrentBlock.getY2() - 30 == blokken.get(i).getY(o))
                      || (CurrentBlock.getX3() + 30 == blokken.get(i).getX(o) && CurrentBlock.getY3() + 30 == blokken.get(i).getY(o))
                      || (CurrentBlock.getX4() == blokken.get(i).getX(o) && CurrentBlock.getY4() + 60 == blokken.get(i).getY(o))) {
                clear = false;
              }
            }
          }
        }
      }
    } else if (blockClass.equals("Tblock")) {
      if (blokken.size() > 1) {
        for (int i = 0; i < blokken.size() - 1; i++) {
          for (int o = 1; o <= 4; o++) {
            if (CurrentBlock.getRotatenr() == 1) {
              if ((CurrentBlock.getX1() + 30 == blokken.get(i).getX(o) && CurrentBlock.getY1() + 30 == blokken.get(i).getY(o))
                      || (CurrentBlock.getX3() - 30 == blokken.get(i).getX(o) && CurrentBlock.getY3() + 30 == blokken.get(i).getY(o))
                      || (CurrentBlock.getX4() + 30 == blokken.get(i).getX(o) && CurrentBlock.getY4() - 30 == blokken.get(i).getY(o))) {
                clear = false;
              }
            } else if (CurrentBlock.getRotatenr() == 2) {
              if (((CurrentBlock.getX1() - 30 == blokken.get(i).getX(o) && CurrentBlock.getY1() + 30 == blokken.get(i).getY(o)))
                      || (CurrentBlock.getX3() - 30 == blokken.get(i).getX(o) && CurrentBlock.getY3() - 30 == blokken.get(i).getY(o))
                      || (CurrentBlock.getX4() + 30 == blokken.get(i).getX(o) && CurrentBlock.getY4() + 30 == blokken.get(i).getY(o))) {
                clear = false;
              }
            } else if (CurrentBlock.getRotatenr() == 3) {
              if ((CurrentBlock.getX1() - 30 == blokken.get(i).getX(o) && CurrentBlock.getY1() - 30 == blokken.get(i).getY(o))
                      || (CurrentBlock.getX3() + 30 == blokken.get(i).getX(o) && CurrentBlock.getY3() - 30 == blokken.get(i).getY(o))
                      || (CurrentBlock.getX4() - 30 == blokken.get(i).getX(o) && CurrentBlock.getY4() + 30 == blokken.get(i).getY(o))) {
                clear = false;
              }
            } else if (CurrentBlock.getRotatenr() == 4) {
              if ((CurrentBlock.getX1() + 30 == blokken.get(i).getX(o) && CurrentBlock.getY1() - 30 == blokken.get(i).getY(o))
                      || (CurrentBlock.getX3() + 30 == blokken.get(i).getX(o) && CurrentBlock.getY3() + 30 == blokken.get(i).getY(o))
                      || (CurrentBlock.getX4() - 30 == blokken.get(i).getX(o) && CurrentBlock.getY4() - 30 == blokken.get(i).getY(o))) {
                clear = false;
              }
            }
          }
        }
      }
    } else if (blockClass.equals("Zblock")) {
      if (blokken.size() > 1) {
        for (int i = 0; i < blokken.size() - 1; i++) {
          for (int o = 1; o <= 4; o++) {
            if (CurrentBlock.getRotatenr() == 1) {
              if ((CurrentBlock.getX2() + 30 == blokken.get(i).getX(o) && CurrentBlock.getY2() - 30 == blokken.get(i).getY(o))
                      || (CurrentBlock.getX3() - 30 == blokken.get(i).getX(o) && CurrentBlock.getY3() - 30 == blokken.get(i).getY(o))
                      || (CurrentBlock.getX4() - 60 == blokken.get(i).getX(o) && CurrentBlock.getY4() == blokken.get(i).getY(o))) {
                clear = false;
              }
            } else if (CurrentBlock.getRotatenr() == 2) {
              if ((CurrentBlock.getX2() - 30 == blokken.get(i).getX(o) && CurrentBlock.getY2() + 30 == blokken.get(i).getY(o))
                      || (CurrentBlock.getX3() + 30 == blokken.get(i).getX(o) && CurrentBlock.getY3() + 30 == blokken.get(i).getY(o))
                      || (CurrentBlock.getX4() + 60 == blokken.get(i).getX(o) && CurrentBlock.getY4() == blokken.get(i).getY(o))) {
                clear = false;
              }
            }
          }
        }
      }
    }
    if (clear == true) {
      CurrentBlock.rotate();
    }
    clear = true;
  }

  /**
   * Kijkt na of er volledige horizontale lijnen zijn,
   * en verwijdert die.
   */
  private void CheckLines() {
    for (int t = 0; t <= 510; t += 30) {
      int BlocksOnLine = 0;

      for (int i = 0; i < blokken.size(); i++) {
        for (int u = 1; u <= 4; u++) {
          if (blokken.get(i).getY(u) == t) {
            BlocksOnLine++;
          }
        }

      }
      if (BlocksOnLine == 10) {
        for (int i = 0; i < blokken.size(); i++) {
          for (int u = 1; u <= 4; u++) {
            if (blokken.get(i).getY(u) == t) {
              blokken.get(i).setY(u, 5000);
            }
            if (blokken.get(i).getY(u) < t) {
              blokken.get(i).setY(u, blokken.get(i).getY(u) + tileSize);
            }
          }
        }
        lines++;
        score += 50;
        System.out.println("line made");
        this.fireNewScoreEvent(new NewScoreEvent(this));
      }
    }
  }

  /*
   * Kijkt na of een een botsing is gebeurt, indien dit het geval is zet het een niewue blik boven aan het rooster.
   * Ook kijkt het na of het spel niet gedaan is, indien dit het geval is geeft het de behaalde score weer.
   */
  private void UpdateGrid() {
    boolean gameover = false;

    if (collision == true) {
      score += 5;
      if (score > levelScore + 200) {
        levelScore = score;
        level += 1;
        speed = speed * 70 / 100;
      }
      this.fireNewScoreEvent(new NewScoreEvent(this));
      CheckLines();
      // Random blok / klasse
      CurrentBlock = NextBlock;
      CurrentBlock.setPosition(defaultBlockX, defaultBlockY);
      NextBlock = this.getRandomBlock();
      NextBlock.setPosition(0, -10000);
      blokken.add(CurrentBlock);
      System.out.println("Nieuwe blok toegevoegd aan arraylist, grootte is nu: " + blokken.size());

      // Dispatch event for new block
      this.fireNewBlockEvent(new NewBlockEvent(this));



      // Test voor game over
      if (blokken.size() > 1) {
        for (int u = 1; u <= 4; u++) {
          if (blokken.get(blokken.size() - 2).getY(u) < 30) {
            pleaseWait = true;
            gameover = true;

          }
        }
      }
      if (gameover == true) {
        System.out.println("                                            GAME OVER");
        System.out.println("---------------------------------------------------------------------------------------------------------------------");
        JOptionPane.showConfirmDialog(null, "Congratulations, you cleared " + lines + " lines,\n got a score of " + score + " \nand made it to level " + level + "!", "Congratulations !", JOptionPane.PLAIN_MESSAGE);
        JOptionPane pane = new JOptionPane("You got a new highscore! Do you want to save it?");
        Object[] options = new String[]{"Yes", "No"};
        pane.setOptions(options);
        JDialog dialog = pane.createDialog(new JFrame(), "Dialog");
        dialog.setVisible(true);
        Object obj = pane.getValue();
        int result = -1;
        for (int k = 0; k < options.length; k++) {
          if (options[k].equals(obj)) {
            result = k;
          }
        }
        System.out.println("User's choice: " + result);
      }
      collision = false;
    }
  }

  /**
   * Methode om een nieuwe, willekeurige block te genereren.
   * @return Block een willekeurige block
   */
  protected final BlockInterface getRandomBlock() {
    // Random blok / klasse
    Class randomBlockClass;
    int randomNum = (int) (Math.random() * 7);
    switch (randomNum) {
      case 0:
        randomBlockClass = Iblock.class;
        break;
      case 1:
        randomBlockClass = Jblock.class;
        break;
      case 2:
        randomBlockClass = Lblock.class;
        break;
      case 3:
        randomBlockClass = Oblock.class;
        break;
      case 4:
        randomBlockClass = Sblock.class;
        break;
      case 5:
        randomBlockClass = Tblock.class;
        break;
      case 6:
        randomBlockClass = Zblock.class;
        break;
      default:
        randomBlockClass = Iblock.class;
    }

    // Coordinaten van de nieuwe block
    int blockX = (int) horTiles / 2 * tileSize;
    int blockY = 0;

    BlockInterface newRandomBlock;
    try {
      // Instantieer random block
      System.out.println("Get constructor...");
      Constructor randomBlockConstructor = randomBlockClass.getConstructor();
      System.out.println("Constructor created...");
      newRandomBlock = (BlockInterface) randomBlockConstructor.newInstance();
      System.out.println("newRandomBlock created..." + " Grootte van ArrayList is nu: " + blokken.size());
      newRandomBlock.setPosition(blockX, blockY);
      return newRandomBlock;
    } catch (Exception e) {
      // Er zijn enkele exceptions die gecatched moeten worden,
      // maar normaal gezien doen die zich niet voor dus... exit
      // Voor te debuggen:
      System.out.println("Error: Geen nieuwe random block!!!");
      System.out.println(e.getLocalizedMessage());
      System.out.println(e.getMessage());
      System.out.println(e.toString());
      System.exit(0);
      return null;
    }


  }

  public void addNewScoreEventListener(NewScoreEventListener listener) {
    listenerList.add(NewScoreEventListener.class, listener);
  }

  public final void fireNewScoreEvent(NewScoreEvent evt) {
    Object[] listeners = listenerList.getListenerList();
    int listenerCount = listeners.length;

    for (int i = 0; i < listenerCount; i += 2) {
      if (listeners[i] == NewScoreEventListener.class) {
        ((NewScoreEventListener) listeners[i + 1]).newScoreEventOccurred(evt);
      }
    }
  }

  public void addNewBlockEventListener(NewBlockEventListener listener) {
    listenerList.add(NewBlockEventListener.class, listener);
  }

  public final void fireNewBlockEvent(NewBlockEvent evt) {
    Object[] listeners = listenerList.getListenerList();
    int listenerCount = listeners.length;

    for (int i = 0; i < listenerCount; i += 2) {
      if (listeners[i] == NewBlockEventListener.class) {
        ((NewBlockEventListener) listeners[i + 1]).newBlockEventOccurred(evt);
      }
    }
  }

  @Override
  public void newStartgameEventOccurred(NewStartgameEvent e) {
    System.out.println("startgame is ingedrukt en startgame == true");
    if (startgame == false) {
      speed = 500;
      NextBlock = this.getRandomBlock();
      NextBlock.setPosition(0, -10000);
      CurrentBlock = this.getRandomBlock();
      CurrentBlock.setPosition(defaultBlockX, defaultBlockY);
      blokken.add(CurrentBlock);
      this.fireNewBlockEvent(new NewBlockEvent(this));
      pleaseWait = false;
      synchronized (this) {
        this.notifyAll();
      }
      System.out.println("spel is gestart");
      startgame = true;
    } else if (startgame == true) {
      System.out.println("stopgame is ingedrukt---------------------------------------------------------------------------------------------");
      blokken.clear();
      level = score = lines = 0;
      this.fireNewScoreEvent(new NewScoreEvent(this));
      repaint();
      startgame = false;
      pleaseWait = true;
    }
  }

  @Override
  public void newPausegameEventOccurred(NewPausegameEvent e) {
    if (startgame == true) {
      if (pleaseWait == false) {
        pleaseWait = true;
        System.out.println("Pause game pressed" + "pleaseWait = " + pleaseWait);
      } else if (pleaseWait == true) {
        pleaseWait = false;
        synchronized (this) {
          this.notifyAll();
        }
        System.out.println("Resume game pressed" + "pleaseWait = " + pleaseWait);
      }
    }
  }
}

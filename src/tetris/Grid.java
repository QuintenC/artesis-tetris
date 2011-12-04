package tetris;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.lang.reflect.Constructor;
import tetris.events.*;

public class Grid extends JPanel implements KeyListener,Runnable {

  // De breedte en hoogte van de tegels (vierkant)
  private static final int tileSize = 30;
  // Het aantal horizantale en verticale tegels
  private static final int horTiles = 10;
  private static final int verTiles = 18;
  // De breedte en hoogte van het veld in pixels
  private static final int gridWidth = tileSize * horTiles;
  private static final int gridHeight = tileSize * verTiles;
  // De kleuren van het veld, de tegels en muren
  private static final Color tileColor = Color.GRAY;
  private static final Color gridColor = Color.DARK_GRAY;
  private int Direction;
  private boolean collision = false;
  private boolean clear = true;
  private int defaultBlockX = (int) horTiles / 2 * tileSize;
  private int defaultBlockY = 0;
  private Block CurrentBlock; // new Lblock((int) horTiles / 2 * tileSize, (int) 0);
  private Block NextBlock; // new Lblock((int) horTiles / 2 * tileSize, (int) 0);
  private ArrayList<Block> blokken = new ArrayList<Block>();
  private int score;
  private int lines;

  //initialiseert het grid
  public Grid() {
    // Constructor
    this.setSize(gridWidth, gridHeight);
    NextBlock = this.getRandomBlock();
    NextBlock.setPosition(0, -10000);
    CurrentBlock = this.getRandomBlock();
    CurrentBlock.setPosition(defaultBlockX, defaultBlockY);
    blokken.add(CurrentBlock);
    this.fireNewBlockEvent(new NewBlockEvent(this));
    System.out.println("Contructor Nieuwe blok toegevoegd aan arraylist, grootte is nu: " + blokken.size());
         start();
  }

     final public void start(){
            //starts a new thread
            Thread th= new Thread(this);
            //starts the thread
            th.start();
     }

     public void run(){
         Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
         boolean clear= false;
         while(true){
             moveDown();
          try
         {
              Thread.sleep (200);
         }
         catch (InterruptedException ex)
         {
          // do nothing
         }
         Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
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

  final public ArrayList<Block> getBlokken() {
    return blokken;
  }

  final public int getScore() {
    return score;
  }

  final public int getLines() {
    return lines;
  }



  @Override
  public void keyPressed(KeyEvent e) {
    int key = e.getKeyCode();
    if (key == KeyEvent.VK_RIGHT) {
      moveRight();
    } else if (key == KeyEvent.VK_LEFT) {
      moveLeft();
    } else if (key == KeyEvent.VK_UP) {
      CurrentBlock.rotate();
      Direction = 3;
    } else if (key == KeyEvent.VK_DOWN) {
      moveDown();
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

  //Beweegt de huidige blok naar rechts indien en niets rechts van staat
  private void moveRight() {
    if (blokken.size() > 1) {
      for (int i = 0; i < blokken.size() - 1; i++) {
        for (int u = 1; u <= 4; u++) {
          for (int o = 1; o <= 4; o++) {
            if (CurrentBlock.getX(u) + tileSize == blokken.get(i).getX(o) && CurrentBlock.getY(u) == blokken.get(i).getY(o)) {
//                                System.out.println("check------------------------------------------------------------------------");
              clear = false;
              Direction = 0;
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

  //Beweegt de huidige blok naar links indien en niets links van staat
  private void moveLeft() {
    if (blokken.size() > 1) {
      for (int i = 0; i < blokken.size() - 1; i++) {
        for (int u = 1; u <= 4; u++) {
          for (int o = 1; o <= 4; o++) {
            if (CurrentBlock.getX(u) - tileSize == blokken.get(i).getX(o) && CurrentBlock.getY(u) == blokken.get(i).getY(o)) {
//                                System.out.println("check------------------------------------------------------------------------");
              clear = false;
              Direction = 1;
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
              Direction = 2;
              collision = true;
            }
          }
        }
      }
    }
    if (clear == true) {
      CurrentBlock.moveDown();
      for (int u = 1; u <= 4; u++) {
//                    System.out.println("onder------------------------------------------------------------------------" + CurrentBlock.getY(u));
        if (CurrentBlock.getY(u) == 510) {
          collision = true;
        }
      }
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
        this.fireNewScoreEvent(new NewScoreEvent(this));
      }
    }
  }

  private void UpdateGrid() {
    boolean gameover = false;

    if (collision == true) {
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

            gameover = true;
            blokken.clear();
            System.out.println("                                            GAME OVER");
            System.out.println("---------------------------------------------------------------------------------------------------------------------");
            JOptionPane.showConfirmDialog(null, "Your score is " + TetrisUI.tetrisSidebar.score + " and the number of lines was " + TetrisUI.tetrisSidebar.lines + ".", "Congratulations !", JOptionPane.PLAIN_MESSAGE);
          }
        }
      }
      if (gameover == true) {
      }
      collision = false;
    }
  }

  /**
   * Methode om een nieuwe, willekeurige block te genereren.
   * @return Block een willekeurige block
   */
  protected final Block getRandomBlock() {
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

    Block newRandomBlock;
    try {
      // Instantieer random block
      System.out.println("Get constructor...");
      Constructor randomBlockConstructor = randomBlockClass.getConstructor();
      System.out.println("Constructor created...");
      newRandomBlock = (Block) randomBlockConstructor.newInstance();
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

//    public void newBlockEventOccurred(NewBlockEvent e) {
//    }

  public final void fireNewBlockEvent(NewBlockEvent evt) {
    Object[] listeners = listenerList.getListenerList();
    int listenerCount = listeners.length;

    for (int i = 0; i < listenerCount; i += 2) {
      if (listeners[i] == NewBlockEventListener.class) {
        ((NewBlockEventListener) listeners[i + 1]).newBlockEventOccurred(evt);
      }
    }
  }
}

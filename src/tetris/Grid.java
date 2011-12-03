package tetris;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.lang.reflect.Constructor;

public class Grid extends JPanel implements KeyListener {

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
  private int CurrentBlockIndex = 0;
  public static int randomNum = (int) (Math.random() * 7);
  private int Direction;
  private boolean collision = false;
  Block CurrentBlock = new Lblock((int) horTiles / 2 * tileSize, (int) 0);
  ArrayList<Block> blokken = new ArrayList<Block>();

//     final public void start(){
//            //starts a new thread
//            Thread th= new Thread(this);
//            //starts the thread
//            th.start();
//     }
//
//     public void run(){
//         Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
//         boolean clear= false;
//         while(true){
//             Direction = 2;
//              if (CurrentBlockIndex != 0){
//                    for (int i=0; i<CurrentBlockIndex; i++){
//			for (int u = 1; u<=4; u++){
//				for (int o = 1; o<=4;o++) {
//                                    if(blokken.get(CurrentBlockIndex).getX(u) == blokken.get(i).getX(o) && blokken.get(CurrentBlockIndex).getY(u) + tileSize == blokken.get(i).getY(o) ||
//                                       blokken.get(CurrentBlockIndex).getY(u) == 510) {
////                                    System.out.println("check------------------------------------------------------------------------");
//                                    clear = false;
//                                    Direction = 2;
//                                    collision = true;
//                                    }
//                                }
//                        }
//                    }
//             }
//             if (clear == true){
////                 System.out.println("onder------------------------------------------------------------------------");
//                 blokken.get(CurrentBlockIndex).moveDown();
//             }
//             clear = true;
//          try
//         {
//              Thread.sleep (200);
//         }
//         catch (InterruptedException ex)
//         {
//          // do nothing
//         }
//         Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
//         CheckCollision();
//         this.repaint();
//
//         }
//     }
  public void keyPressed(KeyEvent e) {
    int key = e.getKeyCode();
    boolean clear = true;
    if (key == KeyEvent.VK_RIGHT) {
      if (CurrentBlockIndex != 0) {
        for (int i = 0; i < CurrentBlockIndex; i++) {
          for (int u = 1; u <= 4; u++) {
            for (int o = 1; o <= 4; o++) {
              if (blokken.get(CurrentBlockIndex).getX(u) + tileSize == blokken.get(i).getX(o) && blokken.get(CurrentBlockIndex).getY(u) == blokken.get(i).getY(o)) {
//                                    System.out.println("check------------------------------------------------------------------------");
                clear = false;
                Direction = 0;
              }
            }
          }
        }
      }
      if (clear == true) {
//                 System.out.println("rechts------------------------------------------------------------------------");
        blokken.get(CurrentBlockIndex).moveRight();
      }
      clear = true;
    } else if (key == KeyEvent.VK_LEFT) {
      if (CurrentBlockIndex != 0) {
        for (int i = 0; i < CurrentBlockIndex; i++) {
          for (int u = 1; u <= 4; u++) {
            for (int o = 1; o <= 4; o++) {
              if (blokken.get(CurrentBlockIndex).getX(u) - tileSize == blokken.get(i).getX(o) && blokken.get(CurrentBlockIndex).getY(u) == blokken.get(i).getY(o)) {
//                                    System.out.println("check------------------------------------------------------------------------");
                clear = false;
                Direction = 1;
              }
            }
          }
        }
      }
      if (clear == true) {
//                 System.out.println("links------------------------------------------------------------------------");
        blokken.get(CurrentBlockIndex).moveLeft();
      }
      clear = true;
    } else if (key == KeyEvent.VK_UP) {
      blokken.get(CurrentBlockIndex).rotate();
      Direction = 3;
    } else if (key == KeyEvent.VK_DOWN) {
      if (CurrentBlockIndex != 0) {
        for (int i = 0; i < CurrentBlockIndex; i++) {
          for (int u = 1; u <= 4; u++) {
            for (int o = 1; o <= 4; o++) {
              if (blokken.get(CurrentBlockIndex).getX(u) == blokken.get(i).getX(o) && blokken.get(CurrentBlockIndex).getY(u) + tileSize == blokken.get(i).getY(o)
                      || blokken.get(CurrentBlockIndex).getY(u) == 510) {
//                                    System.out.println("check------------------------------------------------------------------------");
                clear = false;
                Direction = 2;
                collision = true;
              }
            }
          }
        }
      }
      if (clear == true) {
//                 System.out.println("onder------------------------------------------------------------------------");
        blokken.get(CurrentBlockIndex).moveDown();
      }
      clear = true;
    }

    CheckCollision();
    this.repaint();
//         System.out.println("Direction is " + Direction);
    for (int i = 1; i <= 4; i++) {
//         System.out.println("coordinaten y zijn " + blokken.get(CurrentBlockIndex).getY(i) + " index is " + CurrentBlockIndex);
    }

  }

  public void keyTyped(KeyEvent e) {
//        throw new UnsupportedOperationException("Not supported yet.");
  }

  public void keyReleased(KeyEvent e) {
//        throw new UnsupportedOperationException("Not supported yet.");
  }

  //getters
  final public int getgridHeight() {
    return gridHeight;
  }

  final public int getgridWidth() {
    return gridWidth;
  }

  final public ArrayList<Block> getBlokken() {
    return blokken;
  }

  //initialiseert het grid
  public Grid() {
    // Constructor
    this.setSize(gridWidth, gridHeight);
    blokken.add(CurrentBlock);
    UpdateGrid();
//         start();
  }

  private void drawTiles(Graphics g) {
    // Tekent de vierkante vakjes (met een grootte van tileSize * tileSize)
    for (int x = 0; x < horTiles; x++) {
      for (int y = 0; y < verTiles; y++) {
        // De randkleur:
        g.setColor(tileColor);
        g.drawRect(x * tileSize, y * tileSize, tileSize, tileSize);
        // De vulkleur:
        g.setColor(gridColor);
        g.fillRect(x * tileSize + 1, y * tileSize + 1, tileSize - 1, tileSize - 1);
      }
    }
  }

  //tekent alle onderdelen
  @Override
  public void paintComponent(Graphics g) {
    drawTiles(g);
    drawBlocks(g);
    for (int x = 0; x < horTiles; x++) {
      for (int y = 0; y < verTiles; y++) {
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

  private void CheckCollision() {
    if (CurrentBlockIndex != 0) {
      for (int i = 0; i < CurrentBlockIndex; i++) {
        for (int u = 1; u <= 4; u++) {
          for (int o = 1; o <= 4; o++) {
//                                    if (blokken.get(CurrentBlockIndex).getX(u) == blokken.get(i).getX(o) && blokken.get(CurrentBlockIndex).getY(u) == blokken.get(i).getY(o)){
//                                          try
//                                         {
//                                              Thread.sleep (10);
//                                         }
//                                         catch (InterruptedException ex)
//                                         {
//                                          // do nothing
//                                         }
            if (Direction == 0
                    && ((blokken.get(CurrentBlockIndex).getX(u) + tileSize) == blokken.get(i).getX(o)) && ((blokken.get(CurrentBlockIndex).getY(u)) == blokken.get(i).getY(o))) {
//                                             blokken.get(CurrentBlockIndex).moveLeft();
            } else if (Direction == 1
                    && ((blokken.get(CurrentBlockIndex).getX(u) - tileSize) == blokken.get(i).getX(o)) && ((blokken.get(CurrentBlockIndex).getY(u)) == blokken.get(i).getY(o))) {
//                                             blokken.get(CurrentBlockIndex).moveRight();
            } else if (Direction == 2
                    && ((blokken.get(CurrentBlockIndex).getX(u)) == blokken.get(i).getX(o)) && ((blokken.get(CurrentBlockIndex).getY(u) + tileSize) == blokken.get(i).getY(o))
                    || blokken.get(CurrentBlockIndex).getY(u) == 510) {
//                                             blokken.get(CurrentBlockIndex).moveUp();
//                                             collision = true;
//                                             UpdateGrid();
            } else if (Direction == 3) {
              blokken.get(CurrentBlockIndex).rotate();
              blokken.get(CurrentBlockIndex).rotate();
              blokken.get(CurrentBlockIndex).rotate();
            }
//                                    }else
//                                        if (blokken.get(CurrentBlockIndex).getY(u) == 510){
////                                        blokken.get(CurrentBlockIndex).moveUp();
//                                        collision = true;
//                                        UpdateGrid();
//                                    }else if(Direction == 2 &&
//                                            ((blokken.get(CurrentBlockIndex).getX(u)) == blokken.get(i).getX(o)) && ((blokken.get(CurrentBlockIndex).getY(u) + tileSize) == blokken.get(i).getY(o)) ||
//                                            blokken.get(CurrentBlockIndex).getY(u) == 510){
////                                        blokken.get(CurrentBlockIndex).moveUp();
//                                        DownClear = false;
//                                        collision = true;
//
//                                    }

//                                }
          }
        }
      }
    } else {
      for (int i = 1; i <= 4; i++) {
        if (blokken.get(CurrentBlockIndex).getY(i) == 510) {
//                    System.out.println("Hallo van if loop met index 0");
          collision = true;
//                    UpdateGrid();
        }
      }
    }
    UpdateGrid();
  }

  /**
   * Kijkt na of er volledige horizontale lijnen zijn,
   * en verwijdert die.
   */
  private void CheckLines() {
    for (int t = 0; t <= 510; t += 30) {
      int HorizontalTiles = 0;

      for (int i = 0; i < CurrentBlockIndex + 1; i++) {
        for (int u = 1; u <= 4; u++) {
          if (blokken.get(i).getY(u) == t) {
            HorizontalTiles++;
          }
        }

      }
      if (HorizontalTiles == 10) {
        for (int i = 0; i < CurrentBlockIndex + 1; i++) {
          for (int u = 1; u <= 4; u++) {
            if (blokken.get(i).getY(u) == t) {
              blokken.get(i).setY(u, 5000);
            }
            if (blokken.get(i).getY(u) < t) {
              blokken.get(i).setY(u, blokken.get(i).getY(u) + 30);
            }
          }
        }
        TetrisUI.tetrisSidebar.lines++;
        TetrisUI.tetrisSidebar.score += 50;
        // Todo:
        // Trow Event line complete


//                RefreshSidebar();
//                TetrisUI.tetrisSidebar = TetrisUI.panelCreator.createSidebar(TetrisUI.gameUI);
      }
    }
  }

//     private void RefreshSidebar() {
//         TetrisUI.gameUI.getContentPane().remove(TetrisUI.tetrisSidebar);
//         TetrisUI.gameUI.invalidate();
//         TetrisUI.gameUI.validate();
//     }
  private void UpdateGrid() {
    boolean gameover = false;

    if (collision == true) {
      CheckLines();
      // Random blok / klasse
      CurrentBlock = this.getRandomBlock();

      if (CurrentBlockIndex != 0) {
        for (int u = 1; u <= 4; u++) {
          if (blokken.get(CurrentBlockIndex - 1).getY(u) < 30) {

            gameover = true;
            blokken.clear();
            System.out.println("                                            GAME OVER");
            System.out.println("---------------------------------------------------------------------------------------------------------------------");
            JOptionPane.showConfirmDialog(null, "Your score is " + TetrisUI.tetrisSidebar.score + " and the number of lines was " + TetrisUI.tetrisSidebar.lines + ".", "Congratulations !", JOptionPane.PLAIN_MESSAGE);
            try {
              Thread.sleep(100000);
            } catch (InterruptedException ex) {
              // do nothing
            }
          }
        }


      }
      if (gameover == true) {
      }
      collision = false;
      CurrentBlockIndex++;
      randomNum = (int) (Math.random() * 7);
//         TetrisUI.tetrisSidebar.sidePanel.removeAll();
      TetrisUI.tetrisSidebar.sidePanel.validate();
      TetrisUI.tetrisSidebar.sidePanel.repaint();
      System.out.println("Repaint is gebeurd");
    }

//             System.out.println("number is " + Grid.randomNum);

//             if(randomNum == 0){
//                 TetrisUI.tetrisSidebar.block = "Iblock";
//                 TetrisUI.tetrisSidebar = TetrisUI.panelCreator.createSidebar(TetrisUI.gameUI);
//             }else if(randomNum == 1){
//                 TetrisUI.tetrisSidebar.block = "Jblock";
//                 TetrisUI.tetrisSidebar = TetrisUI.panelCreator.createSidebar(TetrisUI.gameUI);
//             }else if(randomNum == 2){
//                 TetrisUI.tetrisSidebar.block = "Lblock";
//                 TetrisUI.tetrisSidebar = TetrisUI.panelCreator.createSidebar(TetrisUI.gameUI);
//             }else if(randomNum == 3){
//                 TetrisUI.tetrisSidebar.block = "Oblock";
//                 TetrisUI.tetrisSidebar = TetrisUI.panelCreator.createSidebar(TetrisUI.gameUI);
//             }else if(randomNum == 4){
//                 TetrisUI.tetrisSidebar.block = "Sblock";
//                 TetrisUI.tetrisSidebar = TetrisUI.panelCreator.createSidebar(TetrisUI.gameUI);
//             }else if(randomNum == 5){
//                 TetrisUI.tetrisSidebar.block = "Tblock";
//                 TetrisUI.tetrisSidebar = TetrisUI.panelCreator.createSidebar(TetrisUI.gameUI);
//             }else if(randomNum == 6){
//                 TetrisUI.tetrisSidebar.block = "Zblock";
//                 TetrisUI.tetrisSidebar = TetrisUI.panelCreator.createSidebar(TetrisUI.gameUI);
//             }
//             RefreshSidebar();

  }


  protected Block getRandomBlock() {
          // Random blok / klasse
      Class randomBlockClass;
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
        Constructor randomBlockConstructor = randomBlockClass.getConstructor(new Class[]{int.class, int.class});
        newRandomBlock = (Block) randomBlockConstructor.newInstance(new Object[]{blockX, blockY});
        blokken.add(newRandomBlock);
        return newRandomBlock;
      } catch (Exception e) {
        // Er zijn enkele exceptions die gecatched moeten worden,
        // maar normaal gezien doen die zich niet voor dus... return
        System.exit(0);
        return null;
      }
      
      
  }
}

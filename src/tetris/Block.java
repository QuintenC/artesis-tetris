/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tetris;

import java.awt.*;
/**
 *
 * @author vdb
 */
public interface Block {
    void setPosition(int x, int y);

    void draw(Graphics g);

    void moveDown();

    void moveLeft();

    void moveRight();

    void moveUp();

    void rotate();

    //setters
    void setX1(int x);
    void setX2(int x);
    void setX3(int x);
    void setX4(int x);
    void setX(int i, int x);

    void setY1(int y);
    void setY2(int y);
    void setY3(int y);
    void setY4(int y);
    void setY(int i, int x);

    void setSize(int size);

    void setColor(Color kleur);

    //getters
    int getX1();
    int getX2();
    int getX3();
    int getX4();
    int getX(int x);

    int getY1();
    int getY2();
    int getY3();
    int getY4();
    int getY(int y);
}

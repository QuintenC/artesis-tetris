package tetris;

import java.awt.*;


public class Sblock implements Block{

    private int x1 = 0;
    private int y1 = 0;
    private int x2 = 0;
    private int y2 = 0;
    private int x3 = 0;
    private int y3 = 0;
    private int x4 = 0;
    private int y4 = 0;
    private int size = 30;
    private int rotatenr = 1;
    private Color kleur = Color.GREEN;

    Sblock() {
        //contructor
    }

    Sblock(int x,int y) {
        //constructor
        this.setAllXCoord(x, x + 30, x, x -30);
        this.setAllYCoord(y, y, y + 30, y + 30);
    }

    public void draw(Graphics g){
        g.setColor(kleur);
        g.drawRect(x1, y1, size, size);
        g.fillRect(x1, y1, size, size);
        g.drawRect(x2, y2, size, size);
        g.fillRect(x2, y2, size, size);
        g.drawRect(x3, y3, size, size);
        g.fillRect(x3, y3, size, size);
        g.drawRect(x4, y4, size, size);
        g.fillRect(x4, y4, size, size);
    }

    public void moveUp(){
            this.setAllYCoord(y1 - 30, y2 - 30, y3 - 30, y4 - 30);
    }

    public void moveDown(){
            this.setAllYCoord(y1 + 30, y2 + 30, y3 + 30, y4 + 30);
    }

    public void moveRight(){
         if( x1<241 && x2<241 && x3<241 && x4<241){
            this.setAllXCoord(x1 + 30, x2 + 30, x3 + 30, x4 + 30);
        }
    }

    public void moveLeft(){
        if (x1 > 30){
            this.setAllXCoord(x1 - 30, x2 - 30, x3 - 30, x4 - 30);
        }
    }

    public void rotate(){
        if (x1 < 270){
            if (rotatenr == 1){
                this.setAllXCoord(x1, x2 - 30, x3 - 30, x4);
                this.setAllYCoord(y1, y2 + 30, y3 - 30, y4 - 60);
                rotatenr = 2;
            }else{
                this.setAllXCoord(x1, x2 + 30, x3 + 30, x4);
                this.setAllYCoord(y1, y2 - 30, y3 + 30, y4 + 60);
                rotatenr = 1;
            }
        }
    }

    final public void setX1(int x) {
        this.x1 = x;
    }

    final public void setY1(int y) {
        this.y1 = y;
    }

    final public void setX2(int x) {
        this.x2 = x;
    }

    final public void setY2(int y) {
        this.y2 = y;
    }

    final public void setX3(int x) {
        this.x3 = x;
    }

    final public void setY3(int y) {
        this.y3 = y;
    }

    final public void setX4(int x) {
        this.x4 = x;
    }

    final public void setY4(int y) {
        this.y4 = y;
    }

    final public void setAllXCoord (int x1, int x2, int x3, int x4){
        this.x1 = x1;
        this.x2 = x2;
        this.x3 = x3;
        this.x4 = x4;
    }

    final public void setAllYCoord (int y1, int y2, int y3, int y4){
        this.y1 = y1;
        this.y2 = y2;
        this.y3 = y3;
        this.y4 = y4;
    }

    final public void setX(int i, int x) {
        if (i == 1){
            this.x1 = x;
        }else if (i == 2){
            this.x2 = x;
        }else if (i == 3){
            this.x3 = x;
        }else if (i == 4){
            this.x4 = x;
        }
    }

    final public void setY(int i, int y) {
        if (i == 1){
            this.y1 = y;
        }else if (i == 2){
            this.y2 = y;
        }else if (i == 3){
            this.y3 = y;
        }else if (i == 4){
            this.y4 = y;
        }
    }

    final public void setSize(int size){
        this.size = size;
    }

    final public void setColor(Color kleur){
        this.kleur = kleur;
    }

    final public int getX1() {
        return x1;
    }

    final public int getY1() {
        return y1;
    }

    final public int getX2() {
        return x2;
    }

    final public int getY2() {
        return y2;
    }

    final public int getX3() {
        return x3;
    }

    final public int getY3() {
        return y3;
    }

    final public int getX4() {
        return x4;
    }

    final public int getY4() {
        return y4;
    }

    final public int getX(int x) {
        if (x == 1){
            return x1;
        }else if (x == 2){
            return x2;
        }else if (x == 3){
            return x3;
        }else if (x == 4){
            return x4;
        }else{
            return x1;
        }
    }

    final public int getY(int y) {
        if (y == 1){
            return y1;
        }else if (y == 2){
            return y2;
        }else if (y == 3){
            return y3;
        }else if (y == 4){
            return y4;
        }else{
            return y1;
        }
    }

}

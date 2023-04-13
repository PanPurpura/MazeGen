package com.example.mazegen;

import javafx.geometry.Point2D;

public class Cell {
    private boolean left;
    private boolean down;
    private boolean right;
    private boolean up;
    public Point2D leftUp;
    public boolean visited;
    public Point2D coordinates;

    public Cell(boolean left, boolean down, boolean right, boolean up, Point2D coordinates, Point2D leftUp){
        this.left = left;
        this.down = down;
        this.right = right;
        this.up = up;
        this.visited = false;
        this.leftUp = leftUp;
        this.coordinates = coordinates;
    }

    public boolean get_left()
    { return left; }
    public boolean get_down()
    { return down; }
    public boolean get_right()
    { return right; }
    public boolean get_up()
    { return up; }

    public void set_left(boolean variable)
    { left = variable; }

    public void set_down(boolean variable)
    { down = variable; }

    public void set_right(boolean variable)
    { right = variable; }

    public void set_up(boolean variable)
    { up = variable; }

}

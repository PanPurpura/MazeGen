package com.example.mazegen;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.util.LinkedList;
import java.util.Random;
import java.util.Stack;

public class Generator {

    public Cell[][] maze;
    public LinkedList<Cell> visited;
    private final int X = 20;
    private final int Y = 20;

    public Generator(int size, int m_wid, int m_hei) {
        int Y1 = Y;
        int X1 = X;

        maze = new Cell[size][size];
        visited = new LinkedList<Cell>();

        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                maze[i][j] = new Cell(true, true, true, true, new Point2D(i, j), new Point2D(X1, Y1));
                X1+= m_wid;
            }
            X1 = 20;
            Y1 += m_hei;
        }
    }

    public Cell getNeighbour(Cell cell, int mSize) {
        LinkedList<Cell> neighbours = new LinkedList<Cell>();
        int x = (int)cell.coordinates.getX();
        int y = (int)cell.coordinates.getY();

        if(y > 0 && !maze[x][y - 1].visited) {
            neighbours.add(maze[x][y-1]);
        }
        if(x > 0 && !maze[x-1][y].visited) {
            neighbours.add(maze[x-1][y]);
        }
        if(y < mSize-1 && !maze[x][y+1].visited) {
            neighbours.add(maze[x][y+1]);
        }
        if(x < mSize-1 && !maze[x+1][y].visited) {
            neighbours.add(maze[x+1][y]);
        }
        if(neighbours.size() > 0) {
            return neighbours.get(new Random().nextInt(neighbours.size()));
        }
        else
            return null;
    }

    public void removeWall(Cell current, Cell neighbour, GraphicsContext gc, int m_wid, int m_hei) {
        if ((current.coordinates.getX() == neighbour.coordinates.getX()) && (current.coordinates.getY() == neighbour.coordinates.getY() + 1))
        {
            current.set_left(false);
            neighbour.set_right(false);

            gc.setStroke(Color.DARKBLUE);
            gc.strokeLine(current.leftUp.getX(), current.leftUp.getY() + 1, current.leftUp.getX(), current.leftUp.getY() + (m_hei-1));

        }
        if ((current.coordinates.getX() == neighbour.coordinates.getX()) && (current.coordinates.getY() == neighbour.coordinates.getY() - 1))
        {
            current.set_right(false);
            neighbour.set_left(false);

            gc.setStroke(Color.DARKBLUE);
            gc.strokeLine(neighbour.leftUp.getX(), neighbour.leftUp.getY() + 1, neighbour.leftUp.getX(), neighbour.leftUp.getY() + (m_hei - 1));
        }
        if ((current.coordinates.getX() == neighbour.coordinates.getX() + 1) && (current.coordinates.getY() == neighbour.coordinates.getY()))
        {
            current.set_up(false);
            neighbour.set_down(false);

            gc.setStroke(Color.DARKBLUE);
            gc.strokeLine(current.leftUp.getX() + 1, current.leftUp.getY(), current.leftUp.getX() + (m_wid - 1), current.leftUp.getY());
        }
        if ((current.coordinates.getX() == neighbour.coordinates.getX() - 1) && (current.coordinates.getY() == neighbour.coordinates.getY()))
        {
            current.set_down(false);
            neighbour.set_up(false);

            gc.setStroke(Color.DARKBLUE);
            gc.strokeLine(neighbour.leftUp.getX() + 1, neighbour.leftUp.getY(), neighbour.leftUp.getX() + (m_wid - 1), neighbour.leftUp.getY());
        }
    }

    public void DFS(Cell start, int mSize, GraphicsContext gc, AnchorPane panel2, int m_wid, int m_hei) {
        Stack<Cell> stack = new Stack<Cell>();

        Cell temp;
        Cell curr = start;
        curr.visited = true;
        stack.push(curr);

        while (stack.size() > 0) {

            curr = stack.pop();
            if ((temp = getNeighbour(curr, mSize)) == null) {
                continue;
            }
            stack.push(curr);
            removeWall(curr, temp, gc, m_wid, m_hei);
            temp.visited = true;
            stack.push(temp);

        }
    }

}

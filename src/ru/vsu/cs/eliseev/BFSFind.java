package ru.vsu.cs.eliseev;

import java.util.LinkedList;
import java.util.Queue;

public class BFSFind {
    private static class Cell {
        public int row;
        public int col;
        public int step;

        public Cell(int row, int col, int step) {
            this.row = row;
            this.col = col;
            this.step = step;
        }

        @Override
        public String toString() {
            return "Cell{" +
                    "row=" + row +
                    ", col=" + col +
                    ", step=" + step +
                    '}';
        }
    }

    public static void bfsPathFoundJavaQueue(int firstRow, int firstCol, int[][] maze) {
        Queue<Cell> cells = new LinkedList<>();

        maze[firstRow][firstCol] = 1;
        cells.add(new Cell(firstRow, firstCol, 2));

        while (!cells.isEmpty()) {
            Cell temp = cells.poll();
            for (int r = temp.row - 1; r <= temp.row + 1; r++) {
                for (int c = temp.col - 1; c <= temp.col + 1; c++) {
                    if (r >= 0 && r < maze.length && c >= 0 && c < maze[0].length
                            && (r == temp.row || c == temp.col) && maze[r][c] == 0) {
                        maze[r][c] = temp.step;
                        cells.add(new Cell(r, c, temp.step + 1));
                    }
                }
            }
        }
    }

    public static void bfsPathFoundSimpleQueue(int firstRow, int firstCol, int[][] maze) {

        SimpleQueue<Cell> cells = new SimpleQueue<>();

        maze[firstRow][firstCol] = 1;
        cells.add(new Cell(firstRow, firstCol, 2));
        while (!cells.isEmpty()) {
            Cell current = cells.poll();
            for (int r = current.row - 1; r <= current.row + 1; r++) {
                for (int c = current.col - 1; c <= current.col + 1; c++) {
                    if (r >= 0 && r < maze.length && c >= 0 && c < maze[0].length
                            && (r == current.row || c == current.col) && maze[r][c] == 0) {
                        maze[r][c] = current.step;
                        cells.add(new Cell(r, c, current.step + 1));
                    }
                }
            }
        }
    }
}

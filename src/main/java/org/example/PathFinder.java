package org.example;

import java.util.Arrays;

public class PathFinder {
    private static boolean[][] path = new boolean[10][10];
    private static enum Direction {LEFT, RIGHT}
    private static Direction direction;

    public static void main(String[] args) {
        int[][] map = new int[][] {
                {0, 0, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 1, 1},
                {1, 1, 1, 0, 1, 1, 1, 0, 1, 1},
                {1, 0, 0, 0, 1, 0, 0, 0, 1, 1},
                {1, 0, 1, 1, 1, 1, 1, 0, 1, 1},
                {1, 0, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 0, 0, 0, 0, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 0, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 0, 0, 0, 0, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 0, 0, 0}
        };
        boolean pathFound = findPath(map, 0, 0, new boolean[10][10]);
        if (pathFound) {
            System.out.println("Found path:");
            for (int i = 0; i < path.length; i++) {
                System.out.print("[");
                for (int j = 0; j < path[0].length; j++) {
                    System.out.print(((path[i][j]) ? "1" : "0") + ((j < map[0].length - 1) ? ", " : ""));
                }
                System.out.println("]");
            }
        }
    }

    public static boolean findPath(int[][] map, int cR, int cC, boolean[][] visited) {
        if (cR == map.length - 1 && cC == map[0].length - 1) {
            path = visited;
            return true;
        }
        if (cR < 0 || cR > map.length || cC < 0 || cC > map[0].length) return false;

        visited[cR][cC] = true;

        if (cC-1 >= 0 && map[cR][cC-1] == 0 && !visited[cR][cC-1]) { // left
            boolean hasSolution = findPath(map, cR, cC-1, visited);
            if (hasSolution) {
                direction = Direction.LEFT;
                return true;
            }
        }

        if (cC+1 < map[0].length && map[cR][cC+1] == 0 && !visited[cR][cC+1]) { // right
            boolean hasSolution = findPath(map, cR, cC+1, visited);
            if (hasSolution) {
                direction = Direction.RIGHT;
                return true;
            }
        }

        if (cR-1 >= 0 && map[cR-1][cC] == 0 && !visited[cR-1][cC]) { // up
            boolean hasSolution = findPath(map, cR-1, cC, visited);
            if (hasSolution) return true;
        }

        if (cR+1 < map.length && map[cR+1][cC] == 0 && !visited[cR+1][cC]) { // down
            boolean hasSolution = findPath(map, cR+1, cC, visited);
            if (hasSolution) return true;
        }

        visited[cR][cC] = false;

        return false;
    }
}









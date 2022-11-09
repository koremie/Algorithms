package ua.lviv.iot.algorithms.ijones;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class IJONES {
    char[][] tiles;
    int H;
    int W;
    int[][] meme;

    public char[][] readTilesFromFile(File file) {
        char[][] tiles;
        try (Scanner scanner = new Scanner(file)) {
            int rows = scanner.nextInt();
            tiles = new char[scanner.nextInt()][rows];
            scanner.nextLine();

            int row = 0;
            while (scanner.hasNextLine()) {
                tiles[row] = scanner.nextLine().toCharArray();
                row++;
            }
            return tiles;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private int countPaths(int y, int x, char[][] tiles) {

        if (x == 0)
            return 1;
        else if (meme[y][x] != 0) {
            return meme[y][x];
        } else {
            ArrayList<int[]> paths = getPaths(y, x, tiles);
            int pathsCount = 0;

            for (int[] i : paths) {
                meme[i[0]][i[1]] = countPaths(i[0], i[1], tiles);
                pathsCount += meme[i[0]][i[1]];
            }
            return pathsCount;
        }
    }

    public ArrayList<int[]> getPaths(int y, int x, char[][] tiles) {
        char currVal = tiles[y][x];
        ArrayList<int[]> paths = new ArrayList<>();

        paths.add(new int[] { y, x - 1 });

        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < x; j++) {

                if (tiles[i][j] == currVal && !Arrays.equals((new int[] { i, j }), (new int[] { y, x - 1 }))) {
                    paths.add(new int[] { i, j });
                }
            }
        }
        return paths;
    }

    public int countPathsPublic(File file) {
        tiles = readTilesFromFile(file);
        H = tiles.length;
        W = tiles[0].length;
        meme = new int[H][W];

        int result = 0;

        if (tiles.length > 1) {
            result += countPaths(0, W - 1, tiles);
            result += countPaths(H - 1, W - 1, tiles);
            return result;
        } else {
            result += countPaths(0, W - 1, tiles);
            return result;
        }

    }

    public void writeToFile(File file, int value) {
        try (FileWriter writer = new FileWriter(file, Charset.defaultCharset())) {
            writer.write("hmm: " + value);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        IJONES ijones = new IJONES();
        ijones.writeToFile(
                new File(Paths.get("").toAbsolutePath().toString() + "\\src\\test\\resources", "ijones.out.1.txt"),
                ijones.countPathsPublic(new File(Paths.get("").toAbsolutePath().toString() + "\\src\\test\\resources",
                        "ijones.in.1.txt")));
    }
}

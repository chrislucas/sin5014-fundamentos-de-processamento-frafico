package com.br.editor.utils.exercicies;

import java.util.ArrayList;

/**
 * Created by C.Lucas on 27/05/2017.
 */
public class FloodFill {


    static final int px [] = {1,-1,0,0,-1,1,-1,1};      // 8-neighbors
    static final int py [] = {0,0,1,-1,-1,-1,1,1};
    static int [][] matrix;
    static boolean[][] visited;
    static int w, h;

    public static class Pixel {
        int x, y;
        public Pixel(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public static boolean isBorder(int x, int y) {
        //if(h < 0 || h > w || w < 0 || w > h)
            //return true;
        return (x < 0 || x > w || y < 0 || y > h);
    }

    public static ArrayList<Pixel> neighbors(int x, int y) {
        ArrayList<Pixel> pixels = new ArrayList<>();
        for(int i=0; i<px.length; i++) {
            int nx = x+px[i], ny = y+py[i];
            if(isBorder(nx, ny))
                continue;
            pixels.add(new Pixel(nx, ny));
        }
        return pixels;
    }

    public static void dfs(int x, int y, int counter) {
        for (int i = x; i <h ; i++) {
            for (int j = y; j <w ; j++) {
                if(!visited[i][j]) {
                    visited[i][j] = true;
                    ArrayList<Pixel> pixels = neighbors(i, j);
                    for(Pixel pixel : pixels) {
                        int nx = pixel.x, ny = pixel.y;
                        dfs(nx, ny, counter);
                    }
                }
            }
            counter++;
        }
    }

    public static void init() {

    }

    public static void main(String[] args) {

    }

}

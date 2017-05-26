package geom;

import com.sun.deploy.pings.Pings;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by r028367 on 26/05/2017.
 * https://www.hackerrank.com/challenges/points-on-a-line
 * DONE
 */
public class PointsOnVHLine {

    public static class Point2D {
        int x, y;
        Point2D(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public static void solver() {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            int cases = Integer.parseInt(bufferedReader.readLine());
            boolean colinearX = true, colinearY = true;
            int oldPoint [] = new int[2];
            PrintWriter  writer = new PrintWriter(new OutputStreamWriter(System.out), true);
            for(int i=0;i<cases; i++) {
                StringTokenizer tokenizer = new StringTokenizer(bufferedReader.readLine(), " ");
                int x = Integer.parseInt(tokenizer.nextToken());
                int y = Integer.parseInt(tokenizer.nextToken());
               // points2D.add(new Point2D(x, y));
                if(i==0) {
                    oldPoint[0] = x;
                    oldPoint[1] = y;
                }

                else {
                    if(x != oldPoint[0])
                        colinearX = false;
                    if(y != oldPoint[1])
                        colinearY = false;
                }
            }
            writer.println(colinearX || colinearY ? "YES" : "NO");
        } catch (IOException ioex) {}
    }

    public static void main(String[] args) {
        solver();
    }

}

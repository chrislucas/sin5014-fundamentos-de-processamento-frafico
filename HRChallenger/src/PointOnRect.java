import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by r028367 on 13/06/2017.
 * https://www.hackerrank.com/challenges/points-on-rectangle
 */
public class PointOnRect {

    static class Point2D {
        int x, y;
        public Point2D(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public boolean equals(Point2D that) {
            return this.x == that.x && this.y == that.y;
        }
    }

    public static void solver() {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            int cases = Integer.parseInt(bufferedReader.readLine());
            while(cases > 0) {
                int n = Integer.parseInt(bufferedReader.readLine());
                boolean isOnTheEdgeRect = true;
                ArrayList<Point2D> points = new ArrayList<>();
                int maxX = -((1<<31)-1), maxY = -((1<<31)-1), minX = ((1<<31)-1), minY = ((1<<31)-1);
                for(int i=0; i<n; i++) {
                    StringTokenizer tokenizer = new StringTokenizer(bufferedReader.readLine(), " ");
                    int x, y;
                    x = Integer.parseInt(tokenizer.nextToken());
                    y = Integer.parseInt(tokenizer.nextToken());
                    maxX = Math.max(x, maxX);
                    maxY = Math.max(y, maxY);
                    minX = Math.min(x, minX);
                    minY = Math.min(y, minY);
                    points.add(new Point2D(x, y));
                }

                for(Point2D point : points) {
                    if(point.x != maxX && point.x != minX && point.y != maxY && point.y != minY) {
                        isOnTheEdgeRect = false;
                        break;
                    }
                }
                System.out.println(isOnTheEdgeRect ? "YES" : "NO");
                cases--;
            }
        } catch (IOException ieox) {}
    }

    public static void main(String[] args) {
        solver();
    }

}

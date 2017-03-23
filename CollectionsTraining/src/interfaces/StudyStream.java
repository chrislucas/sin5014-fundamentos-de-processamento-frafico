package interfaces;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Created by r028367 on 10/03/2017.
 */
public class StudyStream {


    public static void learnFilterStream() {
        ArrayList<Integer> I = new ArrayList<>();
        for(int i=10; i<25; i++)
            I.add(i);
        I.stream().filter(new Predicate<Integer>() {
            @Override
            public boolean test(Integer integer) {
                return integer % 2 == 0;
            }
        }).forEach(System.out::println);

        Optional n = I.stream().filter(i -> i.intValue() < 20 ).max(new Comparator<Integer>() {
            @Override
            public int compare(Integer A, Integer B) {
                return A == B ? 0 : A > B ? 1 : -1;
            }
        });

        System.out.println(n.toString());

    }


    public static class Point2D implements Comparable<Point2D> {
        private int x, y;
        public Point2D(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public int compareTo(Point2D that) {
            int Tx = this.x, Ty = this.y;
            return Tx > that.x && Ty > that.y ? 1 : 0;
        }

        @Override
        public String toString() {
            return String.format("%d %d", x, y);
        }
    }

    public static void learningStream2() {
        ArrayList<Point2D> points = new ArrayList<>();
        points.add(new Point2D(1,2));
        points.add(new Point2D(3,4));
        points.add(new Point2D(10,-1));

    }

    public static void learningStream3() {
        ArrayList<Point2D> points = new ArrayList<>();
        points.add(new Point2D(1, 2));
        points.add(new Point2D(3, 4));
        points.add(new Point2D(10, -1));
        /*
        points.stream().forEach( point -> {
                System.out.println(point);
            }
        );
        */
        //points.forEach( System.out::println );
        points.forEach( (x) -> {
                System.out.println(x);
            }
        );
    }


    public static void main(String[] args) {
        learningStream3();
    }
}

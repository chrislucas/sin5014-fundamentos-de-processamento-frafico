import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by r028367 on 31/03/2017.
 */
public class LearningRunnable {

    public static int [][] matrix;

    public class TransverveMatrix implements Callable<int [][]> {
        private int matrix [][];
        private int i, j, value, w, h;
        public TransverveMatrix(int [][] matrix, int h, int w, int i, int j, int value) {
            this.matrix = matrix;
            this.i      = i;
            this.j      = j;
            this.h      = h;
            this.w      = w;
            this.value  = value;
        }

        @Override
        public int[][] call() throws Exception {
            for(int x=i; x<h; x++) {
                for(int y=j; y<w; y++) {
                    matrix[x][y] = value++;
                }
            }
            return matrix;
        }
    }

    public class TransverveMatrix2 implements Callable<Integer> {
        private int matrix [][];
        private int i, j, value, w, h;
        public TransverveMatrix2(int [][] matrix, int h, int w, int i, int j, int value) {
            this.matrix = matrix;
            this.i      = i;
            this.j      = j;
            this.h      = h;
            this.w      = w;
            this.value  = value;
        }

        @Override
        public Integer call() throws Exception {
            for(int x=i; x<h; x++) {
                for(int y=j; y<w; y++) {
                    matrix[x][y] = value++;
                }
            }
            return value;
        }
    }

    private static void exampleLambda() {
        Runnable runnable = () -> {
            System.out.println("Teste");
        };
        runnable.run();
    }


    private static void fillMatrix() {
        // Java 8 eh lindo
        matrix = new int[6][6];
        ExecutorService executorService = Executors.newFixedThreadPool(9);
        List<Future<int [][]>> tasks = new ArrayList<>();
        int limitW = 3, limitH = 3
                ,i = 0, j= 0
                ,value = 1, h = 3, w = 3;
        int H = matrix.length, W = matrix[0].length;
        for(int x=1; x<H+1; x++) {
            Callable<int[][]> tm = new LearningRunnable().new TransverveMatrix(matrix, h, w, i, j, value);
            Future<int[][]> futureTasks = executorService.submit(tm);
            i = (x / limitH) * limitH;
            j = (j + limitW) % W;
            h = i + limitH;
            w = j + limitW;
            value += H;
            tasks.add(futureTasks);
        }

        for(Future<int [][]> task : tasks) {
            while ( ! task.isDone() ) {}
            try {
                matrix = task.get();
                executorService.shutdown();
            } catch (InterruptedException e) {
                System.out.printf("InterruptedException: %s\n", e.getCause());
            } catch (ExecutionException e) {
                System.out.printf("ExecutionException: %s\n", e.getCause());
            }
        }

        for(i=0; i<H; i++) {
            for(j=0; j<W; j++) {
                System.out.printf("%d ", matrix[i][j]);
            }
            System.out.println("");
        }
    }

    private static void fillMatrix2() {
        matrix = new int[12][12];
        int limitW = 3, limitH = 3, i = 0, j= 0, value = 1, h = 3, w = 3;
        int H = matrix.length, W = matrix[0].length;
        ExecutorService executorService = Executors.newFixedThreadPool(H);
        for(int x=1; x<H+1; x++) {
            Callable<Integer> tm = new LearningRunnable().new TransverveMatrix2(matrix, h, w, i, j, value);
            Future<Integer> futureTasks = executorService.submit(tm);
            while( ! futureTasks.isDone() ) {}
            try {
                value = futureTasks.get();
            } catch (InterruptedException e) {
                System.out.printf("InterruptedException: %s\n", e.getCause());
            } catch (ExecutionException e) {
                System.out.printf("ExecutionException: %s\n", e.getCause());
            }

            i = (x / limitH) * limitH;
            j = (j + limitW) % W;
            h = i + limitH;
            w = j + limitW;
        }
        executorService.shutdown();

        for(i=0; i<H; i++) {
            for(j=0; j<W; j++) {
                System.out.printf("%d ", matrix[i][j]);
            }
            System.out.println("");
        }
    }

    public static void main(String[] args) {
        fillMatrix2();
    }

}

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
        public TransverveMatrix(int [][] matrix, int w, int h, int i, int j, int value) {
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

    public static void main(String[] args) {
        // Java 8 eh lindo
        matrix = new int[9][9];


        Runnable runnable = () -> {
            System.out.println("Teste");
        };
        runnable.run();

        ExecutorService executorService = Executors.newFixedThreadPool(9);
        List<Future<int [][]>> tasks = new ArrayList<>();
        int limitW = 3, limitH = 3, initI = 0, initJ = 0, value = 1, accH = 3, accW = 3;
        int H = matrix.length, W = matrix[0].length;
        for(int i=0; i<9; i++) {
            Callable<int[][]> tm = new LearningRunnable()
                    .new TransverveMatrix(matrix, accH, accW, initI, initJ, value);
            Future<int [][]> futureTasks = executorService.submit(tm);
            initI = (initI + limitH);
            initJ = (initJ + limitW);
            value += limitW;
            accH = (accH + limitH) % (limitH+1);
            accW = (accW + limitW) % (limitW+1);
            tasks.add(futureTasks);
        }

        for(Future<int [][]> task : tasks) {
            while ( ! task.isDone() ) {
                System.out.println(task.hashCode());
            }
            try {
                matrix = task.get();
                executorService.shutdown();
            } catch (InterruptedException e) {
                System.out.printf("InterruptedException: %s\n", e.getCause());
            } catch (ExecutionException e) {
                System.out.printf("ExecutionException: %s\n", e.getCause());
            }
        }


/*
        Future<int [][]> future = executorService.submit(tm);
        executorService.execute(new Runnable() {
                @Override
                public void run() {

                }
            }
        );
        */
    }

}

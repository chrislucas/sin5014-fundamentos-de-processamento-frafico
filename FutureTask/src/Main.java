import java.util.Random;
import java.util.UUID;
import java.util.concurrent.*;

/**
 * Created by r028367 on 26/01/2017.
 *
 * Estudo sobre a interface Future e a classe FutureTask<T>
 *     https://docs.oracle.com/javase/7/docs/api/java/util/concurrent/FutureTask.html
 *http://www.devmedia.com.br/processamento-assincrono-em-java-com-future-e-futuretask/33851
 */
public class Main {

    /*
    * A interface Callable permite executar um recurso de forma paralela, parecido
    * com a interface Runnable, porem o metodo call definido pela interface Callable
    * nos permite retornar um valor
    * */

    public static class Randomic implements Callable<Integer> {
        @Override
        public Integer call() throws Exception {
            Random random = new Random();
            return random.nextInt(100);
        }
    }

    public static void runTestFutureTask() {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        Randomic rdm = new Randomic();
        Future<Integer> future = executorService.submit(rdm);
        while( ! future.isDone() ) {
            System.out.println("Tarefa incompleta");
        }

        System.out.println("Tarefa completa");
        try {
            long f = (long) future.get();
            System.out.println(f);
            executorService.shutdown();
        } catch (InterruptedException e) {
            System.out.printf("InterruptedException: %s\n", e.getCause());
        } catch (ExecutionException e) {
            System.out.printf("ExecutionException: %s\n", e.getCause());
        }
    }

    public enum STATUS_TROCA {
        ACEITO(1), RECUSO(2), EM_AVALIACAO(3);
        private final int status;
        STATUS_TROCA(int status) {
            this.status = status;
        }
    }

    public enum STATUS_TRANSACAO {
        PENDENTE_ENVIO_WEB(1), PENDENTE_RESPOSTA_WEB(2), PROCESSADO_WEB(3), ERRO_WEB(4), PROCESSADO_SCL(5);
        private final int status;
        STATUS_TRANSACAO(int status) {
            this.status = status;
        }
    }

    public static class Transacao {
        private STATUS_TROCA troca;
        private STATUS_TRANSACAO transacao;

        public Transacao(STATUS_TROCA troca, STATUS_TRANSACAO transacao) {
            this.troca = troca;
            this.transacao = transacao;
        }

        public STATUS_TROCA getTroca() {
            return troca;
        }

        public STATUS_TRANSACAO getTransacao() {
            return transacao;
        }
    }

    public static void runTestEnum() {
        Transacao transacao = new Transacao( STATUS_TROCA.ACEITO, STATUS_TRANSACAO.PENDENTE_ENVIO_WEB);
        System.out.printf("%s %s\n", transacao.getTransacao(), transacao.getTroca());
    }

    public static void testUUID() {
        UUID uuid = UUID.randomUUID();
        System.out.println(uuid.toString());
        System.out.println(uuid.version());
        System.out.println(uuid.variant());
    }

    public static void main(String[] args) {
        runTestEnum();
        testUUID();
    }
}

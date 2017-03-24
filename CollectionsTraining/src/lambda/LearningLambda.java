package lambda;

/**
 * Created by r028367 on 20/03/2017.
 * https://docs.oracle.com/javase/tutorial/java/javaOO/lambdaexpressions.html
 * https://rodrigouchoa.wordpress.com/2014/05/20/novidades-do-java-8-lambda-expressions/
 * http://www.devmedia.com.br/como-usar-funcoes-lambda-em-java/32826
 * https://www.tutorialspoint.com/java8/java8_lambda_expressions.htm
 */
public class LearningLambda {

    public interface Operation {
        public int binaryOp(int a, int n);
    }

    public static int binaryOp(int a, int b, Operation operation) {
        return operation.binaryOp(a, b);
    }

    public static void main(String[] args) {
        Operation op = (a, b) -> a << b;
        System.out.println(binaryOp(2, 3, op));
    }

}

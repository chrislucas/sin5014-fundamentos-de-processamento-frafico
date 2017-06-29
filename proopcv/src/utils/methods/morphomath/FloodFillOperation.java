package utils.methods.morphomath;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

/**
 * Created by C_Luc on 22/06/2017.
 */
public class FloodFillOperation {

    /**
     * Ao aplicarmos o algoritmo de Flood Fill, estamos procurando pixels na
     * imagem e comparando-os com um pixel denominado 'seed' (esse passado como argumento para os metodos abaixo) ou
     * comparando um pixel central na imagem com os seus vizinhos (4-neighbors ou 8-neighbors)
     *
     * Chamamos de flood fill fixed range, quando comparamos o seed com os viziinhos de um dado pixel ou
     * de floating range quando comparamos um pixel da imagem com os seus vizinhos
     * */


    /**
     * A funcao Imgproc.floodFill aceita parametros de limiar inferior para um valor procurado. Um pixel P(x, y)
     * pode ser considerado na busca caso esteja em P(x',y') - inferior < P(x,y) < P(x',y') + superior
     *
     * P(x, y) eh um pixel na posicao (x, y) que esta sendo testado, queremos saber se ele faz parte do mesmo componente
     * que o ponto 'seed'
     * P(x',y') eh um ponto ja conhecido, que sabemos que faz parte do mesmo componente
     * */
    public static void floodfill(Mat src, Mat mask, Point seed, Scalar scalar) {
        Imgproc.floodFill(src, mask, seed, scalar);
    }

    public static void floodfill(Mat src, Mat mask, Scalar scalar) {
        Imgproc.floodFill(src, mask, new Point(0,0), scalar);
    }

    public static void flood(Mat src, Mat mask, int x, int y) {

    }
}

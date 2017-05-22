package cv.studies;

import org.opencv.core.*;

/**
 * Created by C.Lucas on 21/05/2017.
 */
public class Sample1 {

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void propMatrixOpenCV1() {
        int rows = 10, cols = 20, type = CvType.CV_8UC3;
        Mat mat = new Mat(rows, cols, type/*, new Scalar(10)*/);
        int w = cols, h = rows;
        Size dim = new /*Size()*/ Size(w, h);
        Mat mat2 = new Mat(dim, type);
        System.out.println(mat);
        /**
         *
         * Matrix possui uma propriedade chama isCont
         * que indica se a mesma usa um espaco extra para
         * armazenar uma imagem
         * */
        System.out.println(mat.isContinuous());
        Range rowRange = new Range(0, 4);
        Range colRange = new Range(2, 4);
        /**
         * isSubmat indica se uma matrix 'n' eh uma submatriz de 'a'
         * ou seja se 'b' foi criada a partir de a
         * */
        Mat mat3 = new /*Mat(mat2, rowRange)*/  Mat(mat2, rowRange, colRange);
        System.out.println(mat3);
        System.out.println(mat3.isSubmatrix());
    }

    public static void matConstructorOCV() {
        double scl [] = {128,3,4};
        //new Size(col | w, lin | h);

        int unsignedCvType [] = {CvType.CV_8U, CvType.CV_8UC1
                ,CvType.CV_8UC2, CvType.CV_8UC3, CvType.CV_8UC4};
        System.out.println("Unsigned CV Type");
        // CV_8C N. O valor de N aumenta em N vezes o tamanho da largura
        // da matriz
        for(int tp : unsignedCvType) {
            // criando uma matriz com um tamanho especifico
            // definindo o valor que essa matriz vai ter
            Mat image = new Mat(3,3/*new Size(3,3)*/, tp, new Scalar(scl));
            System.out.println(image);
            System.out.println(image.dump());
            System.out.println("");
        }

        int signedCvType [] = {CvType.CV_8S, CvType.CV_8SC1
                ,CvType.CV_8SC2, CvType.CV_8SC3, CvType.CV_8SC4};

        System.out.println("Signed CV Type");
        for(int tp : signedCvType) {
            Mat image = new Mat(new Size(3,3), tp, new Scalar(scl));
            System.out.println(image);
            System.out.println(image.dump());
            System.out.println("");
        }

    }

    public static void main(String[] args) {
        //propMatrixOpenCV1();
        matConstructorOCV();
    }

}

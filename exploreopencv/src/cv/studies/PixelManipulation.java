package cv.studies;

import org.opencv.core.Mat;

/**
 * Created by C.Lucas on 21/05/2017.
 */

// se sua classe for uma espcializacao de uma outra classe
// seja ela abstrata ou concreta, e essa outra classe possuir
// um bloco estatico, esse sera executado na classe espcialista

public class PixelManipulation extends LoadOpenCvLib {

    public static void pxTest1(Mat image) {
        if(image != null) {
            for (int i=0; i<image.rows(); i++) {
                for(int j=0; j<image.cols(); j++)
                    // para os canais blue(1), green(2), red(3)_
                    image.put(i, j, new byte[] {1,2,3});
            }
            System.out.println(image);
            System.out.println(image.dump());
        }
    }


    public static byte [] toByteArray(Mat mat) {
        byte [] buffer = {};
        if(mat != null) {
            /**
             * funcao: size vs total
             *
             * */
            int totalBytes = (int) (mat.total() * mat.elemSize());
            buffer = new byte[totalBytes];
            mat.get(0,0, buffer);
            // esse procedimento itera por todos os pixels da imagem
            for (int i = 0; i <totalBytes ; i++) {
                // sempre o i-esimo byte sera transformado em zero
                // OpenCV armazena a imagem usando a seguencia BGR = canal B = 0
                if(i%3==0)
                    buffer[i] = 0;
            }
            mat.put(0, 0, buffer);
        }
        return buffer;
    }

    public static int unsignedValue(long value) {
        return (int) (value & 0xff);
    }

    public static void test() {
        for(int i=1<<8; i<=(1<<12); i++) {
            System.out.println(unsignedValue(i));
        }
    }


    public static void main(String[] args) {
        //pxTest1(new Mat(new Size(3,3), CvType.CV_8U));
        test();
    }
}

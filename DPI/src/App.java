import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by C.Lucas on 24/03/2017.
 */
public class App {

    public static void main(String[] args) {
        Map<Integer, Integer> histogram = mockHistogram();
        HistogramaImageGrayScale.draw(histogram);
    }


    public static Map<Integer, Integer> mockHistogram() {
        Random r = new Random();
        // int randomNum = rand.nextInt((max - min) + 1) + min;
        int min = 2500, max = 100000;
        int acc, totalPixels = r.nextInt(max - min) + min;
        Map<Integer, Integer> histogram = new HashMap<>();
        acc = totalPixels;
        int repeat = 0;
        int maxColor = 255;
        while(acc > 0 && repeat < 2) {
            int colorPixel = r.nextInt(maxColor);
            int quantityPixelsOnImage = r.nextInt(acc) + 1;
            acc -= quantityPixelsOnImage;
            histogram.put(colorPixel, quantityPixelsOnImage);
        }
        return histogram;
    }
}

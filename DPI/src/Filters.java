import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by C.Lucas on 27/03/2017.
 */
public class Filters {

    public static final ActionListener filterMean = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Mean");
        }
    };

    public static final ActionListener filterMedian = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Median");
        }
    };

}

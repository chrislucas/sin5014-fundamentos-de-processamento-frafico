package transformation;

/**
 * Created by C.Lucas on 04/04/2017.
 */
public class Trigonometry {


    public static double toRadian(double degree) {
        return degree * Math.PI / 180.0 ;
    }


    public static double toDegree(double radians) {
        return radians * 180 / Math.PI;
    }


    public static void main(String[] args) {
        System.out.println(Math.tan(toRadian(45)));
        System.out.println(toDegree(toRadian(45)));
    }



}

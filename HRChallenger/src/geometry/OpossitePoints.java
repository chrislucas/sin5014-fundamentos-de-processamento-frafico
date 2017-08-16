package geometry;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;

/**
 * Created by C_Luc on 15/07/2017.
 */
public class OpossitePoints {

    static int [][] positiveRotate = {
             {(int) cos(toRadians(90)), (int) -sin(toRadians(90))}
            ,{(int) sin(toRadians(90)), (int)  cos(toRadians(90))}
    };

    static int [][] negativeRotate = {
             {(int)  cos(toRadians(90)), (int) sin(toRadians(90))}
            ,{(int) -sin(toRadians(90)), (int) cos(toRadians(90))}
    };
}

/**
 * Created by C.Lucas on 19/03/2017.
 * http://pixelyzer.com/inches_to_pixels.html
 */
public class DPI {

    public static void exec() {
        double inch = 2.54;
        double [][] variables = {
             {3.0, 5.0,  1, 300.0}
            ,{3.0, 5.0,  2, 300.0}
            ,{3.0, 5.0,  2, 600.0}
            ,{6.0, 10.0,  1, 300.0}
            ,{6.0, 10.0,  2, 300.0}
            ,{6.0, 10.0,  2, 600.0}
        };

        for(int i=0; i<variables.length; i++) {
            double heigth   = variables[i][0];
            double width    = variables[i][1];
            double bytes    = variables[i][2];
            double dpi      = variables[i][3];
            /**
             * 1 pol = 2.54 cm
             * 1 cm = 0,3937007874015748031496062992126 pol
             * Converter cm para polegadada
             * cm/pol
             * a cada 1 cm temos 0.3937 polegadas
             * */
            //double pixelInHeight = heigth/inch * dpi;//Math.floor(heigth/inch * dpi) ;
            //double pixelInWidth  = width/inch * dpi; //Math.floor(width/inch * dpi) ;
            //System.out.println(pixelInHeight * pixelInWidth *  bytes);
            System.out.println(((heigth/inch*dpi) * (width/inch*dpi)) * bytes);
            System.out.println( (heigth * (1/inch) * dpi ) * (width * (1/inch) * dpi ) * bytes);;
            // OU
            double T1 = heigth * 1 / inch;
            double T2 = width * 1 / inch;
            double P1 = T1 * dpi;
            double P2 = T2 * dpi;
            double answer = P1 * P2 * bytes;
            System.out.println(answer);
        }
    }


    public static void main(String[] args) {
        exec();
    }
}

/**
 * Created by r028367 on 17/04/2017.
 * http://homepages.inf.ed.ac.uk/rbf/HIPR2/dilate.htm
 * https://www.hackerrank.com/challenges/dip-morphological-operations-dilation
 * https://www.hackerrank.com/challenges/dip-morphological-operations-dilation
 */
public class Morphological {


    public static final int [][] IMAGE = {
         {0,0,0,0,0,0,0,0,0,0}
        ,{0,1,1,1,1,1,1,1,0,0}
        ,{0,0,0,0,1,1,1,1,0,0}
        ,{0,0,0,0,1,1,1,1,0,0}
        ,{0,0,0,1,1,1,1,1,0,0}
        ,{0,0,0,0,1,1,1,1,0,0}
        ,{0,0,0,1,1,0,0,0,0,0}
        ,{0,0,0,0,0,0,0,0,0,0}
        ,{0,0,0,0,0,0,0,0,0,0}
    };

    public static final int [][] KERNEL = {
         {1,1,1}
        ,{1,1,1}
        ,{1,1,1}
    };


    public static int [][] dilatation(int IMAGE[][], int KERNEL[][]) {
        int limitLImage = IMAGE.length, limitCImage = IMAGE[0].length;
        int limitLKernel = KERNEL.length, limitCKernel = KERNEL[0].length;
        int [][] rs = new int[limitLImage][limitCImage];


        for(int i=0; i<limitLImage-limitLKernel+1; i++) {
            for(int j=0; j<limitCImage-limitCKernel+1; j++) {
                // int accImage = 0, accKernel = 0;
                int max =  0;
                for(int k=0; k<limitLKernel; k++) {
                    for(int w=0; w<limitCKernel; w++) {
                        //accImage += IMAGE[i+k][j+w];
                        //accKernel += KERNEL[k][w];
                        if(KERNEL[k][w] == 1 && IMAGE[i+k][j+w] > max) {
                            max =  IMAGE[i+k][j+w];
                        }
                    }
                }
                rs[i][j] = max;
            }
        }

        int acc = 0;
        for(int i=0; i<limitLImage; i++) {
            for(int j=0; j<limitCImage; j++) {
                System.out.printf("%d", rs[i][j]);
                if(rs[i][j] == 1)
                    acc++;
            }
            System.out.println("");
        }

        System.out.println(acc);
        return rs;
    }

    public static void main(String[] args) {
        dilatation(IMAGE, KERNEL);
    }
}

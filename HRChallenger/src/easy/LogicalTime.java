package easy;

import java.util.Calendar;

/**
 * Created by r028367 on 14/07/2017.
 */
public class LogicalTime {





    public static String getTimeAgo() {
        long createAt           = 1500044854898l;       // 14/07/2017 12:07
        Calendar calendar       = Calendar.getInstance();
        // aprox
        // 1 ano  = 31104000000 ms
        // 1 mes  = 2592000000 ms
        // 1 dia  = 86400000 ms
        // 1 hora = 3600000 ms
        long currentTime = calendar.getTimeInMillis();
        //currentTime += 3600000l + 657000;
        //currentTime += (2592000000l + 86400000);
        //currentTime += (2592000000l + 86400000) * 12 * 10; // 31104000000l
        long diffInSeconds = (currentTime - createAt) / 1000;
        String message = "";
        if(diffInSeconds < 60) {
            message = String.format("Aprox. %d seg. atrás", diffInSeconds);
        }
        else if(diffInSeconds > 59 && diffInSeconds < 3600) {
            diffInSeconds /= 60;
            message = String.format("Aprox. %d min. atrás", diffInSeconds);
        }

        else if(diffInSeconds > 3599 &&  diffInSeconds < 86400) {
            diffInSeconds /= 3600;
            message = String.format("Aprox. %d %s atrás", diffInSeconds, diffInSeconds > 1 ? "horas" : "hora");
        }

        else {
            if((diffInSeconds / 86400) < 31) {
                // numero de segundos num dia
                diffInSeconds /= 86400;
                message = String.format("Aprox. %d %s atrás", diffInSeconds, diffInSeconds > 1 ? "dias" : "dias");
            }
            else {
                // numero de segundos em um mês de 30 dias = 2592000
                if( (diffInSeconds / 2592000) < 12) {
                    diffInSeconds /= 2592000;
                    message = String.format("Aprox. %d %s atrás", diffInSeconds, diffInSeconds > 1 ? "meses" : "mês");
                }
                else {
                    diffInSeconds /= 31104000;
                    message = String.format("Aprox. %d %s atrás", diffInSeconds, diffInSeconds > 1 ? "anos" : "ano");
                }
            }
        }
        return message;
    }

    public static void main(String[] args) {
        System.out.println(getTimeAgo());;
    }
}

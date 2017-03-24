import java.time.Instant;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by r028367 on 30/01/2017.
 *
 * http://www.geeksforgeeks.org/binary-indexed-tree-or-fenwick-tree-2/
 * https://www.hackerearth.com/practice/notes/binary-indexed-tree-or-fenwick-tree/
 * https://www.topcoder.com/community/data-science/data-science-tutorials/binary-indexed-trees/
 *
 * http://www.geeksforgeeks.org/count-inversions-array-set-3-using-bit/
 * http://www.geeksforgeeks.org/two-dimensional-binary-indexed-tree-or-fenwick-tree/
 * http://www.geeksforgeeks.org/counting-triangles-in-a-rectangular-space-using-2d-bit/
 *
 */
public class Main {

    public static int getSum(int [] BIT, int idx) {
        int acc = 0;
        idx += 1;
        while(idx > 0) {
            acc += BIT[idx];
            idx -= idx & (-idx);
        }
        return acc;
    }

    public static  void update(int [] BIT, int n, int idx, int val) {
        idx += 1;
        while(idx <= n) {
            BIT[idx] += val;
            idx += idx & (-idx);
        }
    }

    public static int [] construct(int arr [], int n) {
        int BIT [] = new int[n+1];
        for(int i=1; i<=n; i++)
            BIT[i] = 0;

        for(int i=0; i<n; i++) {
            update(BIT, n, i, arr[i]);
        }

        return BIT;
    }


    public static void runTest() {
        int [] freq = {2, 1, 1, 3, 2, 3, 4, 5, 6, 7, 8, 9};
        int sz = freq.length;
        int BIT [] = construct(freq, sz);
        System.out.println(getSum(BIT, 5));

        freq[3] += 6;
        update(BIT, sz, 3, 6);
        System.out.println(getSum(BIT, 5));
    }


    public static Calendar longToCalendar(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(time));
        //calendar.setTimeInMillis(time);
        return calendar;
    }

    public static void main(String[] args) {
        Calendar calendar = longToCalendar(System.currentTimeMillis());
        System.out.println(calendar.getTime().toString());
    }

}

/**
 * Created by C.Lucas on 26/03/2017.
 */
public class Pixels {

    int x, y;

    public Pixels(int x, int y) {
        this.x = x;
        this.y = y;
    }


    public int euclidianDistance(Pixels that) {
        int x = this.x, y = this.y, s = that.x, t = that.y;
        int diffX = x - s;
        int diffY = y - t;
        int distance = (int) Math.sqrt((diffX * diffX) + (diffY * diffY));
        return distance;
    }

    public int cityBlockDistance(Pixels that) {
        int x = this.x, y = this.y, s = that.x, t = that.y;
        int diffX = x - s;
        int diffY = y - t;
        return diffX + diffY;
    }


    public int chessBoardDistance(Pixels that) {
        int x = this.x, y = this.y, s = that.x, t = that.y;
        int diffX = x - s;
        int diffY = y - t;
        return diffX > diffY ? diffX : diffY;
    }

    public Pixels chessBoardDistanceGetPixel(Pixels that) {
        int x = this.x, y = this.y, s = that.x, t = that.y;
        int diffX = x - s;
        int diffY = y - t;
        return diffX > diffY ? this : that;
    }
}

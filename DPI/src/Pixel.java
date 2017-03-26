/**
 * Created by C.Lucas on 26/03/2017.
 */
public class Pixel {

    int x, y;

    public Pixel(int x, int y) {
        this.x = x;
        this.y = y;
    }


    public int euclidianDistance(Pixel that) {
        int x = this.x, y = this.y, s = that.x, t = that.y;
        int diffX = x - s;
        int diffY = y - t;
        int distance = (int) Math.sqrt((diffX * diffX) + (diffY * diffY));
        return distance;
    }

    public int cityBlockDistance(Pixel that) {
        int x = this.x, y = this.y, s = that.x, t = that.y;
        int diffX = x - s;
        int diffY = y - t;
        return diffX + diffY;
    }


    public int chessBoardDistance(Pixel that) {
        int x = this.x, y = this.y, s = that.x, t = that.y;
        int diffX = x - s;
        int diffY = y - t;
        return diffX > diffY ? diffX : diffY;
    }

    public Pixel chessBoardDistanceGetPixel(Pixel that) {
        int x = this.x, y = this.y, s = that.x, t = that.y;
        int diffX = x - s;
        int diffY = y - t;
        return diffX > diffY ? this : that;
    }

    public int norm(Pixel O, Pixel D) {
        int x = O.x, y = O.y, s = D.x, t = D.y;
        int diffX = x - s;
        int diffY = y - t;
        int distance = (int) Math.sqrt((diffX * diffX) + (diffY * diffY));
        return distance;
    }
}

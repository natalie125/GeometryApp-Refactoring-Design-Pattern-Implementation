/**
 * a two dimensional point
 */
public class Point {

    // coordinates
    double x, y;

    /**
     * constructor
     * @param x (double): X coordinate
     * @param y (double): Y coordinate
     * @return Point
     */
    public Point(double x, double y){
        this.x=x;
        this.y=y;
    }

    /**
     * getter for nearest integer
     * @return int
     */
    public int getX(){
        return (int) Math.round(this.x);
    }

    /**
     * getter for nearest integer
     * @return int
     */
    public int getY(){
        return (int) Math.round(this.y);
    }

    /**
     * string description
     * @return String
     */
    public String toString(){
        StringBuilder str = new StringBuilder();
        str.append("Point(");
        str.append(this.x);
        str.append(", ");
        str.append(this.y);
        str.append(")");

        return str.toString();
    }

}

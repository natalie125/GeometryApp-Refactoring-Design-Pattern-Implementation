import java.awt.*;
import java.util.ArrayList;

/**
 * object holding a two dimesional shape
 */
public class Shape {

    // lines forming shape
    ArrayList<Line> lines;

    // display colour
    Color color;

    /**
     * constructor provides an empty shape
     */
    public Shape(){

       lines = new ArrayList<Line>();

    }

    /**
     * set display color
     * @param c (Color): new color
     */
    public void setColor(Color c){
        color = c;
    }

    /**
     * add a line to shape
     * @param line (Line)
     */
    public void addLine(Line line){
        lines.add(line);
    }

    /**
     * render the Shape to a drawing area
     * @param context (Graphics2D): drawing area
     * @param matrix (TransformMatrix): current transform
     */
    public void draw(Graphics2D context, TransformMatrix matrix){
        // save old drawing colour
        final Color prev = context.getColor();
        context.setColor(color);

        for(Line l: lines){ // for each line
            Point[] pts = l.getPoints(); // get points

            // apply transform matrix to both points
            Point p1 = matrix.apply(pts[0]);
            Point p2 = matrix.apply(pts[1]);

            // draw line between transformed points
            context.drawLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());
        }

        // restore old drawing colour
        context.setColor(prev);
    }
}

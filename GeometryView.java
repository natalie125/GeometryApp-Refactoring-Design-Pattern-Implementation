import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Dimension;
import java.awt.RenderingHints;

/**
 * Provides a drawing area that uses a transformation matrix to apply shifts and rotations.
 */
public class GeometryView extends JPanel {

    // the application holding this panel
    GeometryApp app;

    /**
     * constructor
     * @param parent (GeometryApp): the application holding this panel
     */
    public GeometryView(GeometryApp parent){

        this.app = parent;
        setPreferredSize(new Dimension(800, 600));

    }

    /**
     * override panel paint method
     * @param (Graphics) g: the drawing area
     */
    protected void paintComponent(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;
        g2d.clearRect(0, 0, this.getWidth(), this.getHeight());
        g2d.setRenderingHint(
                RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        // iterate shapes in parent application
        TransformMatrix matrix = app.getDrawing().copyOfMatrix();
        for(Shape shp: app.getDrawing().shapes){
            shp.draw(g2d, matrix);
        }
    }
}

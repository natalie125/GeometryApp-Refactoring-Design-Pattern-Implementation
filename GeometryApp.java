/**
 * School of Computer Science, University of Leeds, 2024
 *
 * Geometry App -
 *
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.BoxLayout;

public class GeometryApp {
    // array of shapes to be drawn
    protected Drawing drawing;

    // the drawing area
    protected GeometryView drawingArea;

    /**
     * constructor
     */
    public GeometryApp(){
        JFrame frame = new JFrame("Geometry App");
        frame.getContentPane().setLayout(new FlowLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // make a drawing
        drawing = new Drawing();
        drawing.add(createRectangle(240,100, 100,100));
        drawing.add(createTriangle());

        drawingArea = new GeometryView(this);

        frame.getContentPane().add(drawingArea);
        frame.getContentPane().add(make_input_panel());
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * getter for the drawing
     * @return Drawing
     */
    public Drawing getDrawing() {
        return drawing;
    }

    /**
     * make the button panel
     * @return JPanel
     */
    protected JPanel make_input_panel(){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        JSpinner scale_x = makeDoublSpinner(1.0, 10.0, 0.0, 0.1);
        JSpinner scale_y = makeDoublSpinner(1.0, 10.0, 0.0, 0.1);
        JSpinner shear_x = makeDoublSpinner(0.0, 10.0, -10.0, 0.1);
        JSpinner shear_y = makeDoublSpinner(0.0, 10.0, -10.0, 0.1);
        JSpinner rotate = makeDoublSpinner(0.0, 180.0, -180.0, 1.0);
        JButton view = new JButton("View result");
        JButton reset = new JButton("Reset");
        JButton text = new JButton("To text");

        view.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                processRequest((Double) scale_x.getValue(), (Double) scale_y.getValue(), (Double) shear_x.getValue(), (Double) shear_y.getValue(),Math.toRadians((Double) rotate.getValue()));
            }
        });

        reset.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                processReset(scale_x, scale_y, shear_x, shear_y, rotate);
            }
        });

        text.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                saveToFile();
            }
        });

        panel.add(new JLabel("Scale X"));
        panel.add(scale_x);
        panel.add(new JLabel("Scale Y"));
        panel.add(scale_y);
        panel.add(new JLabel("Shear X"));
        panel.add(shear_x);
        panel.add(new JLabel("Shear Y"));
        panel.add(shear_y);
        panel.add(new JLabel("Rotation"));
        panel.add(rotate);
        panel.add(new JLabel(" "));
        panel.add(view);
        panel.add(new JLabel(" "));
        panel.add(reset);
        panel.add(new JLabel(" "));
        panel.add(text);
        return panel;
    }

    /**
     * make a floating point spin box
     * @param init_value (double): initial value of spinnor
     * @param maximum (double): maximum value of spinnor
     * @param minimum (double): minimum value of spinnor
     * @return JSpinner
     */
    static JSpinner makeDoublSpinner(final double init_value, final double maximum, final double minimum, final double step_size){
        Double value = Double.valueOf(init_value);
        Double min = Double.valueOf(minimum);
        Double max = Double.valueOf(maximum);
        Double step = Double.valueOf(step_size);
        SpinnerNumberModel model = new SpinnerNumberModel(value, min, max, step);

        return new JSpinner(model);
    }

    /**
     * create a square
     * @return (Shape)
     */
    static Shape createRectangle(double x, double y, double width, double height){
        Shape shape = new Shape();

        Point p1 = new Point(x, y);
        Point p2 = new Point(x+width, y);
        Point p3 = new Point(x+width, y+height);
        Point p4 = new Point(x, y+height);

        Line l1 = new Line(p1, p2);
        Line l2 = new Line(p2, p3);
        Line l3 = new Line(p3, p4);
        Line l4 = new Line(p4, p1);

        shape.addLine(l1);
        shape.addLine(l2);
        shape.addLine(l3);
        shape.addLine(l4);

        return shape;
    }

    /**
     * create a triangle
     * @return (Shape)
     */
    static Shape createTriangle(){
        Shape shape = new Shape();

        Point p1 = new Point(100.0, 100.0);
        Point p2 = new Point(200.0, 200.0);
        Point p3 = new Point(100.0, 200.0);

        Line l1 = new Line(p1, p2);
        Line l2 = new Line(p2, p3);
        Line l3 = new Line(p3, p1);

        shape.addLine(l1);
        shape.addLine(l2);
        shape.addLine(l3);

        shape.setColor(Color.RED);

        return shape;
    }

    /**
     * callback function for press of "view results" button
     * @param scale_x (double)
     * @param scale_y (double)
     * @param shear_x (double)
     * @param shear_y (double)
     * @param rotation (double)
     */
    public void processRequest(final Double scale_x, final Double scale_y, final Double shear_x, final Double shear_y, final Double rotation){
        // include the parameter in the current transform matrix
        TransformMatrix matrix = drawing.copyOfMatrix();
        matrix.updateMatrix(scale_x, scale_y, shear_x, shear_y, rotation);
        drawing.newMatrix(matrix);

        // force panel to redraw itself
        drawingArea.repaint();
    }

    /**
     * reset the view
     *
     * the alternative is to make the spinners object variables
     * @param scale_x (JSpinner)
     * @param scale_y (JSpinner)
     * @param shear_x (JSpinner)
     * @param shear_y (JSpinner)
     * @param rotate (JSpinner)
     */
    public void processReset(JSpinner scale_x, JSpinner scale_y, JSpinner shear_x, JSpinner shear_y, JSpinner rotate){
        // set matrix to identity
        TransformMatrix matrix = new TransformMatrix();

        // reset spinboxes
        scale_x.setValue(1.0);
        scale_y.setValue(1.0);
        shear_x.setValue(0.0);
        shear_y.setValue(0.0);
        rotate.setValue(0.0);

        drawing.newMatrix(matrix);

        // force panel to redraw itself
        drawingArea.repaint();
    }

    /**
     * save to file
     *
     * prototype prints to standard output
     */
    public void saveToFile(){
       System.out.println(drawing.toFileString());
    }

    /**
     * main method for the application
     * @param args (String[]): command line
     */
    public static void main(String[] args) {
        GeometryApp gui = new GeometryApp();
    }
}

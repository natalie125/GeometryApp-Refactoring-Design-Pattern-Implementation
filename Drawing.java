import java.util.Vector;

public class Drawing {
    // current transform
    protected TransformMatrix transform;

    // array of shapes to be drawn
    public Vector<Shape> shapes;

    /**
     * constructor, provides empty Vector and identity matrix
     */
    public Drawing(){
        // identity matrix
        transform = new TransformMatrix();

        // empty drawing
        shapes = new Vector<Shape>();
    }

    /**
     * add a new shape
     * @param shape
     */
    public void add(Shape shape){
        shapes.add(shape);
    }

    /**
     * change the matrix
     */
    public void newMatrix(final TransformMatrix matrix){
        transform.copy(matrix);
    }

    /**
     * getter for copy of the matrix
     * @return TransformMatrix
     */
    public TransformMatrix copyOfMatrix(){
        return new TransformMatrix(transform);
    }

    /**
     * make a string for file output
     */
    public String toFileString(){
        StringBuilder str = new StringBuilder();
        str.append("title = \"Geometry\"\n\n");

        str.append("[TransformMatrix]\n");
        str.append("entries = [");
        final double[] tmp = transform.getMatrix();
        for(int i=0; i<3; ++i)
            str.append(tmp[i] + ", ");
        str.append(tmp[3] + "]\n\n");

        for (Shape s : shapes) {
            str.append("[Shape]\n");
            str.append("lines=[");
            for(int i=0; i<s.lines.size(); ++i){
                Line line = s.lines.get(i);
                str.append("{");
                str.append("start=[");
                str.append(line.p1.x+","+line.p1.y);
                str.append("],");

                str.append("end=[");
                str.append(line.p2.x+","+line.p2.y);
                str.append("]");

                if(i != s.lines.size()-1){
                    str.append("},");
                }else{
                    str.append("}");
                }
            }
            str.append("]\n");
            str.append("color = "+s.color+"\n\n");
        }
        return str.toString();
    }
}

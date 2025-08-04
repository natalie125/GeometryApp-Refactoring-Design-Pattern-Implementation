import java.util.Arrays;

/**
 * a two dimensional transformation matrix and associated methods
 */
public class TransformMatrix {

    // the raw matrix
    public double matrix[];

    /**
     * default constructor produces identity matrix
     */
    public TransformMatrix() {
        matrix = new double[]{1.0, 0.0, 0.0, 1.0};
    }

    /**
     * constructor
     * @param m00 (double): 00 element
     * @param m01 (double): 01 element
     * @param m10 (double): 10 element
     * @param m11 (double): 11 element
     */
    public TransformMatrix(final double m00, final double m01, final double m10, final double m11) {
        matrix = new double[]{m00, m01, m10, m11};
    }

    /**
     * copy constructor
     * @param mat TransformMatrix to be copied
     */
    public TransformMatrix(final TransformMatrix mat) {
        matrix = Arrays.copyOf(mat.matrix, 4);
    }

    /**
     * get a copy of the current matrix
     *
     * @return (double[]): matrix
     */
    public double[] getMatrix() {
        return Arrays.copyOf(matrix, 4);
    }

    /**
     * copy another transform
     * @param rhs (TransformMatrix)
     * @return TransformMatrix pointer to self
     */
    public TransformMatrix copy(final TransformMatrix rhs) {
        matrix = rhs.getMatrix();
        return this;
    }

    /**
     * Multiply two 2x2 matrices and return the resulting matrix
     * @param matrixA (double[]): First matrix
     * @param matrixB (double[]): Second matrix
     * @return (double[]): Resultant matrix after multiplication
     */
    public static double[] multiplyMatrices(final double[] matrixA, final double[] matrixB) {
        if (matrixA.length != 4 || matrixB.length != 4) {
            throw new IllegalArgumentException("Both matrices must be 2x2.");
        }
    
        double[] result = new double[4];
        result[0] = matrixA[0] * matrixB[0] + matrixA[1] * matrixB[2];
        result[1] = matrixA[0] * matrixB[1] + matrixA[1] * matrixB[3];
        result[2] = matrixA[2] * matrixB[0] + matrixA[3] * matrixB[2];
        result[3] = matrixA[2] * matrixB[1] + matrixA[3] * matrixB[3];
    
        return result;
    }    

    /**
     * update the current matrix
     * @param scale_x (double)
     * @param scale_y (double)
     * @param shear_x (double)
     * @param shear_y (double)
     * @param theta (double)
     */
    public void updateMatrix(final Double scale_x, final Double scale_y, final Double shear_x, final Double shear_y, final Double theta) {
        // Construct the matrices as one-dimensional arrays
        final double shear[] = {1.0, shear_x, shear_y, 1.0};
        final double scale[] = {scale_x, 0.0, 0.0, scale_y};
        final double cos_theta = Math.cos(theta);
        final double sin_theta = Math.sin(theta);
        final double rotate[] = {cos_theta, -sin_theta, sin_theta, cos_theta};
    
        // Use the current matrix
        double[] current = this.getMatrix();
    
        // Multiply matrices using the new method
        current = multiplyMatrices(current, shear);
        current = multiplyMatrices(current, scale);
        current = multiplyMatrices(current, rotate);
    
        // Assign to the object
        this.matrix = current;
    }  

    /**
     * apply transformation to a data in a point and return result as new point
     * @param point (Point): input point
     * @return Point: transformed version of input.
     */
    public Point apply(final Point point) {
        // Represent the point using a 2x1 matrix
        double[] pointMatrix = {point.x, point.y};

        // Do the transformation
        double x = matrix[0] * pointMatrix[0] + matrix[1] * pointMatrix[1];
        double y = matrix[2] * pointMatrix[0] + matrix[3] * pointMatrix[1];

        return new Point(x, y);
    }

    /**
     * string representation
     * @return String
     */
    public String toString() {
        StringBuilder str = new StringBuilder();

        str.append("[");
        str.append(this.matrix[0]);
        str.append(", ");
        str.append(this.matrix[1]);
        str.append("]\n");
        str.append("[");
        str.append(this.matrix[2]);
        str.append(", ");
        str.append(this.matrix[3]);
        str.append("]\n");

        return str.toString();
    }
}
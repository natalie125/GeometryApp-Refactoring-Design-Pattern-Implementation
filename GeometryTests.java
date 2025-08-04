/**
 * School of Computing, University of Leeds, 2024
 *
 * To compile:
 * javac -cp junit-platform-console-standalone-1.9.3.jar *.java -d target
 *
 * To run tests:
 * java -jar .\junit-platform-console-standalone-1.9.3.jar --class-path target --select-class GeometryTests
 */

 import static org.junit.Assert.assertArrayEquals;
 import static org.junit.Assert.assertEquals;
 
 import org.junit.Before;
 import org.junit.Test;
 
 /**
  * class holding the Junit4 tests for TransformMatrix
  */
 public class GeometryTests {
     /**
      * declare a field that will hold the matrix being tested
      */
     private TransformMatrix id_matrix;
 
     /**
      * declare a field to hold point (1, 1)
      */
     private Point unit;
 
     /**
      * this is our interpretation of zero
      */
     static final double LOCAL_ZERO = 0.0000001;
 
     // this is the Junit4 version of @Before, equivalent to @BeforeEach in Junit5
     @Before
     public void setUp() throws Exception {
         id_matrix = new TransformMatrix(1.0, 0.0, 0.0, 1.0);
         unit = new Point(1.0, 1.0);
     }
 
     /**
      * simple test of identity matrix
      */
     @Test
     public void testIdentity() {
         double id[] = {1.0, 0.0, 0.0, 1.0};
         assertArrayEquals(id, id_matrix.matrix, GeometryTests.LOCAL_ZERO);
     }
 
     /**
      * test apply id matrix
      */
     @Test
     public void testApply0() {
         Point same = id_matrix.apply(unit);
         assertEquals(1.0, same.x, GeometryTests.LOCAL_ZERO);
         assertEquals(1.0, same.y, GeometryTests.LOCAL_ZERO);
     }
 
     /**
      * test apply with shear
      */
     @Test
     public void testApply1() {
         id_matrix.matrix[0] = 2.0;
         Point same = id_matrix.apply(unit);
         assertEquals(2.0, same.x, GeometryTests.LOCAL_ZERO);
         assertEquals(1.0, same.y, GeometryTests.LOCAL_ZERO);
     }
 
     /**
      * test TransformMatrix.updateMatrix method with a rotation
      */
     @Test
     public void testRotationUpdate() {
         TransformMatrix tmp = new TransformMatrix();
         final double theta = Math.toRadians(45.0);
         final double cosTheta = Math.cos(theta);
         final double sinTheta = Math.sin(theta);
 
         tmp.updateMatrix(1.0, 1.0, 0.0, 0.0, theta);
         final double[] shouldBe = {cosTheta, -sinTheta, sinTheta, cosTheta};
 
         assertArrayEquals(tmp.matrix, shouldBe, GeometryTests.LOCAL_ZERO);
     }
 
     /**
      * test TransformMatrix.updateMatrix method with a shift and shear
      */
     @Test
     public void testScaleShearUpdate() {
         TransformMatrix tmp = new TransformMatrix();
         final double scale_x = 1.5;
         final double scale_y = 0.5;
         final double shear_x = 0.6;
         final double shear_y = -0.3;
         final double rotation = 0.0;
 
         tmp.updateMatrix(scale_x, scale_y, shear_x, shear_y, rotation);
         final double[] shouldBe = {scale_x, 0.3, -0.45, scale_y};
 
         assertArrayEquals(shouldBe, tmp.matrix, GeometryTests.LOCAL_ZERO);
     }
 
     /**
      * test TransformMatrix.updateMatrix method with a scale, shear and rotation
      */
     @Test
     public void testScaleShearRotateUpdate() {
         TransformMatrix tmp = new TransformMatrix();
         final double scale_x = 1.5;
         final double scale_y = 1.0;
         final double shear_x = 1.0;
         final double shear_y = 0.0;
         final double rotation = Math.toRadians(30.0);
 
         tmp.updateMatrix(scale_x, scale_y, shear_x, shear_y, rotation);
         final double[] shouldBe = {1.799038105676658, 0.11602540378443882, 0.49999999999999994, 0.8660254037844387};
 
         assertArrayEquals(shouldBe, tmp.matrix, GeometryTests.LOCAL_ZERO);
     }
 
     /**
      * test multiplying two identity matrices
      */
     @Test
     public void testMatrixMultiplicationIdentity() {
         double[] identity = {1.0, 0.0, 0.0, 1.0};
         double[] result = TransformMatrix.multiplyMatrices(identity, identity);
         assertArrayEquals(identity, result, LOCAL_ZERO);
     }
 
     /**
      * test multiplying with a zero matrix
      */
     @Test
     public void testMatrixMultiplicationZeroMatrix() {
         double[] zeroMatrix = {0.0, 0.0, 0.0, 0.0};
         double[] result = TransformMatrix.multiplyMatrices(id_matrix.getMatrix(), zeroMatrix);
         assertArrayEquals(zeroMatrix, result, LOCAL_ZERO);
     }
 
     /**
      * test multiplying two arbitrary matrices
      */
     @Test
     public void testMatrixMultiplicationArbitrary() {
         double[] matrixA = {1.0, 2.0, 3.0, 4.0};
         double[] matrixB = {2.0, 0.0, 1.0, 2.0};
         double[] expected = {4.0, 4.0, 10.0, 8.0};
         double[] result = TransformMatrix.multiplyMatrices(matrixA, matrixB);
         assertArrayEquals(expected, result, LOCAL_ZERO);
     }
 
     /**
      * test multiplying matrices with negative values
      */
      @Test
      public void testMatrixMultiplicationNegative() {
          double[] matrixA = {-1.0, 2.0, -3.0, 4.0};
          double[] matrixB = {2.0, -1.0, 1.0, -2.0};
          double[] expected = {0.0, -3.0, -2.0, -5.0}; // Corrected expected result
          double[] result = TransformMatrix.multiplyMatrices(matrixA, matrixB);
          assertArrayEquals(expected, result, LOCAL_ZERO);
      }      

      /**
     * Test multiplying with a non-invertible matrix (determinant = 0)
     */
    @Test
    public void testMatrixMultiplicationNonInvertible() {
        double[] nonInvertibleMatrix = {1.0, 2.0, 2.0, 4.0}; // Determinant = 0
        double[] matrixB = {1.0, 0.0, 0.0, 1.0}; // Identity matrix
        double[] expected = {1.0, 2.0, 2.0, 4.0}; // Result is the non-invertible matrix itself
        double[] result = TransformMatrix.multiplyMatrices(nonInvertibleMatrix, matrixB);
        assertArrayEquals(expected, result, LOCAL_ZERO);
    }
    /**
     * Test multiplying two symmetric matrices
     */
    @Test
    public void testMatrixMultiplicationSymmetric() {
        double[] symmetricMatrixA = {2.0, 1.0, 1.0, 2.0}; // Symmetric matrix
        double[] symmetricMatrixB = {1.0, 0.0, 0.0, 1.0}; // Another symmetric matrix
        double[] expected = {2.0, 1.0, 1.0, 2.0}; // Result is symmetric
        double[] result = TransformMatrix.multiplyMatrices(symmetricMatrixA, symmetricMatrixB);
        assertArrayEquals(expected, result, LOCAL_ZERO);
    }
    /**
     * Test multiplying an identity matrix with a scaling matrix
     */
    @Test
    public void testMatrixMultiplicationIdentityScaling() {
        double[] identity = {1.0, 0.0, 0.0, 1.0};
        double[] scalingMatrix = {2.0, 0.0, 0.0, 3.0}; // Scaling matrix
        double[] expected = {2.0, 0.0, 0.0, 3.0}; // Should match the scaling matrix
        double[] result = TransformMatrix.multiplyMatrices(identity, scalingMatrix);
        assertArrayEquals(expected, result, LOCAL_ZERO);
    }
    /**
     * Test multiplying two rotation matrices
     */
    @Test
    public void testMatrixMultiplicationRotations() {
        double angle1 = Math.toRadians(30.0); // 30 degrees
        double angle2 = Math.toRadians(60.0); // 60 degrees

        double[] rotationMatrix1 = {
            Math.cos(angle1), -Math.sin(angle1),
            Math.sin(angle1), Math.cos(angle1)
        };

        double[] rotationMatrix2 = {
            Math.cos(angle2), -Math.sin(angle2),
            Math.sin(angle2), Math.cos(angle2)
        };

        // Resulting rotation matrix should represent a 90-degree rotation (30 + 60 degrees)
        double[] expected = {
            0.0, -1.0,
            1.0, 0.0
        };

        double[] result = TransformMatrix.multiplyMatrices(rotationMatrix1, rotationMatrix2);
        assertArrayEquals(expected, result, LOCAL_ZERO);
    }
    /**
     * Test multiplying matrices with invalid dimensions
     */
    @Test(expected = IllegalArgumentException.class)
    public void testMatrixMultiplicationInvalidDimensions() {
        double[] invalidMatrix = {1.0, 2.0}; // Not a 2x2 matrix
        double[] validMatrix = {1.0, 0.0, 0.0, 1.0}; // Identity matrix
        TransformMatrix.multiplyMatrices(invalidMatrix, validMatrix); // Should throw an exception
    }
    /**
     * Test multiplying matrices with very large values
     */
    @Test
    public void testMatrixMultiplicationLargeValues() {
        double[] matrixA = {1e10, 2e10, 3e10, 4e10};
        double[] matrixB = {5e10, 6e10, 7e10, 8e10};
        double[] expected = {1.9e21, 2.2e21, 4.3e21, 5.0e21}; // Precomputed expected result
        double[] result = TransformMatrix.multiplyMatrices(matrixA, matrixB);
        assertArrayEquals(expected, result, LOCAL_ZERO);
    }
}
 
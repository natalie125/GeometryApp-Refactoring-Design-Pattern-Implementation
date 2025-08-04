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
  * class holding the Junit4 tests for Drawing
 */
public class DrawingTests {

    /**
     * declare a field that will hold the drawing being tested
     */
    private Drawing drawing;

    private String target;

    // this is the Junit4 version of @Before, equivalent to @BeforEach in Junit5
    @Before
    public void setUp() throws Exception {
        drawing = new Drawing();
        drawing.add(GeometryApp.createRectangle(240,100, 100,100));
        drawing.add(GeometryApp.createTriangle());

        StringBuilder build = new StringBuilder();
        build.append("title = \"Geometry\"\n\n");
        build.append("[TransformMatrix]\n");
        build.append("entries = [1.0, 0.0, 0.0, 1.0]\n\n");
        build.append("[Shape]\n");
        build.append("lines=[{start=[240.0,100.0],end=[340.0,100.0]},{start=[340.0,100.0],end=[340.0,200.0]},{start=[340.0,200.0],end=[240.0,200.0]},{start=[240.0,200.0],end=[240.0,100.0]}]\n");
        build.append("color = null\n\n");
        build.append("[Shape]\n");
        build.append("lines=[{start=[100.0,100.0],end=[200.0,200.0]},{start=[200.0,200.0],end=[100.0,200.0]},{start=[100.0,200.0],end=[100.0,100.0]}]\n");
        build.append("color = java.awt.Color[r=255,g=0,b=0]\n\n");

        target = build.toString();
    }

    @Test
    public void testToFileString(){
        final String fileout = drawing.toFileString();

        assertEquals(target, fileout);
    }
}

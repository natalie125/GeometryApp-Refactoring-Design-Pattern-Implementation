Compile App & Tests
=========================
javac -cp junit-platform-console-standalone-1.9.3.jar *.java -d target

Run Tests
=========
java -jar .\junit-platform-console-standalone-1.9.3.jar --class-path target --select-class GeometryTests

java -jar .\junit-platform-console-standalone-1.9.3.jar --class-path target --select-class DrawingTests

Run App
=======
java --class-path target GeometryApp

Task 1
======
Refactor the TransformationMatrix.updateMatrix() method by introducing a dedicated matrix multiplication method and improving the transformation logic structure.

Summary of Changes:
1. Implemented a multiplyWith(TransformationMatrix other) method to handle 2×2 matrix multiplication.

2. Refactored updateMatrix() to sequentially apply shear, scale, and rotation transformations using the new method.

3. Developed comprehensive unit tests in GeometryTests to validate the multiplication logic with identity, scaling, and rotation matrices.

Task 2
======
Redesign the text export functionality using the Strategy design pattern to allow extensibility for multiple export formats.

Summary of Changes
1. Introduced a new interface ExportStrategy with a method String export(Drawing drawing).

2. Implemented:

   - TOMLExportStrategy: Encapsulates the existing export logic.

   - PlainTextExportStrategy: Provides an example of an alternative output format.

3. Modified Drawing to delegate export behaviour to an injected strategy instance.

4. Updated DrawingTests to verify correctness of output across export strategies.

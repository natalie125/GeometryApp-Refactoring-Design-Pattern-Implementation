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

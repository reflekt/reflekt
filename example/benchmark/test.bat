echo "Building Example project with benchmarks"
@echo off

SET JAR="example/benchmark/target/reflekt-benchmark-0.0.1-SNAPSHOT-jar-with-dependencies.jar"
SET MAIN="com.example.Main"

java -cp %JAR% %MAIN% 3 null        RefleKt         org.reflections
java -cp %JAR% %MAIN% 3 null        org.reflections RefleKt
java -cp %JAR% %MAIN% 3 com.example RefleKt         org.reflections
java -cp %JAR% %MAIN% 3 com.example org.reflections RefleKt

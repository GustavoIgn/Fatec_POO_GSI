rmdir build /S /Q

mkdir build

javac -cp .;./src;./lib/* -d ./build ./src/edu/curso/*.java

java --module-path ./build;./lib --add-modules javafx.controls,javafx.fxml -cp ./build edu.curso.PrincipalBoundary
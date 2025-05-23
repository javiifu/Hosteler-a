REM Windows

javac -d bin -cp .;lib\jackson-annotations-3.0-rc3.jar;lib\jackson-core-2.19.0-rc2.jar;lib\jackson-databind-2.19.0-rc2.jar;lib\mysql-connector-j-9.2.0.jar src\dao\*.java src\view\*.java src\model\*.java src\main\*.java src\config\*.java
java -cp ".\bin;.\lib\jackson-annotations-3.0-rc3.jar;.\lib\jackson-core-2.19.0-rc2.jar;.\lib\jackson-databind-2.19.0-rc2.jar;.\lib\mysql-connector-j-9.2.0.jar" \main\App.java
REM Linux/Mac
javac -d bin -cp .:lib/* src/dao/*.java src/view/*.java src/model/*.java src/main/*.java src/config/*.java
java -cp ".:bin:lib/*" main.App

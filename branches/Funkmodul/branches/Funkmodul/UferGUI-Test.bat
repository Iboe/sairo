@ECHO OFF
cd 
set boat_cp=./bin
set boat_cp=%boat_cp%;./lib/RXTXcomm.jar
set boat_cp=%boat_cp%;./lib/slf4j-api-1.6.4.jar
set boat_cp=%boat_cp%;./lib/slf4j-log4j12-1.6.4.jar
set boat_cp=%boat_cp%;./lib/vecmath-1.3.1.jar
set boat_cp=%boat_cp%;./lib/log4j-1.2.15.jar
set boat_cp=%boat_cp%;./lib/JMapViewer.jar
set boat_cp=%boat_cp%;./lib/easymock-3.1.jar

java -cp %boat_cp% de.fhb.sailboat.test.UferGUITest

echo.
echo closing with the any key!
pause > nul
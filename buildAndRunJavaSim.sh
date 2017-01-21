#!/bin/bash
cd src
echo "compiling the java source"
javac *.java
echo "begining to run the java simulation"
java Driver < file.txt > ../latest_run_sim.csv

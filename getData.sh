#!/bin/bash
echo "" > outputData.csv
for run in {1..3}
do
  ./buildAndRunJavaSim.sh &>>outputData.csv
done

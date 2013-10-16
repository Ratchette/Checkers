#!/bin/bash

rm checkers.jar checkers/*.class

javac checkers/*.java
jar cvf checkers.jar checkers/*.class

javac -cp checkers.jar checkers/CheckersServer.java
javac -cp checkers.jar checkers/CheckersClient.java

java -cp $PWD/checkers.jar:$PWD -Djava.security.policy=server.policy checkers.CheckersServer

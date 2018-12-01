#!/bin/bash

MVN_OPTIONS="-q"

# Build project
mvn compile $MVN_OPTIONS

if [ "$?" -ne 0 ]; then
	echo "Compilation failed."
	exit 1
else
	echo "Compilation successful."
fi

# Run unit tests
mvn test $MVN_OPTIONS

if [ "$?" -ne 0 ]; then
	echo "Unit testing failed."
	exit 1
else
	echo "Unit testing successful."
fi

# Pack to JAR file
mvn package $MVN_OPTIONS

if [ "$?" -ne 0 ]; then
	echo "Mvn package failed."
	exit 1
else
	echo "Mvn package successful."
fi

# Run server in background
java -jar target/*.jar > /dev/null &
SERV_PID=$!

# Run integration tests
mvn integration-test verify $MVN_OPTIONS

if [ "$?" -ne 0 ]; then
	echo "Integration testing failed."
	exit 1
else
	echo "Integration testing successful."
fi

# Kill server process
kill $SERV_PID

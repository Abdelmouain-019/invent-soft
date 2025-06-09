all: clear compile run

clear:
	clear

install:
	mvn install

run:
	mvn exec:java

compile:
	mvn compile

clean:
	mvn clean

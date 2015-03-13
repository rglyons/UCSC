#Robert Lyons
#rglyons
#cmps012m
#10/16/14
# A simple makefile with variables

JAVASRC = Calc.java
SOURCES = README makefile ${JAVASRC}
MAINCLASS = Calc
CLASSES = Calc.class
JARFILE = myCalc
all: ${JARFILE}
${JARFILE}: ${CLASSES}
	echo "Main-class: ${MAINCLASS}" > Manifest
	jar cvfm ${JARFILE} Manifest ${JARCLASSES}
	rm Manifest
	chmod +x ${JARFILE}
${CLASSES}: ${JAVASRC}
	javac -Xlint ${JAVASRC}
clean:
	rm ${CLASSES} ${JARFILE}
test: 
	javac -cp "junit-4.12-beta-2.jar:hamcrest-core-1.3.jar:." CalcTest.java
	java -cp "junit-4.12-beta-2.jar:hamcrest-core-1.3.jar:." org.junit.runner.JUnitCore CalcTest
	
.PHONY: clean all
.PHONY: test

#Robert Lyons
#rglyons
#cmps012m
#10/16/14
# A simple makefile with variables

JAVASRC = greet.java greetings.java
SOURCES = README makefile ${JAVASRC}
MAINCLASS = greet
CLASSES = greet.class greetings.class
JARFILE = myGreet
JARCLASSES = ${CLASSES}
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
runGreet: greet.class
	java greet
runGreetings: greetings.class
	java greetings
.PHONY: clean all
.PHONY: runGreet
.PHONY: runGreetings

#Robert Lyons
#rglyons
#cmps012m
#11/30/14
# A simple makefile for cyoa.java

JAVASRC = main.java dllist.java option.java
SOURCES = README makefile ${JAVASRC}
MAINCLASS = main
CLASSES = main.class dllist.class option.class
JARFILE = cyoa
JARCLASSES = ${CLASSES} dllist\$$*.class
all: ${JARFILE}
${JARFILE}: ${CLASSES}
	echo "Main-class: ${MAINCLASS}" > Manifest
	jar cvfm ${JARFILE} Manifest ${JARCLASSES}
	rm Manifest
	chmod +x ${JARFILE}
${CLASSES}: ${JAVASRC}
	javac -g -Xlint ${JAVASRC}
clean:
	rm ${CLASSES} ${JARFILE}
test: all
	cyoa demo.adventure

.PHONY: clean all test

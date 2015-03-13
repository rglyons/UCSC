#Robert Lyons
#rglyons
#CMPS012B
#10-6-2014
#Makefile automates the build of any project into an executable file.

JAVASRC = hello.java hello2.java
SOURCES = README makefile ${JAVASRC}
MAINCLASS = hello2
CLASSES = hello.class hello2.class
JARFILE = myHello
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
.PHONY: clean all

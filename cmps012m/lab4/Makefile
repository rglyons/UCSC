#Robert Lyons
#rglyons
#cmps012m
#11-6-14
#Makefile
#Makefile for queue.c and unit tests
CC = gcc
CFLAGS = -std=c99 -Wall -Werror -Wfatal-errors -g
OBJS = queue.o
OUTPUT = queue
HEADERS = minunit.h

${OUTPUT}: ${OBJS}
	${CC} $< -o ${OUTPUT}

%.o: %.c ${HEADERS}
	${CC} -c ${CFLAGS} $<

clean:
	rm -f *.o

spotless: clean
	rm -f ${OUTPUT}

test: ${OUTPUT}
	./${OUTPUT}

.PHONY: clean spotless test


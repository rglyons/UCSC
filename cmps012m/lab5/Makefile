#Robert Lyons
#rglyons
#cmps012m
#11-13-14
#Makefile
#Makefile for list.c

CC = gcc
CFLAGS = -std=c99 -Wall -Werror -Wfatal-errors -g
OBJS = list.o
OUTPUT = list
HEADERS = 

${OUTPUT}: ${OBJS}
	${CC} $< -o ${OUTPUT}

%.o: %.c ${HEADERS}
	${CC} -c ${CFLAGS} $<

clean:
	rm -f *.o

spotless: clean
	rm -f ${OUTPUT}

test: ${OUTPUT}
	${OUTPUT}

.PHONY: clean spotless test


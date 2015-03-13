; CMPE12 - Fall 2008
; echo.asm
;
; This program echoes a user's input.



; The code will begin in memory at the address
; specified by .orig <number>.

	.ORIG   x3000


START:
; clear all registers that we may use
	AND	R0, R0, 0
	AND	R1, R0, 0
	AND	R2, R0, 0
	AND	R3, R0, 0
	AND	R4, R0, 0

; print out a greeting
	LEA	R0, GREETING
	PUTS

; get a user-entered character (result in R0)
; echo it back right away (otherwise it isn't visible)
	GETC
	PUTC

; store entered string (otherwise it may be overwritten)
	ST	R0, USERINPUT

; print out a newline and some other stuff
	LEA	R0, NEWLINE
	PUTS

; print out the user's input
	LD	R0, USERINPUT
	PUTC

; print out the rest of the sentance
	LEA	R0, USERSTRING
	PUTS

; stop the processor
	HALT



; data declarations follow below

; strings
GREETING:	.STRINGZ	"Welcome to the sample program.\nPlease enter a letter: "
USERSTRING:	.STRINGZ	" <-- is the character you entered.\n"
NEWLINE:	.STRINGZ "\n--> ";

; variables
USERINPUT:	.FILL	0

; end of code
	.END

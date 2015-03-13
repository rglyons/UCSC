; CMPE12 - Fall 2015
; Lab3.asm
;
; This program subtracts, multiplies, and divides user input



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
	AND	R5, R0, 0
	AND	R6, R0, 0
	AND	R7, R0, 0

;load decimal offsets for conversions
	JSR	LDOFFSET			;load positive offset into R1 (for conversion)
	JSR	LDNEGOFFSET			;load negative offset into R2 (for conversion)
	
; print out a greeting
	LEA	R0, GREETING
	PUTS

; get first user-entered number (result in R0)
; echo it back right away (otherwise it isn't visible)
	GETC
	PUTC
	ADD R0, R0, R2				;convert ascii to decimal

; store number at USERINPUT1 (otherwise it may be overwritten)
	ST	R0, USERINPUT1

; print out a newline and ask for second input
	LEA	R0, ASKNO
	PUTS

; get second user-entered number (result in R0)
; echo it back right away (otherwise it isn't visible)
	GETC
	PUTC
	ADD R0, R0, R2				;convert ascii to decimal

; store entered number at USERINPUT2 (otherwise it may be overwritten)
	ST	R0, USERINPUT2

; SUBTRACT USERINPUT1-USERINPUT2, store at DIFFERENCE
	LD 	R3, USERINPUT2			;load input2
	LD 	R4, USERINPUT1			;load input1
	JSR	SUBTRACTION

;print DIFFERENCE, store at x3100
	PRINTDIFF:	.STRINGZ	"Difference: "

	LEA	R0, NEWLINE			;load \n into R0
	PUTS					;print new line
	LEA	R0, PRINTDIFF
	PUTS					;print "Difference: "
	LD 	R3, DIFFERENCE			;load DIFFERENCE into R1
	ADD	R0, R3, R1			;convert ascii to decimal
	PUTC					;print DIFFERENCE
	LEA	R0, NEWLINE
	PUTS					;print new line
	STI	R3, DIFFLOC			;store difference at x3100

;MULTIPLY USERINPUT1 * USERINPUT2
	PRINTPROD	.STRINGZ	"Product: "

	LEA	R0, PRINTPROD			;print "Product: "
	PUTS
	JSR CLEAR				;clear registers

	LD	R3, USERINPUT1			;load input1
	LD 	R4, USERINPUT2			;load input2
	JSR	MULTIPLICATION			;jump to mult loop

;Correction loop
       CORRLOOP ADD R6, R6, 1			;incremement R5 (sig. number) # of loops
		ADD R5, R5, #-10		;decrement mult answer by 10 * # of loops
		BRzp CORRLOOP
;end correction loop

		ADD R6, R6, #-1			;MSB correction
		ADD R5, R5, #10			;LSB correction
		ADD R6, R6, R1			;MSB Offset ADD
		ADD R5, R5, R1			;LSB Offset ADD
;print product
		ST R6, PRODUCT
		LD R0, PRODUCT
		PUTC
		ST R5, PRODUCT
		LD R0, PRODUCT
		PUTC
		LEA R0, NEWLINE
		PUTS

;DIVIDE USERINPUT1 / USERINPUT2
	PRINTDIV	.STRINGZ	"Quotient: "
	PRINTREM	.STRINGZ	"r"

	LEA	R0, PRINTDIV
	PUTS					;print "Quotient: "

	JSR CLEAR
	LD	R3, USERINPUT2			;load input2
	LD	R4, USERINPUT1			;load input1
	JSR	DIVISION

; stop the processor
	HALT


LDOFFSET
	LD	R1, DEC_OFF
	RET

LDNEGOFFSET
	LD	R2, NEG_DEC_OFF
	RET
;-------------------------------------------------------------------------------------------------
SUBTRACTION:
	NOT 	R3, R3				;NOT input2
	ADD 	R3, R3, #1			;ADD 1 to ~input2
	ADD 	R3, R3, R4			;ADD input1 + ~input2
	ST 	R3, DIFFERENCE			;store difference in DIFFERENCE
	RET
;-------------------------------------------------------------------------------------------------
MULTIPLICATION:
	ADD	R5, R5, R3			;add to sum
	ADD	R4, R4, #-1			;decrement multiplier
	BRp	MULTIPLICATION			;repeat until multiplier is zero
	STI	R5, PRODLOC			;store PRODUCT at x3101
	RET
;-------------------------------------------------------------------------------------------------
DIVISION: 
	NOT	R3, R3				;not input2
	ADD	R3, R3, #1			; ~input2 + 1 (neg. input2)

DIVIDE	 	ADD R6, R6, #1			;count iterations (integer quotient)
		ADD R4, R4, R3			;subtract input2 from input1
		BRz CONT
		BRp DIVIDE			;reiterate while R4>0
		
	LD	R0, USERINPUT2
	ADD	R4, R4, R0			;add input2 to input1 to get positive remainder
	ADD	R6, R6, #-1			;subtract 1 from R6 (for crossing into neg. value)

CONT	STI	R4, REMLOC			;store remainder at x3103
	ADD	R4, R4, R1			;decimal to ascii conversion
	STI	R6, DIVLOC			;store quotient at x3102
	ADD	R6, R6, R1			;decimal to ascii conversion
	ST	R6, QUOTIENT
	LD	R0, QUOTIENT
	PUTC					;print quotient
	LEA	R0, PRINTREM			;print "r" after quotient
	PUTS
	ST	R4, REM
	LD	R0, REM
	PUTC					;print remainder
	LEA	R0, NEWLINE
      	PUTS
	BR START     				;restart the process
;--------------------------------------------------------------------------------------------------
; clear all registers that we may use (keep decimal offset)
CLEAR: 
	AND	R0, R0, 0
	AND	R3, R3, 0
	AND	R4, R4, 0
	AND	R5, R5, 0
	AND	R6, R6, 0
	RET

; data declarations follow below

; variables
USERINPUT1:	.FILL	0
USERINPUT2:	.FILL	0
DIFFERENCE:	.FILL   0
PRODUCT:	.FILL	0
QUOTIENT:	.FILL	0
REM:		.FILL	0
DEC_OFF:	.FILL	#48
NEG_DEC_OFF: 	.FILL	#-48
DIFFLOC:	.FILL	x3100
PRODLOC:	.FILL	x3101
DIVLOC:		.FILL	x3102
REMLOC:		.FILL	x3103

; strings
ASKNO:	        .STRINGZ        "\nPlease enter another number between 0 and 9: ";
NEWLINE:	.STRINGZ	"\n"
GREETING:	.STRINGZ	"Welcome to the the calculator program.\nPlease enter a number between 0 and 9: "

; end of code
	.END

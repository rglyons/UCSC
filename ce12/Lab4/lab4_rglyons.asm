; CMPE12 - Fall 2015
; lab4_rglyons.asm
; Robert Lyons
; Partner - Aronis 
; This program multiplies floating point numbers



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
GREETING:	.STRINGZ	"Welcome to the the floating point number multiplier.\nPlease enter a sign (+/-) followed by a two digit number: "
	
	LEA	R0, GREETING
	PUTS

;check for the sign of the input number
	LD	R4, MINUS
	GETC					;get the sign
	PUTC					;put the sign
	ADD	R4, R4, R0			;is the sign a "-"? 
	BRz	NEG1				;yes? go to POS
	BR	CONT1

NEG1	LD	R3, SIGNMASK			;R3 has 1000000000000000
	ST	R3, SIGN1
	BR	USEIN

CONT1	ADD	R3, R3, #0
	ST	R3, SIGN1

; get first user-entered number (result in R0)
; echo it back right away (otherwise it isn't visible)
USEIN	GETC
	PUTC
	ADD 	R0, R0, R2			;convert ascii to decimal

; store number at DIGIT1 (otherwise it may be overwritten)
	ST	R0, DIGIT1

;multiply first digit by ten to obtain tens position of two digit number
	JSR	CLEAR
	LD 	R3, DIGIT1
	ADD	R4, R4, #10
	JSR 	MULTIPLICATION			;multiply R3 x R4, store in TENS
	ST	R5, TENS
	
; get second user-entered number (result in R0)
; echo it back right away (otherwise it isn't visible)
	GETC
	PUTC
	ADD R0, R0, R2				;convert ascii to decimal

; store entered number at DIGIT2 (otherwise it may be overwritten)
	ST	R0, DIGIT2

;add second digit, now in decimal, to tens position
	LD	R3, TENS
	LD	R4, DIGIT2
	ADD	R5, R3, R4			;add tens + digit2 to get full number
	ST	R5, INPUT1			;store final decimal number in INPUT1

;check for zero input
	ADD	R5, R5, #0
	BRz	TWOIN

;;;;;;;;;;;;;;;;;;;;;;;; your 1st number is now converted to decimal ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;normalize - find the mantissa
	JSR	CLEAR				;clear registers
	LD	R3, INPUT1
	LD	R4, MANTMASK
	JSR	FINDMANT
	ST	R0, SHIFTCOUNT			;SHIFTCOUNT is now number of left shifts
	LD	R4, MANTDEF			;load R4 with 0000001111111111
	AND	R3, R4, R3			;AND to make R3 into the final MANTISSA
	ST	R3, MANTISSA1			;MANTISSA1 is now the final mantissa1
	LD	R5, MANTMASK
	ADD	R3, R3, R5			;add 0000010000000000 to mantissa1
	ST	R3, MANTFORMULT1

;find the exponent
	JSR 	CLEAR				;clear registers
	JSR	FINDEXP				;real exponent in EXPONENT1
	ST	R3, EXPONENT1			;R3 contains the real exponent

;;;;;;;;;;;;;;;;;;;;;;;; your 1st number is now converted to FP ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;ask for second number
TWOIN	JSR	CLEAR
	LEA	R0, SECONDNO
	PUTS

;check for the sign of the input number
	LD	R3, MINUS
	GETC					;get the sign
	PUTC					;put the sign
	ADD	R4, R3, R0			;is the sign a "-"? 
	BRz	NEG2				;yes? go to POS
	BR	CONT2

NEG2	LD	R3, SIGNMASK			;R3 has 1000000000000000
	ST	R3, SIGN2
	BR	USEIN2

CONT2	AND	R3, R3, #0			;clear R3
	ST	R3, SIGN2

; get first user-entered number (result in R0)
; echo it back right away (otherwise it isn't visible)
USEIN2	GETC
	PUTC
	ADD 	R0, R0, R2			;convert ascii to decimal

; store number at DIGIT3 (otherwise it may be overwritten)
	ST	R0, DIGIT3

;multiply first digit by ten to obtain tens position of two digit number
	LD 	R3, DIGIT3
	ADD	R4, R4, #10
	JSR 	MULTIPLICATION			;multiply R3 x R4, store in TENS
	ST	R5, TENS
	
; get second user-entered number (result in R0)
; echo it back right away (otherwise it isn't visible)
	GETC
	PUTC
	ADD R0, R0, R2				;convert ascii to decimal

; store entered number at DIGIT4 (otherwise it may be overwritten)
	ST	R0, DIGIT4

;add second digit, now in decimal, to tens position
	LD	R3, TENS
	LD	R4, DIGIT4
	ADD	R5, R3, R4			;add tens + digit4 to get full number
	ST	R5, INPUT2			;store final decimal number in INPUT2

;check for zero input(s)
	JSR	CLEAR
	LD	R3, INPUT1
	ADD	R3, R3 #0
	BRz	ZINPUT
	LD	R4, INPUT2
	ADD	R4, R4, #0
	BRz	ZINPUT

;;;;;;;;;;;;;;;;;;;;;;;; your 2nd number is now converted to decimal ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;normalize - find the mantissa
	JSR	CLEAR				;clear registers
	LD	R3, INPUT2
	LD	R4, MANTMASK
	JSR	FINDMANT
	ST	R0, SHIFTCOUNT			;SHIFTCOUNT is now number of left shifts
	LD	R4, MANTDEF			;load R4 with 0000000111111111
	AND	R3, R4, R3			;AND to make R3 into the final MANTISSA
	ST	R3, MANTISSA2			;MANTISSA2 is now the final mantissa2
	LD	R5, MANTMASK
	ADD	R3, R3, R5			;add 0000010000000000 to mantissa2
	ST	R3, MANTFORMULT2

;find the exponent
	JSR 	CLEAR				;clear registers
	JSR	FINDEXP
	ST	R3, EXPONENT2			;R3 contains the real exponent

;;;;;;;;;;;;;;;;;;;;;;; your 2nd number is now converted to FP ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;find the final sign
	JSR 	CLEAR
	LD	R3, SIGN1
	LD	R4, SIGN2
	ADD	R5, R4, R3
	ST	R5, SIGNF

;find the final mantissa
	JSR	CLEAR

	LD	R4, MANTFORMULT1
	ST	R4, QUOTIENT
	ADD	R5, R5, #4
	JSR	RIGHTSHIFT
	ST	R6, MANTFORMULT1

	LD	R4, MANTFORMULT2
	ST	R4, QUOTIENT
	ADD	R5, R5, #4
	JSR	RIGHTSHIFT
	ST	R6, MANTFORMULT2

	LD	R3, MANTFORMULT1
	LD	R4, MANTFORMULT2	
	JSR 	MULTIPLICATION			;multiply mantissas, product in R5
	ST	R5, PRODUCT
	LD	R6, MANTAND			
	AND	R6, R5, R6			;AND product with x2000
	BRnz	NEGZ
	BRp	POSIT

POSIT	AND	R5, R5, #0			;clear R5
	ADD	R5, R5, #1
	ST	R5, MANTOFF			;for adding one to the final exponent
	AND	R5, R5, #0
	ADD	R5, R5, #3
	LD	R4, PRODUCT
	ST	R4, QUOTIENT
	JSR	RIGHTSHIFT
	ST	R6, MANTISSAF
	JSR	CLEAR
	LD	R3, MANTISSAF
	LD 	R4, FINALMANTMASK		;mask final mantissa to get rid of leading 1
	AND	R5, R4, R3
	ST	R5, MANTISSAF
	BR	FINEX

NEGZ	AND	R5, R5, #0
	ADD	R5, R5, #2
	LD	R4, PRODUCT
	ST	R4, QUOTIENT
	JSR	RIGHTSHIFT
	ST	R6, MANTISSAF
	JSR	CLEAR
	LD	R3, MANTISSAF
	LD 	R4, FINALMANTMASK		;mask final mantissa to get rid of leading 1
	AND	R5, R4, R3
	ST	R5, MANTISSAF	

;find the final exponent
FINEX	JSR	CLEAR
	LD	R3, EXPONENT1
	LD	R4, EXPONENT2
	ADD	R5, R4, R3
	LD	R6, MANTOFF			;if final mantissa requires normalization
	ADD	R6, R5, R6			;add number of shifts to normalize final mantissa
	ST	R6, EXPONENTF			;final decimal exponent
	
	ADD	R3, R6, #15			;add bias to final exponent, store in R3
	LD	R4, NO10 			;10 is the no. of left shifts we need to do

EXPSHIFT: ADD	R3, R3, R3			;store R3 + R3 into R3 (left shift)
	ADD	R4, R4, #-1			;decrement R4
	BRp	EXPSHIFT			;repeat while R4 > 0

	LD	R4, EXPMASK
	AND	R3, R3, R4			;AND expmask and shifted fp exponent
	ST	R3, EXPFP			;final FP exponent (in position)

;;;;;;;;;;;;;;;;;;;;;;; you now have the final mantissa and exponent(s) ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;bring everything together
	JSR	CLEAR
	LD	R3, MANTISSAF
	LD	R4, EXPFP
	LD	R5, SIGNF
	ADD	R6, R5, R4			;combine sign and exponent bit strings
	ADD	R6, R6, R3			;ADD final mantissa string to the final bit string
	STI	R6, PRODLOC			;store product at x3200
	BR	STOP

;zero input check
ZINPUT	JSR	CLEAR
	STI	R0, PRODLOC
	BR	STOP

; stop the processor
STOP	HALT

;---------------------------------------SUBROUTINES---------------------------------------------
LDOFFSET
	LD	R1, DEC_OFF
	RET

LDNEGOFFSET
	LD	R2, NEG_DEC_OFF
	RET

MULTIPLICATION:					;multiplies R3 x R4, stores in R5
	ADD	R5, R5, R3			;add to sum
	ADD	R4, R4, #-1			;decrement multiplier
	BRp	MULTIPLICATION			;repeat until multiplier is zero
	RET

RIGHTSHIFT:					;shifts what's in R4 right by # times in R5

	AND	R3, R3, #0
	ADD	R3, R3, #2
	NOT	R3, R3				;not input2
	ADD	R3, R3, #1			; ~input2 + 1 (neg. input2)

	RES	LD	R4, QUOTIENT
		AND	R6, R6, #0		;clear integer quotient

	DIVIDE	ADD R6, R6, #1			;count iterations (integer quotient)
		ADD R4, R4, R3			;subtract input2 from input1
		BRp DIVIDE			;reiterate while R4>0
		BRz ZERO
		BRn NEG
	NEG	ADD R6, R6, #-1
		
	ZERO	ST  R6, QUOTIENT
		ADD R5, R5, #-1			;decrement iteration condition
		BRp RES				;load new quotient and repeat division by 2
		
	RET

FINDMANT:
	ADD	R0, R0, #1			;count number of left shifts
	ADD	R3, R3, R3			;add input to itself, update R3 to R3 x 2 (LEFT SHIFT)
	AND	R5, R4, R3			;AND with MANTMASK to see if a 1 is in bit pos. 10
	BRz	FINDMANT			;repeat until AND gives a positive (true) status code
	RET

FINDEXP:
	LD	R4, NO25
	LD	R3, SHIFTCOUNT
	NOT 	R3, R3				;NOT input2		<-----------------------
	ADD 	R3, R3, #1			;ADD 1 to ~input2		subtration
	ADD 	R3, R3, R4			;ADD input1 + ~input2	----------------------->
	ST	R3, FPEXP			;R3 now has the FP exponent
	LD	R4, FPEXP
	LD	R3, NO15
	NOT 	R3, R3				;NOT input2		<-----------------------
	ADD 	R3, R3, #1			;ADD 1 to ~input2		subtration
	ADD 	R3, R3, R4			;ADD input1 + ~input2	----------------------->
	RET
	

; clear all registers that we may use (keep decimal offset)
CLEAR: 
	AND	R0, R0, 0
	AND	R3, R3, 0
	AND	R4, R4, 0
	AND	R5, R5, 0
	AND	R6, R6, 0
	RET

; data declarations follow below

; variable
DIGIT1:		.FILL	0
DIGIT2:		.FILL	0
DIGIT3:		.FILL	0
DIGIT4:		.FILL	0
TENS:		.FILL	0
SIGN1:		.FILL	0
SIGN2:		.FILL	0
SIGNF:		.FILL	0
MANTISSA1:	.FILL	0
MANTISSA2:	.FILL	0
MANTISSAF:	.FILL	0
MANTOFF:	.FILL	0
MANTFORMULT1:	.FILL	0
MANTFORMULT2:	.FILL	0
FPEXP:		.FILL	0
EXPONENT1:	.FILL	0
EXPONENT2:	.FILL	0
EXPONENTF: 	.FILL	0
EXPFP:		.FILL	0
INPUT1:		.FILL	0
INPUT2:		.FILL	0
SHIFTCOUNT:	.FILL	0
FINALMANTMASK:	.FILL	b1111101111111111
EXPMASK:	.FILL	b0111110000000000
SIGNMASK:	.FILL	b1000000000000000
MANTMASK:	.FILL	b0000010000000000
MANTDEF:	.FILL	b0000001111111111
NO10:		.FILL	#10
NO15:		.FILL	#15
NO25:		.FILL	#25
MINUS:		.FILL	#-45
DEC_OFF:	.FILL	#48
NEG_DEC_OFF: 	.FILL	#-48
MANTAND:	.FILL	x2000
PRODUCT:	.FILL	0
QUOTIENT:	.FILL	0
PRODLOC:	.FILL	x3200

; strings
SECONDNO:	.STRINGZ	"\nPlease enter a second number with the same format: "
NEWLINE:	.STRINGZ	"\n"

; end of code
	.END

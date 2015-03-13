; CMPE12 - Winter 2014
; LCE3.asm
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

; print out the greeting
        JSR GREET
	PUTS

; Load R4 with -48 to offset
        JSR RFOUROFFSET

; get a user-entered character (result in R0)
        GETC
        PUTC
        ADD R0, R0, R4
        ADD R1, R1, R0

; print out a newline and some other stuff
        JSR NLINE
	PUTS

; get the second user-entered character (result in R0)
        GETC
        PUTC
        ADD R0, R0, R4
        ADD R2, R2, R0
        AND R4, R4, #0 ; Clears previous answer

; print out a newline and some other stuff
        JSR NLINE
	PUTS         
        AND R7, R7, #0

; Subtraction Call
        JSR SUBT

; Multiplication Call
 TOMUL  JSR MULT

; Division Call
 TODIV  JSR DIVT
           
; stop the processor
    END  HALT

; JSR FUNCTION SUBROUTINE CALLS

  ; The Subtraction Function
  SUBT AND	R4, R0, #0               
       LEA      R0, SUB
       PUTS
       BR  RFOUROFF                     ; Set R4 as OFFSET
   RRE ADD      R4, R4, #1              ; Add one to the OFFSET to get correct value
       NOT      R6, R2                  ; Load R6 with Negative value of R2
       ADD      R3, R1, R6              ; Add numbers
       ADD      R3, R3, R4              ; Correct answer for offset
       BR SUBX                          ; Store and Load Results
  SUBR PUTC
       LEA	    R0, NEWLINE
       PUTS
       BR TOMUL                         ; Jump to Multiplication
  
 ; The Multiplication Function
  MULT LEA     R0, MUL
       PUTS
       JSR CLEAR
       BR  RFIVEOFF
   RRR ADD R4, R4, R2                   ; Sets R4 with the second input contained in R2 ( to protect User input in R2 )

      ; Start Multiplication Loop
                AGAIN ADD R3, R3, R1    ; ADD R3 with R1 (User Input 1)
                      ADD R4, R4, -1   ; Deincrement R4 (Copy of User Input 2) in accordance with the number of times you want to multiply
                      BRp AGAIN         ; Goes back to AGAIN if R4 is not 0
       ; End Multiplication loop  

       JSR MULTM                        ; Jumps to Section where DECIMAL value of R3 is stored in x3101

       ; Start Correction Loop
             AGAINTWO ADD R6, R6, 1    ; Increment R6 ( The significant number ) the number of times AGAINTWO loops
                      ADD R3, R3, -10  ; Deincrement R3 ( The answer from Multiplication loop )  by 10 until negative
                      BRzp AGAINTWO      ; Goes back to AGAINTWO if R3 is still positive
       ; End Correction loop

       JSR MULTCOR                      ; Jumps to Section where the Multiplication values are corrected into ASCII
       ST       R6, RESULT
       LD       R0, RESULT
       PUTC                             ; Display MOST Significant Number
       ST       R3, RESULT
       LD       R0, RESULT
       PUTC                             ; Display LEAST Significant Number
       LEA      R0, NEWLINE
       PUTS
       BR TODIV                         ; Jump to Division

; The Division and Remainder Function 
  DIVT JSR CLEAR
       LEA R0, DIV
       PUTS
       LD  R4, OFFSET
       ADD R5, R5, R1                  ; Fill R5 with R1 (User input 1)
       ADD R3, R3, R2                  ; Fill R3 with R2 (User input 2)
       NOT R3, R3                      ; Get negative value from R3
       ADD R3, R3, #1                  ; Corrects for slight value offset

       ; Start Division Loop
            DIVAGAIN ADD R6, R6, #1    ; Increment R6 ( The number divisible ) the number of times DIVAGAIN loops
                     ADD R5, R5, R3    ; Subtract R3 from R5 ( This is the Remainder )
                     BRz CONT
                     BRp DIVAGAIN      ; Goes back to DIVAGAIN if R5 is still positive
       ; End Division loop

       ADD      R5, R5, R2             ; Add R2 to R5 to get the positive remainder value
       ADD      R6, R6, #-1            ; Subtract 1 from R6 ( For crossing into negative values )
 CONT  STI      R5, RR                 ; Stores R5 ( The Remainder ) into x3103
       ADD      R5, R5, R4             ; Convert R5 Decimal to ASCII
       STI      R6, DR                 ; Stores R6 ( The Divisor ) into x3102
       ADD      R6, R6, R4             ; Convert R6 Decimal to ASCII
       ST       R6, RESULT
       LD       R0, RESULT
       PUTC
       LEA      R0, REM
       PUTS
       ST       R5, REMAINDER
       LD       R0, REMAINDER
       PUTC
       LEA	R0, NEWLINE
       PUTS
       BR START                       ; Jump back to the begining to play a again

; BRANCH/ JSR MISC. FUNCTIONS

     ; Clears Registers for reuse
     CLEAR AND      R3, R3, 0 ; Clears previous answer
           AND      R4, R4, 0 ; Clears previous answer
           AND      R5, R5, 0 ; Clears previous answer
           AND      R6, R6, 0 ; Clears previous answer
           AND      R0, R0, 0 ; Clears previous answer
           RET
     
     GREET LEA	    R0, GREETING
           RET

     NLINE LEA	    R0, NEWLINE
           RET 

      ; Subtraction Function Storing and Loading    
      SUBX STI      R3, SR                  ; Stores DECIMAL Value of Subtraction Answer into x3100
           ST       R3, RESULT
           LD       R0, RESULT
           BR SUBR
      ; Multiplication Function Storing and Loading
     MULTM STI      R3, MR                  ; Stores DECIMAL Value of Multiplication Answer into x3101
           RET

      ; Multiplication Function value correction and ASCII conversion
   MULTCOR ADD      R6, R6, #-1             ; Most Significant Number Correction
           ADD      R3, R3, #10             ; Least Significant Number Correction
           ADD      R6, R6, R5              ; Most Significant Number OFFSET ADD
           ADD      R3, R3, R5              ; Least Significant Number OFFSET ADD
           RET
      
      ; R4 OFFSET NEGATIVE (For Use Anywhere)
  RFOUROFFSET LD     R4, OFFSETNEG
              RET

      ; Subtraction  R4 OFFSET POSITIVE
    RFOUROFF LD      R4, OFFSET ;
             BR RRE
   
   ; Multiplication R5 OFFSET POSITIVE
    RFIVEOFF LD      R5, OFFSET
             BR RRR

; data declarations follow below
; strings
GREETING:	.STRINGZ	"\n \nWelcome to Chris's Lab3 Program.\n Please enter two numbers: \n"
NEWLINE:	.STRINGZ        "\n"
SUB:         .STRINGZ        "SUBTRACTION: "
MUL:        .STRINGZ        "MULTIPLICATION: "
DIV:            .STRINGZ        "DIVISION: "
REM:         .STRINGZ        "\nREMAINDER: "

; variables
RESULT:         .FILL   0
REMAINDER:      .FILL   0
OFFSETNEG:      .FILL  -48
OFFSET:         .FILL   48
SR:             .FILL   x3100
MR:             .FILL   x3101
DR:             .FILL   x3102
RR:             .FILL   x3103       

; end of code
	.END

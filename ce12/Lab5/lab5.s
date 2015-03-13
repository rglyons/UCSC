
#include <WProgram.h>

/* define all global symbols here */
.global getDelay
.global milliseconds
.global T1Setup
.global T1Start
.global T1Stop

.text
.set noreorder


/*********************************************************************
 * Setup getDelay
 ********************************************************************/
.ent getDelay
getDelay:

           ADDI  $sp, $sp, -4           /*make room on stack*/
           sw    $ra, 0($sp)            /*push PC onto stack*/
           ANDI  $a1, $a1, #0           /*clear $a1*/
           ADD   $a1, $a1, $a0          /*add 'counter' argument into $a1*/
           ADDI  $a1, $a1, 1            /*increment 'counter'*/
           la    $a0, space
           jal   printf                 /*print iteration count*/
           nop
           lw    $ra, 0($sp)            /*pop PC off stack*/
           ADDI  $sp, $sp, 4            /*change stack pointer*/

/* read PORTD */
           lw    $v0, tuneMask          /*load 8-11 bit mask into $v0*/
           LW    $v1, PORTD
           AND   $v0, $v1, $v0
           SW    $v0, pinVal       /*'pinVal' holds switch values*/
           SLL   $v0, $v0, 12       /*shift bits 8-11 to bits 20-23*/
           sw    $v0, returnval

           lw    $v0, returnval
           jr    $ra

.end getDelay

/*********************************************************************
* Start Timer1
*********************************************************************/
.ent T1Start
T1Start:
/*Turn timer on*/
           la    $t1, T1CON
           li    $t0, 0x8000
           sw    $t0, 8($t1)          /* 'set' T1CON*/
/*Enable interrupts on IEC0[4:0]*/
           la    $t1, IEC0
           li    $t0, 0x0010
           sw    $t0, 8($t1)
           jr    $ra
           nop
.end T1Start

/*********************************************************************
* Stop Timer1
*********************************************************************/
.ent T1Stop
T1Stop:
/*Turn timer off*/
           la    $t1, T1CON
           li    $t0, 0x8000
           sw    $t0, 4($t1)          /* 'clear' T1CON*/
/*Disenable interrupts on IEC0[4:0]*/
           la    $t1, IEC0
           li    $t0, 0x0010
           sw    $t0, 4($t1)
           jr    $ra
           nop
.end T1Stop

/*********************************************************************
* Setup Timer and interrupts
*********************************************************************/
.ent T1Setup
T1Setup:
           ADDI  $sp, $sp, -4           /*make room on stack*/
           sw    $ra, 0($sp)            /*push PC onto stack*/
/*Clear T1CON*/
           la    $t0, T1CON
           li    $t1, 0xFFFF
           sw    $t1, 4($t0)
/*Set T1CKPS (configure to use max prescalar)*/
           la    $t0, T1CON
           li    $t1, 0b11
           sw    $t1, 8($t0)
/*Clear TMR1*/
           la    $t0, TMR1
           li    $t1, 0x1
           sw    $t1, 4($t0)
/*Set PR1*/
           la    $t0, PR1
           li    $t1, 0x1E84
           sw    $t1, 8($t0)
/*Set T1IP*/
           la    $t0, IPC1
           li    $t1, 0b100
           sw    $t1, 8($t0)
/*Clear T1IF*/
           la    $t0, IFS0
           li    $t1, 0x0010
           sw    $t1, 4($t0)
/*Enable interrupts*/
           la    $t0, IEC0
           li    $t1, 0x0010
           sw    $t1, 8($t0)

           ei

           lw    $ra, 0($sp)            /*pop PC off stack*/
           ADDI  $sp, $sp, 4            /*change stack pointer*/
           jr    $ra
           nop
.end T1Setup

/*********************************************************************
 * This is your ISR implementation. It is called from the vector table jump.
 ********************************************************************/
Lab5_ISR:
           addi  $sp, $sp, -4
           sw    $t0, 0($sp)
           addi  $sp, $sp, -4
           sw    $t1, 0($sp)

           lw    $t0, milliseconds
           addi  $t0, $t0, 1
           sw    $t0, milliseconds

           la    $t0, IFS0
           li    $t1, 0x0010
           sw    $t1, 4($t0)

           lw    $t1, 0($sp)
           addi  $sp, $sp, 4
           lw    $t0, 0($sp)
           addi  $sp, $sp, 4

           ERET
           nop
	
/*********************************************************************
 * This is the actual interrupt handler that gets installed
 * in the interrupt vector table. It jumps to the Lab5
 * interrupt handler function.
 ********************************************************************/
.section .vector_4, code
	j Lab5_ISR
	nop


.data
milliseconds: .word   0
returnval:    .word   0x80000
space:        .asciiz " %d\n"
tuneMask:     .word   3840
pinVal:       .word   0
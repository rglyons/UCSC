//Robert Lyons
//rglyons
//pa2
//ListClient.c

#include<stdio.h>
#include<stdlib.h>
#include"List.h"

int main(int argc, char* argv[]){
   
   List A = newList();
   List B = newList();
   List C = NULL;
   int i;

   for(i=1; i<=20; i++){
      append(A,i);
      prepend(B,i);
   }

   printList(stdout,A); 
   printf("\n");
   printList(stdout,B); 
   printf("\n");

   for(moveTo(A,0); getIndex(A)>=0; moveNext(A)){
      printf("%d ", getElement(A));
   }
   printf("\n");
   for(moveTo(B,length(B)-1); getIndex(B)>=0; movePrev(B)){
      printf("%d ", getElement(B));
   }
   printf("\n");

   C = copyList(A);
   printf("%s\n", equals(A,B)?"true":"false");
   printf("%s\n", equals(B,C)?"true":"false");
   printf("%s\n", equals(C,A)?"true":"false");

   moveTo(A,5);
   insertBefore(A,-1);
   moveTo(A,15);
   insertAfter(A,-2);
   moveTo(A,10);
   delete(A);
   printList(stdout,A);
   printf("\n");
   // printf("%d\n", length(A));
   clear(A);
   printf("%d\n", length(A));

   freeList(&A);
   freeList(&B);
   freeList(&C);

   return(0);
}

/*
 * Output of this program:
 * 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20
 * 20 19 18 17 16 15 14 13 12 11 10 9 8 7 6 5 4 3 2 1
 * 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20
 * 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20
 * false
 * false
 * true
 * 1 2 3 4 5 -1 6 7 8 9 11 12 13 14 15 -2 16 17 18 19 20
 * 0
 * */

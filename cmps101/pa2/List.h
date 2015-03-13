//Robert Lyons
//rglyons
//pa2
//List.h

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

typedef struct ListObj* List;

//Constructor-Destructor
List newList(void);
void freeList(List* pL);

//Access Functions
int length(List L);
int getIndex(List L);
int front(List L);
int back(List L);
int getElement(List L);
int equals(List A, List B);

//Manipulation procedures
void clear(List L);
void moveTo(List L, int i);
void movePrev(List L);
void moveNext(List L);
void prepend(List L, int data);
void append(List L, int data);
void insertBefore(List L, int data);
void insertAfter(List L, int data);
void deleteFront(List L);
void deleteBack(List L);
void delete(List L);

//Other operations
void printList(FILE* out, List L);
List copyList(List L);


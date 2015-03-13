// Robert Lyons
// rglyons
// pa5
// cmps101
// 3-8-15
// List.c
// Implementation for List class

#include <stdio.h>
#include <stdlib.h>
#include "List.h"
#include <string.h>

//--------------------node implementation -----------------------------------------------
typedef struct NodeObj {
      int item;
      struct NodeObj* prev;
      struct NodeObj* next;
} NodeObj;

typedef NodeObj* node;

//Constructor      
node Node(int data) {
  node newNode = malloc(sizeof(NodeObj));
  newNode->item = data;
  newNode->next = NULL;
  newNode->prev = NULL;
  return newNode;
}
//--------------------------------------------------------------------------------------
//----------------------List implementation---------------------------------------------
typedef struct ListObj {
   node first;
   node current;  //cursor element
   node last;
   int cursor;  //index of cursor element
   int length;
} ListObj;

typedef ListObj* List;

//Constructor
List newList (void) {
  List L = malloc(sizeof(ListObj));
  L->first = NULL;
  L->last = NULL;
  L->current = NULL;
  L->cursor = -1;
  L->length = 0;
  return L;
}

//Destructor
void freeList(List* pL) {

  if (pL != NULL && *pL != NULL) {

    clear(*pL);
    free(*pL);
    *pL = NULL;

  } else {
    fprintf(stderr, "%s\n", "List error: calling freeList() on NULL List reference.");
    exit(1);
  }
}

//returns the number of elements in the list
int length(List L) {

  if (L != NULL) {

    return L->length;

  } else {
    fprintf(stderr, "%s\n", "List error: calling length() on NULL List reference.");
    exit(1);
  }
}


//returns index of cursor element
int getIndex(List L) {

  if(L != NULL) {

    return L->cursor;
 
  } else {
    fprintf(stderr, "%s\n", "List error: calling getIndex() on NULL List reference.");
    exit(1);
  }
}


//returns front element
//Pre: length>0
int front (List L) {

  if(L != NULL) {

      if (L->first == NULL) { 
	fprintf(stderr, "List.front(): Cannot display front element because the list is empty!\n");
	exit(1);
      } else {
	return L->first->item;
      }

   } else {
    fprintf(stderr, "%s\n", "List error: calling front() on NULL List reference.");
    exit(1);
  }
}


//returns back element
//Pre: length>0
int back (List L) {

  if (L != NULL) {

      if (L->first == NULL) {
        fprintf(stderr, "List.back(): Cannot display back element because the list is empty!\n");
        exit(1);
      } else {
        return L->last->item;
      }
   
  } else {
    fprintf(stderr, "%s\n", "List error: calling back() on NULL List reference.");
    exit(1);
  }
}

//returns cursor elements in this list
//Pre: length>0, getIndex>=0
int getElement (List L) {
 
  if (L != NULL) {

     if (L->first == NULL) {
	fprintf(stderr, "List.getElement(): Cannot display the current element because the list is empty!\n");
	exit(1);
      }
      if (getIndex(L) < 0) {
	fprintf(stderr, "List.getElement(): Cursor undefined!\n");
	exit(1);
      }

      return L->current->item;
   
  } else {
    fprintf(stderr, "%s\n", "List error: calling getElement() on NULL List reference.");
    exit(1);
  }
}

//returns true is this List and L are the same integer sequence. Cursor is ignored.
int equals (List A, List B) {

if (A != NULL && B != NULL) {

      int i;
      node tempA = A->first;
      node tempB = B->first;

      if (length(A) != length(B)) return 0;

      for (i=0; i<length(A); i++) {

	if (tempA->item != tempB->item) return 0;

	tempA = tempA->next;
	tempB = tempB->next;
      }
      
      free(tempA);
      free(tempB);
      return 1;
   
  } else {
    fprintf(stderr, "%s\n", "List error: calling equals() on NULL List reference(s).");
    exit(1);
  }
}

//Resets this List to the empty state
void clear (List L) {

  if (L != NULL) {

     if (L->first == NULL) return;

      while (L->length != 0) {
	deleteFront(L);
      }	

      L->first = NULL;
      L->last = NULL;
      L->cursor = -1;
      L->current = NULL;
      L->length = 0;
   
  } else {
    fprintf(stderr, "%s\n", "List error: calling clear() on NULL List reference.");
    exit(1);
  }
}

//Moves cursor to element at argument index if 0<=argument<=length-1. 
//Otherwise  the cursor becomes undefined.
void moveTo (List L, int i) {

  if (L != NULL) {

      if (i < 0 || i > length(L)-1) {
	L->cursor = -1;
	return;
      }

      L->cursor = 0;
      L->current = L->first;
      while (L->cursor < i) {
	L->current = L->current->next;
	L->cursor++;
      }
   
  } else {
    fprintf(stderr, "%s\n", "List error: calling moveTo() on NULL List reference.");
    exit(1);
  }
}

//Moves cursor one step toward the front of List.
//If getIndex() == 0 the cursor becomes undef.
void movePrev (List L) {
 
  if (L != NULL) {

     if (0 < getIndex(L) && getIndex(L) <= length(L)-1){
	L->current = L->current->prev;
	L->cursor--;
      } else if (getIndex(L) == 0) {
	L->cursor = -1;
      } else return;
   
  } else {
    fprintf(stderr, "%s\n", "List error: calling movePrev() on NULL List reference.");
    exit(1);
  }
}

//Moves cursor one step toward the back of List.
//Equivalent to moveTo(getIndex()+1)
void moveNext (List L) {

  if (L != NULL) {

      if (0 <= getIndex(L) && getIndex(L) < length(L)-1){
        L->current = L->current->next;
        L->cursor++;
      } else if (getIndex(L) == length(L)-1) {
        L->cursor = -1;
      } else return;
   
  } else {
    fprintf(stderr, "%s\n", "List error: calling moveNext() on NULL List reference.");
    exit(1);
  }
}
 
//Insert new element before front element of List.
void prepend (List L, int data) {

  if (L != NULL) {

      node newNode = malloc(sizeof(NodeObj));
      newNode->item = data;

      if (L->first == NULL) {
	L->first = newNode;
	L->last = newNode;
      } else {
	L->first->prev = newNode;
        newNode->next = L->first;
        L->first = newNode;
      }
      L->length++;
   
  } else {
    fprintf(stderr, "%s\n", "List error: calling prepend() on NULL List reference.");
    exit(1);
  }
}

//Insert new element after back element in List
void append (List L, int data) {

  if (L != NULL) {

      node newNode = Node(data);

      if (L->first == NULL) {
	L->first = newNode;
	L->last = newNode;
      } else {
	L->last->next = newNode;
        newNode->prev = L->last;
        L->last = newNode;
      }
      L->length++;
   
  } else {
    fprintf(stderr, "%s\n", "List error: calling append() on NULL List reference.");
    exit(1);
  }
}

//Inserts new element before cursor element in List.
//Pre; length>0 and getIndex >=0
void insertBefore (List L, int data) {

  if (L != NULL) {

      if (length(L) <= 0 || getIndex(L) < 0) {
	fprintf(stderr, "List.insertBefore(): No node is present to insert before / cursor undefined!\n");
	exit(1);
      }

      node newNode = malloc(sizeof(NodeObj));
      newNode->item = data;
      newNode->prev = L->current->prev;
      newNode->next = L->current;
      if (L->current != L->first) L->current->prev->next = newNode;
      L->current->prev = newNode;
      if (L->current == L->first) L->first = newNode;
      L->cursor++;
      L->length++;
   
  } else {
    fprintf(stderr, "%s\n", "List error: calling insertBefore() on NULL List reference.");
    exit(1);
  }
}

//Inserts new element before cursor element in List.
//Pre; length>0 and getIndex >=0
void insertAfter (List L, int data) {

  if (L != NULL) {

      if (length(L) <= 0 || getIndex(L) < 0) {
        fprintf(stderr, "List.insertAfter(): No node is present to insert after / cursor undefined!\n");
        exit(1);
      }

      node newNode = malloc(sizeof(NodeObj));
      newNode->item = data;
      newNode->prev = L->current;
      newNode->next = L->current->next;
      if (L->current != L->last) L->current->next->prev = newNode;
      L->current->next = newNode;
      if (L->current == L->last) L->last = newNode;
      L->length++;
   
  } else {
    fprintf(stderr, "%s\n", "List error: calling insertAfter() on NULL List reference.");
    exit(1);
  }
}
 
//Deletes front element in List
//Pre: length>0
void deleteFront (List L) {

  if (L != NULL) {
      
      if (length (L) <= 0) {
	fprintf(stderr, "List.deleteFront(): No node exists to delete!\n");
	exit(1);
      }

      node temp;
      if (L->current == L->first) {
	L->current = L->current->next;
	L->cursor++;
      }
      temp = L->first;
      L->first = L->first->next;
      free(temp);
      L->cursor--;
      L->length--;
   
  } else {
    fprintf(stderr, "%s\n", "List error: calling deleteFront() on NULL List reference.");
    exit(1);
  }
}

//Deletes back element in List.
//Pre: length>0
void deleteBack (List L) {

  if (L != NULL) {

      if (length (L) <= 0) {
        fprintf(stderr, "List.deleteBack(): No node exists to delete!\n");
        exit(1);
      }

      node temp;
      if (L->current == L->last) {
	L->current = L->current->prev;
	L->cursor--;
      }
      temp = L->last;
      L->last = L->last->prev;
      free(temp);
      L->length--;
   
  } else {
    fprintf(stderr, "%s\n", "List error: calling deleteBack() on NULL List reference.");
    exit(1);
  }
}

//Deletes cursor element in List. Cursor is undef. afterwards.
//Pre: length>0, getIndex>=0
void delete (List L) {

  if (L != NULL) {
                               
      if (length(L) <= 0 || getIndex(L) < 0) {
	fprintf(stderr, "List.delete(): No node exists to delete / cursor undefined!\n");
	exit(1);
      }

      node temp = L->current;
      if (L->current == L->first){
	L->first = L->current->next;
      } else if (L->current == L->last){
        L->last = L->current->prev;
      } else {
	L->current->prev->next = L->current->next;
	L->current->next->prev = L->current->prev;
      }
      free(temp);
      L->cursor = -1;
      L->length--;
   
  } else {
    fprintf(stderr, "%s\n", "List error: calling delete() on NULL List reference.");
    exit(1);
  }
}

//Creates a String consisting of a space separated sequence of integers
//with front on the left and back on the right. Cursor is ignored.
void printList (FILE* out, List L) {

  if (L != NULL) {

    node temp = L->first;
    while(temp != NULL) {
      fprintf(out, "%d" "%s", temp->item, " ");
      temp = temp->next;
    }
   
    free(temp);

  } else {
    fprintf(stderr, "%s\n", "List error: calling printList() on NULL List reference.");
    exit(1);
  }
}

//Returns a new List representing the same integer sequence as this list.
//Cursor in new list is undef.
//This List is unchanged.
List copyList (List L) {

  if (L != NULL) {

      List copy = malloc(sizeof(ListObj));
      append(copy, L->first->item);
      L->current = L->first->next;
      while (L->current) {
	append(copy, L->current->item);
	L->current = L->current->next;
      }
      copy->cursor = -1;

      L->current = L->first;
      int i=0;
      while (i < L->cursor) {
        L->current = L->current->next;
        i++;
      }

      return copy;
   
  } else {
    fprintf(stderr, "%s\n", "List error: calling copyList() on NULL List reference.");
    exit(1);
  }
}

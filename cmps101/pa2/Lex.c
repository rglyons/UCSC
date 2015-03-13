// Robert Lyons
// rglyons
// pa2
// cmps101
// 1-23-15
// Lex.c

#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include "List.h"

#define maxLength 160

List insertSort(char** arr, int Length) {
  List L = newList();
  if (length > 0) append(L, 0);
  for (int i=1; i<Length; i++) {
    char *temp = arr[i];
    int index = i-1;
    moveTo(L, index);
    while (index > -1 && strcmp(temp, arr[getElement(L)]) < 1) {
      index--;
      movePrev(L);
    }
    if (getIndex(L) == -1) prepend(L, i);
    else insertAfter(L, i);
  }
  return L;
}

char *strdup (const char *s) {
  char *d = malloc(strlen(s) + 1);
  if (d == NULL) return NULL;
  strcpy(d,s);
  return d;
}

//function for reading input file lines into an array
char** read(FILE* in, int numLines) {
  char** array = malloc(sizeof(char**) * numLines);
  char line[maxLength];
  for (int i=0; i<numLines; i++) {
    fgets(line, maxLength, in); 
    array[i] = strdup(line);
  }
  return array;
}

void freeAll(char** lines, int lineCount, List L) {
  for (int i=0; i<lineCount; i++) {
    free(lines[i]);
  }
  free(lines);
  freeList(&L);
}

int main(int argc, char* argv[]) {
      
      int count = 0;
      char line[maxLength];
      
      //check for corrct usage
      if (argc != 3) {
	fprintf(stderr, "Usage: Lex [read file] [write file]\n");
	exit(1);
      }
      FILE* in = fopen(argv[1], "r");
      FILE* out = fopen(argv[2], "w");

      if (in == NULL) {
	fprintf(stderr, "Unable to open file %s for reading", argv[1]);
        return 1;
      }
      if (out == NULL) {
	fprintf(stderr, "Unable to open file %s for writing", argv[2]);
        return 1;
      }

      //count lines in read file
      while (fgets(line, maxLength, in) != NULL) count++;

      //read lines into array
      fclose(in);
      in = fopen(argv[1], "r"); //reset fgets
      char** lines = read(in, count);

      //carry out the sort
      List theList = insertSort(lines, count);

      //write to new file
      for (moveTo(theList, 0); getIndex(theList) >= 0; moveNext(theList)) {
	fprintf(out, "%s", lines[getElement(theList)]);
      }

      //close files
      fclose(in);
      fclose(out);
      freeAll(lines, count, theList);

      return 0;

}    

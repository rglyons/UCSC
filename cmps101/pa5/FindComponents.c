//Robert Lyons
//rglyons
//pa5
//FindComponents.c
//3-8-15

#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include "List.h"
#include "Graph.h"

void printComponents (FILE* out, Graph G, List S) {
  if (out != NULL && G != NULL && S != NULL) {

    fprintf(out, "G contains %d strongly connected components:\n", getComponents(G));
    if (length(S) != 0) moveTo(S, length(S)-1);

    List SCC = newList();
    int i = 1;
    while(getIndex(S) != -1 && i <= getComponents(G)) {
	int curr = getElement(S);
	fprintf(out, "Component %d: ", i);
	while (getParent(G, curr) != NIL) {
	  prepend(SCC, curr);
	  movePrev(S);
	  curr = getElement(S);
	}
	prepend(SCC, curr);     //add the root of current DFS tree
	movePrev(S);		//prep for next tree
	i++;
	printList(out, SCC);
	fprintf(out, "\n");
	clear(SCC);
    } 

    freeList(&SCC);

  } else {
    fprintf(stderr, "FindComponents.c: printComponent() called on NULL file/Graph/List/vertex");
    exit(1);
  }
}

int main (int argc, char * argv[]) {
  
  //Check for correct usage
  if (argc != 3) {
    printf("Usage: %s inputfile outputfile\n", argv[0]);
    exit(1);
  }

  //prepare input-output
  FILE *in, *out;
  in = fopen(argv[1], "r");
  if (in == NULL) {
    printf("ERROR: cannot open input file for reading!\n");
    exit(1);
  }
  out = fopen(argv[2], "w");
  if (out== NULL) {
    printf("ERROR: cannot open output file for writing!\n");
    exit(1);
  }

  //variables
  char line[5];
  char* content;
  int lineNo = 0;
  int n, x, y;
  Graph G, Transpose;
  List Stack = newList();

  //build the graph
  while (fgets(line, 500, in) != NULL) {
    lineNo++;
    content = strtok(line, " \n");
    if (lineNo == 1) {
      n = atoi(content);
      G = newGraph(n);
    } else {
      x = atoi(content);
      content = strtok(NULL, " \n");
      y = atoi(content);
      if (x != 0) addArc(G, x, y);
    }
  }
  fprintf(out, "Adjacency list representation of G:\n");
  printGraph(out, G);
  fprintf(out, "\n");

  //fill Stack so length(Stack) = getOrder(G)
  for(int j=1; j<=getOrder(G); j++) {
	append(Stack, j);
  }

  //run DFS (and change Stack to order of decreasing finishing times)
  DFS(G, Stack);

  //Find Traspose, run DFS to find Connected Components
  Transpose = transpose(G);
  DFS(Transpose, Stack);

  //Print strongly connected components
  printComponents(out, Transpose, Stack);

  //frees
  freeList(&Stack);
  freeGraph(&G);
  freeGraph(&Transpose);

  //close files
  fclose(in);
  fclose(out);

  return(0);

}

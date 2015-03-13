//Robert Lyons
//rglyons
//pa4
//FindPath.c
//2-26-15

#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include "List.h"
#include "Graph.h"

void printPath (FILE* out, Graph G, List L, int terminus) {
  if (out != NULL && G != NULL && L != NULL && terminus != NULL) {

    fprintf(out, "The distance from  %d to %d is ", getSource(G), terminus);

    if (front(L) == NIL) {
	fprintf(out, "infinity\n");
	fprintf(out, "No %d-%d path exists", getSource(G), terminus);
    } else {
	fprintf(out, "%d\n", getDist(G, terminus));
	fprintf(out, "A shortest %d-%d path is: ", getSource(G), terminus);
	printList(out, L);
    }

    fprintf(out, "\n");
    fprintf(out, "\n");

  } else {
    fprintf(stderr, "FindPath.c: printPath() called on NULL file/Graph/List/vertex");
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
  int finished = 0;
  int s, term, n, x, y;
  Graph G;
  List thePath = newList();

  //build the graph
  while (finished == 0 && fgets(line, 500, in) != NULL) {
    lineNo++;
    content = strtok(line, " \n");
    if (lineNo == 1) {
      n = atoi(content);
      G = newGraph(n);
    } else {
      x = atoi(content);;
      content = strtok(NULL, " \n");
      y = atoi(content);
      if (x != 0) addEdge(G, x, y);
      else finished = 1;
    }
  }
  printGraph(out, G);
  fprintf(out, "\n");

  //read vertices, run bfs, -> printPath
  while (fgets(line, 500, in) != NULL) {
    content = strtok(line, " \n");
    s = atoi(content);
    content = strtok(NULL, " \n");
    term = atoi(content);
    if (s != NIL) {
      BFS(G, s);
      clear(thePath);
      getPath(thePath, G, term);     
      printPath(out, G, thePath, term);	
    }
  }

  //frees
  freeList(&thePath);
  freeGraph(&G);

  //close files
  fclose(in);
  fclose(out);

  return(0);

}

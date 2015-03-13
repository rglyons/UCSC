//Rober Lyons
//rglyons
//pa4
//GraphTest.c
//2-26-15

#include <stdio.h>
#include <stdlib.h>
#include "List.h"
#include "Graph.h"
#include <string.h>

void printPath (FILE* out, Graph G, List L, int terminus) {
  if (out != NULL && G != NULL && L != NULL && terminus != NULL) {

    fprintf(out, "\nThe distance from  %d to %d is ", getSource(G), terminus);

    if (front(L) == NIL) {
        fprintf(out, "infinity\n");
        fprintf(out, "No %d-%d path exists", getSource(G), terminus);
    } else {
        fprintf(out, "%d\n", getDist(G, terminus));
        fprintf(out, "A shortest %d-%d path is: ", getSource(G), terminus);
        printList(out, L);
    }

    fprintf(out, "\n");

  } else {
    fprintf(stderr, "FindPath.c: printPath() called on NULL file/Graph/List/vertex");
    exit(1);
  }
}

int main (int argc, char* argv[]) {

  int n = 10;
  int terminus;
  Graph G = newGraph(n);
  Graph H = newGraph(n);;
  List thePath = newList();
  
  //build graphs
  addEdge(G, 1, 4);                     addArc(H, 1, 4);
  addEdge(G, 1, 5);			addArc(H, 1, 5);
  addEdge(G, 2, 3);			addArc(H, 2, 3);
  addEdge(G, 2, 6);			addArc(H, 2, 6);
  addEdge(G, 3, 7);			addArc(H, 3, 7);
  addEdge(G, 4, 5);			addArc(H, 4, 5);
  addEdge(G, 6, 7);			addArc(H, 6, 7);
  addEdge(G, 6, 8);			addArc(H, 6, 8);
  addEdge(G, 6, 9);			addArc(H, 6, 9);
  addEdge(G, 7, 1);			addArc(H, 7, 1);
  addEdge(G, 8, 4);			addArc(H, 8, 4);
  addEdge(G, 9, 8);			addArc(H, 9, 6);
  					addArc(H, 10, 3);
  					addArc(H, 10, 2);

  //print graphs
  printf("Undirected Graph (G):\n");
  printGraph(stdout, G);

  printf("Directed Graph (H):\n");
  printGraph(stdout, H);

  //test path finding
  printf("G path tests:\n");
  BFS(G, 10);
  terminus = 4;
  getPath(thePath, G, terminus);
  printPath(stdout, G, thePath, terminus);

  BFS(G, 2);
  clear(thePath);
  terminus = 7;
  getPath(thePath, G, terminus);
  printPath(stdout, G, thePath, terminus);
		
  BFS(G, 3);
  clear(thePath);
  terminus = 6;
  getPath(thePath, G, terminus);
  printPath(stdout, G, thePath, terminus);
	
  BFS(G, 1);
  clear(thePath);
  terminus = 7;
  getPath(thePath, G, terminus);
  printPath(stdout, G, thePath, terminus);

  printf("H path tests: \n");	
  BFS(H, 10);
  clear(thePath);
  terminus = 3;
  getPath(thePath, H, terminus);
  printPath(stdout, H, thePath, terminus);

  BFS(H, 2);
  clear(thePath);
  terminus = 3;
  getPath(thePath, H, terminus);
  printPath(stdout, H, thePath, terminus);

  BFS(H, 3);
  clear(thePath);
  terminus = 10;
  getPath(thePath, H, terminus);
  printPath(stdout, H, thePath, terminus);

  BFS(H, 1);
  clear(thePath);
  terminus = 7;
  getPath(thePath, H, terminus);
  printPath(stdout, H, thePath, terminus);
  
  //Misc Tests
  printf("Vertices in G: %d\n", getOrder(G));
  printf("Vertices in H: %d\n", getOrder(H));

  printf("Edges in G: %d\n", getSize(G));
  printf("Edges in H: %d\n", getSize(H));

  makeNull(G);
  printGraph(stdout, G);

  //frees
  freeList(&thePath);
  freeGraph(&G);
  freeGraph(&H);
	
  return 0;

}


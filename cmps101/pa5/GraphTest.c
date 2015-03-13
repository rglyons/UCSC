//Rober Lyons
//rglyons
//pa5
//GraphTest.c
//3-8-15

#include <stdio.h>
#include <stdlib.h>
#include "List.h"
#include "Graph.h"
#include <string.h>

void printComponent (FILE* out, Graph G, List L) {
  if (out != NULL && G != NULL && L != NULL) {

    fprintf(out, "\n");
    fprintf(out, "G contains %d connected components:\n", getComponents(G));
    printList(out, L);
    fprintf(out, "\n");

  } else {
    fprintf(stderr, "FindComponents.c: printComponent() called on NULL file/Graph/List/vertex");
    exit(1);
  }
}

int main (int argc, char* argv[]) {

  Graph G = newGraph(10);
  Graph H = newGraph(8);
  List Stack = newList();
  //fill Stack so length(Stack) = getOrder(H)
  for(int i=1; i<=getOrder(H); i++) {
    append(Stack, i);
  }
  
  //build graphs
  addEdge(G, 1, 5);                     addArc(H, 1, 2);
  addEdge(G, 1, 4);			addArc(H, 7, 8);
  addEdge(G, 2, 6);			addArc(H, 2, 5);
  addEdge(G, 2, 3);			addArc(H, 2, 3);
  addEdge(G, 3, 7);			addArc(H, 2, 6);
  addEdge(G, 4, 5);			addArc(H, 3, 4);
  addEdge(G, 6, 9);			addArc(H, 3, 7);
  addEdge(G, 6, 8);			addArc(H, 4, 8);
  addEdge(G, 6, 7);			addArc(H, 4, 3);
  addEdge(G, 7, 1);			addArc(H, 5, 1);
  addEdge(G, 8, 4);			addArc(H, 5, 6);
  addEdge(G, 9, 8);			addArc(H, 6, 7);
  					addArc(H, 8, 8);
					addArc(H, 7, 6);

  //print graphs
  printf("Undirected Graph (G):\n");
  printGraph(stdout, G);

  printf("Directed Graph (H):\n");
  printGraph(stdout, H);

  //tests
  DFS(H, Stack);
  printList(stdout, Stack);
  printf("\nCopy of H:\n");
  Graph Copy = copyGraph(H);
  printGraph(stdout, Copy);
  printf("Transpose of H:\n");
  Graph Transpose = transpose(H);
  printGraph(stdout, Transpose);
  DFS(Transpose, Stack);

  printComponent(stdout, Transpose, Stack);
  
  //Misc Tests
  printf("Vertices in G: %d\n", getOrder(G));
  printf("Vertices in H: %d\n", getOrder(H));

  printf("Edges in G: %d\n", getSize(G));
  printf("Edges in H: %d\n", getSize(H));

  //frees
  freeList(&Stack);
  freeGraph(&G);
  freeGraph(&H);
  freeGraph(&Copy);
  freeGraph(&Transpose);
	
  return 0;

}


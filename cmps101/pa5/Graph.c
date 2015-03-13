// Robert Lyons
// rglyons
// pa5
// cmps101
// 3-8-15
// Graph.c
// Implementation for Graph class

#include <stdio.h>
#include <stdlib.h>
#include "Graph.h"
#include "List.h"
#include <string.h>

typedef struct GraphObj {
    List* AdjListOf;     //ith element is a List of the neighbors of vertex i
    int* colorOf;       //ith element is the color of vertex i
    int* parentOf;         //ith element is the parent of vertex i
    int* discover;	//ith element is the discover time of vertex i
    int* finish;		//ith element iss the finish time of the vertex i
    int order;               //number of vertices in graph
    int size;                //number of edges in graph
    int components;	//number of connectde components in Graph
} GraphObj;
   
typedef GraphObj* Graph;
int time;

//Constructor
Graph newGraph (int n) {
  if (n != NULL) {

    Graph G = malloc(sizeof(GraphObj));
    G->AdjListOf = calloc(n+1, sizeof(List));
    G->colorOf = calloc(n+1, sizeof(int));
    G->parentOf = calloc(n+1, sizeof(int));
    G->discover = calloc(n+1, sizeof(int));
    G->finish = calloc(n+1, sizeof(int));
    G->order = n;
    G->size = 0;
    G->components = 0;

  for (int i=1; i<=n; i++) {
    G->AdjListOf[i] = newList();
    G->colorOf[i] = WHITE;
    G->parentOf[i] = NIL;
    G->discover[i] = UNDEF;
    G->finish[i] = UNDEF;
  }

  return(G);

  } else {
    fprintf(stderr, "Graph.c: newGraph() called on NULL integer vertex count.\n");
    exit(1);
  }
}

//Destructor
void freeGraph (Graph* pG) {
  if (pG != NULL && *pG != NULL) {

    for (int i=1; i<=getOrder(*pG); i++) {
	freeList(&((*pG)->AdjListOf[i]));
    }
    free((*pG)->AdjListOf);
    free((*pG)->colorOf);
    free((*pG)->parentOf);
    free((*pG)->discover);
    free((*pG)->finish);
    free(*pG);
    *pG = NULL;

  } else {
    fprintf(stderr, "%s\n", "Graph.c: freeGraph() called on NULL Graph reference.");
    exit(1);
  }
}

//Access Functions
int getOrder (Graph G) {
  if (G != NULL) {

    return G->order;

  } else {
    fprintf(stderr, "%s\n", "Graph.c: getOrder() called on NULL Graph reference.");
    exit(1);
  }
}

int getSize (Graph G) {
  if (G != NULL) {

    return G->size;

  } else {
    fprintf(stderr, "%s\n", "Graph.c: getSize() called on NULL Graph reference.");
    exit(1);
  }
}

int getComponents (Graph G) {
  if (G != NULL) {

    return G->components;

  } else {
    fprintf(stderr, "Graph.c: getComponents() called on NULL Graph reference.");
    exit(1);
  }
}

int getParent (Graph G, int u) {
  if (G != NULL && u != NULL) {

    if (u < 1 || u > getOrder(G)) {
	printf("Graph.c: getParent() called on invalid vertex");
	exit(1);
    }

    return G->parentOf[u];

  } else {
    fprintf(stderr, "%s\n", "Graph.c: getParent() called on NULL Graph/vertex reference.");
    exit(1);
  }
}

int getDiscover (Graph G, int u) {
  if (G != NULL && u != NULL) {

    if (u < 1 || u > getOrder(G)) {
        printf("Graph.c: getDiscover() called on invalid vertex");
        exit(1);
    }

    return G->discover[u];

  } else {
    fprintf(stderr, "Graph.c: getDiscover() called on NULL Graph/integer reference.\n");
    exit(1);
  }
}

int getFinish (Graph G, int u) {
  if (G != NULL && u != NULL) {

    if (u < 1 || u > getOrder(G)) {
        printf("Graph.c: getFinish() called on invalid vertex");
        exit(1);
    }

    return G->finish[u];

  } else {
    fprintf(stderr, "Graph.c: getFinish() called on NULL Graph/integer reference.\n");
    exit(1);
  }
}

//Manipulation Procedures
void addEdge (Graph G, int u, int v) {
  if (G != NULL && u != NULL && v != NULL) {

    if (u < 1 || u > getOrder(G) || v < 1 || v > getOrder(G)) {
        printf("Graph.c: addEdge() called on invalid vertex");
        exit(1);
    }

    int indexU = getIndex(G->AdjListOf[u]);
    int indexV = getIndex(G->AdjListOf[v]);

    if (length(G->AdjListOf[u]) == 0 && length(G->AdjListOf[v]) == 0) {
	append(G->AdjListOf[u], v);
	append(G->AdjListOf[v], u);
	++G->size;
	return;
    } else if (length(G->AdjListOf[u]) == 0 && length(G->AdjListOf[v]) != 0) {
	append(G->AdjListOf[u], v);
 	moveTo(G->AdjListOf[v], 0);
        while (getIndex(G->AdjListOf[v]) != -1 && u > getElement(G->AdjListOf[v])) {  //scan through adj list to find where new entry belongs
          moveNext(G->AdjListOf[v]);
        }
        if (getIndex(G->AdjListOf[v]) != -1) insertBefore(G->AdjListOf[v], u); //off the end of the adj list?
        else append(G->AdjListOf[v], u);
	++G->size;
	moveTo(G->AdjListOf[v], indexV); //put list back to original state
	return;
    } else if (length(G->AdjListOf[v]) == 0 && length(G->AdjListOf[u]) != 0) {
	append (G->AdjListOf[v], u);
	moveTo(G->AdjListOf[u], 0);
	while (getIndex(G->AdjListOf[u]) != -1 && v > getElement(G->AdjListOf[u])) {
          moveNext(G->AdjListOf[u]);
        }
        if (getIndex(G->AdjListOf[u]) != -1) insertBefore(G->AdjListOf[u], v);
        else append(G->AdjListOf[u], v);
	++G->size;
	moveTo(G->AdjListOf[u], indexU);
	return;
    } else if (length(G->AdjListOf[u]) != 0 && length(G->AdjListOf[v]) != 0) {
        moveTo(G->AdjListOf[u], 0);
        moveTo(G->AdjListOf[v], 0);

        while (getIndex(G->AdjListOf[v]) != -1 && u > getElement(G->AdjListOf[v])) {
	  moveNext(G->AdjListOf[v]);
        }  
        if (getIndex(G->AdjListOf[v]) != -1) insertBefore(G->AdjListOf[v], u);
        else append(G->AdjListOf[v], u);

        while (getIndex(G->AdjListOf[u]) != -1 && v > getElement(G->AdjListOf[u])) {
	  moveNext(G->AdjListOf[u]);
        }
        if (getIndex(G->AdjListOf[u]) != -1) insertBefore(G->AdjListOf[u], v);
        else append(G->AdjListOf[u], v);
        ++G->size;
        moveTo(G->AdjListOf[u], indexU);
        moveTo(G->AdjListOf[v], indexV);
        return;
    }

  } else {
    fprintf(stderr, "%s\n", "Graph.c: addEdge() called on NULL Graph/vertex reference.");
    exit(1);
  }
}

void addArc (Graph G, int u, int v) {
  if (G != NULL && u != NULL && v != NULL) {

    if (u < 1 || u > getOrder(G) || v < 1 || v > getOrder(G)) {
        printf("Graph.c: addArc() called on invalid vertex");
        exit(1);
    }

    int indexU = getIndex(G->AdjListOf[u]);

    if (length(G->AdjListOf[u]) == 0) {
	append(G->AdjListOf[u], v);
	++G->size;
	return;
    } else {
	moveTo(G->AdjListOf[u], 0);
	while (getIndex(G->AdjListOf[u]) != -1 && v > getElement(G->AdjListOf[u])) {
          moveNext(G->AdjListOf[u]);
        }
        if (getIndex(G->AdjListOf[u]) != -1) insertBefore(G->AdjListOf[u], v);
        else append(G->AdjListOf[u], v);
        ++G->size;
	moveTo(G->AdjListOf[u], indexU);
    }

  } else {
    fprintf(stderr, "%s\n", "Graph.c: addArc() called on NULL Graph/vertex reference.");
    exit(1);
  }
}

void VISIT (Graph G, int u, List L) {
  if (u != NULL) {

    G->colorOf[u] = GREY;
    time++;
    G->discover[u] = time;
    moveTo(G->AdjListOf[u], 0);
    for(int i=0; i<length(G->AdjListOf[u]); i++) {
        int v = getElement(G->AdjListOf[u]);
        if (G->colorOf[v] == WHITE) {
            G->parentOf[v] = u;
            VISIT(G, v, L);
        }
        moveNext(G->AdjListOf[u]);
    }
    G->colorOf[u] = BLACK;
    prepend(L, u);
    G->finish[u] = ++time;

  } else {
    fprintf(stderr, "Graph.c: VISIT() called on NULL vertex reference.\n");
    exit(1);
  }
}

void DFS (Graph G, List S) {
  if (G != NULL && S != NULL) {

    if (length(S) != getOrder(G)) {
	fprintf(stderr, "Graph.c: DFS() called on List of incorrect length.\n");
	exit(1);
    }

    for (int i=1; i<=getOrder(G); i++) {
	G->colorOf[i] = WHITE;
	G->parentOf[i] = NIL;
    }

    List L = newList(); //for the topological sort
    time = 0;
    G->components = 0;
    moveTo(S, 0);
    while (getIndex(S) != -1) {
	int j = getElement(S);
	if (G->colorOf[j] == WHITE) {
	  VISIT(G, j, L);
	  ++G->components;
	}
	moveNext(S);
    }

    moveTo(L, 0);
    while (getIndex(L) != -1) { //replace S with topological sort
	append(S, getElement(L));
	moveNext(L);
	deleteFront(S);
    }

    freeList(&L);

  } else {
    fprintf(stderr, "Graph.c: DFS() called on NULL Graph/List reference.\n");
    exit(1);
  }
}

//Other operations
void printGraph(FILE* out, Graph G){
  if (G == NULL) {
    fprintf(stderr, "%s\n", "Graph.c: printGraph() called on NULL Graph reference");
    exit(1);
  }
	
  int temp = 0;
  for (int i=1; i<=getOrder(G); i++) {
    fprintf(out, "%d:", i);
    if (length(G->AdjListOf[i]) != 0) {
      moveTo(G->AdjListOf[i], 0);
      for (int j=0; j<length(G->AdjListOf[i]); j++) {
         temp = getElement(G->AdjListOf[i]);
         moveNext(G->AdjListOf[i]);
         fprintf(out, " %d", temp);
      }
    }
    fprintf(out, "\n");
  }
}

Graph copyGraph(Graph G) {
  if (G != NULL) {

    Graph H = newGraph(G->order);
    H->size = G->size;
    for (int i=1; i<=getOrder(G); i++) {
	if (length(G->AdjListOf[i]) != 0) moveTo(G->AdjListOf[i], 0);
	while (getIndex(G->AdjListOf[i]) != -1) {
	  append(H->AdjListOf[i], getElement(G->AdjListOf[i]));
	  moveNext(G->AdjListOf[i]);
	}
	H->colorOf[i] = G->colorOf[i];
	H->parentOf[i] = getParent(G, i);
	H->discover[i] = getDiscover(G, i);
	H->finish[i] = getFinish(G, i);
    }

    H->size = getSize(G);
    H->components = getComponents(G);
    return H;

  } else {
    fprintf(stderr, "Graph.c: copyGraph() called on NULL Graph reference.\n");
    exit(1);
  }
}

Graph transpose (Graph G) {
  if (G != NULL) {

    Graph H = newGraph(getOrder(G));
    for (int i=1; i<=getOrder(G); i++) {
      for(int j=0; j<length(G->AdjListOf[i]); j++) {
	moveTo(G->AdjListOf[i], j);
	addArc(H, getElement(G->AdjListOf[i]), i);
      }
    }

    return H;

  } else {
    fprintf(stderr, "Graph.c: transpose() called on NULL Graph reference.\n");
    exit(1);
  }
}


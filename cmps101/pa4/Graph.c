// Robert Lyons
// rglyons
// pa4
// cmps101
// 2-24-15
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
    int* distFromSource;     //number of edges traversed from most recent source to vertex i
    int order;               //number of vertices in graph
    int size;                //number of edges in graph
    int source;              //updated each time BFS() is called on a new vertex
} GraphObj;
   
typedef GraphObj* Graph;

//Constructor
Graph newGraph (int n) {
  if (n != NULL) {

    Graph G = malloc(sizeof(GraphObj));
    G->AdjListOf = calloc(n+1, sizeof(List));
    G->colorOf = calloc(n+1, sizeof(int));
    G->parentOf = calloc(n+1, sizeof(int));
    G->distFromSource = calloc(n+1, sizeof(int));
    G->order = n;
    G->size = 0;
    G->source = NIL;

  for (int i=1; i<=n; i++) {
    G->AdjListOf[i] = newList();
    G->colorOf[i] = WHITE;
    G->parentOf[i] = NIL;
    G->distFromSource[i] = INF;
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
    free((*pG)->distFromSource);
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

int getSource (Graph G) {
  if (G != NULL) {

    return G->source;			//updated when BFS is called on a new source

  } else {
    fprintf(stderr, "%s\n", "Graph.c: getSource() called on NULL Graph reference.");
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

int getDist (Graph G, int u) {
 if (G != NULL && u != NULL) {

    if (u < 1 || u > getOrder(G)) {
        printf("Graph.c: getDist() called on invalid vertex");
        exit(1);
    }

    return G->distFromSource[u];           //whole array is updated with each run on a new source in BFS()

  } else {
    fprintf(stderr, "%s\n", "Graph.c: getDist() called on NULL Graph/vertex reference.");
    exit(1);
  }
}

void getPath (List L, Graph G, int u) {
  if (G != NULL && u != NULL && L != NULL) {

    if (u < 1 || u > getOrder(G)) {
        printf("Graph.c: getPath() called on invalid vertex");
        exit(1);
    }
    if (getSource(G) == NIL) {
	printf("Graph.c: getPath() called on NIL source");
	exit(1);
    }
    
    if (u == getSource(G)) append(L, u);
    else if (getParent(G, u) == NIL) append(L, NIL);
    else {
	prepend(L, u);
	int parent = G->parentOf[u];
	while (parent != getSource(G) && parent != NIL) {
	  prepend(L, parent);
	  parent = G->parentOf[parent];
	}
	if (parent == NIL) prepend(L, NIL);
	else prepend(L, parent);
    }

  } else {
    fprintf(stderr, "%s\n", "Graph.c: getPath() called on NULL Graph/vertex/List reference.");
    exit(1);
  }
}

//Manipulation Procedures
void makeNull (Graph G) {
  if (G != NULL) {

    for (int i=1; i<=getOrder(G); i++) {
	clear(G->AdjListOf[i]);
	G->colorOf[i] = WHITE;
	G->distFromSource[i] = INF;
	G->parentOf[i] = NIL;
    }
    G->size = 0;
    G->source = NIL;

  } else {
    fprintf(stderr, "%s\n", "Graph.c: makeNull() called on NULL Graph reference");
    exit(1);
  }
}

void addEdge (Graph G, int u, int v) {
  if (G != NULL && u != NULL && v != NULL) {

    if (u < 1 || u > getOrder(G) || v < 1 || v > getOrder(G)) {
        printf("Graph.c: addEdge() called on invalid vertex");
        exit(1);
    }

    append(G->AdjListOf[u], v);
    append(G->AdjListOf[v], u);
    ++G->size;

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

    append(G->AdjListOf[u], v);
    ++G->size;

  } else {
    fprintf(stderr, "%s\n", "Graph.c: addArc() called on NULL Graph/vertex reference.");
    exit(1);
  }
}

void BFS (Graph G, int s) {
  if (G != NULL && s != NULL) {

    if (s < 1 || s > getOrder(G)) {
        printf("Graph.c: BFS() called on invalid vertex");
        exit(1);
    }

    int QFront, x;
    G->source = s;
    for (int i=1; i<=getOrder(G); i++) {
	G->colorOf[i] = WHITE;
	G->distFromSource[i] = INF;
	G->parentOf[i] = NIL;
    }
    G->colorOf[s] = GREY;
    G->distFromSource[s] = 0;
    G->parentOf[s] = NIL;
    List Queue = newList();
    append(Queue, s);

    while (length(Queue) > 0) {
	QFront = front(Queue);
	deleteFront(Queue);
	if (length(G->AdjListOf[QFront]) > 0) moveTo(G->AdjListOf[QFront], 0);
	while (getIndex(G->AdjListOf[QFront]) != -1) {           //for whole adj list of QFront
	    x = getElement(G->AdjListOf[QFront]);
	    if (G->colorOf[x] == WHITE) {
	       G->colorOf[x] = GREY;
	       G->distFromSource[x] = getDist(G, QFront) + 1;
	       G->parentOf[x] = QFront;
	       append(Queue, x);
	    }
	    moveNext(G->AdjListOf[QFront]);
	}
	G->colorOf[QFront] = BLACK;
    }
    freeList(&Queue);

  } else {
    fprintf(stderr, "%s\n", "Graph.c: BFS() called on NULL Graph/source reference.");
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


//Robert LYons
//rglyons
//pa4
//Graph.h
//header file listing "public" functions for Graph.c

#define INF -1
#define NIL 0
#define WHITE 0
#define GREY 1
#define BLACK 2

#include "List.h"

typedef struct GraphObj* Graph;

//Constructor - Destructor
Graph newGraph(int n);
void freeGraph(Graph* pG);

//Access Functions
int getOrder(Graph G);
int getSize(Graph G);
int getSource(Graph G);
int getParent(Graph G, int u);
int getDist(Graph G, int u);
void getPath(List L, Graph G, int u);

//Manipulation Procedures
void makeNull(Graph G);
void addEdge(Graph G, int u, int v);
void addArc(Graph G, int u, int v);
void BFS(Graph G, int s);

//Other Operations
void printGraph(FILE* out, Graph G);

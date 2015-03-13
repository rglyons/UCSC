//Robert LYons
//rglyons
//pa5
//Graph.h
//header file listing "public" functions for Graph.c

#define UNDEF -1
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
int getComponents(Graph G);
int getParent(Graph G, int u);
int getDiscover(Graph G, int u);
int getFinish(Graph G, int u);

//Manipulation Procedures
void addEdge(Graph G, int u, int v);
void addArc(Graph G, int u, int v);
void DFS(Graph G, List S);

//Other Operations
void printGraph(FILE* out, Graph G);
Graph transpose(Graph G);
Graph copyGraph(Graph G);

// Robert Lyons
// rglyons
// cmps12m
// 11/23/14
// queue.c
// implementation of a queue in C

#include <assert.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "queue.h"

#define STUBPRINTF(...) fprintf(stderr, __VA_ARGS__);

/* Internal implementation definitions */
struct queue_node {
   queue_item_t item;
   struct queue_node *link;
};

typedef struct queue_node queue_node;

struct queue {
   queue_node *front;
   queue_node *rear;
};

/* Functions */

queue *queue_new(void) {
   struct queue *queue = (struct queue*)malloc(sizeof(struct queue));
   queue->front = NULL;
   queue->rear = NULL;
   return queue;
}

void queue_free(queue *this) {
   assert(queue_isempty(this));
   free(this);
}

void queue_insert(queue *this, queue_item_t item) {
   if (item == NULL || this == NULL) return;
   struct queue_node *temp = (struct queue_node*)malloc(sizeof(struct queue_node));
   temp->item = item;
   if(this->front == NULL){
	this->front = temp;
	this->rear = temp;
	this->front->link = NULL;
   } else {
	queue_node *last = this->front;
	while (last->link != NULL) last = last->link;
	last->link = temp;
	temp->link = NULL;
	this->rear = temp;
   }
}

queue_item_t queue_remove(queue *this) {
   assert(!queue_isempty(this));
   if (queue_isempty(this)) return NULL;
   struct queue_node *oldFront = this->front;
   queue_item_t a = this->front->item;
   this->front = this->front->link;
   free(oldFront);
   return a;
}

bool queue_isempty(queue *this) {
   return this->front == NULL;
}

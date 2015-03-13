// Robert Lyons
// rglyons
// cmps012m
// 11/23/14
// queue.h
// interface to the queue ADT; header file for queue.c
 
#ifndef __QUEUE_H__
#define __QUEUE_H__

#include <stdbool.h>

/* External interface declarations */

struct queue;
typedef struct queue queue;
typedef char *queue_item_t;

/* Function declarations */

queue *queue_new(void);
	//Create a new queue
	//and return it
void queue_free(queue*);
	//free the argument queue
void queue_insert(queue*, queue_item_t);
	//Insert queue_item_t into the queue
queue_item_t queue_remove(queue*);
	//remove queue_item_t from the queue 
	//and return the updated queue
bool queue_isempty(queue*);
	//return true if queue is empty 
	//or false if queue is not empty
#endif

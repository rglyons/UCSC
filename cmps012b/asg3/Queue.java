//Robert Lyons
//rglyons
//cmps12b
//11/20/14
//Queue.java
//Implementation of a queue to hold line number positions for words in a text file

import java.util.Iterator;
import java.util.NoSuchElementException;

class Queue <Item> implements Iterable <Item> {

   private class Node {
      Item item;
      Node next;
   }
   private Node head = null;
   private Node tail = null;

   public boolean isempty() {
      if (head == null && tail == null)
	return true;
      else return false;
   }

   public void insert(Item newitem) {
      if (head == null && tail == null){
        Node temp = new Node();
        temp.item = newitem;
        head = temp;
	tail = temp;
	return;
      }
      Node temp = new Node();
      temp.item = newitem;
      temp.next = null;
      tail.next = temp;
      tail = temp;
   }

   public Iterator <Item> iterator() {
      return new Itor ();
   }

   class Itor implements Iterator <Item> {
      Node current = head;
      public boolean hasNext() {
         return current != null;
      }
      public Item next() {
         if (! hasNext ()) throw new NoSuchElementException();
         Item result = current.item;
         current = current.next;
         return result;
      }
      public void remove(){ 
	throw new UnsupportedOperationException();
      }
   }

}

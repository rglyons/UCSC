// Robert Lyons
// rglyons
// cmps12b
// 11-5-14
// dllist.java
// Implementation for dllist class

public class dllist {

   public enum position {FIRST, PREVIOUS, FOLLOWING, LAST};

   private class node {
      String item;
      node prev;
      node next;
   }

   private node first = null;
   private node current = null;
   private node last = null;
   private int currentPosition = 0;

   public void setPosition (position pos) {
      if (pos == position.FIRST){
	currentPosition = 0;
	current = first;
      } 
      if (pos == position.FOLLOWING && current != last){
	current = current.next;
	currentPosition++;
      }
      if (pos == position.PREVIOUS && current != first){
	current = current.prev;
	currentPosition--;
      }
      if (pos == position.LAST){
	while(current.next != null){
	  current = current.next;
	  currentPosition++;
	}
        last = current;
	current = last;
      }
   }

   public boolean isEmpty () {
      boolean result = true;
      if (first == null) result = true;
      if (first != null) result = false;
      return result;
   }

   public String getItem () {
	  if (first == null)
	    throw new java.util.NoSuchElementException();
	  else
	    return current.item;
   }

   public int getPosition () {
	if (first == null){
	  throw new java.util.NoSuchElementException();
        } else
	  return currentPosition;
   }

   public void delete () {                               //------------------------------
        if (first == null) throw new java.util.NoSuchElementException();
        if (current == first){
	  first = current.next;
	  current = first;
        } else if (current == last){
          last = current.prev;
          currentPosition--;
	  current = last;
        } else {
	  current.prev.next = current.next;
	  current.next.prev = current.prev;
	  current = current.next;
        }
   }

   public void insert (String item, position pos) {
      node newLink = new node();
      newLink.item = item;
      node temp = first;
      int temp2 = 0;
      if (isEmpty()){
	if (pos == position.PREVIOUS)
          throw new java.lang.IllegalArgumentException();
        if (pos == position.FOLLOWING)
          throw new java.lang.IllegalArgumentException();
	first = newLink;
	current = first;
	last = newLink;
        } else if (pos == position.FIRST){                   // code taken from Data Structures
	  first.prev = newLink;                            // and Algorithms in Java 2nd Ed.
	  newLink.next = first;                            // p. 223 - 229
	  first = newLink;
	  current = first;
	  currentPosition = 0;
        } else if (pos == position.LAST){
	  last.next = newLink;
	  newLink.prev = last;
	  last = newLink;
	  current = last;
	  while(temp != current){
	    temp = temp.next;
	    temp2++;
	}
	currentPosition = temp2;
      }
      if (current == last && pos == position.FOLLOWING){
	newLink.next = null;
	last = newLink;
	newLink.prev = current;
	current.next = newLink;
	current = last;
	currentPosition++;
      } else if (current != last && pos == position.FOLLOWING){
	newLink.next = current.next;
	current.next.prev = newLink;
	newLink.prev = current;
	current.next = newLink;
	current = newLink;
	currentPosition++;                             //-----------------------------
      }
      if (current == first && pos == position.PREVIOUS){
	newLink.prev = null;
	first = newLink;
	newLink.next = current;
	current.prev = newLink;
	current = first;
	currentPosition = 0;
      } else if (current != first && pos == position.PREVIOUS){
	newLink.prev = current.prev;
	current.prev.next = newLink;
	newLink.next = current;
	current.prev = newLink;
	current = newLink;
      }
   }
}


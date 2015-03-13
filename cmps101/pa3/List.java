// Robert Lyons
// rglyons
// pa3
// cmps101
// 2-3-15
// List.java
// Implementation for List class

public class List {

   private class node {
      Object obj;
      node prev;
      node next;
      
      node Node(Object x) {
	node newNode = new node();
	newNode.obj = x;
	newNode.next = null;
	newNode.prev = null;
	return newNode;
      }

      public String toString () {

	return this.obj.toString();

      }

   }

   private node first = null;
   private node current = null;  //cursor element
   private node last = null;
   private int cursor = -1;  //index of cursor element
   private int length = 0;

   //Constructor
   public void List () {
      List theList = new List ();
   }

   //returns the number of elements in the list
   public int length() {
      return length;
   }

   //returns index of cursor element
   public int getIndex() {
      return cursor;
   }

   //returns front element
   //Pre: length>0
   public Object front () {
      if (isEmpty()) { 
	System.out.println ("Cannot display front element because the list is empty!");
	throw new RuntimeException();
      } else {
	return first.obj;
      }
   }

   //returns back element
   //Pre: length>0
   public Object back () {
      if (isEmpty()) {
        System.out.println("Cannot display back element because the list is empty!");
        throw new RuntimeException();
      } else {
        return last.obj;
      }
   }

   //returns true if the list is empty
   public boolean isEmpty () {
      return first == null;
   }

   //returns cursor elements in this list
   //Pre: length>0, getIndex>=0
   public Object getElement () {
 
     if (isEmpty()) {
	System.out.println("Cannot display the current element because the list is empty!");
	throw new RuntimeException();
   }
      if (getIndex() < 0) {
	System.out.println("Cursor undefined!");
	throw new RuntimeException();
      }

      return current.obj;
   }

   //returns true is this List and L are the same object sequence. Cursor is ignored.
   public boolean equals (List L) {
      int i;
      int j=0;
      boolean result = true;
      int temp = cursor;

      if (this.length() != L.length()) result = false;

      this.moveTo(0);
      L.moveTo(0);
      for (i=0; i<this.length(); i++) {

	if (!this.current.obj.equals(L.current.obj)) result = false;

	this.moveNext();
	L.moveNext();
      }
      this.current = this.first;
      cursor = temp;
      while(j < cursor) {
	current = current.next;
	j++;
      }
      return result;
   }

   //Resets this List to the empty state
   public void clear () {
      this.moveTo(this.length()-1);
      while (current != null) {
	current.next = null;
	current = current.prev;
      }
      first = null;
      cursor = -1;
      length = 0;
   }

   //Moves cursor to element at argument index if 0<=argument<=length-1. 
   //Otherwise  the cursor becomes undefined.
   public void moveTo (int i) {

      if (i < 0 || i > this.length()-1) {
	cursor = -1;
	return;
      }

      cursor = 0;
      current = first;
      while (cursor < i) {
	current = current.next;
	cursor++;
      }
   }

   //Moves cursor one step toward the front of List.
   //If getIndex() == 0 the cursor becomes undef.
   public void movePrev () {
      if (0 < this.getIndex() && this.getIndex() <= this.length()-1){
	current = current.prev;
	cursor--;
      } else if (this.getIndex() == 0) {
	cursor = -1;
      } else return;
   }

   //Moves cursor one step toward the back of List.
   //Equivalent to moveTo(getIndex()+1)
   public void moveNext () {
      if (0 <= this.getIndex() && this.getIndex() < this.length()-1){
        current = current.next;
        cursor++;
      } else if (this.getIndex() == this.length()-1) {
        cursor = -1;
      } else return;
   }
 
   //Insert new element before front element of List.
   public void prepend (Object x) {
      node newNode = new node();
      newNode.obj = x;

      if (isEmpty()) {
	first = newNode;
	last = newNode;
      } else {
	first.prev = newNode;
        newNode.next = first;
        first = newNode;
      }
      length++;
   }

   //Insert new element after back element in List
   public void append (Object x) {
      node newNode = new node();
      newNode.obj = x;

      if (isEmpty()) {
	first = newNode;
	last = newNode;
      } else {
	last.next = newNode;
        newNode.prev = last;
        last = newNode;
      }
      length++;
   }

   //Inserts new element before cursor element in List.
   //Pre; length>0 and getIndex >=0
   public void insertBefore (Object x) {

      if (this.length() <= 0 || this.getIndex() < 0) {
	System.out.println ("No node is present to insert before / cursor undefined!");
	throw new RuntimeException();
      }

      node newNode = new node();
      newNode.obj = x;
      newNode.prev = current.prev;
      newNode.next = current;
      if (current != first) current.prev.next = newNode;
      current.prev = newNode;
      if (current == first) first = newNode;
      cursor++;
      length++;
   }

   //Inserts new element before cursor element in List.
   //Pre; length>0 and getIndex >=0
   public void insertAfter (Object x) {

      if (this.length() <= 0 || this.getIndex() < 0) {
        System.out.println ("No node is present to insert after / cursor undefined!");
        throw new RuntimeException();
      }

      node newNode = new node();
      newNode.obj = x;
      newNode.prev = current;
      newNode.next = current.next;
      if (current != last) current.next.prev = newNode;
      current.next = newNode;
      if (current == last) last = newNode;
      length++;
   }
 
   //Deletes front element in List
   //Pre: length>0
   public void deleteFront () {
      
      if (this.length () <= 0) {
	System.out.println ("No node exists to delete!");
	throw new RuntimeException();
      }

      if (current == first) {
	current = current.next;
	cursor++;
      }
      first = first.next;
      first.prev = null;
      cursor--;
      length--;
   }

   //Deletes back element in List.
   //Pre: length>0
   public void deleteBack () {

      if (this.length () <= 0) {
        System.out.println ("No node exists to delete!");
        throw new RuntimeException();
      }

      if (current == last) {
	current = current.prev;
	cursor--;
      }
      last = last.prev;
      last.next = null;
      length--;
   }

   //Deletes cursor element in List. Cursor is undef. afterwards.
   //Pre: length>0, getIndex>=0
   public void delete () {
                               
      if (this.length() <= 0) {
	System.out.println ("No node exists to delete");
	throw new RuntimeException();
      }

      if (this.getIndex() < 0) {
	throw new RuntimeException("Cursor undefined!");
      }

      if (current == first){
	first = current.next;
	current = null;
      } else if (current == last){
        last = current.prev;
	current = null;
      } else {
	current.prev.next = current.next;
	current.next.prev = current.prev;
	current = null;
      }
      cursor = -1;
      length--;
   }

   //Creates a String consisting of a space separated sequence of integers
   //with front on the left and back on the right. Cursor is ignored.
   public String toString () {
      String List = "";
      moveTo(0);
      while (current != null) {
	List += (current.toString() + " ");
	current = current.next;
      }

      current = first;
      int i=0;
      while (i < cursor) {
	current = current.next;
	i++;
      }

      return List;
   }

}

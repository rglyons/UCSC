// Robert Lyons
// rglyons
// pa1
// cmps101
// 1-12-15
// List.java
// Implementation for List class

public class List {

   private class node {
      int item;
      node prev;
      node next;
      
      public node Node(int data) {
	node newNode = new node();
	newNode.item = data;
	newNode.next = null;
	newNode.prev = null;
	return newNode;
      }

      public String toString () {
	String data = String.valueOf(this.item);
	return data;
      }
   }

   private node first = null;
   private node current = null;  //cursor element
   private node last = null;
   private int cursor = -1;  //index of cursor element

   //Constructor
   public void List () {
      List theList = new List ();
   }

   //returns the number of elements in the list
   public int length() {
      int length = 0;
      int i = 0;
      current = first;
      if (first == last) length = 1;
      else {
        while (current != null) {
	  current = current.next;
	  length++;
        }
      }
      current = first;
      while (i < cursor){
        current = current.next;
	i++;
      }
      return length;
   }

   //returns index of cursor element
   public int getIndex() {
      return cursor;
   }

   //returns front element
   //Pre: length>0
   public int front () {
      if (isEmpty()) { 
	System.out.println ("Cannot display front element because the list is empty!");
	throw new RuntimeException();
      } else {
	return first.item;
      }
   }

   //returns back element
   //Pre: length>0
   public int back () {
      if (isEmpty()) {
        System.out.println("Cannot display back element because the list is empty!");
        throw new RuntimeException();
      } else {
        return last.item;
      }
   }

   //returns true if the list is empty
   public boolean isEmpty () {
      return first == null;
   }

   //returns cursor elements in this list
   //Pre: length>0, getIndex>=0
   public int getElement () {
 
     if (isEmpty()) {
	System.out.println("Cannot display the current element because the list is empty!");
	throw new RuntimeException();
      }
      if (getIndex() < 0) {
	System.out.println("Cursor undefined!");
	throw new RuntimeException();
      }

      return current.item;
   }

   //returns true is this List and L are the same integer sequence. Cursor is ignored.
   public boolean equals (List L) {
      int i;
      int j=0;
      boolean result = true;
      int temp = cursor;

      if (this.length() != L.length()) result = false;

      this.moveTo(0);
      L.moveTo(0);
      for (i=0; i<this.length(); i++) {

	if (this.current.item != L.current.item) result = false;

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
   public void prepend (int data) {
      node newNode = new node();
      newNode.item = data;

      if (isEmpty()) {
	first = newNode;
	last = newNode;
      } else {
	first.prev = newNode;
        newNode.next = first;
        first = newNode;
      }
   }

   //Insert new element after back element in List
   public void append (int data) {
      node newNode = new node();
      newNode.item = data;

      if (isEmpty()) {
	first = newNode;
	last = newNode;
      } else {
	last.next = newNode;
        newNode.prev = last;
        last = newNode;
      }
   }

   //Inserts new element before cursor element in List.
   //Pre; length>0 and getIndex >=0
   public void insertBefore (int data) {

      if (this.length() <= 0 || this.getIndex() < 0) {
	System.out.println ("No node is present to insert before / cursor undefined!");
	throw new RuntimeException();
      }

      node newNode = new node();
      newNode.item = data;
      newNode.prev = current.prev;
      newNode.next = current;
      if (current != first) current.prev.next = newNode;
      current.prev = newNode;
      if (current == first) first = newNode;
      cursor++;
   }

   //Inserts new element before cursor element in List.
   //Pre; length>0 and getIndex >=0
   public void insertAfter (int data) {

      if (this.length() <= 0 || this.getIndex() < 0) {
        System.out.println ("No node is present to insert after / cursor undefined!");
        throw new RuntimeException();
      }

      node newNode = new node();
      newNode.item = data;
      newNode.prev = current;
      newNode.next = current.next;
      if (current != last) current.next.prev = newNode;
      current.next = newNode;
      if (current == last) last = newNode;
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
   }

   //Deletes cursor element in List. Cursor is undef. afterwards.
   //Pre: length>0, getIndex>=0
   public void delete () {
                               
      if (this.length() <= 0 || this.getIndex() < 0) {
	System.out.println ("No node exists to delete / cursor undefined!");
	throw new RuntimeException();
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
   }

   //Creates a String consisting of a space separated sequence of integers
   //with front on the left and back on the right. Cursor is ignored.
   public String toString () {
      String List = String.valueOf(first.item);
      current = first.next;
      while (current != null) {
	List += (" " + current.toString());
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

   //Returns a new List representing the same integer sequence as this list.
   //Cursor in new list is undef.
   //This List is unchanged.
   public List copy () {
      List newList = new List();
      newList.append(first.item);
      current = first.next;
      while (current != null) {
	newList.append(current.item);
	current = current.next;
      }
      newList.cursor = -1;

      current = first;
      int i=0;
      while (i < cursor) {
        current = current.next;
        i++;
      }

      return newList;
   }

}

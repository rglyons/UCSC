// Robert Lyons
// rglyons
// cmps12b
// 12-7-14
// dllist.java
// Implementation for dllist class for cyoa game

import java.util.List;
import java.util.ArrayList;

public class dllist {

   public enum position {FIRST, PREVIOUS, FOLLOWING, LAST};

   public class node {
      String item;
      node next;
      String [] descriptions = new String[50];
      List<option> options = new ArrayList<option>();
   }
   
   public String [] history = new String [100];
   public int top = -1;
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
   }

   public void setRoom(String tag){
      current = first;

      while (current != null) {
        if (current.item.equals(tag)){
	  break;
	}
        current = current.next;
      }
   }

   public node getRoom() {
      return current;
   }

    // Push a String
    public void push(String x) {
      history[++top] = x;
    }

    // Pop top String (removes)
    public String pop() {
      String h = "";
      if (history.length >= 2){
        h =  history[top];
	history[top] = null;
	top--;
      } if (history.length < 2)
        System.out.println("No moves to undo!");
      return h;
   }

   public void clearHistory () {
      for (int i=0; i<history.length; i++)
	history[i] = null;
      top = -1;
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

   public void addToDescriptions (String item) {
      int i=0;
      while (current.descriptions[i] != null) i++;
      current.descriptions[i] = item;
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
        } else if (pos == position.FIRST){
	  newLink.next = first;
	  first = newLink;
	  current = first;
	  currentPosition = 0;
      }
      if (current == last && pos == position.FOLLOWING){
	newLink.next = null;
	last = newLink;
	current.next = newLink;
	current = last;
	currentPosition++;
      } else if (current != last && pos == position.FOLLOWING){
	newLink.next = current.next;
	current.next = newLink;
	current = newLink;
	currentPosition++;
      }
   }
}

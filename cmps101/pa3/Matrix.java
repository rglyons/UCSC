// Robert Lyons
// rglyons
// pa3
// cmps101
// 2-3-15
// Matrix.java
// Implementation of Matrix class

import java.io.*;
import java.lang.*;

public class Matrix {

  private class Entry {

    int column;
    double value;

    //Constructor
    Entry(int col, double val) {
      column = col;
      value = val;
    }

    public boolean equals (Object x) {
      
      if (!(x instanceof Entry)) return false;
      if (x == this) return true;
      Entry E = (Entry)x;

      if (this.column == E.column && this.value == E.value) return true;
      else return false;

    }

    public String toString () {

      return ("("+this.column+", "+this.value+")");

    }

  }

  //Fields
  private int size;
  private List[] rows;
  
  //Constructor
  Matrix(int n) {
    
    if (n<1) {
      System.out.println("Matrix() called with invalid size n");
      throw new RuntimeException();
    }

    size = n;
    rows = new List[n+1];
    for (int i=1; i<=n; i++) {
      rows[i] = new List();
    }

  }

  //Access Functions
  int getSize () {

    return size;

  }

  //returns number of non-zero elements in matrix
  int getNNZ () {

    int count = 0;
    int i = 1;

     while (i < this.rows.length) {
      if (!this.rows[i].isEmpty()) count += this.rows[i].length();  //add length of each row
      i++;
    }

    return count;

  }

  public boolean equals (Object x) {

    if (!(x instanceof Matrix)) return false;
    if (this == x) return true;

    Matrix M = (Matrix)x;
    if (this.getSize() != M.getSize()) return false; //different sizes?
    if (this.getNNZ() != M.getNNZ()) return false;  //different row lengths?
    
    for (int i=1; !this.rows[i].isEmpty(); i++) {
      if (!this.rows[i].equals(M.rows[i])) return false;  //different sequences?
    }

    return true;

  }

  //Manipuation Procedures

  //resets matrix to zero matrix
  void makeZero () {
    
    for(int i=1; !this.rows[i].isEmpty(); i++) {
      this.rows[i].clear();  //clear each row
    }

  }

  //creates a new identical matrix
  Matrix copy () {

    Matrix newMat = new Matrix(this.size);
    for (int i=1; !this.rows[i].isEmpty(); i++) {  //for each row

      this.rows[i].moveTo(0);                 //move to front of list (row)
      while (this.rows[i].getIndex() != -1) {  //while there's something there
        Entry E = (Entry)this.rows[i].getElement(); //cast Object as Entry
        E = new Entry(E.column, E.value);              //create new entry with same value/column   
        if (E.value != 0) newMat.rows[i].append(E);   //append it to corresponding newMat row
        this.rows[i].moveNext();                   //move through this row List
      }

    }

    return newMat;

  }

  //changes ith row, jth column of this Matrix to x
  //Pre:1<=i<=getSize(), 1<=j<=getSize()
  void changeEntry (int i, int j, double x) {

    if (i<1 || i>this.getSize() || j<1 || j>this.getSize()) {
      System.out.println("Matrix.changeEntry(): element ij is out of Matrix bounds.");
      throw new RuntimeException(); 
    }

    Entry E = new Entry(j, x);

    if (this.rows[i].isEmpty() && x != 0) {
      this.rows[i].append(E);
      return;
    } else if (this.rows[i].isEmpty() && x == 0) return;

    this.rows[i].moveTo(0);           
    Entry F = (Entry)this.rows[i].getElement();
    while (F.column != j) { //find entry at column j if it exists
      this.rows[i].moveNext();                //if it doesn't exist then empty = true
      if (this.rows[i].getIndex() == -1) break;
      F = (Entry)this.rows[i].getElement();
    }                                         
    boolean empty = (this.rows[i].getIndex() == -1);

    if (x == 0.0 && empty) {         //entry = 0, x = 0
      return;
    } else if (empty && x != 0.0) {  //entry = 0, x != 0
      this.rows[i].moveTo(0);
      F = (Entry)this.rows[i].getElement();
      while (F.column < j) { //move through row until you're at the first
	this.rows[i].moveNext(); //entry after column j
	if (this.rows[i].getIndex() == -1) break;
        F = (Entry)this.rows[i].getElement();
      }
      if (this.rows[i].getIndex() != -1) this.rows[i].insertBefore(E);
      else this.rows[i].append(E);
    } else if (!empty && x == 0.0) {  //entry != 0, x = 0 
      this.rows[i].delete();
    } else if (!empty && x != 0.0) {  //entry != 0, x != 0 
      this.rows[i].delete();
      if(!this.rows[i].isEmpty()) {
        this.rows[i].moveTo(0);
        F = (Entry)this.rows[i].getElement();
	while (F.column < j) {
	  this.rows[i].moveNext();
	  if (this.rows[i].getIndex() == -1) break;
	  F = (Entry)this.rows[i].getElement();
	}
        this.rows[i].insertBefore(E);
      } else this.rows[i].append(E);
    }

  }

  //returns a new matrix that is the scalar multiple of this matrix
  Matrix scalarMult (double x) {

   int j;
   int i = 1;
   Matrix newMat = new Matrix(this.size);
   while (i < this.rows.length) {
     
      j = 0;
      if(!this.rows[i].isEmpty()) {
        this.rows[i].moveTo(0);                     //move to front of list (row)
        while (j < this.rows[i].length()) {         //for this entire list
          Entry E = (Entry)this.rows[i].getElement();
          E = new Entry(E.column, x*(E.value));
          newMat.rows[i].append(E);                   //append its scalar multiple to corresponding newMat row
          this.rows[i].moveNext();                   //move through this row List
          j++;
        }
      }

      i++;
    }

    return newMat;

  }

  //returns a new matrix that is the sum of this and M
  //Pre: getSize() == M.getSize()
  Matrix add (Matrix M) {

    if (this.getSize() != M.getSize()) {
      System.out.println("Matrix.add: this matrix and M must be the same size.");
      throw new RuntimeException();
    }

    Matrix newMat = new Matrix(this.size);
    Entry E1, E2;
    int i = 1;

    while (i < this.rows.length) {

      if (!this.rows[i].isEmpty()) this.rows[i].moveTo(0);
      if (!M.rows[i].isEmpty()) M.rows[i].moveTo(0);
      while (this.rows[i].getIndex() != -1 || M.rows[i].getIndex() != -1) { 
     
	if (this.rows[i].getIndex() == -1 && M.rows[i].getIndex() != -1) {      //finish this row before M's row?
	  E2 = (Entry)M.rows[i].getElement();                                       //add the rest of M's row
          E2 = new Entry(E2.column, E2.value);
	  newMat.rows[i].append(E2);
	  M.rows[i].moveNext();
	} else if (this.rows[i].getIndex() != -1 && M.rows[i].getIndex() == -1) { //finish M's row before this row?
	  E1 = (Entry)this.rows[i].getElement();                                      //add the rest of this row     
	  E1 = new Entry(E1.column, E1.value);
	  newMat.rows[i].append(E1);
	  this.rows[i].moveNext();
	} else if (this.rows[i].getIndex() != -1 && M.rows[i].getIndex() != -1) { //this row and M's row unfinished?
	  E1 = (Entry)this.rows[i].getElement();
	  E2 = (Entry)M.rows[i].getElement();

	  if (E1.column == E2.column) {                                              //entries in the same column?
            Entry E3 = new Entry(E1.column, E1.value + E2.value);                        //add and append to newMat's row
            newMat.rows[i].append(E3);
            this.rows[i].moveNext();
	    if (this != M) M.rows[i].moveNext();
          } else if (E1.column < E2.column) {                                        //entry in this row in column before
            Entry E3 = new Entry(E1.column, E1.value);                                   //entry in M's row?
	    newMat.rows[i].append(E3);                                               //just append entry in this row to newMat's row
	    this.rows[i].moveNext();                                                
	  } else if (E1.column > E2.column) {                                        //entry in M's row in column before
	    Entry E3 = new Entry(E2.column, E2.value);                                   //entry in this row?
	    newMat.rows[i].append(E3);                                               //just append entry in M's row to newMat's row
	    M.rows[i].moveNext();
	  }

        } 
        
      } 
      i++;
    }

    return newMat;

  }

  //returns a new matrix that is the differentce of this and M
  //Pre: getSize() == M.getSize()
  Matrix sub (Matrix M) {

    if (this.getSize() != M.getSize()) {
      System.out.println("Matrix.sub: this matrix and M must be the same size.");
      throw new RuntimeException();
    }

    Matrix newMat = new Matrix(this.size);
    Entry E1, E2;
    int i = 1;

    while (i < this.rows.length) {

      if (!this.rows[i].isEmpty()) this.rows[i].moveTo(0);
      if (!M.rows[i].isEmpty()) M.rows[i].moveTo(0);
      while (this.rows[i].getIndex() != -1 || M.rows[i].getIndex() != -1) {  

        if (this.rows[i].getIndex() == -1 && M.rows[i].getIndex() != -1) { 
          E2 = (Entry)M.rows[i].getElement();                                 
          E2 = new Entry(E2.column, -(E2.value));
          newMat.rows[i].append(E2);
          M.rows[i].moveNext();
        } else if (this.rows[i].getIndex() != -1 && M.rows[i].getIndex() == -1) {           
	  E1 = (Entry)this.rows[i].getElement();        
          E1 = new Entry(E1.column, -(E1.value));
          newMat.rows[i].append(E1);
          this.rows[i].moveNext();
        } else if (this.rows[i].getIndex() != -1 && M.rows[i].getIndex() != -1) {                    
          E1 = (Entry)this.rows[i].getElement();
          E2 = (Entry)M.rows[i].getElement();

          if (E1.column == E2.column) {                                              
            Entry E3 = new Entry(E1.column, E1.value - E2.value);  
            if (E3.value != 0) newMat.rows[i].append(E3);
            this.rows[i].moveNext();
            if (this != M) M.rows[i].moveNext();
          } else if (E1.column < E2.column) {                                        
            Entry E3 = new Entry(E1.column, E1.value);              
            newMat.rows[i].append(E3);  
            this.rows[i].moveNext();
          } else if (E1.column > E2.column) {                                        
            Entry E3 = new Entry(E2.column, -(E2.value));            
            newMat.rows[i].append(E3);  
            M.rows[i].moveNext();
          }

        }

      }
      i++;
    }

    return newMat;

  }


  //returns a new Matrix that is the transpose of this Matrix
  Matrix transpose () {

    Matrix newMat = new Matrix(this.size);
    int j;
    int i = 1;
    Entry E;
    while (i < this.rows.length) {

      if (!this.rows[i].isEmpty()) {
        this.rows[i].moveTo(0);
        while (this.rows[i].getIndex() != -1) {
          E = (Entry)this.rows[i].getElement();
          j = E.column;
	  E = new Entry(i, E.value);
	  newMat.rows[j].append(E);
	  this.rows[i].moveNext();
        }
      }

      i++;
    }

    return newMat;

  }

  //returns the dot product of two Lists
  private static double dot(List P, List Q) {

    double dot = 0;
    if (!P.isEmpty() && !Q.isEmpty()) {
      Entry E1, E2;
      P.moveTo(0);
      Q.moveTo(0);

      while (P.getIndex() != -1 && Q.getIndex() != -1) {
        E1 = (Entry)P.getElement();
        E2 = (Entry)Q.getElement();
        if (E1.column == E2.column) {
	  dot += E1.value * E2.value;
	  P.moveNext();
	  Q.moveNext();
        } else if (E1.column < E2.column) { //multiply E1.value by 0, add 0
	  P.moveNext();
        } else if (E2.column < E1.column) { //multiply E2.value by 0, add 0
	  Q.moveNext();
        }
      }

    }

    return dot;

  }

  //returns a new matrix that is the product of this matrix and M
  //Pre: getSize() == M.getSize
  Matrix mult(Matrix M) {

    if (this.getSize() != M.getSize()) {
      System.out.println("Matrix.mult(): this matrix and M are not the same size.");
      throw new RuntimeException();
    }

    Matrix newMat = new Matrix(this.size);
    Entry E;
    double dotP;
    
    Matrix transM = M.transpose(); //easier to take dot product
    for (int i=1; i < this.rows.length; i++) {
      for (int j=1; j < transM.rows.length; j++) {
	dotP = dot(this.rows[i], transM.rows[j]);
	if (dotP != 0) {
	  E = new Entry(j, dotP);
	  newMat.rows[i].append(E);
	}
      }
    }

    return newMat;
  
  }

  //overrides Object's toString function
  public String toString() {

    int i = 1;
    String MTX = "";

    while (i < this.rows.length) {

      if (!this.rows[i].isEmpty()) MTX += (i+": "+this.rows[i].toString()+"\n");
      i++;

    }
    return MTX;
  }

}


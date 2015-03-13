//Robert Lyons
//rglyons
//cmps12b
//11/20/14
//Tree.java
//Implementation of a Binary Tree that sorts words from a text file alphabetically

import static java.lang.System.*;
import java.util.NoSuchElementException;
import java.util.Iterator;

class Tree {

    private class Node {
        String key;
        Queue<Integer> value;
        Node left;
        Node right;
    }
    private Node root;

    private void printTheQueue (Node tree){
	String output = "";
	Iterator<Integer> LinenumIterator = tree.value.iterator();
	while (true)
	  try {
	    String follows = Integer.toString(LinenumIterator.next());
	    output = output + " " + follows;
	  } catch (NoSuchElementException e) {
	    break;
	  }
	System.out.print( tree.key+" : "+output);
	System.out.println();
    }

    private void debugHelper(Node tree, int depth) {
        // Your code here might be recursive
	String padding = "";
        for (int i=0; i<2*depth; i++) padding = padding + " ";
	if (tree == null) return;
	else {
	  debugHelper(tree.left, depth+1);
	  System.out.println(padding+depth+" "+tree.key);
	  debugHelper(tree.right, depth+1);
	}
    }

    private void outputHelper(Node tree) {
        // Your code here might be recursive
	if (tree.left != null) outputHelper(tree.left);
	printTheQueue(tree);
	if (tree.right != null) outputHelper(tree.right);
    }

    public void insert(String key, Integer linenum) {
        // Insert a word into the tree
        boolean inserted = false;
	if (root == null){
	  Node b = new Node();
	  b.key = key;
	  b.left = null;
	  b.right = null;
	  b.value = new Queue<Integer>();
	  b.value.insert(linenum);
	  root = b;
	  return;
	} 
	Node curr = root;
	while (inserted == false){
	  if (key.compareTo(curr.key) < 0 && curr.left == null){
	    Node a = new Node();
            a.key = key;
            a.left = null;
            a.right = null;
	    a.value = new Queue<Integer>();
	    a.value.insert(linenum);
	    curr.left = a;
	    curr = a;
	    inserted = true;
	  } else if (key.compareTo(curr.key) < 0 && curr.left != null){
	    curr = curr.left;
	  } else if (key.compareTo(curr.key) > 0 && curr.right == null){
	    Node a = new Node();
            a.key = key;
            a.left = null;
            a.right = null;
	    a.value = new Queue<Integer>();
	    a.value.insert(linenum);
	    curr.right = a;
	    curr = a;
	    inserted = true;
	  } else if (key.compareTo(curr.key) > 0 && curr.right != null){
	    curr = curr.right;
	  } else if (key.compareTo(curr.key) == 0){
	    key = curr.key;
	    curr.value.insert(linenum);
	    inserted = true;
	  }  
	}
    }

    public void debug() {
        // Show debug output of tree
        debugHelper(root, 0);
    }

    public void output() {
        // Show sorted words with lines where each word appears
        outputHelper(root);
    }

}

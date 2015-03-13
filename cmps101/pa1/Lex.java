// Robert Lyons
// rglyons
// pa1
// cmps101
// 1-15-15
// Lex.java

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.FileNotFoundException;

public class Lex {

   public static void main(String [] args) throws IOException {
      
      String read, write, min, temp, s;
      String prevMin = "";
      String max = "";
      int index = 0;
      int n = 0;
      int i, j, k, minCount;
      
      //check for corrct usage
      if (args.length != 2) {
	System.out.println("Usage: Lex [read file] [write file]");
	System.exit(1);
      }
      read = args[0];
      write = args[1];

      //count lines in read file & build array; create theList
      BufferedReader count = new BufferedReader(new FileReader(read));
      while (count.readLine() != null) n++;
      String [] lines = new String[n];
      List theList = new List();

      //read lines into array
      try {
        BufferedReader in = new BufferedReader(new FileReader(read)); 
        while ((s = in.readLine()) != null) {
	  lines[index] = s;
	  index++;
        }
      } catch (FileNotFoundException e) {
	System.out.println ("Read file does not exist!");
	System.exit(1);
      }

      //sort array & build theList
      min = lines[0];
      minCount = 0;

      for (k = 0; k<lines.length; k++) {                      //find max element to reset min to 
	if (lines[k].compareTo(max) > 0) max = lines[k];      //after each outer iteration
      }

      for (i=0; i<lines.length; i++) {
	for (j=0; j<lines.length; j++) {
	  if (lines[j].compareTo(min) <= 0 && lines[j].compareTo(prevMin) > 0) { //Is this element alphabetically
	    min = lines[j];                                                      //before all elements that haven't
	    minCount = j;                                                        //already been added to theList? If so,
	  }                                                                      //make it the new current min.
	}
	prevMin = min; //record the current min as already added to theList
	min = max;
	theList.append(minCount);
      }

      //write to new file
      theList.moveTo(0);
      PrintWriter file = new PrintWriter(write);
      for (int x = 0; x<lines.length; x++) {
        file.println(lines[theList.getElement()]);
	theList.moveNext();
      }
      file.close();
   }
}

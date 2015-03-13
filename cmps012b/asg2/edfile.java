// Robert Lyons, Kenta Cole
// rglyons, kncole
// cmps12b
// 11-5-14
// edfile.java
// Main application for text editor program

import java.util.Scanner;
import static java.lang.System.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

class edfile{

   public static void main (String[] args) throws IOException {
      boolean want_echo = true;
      String filename = "";
      String option = "-e";
      int lineDifference = 0; 
      int temp;
      dllist lines = new dllist();
      Scanner stdin = new Scanner (in);
      System.out.println ("Welcome to edfile, your line text editor.");
      if (args.length == 0){
	want_echo = false;
	option = "";
	filename = "";
      } else if (args[0] != null && args[0].compareTo("-e") != 0){
	want_echo = false;
	option = "";
	filename = args[0];
      } else if ((args[0] != null && args[0].compareTo("-e") == 0) && args.length == 1){
	filename = "";
      } else if ((args[0] != null && args[0].compareTo("-e") == 0) && args[1] != null){
	filename = args[1];
      } 
      if (filename.compareTo("") != 0)
	readInLines(lines, filename);	
      for (;;) {
         if (! stdin.hasNextLine()) break;
         String inputline = stdin.nextLine();
         if (want_echo) out.printf ("%s%n", inputline);
         if (inputline.matches ("^\\s*$")) continue;
         char command = inputline.charAt(0);
         switch (command) {
            case '#': break;
            case '$': lines.setPosition(dllist.position.LAST);
		      System.out.println(lines.getItem());
		      break;
            case '*': lines.setPosition(dllist.position.LAST);
		      int max = lines.getPosition();
		      int i = 0;
		      lines.setPosition(dllist.position.FIRST);
		      while (i <= max){
			System.out.println(lines.getItem());
			lines.setPosition(dllist.position.FOLLOWING);
			i++;
		      }
		      lines.setPosition(dllist.position.LAST);
		      break;
            case '.': System.out.println(lines.getItem());
		      break;
            case '0': lines.setPosition(dllist.position.FIRST);
		      System.out.println(lines.getItem());
		      break;
            case '<': temp = lines.getPosition(); 
		      lines.setPosition(dllist.position.PREVIOUS);
		      if (temp != lines.getPosition()){
		        System.out.println(lines.getItem());
		        break;
		      } else  break;
            case '>': temp = lines.getPosition();
		      lines.setPosition(dllist.position.FOLLOWING);
		      if(temp != lines.getPosition()){
		        System.out.println(lines.getItem());
		        break;
		      } else break;
            case 'a': lines.insert(inputline.substring(1), dllist.position.FOLLOWING);
		      System.out.println(lines.getItem());
		      break;
            case 'd': lines.delete();
		      break;
            case 'i': lines.insert(inputline.substring(1), dllist.position.PREVIOUS);
                      System.out.println(lines.getItem()); 
		      break;
            case 'r': boolean emptyAtFirst = lines.isEmpty();
		      int oldCount = 0;
		      if (!emptyAtFirst){
			 lines.setPosition(dllist.position.LAST);
		         oldCount = lines.getPosition();
		      }
		      try {
			readInLines(lines, inputline.substring(1));
			if(emptyAtFirst) lineDifference = lines.getPosition() + 1;
			else lineDifference = lines.getPosition() - oldCount;
		        System.out.println("Number of lines added: "+lineDifference);
		      } catch (FileNotFoundException e) {
			System.out.println("The file could not be found.");
			break;
		      }
		      break;
            case 'w': String writeToFile = inputline.substring(1);
		      PrintWriter file = new PrintWriter(writeToFile);
		      int j = 0;
		      int max2 = 0;
		      int lineDifference2 = 0;
		      if (lines.isEmpty()){
			System.out.println("No lines to write.");
			break;
		      } else {
			lines.setPosition(dllist.position.LAST);
			max2 = lines.getPosition();
			lines.setPosition(dllist.position.FIRST);
                      while (j <= max2){
                        file.println(lines.getItem());
                        lines.setPosition(dllist.position.FOLLOWING);
                        j++;
                      }
		      file.close();
                      lines.setPosition(dllist.position.LAST);
		      lineDifference2 = lines.getPosition() + 1;
		      System.out.println("Number of lines written to "+writeToFile+": "+lineDifference2);
		      break;
		      }
            default : System.out.println("Invalid command");
		      break;
         }
      }
   }

      public static void readInLines(dllist a, String x) throws IOException {
	BufferedReader operand = new BufferedReader(new FileReader(x));  
	String s;
	while((s = operand.readLine()) != null){
	  a.insert(s, dllist.position.LAST);
	}
	operand.close();
      }    
}

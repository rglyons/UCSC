//Robert Lyons
//rglyons
//cmps012m
//10/16/14
//greetings.java
//Greet everyone listed in people.txt

import static java.lang.System.*; 
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;
class greetings {
  public static void main( String[] args ) throws IOException {
  	BufferedReader in = new BufferedReader(new FileReader("directory.txt"));
 	 while(true) {
    		String name = in.readLine();
		if (name == null) break;
		String [] matches = name.split(",");
    		System.out.println("Hello, " + matches[0] + ".");
  	}
  	in.close();
  }
}

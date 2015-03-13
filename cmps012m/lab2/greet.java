// Robert Lyons
// rglyons
// 10/16/14
//  cmps012m
// greet.java
// Asks user to type their name, then says hello.

import static java.lang.System.*; 
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
class greet {
  public static void main( String[] args ) {
    BufferedReader in = new BufferedReader(
    new InputStreamReader(System.in));
    try {
      while(true){
        System.out.println("What is your name?");
        String name = in.readLine();
        System.out.println("Hello, " + name + ".");
      }
    }  catch(IOException io) {
         io.printStackTrace();
     }
   }
}

/*
 * HelloWorld4
*/

import java.util.Scanner;

class HelloWorld4{

  public static void main(String[] args){

    Scanner sc = new Scanner(System.in);
    String sentence1 = "Hello, World!";
    String sentence2 = "My name is ";
    String name;

    System.out.print("Please type your first name: ");
    name = sc.next();
    System.out.println(sentence1);
    System.out.print(sentence2);
    System.out.println(name);

  }
} 

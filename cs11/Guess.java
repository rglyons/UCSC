/*
 * Guess.java
 * Robert Lyons
 * rglyons
 * pa2
 * Plays an interactive guessing number game with the user.
*/

import java.util.Scanner;

class Guess {
  public static void main(String[] args){

    Scanner sc = new Scanner(System.in);
    int number = (int)(10.0 * Math.random()) + 1;
    int guess1, guess2, guess3;

    System.out.println();
    System.out.println("I'm thinking of an integer in the range 1 to 10. You have three guesses.");
    System.out.println();
  
    System.out.print("Enter your first guess: ");
    guess1 = sc.nextInt();
    if(guess1==number) {
      System.out.println("You win!");
      System.out.println();
      System.exit(0);
    }else if(guess1>number) {
      System.out.println("Your guess is too high.");
      System.out.println();
    }else if(guess1<number) {
      System.out.println("Your guess is too low.");
      System.out.println();
    }

    System.out.print("Enter your second guess: ");
    guess2 = sc.nextInt();
    if(guess2==number) {
      System.out.println("You win!");
      System.out.println();
      System.exit(0);
    }else if(guess2>number) {
      System.out.println("Your guess is too high.");
      System.out.println();
    }else if(guess2<number) {
      System.out.println("Your guess is too low.");
      System.out.println();
    }

    System.out.print("Enter your third guess: ");
    guess3 = sc.nextInt();
    if(guess3==number) {
      System.out.println("You win!");
      System.out.println();
      System.exit(0);
    }else if(guess3>number) {
      System.out.println("Your guess is too high.");
      System.out.println();
      System.out.println("You lose. The number was "+number+".");
      System.out.println();
    }else if(guess3<number) {
      System.out.println("Your guess is too low.");
      System.out.println();
      System.out.println("You lose. The number was "+number+".");
      System.out.println();
    }
  }
}

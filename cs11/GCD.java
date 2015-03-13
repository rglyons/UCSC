/* GCD.java
 * Robert Lyons
 * rglyons
 * pa3
 * Program finds the greatest common divisor of two integers.
*/
import java.util.Scanner;

class GCD{
  public static void main(String[] args) {
    int x, y, a, b, r;
    int temp = 0;
    Scanner sc = new Scanner(System.in);

    System.out.print("Enter a positive integer: ");
    while(!sc.hasNextInt()){
      sc.next();
      System.out.print("Please enter a positive integer: ");
    }
    x = sc.nextInt();
    if (x<=0){
      System.out.print("Please enter a positive integer: ");
      x = sc.nextInt();
    }
    a = x; 



    System.out.print("Enter another positive integer: ");
    while(!sc.hasNextInt()){
      sc.next();
      System.out.print("Please enter a positive integer: ");
    }
    y = sc.nextInt();
    if (y<=0){
      System.out.print("Please enter a positive integer: ");
      y = sc.nextInt();
    }
    b = y;



    if (a<b) {
      temp = a;
      a = b;
      b = temp;
    }


    r = a%b;
    while (r>0){
      a = b;
      b = r;
      r = a%b;
    }
    System.out.println("The GCD of "+x+" and "+y+" is "+b);
  }
}

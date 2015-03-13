// Robert Lyons
// rglyons
// pa3
// 2-6-15
// Spase.java

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.lang.Integer;
import java.lang.Double;

public class Sparse {

   public static void main(String [] args) throws IOException {

      String read, write;

      //check for corrct usage
      if (args.length != 2) {
        System.out.println("Usage: Sparse [read file] [write file]");
        System.exit(1);
      }
      read = args[0];
      write = args[1];

      //read in file, build matrices
      BufferedReader in = new BufferedReader(new FileReader(read));
      String[] firstLine = in.readLine().split(" ");
      int size = Integer.parseInt(firstLine[0]);
      Matrix A = new Matrix(size);
      Matrix B = new Matrix(size);

      //fill in matrix A
      in.readLine();                                        //read over blank line
      int i = 0;
      while(i < Integer.parseInt(firstLine[1])) {
	String[] thisLine = in.readLine().split(" ");
	A.changeEntry(Integer.parseInt(thisLine[0]), 
			Integer.parseInt(thisLine[1]), Double.parseDouble(thisLine[2]));
        i++;
      }

      //fill in matrix B
      in.readLine();                       //read over blank line 
      int j = 0;
      while(j < Integer.parseInt(firstLine[2])) {
        String[] thisLine = in.readLine().split(" ");
        B.changeEntry(Integer.parseInt(thisLine[0]), 
			Integer.parseInt(thisLine[1]), Double.parseDouble(thisLine[2]));
        j++;
      }
      
      PrintWriter file = new PrintWriter(write);       //open new file for printing results to

      //print A
      file.println("A has "+A.getNNZ()+" non-zero entries: ");
      file.println(A);

      //print B
      file.println("B has "+B.getNNZ()+" non-zero entries: ");
      file.println(B);

      //print 1.5*A
      file.println("(1.5)*A =");
      Matrix C = A.scalarMult(1.5);
      file.println(C);

      //print A+B
      file.println("A+B =");
      Matrix D = A.add(B);
      file.println(D);

      //print A+A
      file.println("A+A =");
      Matrix E = A.add(A);
      file.println(E);

      //print B-A
      file.println("B-A =");
      Matrix F = B.sub(A);
      file.println(F);

      //print A-A
      file.println("A-A =");
      Matrix G = A.sub(A);
      file.println(G);

      //print A transpose
      file.println("Transpose (A) =");
      Matrix H = A.transpose();
      file.println(H);

      //print A*B
      file.println("A*B =");
      Matrix I = A.mult(B);
      file.println(I);

      //print B*B
      file.println("B*B =");
      Matrix J = B.mult(B);
      file.println(J);

      //close PrintWriter
      file.close();
   }
}

//-----------------------------------------------------------------------------
//  ComplexTest.java
//  Test the Complex class
//
//  Submit this file with pa6.  Do not alter it in any way.  Use the 
//  accompanying Makefile to create the executable Jar file ComplexTest.  
//  Run the program by doing
//
//  % ComplexTest input_file output_file
//
//  An input file consists of 10 lines of text, with one complex number on 
//  each line.  The output file has the very same format, so you can use your 
//  output as input on another run.
//
//  Use the input file file1 to create the sequence file2, file3, file4, file5.
//  Each file in this sequence is the output of the previous file.  You can 
//  therefore test your Complex class by doing the following commands:
//
//  % make
//  % ComplexTest file1 f2
//  % ComplexTest f2 f3
//  % ComplexTest f3 f4
//  % ComplexTest f4 f5
//  
//  If your sequence f2, f3, f4, f5 is identical to my sequence file2, file3
//  file4, file5, then you pass the test.  You can check this using the Unix
//  command diff.  For instance, if you do
//
//  % diff file5 f5
//
//  and get no output, then there is no difference between files file5 and f5.
//  
//-----------------------------------------------------------------------------
import java.util.Scanner;
import java.io.*;

class ComplexTest{
   
   public static void main(String[] args) throws IOException{
      Scanner in  = new Scanner(new File(args[0]));
      PrintWriter out = new PrintWriter(new FileWriter(args[1]));
      Complex[] C = new Complex[10];
      int i = 0;
      
      // read 10 complex numbers from file
      while(in.hasNextLine()){
         C[i] = new Complex(in.nextLine());
         i++;
      }
      
      // perform some arithmetic operations
      C[0] = C[0].add(C[1]);
      C[2] = C[2].sub(C[3]);
      C[3] = C[3].mult(Complex.I);
      C[4] = C[4].mult(C[5]);
      C[6] = C[6].conj().negate();
      C[7] = ( C[7].equals(Complex.ZERO) ? C[7].add(Complex.I) : C[7].recip() );
      C[8] = ( C[9].equals(Complex.ZERO) ? C[8].div(Complex.valueOf("1+i")) : C[8].div(C[9]) );
      C[9] = Complex.valueOf(C[9].abs(),C[0].arg());
      C[5] = Complex.valueOf(C[5].Im()).add(Complex.valueOf(C[5].Re()).mult(Complex.I));
      
      // write 10 complex numbers to file
      for(i=0; i<10; i++){
         out.println(C[i]);
      }
      
      // close files
      in.close();
      out.close();
   
   }

}
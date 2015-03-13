//-----------------------------------------------------------------------------
////  MatrixTest.java
////  A test client for the Matrix ADT
//
//
//
//Robert Lyons
//rglyons
//pa3
//2-3-15
////-----------------------------------------------------------------------------


public class MatrixTest{
   public static void main(String[] args){
      int i, j, n=35000;
      Matrix A = new Matrix(n);
      Matrix B = new Matrix(n);
      Matrix C = new Matrix(n);
      Matrix D = new Matrix(n);
      List theList = new List();

      A.changeEntry(1,1,1); B.changeEntry(1,1,1);
      A.changeEntry(1,2,2); B.changeEntry(1,2,0);
      A.changeEntry(1,3,3); B.changeEntry(1,3,1);
      A.changeEntry(2,1,4); B.changeEntry(2,1,0);
      A.changeEntry(2,2,5); B.changeEntry(2,2,1);
      A.changeEntry(2,3,6); B.changeEntry(2,3,0);
      A.changeEntry(3,1,7); B.changeEntry(3,1,1);
      A.changeEntry(3,2,8); B.changeEntry(3,2,1);
      A.changeEntry(3,3,9); B.changeEntry(3,3,1);    

      C.changeEntry(1,1,-1); D.changeEntry(18,1,1);
      C.changeEntry(1,2,-2); D.changeEntry(19,2,1);
      C.changeEntry(1,3,-3); 
      C.changeEntry(2,1,-4); D.changeEntry(25,1,1);
      C.changeEntry(2,2,-5); D.changeEntry(29,2,1);
      C.changeEntry(2,3,-6); 
      C.changeEntry(3,1,-7); 
      C.changeEntry(3,2,-8); D.changeEntry(327,2,1);
      C.changeEntry(3,3,-9); D.changeEntry(32287,3,1);
 
                             D.changeEntry(18,1,5.6);
                             D.changeEntry(19,2,2.7);
                             D.changeEntry(25,1,9.1);
                             D.changeEntry(29,2,4.3);
                             D.changeEntry(327,2,2.0);
                             D.changeEntry(32287,3,7.4);

//--------------------------------A & B-----------------------------------------------
      System.out.println("*************************A & B**************************");

      System.out.println("A: "+A.getNNZ());
      System.out.println(A);

      System.out.println("B: "+B.getNNZ());
      System.out.println(B);

      Matrix E = A.scalarMult(1.5);
      System.out.println("1.5*A: "+E.getNNZ());
      System.out.println(E);

      Matrix F = A.add(A);
      System.out.println("A+A: "+F.getNNZ());
      System.out.println(F);

      Matrix G = A.sub(A);
      System.out.println("A-A: "+G.getNNZ());
      System.out.println(G);

      Matrix H = B.transpose();
      System.out.println("B Transpose: "+H.getNNZ());
      System.out.println(H);

      Matrix I = B.mult(B);
      System.out.println("B*B: "+I.getNNZ());
      System.out.println(I);

      Matrix J = A.copy();
      System.out.println("H (A.copy): "+J.getNNZ());
      System.out.println(J);
      System.out.println(A.equals(J));
      System.out.println(A.equals(B));
      System.out.println(A.equals(A));
      //Matrix.equals() special case
      System.out.println(A.equals(theList));

      A.makeZero();
      System.out.println("A.makeZero: "+A.getNNZ());
      System.out.println(A);
//------------------------------------------C & D-----------------------------------------
      System.out.println("******************************C & D***************************");

      System.out.println("C: "+C.getNNZ());
      System.out.println(C);

      System.out.println("D: "+D.getNNZ());
      System.out.println(D);

      Matrix K = C.scalarMult(1.5);
      System.out.println("1.5*C: "+K.getNNZ());
      System.out.println(K);

      Matrix L = D.scalarMult(1.5);
      System.out.println("1.5*D: "+L.getNNZ());
      System.out.println(L);

      Matrix M = C.add(D);
      System.out.println("C+D: "+M.getNNZ());
      System.out.println(M);

      Matrix N = D.sub(C);
      System.out.println("D-C: "+N.getNNZ());
      System.out.println(N);

      Matrix O = D.transpose();
      System.out.println("D Transpose: "+O.getNNZ());
      System.out.println(O);

      Matrix P = C.mult(D);
      System.out.println("C*D: "+P.getNNZ());
      System.out.println(P);

   }
}

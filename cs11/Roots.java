import java.util.Scanner;

class Roots {	
  public static void main(String[] args){
	  Scanner sc = new Scanner(System.in);
	  double resolution = 0.01;
	  double tolerance = 0.0000001;
	  double threshold = 0.001;
	  int n;
	  double o, p, l, r, t;
	  boolean roots = true;
      
	  System.out.print("Enter the degree: ");
	  n = sc.nextInt();
	  double [] C = new double [n+1];
	  System.out.print("Enter " + (n+1) + " coefficients: ");
	  for (int i=0; i<C.length; i++){
		  C[i] = sc.nextDouble();
	  }
	  System.out.print("Enter the left and right endpoints: ");
	  l = sc.nextDouble();
	  r = sc.nextDouble();
	  t = r-l;
	  System.out.println();
	  
	  double [] D = diff(C);
			  
	  for (double i=0; i<=t; i+=resolution){ 
	      if(poly(C,(l+i))*poly(C,(l+i+resolution))<0){
		      o = findRoot(C, l+i, l+i+resolution, tolerance);
		      System.out.print("Root found at: ");
		      System.out.printf("%.5f%n", o);
		      roots = true;
	      }
	      if(poly(D,(l+i))*poly(D,(l+i+resolution))<0){
	    	  p = findRoot(D, l+i, l+i+resolution, tolerance);
	    	  if((Math.abs(poly(C,p))<threshold)){
	    		  System.out.print("Root found at: ");
	    	  	  System.out.printf("%.5f%n", p);
	    	  	  roots = true;
	    	  }
	      }
	  }
	  if(!roots){
    	  System.out.println("There are no roots in the specified range for this polynomial function.");
      }
	}
	  	  	  
      static double poly(double [] C, double x){
    	  int i;
    	  double k = 0;
    	  for (i=0; i<C.length; i++){
    		  k += C[i]*(Math.pow(x, i));
    	  }
    	  return k;
      }
      
      static double[] diff(double[] C){
    	  int i;
    	  double [] D = new double [C.length-1];
    	  for (i=0; i<C.length-1; i++){
    		  D[i] = (i+1)*C[i+1];
    	  }
    	  return D;
      }
    
      static double findRoot(double[] C, double a, double b, double tolerance){
    	  double root=0, residual;
    	  while (b - a > tolerance){
    		  root = (a + b) / 2.0;
    		  residual = poly(C, root);
    	      if (poly(C,a)*residual < 0)
    		      b = root;
    	      else
    		      a = root;
    	  }
    	  return root;
      }
 }

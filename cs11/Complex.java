	//-----------------------------------------------------------------------------
	//   Complex.java
	//   Robert Lyons
	//   rglyons
	//   pa6
	//   Represents complex numbers as a pair of doubles
	//-----------------------------------------------------------------------------

	class Complex{

	   //--------------------------------------------------------------------------
	   // Private Data Fields 
	   //--------------------------------------------------------------------------
	   private double re;
	   private double im;
	   
	   //--------------------------------------------------------------------------
	   // Public Constant Fields 
	   //--------------------------------------------------------------------------
	   public static final Complex ONE = Complex.valueOf(1,0);
	   public static final Complex ZERO = Complex.valueOf(0,0);
	   public static final Complex I = Complex.valueOf(0,1);

	   //--------------------------------------------------------------------------
	   // Constructors 
	   //--------------------------------------------------------------------------
	   Complex(double a, double b){
	      this.re = a;
	      this.im = b;
	   }

	   Complex(double a){
	      this.re = a;
	      this.im = 0;
	   }

	   Complex(String s){
		  double [] make = parseComplex(s);
		  this.re = make[0];
		  this.im = make[1];
	   }

	   //---------------------------------------------------------------------------
	   // Public methods 
	   //---------------------------------------------------------------------------

	   // Complex arithmetic -------------------------------------------------------

	   // copy()
	   // Return a new Complex equal to this Complex
	   Complex copy(){
	      double re = this.re;
	      double im = this.im;
	      return new Complex(re, im);
	   }
	   
	   // add()
	   // Return a new Complex representing the sum this plus z.
	   Complex add(Complex z){
	      re = this.re + z.re;
	      im = this.im + z.im;
	      return new Complex(re, im);
	   }
	   
	   // negate()
	   // Return a new Complex representing the negative of this.
	   Complex negate(){
	      re = this.re*-1;
	      im = this.im*-1;
	      return new Complex(re, im);
	   }

	   // sub()
	   // Return a new Complex representing the difference this minus z.
	   Complex sub(Complex z){
		   re = this.re - z.re;
		   im = this.im - z.im;
		   return new Complex(re, im);
	   }

	   // mult()
	   // Return a new Complex representing the product this times z.
	   Complex mult(Complex z){
	      double re = ((this.re*z.re)-(this.im*z.im));
	      double im = ((this.re*z.im)+(this.im*z.re));
	      return new Complex(re, im);
	   }

	   // recip()
	   // Return a new Complex representing the reciprocal of this.
	   // Throw an ArithmeticException with appropriate message if 
	   // this.equals(Complex.ZERO).
	   Complex recip(){
		  if(this.equals(Complex.ZERO)){ 
			 throw new ArithmeticException("Cannot take reciprocal of zero.");
		  }
	      double re = ((this.re)/(Math.pow(this.re,2)+Math.pow(this.im, 2)));
	      double im = ((this.im*-1)/(Math.pow(this.re, 2)+Math.pow(this.im,2)));
	      return new Complex(re, im);
	   }

	   // div()
	   // Return a new Complex representing the quotient of this by z.
	   // Throw an ArithmeticException with appropriate message if 
	   // z.equals(Complex.ZERO).
	   Complex div(Complex z){
		   if(z.equals(Complex.ZERO)){
		    	  throw new ArithmeticException("Cannot divide by zero");
		      }
	      return this.mult(z.recip());
	   }

	   // conj()
	   // Return a new Complex representing the conjugate of this Complex.
	   Complex conj(){
	      double re = this.re;
	      double im = this.im*-1;
	      return new Complex(re, im);
	   }
	   
	   // Re()
	   // Return the real part of this.
	   double Re(){
	      return re;
	   }

	   // Im()
	   // Return the imaginary part of this.
	   double Im(){
	      return im;
	   }

	   // abs()
	   // Return the modulus of this Complex, i.e. the distance between 
	   // points (0, 0) and (re, im).
	   double abs(){
	      double z = Math.sqrt(Math.pow(this.re, 2)+Math.pow(this.im, 2));
	      return z;
	   }

	   // arg()
	   // Return the argument of this Complex, i.e. the angle this Complex
	   // makes with positive real axis.
	   double arg(){
	      return Math.atan2(im, re);
	   }

	   // Other functions ---------------------------------------------------------
	   
	   // toString()
	   // Return a String representation of this Complex.
	   // The String returned will be readable by the constructor Complex(String s)
	   public String toString(){
		  String complex = "";
		  String real, imag;
		  real = String.valueOf(this.re);
		  imag = String.valueOf(this.im);
		  if(this.re==0 && this.im==0){
			  complex = ("0");
		  }
		  if(this.re==0){
			  complex += (imag+"i");
		  }else if(this.re>0 || this.re<0){
			  complex += (real);
		  }
		  if(this.re>0 && this.im>0 || this.re<0 && this.im>0){
			  complex += (" + "+imag+"i");
		  }else if(this.re>0 && this.im<0 || this.re<0 && this.im<0){
			  complex += (" - "+String.valueOf(Math.abs(this.im))+"i");
		  }else if(this.im==0){
			  complex += "";
		  }
		  return complex;
	   }

	   // equals()
	   // Return true iff this and obj have the same real and imaginary parts.
	   public boolean equals(Object obj){
	      boolean eq;
	      Complex temp = (Complex)obj;
	      if(this.re==temp.re && this.im==temp.im){
	    	  eq = true;
	      } else eq = false;
	      return eq;
	   }

	   // valueOf()
	   // Return a new Complex with real part a and imaginary part b.
	   static Complex valueOf(double a, double b){
	      double re = a;
	      double im = b;
	      return new Complex(re, im);
	   }

	   // valueOf()
	   // Return a new Complex with real part a and imaginary part 0.
	   static Complex valueOf(double a){
	      double re = a;
	      double im = 0;
	      return new Complex(re, im);
	   }

	   // valueOf()
	   // Return a new Complex constructed from s.
	   static Complex valueOf(String s){
	      return new Complex(s);
	   }
	   
	   static private double[] parseComplex(String str){
		      double[] part = new double[2];
		      String s = str.trim();
		      String NUM = "(\\d+\\.\\d*|\\.?\\d+)";
		      String SGN = "[+-]?";
		      String OP =  "\\s*[+-]\\s*";
		      String I =   "i";
		      String OR =  "|";
		      String REAL = SGN+NUM;
		      String IMAG = SGN+NUM+"?"+I;
		      String COMP = REAL+OR+
		                    IMAG+OR+
		                    REAL+OP+NUM+"?"+I;
		      
		      if( !s.matches(COMP) ){
		         throw new NumberFormatException(
		                   "Cannot parse input string \""+s+"\" as Complex");
		      }
		      s = s.replaceAll("\\s","");     
		      if( s.matches(REAL) ){
		         part[0] = Double.parseDouble(s);
		         part[1] = 0;
		      }else if( s.matches(SGN+I) ){
		         part[0] = 0;
		         part[1] = Double.parseDouble( s.replace( I, "1.0" ) );
		      }else if( s.matches(IMAG) ){
		         part[0] = 0;
		         part[1] = Double.parseDouble( s.replace( I , "" ) );
		      }else if( s.matches(REAL+OP+I) ){
		         part[0] = Double.parseDouble( s.replaceAll( "("+REAL+")"+OP+".+" , "$1" ) );
		         part[1] = Double.parseDouble( s.replaceAll( ".+("+OP+")"+I , "$1"+"1.0" ) );
		      }else{   //  s.matches(REAL+OP+NUM+I) 
		         part[0] = Double.parseDouble( s.replaceAll( "("+REAL+").+"  , "$1" ) );
		         part[1] = Double.parseDouble( s.replaceAll( ".+("+OP+NUM+")"+I , "$1" ) );
		      }
		      return part;
		   }
		   
	}


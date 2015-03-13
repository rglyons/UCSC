/* Queens.java
 * Robert Lyons
 * rglyons
 * pa5
 * Solves the N-Queens problem.
*/
import java.util.Scanner;
import java.util.Arrays;

public class Queens {
	static public void main(String[] args){
		Scanner sc = new Scanner(System.in);
		int i, j, k, counter=0;
		int n = 1;
		boolean verbose = false;
		if(args.length==1){
			verbose = false;
			try{
				n = Integer.parseInt(args[0]);
			}catch(NumberFormatException e){
				Usage();
			}
		}else if(args.length==2){
			if(args[0].equals("-v")){
				verbose = true;
				try{
					n = Integer.parseInt(args[1]);
				}catch(NumberFormatException e){
					Usage();
				}
			}
		}
		int [] A = new int[n+1];
		
		for (i=1; i<n+1; i++){
			A[i] = i;
		}
		
		if(verbose==true){
			for(j=0; j<factorial(n); j++){
				nextPermutation(A);
				if(isSolution(A)){
					counter++;
					System.out.print("(");
					for(k=1; k<A.length-1; k++){
						System.out.print(A[k]+", ");
					}
					if(k == A.length-1){
						System.out.print(A[k]);
					}
					System.out.println(")");
				}
			}
		}else{
			for(j=0; j<factorial(n); j++){
				nextPermutation(A);
				if(isSolution(A)){
					counter++;
				}
			}
		}
		System.out.println(n+"-Queens has "+counter+" solutions.");
	}

static void nextPermutation(int[] A){
		int i, j, k, pivot=0, successor=0;
		boolean pivotFound = false;
		for(i=A.length-2; i>0; i--){
			if(A[i]<A[i+1]){
				pivot = i;
				pivotFound = true;
				break;
			}
		}
		if(pivotFound == false){
			reverse(A, 1, A.length-1);
			return;
		}
		for(k=A.length-1; k>0; k--){
			if(A[k]>A[i]){
				successor = k;
				break;
			}
		}
		swap(A, pivot, successor);
		reverse(A, pivot+1, A.length-1);
		return;
}
	

static boolean isSolution(int[] A){
	int i, j;
	int n = A.length;
	for(i=1; i<=n-1; i++){
		for(j=i+1; j<=n-1; j++){
			if(j-i==Math.abs(A[i]-A[j])){
				return false;
			}
		}
	}
	return true;
}

static int factorial(int n){
	int i;
	int fact = 1;
	for (i=n; i>0; i--){
		fact *= i;
	}
	return fact;
}

static void Usage(){
	System.err.println("Usage: Queens [-v] number");
	System.out.println("Option: -v verbose output, print all solutions");
	System.exit(1);
}

static void swap(int[]A, int i, int j){
	int temp;
	temp = A[i];
	A[i] = A[j];
	A[j] = temp;
}

static void reverse(int[]A, int i, int j){
	while(i<j){
		swap(A,i,j);
		i++;
		j--;
	}
}
}

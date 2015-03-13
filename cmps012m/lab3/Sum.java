//Robert LYons
//rglyons
//cmps012m
//10/25/14
//lab 3
//Sum.java
//Define a class for adding numbers

public class Sum {
	//Keep track of running total
	private int value;
 
	// Constructor
	public Sum() {
		value = 0;
	}
       
	// Get current value
	public int getValue() {
		return value;
	}
             
	// Add something to running total
	public void add(int v) {
		value += v;
	}
                  
	// Reset total
	public void reset() {
		value = 0;
	}
}

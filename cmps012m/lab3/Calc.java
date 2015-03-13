// Robert Lyons
// rglyons
// cmps012m
// lab 3
// 10/26/14
// Calc.java
// Define a class for doing RPN.

public class Calc {
	double[] stack;
	int size = 100;
	int top;
	double h=2.0;
	double i=2.0;
	double j=2.0;
	double k=2.0;
	double l=2.0;

    // Constructor
    public Calc() {
	stack = new double[size];
	top = -1;
    }

    // Push a number
    public void push(double x) {
	stack[++top] = x;
    }

    // Pop top number (removes)
    public double pop() {
	if (stack.length >=  1)
		h =  stack[top--];
	if (stack.length < 1)
		throw new RuntimeException();
	return h;
    }

    // Peek at top number (does not remove)
    public double peek() {
        return stack[top];
    }

    // Add top two numbers
    public void add() {
	if (stack.length >=  1){
                stack[top-1] =  stack[top] + stack[top-1];
		top--;
	}
        if (stack.length < 1)
                throw new RuntimeException();
    }

    // Subtract top two numbers (top on right side)
    public void subtract() {
	if (stack.length >=  1){
               stack[top-1] =  stack[top-1] - stack[top];
		top--;
	}
        if (stack.length < 1)
                throw new RuntimeException();
    }

    // Multiply top two numbers
    public void multiply() {
	if (stack.length >=  1){
                stack[top-1] =  stack[top]*stack[top-1];
		top--;
	}
        if (stack.length < 1)
                throw new RuntimeException();
    }

    // Divide top two numbers (top on bottom)
    public void divide() {
	if (stack.length >  1 && stack[top] != 0.0){
               stack[top-1] =  stack[top-1] / stack[top];
		top--;
	}
	if (stack[top] == 0.0)
		throw new RuntimeException();
        if (stack.length <= 1)
                throw new RuntimeException();
    }

    //Transform top number to its reciprocal (1/x)
    public void reciprocal(){
	if (stack.length > 1)
		stack[top] = (1 / stack[top]);
	if (stack.length <= 1)
		throw new RuntimeException();
    }

    // Return how many numbers are in the stack
    public int depth() {
        return top+1;
    }
}

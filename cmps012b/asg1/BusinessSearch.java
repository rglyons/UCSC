//Robert Lyons
//rglyons
//cmps12b
//10/20/14
//BusinessSearch.java
//Takes a txt file as input and returns the phone numbers of the businesses in the txt file

import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.Scanner;

class BusinessSearch {
	static int nfcount;

	public static void main(String[] args) throws IOException {
		String argument = "input file";;
		if (args.length == 0){
			System.out.println("Usage: BusinessSearch businessDB");
			System.exit(0);
		} else {
			argument = args[0];
		}
		BufferedReader in = new BufferedReader(new FileReader(argument));
		int size = Integer.parseInt(in.readLine());
		int i, qcount = 0;
		String[] biz = new String[size];
		for (i = 0; i < size; i++) {
			biz[i] = in.readLine();
		}
		mergeSort(biz);
		BufferedReader type = new BufferedReader(new InputStreamReader(System.in));
		String s="word", result;
		while(s.compareTo("") != 0){
			s = type.readLine();
			if(s.compareTo("")==0){
				System.out.println(qcount+" total queries, "+nfcount+" not found.");
				System.exit(0);
			} else {
			result = binFind(biz,s);
			System.out.println(result);
			qcount++;
			}
		}
	}

	// ------------------------------------------------------------------------------------
	public static void mergeSort(String[] Array) {
		if (Array.length <= 1) {
			return;
		} else {
			String[] left = new String[Array.length / 2];
			String[] right = new String[Array.length - Array.length / 2];

			for (int i = 0; i < left.length; i++) {
				left[i] = Array[i];
			}
			for (int i = 0; i < right.length; i++) {
				right[i] = Array[i + Array.length / 2];
			}

			mergeSort(left);
			mergeSort(right);

			merge(Array, left, right);
		}
	}

	// ------------------------------------------------------------------------------------
	public static void merge(String[] sorted, String[] left, String[] right) {
		int h = 0;
		int j = 0;
		for (int i = 0; i < sorted.length; i++) {
			if (j >= right.length
					|| (h < left.length && left[h].compareToIgnoreCase(right[j]) < 0)) {
				sorted[i] = left[h];
				h++;
			} else {
				sorted[i] = right[j];
				j++;
			}
		}
	}

	// mergeSort and merge from
	// http://stackoverflow.com/questions/22925361/sorting-names-alphabetically-with-merge-sort-recursive

	public static String binFind(String[] a, String s) {
		int lowerbound = 0, upperbound = a.length;
		String [] name = new String [a.length];
		String[] number = new String[a.length];
                String [] temp = new String[2];
		for(int i=0;i<a.length;i++){
			temp = a[i].split(",");
                        name[i] = temp[0];
			number[i] = temp[1];
		}
		String result;
		int x = 2;
		while (name[x].compareToIgnoreCase(s) != 0 && lowerbound <=  upperbound) {
			x = (lowerbound + upperbound) / 2;
			if (name[x].compareToIgnoreCase(s) < 0) {
				lowerbound = x + 1;
			} else if (name[x].compareToIgnoreCase(s) > 0) {
				upperbound = x - 1;
			}
		}
		if (name[x].compareToIgnoreCase(s) == 0) {
			result =  number[x];
		} else {
			result = "NOT FOUND";
			nfcount++;
		}
	return result;
	}
}

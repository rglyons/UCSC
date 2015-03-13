//Robert Lyons
//rglyons
//cmps12b
//11/20/14
//xref.java
//Prints the words from a text file alphabetically and lists the line numbers on which those words are found in the text file


import java.io.*;
import java.util.Scanner;
import static java.lang.System.*;

class xref {

    static void processFile(String filename, boolean debug) throws IOException {
        Scanner scan = new Scanner (new File(filename));
        Tree tree = new Tree();
        for (int linenr = 1; scan.hasNextLine (); ++linenr) {
            for (String word: scan.nextLine().split ("\\W+")) {
                tree.insert(word, linenr);
            }
        }
        scan.close();
        if (debug) {
            tree.debug();
        } else {
            tree.output();
        }
    }

    public static void main(String[] args) {
        String option = "-d";
	boolean want_debug = false;
        String filename = "";
        if (args.length == 0){
          System.out.println("operand file argument required");
          System.exit(1); 
        } else if (args[0] != null && args[0].compareTo("-d") != 0){
          option = "";
          filename = args[0];
        } else if ((args[0] != null && args[0].compareTo("-d") == 0) && args.length == 1){
          System.out.println("operand file argument required");
          System.exit(1);
        } else if ((args[0] != null && args[0].compareTo("-d") == 0) && args[1] != null){
          want_debug = true;
	  filename = args[1];
        }
//        if (filename.compareTo("") != 0)
//          processFile((filename, want_debug);
        try {
            processFile(filename, want_debug);
        }catch (IOException error) {
            auxlib.warn (error.getMessage());
        }
        auxlib.exit();
    }

}


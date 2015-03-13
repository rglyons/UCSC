//Robert Lyons
//rglyons
//cmps012b
//11-30-14
//main.java
//A choose-your-own-adventure game that reads in an adventure text file and begins an adventure

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

class main {
  public static void main(String[] args) throws IOException {
    dllist allRooms = new dllist();
    String filename = "";
    if (args[0] != null) filename = args[0];
    else {
      System.out.println("File argument required.");
      System.exit(1);
    }
    buildAdventure(allRooms, filename);
    allRooms.setRoom("start");
    interact(allRooms, allRooms.getRoom());
  }

  public static void buildAdventure(dllist a, String x) throws IOException {
    BufferedReader operand = new BufferedReader(new FileReader(x));
    String s;
    String tag = "";
    while((s = operand.readLine()) != null){
      if (s.startsWith("r")){
	  if (a.isEmpty()) a.insert(s.substring(2), dllist.position.FIRST);
	  else a.insert(s.substring(2), dllist.position.FOLLOWING);
      } else if (s.startsWith("d")){
	  a.addToDescriptions(s.substring(2));
      } else if (s.startsWith("o")){
	  a.getRoom().options.add(new option(s.substring(2), tag));
      } else if (s.startsWith("t")){
	  tag = s.substring(2);
	  a.getRoom().options.get(a.getRoom().options.size() - 1).setRoom(tag);
      }
    }
    operand.close();
  }

  public static void displayRoom(dllist a, dllist.node room) {
    for (int i = 0; room.descriptions[i] != null; i++) System.out.println(room.descriptions[i]);
    System.out.println();
    String[] letters = new String[12];
    letters[0] = "a";
    letters[1] = "b";
    letters[2] = "c";
    letters[3] = "d";
    letters[4] = "e";
    letters[5] = "f";
    letters[6] = "g";
    letters[7] = "h";
    letters[8] = "i";
    letters[9] = "j";
    letters[10] = "k";
    letters[11] = "l";
    String content = room.options.get(0).content;
    int z;
    for (z=0; z<room.options.size()-1; z++){
      System.out.println(letters[z]+" - "+content);
      content = room.options.get(z+1).content;
    }
    System.out.println(letters[z]+" - "+content);
    System.out.println();
  }

  public static void interact (dllist a, dllist.node room) {
    Scanner stdin = new Scanner(System.in);
    displayRoom(a, room);
    String command = null;
    command = stdin.nextLine();
    try {
    switch(command){
        case "r":
          System.out.println("[restart]");
	  System.out.println();
	  a.clearHistory();
	  a.setRoom("start");
          interact(a, a.getRoom());
          return;

        case "q":
          System.out.println("[quit]");
	  System.out.println();
          System.exit(0);
          break;

        case "y":
	  int f;
	  String currentRoom = a.getItem();
	  a.setPosition(dllist.position.FIRST);
	  dllist.node tempRoom = a.getRoom();
          System.out.println("[information]");
	  System.out.println();
	  while (tempRoom != null){
	    System.out.print (tempRoom.item+" : ");
	    for (f=0; f<tempRoom.options.size()-1; f++)
	      System.out.print(tempRoom.options.get(f).room+" ");
	    System.out.print(tempRoom.options.get(f).room);
	    System.out.println();
	    tempRoom = tempRoom.next;
	  }
	  System.out.println();
	  a.setRoom(currentRoom);
	  interact(a, a.getRoom());
          break;

        case "z":
	  System.out.println("[undo]");
	  System.out.println();
	  String lastRoom = a.pop();
          a.setRoom(lastRoom);
	  interact(a, a.getRoom());
          break;

        case "a": System.out.println ("["+a.getRoom().options.get(0).content+"]");
		  System.out.println();
		  a.push(a.getItem());
		  a.setRoom(a.getRoom().options.get(0).room);
                  interact(a, a.getRoom());
		  break;
        case "b": System.out.println ("["+a.getRoom().options.get(1).content+"]");
		  System.out.println();
		  a.push(a.getItem());
		  a.setRoom(a.getRoom().options.get(1).room);
                  interact(a, a.getRoom());
		  break;
        case "c": System.out.println ("["+a.getRoom().options.get(2).content+"]");
		  System.out.println();
		  a.push(a.getItem());
		  a.setRoom(a.getRoom().options.get(2).room);
                  interact(a, a.getRoom());
                  break;
        case "d": System.out.println ("["+a.getRoom().options.get(3).content+"]");
		  System.out.println();
		  a.push(a.getItem());
		  a.setRoom(a.getRoom().options.get(3).room);
                  interact(a, a.getRoom());
                  break;
        case "e": System.out.println ("["+a.getRoom().options.get(4).content+"]");
		  System.out.println();
		  a.push(a.getItem());
		  a.setRoom(a.getRoom().options.get(4).room);
                  interact(a, a.getRoom());
                  break;
        case "f": System.out.println ("["+a.getRoom().options.get(5).content+"]");
		  System.out.println();
		  a.push(a.getItem());
		  a.setRoom(a.getRoom().options.get(5).room);
                  interact(a, a.getRoom());
                  break;
        case "g": System.out.println ("["+a.getRoom().options.get(6).content+"]");
		  System.out.println();
		  a.push(a.getItem());
		  a.setRoom(a.getRoom().options.get(6).room);
                  interact(a, a.getRoom());
                  break;
        case "h": System.out.println ("["+a.getRoom().options.get(7).content+"]");
		  System.out.println();
		  a.push(a.getItem());
		  a.setRoom(a.getRoom().options.get(7).room);
                  interact(a, a.getRoom());
                  break;
        case "i": System.out.println ("["+a.getRoom().options.get(8).content+"]");
		  System.out.println();
		  a.push(a.getItem());
		  a.setRoom(a.getRoom().options.get(8).room);
                  interact(a, a.getRoom());
                  break;
        case "j": System.out.println ("["+a.getRoom().options.get(9).content+"]");
		  System.out.println();
		  a.push(a.getItem());
		  a.setRoom(a.getRoom().options.get(9).room);
                  interact(a, a.getRoom());
                  break;
        case "k": System.out.println ("["+a.getRoom().options.get(10).content+"]");
		  System.out.println();
		  a.push(a.getItem());
		  a.setRoom(a.getRoom().options.get(10).room);
                  interact(a, a.getRoom());
                  break;
        case "l": System.out.println ("["+a.getRoom().options.get(11).content+"]");
		  System.out.println();
		  a.push(a.getItem());
		  a.setRoom(a.getRoom().options.get(11).room);
                  interact(a, a.getRoom());
                  break;
      }
      } catch (java.lang.IndexOutOfBoundsException e) {
          System.out.println ("Command not available!");
	  interact(a, a.getRoom());
      }
  }
}

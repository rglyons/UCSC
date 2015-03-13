// Robert Lyons
// rglyons
// cmps12b
// 12-3-14
// options.java
// Implementation for options object for cyoa game

public class option {
   public String content;
   public String room;

   public option(String content, String room) {
       this.content = content;
       this.room = room;
   }

   public void setRoom(String room) {
       this.room = room;
   }
}                    

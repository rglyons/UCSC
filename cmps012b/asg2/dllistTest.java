// Robert Lyons, Kenta Cole
// rglyons, kncole
// cmps12b
// 11-5-14
// dllistTest.java
// Unit tests for dllist

import org.junit.*;
import static org.junit.Assert.assertEquals;

public class dllistTest {

    @Test
    public void startsEmptyTest() {
        dllist lst = new dllist();
        assertEquals(true, lst.isEmpty());
    }

    @Test
    public void emptyAfterInsert() {
	dllist lst = new dllist();
        String i = "poop";
	lst.insert(i, dllist.position.LAST);
	assertEquals(false, lst.isEmpty());
    }

    @Test
    public void insertAtEndAndGetItem() {
	String i = "poop";
	String j = "Kenta";
	dllist lst = new dllist();
	lst.insert(i, dllist.position.LAST);
	assertEquals(i, lst.getItem());
	lst.insert(j, dllist.position.LAST);
	assertEquals(j, lst.getItem());
    }

    @Test
    public void insertAtFirstAndGetItem() {
	String i = "cat";
	String j = "dog";
	dllist lst = new dllist();
	lst.insert(i, dllist.position.FIRST);
	assertEquals(i, lst.getItem());
	lst.insert(j, dllist.position.FIRST);
	assertEquals(j, lst.getItem());
    }

    @Test
    public void setPositionAtEnd() {
	dllist lst = new dllist();
	String i = "cat";
	String j = "dog";
	lst.insert(i, dllist.position.FIRST);
	lst.insert(j, dllist.position.FIRST);
	lst.setPosition(dllist.position.LAST);
	assertEquals(i, lst.getItem());
    }

    @Test 
    public void setPositionAtBeginning() {
	dllist lst = new dllist();
        String i = "cat";
        String j = "dog";
        lst.insert(i, dllist.position.LAST);
        lst.insert(j, dllist.position.LAST);
        lst.setPosition(dllist.position.FIRST);
        assertEquals(i, lst.getItem());
    }

    @Test
    public void buildUpListCheckLast() {	
	dllist lst = new dllist();
	String A = "a";
	String B = "b";
	String C = "c";
	String D = "d";
	lst.insert(A, dllist.position.LAST);
	lst.insert(B, dllist.position.LAST);
	lst.insert(C, dllist.position.LAST);
	lst.insert(D, dllist.position.PREVIOUS);
	lst.setPosition(dllist.position.LAST);
	assertEquals(C, lst.getItem());
    }

    @Test
    public void buildUpListCheckFirst() {
	dllist lst = new dllist();
        String A = "a";
        String B = "b";
        String C = "c";
        String D = "d";
        lst.insert(A, dllist.position.FIRST);
        lst.insert(B, dllist.position.FIRST);
        lst.insert(C, dllist.position.FIRST);
        lst.insert(D, dllist.position.FOLLOWING);
        lst.setPosition(dllist.position.FIRST);
        assertEquals(C, lst.getItem());
    }

    @Test
    public void navWithPrevAndFol() {
	dllist lst = new dllist();
	String A = "a";
        String B = "b";
        String C = "c";
        String D = "d";
	String E = "e";
	lst.insert(A, dllist.position.FIRST);
        lst.insert(B, dllist.position.FIRST);
        lst.insert(C, dllist.position.FOLLOWING);
        lst.insert(D, dllist.position.LAST);
	lst.insert(E, dllist.position.PREVIOUS);
	lst.setPosition(dllist.position.PREVIOUS);
	assertEquals(A, lst.getItem());
	lst.setPosition(dllist.position.PREVIOUS);
	lst.setPosition(dllist.position.PREVIOUS);
        assertEquals(B, lst.getItem());
	lst.setPosition(dllist.position.FOLLOWING);
        assertEquals(C, lst.getItem());
	lst.setPosition(dllist.position.FOLLOWING);
	lst.setPosition(dllist.position.FOLLOWING);
	lst.setPosition(dllist.position.FOLLOWING);
        assertEquals(D, lst.getItem());
	lst.setPosition(dllist.position.PREVIOUS);
        assertEquals(E, lst.getItem());
    }

    @Test
    public void getPositionTest() {
	dllist lst = new dllist();
        String A = "a";
        String B = "b";
        String C = "c";
        String D = "d";
        String E = "e";
        lst.insert(A, dllist.position.FIRST);
        lst.insert(B, dllist.position.FIRST);
        lst.insert(C, dllist.position.FOLLOWING);
        lst.insert(D, dllist.position.LAST);
        lst.insert(E, dllist.position.PREVIOUS);
	assertEquals(3, lst.getPosition());
	lst.setPosition(dllist.position.PREVIOUS);
        assertEquals(2, lst.getPosition());
        lst.setPosition(dllist.position.FIRST);
        assertEquals(0, lst.getPosition());
        lst.setPosition(dllist.position.FOLLOWING);
        assertEquals(1, lst.getPosition());
        lst.setPosition(dllist.position.LAST);
        assertEquals(4, lst.getPosition());
        lst.setPosition(dllist.position.PREVIOUS);
        assertEquals(3, lst.getPosition());
    }

    @Test
    public void deleteTest() {
	dllist lst = new dllist();
        String A = "a";
        String B = "b";
        String C = "c";
        String D = "d";
        String E = "e";
        lst.insert(A, dllist.position.FIRST);
        lst.insert(B, dllist.position.FIRST);
        lst.insert(C, dllist.position.FOLLOWING);
        lst.insert(D, dllist.position.LAST);
        lst.insert(E, dllist.position.PREVIOUS);
	lst.delete();
	assertEquals(3, lst.getPosition());
	assertEquals(D, lst.getItem());
	lst.delete();
	assertEquals(2, lst.getPosition());
	assertEquals(A, lst.getItem());
	lst.setPosition(dllist.position.FIRST);
	lst.delete();
	assertEquals(0, lst.getPosition());
	assertEquals(C, lst.getItem());
    }

    @Test (expected = Exception.class)
    public void getItemExcepTest() {
	dllist lst = new dllist();
	lst.getItem();
    }

    @Test (expected = Exception.class)
    public void getPosExcepTest() {
	dllist lst = new dllist();
	lst.getPosition();
    }

    @Test (expected = Exception.class)
    public void deleteExcepTest() {
	dllist lst = new dllist();
	lst.delete();
    }

    @Test (expected = Exception.class)
    public void insertPrevExcepTest() {
	dllist lst = new dllist();
	lst.insert("hello", dllist.position.PREVIOUS);
    }

    @Test (expected = Exception.class)
    public void insertFolExcepTest() {
	dllist lst = new dllist();
	lst.insert("hello", dllist.position.FOLLOWING);
    }

}

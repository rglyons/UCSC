// Robert Lyons
// rglyons
// cmps101
// ListTest.java
// Unit tests for List class
//
// TO RUN:
// COMPILE: javac -cp "junit-4.12-beta-2.jar:hamcrest-core-1.3.jar:." ListTest.java
// RUN: java -cp "junit-4.12-beta-2.jar:hamcrest-core-1.3.jar:." org.junit.runner.JUnitCore ListTest 

import org.junit.*;
import static org.junit.Assert.assertEquals;

public class ListTest {

    //Access Function Tests

    @Test
    public void basicTest() {
        List l = new List();
        l.append(11);
	assertEquals(-1, l.getIndex());
	l.moveTo(0);
        assertEquals(11, l.getElement());
	assertEquals(0, l.getIndex());
	assertEquals(11, l.front());
	assertEquals(11, l.back());
    }

    @Test
    public void lengthTest() {
        List l = new List();
	l.prepend(42);
	l.append(1);
	l.append(2);
	assertEquals(42, l.front());
	assertEquals(2, l.back());
        assertEquals(3, l.length());
    }

    @Test
    public void equalsTest() {
	List l = new List();
	List m = new List();
	m.append(1);
	m.append(2);
	m.append(3);
	l.append(1);
	l.append(2);
	l.append(3);
	l.moveTo(2);
	assertEquals(3, l.length());
	assertEquals(3, m.length());
	assertEquals(true, l.equals(m));
	assertEquals(2, l.getIndex());
	l.deleteBack();
	l.append(4);
	assertEquals(3, l.length());
	assertEquals(false, l.equals(m));
    }

    @Test
    public void clearTest() {
        List l = new List();
	l.append(12);
	l.append(3);
	l.prepend(6);
	l.moveTo(0);
	assertEquals(0, l.getIndex());
	assertEquals(6, l.getElement());
	assertEquals(3, l.length());
	l.clear();
	assertEquals(-1, l.getIndex());
	assertEquals(0, l.length());
    }

    @Test(expected=Exception.class)
    public void emptyFrontTest() {
        List l = new List();
        l.front();
    }

    @Test(expected=Exception.class)
    public void emptyBackTest() {
        List l = new List();
	l.back();
    }

    @Test(expected=Exception.class)
    public void emptyGetEelementTest() {
        List l = new List();
	l.getElement();
    }

    @Test
    public void moveNextMovePrevTest() {
        List l = new List();
	l.append(4);
	l.append(5);
	l.append(6);
	l.moveTo(1);
	assertEquals(1, l.getIndex());
	assertEquals(5, l.getElement());
	l.movePrev();
	assertEquals(0, l.getIndex());
	assertEquals(4, l.getElement());
	l.movePrev();
	assertEquals(-1, l.getIndex());
	l.movePrev();
	assertEquals(-1, l.getIndex());
	l.moveTo(1);
	l.moveNext();
	assertEquals(2, l.getIndex());
	assertEquals(6, l.getElement());
	l.moveNext();
	assertEquals(-1, l.getIndex());
	l.moveNext();
	assertEquals(-1, l.getIndex());
    }

    @Test(expected=Exception.class)
    public void emptyInsertBeforeTest() {
        List l = new List();
	l.insertBefore(4);
    }

    @Test(expected=Exception.class)
    public void emptyInsertAfterTest() {
        List l = new List();
	l.insertAfter(2);
    }

    @Test(expected=Exception.class)
    public void emptyDeleteFrontTest() {
        List l = new List();
	l.deleteFront();
    }

    @Test(expected=Exception.class)
    public void underflowDivideTest() {
        List l = new List();
	l.deleteBack();
    }

    @Test(expected=Exception.class)
    public void emptyDeleteTest() {
        List l = new List();
        l.delete();
    }

    @Test
    public void InsertBeforeTest() {
        List l = new List();
	l.append(5);
	l.moveTo(0);
	l.insertBefore(4);
	l.movePrev();
	assertEquals(0, l.getIndex());
	assertEquals(4, l.getElement());
    }

    @Test
    public void insertAfterTest() {
        List l = new List();
        l.append(5);
        l.moveTo(0);
        l.insertAfter(4);
        l.moveNext();
        assertEquals(1, l.getIndex());
        assertEquals(4, l.getElement());
    }

    @Test
    public void DeleteFrontTest() {
        List l = new List();
	l.append(65);
	l.append(46);
	l.moveTo(0);
	l.deleteFront();
	assertEquals(0, l.getIndex());
	assertEquals(46, l.getElement());
	l.append(9);
	l.moveTo(1);
	l.deleteFront();
	assertEquals(0, l.getIndex());
	assertEquals(9, l.getElement());
	assertEquals(1, l.length());
    }

    @Test
    public void deleteBackTest() {
        List l = new List();
	l.append(5);
	l.append(9);
	l.deleteBack();
	assertEquals(1, l.length());
	l.append(8);
	l.moveTo(1);
	assertEquals(8, l.getElement());
	l.deleteBack();
	assertEquals(0, l.getIndex());
	assertEquals(1, l.length());
    }

    @Test
    public void deleteTest() {
        List l = new List();
	l.append(5);
	l.append(6);
	l.moveTo(1);
	assertEquals(2, l.length());
	l.delete();
	assertEquals(-1, l.getIndex());
	assertEquals(1, l.length());
    }

    @Test
    public void toStringTest() {
        List l = new List();
	l.append(2);
	l.append(1);
	l.append(3);
	l.moveTo(1);
	String list = l.toString();
	assertEquals(1, l.getIndex());
	assertEquals("2 1 3", list);
    }

    @Test
    public void copyTest() {
        List l = new List();
	l.append(5);
	l.append(4);
	l.append(6);
	l.moveTo(1);
	List m = l.copy();
	assertEquals(1, l.getIndex());
	assertEquals(-1, m.getIndex());
	assertEquals("5 4 6", m.toString());
    }
}

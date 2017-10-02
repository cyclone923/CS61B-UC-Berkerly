package pj1;
/* SListNode.java */

/**
 *  SListNode is a class used internally by the SList class.  An SList object
 *  is a singly-linked list, and an SListNode is a node of a singly-linked
 *  list.  Each SListNode has two references:  one to an object, and one to
 *  the next node in the list.
 *
 *  @author Kathy Yelick and Jonathan Shewchuk
 */

class SListNode {
  short R;
  short G;
  short B;
  int runL;
  SListNode next;


  /**
   *  SListNode() (with two parameters) constructs a list node referencing the
   *  item "obj", whose next list node is to be "next".
   */

  SListNode(short red, short green,short blue,int runLengths,SListNode next) {
    R = red;
    G = green;
    B = blue;
    runL = runLengths;
    this.next = next;
  }

  /**
   *  SListNode() (with one parameter) constructs a list node referencing the
   *  item "obj".
   */

  SListNode(short red, short green,short blue,int runLengths) {
    this(red,green,blue,runLengths,null);
  }
}

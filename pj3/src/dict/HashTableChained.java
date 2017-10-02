/* HashTableChained.java */

package dict;
import lists.*;

/**
 *  HashTableChained implements a Dictionary as a hash table with chaining.
 *  All objects used as keys must have a valid hashCode() method, which is
 *  used to determine which bucket of the hash table an entry is stored in.
 *  Each object's hashCode() is presumed to return an int between
 *  Integer.MIN_VALUE and Integer.MAX_VALUE.  The HashTableChained class
 *  implements only the compression function, which maps the hash code to
 *  a bucket in the table's range.
 *
 *  DO NOT CHANGE ANY PROTOTYPES IN THIS FILE.
 **/

public class HashTableChained implements Dictionary {

  /**
   *  Place any data fields here.
   **/
	
	private DList[] Buckets;
	

  /** 
   *  Construct a new empty hash table intended to hold roughly sizeEstimate
   *  entries.  (The precise number of buckets is up to you, but we recommend
   *  you use a prime number, and shoot for a load factor between 0.5 and 1.)
   **/

  public HashTableChained(int size) {
    // Your solution here.
	  Buckets = new DList[size];
	  for(int i = 0;i < Buckets.length;i++)
	  {
		  Buckets[i] = new DList();
	  }
  }

  /** 
   *  Construct a new empty hash table with a default size.  Say, a prime in
   *  the neighborhood of 100.
   **/

  public HashTableChained() {
    // Your solution here.
	  Buckets = new DList[1];
	  Buckets[0] = new DList();

  }

  /**
   *  Converts a hash code in the range Integer.MIN_VALUE...Integer.MAX_VALUE
   *  to a value in the range 0...(size of hash table) - 1.
   *
   *  This function should have package protection (so we can test it), and
   *  should be used by insert, find, and remove.
   **/

  int compFunction(int code) {
	    // Replace the following line with your solution.
		  int cmpValue = ((7*code+11) % 16908799) % Buckets.length;
		  if(cmpValue < 0)
		  {
			  cmpValue += Buckets.length;
		  }
	    return cmpValue;
	  }

  /** 
   *  Returns the number of entries stored in the dictionary.  Entries with
   *  the same key (or even the same key and value) each still count as
   *  a separate entry.
   *  @return number of entries in the dictionary.
   **/

  public int size() {
    // Replace the following line with your solution.
	  int size = 0;
	  for(int i = 0;i < Buckets.length;i++)
	  {
		  size += Buckets[i].length();
	  }
    return size;
  }
  
  /** 
   *  Returns the load factor of the dictionary. 
   *  It equals to the total buckets over the size of table.
   *  @return the load factor.
   **/

  public double getLoadFactor() {
    // Replace the following line with your solution.
	  double size = this.size();
	  double length = Buckets.length;
	  double loadFactor = size/length;
    return loadFactor;
  }

  /** 
   *  Tests if the dictionary is empty.
   *
   *  @return true if the dictionary has no entries; false otherwise.
   **/

  public boolean isEmpty() {
    // Replace the following line with your solution.
    return (size() == 0);
  }

  /**
   *  Create a new Entry object referencing the input key and associated value,
   *  and insert the entry into the dictionary.  Return a reference to the new
   *  entry.  Multiple entries with the same key (or even the same key and
   *  value) can coexist in the dictionary.
   *
   *  This method should run in O(1) time if the number of collisions is small.
   *
   *  @param key the key by which the entry can be retrieved.
   *  @param value an arbitrary object.
   *  @return an entry containing the key and value.
   **/

  public Entry insert(Object key, Object value) {
    // Replace the following line with your solution.
	  
	  resize();
	  
	  Entry newEntry = new Entry();
	  newEntry.key = key;
	  newEntry.value = value;
	  int nthBuckect = compFunction(key.hashCode());
	  Buckets[nthBuckect].insertFront(newEntry);
    return newEntry;
  }

  /** 
   *  Search for an entry with the specified key.  If such an entry is found,
   *  return it; otherwise return null.  If several entries have the specified
   *  key, choose one arbitrarily and return it.
   *
   *  This method should run in O(1) time if the number of collisions is small.
   *
   *  @param key the search key.
   *  @return an entry containing the key and an associated value, or null if
   *          no entry contains the specified key.
   **/

  public Entry find(Object key) {
    // Replace the following line with your solution.
	  for(int i = 0;i < Buckets.length;i++)
	  {
		  try 
		  {
			  for(DListNode j = (DListNode) Buckets[i].front();j.isValidNode();j = (DListNode) j.next())
			  {
				  if(((Entry)j.item()).key.equals(key))
				  {
					  return (Entry)j.item();
				  }
			  }
		  }
		  catch (InvalidNodeException e) 
		  {
			  // TODO Auto-generated catch block
			  e.printStackTrace();
		  }
	  }
    return null;
  }
  
  /** 
   *  Find all Entries and return them in an array.
   *
   *  This method should run in O(size()) time if the number of collisions is small.
   *
   *  @return an array of Entries.
   **/

  public Entry[] findall() {
    // Replace the following line with your solution.
	  Entry[] retEntry = new Entry[size()];
	  int k = 0;
	  for(int i = 0;i < Buckets.length;i++)
	  {
		  try 
		  {
			  for(DListNode j = (DListNode) Buckets[i].front();j.isValidNode();j = (DListNode) j.next())
			  {
				  retEntry[k] = (Entry) j.item();
				  k++;
			  }
		  }
		  catch (InvalidNodeException e) 
		  {
			  // TODO Auto-generated catch block
			  e.printStackTrace();
		  }
	  }
    return retEntry;
  }

  /** 
   *  Remove an entry with the specified key.  If such an entry is found,
   *  remove it from the table and return it; otherwise return null.
   *  If several entries have the specified key, choose one arbitrarily, then
   *  remove and return it.
   *
   *  This method should run in O(1) time if the number of collisions is small.
   *
   *  @param key the search key.
   *  @return an entry containing the key and an associated value, or null if
   *          no entry contains the specified key.
   */

  public Entry remove(Object key) {
    // Replace the following line with your solution.
	  resize();
	  for(int i = 0;i < Buckets.length;i++)
	  {
		  try 
		  {
			  for(DListNode j = (DListNode) Buckets[i].front();j.isValidNode();j = (DListNode) j.next())
			  {
				  if(((Entry)j.item()).key.equals(key))
				  {
					  Entry retEntry = (Entry) j.item();
					  j.remove();
					  return retEntry;
				  }
			  }
		  }
		  catch (InvalidNodeException e) 
		  {
			  // TODO Auto-generated catch block
			  e.printStackTrace();
		  }
	  }
    return null;
  }

  /**
   *  Remove all entries from the dictionary.
   */
  public void makeEmpty() {
    // Your solution here.
	  for(int i = 0;i < Buckets.length;i++)
	  {
		  Buckets[i] = new DList();
	  }
	  
  }
  
  /**
   *  Resize the table.
   */
  public void resize() {
    // Your solution here.
	  DList newBuckets[];
	  if(this.getLoadFactor() >= 0.7 && this.isEmpty() == false)
	  {
		  newBuckets = new DList[Buckets.length*2];
		  for(int i = 0;i < newBuckets.length;i++)
		  {
			  newBuckets[i] = new DList();
		  }
	  }
	  else if(this.getLoadFactor() <= 0.25 && this.isEmpty() == false)
	  {
		  newBuckets = new DList[Buckets.length/2];
		  for(int i = 0;i < newBuckets.length;i++)
		  {
			  newBuckets[i] = new DList();
		  }
	  }
	  else
	  {
		  return;
	  }
	  
	  
	  for(int i = 0;i < Buckets.length;i++)
	  {
		  try 
		  {
			  for(DListNode j = (DListNode) Buckets[i].front();j.isValidNode();j = (DListNode) j.next())
			  {
				  Entry newEntry = new Entry();
				  newEntry.key = ((Entry)j.item()).key;;
				  newEntry.value = ((Entry)j.item()).value;
				  int hashcode = ((Entry)j.item()).key.hashCode();
				  int cmpValue = ((7*hashcode+11) % 16908799) % newBuckets.length;
				  if(cmpValue < 0)
				  {
					  cmpValue += newBuckets.length;
				  }
				  newBuckets[cmpValue].insertFront(newEntry);
			  }
		  }
		  catch (InvalidNodeException e) 
		  {
			  // TODO Auto-generated catch block
			  e.printStackTrace();
		  }
	  }
	  Buckets = newBuckets;
	  
  }
  
  
  public String toString()
  {
	  String s = "";
	  for(int i = 0;i < Buckets.length;i++)
	  {
		  s = s + i + "thBucket:" + Buckets[i] + "\n";
	  }
	  return s;
  }
  
  public static void main(String[] args)
  {
	  HashTableChained table = new HashTableChained();
	  
	  String s1 = "aaaaaa";
	  String d1 = "D1";
	  String s2 = "Daisy";
	  String d2 = "D2";
	  String s3 = "Archer";
	  String d3 = "D3";
	  String s4 = "Footman";
	  String d4 = "D4";
	  String s5 = "Griffin";
	  String d5 = "D5";
	  String s6 = "Witch";
	  String d6 = "D6";
	  String s7 = "Priest";
	  String d7 = "D7";
	  
	  
	  System.out.println(String.valueOf(table.getLoadFactor()));
	  table.insert(s1, d1);
	  System.out.println(String.valueOf(table.getLoadFactor()));
	  table.insert(s2, d2);
	  System.out.println(String.valueOf(table.getLoadFactor()));
	  table.insert(s3, d3);
	  System.out.println(String.valueOf(table.getLoadFactor()));
	  table.insert(s4, d4);
	  System.out.println(String.valueOf(table.getLoadFactor()));
	  table.insert(s5, d5);
	  System.out.println(String.valueOf(table.getLoadFactor()));
	  table.insert(s6, d6);
	  System.out.println(String.valueOf(table.getLoadFactor()));
	  table.insert(s7, d7);
	  System.out.println(String.valueOf(table.getLoadFactor()));
	  System.out.println(table);
	  
	  table.remove(s1);
	  System.out.println(String.valueOf(table.getLoadFactor()));
	  table.remove(s2);
	  System.out.println(String.valueOf(table.getLoadFactor()));
	  table.remove(s3);
	  System.out.println(String.valueOf(table.getLoadFactor()));
	  table.remove(s4);
	  System.out.println(String.valueOf(table.getLoadFactor()));
	  table.remove(s5);
	  System.out.println(String.valueOf(table.getLoadFactor()));
	  table.remove(s6);
	  System.out.println(String.valueOf(table.getLoadFactor()));
	  table.remove(s7);
	  System.out.println(String.valueOf(table.getLoadFactor()));
	  
	  System.out.println(table);
  }


}

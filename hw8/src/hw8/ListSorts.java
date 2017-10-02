/* ListSorts.java */
package hw8;
import list.*;

public class ListSorts {

  private final static int SORTSIZE = 1000;

  /**
   *  makeQueueOfQueues() makes a queue of queues, each containing one item
   *  of q.  Upon completion of this method, q is empty.
   *  @param q is a LinkedQueue of objects.
   *  @return a LinkedQueue containing LinkedQueue objects, each of which
   *    contains one object from q.
   **/
  public static LinkedQueue makeQueueOfQueues(LinkedQueue q) {
    // Replace the following line with your solution.
	  LinkedQueue newQueue = new LinkedQueue();
	  while(!q.isEmpty())
	  {
		  LinkedQueue temp = new LinkedQueue();
		  try 
		  {
			  temp.enqueue(q.dequeue());
		  }
		  catch (QueueEmptyException e) 
		  {
			  // TODO Auto-generated catch block
			  e.printStackTrace();
		  }
		  newQueue.enqueue(temp);
	  }
    return newQueue;
  }

  /**
   *  mergeSortedQueues() merges two sorted queues into a third.  On completion
   *  of this method, q1 and q2 are empty, and their items have been merged
   *  into the returned queue.
   *  @param q1 is LinkedQueue of Comparable objects, sorted from smallest 
   *    to largest.
   *  @param q2 is LinkedQueue of Comparable objects, sorted from smallest 
   *    to largest.
   *  @return a LinkedQueue containing all the Comparable objects from q1 
   *   and q2 (and nothing else), sorted from smallest to largest.
   **/
  public static LinkedQueue mergeSortedQueues(LinkedQueue q1, LinkedQueue q2) {
    // Replace the following line with your solution.
	  LinkedQueue newQueue = new LinkedQueue();
	  try
	  {
		  while(!q1.isEmpty() || !q2.isEmpty())
		  {
			  if(!q1.isEmpty() && !q2.isEmpty())
			  {
				  if(((Comparable)q1.front()).compareTo(q2.front()) <= 0)
				  {
					  newQueue.enqueue(q1.dequeue());
				  }
				  else
				  {
					  newQueue.enqueue(q2.dequeue());
				  }
			  }
			  else if(q1.isEmpty() && !q2.isEmpty())
			  {
				  newQueue.enqueue(q2.dequeue());
			  }
			  else if(!q1.isEmpty() && q2.isEmpty())
			  {
				  newQueue.enqueue(q1.dequeue());
			  }
	  
		  }
	  }
	  catch(QueueEmptyException e)
	  {
		  e.printStackTrace();
	  }
    return newQueue;
  }

  /**
   *  partition() partitions qIn using the pivot item.  On completion of
   *  this method, qIn is empty, and its items have been moved to qSmall,
   *  qEquals, and qLarge, according to their relationship to the pivot.
   *  @param qIn is a LinkedQueue of Comparable objects.
   *  @param pivot is a Comparable item used for partitioning.
   *  @param qSmall is a LinkedQueue, in which all items less than pivot
   *    will be enqueued.
   *  @param qEquals is a LinkedQueue, in which all items equal to the pivot
   *    will be enqueued.
   *  @param qLarge is a LinkedQueue, in which all items greater than pivot
   *    will be enqueued.  
   **/   
  public static void partition(LinkedQueue qIn, Comparable pivot, 
                               LinkedQueue qSmall, LinkedQueue qEquals, 
                               LinkedQueue qLarge) {
    // Your solution here.
	  if(qIn.isEmpty() || !qSmall.isEmpty() || !qEquals.isEmpty() || !qLarge.isEmpty())
	  {
		  return;
	  }
	  else
	  {
		  try
		  {
			  while(!qIn.isEmpty())
			  {
				  Object temp = qIn.dequeue();
				  if(pivot.compareTo(temp) == 0)
				  {
					  qEquals.enqueue(temp);
				  }
				  else if(pivot.compareTo(temp) > 0)
				  {
					 qSmall.enqueue(temp);
				  }
				  else
				  {
					  qLarge.enqueue(temp);
				  }
			  }
		  }
		  catch(QueueEmptyException e)
		  {
			  e.printStackTrace();
		  }
	  }
	  
  }

  /**
   *  mergeSort() sorts q from smallest to largest using mergesort.
   *  @param q is a LinkedQueue of Comparable objects.
   **/
  public static void mergeSort(LinkedQueue q) {
    // Your solution here.
	  if(q.size() > 1)
	  {
		  LinkedQueue firstHalf = new LinkedQueue();
		  LinkedQueue secondHalf = new LinkedQueue();
		  int halfSize = q.size()/2;
		  
		  try
		  {
			  for(int i = 0;i < halfSize;i++)
			  {
				  firstHalf.enqueue(q.dequeue());
			  }
			  while(!q.isEmpty())
			  {
				  secondHalf.enqueue(q.dequeue());
			  }
		  }
		  catch (QueueEmptyException e) 
		  {
			// TODO Auto-generated catch block
			  e.printStackTrace();
		  }
		  	  
		  mergeSort(firstHalf);
		  mergeSort(secondHalf);
		  
		  LinkedQueue newQueue = mergeSortedQueues(firstHalf,secondHalf);
		  while(!newQueue.isEmpty())
		  {
			  try 
			  {
				  q.enqueue(newQueue.dequeue());
			  }
			  catch (QueueEmptyException e) 
			  {
				// TODO Auto-generated catch block
				  e.printStackTrace();
			  }
		  }
	  }


	  
  }

  /**
   *  quickSort() sorts q from smallest to largest using quicksort.
   *  @param q is a LinkedQueue of Comparable objects.
   **/
  public static void quickSort(LinkedQueue q) {
    // Your solution here.
	  if(q.size() > 1)
	  {
		  LinkedQueue small = new LinkedQueue();
		  LinkedQueue equals = new LinkedQueue();
		  LinkedQueue large = new LinkedQueue();
		  
		  int randIdx = Math.round((float)Math.random()*q.size());
		  Comparable pivot = (Comparable)q.nth(randIdx);
		  partition(q,pivot,small,equals,large);
		  	  
		  quickSort(small);
		  quickSort(large);
		  
		  LinkedQueue newQueue = mergeSortedQueues(small,equals);
		  newQueue = mergeSortedQueues(newQueue,large);
		  while(!newQueue.isEmpty())
		  {
			  try 
			  {
				  q.enqueue(newQueue.dequeue());
			  }
			  catch (QueueEmptyException e) 
			  {
				// TODO Auto-generated catch block
				  e.printStackTrace();
			  }
		  }
	  }

  }

  /**
   *  makeRandom() builds a LinkedQueue of the indicated size containing
   *  Integer items.  The items are randomly chosen between 0 and size - 1.
   *  @param size is the size of the resulting LinkedQueue.
   **/
  public static LinkedQueue makeRandom(int size) {
    LinkedQueue q = new LinkedQueue();
    for (int i = 0; i < size; i++) {
      q.enqueue(new Integer((int) (size * Math.random())));
    }
    return q;
  }

  /**
   *  main() performs some tests on mergesort and quicksort.  Feel free to add
   *  more tests of your own to make sure your algorithms works on boundary
   *  cases.  Your test code will not be graded.
   **/
  public static void main(String [] args) {

    LinkedQueue q = makeRandom(100);
    System.out.println(q.toString());
    mergeSort(q);
    System.out.println(q.toString());

    q = makeRandom(100);
    System.out.println(q.toString());
    quickSort(q);
    System.out.println(q.toString());


    Timer stopWatch = new Timer();
    q = makeRandom(SORTSIZE);
    stopWatch.start();
    mergeSort(q);
    stopWatch.stop();
    System.out.println("Mergesort time, " + SORTSIZE + " Integers:  " +
                       stopWatch.elapsed() + " msec.");

    stopWatch.reset();
    q = makeRandom(SORTSIZE);
    stopWatch.start();
    quickSort(q);
    stopWatch.stop();
    System.out.println("Quicksort time, " + SORTSIZE + " Integers:  " +
                       stopWatch.elapsed() + " msec.");

  }

}

/* Sorts.java */
package hw10;

public class Sorts
{
	  /**
	   *  Place any final static fields you would like to have here.
	   **/


	  /**
	   *  countingSort() sorts an array of int keys according to the
	   *  values of _one_ of the base-16 digits of each key.  "whichDigit"
	   *  indicates which digit is the sort key.  A zero means sort on the least
	   *  significant (ones) digit; a one means sort on the second least
	   *  significant (sixteens) digit; and so on, up to a seven, which means
	   *  sort on the most significant digit.
	   *  @param key is an array of ints.  Assume no key is negative.
	   *  @param whichDigit is a number in 0...7 specifying which base-16 digit
	   *    is the sort key.
	   *  @return an array of type int, having the same length as "keys"
	   *    and containing the same keys sorted according to the chosen digit.
	   *
	   *    Note:  Return a _newly_ created array.  DO NOT CHANGE THE ARRAY keys.
	   **/
	  public static int[] countingSort(int[] keys, int whichDigit) {
	    // Replace the following line with your solution.
		  byte[] cmpDigit = new byte[keys.length];
		  for(int i = 0;i < keys.length;i++)
		  {
			  cmpDigit[i] = (byte)((keys[i]>>(whichDigit*4))%0x10);
			  //System.out.print(Integer.toHexString(cmpDigit[i])+" ");
		  }
		  //System.out.println();
		  int[] counts = new int[16];
		  for(int i = 0;i < cmpDigit.length;i++)
		  {
			  counts[cmpDigit[i]]++;
		  }
		  int total = 0;
		  int c;
		  for(int i = 0;i < 16;i++)
		  {
			  c = counts[i];
			  counts[i] = total;
			  total = total + c;
		  }
		  int[] y = new int[keys.length];
		  
		  for(int i = 0;i < cmpDigit.length;i++)
		  {
			  y[counts[cmpDigit[i]]] = keys[i];
			  counts[cmpDigit[i]]++;
		  }

		  
		  
	    return y;
	  }

	  /**
	   *  radixSort() sorts an array of int keys (using all 32 bits
	   *  of each key to determine the ordering).
	   *  @param key is an array of ints.  Assume no key is negative.
	   *  @return an array of type int, having the same length as "keys"
	   *    and containing the same keys in sorted order.
	   *
	   *    Note:  Return a _newly_ created array.  DO NOT CHANGE THE ARRAY keys.
	   **/
	  public static int[] radixSort(int[] keys) {
	    // Replace the following line with your solution.
		  int n = keys.length;
		  int q = (int)Math.round(Math.log(n)/Math.log(2));
		  int basicUnit = (int)Math.pow(2, q);
		  int passes = (int) Math.ceil(32/q);
		  
		  for(int round = 0;round < passes;round++)
		  {
			  int[] cmpUnit = new int[keys.length];
			  for(int i = 0;i < keys.length;i++)
			  {
				  cmpUnit[i] = keys[i];
				  for(int j = 0;j < round;j++)
				  {
					  cmpUnit[i] = cmpUnit[i]/basicUnit;
				  }
				  cmpUnit[i] = cmpUnit[i]%basicUnit;
			  }
			  
			  int[] counts = new int[basicUnit];
			  for(int i = 0;i < cmpUnit.length;i++)
			  {
				  counts[cmpUnit[i]]++;
			  }
			  int total = 0;
			  int c;
			  for(int i = 0;i < basicUnit;i++)
			  {
				  c = counts[i];
				  counts[i] = total;
				  total = total + c;
			  }
			  
			  int[] afterSort = new int[keys.length];
			  for(int i = 0;i < cmpUnit.length;i++)
			  {
				  afterSort[counts[cmpUnit[i]]] = keys[i];
				  counts[cmpUnit[i]]++;
			  }
			  keys = afterSort;
		  }

	    return keys;
	  }

	  /**
	   *  yell() prints an array of int keys.  Each key is printed in hexadecimal
	   *  (base 16).
	   *  @param key is an array of ints.
	   **/
	  public static void yell(int[] keys) {
	    System.out.print("keys are [ ");
	    for (int i = 0; i < keys.length; i++) {
	      System.out.print(Integer.toString(keys[i], 16) + " ");
	    }
	    System.out.println("]");
	  }

	  /**
	   *  main() creates and sorts a sample array.
	   *  We recommend you add more tests of your own.
	   *  Your test code will not be graded.
	   **/
	  public static void main(String[] args) {
	    int[] keys = { Integer.parseInt("60013879", 16),
	                   Integer.parseInt("11111119", 16),
	                   Integer.parseInt("2c735010", 16),
	                   Integer.parseInt("2c732010", 16),
	                   Integer.parseInt("7fffffff", 16),
	                   Integer.parseInt("4001387c", 16),
	                   Integer.parseInt("10111119", 16),
	                   Integer.parseInt("529a7385", 16),
	                   Integer.parseInt("1e635010", 16),
	                   Integer.parseInt("28905879", 16),
	                   Integer.parseInt("00011119", 16),
	                   Integer.parseInt("00000000", 16),
	                   Integer.parseInt("7c725010", 16),
	                   Integer.parseInt("1e630010", 16),
	                   Integer.parseInt("111111e5", 16),
	                   Integer.parseInt("61feed0c", 16),
	                   Integer.parseInt("3bba7387", 16),
	                   Integer.parseInt("52953fdb", 16),
	                   Integer.parseInt("40013879", 16) };

	    yell(keys);
	    for(int i = 0;i < 8;i++)
	    {
	    	keys = countingSort(keys,i);
	    }
	    yell(keys);
	    
	    int[] key2s = { Integer.parseInt("60013879", 16),
                Integer.parseInt("11111119", 16),
                Integer.parseInt("2c735010", 16),
                Integer.parseInt("2c732010", 16),
                Integer.parseInt("7fffefff", 16),
                Integer.parseInt("4001387c", 16),
                Integer.parseInt("10111119", 16),
                Integer.parseInt("529a7385", 16),
                Integer.parseInt("1e635010", 16),
                Integer.parseInt("28905879", 16),
                Integer.parseInt("00011119", 16),
                Integer.parseInt("00000000", 16),
                Integer.parseInt("7c725010", 16),
                Integer.parseInt("1e630010", 16),
                Integer.parseInt("111111e5", 16),
                Integer.parseInt("61feed0c", 16),
                Integer.parseInt("3bba7387", 16),
                Integer.parseInt("52953fdb", 16),
                Integer.parseInt("40013879", 16),
                Integer.parseInt("11111116", 16),
                Integer.parseInt("2c735010", 16),
                Integer.parseInt("2c732110", 16),
                Integer.parseInt("7fffffff", 16),
                Integer.parseInt("4001387c", 16),
                Integer.parseInt("10132119", 16),
                Integer.parseInt("529a3c85", 16),
                Integer.parseInt("1e635010", 16),
                Integer.parseInt("289bad79", 16),
                Integer.parseInt("00011119", 16),
                Integer.parseInt("00002150", 16),
                Integer.parseInt("7c725010", 16),
                Integer.parseInt("1e634321", 16),
                Integer.parseInt("111111e5", 16),
                Integer.parseInt("61feed0c", 16),
                Integer.parseInt("3bba21af", 16),
                Integer.parseInt("52953fdb", 16),
                Integer.parseInt("40013879", 16) };
	    yell(key2s);
	    key2s = radixSort(key2s);
	    yell(key2s);
	  }

	
}


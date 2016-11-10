package sei.test;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author Mohan K Koganti
Submitted by: Mohan K Koganti	
Consulting Firm: TEK Systems
Contact Info:

1.	Given the following class, write 3 methods that can be used to return an array that has no duplicates.
2.	You should define a method signature that you feel is appropriate to the problem.
3.	We would prefer to see three implementations (one that should take into consideration #4 below) and an explanation of what use-cases are suitable to each implementation 
4.	What if we need to retain the original order?
5.	What are the positives and negatives of your solution?
a.	Can you implement it another way so as to avoid the negatives?
6.	Your solution should be testable and “production ready.”
7.	Your solution should be posted to a public github repo that SEI can clone.

*/
public class DeDup {

	
	public int[] randomIntegers = {1,2,34,34,25,1,45,3,26,85,4,34,86,25,43,2,1,10000,11,16,19,1,18,4,9,3,
            20,17,8,15,6,2,5,10,14,12,13,7,8,9,1,2,15,12,18,10,14,20,17,16,3,6,19,
            13,5,11,4,7,19,16,5,9,12,3,20,7,15,17,10,6,1,8,18,4,14,13,2,11};   
	
	public static void main(String[] args) {
		
		int[] uniqueArray = null;
		long beforeTime = 0;
		long afterTime = 0;
		
		System.out.print("Input Array: ");
		new DeDup().printArray(new DeDup().randomIntegers);
		
		//method 1: using nested for loop
		DeDup deDup1 = new DeDup();
		beforeTime = System.currentTimeMillis();
		uniqueArray = deDup1.deDupNestedFor(deDup1.randomIntegers);
		afterTime = System.currentTimeMillis();
		System.out.print("\nUsing Nested For: ");
		deDup1.printArray(uniqueArray);
		System.out.println("Execution Time: "+(afterTime - beforeTime));
		
		//method 2: using Sort first and remove duplicates
		DeDup deDup2 = new DeDup();
		beforeTime = System.currentTimeMillis();
		uniqueArray = deDup2.deDupSortAndDeDup(deDup2.randomIntegers);
		afterTime = System.currentTimeMillis();
		System.out.print("\nUsing Arrays.sort and then removing duplicates: ");
		deDup2.printArray(uniqueArray);
		System.out.println("Execution Time: "+(afterTime - beforeTime));
		
		//method 3: using LinkedHashSet (Use HashSet if order maintenance is not required as that is more efficient)
		DeDup deDup3 = new DeDup();
		beforeTime = System.currentTimeMillis();
		uniqueArray = deDup3.deDupLinkedHashSet(deDup3.randomIntegers);
		afterTime = System.currentTimeMillis();
		System.out.print("\nUsing LinkedHashSet: ");
		deDup3.printArray(uniqueArray);
		System.out.println("Execution Time: "+(afterTime - beforeTime));
		
		//method 4: using java 8 Streams API (best for functional/ease of implementation/maintainability  but worst performance)
		DeDup deDup4 = new DeDup();
		beforeTime = System.currentTimeMillis();
		uniqueArray = deDup4.deDupStreams(deDup4.randomIntegers);
		afterTime = System.currentTimeMillis();
		System.out.print("\nUsing Java Stream: ");
		deDup4.printArray(uniqueArray);
		System.out.println("Execution Time: "+(afterTime - beforeTime));
		
	}

	
	/* method 1: using nested for loop
	 * This is OK if the use case is for removing duplicates from a small sized list
	 * the logic implemented here does not guarantee the array elements order but is efficient for small lists with no extra memory footprint
	 * time complexity O(n^2) and no space complexity
	 */
	private int[] deDupNestedFor(int[] input)
	{
		//To determine the array size
		int arraySize = input.length;
		for(int i=0;i<arraySize;i++) {
			for(int j=i+1;j<arraySize;j++) {
				if(input[i]==input[j])
				{
					//replace the duplicate element with the latest unique element
					input[j]=input[arraySize-1];
					//adjust the unique array size to reflect the removal of duplicate
					arraySize--;
					j--;
				}
			}
		}
		return Arrays.copyOf(input, arraySize);
	}
	
	/* method 2: using Sort and then remove duplicates
	 * This is a better solution if the use case doesn't care about maintaining the Order
	 * The Arrays Collection utility was used here to perform the sort, but if the use case defines the input to be of a fixed range then radix sort would work best
	 * time complexity O(n log n + n) and space complexity O(n)
	 */
	private int[] deDupSortAndDeDup(int[] input)
	{
		//Used Arrays.sort here which internally uses dual pivot quicksort on primitive arrays
		//for guaranteed performance of O(n log n) the primitive array can be converted to Integer Array
		//in which case the Arrays.sort uses merge sort guaranteeing the O(n log n) time complexity
		//Since the array we are working with is very small just performing the sort on primitive array itself
		Arrays.sort(input);
		
		//now remove duplicates
		int[] output=new int[input.length];
		output[0]=input[0];
		int k=1;
		for(int i=0;i<input.length-1;i++) {
			if(input[i+1]>input[i])
			{
				output[k]=input[i+1];
				k++;
			}
		}
		return Arrays.copyOf(output, k);
	}
	
	/* method 3: using LinkedHashSet
	 * This is Best if the use case is for removing duplicates while maintaining Order of the elements and efficiently for large data sets
	 * Best solution if the use case requires search functionality on the result set.
	 * a TreeSet can be used as well
	 * time complexity O(n) and space complexity O(n)
	 */
	private int[] deDupLinkedHashSet(int[] input)
	{
		Set<Integer> set = new LinkedHashSet<Integer>();
		for(int current:input)
			set.add(current);
		
		//since we need the primitives array 
		input = new int[set.size()];
		int i = 0;
		for (Integer val : set) input[i++] = val;
		
		return input;
	}
	
	/* method 4: using Streams API from Java 8
	 * best for functional/ease of implementation/maintainability  but worst performance
	 */
	private int[] deDupStreams(int[] input)
	{
		return Arrays.stream(input).distinct().toArray();
	}
	
	/*
	 * Method to print the output
	 */
	private void printArray(int[] uniqueArray) {
		for (int i=0;i<uniqueArray.length;i++){
            System.out.print(uniqueArray[i]+" ");
		}
		System.out.println();
	}
}

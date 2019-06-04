package Set;
import java.io.*;
import java.util.ArrayList;


/**
 * The class uses two arrays to implement a set.  The smaller array has size equal to the square root of the larger 
 * array.  When elements are added, they are added to the smaller array. If the smaller array fills up, the two are
 * merged.  Each array is kept sorted with insertion sort.  Keys within the set are searched for by applying a binary search across
 * both arrays.
 * @author samar
 *
 */
public class Set {
	
	private int[] smallArray;
	private int[] largeArray;
	// The pointer to the tail of the data in the small array
	private int tail;
	// Keeps track of the total cost of all insertions
	private int insertionCost;
	// Stores the cost of the most recent search operation
	private int searchCost;
	
	/**
	 * The constructor which creates the set data structure
	 * and initializes it with the input data
	 * @param data - the initial data to be loaded into set
	 */
	public Set(int[] data) {		
		
		insertionCost = 0;
		searchCost = 0;
		smallArray = new int[1];
		largeArray = new int[0];
		tail = -1;
		
		// Inserting the input data into the set
		for(int x:data) {
			insert(x);
		}
	}

	
	/** 
	 * The method insert inserts a new data entry into the set.  By default, it directly inserts the data point
	 * into the small array and uses insertion sort to place it in the correct location.  If the small array is
	 * full however, the small array is merged with the large array with the new large array sorted.  A new small
	 * array is created with a size equal to the square root of the new large array.  Then, the new entry is added
	 * to the new small array.
	 * @param entry the data entry to be inserted into the set
	 */
	public void insert(int entry) {
		
		// Merges the small and large array into new array if the small array is full and resets tail pointer
		if (isSmallFull()) {
			largeArray = merge();
			tail = -1;
			int newSize = (int) Math.sqrt(largeArray.length);
			smallArray = new int[newSize];
		}
		
		
		// Increments tail pointer and inserts data entry into new tail location
		tail++;
		
		// Uses insertion sort to relocate new entry in correct position to ensure array remains sorted
		int loc = tail;
		while (loc > 0 && smallArray[loc - 1] > entry) {
			
			smallArray[loc] = smallArray[loc - 1];
			insertionCost++;
			loc--;
		}
		
		smallArray[loc] = entry;
		
	}
	
	/**
	 * Merge combines two sorted arrays into new array, keeping the data sorted.
	 * @return merged sorted array
	 */
	private int[] merge() {
		
		// Creates new array with size equal to sum of old arrays
		int largeSize = largeArray.length;
		int smallSize = smallArray.length;
		int[] newArray = new int[smallSize + largeSize];
		
		// Sets index pointers for input array and output array
		int smallIndex = 0;
		int largeIndex = 0;
		int newIndex = 0;
		
		// Merges the two old arrays into the new array such that the new array is sorted by comparing elements in each
		while (smallIndex < smallSize && largeIndex < largeSize) {
			
			if (smallArray[smallIndex] < largeArray[largeIndex]) {
				newArray[newIndex] = smallArray[smallIndex];
				insertionCost++;
				smallIndex++;
			}else {
				newArray[newIndex] = largeArray[largeIndex];
				insertionCost++;
				largeIndex++;
			}
			newIndex++;
		}
		
		// Inserts what's left in the small array into the new array
		while (smallIndex < smallSize) {
			newArray[newIndex] = smallArray[smallIndex];
			insertionCost++;
			newIndex++;
			smallIndex++;
		}
		
		// Inserts what's left in the large array into the new array
		while (largeIndex < largeSize) {
			newArray[newIndex] = largeArray[largeIndex];
			insertionCost++;
			newIndex++;
			largeIndex++;
		}
		
		return newArray;
	}
	
	/**
	 * Search first searches the large array for the key, and then searches the small array 
	 * for the key if it wasn't in the large array. It returns the location of the key if found.
	 * 
	 * @param key integer to be searched for in the set
	 * @return location of the key. 0th index value is 0 for small array and 1 for large array.  1st index value is the index location
	 * within the array
	 */
	public int[] search(int key) {
		
		// Reset the search cost
		searchCost = 0;
		
		// Check if key is within bounds of large array, search for key if it is
		if(largeArray[0] <= key && largeArray[largeArray.length - 1] >= key) {
			
			int index = binarySearch(largeArray, key, 0, largeArray.length - 1);
			
			// Return key location if found in large array
			if (index != -1) {
				int[] location = {1, index};
				return location;
			}
		}
			
		// Check if key is within bounds of small array, search for key if it is
		if (smallArray[0] <= key && smallArray[tail] >= key) {
			
			int index = binarySearch(smallArray, key, 0, tail);
			
			// Return key location if found in small array
			if (index != -1) {
				int[] location = {0, index};
				return location;
			}
			
		}
		
		//key was not found in either array
		int[] notFound = {-1, -1};
		return notFound;
	}
	
	
	
	/**
	 * binarySearch searches an integer array for a key and returns the index of the key.  If the key is not found
	 * it returns -1.
	 * @param data the integer array to be searched
	 * @param key the value to be searched for
	 * @param left the left index of the search area
	 * @param right the right index of the search area
	 * @return the index of key location, or -1 if key is not int data
	 */
	private int binarySearch(int[] data, int key, int left, int right) {
		
		searchCost++;
		
		if (right >= left) {
			
			// Find midpoint
			int mid = (left + right)/2;
			
			// Check if midpoint is key
			if (data[mid] == key) {
				// Return index of key if found
				return mid;
			} else if(data[mid] > key) {
				return binarySearch(data, key, left, mid - 1);
			} else {
				return binarySearch(data, key, mid + 1, right);
			}
			
		}
		
		// If value is not found, return -1
		return -1;
		
	}
	
	
	/**
	 * getSize returns the total elements in the set, the sum of the elements in each array.
	 * @return the total elements in the set, which is the sum of the elements in each array.
	 */
	public int getSize() {
		return (tail + 1 + largeArray.length);
	}
	
	/**
	 * Checks to see if the small array is full
	 * @return whether or not the small array is full
	 */
	private boolean isSmallFull() {
		
		// Checks to see if tail is located at the last index of small array
		return (tail == (smallArray.length - 1));
		
	}
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		
		// Read input text file into array
		String fileName = args[0];
		FileInputStream in = new FileInputStream(fileName);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		
		// Insert inputs into dynamic arraylist
		String inString;
		ArrayList<Integer> inList = new ArrayList<Integer>();
		while((inString = br.readLine()) != null) {
			inList.add(Integer.parseInt(inString));
		}
		br.close();
		
		// Copy array list into array
		int[] input = new int[inList.size()];
		for (int i = 0; i < inList.size(); i++) {
			input[i] = inList.get(i);
		}
		
		// 1st number in random order array to later search for to calculate search cost when key exists
		int existKey = input[0];
		
		/* 
		 * The value -3 was excluded from randomly ordered test sets but chosen such that is within between
		 * the max and min of both arrays to illustrate a worst case search. For descending order sets,
		 * it costs
		 */
		int worstKey = -3;
		
		// Initialize Set data structure with input array
		Set testSet = new Set(input);
		
        // Creates outputFile
        File outputFile = new File(args[1]);
        PrintWriter output = new PrintWriter(new FileWriter(outputFile));
        
        // Writing insertion cost results to output file
        output.println("The total cost of inserting " + testSet.getSize() + " elements is "
        		+ testSet.insertionCost);
        
        output.println("This results in an ammortized cost for each insertion of " +
        		testSet.insertionCost/testSet.getSize() + System.getProperty("line.separator"));
        
		// Search for random int in data that exists
		int[] location = testSet.search(existKey);
		int searchCost = testSet.searchCost;
		
		// From location array, gather information about which array key was found
        String arrayLoc;
		if (location[0] == 0) {
			arrayLoc = "Small Array";
		} else if (location[0] == 1) {
			arrayLoc = "Large Array";
		} else {
			arrayLoc = "not found";
		}
        
        // Writing Search Costs to file
		output.println("Key = " + existKey + " was found in the " + arrayLoc + " at index " + location[1]);
		output.println("The search cost " + searchCost + System.getProperty("line.separator"));
        
		// Search for int that does not exist in Set to find worst case
		int[] worstLocation = testSet.search(worstKey);
		int worstSearchCost = testSet.searchCost;
		
		// From location array, gather information about which array key was found
        String worstArrayLoc;
		if (worstLocation[0] == 0) {
			worstArrayLoc = "Small Array";
		} else if (worstLocation[0] == 1) {
			worstArrayLoc = "Large Array";
		} else {
			worstArrayLoc = "not found";
		}
        
        // Writing Search Costs to file
		output.println("Key = " + worstKey + " was " + worstArrayLoc);
		output.println("The search cost " + worstSearchCost + System.getProperty("line.separator"));
        
		// Writes large array to file
        output.println("Large Array:");
		for (int i = 0; i < testSet.largeArray.length; i++) {
			output.println(testSet.largeArray[i]);
		}
		
		// Writes small array to file
        output.println(System.getProperty("line.separator") + "Small Array:");
		for (int i = 0; i <= testSet.tail; i++) {
			output.println(testSet.smallArray[i]);
		}
		
        output.close();
        
	}
	
}

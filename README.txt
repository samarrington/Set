Developed with Java Version JDK 1.8 in the Eclipse IDE

Set.java is a data structure that implements a Set with two arrays. The smaller array has size equal to the square root of the larger 
array.  When elements are added, they are added to the smaller array. If the smaller array fills up, the two are
merged.  Each array is kept sorted with insertion sort.  Keys within the set are searched for by applying a binary search across
both arrays. It includes a main class used to test the stucture.

Time Efficiency:
* The ammortized cost of adding an element is O(n^1/2)
* Searching cost O(lg(n))

Arguments:
	Valid arguments go as follows:
	arg[0] - *.txt file of integers all in one column
	arg[1] - *.txt file to output results

Input Files:
input_100.txt - 100 random unordered integers ranging from -100 to 100
input_10000.txt - 10000 random unordered integers ranging from -10000 to 10000
input_rev10000.txt - 10000 reverse ordered integers ranging from 1 to 10000
input_rev20000.txt - 20000 reverse ordered integers ranging from 1 to 20000

Output Files:
All output files include the following:
* measured costs of inserting all the data into the set
* measured costs of searching for key values
* final state of large and small array

output_100.txt - output for input_100.txt
output_10000.txt - output input_10000.txt
output_rev10000.txt - output for input_rev10000.txt
output_rev20000.txt - output for input_rev20000.txt

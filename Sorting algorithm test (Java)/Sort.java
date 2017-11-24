/*****************************************************/
/***     Initial Author: Jason Steggles 20/09/17   ***/
/***     Extended by: Julius Garalevicius 03/11/17 ***/
/*****************************************************/

import java.io.*;
import java.text.*;
import java.util.*;

public class Sort {

/** Array of integers to sort **/
private int[] A;
	
/** Size of the array **/
private int size;

/** Number of elements actually used in array **/
private int usedSize;

/** Global variables for counting sort comparisons **/
public int compIS; /** Global comparison count for Insertion Sort **/
public int compQS; /** Global comparison count for Quicksort **/
public int compNewS; /** Global comparison count for new sort **/

/*****************/
/** Constructor **/
/*****************/
public Sort(int max)
{
    /** Initialiase global sort count variables **/
    compIS = 0;
    compQS = 0;
    compNewS = 0;
    
    /** Initialise size variables **/
    usedSize = 0;
    size = max;
    
    /** Create Array of Integers **/
    A = new int[size];
}

/*********************************************/
/*** Read a file of integers into an array ***/
/*********************************************/
public void readIn(String file)
{
   try
   {
       /** Initialise loop variable **/
       usedSize = 0;
       
       /** Set up file for reading **/
       FileReader reader = new FileReader(file);
       Scanner in = new Scanner(reader);
       
       /** Loop round reading in data while array not full **/
       while(in.hasNextInt() && (usedSize < size))
       {
           A[usedSize] = in.nextInt();
           usedSize++;
       }
       
    }
    catch (IOException e)
    {
       System.out.println("Error processing file " + file);
    }
}

/**********************/
/*** Display array  ***/
/**********************/
public void display(int line, String header)
{
    /*** Integer Formatter - three digits ***/
    NumberFormat FI = NumberFormat.getInstance();
    FI.setMinimumIntegerDigits(3);

    /** Print header string **/
    System.out.print("\n"+header);

    /** Display array data **/
    for (int i=0;i<usedSize;i++)
    {
        /** Check if new line is needed **/
        if (i%line == 0) 
        { 
            System.out.println(); 
        }
        
        /** Display an array element **/
        System.out.print(FI.format(A[i])+" ");
    }
}
/****/
public void insertion(){
	for (int i=1; i<usedSize; i++){
		int key = A[i];
		int j = i;
		while(j>0 && key<A[j-1]){
			A[j]=A[j-1];
			j--;
			// key is compared to A[j-1] (ignoring occasions where j>0 returns false) counter needs to be increased
			compIS++;
		}
		// last comparison evaluated to false, still need to increment the counter
		compIS++;
		A[j]=key;
	}
}
/**Sorts the array by splitting it into smaller arrays and partitioning them**/
private void quick(int L, int R){
	if (R>L) {
		int pivot = partition(L, R);
		quick(L, pivot-1);
		quick(pivot+1, R);
	}
}
public void quickSort(){
	quick(0, usedSize-1);
}
/**Partitions a portion of array so that every element to the left of the pivot
 *  is less than the pivot itself and every element to the right is bigger than or equal to the pivot**/
private int partition(int L, int R){
	int pivotValue = A[R];
	int leftPointer = L;
	int rightPointer = R;
	while (leftPointer<rightPointer){
		while (A[leftPointer]<pivotValue){
			leftPointer++;
			compQS++;
		}
		compQS++; // This comparison is needed because if this line is reached it means last evaluation returned false
		while (A[rightPointer]>=pivotValue && rightPointer>L){
			rightPointer--;
			compQS++;
		}
		compQS++; // Same as above
		if (leftPointer<rightPointer)
			swap(leftPointer, rightPointer);
	}
	swap(leftPointer, R);
	return leftPointer;
}
/**Swaps two integers**/
private void swap(int a, int b){
	int temp = A[a];
	A[a]=A[b];
	A[b]=temp;
}
/**Sorts an array by finding the minimum value in the array and inserting it into current position and then advancing the current position pointer**/
public void newSort(){
	int position = 0;
	while (position<usedSize){
		int min=findMinFrom(position);
		for (int i=position; i<usedSize; i++){
			if (A[i]==min){
				swap(i, position);
				position++;
			}
			// A[i}==min compares min to array element, which means counter needs to be increased
			compNewS++;
		}
	}
}
/**Iterates through elements in the array finding the minimum**/
private int findMinFrom(int position){
	int min = A[position];
	for (int i = position+1; i<usedSize; i++){
		if (A[i]<min){
			min=A[i];
		}
		// Array elements A[i] compared to min, counter needs to be increased
		compNewS++;
	}
	return min;
}
} /** End of Sort Class **/
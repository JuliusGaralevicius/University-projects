import java.util.InputMismatchException;
import java.util.Scanner;

/*************************************************/
/*** Simple test class for Sort class          ***/
/***                                           ***/
/*** Author: Julius Garalevicius    03/11/2017 ***/
/*************************************************/


public class TestSort
{
	static String[] testFileNames = {"test1.txt", "test2.txt", "test3.txt", "test4.txt", "test5.txt"};
	
	public static void main(String[] args) 
    {

		int selection = getSelection();
		if (selection == 1)
			test1();
		else
			test2();
    }
	// Asks which test user wants to perform (input 1 for test1, 2 for test2)
	private static int getSelection(){
		int selection = 0;
		Scanner sc = new Scanner(System.in);
		int totalSelections=2;
		while (true){
			System.out.println("Select test: \n1. Test1\n2. Test2");
			if (sc.hasNextInt()){
				selection = sc.nextInt();
				if (selection>0 && selection <= totalSelections){
					return selection;
				}
			}
			else {
				sc.next();
			}
		}
	}
	// Tests files test1 to test4 by doing quicksort and insertion sort, prints out comparison counts and sorted arrays
	private static void test1(){
		for (int i =0; i<testFileNames.length-1; i++){
			Sort sort = new Sort(100);
			System.out.println("\n---------------------------------Test " +(i+1) + "---------------------------------");
			sort.readIn(testFileNames[i]);
			sort.insertion();
			System.out.print("\nInsertion sort comparison count: " +sort.compIS);
			sort.display(20, "Insertion sort final array: ");
			
			sort.readIn(testFileNames[i]);
			sort.quickSort();
			System.out.print("\n\nQuick sort comparison count: " +sort.compQS);
			sort.display(20, "Quick sort final array: ");
			
		}
	}
	// Tests files test3-test5 for newsort, test5 for insertion sort aswell, prints out comparison counts and sorted arrays
	private static void test2(){
		for (int i =2; i<testFileNames.length; i++){
			Sort sort = new Sort(100);
			System.out.println("\n---------------------------------Test " +(i+1) + "---------------------------------");
			sort.readIn(testFileNames[i]);
			sort.newSort();
			System.out.print("\nNew sort comparison count: " +sort.compNewS);
			sort.display(20, "New sort final array: ");
		}
		Sort sort = new Sort(100);
		sort.readIn(testFileNames[4]);
		sort.insertion();
		System.out.print("\n\nInsertion sort comparison count: " +sort.compIS);
		sort.display(20, "Insertion sort final array: ");
	}
} /** End of Test class **/
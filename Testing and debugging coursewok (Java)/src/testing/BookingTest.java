package testing;

import java.util.Date;
import java.util.ArrayList;
import project1.Customer;
import project1.Delivery;
import project1.ShowBooking;
import project1.Show;
import project1.Ticket;

/*
 * Test class
 * 
 * @author Jennifer D. Warrender (njw125)
 * @author Joe Bloggs (b1023456)
 * 
 */

public class BookingTest {
	
	private static Show phantom = new Show("The Phantom of the Lecture Theatre", false, new Date());
	private static Show AnimalFarm = new Show("Animal farm", true, new Date());
	
	private static ShowBooking populateBooking(){
		// 
		ShowBooking booking = (Math.random() > 0.5) ? new ShowBooking(phantom): new ShowBooking(AnimalFarm);
		for (Ticket t :Ticket.values()){
			for (int i=0; i<((int)(Math.random()*5)); i++){
				booking.addTicket(t);
			}
		}
		return booking;
	}
	private final static void printCustomers(ArrayList<Customer> a) {
		for (Customer b: a){
			int totalAdult = 0;
			int totalOther = 0;
			for (int i = 0; i<b.getBookings().size(); i++){
				System.out.println(b.getBookings().get(i));
				System.out.println("--------------------");
				totalAdult +=b.getBookings().get(i).getNumOfTicket(Ticket.ADULT);
				totalOther +=b.getBookings().get(i).getNumOfTicket(Ticket.CHILD)+b.getBookings().get(i).getNumOfTicket(Ticket.SENIOR)+
						b.getBookings().get(i).getNumOfTicket(Ticket.STUDENT);
			}
			System.out.println("Total Adult tickets: " +totalAdult);
			System.out.println("Total Other tickets: " +totalOther);
			System.out.println("Deduction price: " +b.getDeductionPrice());
			System.out.println("Grand Total : " +b.getGrandTotal());
			System.out.println("----------------------------------------------");
		}
	}
	private static void testUsual(int nBookings){
		// testing "happy path" of users in the system
		Customer a = new Customer("Joe Bloggs", "Claremont Tower, Newcastle University, Newcastle-upon-Tyne, NE1 7RY",
				Delivery.POST);
		Customer b = new Customer("Boe Jloggs", "Claremont Tower, Newcastle University, Newcastle-upon-Tyne, NE1 7RY",
				Delivery.COLLECTION);
		ArrayList <Customer> arr= new ArrayList<Customer>();
		for (int i=0; i<nBookings; i++){
			a.addShowBooking(populateBooking());
			b.addShowBooking(populateBooking());
		}
		arr.add(a);
		arr.add(b);
		printCustomers(arr);
	}
	private static void testOverflow(){
		// method to book more tickets than there are seats
		testUsual(1000);
	}
	private static void testBookChild(){
		// method to test if Child ticket can be booked for adult show
		Customer a = new Customer("Joe Bloggs", "Claremont Tower, Newcastle University, Newcastle-upon-Tyne, NE1 7RY",
				Delivery.POST);
		ArrayList <Customer> arr= new ArrayList<Customer>();
		arr.add(a);
		
		a.addShowBooking(new ShowBooking(phantom));
		a.getBookings().get(0).addTicket(Ticket.ADULT);
		a.getBookings().get(0).addTicket(Ticket.SENIOR);
		a.getBookings().get(0).addTicket(Ticket.STUDENT);
		a.getBookings().get(0).addTicket(Ticket.CHILD);
		printCustomers(arr);
	}
	private static void testThreeForTwo(){
		// method for testing 342 deal
		Customer a = new Customer("Joe Bloggs", "Claremont Tower, Newcastle University, Newcastle-upon-Tyne, NE1 7RY",
				Delivery.POST);
		ArrayList <Customer> arr= new ArrayList<Customer>();
		arr.add(a);
		
		a.addShowBooking(new ShowBooking(phantom));
		a.addShowBooking(new ShowBooking(AnimalFarm));
		a.getBookings().get(0).addTicket(Ticket.ADULT);
		a.getBookings().get(0).addTicket(Ticket.ADULT);
		a.getBookings().get(1).addTicket(Ticket.ADULT);
		printCustomers(arr);
		a.getBookings().get(0).removeTicket(Ticket.ADULT);
		printCustomers(arr);
	}
	public static void main(String[] args) {
		
		//testUsual(10);
		//testOverflow();
		//testBookChild();
		//testThreeForTwo();
		
	}

}

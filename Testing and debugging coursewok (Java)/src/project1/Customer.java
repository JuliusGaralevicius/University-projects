package project1;

import java.util.ArrayList;

/*
 * This class represents a customer
 * 
 * @author Jennifer D. Warrender (njw125)
 * @author Joe Bloggs (b1023456)
 * 
 */
public class Customer {

	// constants
	// public static final double TRANSACTION_FEE = 2.25;
	private static final double TRANSACTION_FEE = 2.25;
	private static final double POSTAGE_FEE = 2.5;
	
	//  static final double COLLECTION_FEE = 2.0; **14

	// instance variables
	private String name;
	private String address;
	private Delivery method;
	private ArrayList<ShowBooking> bookings = new ArrayList<ShowBooking>();
	
	// constructor
	public Customer(String name, String address, Delivery method) {
		this.name = name;
		// this.address = name; **6
		this.address = address;
		this.method = method;
	}
	
	// method to return the name
	public String getName() {
		return name;
	}

	// method to return the bookings collection
	public ArrayList<ShowBooking> getBookings() {
		return bookings;
	}

	// method to return the delivery method
	public Delivery getMethod() {
		return method;
	}

	// method to return the address
	public String getAddress() {
		// return name; **7
		return address;
	}

	// add a show booking to the customer's booking
	public boolean addShowBooking(ShowBooking booking) {
		return bookings.add(booking);
	}

	// remove a show from the customer's booking
	public boolean removeShowBooking(ShowBooking booking) {
		return bookings.remove(booking);
	}

	// calculate the total ticket price for that customer
	public double getTicketPrice() {
		// if (bookings.size() == 0) useless code **8
		// return 0;
		
		//int price = 0;
		double price = 0;
		//for (int i = 1; i < bookings.size(); i++) **9
		for (int i = 0; i < bookings.size(); i++)
			price += bookings.get(i).getPrice();
		return price;
	}

	// calculate the total deduction for that customer
	public double getDeductionPrice() {
		// if (bookings.size() == 0) useless code
		//	return 0;

		int counter = 0;
		double price = 0;
		// for each booking collection
		for (int i = 0; i < bookings.size(); i++)
			// and for each tickets collection
			for (int j = 0; j < bookings.get(i).getTickets().size(); j++) {
				// get the ticket object
				Ticket t = getBookings().get(i).getTickets().get(j);
				// if the ticket is an adult ticket
				if (t.equals(Ticket.ADULT)){
				// put check for counter == 3 inside this if because there is no need to check counter if it has not increased **11
					counter++;
					// if the ticket is the 3rd adult ticket
					// if (counter == 3) ** 10 brackets
					if (counter == 3) {
						price += ShowBooking.ADULT_PRICE;
						counter = 0;
					}
				}
			}
		return price;
	}
	
	// return the postage price for that customer
	private double getPostagePrice() {
		if (getMethod().equals(Delivery.POST)){
			// return 2.5; // **12
			return POSTAGE_FEE; 
		}
		return 0;
	}

	// calculate the grand total for that customer
	public double getGrandTotal() {
		if (bookings.size() == 0)
			return 0;
		
		// return TRANSACTION_FEE + getTicketPrice() + getDeductionPrice() + POSTAGE_FEE; **13
		System.out.println(TRANSACTION_FEE);
		System.out.println(getTicketPrice());
		System.out.println(getDeductionPrice());
		System.out.println(getPostagePrice());
		return TRANSACTION_FEE + getTicketPrice() - getDeductionPrice() + getPostagePrice(); 
	}

}

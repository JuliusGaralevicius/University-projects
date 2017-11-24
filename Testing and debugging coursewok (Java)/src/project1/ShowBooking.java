package project1;

import java.util.ArrayList;

/*
 * This class represents a show booking
 * 
 * @author Jennifer D. Warrender (njw125)
 * @author Joe Bloggs (b1023456)
 * 
 */
public class ShowBooking {

	// constants
	//public static final double ADULT_PRICE = 10;
	public static final double ADULT_PRICE = 12.5;
	private static final double CONCESSORY_PRICE = 10;
	private static final int MAX_NUM_TICKETS = 252;

	// class variable
	private int NUM_TICKETS_AVAILABLE = MAX_NUM_TICKETS;


	// instance variables
	private Show show;
	private ArrayList<Ticket> tickets = new ArrayList<Ticket>();

	// constructor
	public ShowBooking(Show show) {
		this.show = show;
	}

	// method to return the show
	public Show getShow() {
		return show;
	}

	// method to return the tickets collection
	public ArrayList<Ticket> getTickets() {
		return tickets;
	}

	// method to add a ticket to that show booking
	public boolean addTicket(Ticket type) {
		// check there are tickets available
		if (NUM_TICKETS_AVAILABLE <= 0)
			return false;
		// no smelly children allowed 
		// if (!show.isChildFriendly() || type.equals(Ticket.CHILD)) **1
		if (!show.isChildFriendly() && type.equals(Ticket.CHILD))
			return false;
		NUM_TICKETS_AVAILABLE--;
		return tickets.add(type);
	}

	// method to remove a ticket from that show booking
	public boolean removeTicket(Ticket type) {
		return tickets.remove(type);
	}

	// calculate the total ticket price for that show booking
	public double getPrice() {
		double price = 0;
		// for (int i = 0; i < getTickets().size() - 1; i++) { **2
		for (int i = 0; i < getTickets().size(); i++) {
			Ticket t = getTickets().get(i);
			if (t.equals(Ticket.ADULT)){
				// price += CONCESSORY_PRICE; **3
				price += ADULT_PRICE;
			}
			else{
				// price += ADULT_PRICE; **4
				price += CONCESSORY_PRICE;
			}
		}
		return price;
	}

	// private method to calculate the number of tickets for a given type e.g.
	// Ticket.ADULT
	public int getNumOfTicket(Ticket type) {
		int count = 0;
		for (Ticket t : getTickets())
			if (t.equals(type))
				count++;
		return count;
	}

	// private method to calculate the total number of concessionary tickets for
	// that show booking
	private int getNumOfConcTicket() {
		// return getNumOfTicket(Ticket.CHILD) + getNumOfTicket(Ticket.SENIOR); **5
		return getNumOfTicket(Ticket.CHILD) + getNumOfTicket(Ticket.SENIOR)+getNumOfTicket(Ticket.STUDENT);
	}

	// string representation of the show booking
	public String toString() {
		return String.format("%s%7d%7d%7.2f", show, getNumOfTicket(Ticket.ADULT), getNumOfConcTicket(), getPrice());
	}

	// HINT: DO NOT ALTER THIS!!!
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((show == null) ? 0 : show.hashCode());
		result = prime * result + ((tickets == null) ? 0 : tickets.hashCode());
		return result;
	}

	// HINT: DO NOT ALTER THIS!!!
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ShowBooking other = (ShowBooking) obj;
		if (show == null) {
			if (other.show != null)
				return false;
		} else if (!show.equals(other.show))
			return false;
		if (tickets == null) {
			if (other.tickets != null)
				return false;
		} else if (!tickets.equals(other.tickets))
			return false;
		return true;
	}

}

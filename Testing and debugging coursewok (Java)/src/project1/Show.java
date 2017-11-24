package project1;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Show {

	private String name;
	private boolean childFriendly;
	// public Date dateAndTime;
	private Date dateAndTime;
	
	public Show(String name, boolean childFriendly, Date dateAndTime) {
		this.name = name;
		this.childFriendly = childFriendly;
		this.dateAndTime = dateAndTime;
	}

	public String getName() {
		return name;
	}

	public boolean isChildFriendly() {
		return childFriendly;
	}

	public Date getDateAndTime() {
		return dateAndTime;
	}

	public String toString() {
		SimpleDateFormat date = new SimpleDateFormat("EEE dd MMM, yyyy");
		SimpleDateFormat time = new SimpleDateFormat("hh:mm");
		String name = getName();
		// if (isChildFriendly())
		if (!isChildFriendly())
			name += " (18+)";
		return String.format("%17s @ %5s%35s", date.format(dateAndTime), time.format(dateAndTime), name);
	}

	// HINT: DO NOT ALTER THIS!!!
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dateAndTime == null) ? 0 : dateAndTime.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + (childFriendly ? 1231 : 1237);
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
		Show other = (Show) obj;
		if (dateAndTime == null) {
			if (other.dateAndTime != null)
				return false;
		} else if (!dateAndTime.equals(other.dateAndTime))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (childFriendly != other.childFriendly)
			return false;
		return true;
	}

}

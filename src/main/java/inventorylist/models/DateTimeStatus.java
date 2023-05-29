package inventorylist.models;

import java.time.LocalDateTime;

import jakarta.persistence.Embeddable;

@Embeddable
public class DateTimeStatus {
	
	private LocalDateTime dateTime;
	
	// Constructors
	public DateTimeStatus() {
		
	}
	
	public DateTimeStatus(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}
	
	// Getters & Setters
	public LocalDateTime getDateTime() {
		return dateTime;
	}
	
	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}
	
}

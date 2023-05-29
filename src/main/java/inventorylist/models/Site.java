package inventorylist.models;

import java.time.LocalDateTime;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostUpdate;

@Entity
public class Site {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column
	private String name;
	
	@Column
	private String comment;
	
	// Embeddables
	@Embedded
	@AttributeOverride(name = "dateTime", column = @Column(name = "timedate_created"))
	private DateTimeStatus creationStatus;

	
	@Embedded
	@AttributeOverride(name = "dateTime", column = @Column(name = "timedate_updated"))
	private DateTimeStatus updateStatus;
	
	// Constructors
	public Site() {
		
	}
	
	public Site(String name, String comment) {
		this.name = name;
		this.comment = comment;
	}
	
	// Gives items a date and a time when they're created or updated
	@PostPersist
	private void postPerist() {
		this.setCreationStatus(new DateTimeStatus(LocalDateTime.now()));
	}
	
	@PostUpdate
	private void postUpdate() {
		this.setUpdateStatus(new DateTimeStatus(LocalDateTime.now()));
	}
	
	// Getters & Setters
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getComment() {
		return comment;
	}
	
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public DateTimeStatus getCreationStatus() {
		return creationStatus;
	}
	
	public void setCreationStatus(DateTimeStatus creationStatus) {
		this.creationStatus = creationStatus;
	}
	
	public DateTimeStatus getUpdateStatus() {
		return updateStatus;
	}
	
	public void setUpdateStatus(DateTimeStatus updateStatus) {
		this.updateStatus = updateStatus;
	}
	
}

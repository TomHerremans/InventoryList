package inventorylist.models;

import java.time.LocalDateTime;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostUpdate;

@Entity
public class Item {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false)
	private String articleCode;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private int quantity;
	
	@Column
	private double value;
	
	@Column
	private String comment;
	
	// Nullable prevents any item inserted from being inserted into the DB without
	// an assigned site.
	@ManyToOne
	@JoinColumn(name="site_id", nullable = false)
	private Site site;
	
	// Embeddables
	@Embedded
	@AttributeOverride(name = "dateTime", column = @Column(name = "timedate_created"))
	private DateTimeStatus creationStatus;

	
	@Embedded
	@AttributeOverride(name = "dateTime", column = @Column(name = "timedate_updated"))
	private DateTimeStatus updateStatus;
	
	// Constructors
	public Item() {
		
	}
	
	public Item(String articleCode, String name, int quantity, double value, String comment, Site site) {
		this.articleCode = articleCode;
		this.name = name;
		this.quantity = quantity;
		this.value = value;
		this.comment = comment;
		this.site = site;
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
	
	public String getArticleCode() {
		return articleCode;
	}
	
	public void setArticleCode(String articleCode) {
		this.articleCode = articleCode;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public double getValue() {
		return value;
	}
	
	public void setValue(double value) {
		this.value = value;
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

	public Site getSite() {
		return site;
	}
}

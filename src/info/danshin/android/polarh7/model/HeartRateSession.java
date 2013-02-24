package info.danshin.android.polarh7.model;

import java.io.Serializable;
import java.util.Date;

public class HeartRateSession implements Serializable {
	private static final long serialVersionUID = -3730543014307045197L;
	
	private Long id;
	private Long userId;
	private String name;
	private String description;
	private long status;
	private Date dateStarted;
	private Date dateEnded;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public long getStatus() {
		return status;
	}
	public void setStatus(long status) {
		this.status = status;
	}
	public Date getDateStarted() {
		return dateStarted;
	}
	public void setDateStarted(Date dateStarted) {
		this.dateStarted = dateStarted;
	}
	public Date getDateEnded() {
		return dateEnded;
	}
	public void setDateEnded(Date dateEnded) {
		this.dateEnded = dateEnded;
	}
	
}

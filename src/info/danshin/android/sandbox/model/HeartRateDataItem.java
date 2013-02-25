package info.danshin.android.sandbox.model;

import java.io.Serializable;
import java.util.Date;

public class HeartRateDataItem implements Serializable {
	private static final long serialVersionUID = -6334894776709518038L;

	Long id;
	Long sessionId;
	private int heartBeatsPerMinute;
	private double rrTime;
	private Date timeStamp;
	
	public HeartRateDataItem() {
		this(0, 0);
	}
	
	public HeartRateDataItem(int heartBeatsPerMinute, int rrTime) {
		this.heartBeatsPerMinute = heartBeatsPerMinute;
		this.rrTime = rrTime;
		timeStamp = new Date();
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public int getHeartBeatsPerMinute() {
		return heartBeatsPerMinute;
	}
	
	public void setHeartBeatsPerMinute(int heartBeatsPerMinute) {
		this.heartBeatsPerMinute = heartBeatsPerMinute;
	}
	
	public double getRrTime() {
		return rrTime;
	}
	
	public void setRrTime(double rrTime) {
		this.rrTime = rrTime;
	}
	
	public Date getTimeStamp() {
		return timeStamp;
	}
	
	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	public Long getSessionId() {
		return sessionId;
	}
	
	public void setSessionId(Long sessionId) {
		this.sessionId = sessionId;
	}
}

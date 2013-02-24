package info.danshin.android.polarh7.util;

import java.io.Serializable;
import java.util.Date;

public class HeartRateDataItem implements Serializable {
	private static final long serialVersionUID = -6334894776709518038L;

	long id;
	private int heartBeatsPerMinute;
	private int rrTime;
	private Date timeStamp;
	
	public HeartRateDataItem() {
		this(0, 0);
	}
	
	public HeartRateDataItem(int heartBeatsPerMinute, int rrTime) {
		this.heartBeatsPerMinute = heartBeatsPerMinute;
		this.rrTime = rrTime;
		timeStamp = new Date();
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public int getHeartBeatsPerMinute() {
		return heartBeatsPerMinute;
	}
	public void setHeartBeatsPerMinute(int heartBeatsPerMinute) {
		this.heartBeatsPerMinute = heartBeatsPerMinute;
	}
	public int getRrTime() {
		return rrTime;
	}
	public void setRrTime(int rrTime) {
		this.rrTime = rrTime;
	}
	public Date getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}
}

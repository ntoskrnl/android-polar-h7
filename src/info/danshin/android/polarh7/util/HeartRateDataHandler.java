package info.danshin.android.polarh7.util;

import info.danshin.android.polarh7.db.HeartRateDataItemDAO;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import android.content.Context;
import android.util.Log;

public class HeartRateDataHandler extends Thread {

	private BlockingQueue<HeartRateDataItem> heartRateDataQueue;
	private HeartRateDataItemDAO hrDAO;

	public HeartRateDataHandler(Context context,
			BlockingQueue<HeartRateDataItem> heartRateQueue) {
		this.heartRateDataQueue = heartRateQueue;
		this.hrDAO = new HeartRateDataItemDAO(context);
	}

	@Override
	public void run() {
		hrDAO.open();
		while (!isInterrupted()) {
			try {
				HeartRateDataItem hrDataItem = heartRateDataQueue.poll(200,
						TimeUnit.MILLISECONDS);
				processHeartRateDataItem(hrDataItem);
			} catch (InterruptedException e) {
				break;
			}
		}
		hrDAO.close();
	}

	protected void processHeartRateDataItem(HeartRateDataItem item) {
		if (item == null) {
			return;
		}
		hrDAO.insertHeartRateDataItem(item);
		Log.i("HeartRateDataHandler",
				"new item added to DB: " + item.getRrTime() + " id = "
						+ item.getId());
	}
}

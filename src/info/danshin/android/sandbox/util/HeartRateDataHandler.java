package info.danshin.android.sandbox.util;

import info.danshin.android.sandbox.db.HeartRateDataItemDAO;
import info.danshin.android.sandbox.db.HeartRateSessionDAO;
import info.danshin.android.sandbox.model.HeartRateDataItem;
import info.danshin.android.sandbox.model.HeartRateSession;

import java.util.Date;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class HeartRateDataHandler extends Thread {
	private static final long MAX_TIME_GAP = 5*1000;

	private BlockingQueue<HeartRateDataItem> heartRateDataQueue;
	private HeartRateDataItemDAO hrDAO;
	private HeartRateSessionDAO sessionDAO;
	private long lastTimeStamp = 0;
	private HeartRateSession currentSession;

	public HeartRateDataHandler(Context context,
			BlockingQueue<HeartRateDataItem> heartRateQueue) {
		this.heartRateDataQueue = heartRateQueue;
		this.hrDAO = new HeartRateDataItemDAO(context);
		this.sessionDAO = new HeartRateSessionDAO(context);
	}

	@Override
	public void run() {
		hrDAO.open();
		sessionDAO.open();
		while (!isInterrupted()) {
			try {
				HeartRateDataItem hrDataItem = heartRateDataQueue.poll(200, TimeUnit.MILLISECONDS);
				processHeartRateDataItem(hrDataItem);
			} catch (InterruptedException e) {
				break;
			}
		}
		hrDAO.close();
		sessionDAO.close();
	}
	
	private void closeSession() {
		if (currentSession == null) {
			return;
		}
		if (Math.abs(System.currentTimeMillis() - lastTimeStamp) > MAX_TIME_GAP) {
			if (currentSession.getDateEnded() != null) {
				return;
			}
			final SQLiteDatabase db = hrDAO.getDatabase();
			db.beginTransaction();
			try {
				currentSession.setDateEnded(new Date());
				sessionDAO.update(currentSession);
				db.setTransactionSuccessful();
				Log.i("HeartRateDataHandler", "Session ended: id = " + currentSession.getId());
			} finally {
				db.endTransaction();
			}
		}
	}

	private void processHeartRateDataItem(HeartRateDataItem item) {
		if (item == null) {
			closeSession();
			return;
		}
		
		final SQLiteDatabase db = hrDAO.getDatabase();
		db.beginTransaction();
		try {
			closeSession();
			if (Math.abs(item.getTimeStamp().getTime() - lastTimeStamp) > MAX_TIME_GAP) {
				HeartRateSession session = new HeartRateSession();
				session.setDateStarted(new Date());
				currentSession = session;
				sessionDAO.insert(currentSession);
				Log.i("HeartRateDataHandler", "new session created: id = " + session.getId());
			}
			lastTimeStamp = item.getTimeStamp().getTime();
			item.setSessionId(currentSession.getId());
			hrDAO.insert(item);
			Log.i("HeartRateDataHandler",
				"new item added to DB: " + item.getRrTime() + " id = " + item.getId());
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}
	}
}

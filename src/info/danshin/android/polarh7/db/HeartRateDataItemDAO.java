package info.danshin.android.polarh7.db;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import info.danshin.android.polarh7.model.HeartRateDataItem;
import info.danshin.android.polarh7.model.HeartRateSession;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class HeartRateDataItemDAO extends BaseDAO<HeartRateDataItem> implements HeartRateDBContract.HeartRateData {
	
	private static final String[] ALL_COLUMNS = new String[] {
		_ID, COLUMN_NAME_SESSION_ID, COLUMN_NAME_BPM, COLUMN_NAME_RR_TIME, COLUMN_NAME_TIME_STAMP
	};
	
	public HeartRateDataItemDAO(Context context) {
		super(context);
	}
	
	@Override
	public HeartRateDataItem insert(HeartRateDataItem item) {
		ContentValues values = new ContentValues();
		values.put(COLUMN_NAME_TIME_STAMP, item.getTimeStamp() == null ? null : item.getTimeStamp().getTime());
		values.put(COLUMN_NAME_BPM, item.getHeartBeatsPerMinute());
		values.put(COLUMN_NAME_RR_TIME, (double)item.getRrTime());
		values.put(COLUMN_NAME_SESSION_ID, item.getSessionId());
		long insertId = getDatabase().insert(TABLE_NAME, null, values);
		if (insertId >= 0) {
			item.setId(insertId);
			return item;
		} else return null;
	}
	
	@Override
	public HeartRateDataItem update(HeartRateDataItem item) {
		ContentValues values = new ContentValues();
		values.put(_ID, item.getId());
		values.put(COLUMN_NAME_TIME_STAMP, item.getTimeStamp() == null ? null : item.getTimeStamp().getTime());
		values.put(COLUMN_NAME_BPM, item.getHeartBeatsPerMinute());
		values.put(COLUMN_NAME_RR_TIME, (double)item.getRrTime());
		values.put(COLUMN_NAME_SESSION_ID, item.getSessionId());	
		int r = getDatabase().update(TABLE_NAME, values, _ID + " = " + item.getId(), null);
		return r > 0 ? item : null;
	}
	
	@Override
	public HeartRateDataItem findById(long id) {
		Cursor cursor = getDatabase().query(TABLE_NAME, ALL_COLUMNS, _ID + " = " + id, null, null, null, null);
		HeartRateDataItem item = null;
		if (cursor.moveToFirst()) {
			item = cursorToHeartRateDataItem(cursor);
		}
		cursor.close();
		return item;
	}
	
	@Override
	public void delete(long id) {
		getDatabase().delete(TABLE_NAME, _ID + " = " + id, null);
	}
	
	public List<HeartRateDataItem> getAllItemsOfSession(Long sessionId) {
		Cursor cursor = getDatabase().query(TABLE_NAME, ALL_COLUMNS, COLUMN_NAME_SESSION_ID + " = " + sessionId, null, null, null, null);
		List<HeartRateDataItem> items = new ArrayList<HeartRateDataItem>();
		while (cursor.moveToNext()) {
			items.add(cursorToHeartRateDataItem(cursor));
		}
		return items;
	}
	
	private HeartRateDataItem cursorToHeartRateDataItem(Cursor cursor) {
		HeartRateDataItem item = new HeartRateDataItem();
		item.setId(cursor.getLong(0));
		item.setSessionId(cursor.getLong(1));
		item.setHeartBeatsPerMinute(cursor.getInt(2));
		item.setRrTime(cursor.getDouble(3));
		if (!cursor.isNull(4)) {
			item.setTimeStamp(new Date(cursor.getLong(4)));
		}
		return item;
	}
}

package info.danshin.android.polarh7.db;

import info.danshin.android.polarh7.util.HeartRateDataItem;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class HeartRateDataItemDAO implements HeartRateDBContract.HeartRateData {
	
	private HeartRateDBHelper dbHelper;
	private SQLiteDatabase database;
	
	public HeartRateDataItemDAO(Context context) {
		this.dbHelper = new HeartRateDBHelper(context);
	}
	
	public void open() {
		this.database = dbHelper.getWritableDatabase();
	}
	
	public void close() {
		dbHelper.close();
	}
	
	public HeartRateDataItem insertHeartRateDataItem(HeartRateDataItem item) {
		ContentValues values = new ContentValues();
		values.put(COLUMN_NAME_TIME_STAMP, item.getTimeStamp().getTime());
		values.put(COLUMN_NAME_BPM, item.getHeartBeatsPerMinute());
		values.put(COLUMN_NAME_RR_TIME, (double)item.getRrTime());
		values.put(COLUMN_NAME_SESSION_ID, (Long) null);
		long insertId = database.insert(TABLE_NAME, null, values);
		item.setId(insertId);
		return item;
	}
	
	private HeartRateDataItem cursorToHeartRateDataItem(Cursor cursor) {
		HeartRateDataItem item = new HeartRateDataItem();
		return item;
	}
}

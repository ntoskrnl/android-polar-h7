package info.danshin.android.sandbox.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class HeartRateDBHelper extends SQLiteOpenHelper implements HeartRateDBContract {

	private static class HOLDER {
		private static HeartRateDBHelper instance;
	}
	
	public static HeartRateDBHelper getInstance(Context ctx) {
		if (HOLDER.instance == null) {
			HOLDER.instance = new HeartRateDBHelper(ctx);
		}
		return HOLDER.instance;
	}
	
	private HeartRateDBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// Create database entities
		db.execSQL(SQL.CREATE_TABLE_SESSIONS);
		db.execSQL(SQL.CREATE_TABLE_HR_DATA);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Upgrade or downgrade the database schema
		db.execSQL("DROP TABLE IF EXISTS " + HeartRateData.TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + Sessions.TABLE_NAME);
		onCreate(db);
	}
	
	@Override
	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		onUpgrade(db, oldVersion, newVersion);
	}

}

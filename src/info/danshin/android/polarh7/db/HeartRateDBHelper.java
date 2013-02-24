package info.danshin.android.polarh7.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class HeartRateDBHelper extends SQLiteOpenHelper implements HeartRateDBContract {

	public HeartRateDBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Create database entities
		db.execSQL(SQL.CREATE_TABLE_SESSIONS);
		db.execSQL(SQL.CREATE_TABLE_HR_DATA);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Upgrade or downgrade the database schema
	}
	
	@Override
	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		onUpgrade(db, oldVersion, newVersion);
	}

}

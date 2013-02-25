package info.danshin.android.sandbox.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public abstract class BaseDAO<T> {
	private SQLiteOpenHelper dbHelper;
	private SQLiteDatabase database;
	
	public BaseDAO(Context context) {
		dbHelper = HeartRateDBHelper.getInstance(context);
	}
	
	public void open() {
		this.setDatabase(dbHelper.getWritableDatabase());
	}
	
	public void close() {
		dbHelper.close();
	}

	public SQLiteDatabase getDatabase() {
		return database;
	}

	public void setDatabase(SQLiteDatabase database) {
		this.database = database;
	}
	
	public abstract T insert(T item);
	public abstract T update(T item);
	public abstract T findById(long id);
	public abstract void delete(long id);
	
}

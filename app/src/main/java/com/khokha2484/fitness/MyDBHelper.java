package com.khokha2484.fitness;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

class MyDbHelper extends SQLiteOpenHelper {

	private static final String DB_NAME = "mydb";
	private static final int DB_VERSION = 1;

	public static final String TABLE_NAME = "student";
	public static final String COL_NAME = "pName";
	public static final String COL_DATE = "pDesc";
	private static final String STRING_CREATE = "CREATE TABLE "+TABLE_NAME+" (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
			+COL_NAME+" TEXT, "+COL_DATE+" VARCHAR);";

	public MyDbHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(STRING_CREATE);
		ContentValues cv = new ContentValues(2);
		cv.put(COL_NAME, "New Entry");
		cv.put(COL_DATE, "New Entry");
		//SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//cv.put(COL_DATE, dateFormat.format(new Date()));
		db.insert(TABLE_NAME, null, cv);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
		onCreate(db);
	}
			
}

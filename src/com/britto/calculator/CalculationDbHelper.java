package com.britto.calculator;

import com.britto.calculator.CalculationContract.CalculationEntry;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CalculationDbHelper extends SQLiteOpenHelper {
	// If you change the database schema, you must increment the database
	// version.
	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "Calculation.db";

	private static final String TEXT_TYPE = " TEXT";
	private static final String REAL_TYPE = " REAL";
	private static final String COMMA_SEP = ",";
	private static final String SQL_CREATE_ENTRIES = "CREATE TABLE "
			+ CalculationEntry.TABLE_NAME + " ("
			+ CalculationEntry.COLUMN_NAME_ID + " INTEGER PRIMARY KEY,"
			+ CalculationEntry.COLUMN_NAME_FIRST_NUMBER + REAL_TYPE + COMMA_SEP
			+ CalculationEntry.COLUMN_NAME_SECOND_NUMBER + REAL_TYPE
			+ COMMA_SEP + CalculationEntry.COLUMN_NAME_LAST_MODIFIED_TIME
			+ TEXT_TYPE + COMMA_SEP + CalculationEntry.COLUMN_NAME_NAME
			+ TEXT_TYPE + COMMA_SEP + CalculationEntry.COLUMN_NAME_OPERATION
			+ TEXT_TYPE + COMMA_SEP + CalculationEntry.COLUMN_NAME_OUTPUT
			+ REAL_TYPE  + " )";
	private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS "
			+ CalculationEntry.TABLE_NAME;

	public CalculationDbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SQL_CREATE_ENTRIES);
	}

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// This database is only a cache for online data, so its upgrade policy
		// is
		// to simply to discard the data and start over
		db.execSQL(SQL_DELETE_ENTRIES);
		onCreate(db);
	}

	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		onUpgrade(db, oldVersion, newVersion);
	}
}

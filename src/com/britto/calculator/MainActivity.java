package com.britto.calculator;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.britto.calculator.R;
import com.britto.calculator.CalculationContract.CalculationEntry;

import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.method.DateTimeKeyListener;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends Activity {

	CalculationDbHelper mDbHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
	/*	ListView  listView = (ListView) findViewById(R.id.list);
		mDbHelper = new CalculationDbHelper(this);
		final ArrayList<String> list = getData();
		
		String[] values = new String[] { "Android List View", 
                "Adapter implementation",
                "Simple List View In Android",
                "Create List View Android", 
                "Android Example", 
                "List View Source Code", 
                "List View Array Adapter", 
                "Android Example List View" 
               };
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_single_choice, values);
		listView.setAdapter(adapter);
		listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		*/
	}
	
	@Override
	public void onResume() {
		super.onResume();
		ListView  listView = (ListView) findViewById(R.id.list);
		mDbHelper = new CalculationDbHelper(this);
		final ArrayList<String> list = getData();
		
		String[] values = new String[] { "Android List View", 
                "Adapter implementation",
                "Simple List View In Android",
                "Create List View Android", 
                "Android Example", 
                "List View Source Code", 
                "List View Array Adapter", 
                "Android Example List View" 
               };
		Log.i("Calculate",String.valueOf(list.size()));
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_single_choice, list);
		listView.setAdapter(adapter);
		listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	
	
	public ArrayList<String> getData() {
		SQLiteDatabase db = mDbHelper.getReadableDatabase();

		// Define a projection that specifies which columns from the database
		// you will actually use after this query.
		String[] projection = { CalculationEntry.COLUMN_NAME_FIRST_NUMBER,
				CalculationEntry.COLUMN_NAME_SECOND_NUMBER,
				CalculationEntry.COLUMN_NAME_OUTPUT,
				CalculationEntry.COLUMN_NAME_OPERATION,
				CalculationEntry.COLUMN_NAME_LAST_MODIFIED_TIME,
				CalculationEntry.COLUMN_NAME_NAME };

		// How you want the results sorted in the resulting Cursor
		String sortOrder = CalculationEntry.COLUMN_NAME_LAST_MODIFIED_TIME
				+ " DESC";

		Cursor cursor = db.query(CalculationEntry.TABLE_NAME, // The table
				projection, // The columns to return
				null, // The columns for the WHERE clause
				null, // The values for the WHERE clause
				null, // don't group the rows
				null, // don't filter by row groups
				sortOrder // The sort order
				);
		
		ArrayList<String> result = new ArrayList<String>();
		if (cursor.getCount() == 0) return result;
		
		cursor.moveToFirst();
		do {
			double firstNumber = cursor
					.getDouble(cursor
							.getColumnIndexOrThrow(CalculationEntry.COLUMN_NAME_FIRST_NUMBER));
			double secondNumber = cursor
					.getDouble(cursor
							.getColumnIndexOrThrow(CalculationEntry.COLUMN_NAME_SECOND_NUMBER));
			double output = cursor
					.getDouble(cursor
							.getColumnIndexOrThrow(CalculationEntry.COLUMN_NAME_OUTPUT));
			String operation = cursor
					.getString(cursor
							.getColumnIndexOrThrow(CalculationEntry.COLUMN_NAME_OPERATION));
			String lastModifiedTime = cursor
					.getString(cursor
							.getColumnIndexOrThrow(CalculationEntry.COLUMN_NAME_LAST_MODIFIED_TIME));
			result.add(String.valueOf(firstNumber) + " " + operation + " "
					+ String.valueOf(secondNumber) + " = "
					+ String.valueOf(output));
		} while (cursor.moveToNext());
		return result;
	}
	
	public void newCalculation(View view) {
		Intent intent = new Intent(this, CalculateActivity.class);
		startActivity(intent);
	}
}

package com.britto.calculator;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import com.britto.calculator.R;
import com.britto.calculator.CalculationContract.CalculationEntry;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

public class CalculateActivity extends Activity {
	public final static String FIRST_NUMBER = "com.britto.calculator.FIRST_NUMBER";
	public final static String SECOND_NUMBER = "com.britto.calculator.SECOND_NUMBER";
	public final static String RESULT = "com.britto.calculator.RESULT";
	CalculationDbHelper mDbHelper;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
			
		setContentView(R.layout.activity_calculate);
		// Show the Up button in the action bar.
		setupActionBar();
	}

	public void calculate(View view) {
		Intent intent = new Intent(this, DisplayMessageActivity.class);
		EditText editText = (EditText) findViewById(R.id.First_Number);
		Double firstNumber = Double.valueOf(editText.getText().toString());
		editText = (EditText) findViewById(R.id.Second_Number);
		Double secondNumber = Double.valueOf(editText.getText().toString());
		Double output;
		String operation = "/";
		if (view.getId() == R.id.ADD) {
			output = firstNumber + secondNumber;
			operation = "+";
		} else if (view.getId() == R.id.SUBTRACT) {
			output = firstNumber - secondNumber;
			operation = "-";
		} else if (view.getId() == R.id.MULTIPLY) {
			output = firstNumber * secondNumber;
			operation = "x";
		} else {
			output = firstNumber / secondNumber;
		}
		intent.putExtra(RESULT, output.toString());
		saveData(firstNumber, secondNumber, output, operation);

		startActivity(intent);
	}
	
	public void saveData(double firstNumber, double secondNumber,
			double output, String operation) {
		// Gets the data repository in write mode
		mDbHelper = new CalculationDbHelper(this);
		SQLiteDatabase db = mDbHelper.getWritableDatabase();

		// Create a new map of values, where column names are the keys
		ContentValues values = new ContentValues();
		values.put(CalculationEntry.COLUMN_NAME_FIRST_NUMBER, firstNumber);
		values.put(CalculationEntry.COLUMN_NAME_SECOND_NUMBER, secondNumber);
		values.put(CalculationEntry.COLUMN_NAME_OUTPUT, output);
		values.put(CalculationEntry.COLUMN_NAME_OPERATION, operation);
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss",
				Locale.US);
		Calendar cal = Calendar.getInstance();
		values.put(CalculationEntry.COLUMN_NAME_LAST_MODIFIED_TIME,
				dateFormat.format(cal.getTime()));

		// Insert the new row, returning the primary key value of the new row
		long newRowId = db.insert(CalculationEntry.TABLE_NAME, null, values);
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.calculate, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}

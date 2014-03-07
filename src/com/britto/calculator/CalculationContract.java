package com.britto.calculator;

import android.provider.BaseColumns;

public final class CalculationContract {
	// To prevent someone from accidentally instantiating the contract class,
	// give it an empty constructor.
	public CalculationContract() {
	}

	/* Inner class that defines the table contents */
	public static abstract class CalculationEntry implements BaseColumns {
		public static final String TABLE_NAME = "calculation";
		public static final String COLUMN_NAME_FIRST_NUMBER = "firstnumber";
		public static final String COLUMN_NAME_SECOND_NUMBER = "secondnumber";
		public static final String COLUMN_NAME_ID = "id";
		public static final String COLUMN_NAME_OPERATION = "operation";
		public static final String COLUMN_NAME_LAST_MODIFIED_TIME = "lastmodifiedtime";
		public static final String COLUMN_NAME_NAME = "name";
		public static final String COLUMN_NAME_OUTPUT = "output";

	}
}

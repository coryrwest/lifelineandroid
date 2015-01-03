package com.lifeline.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.lifeline.objects.Step;

import java.util.Date;

/**
 * Created by cory on 1/2/15.
 */
public final class LifeLineDbHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "lifeline_data";
    public static final int DATABASE_VERSION = 1;

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public LifeLineDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /* Inner class that defines the table contents */
    public static abstract class StepData implements BaseColumns {
        public static final String TABLE_NAME = "steps";
        public static final String COLUMN_NAME_COUNT = "count";
        public static final String COLUMN_NAME_DATE = "date";
    }

    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + StepData.TABLE_NAME + " (" +
                    StepData._ID + " INTEGER PRIMARY KEY," +
                    StepData.COLUMN_NAME_COUNT + " INTEGER" + COMMA_SEP +
                    StepData.COLUMN_NAME_DATE + " DATE" + COMMA_SEP +
                    " )";
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + StepData.TABLE_NAME;

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }


    // DATA METHODS
    public Step GetStepByDate(Date date) {
        // Gets the data repository in write mode
        SQLiteDatabase db = this.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                StepData._ID,
                StepData.COLUMN_NAME_DATE,
                StepData.COLUMN_NAME_COUNT
        };

        String selection = StepData.COLUMN_NAME_DATE;

        String[] selectionArgs = { String.valueOf(date) };

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                StepData.COLUMN_NAME_DATE + " DESC";

        Cursor c = db.query(
                StepData.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        Step step = new Step();
        if (c.getCount() == 0) {
            step.date = date;
            step.count = 0;
            step.isNew = true;
            return step;
        }

        c.moveToFirst();
        int count = c.getInt(
                c.getColumnIndexOrThrow(StepData.COLUMN_NAME_COUNT)
        );
        long id = c.getLong(
                c.getColumnIndexOrThrow(StepData._ID)
        );

        step.date = date;
        step.count = count;
        step.id = id;
        return step;
    }

    public boolean SaveStep(Step step) {
        // Gets the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();

        if (step.isNew) {
            // Create a new map of values, where column names are the keys
            ContentValues values = new ContentValues();
            values.put(StepData.COLUMN_NAME_COUNT, step.count);
            values.put(StepData.COLUMN_NAME_DATE, String.valueOf(step.date));

            // Insert the new row, returning the primary key value of the new row
            long newRowId;
            newRowId = db.insert(
                    StepData.TABLE_NAME,
                    "null",
                    values);

            return newRowId != 0;
        } else {
            // New value for one column
            ContentValues values = new ContentValues();
            values.put(StepData.COLUMN_NAME_COUNT, step.count);

            // Which row to update, based on the ID
            String selection = StepData._ID + " =";
            String[] selectionArgs = { String.valueOf(step.id) };

            int count = db.update(
                    StepData.TABLE_NAME,
                    values,
                    selection,
                    selectionArgs);

            return count == 1;
        }
    }
}

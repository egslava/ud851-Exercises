package com.example.android.waitlist.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class WaitlistDbHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "waitlist.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TAG = "WaitlistDbHelper";

    public WaitlistDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_WAITLIST_TABLE = "CREATE TABLE " + WaitlistContract.WaitlistEntry.TABLE_NAME +
        " ( " +
            WaitlistContract.WaitlistEntry.COLUMN_GUEST_NAME + " VARCHAR(255), " +
            WaitlistContract.WaitlistEntry.COLUMN_PARTY_SIZE + " VARCHAR(255), " +
            WaitlistContract.WaitlistEntry.COLUMN_TIMESTAMP  + " DATETIME " +
        " )";

        Log.d(TAG, "onCreate: ");

        db.execSQL(SQL_CREATE_WAITLIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String SQL_DROP_WAITLIST_TABLE = "DROP TABLE " + WaitlistContract.WaitlistEntry.TABLE_NAME;
        db.execSQL(SQL_DROP_WAITLIST_TABLE);

        onCreate(db);
    }

    // TODO (8) Override the onUpgrade method

        // TODO (9) Inside, execute a drop table query, and then call onCreate to re-create it

}
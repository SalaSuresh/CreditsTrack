package com.syntax.creditstrack;
import java.util.ArrayList;
import java.util.List;
 

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
 
public class DatabaseHandler extends SQLiteOpenHelper {
 
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 101;
 
    // Database Name
    private static final String DATABASE_NAME = "creditsManager";
 
    // Credits table name
    private static final String TABLE_NAME = "credits";
 
    // Credits Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_TRANSACTION_VALUE = "transaction_value";
    private static final String KEY_TRANSACTION_STATUS = "transaction_status";
    private static final String KEY_TRANSACTION_NOTE = "transaction_note";
 
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }
 
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CREDITS_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + KEY_ID + " INTEGER PRIMARY KEY," 
        		+ KEY_NAME + " TEXT NOT NULL,"
                + KEY_TRANSACTION_VALUE + " INTEGER,"
        		+ KEY_TRANSACTION_STATUS + " TEXT NOT NULL,"
                + KEY_TRANSACTION_NOTE + " TEXT NOT NULL)";
        db.execSQL(CREATE_CREDITS_TABLE);
    }
 
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
 
        // Create tables again
        onCreate(db);
    }
 
    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */
 
    // Adding new transaction
    void addTransaction(Credits credits) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, credits.getName()); // Credit Name
        values.put(KEY_TRANSACTION_VALUE, credits.getTransactionValue()); // Credit Transaction Value
        values.put(KEY_TRANSACTION_STATUS, credits.getTransactionStatus()); // Credit Transaction Status
        values.put(KEY_TRANSACTION_NOTE, credits.getTransactionNote()); // Credit Transaction Note
        Log.e("credit", "ContentValue: "+values.getAsString(KEY_NAME));
        Log.e("credit", "ContentValue: "+values.getAsInteger(KEY_TRANSACTION_VALUE));
        Log.e("credit", "ContentValue: "+values.getAsString(KEY_TRANSACTION_STATUS));
        Log.e("credit", "ContentValue: "+values.getAsString(KEY_TRANSACTION_NOTE));
        // Inserting Row
        db.insert(TABLE_NAME, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }
 
    // Getting single credit transaction
    Credits getCreditTransaction(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
 
        Cursor cursor = db.query(TABLE_NAME, new String[] { KEY_ID,
                											KEY_NAME, 
                											KEY_TRANSACTION_VALUE,
                											KEY_TRANSACTION_STATUS,
                                                            KEY_TRANSACTION_NOTE}, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
 
        Credits credits = new Credits(Integer.parseInt(cursor.getString(0)),
                						cursor.getString(1), 
                						Integer.parseInt(cursor.getString(2)),
                						cursor.getString(3),
                                        cursor.getString(4));
        // return credits
        return credits;
    }
 
    // Getting All Credits
    public List<Credits> getAllCredits() {
        List<Credits> creditList = new ArrayList<Credits>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Credits credits = new Credits();
                credits.setID(Integer.parseInt(cursor.getString(0)));
                credits.setName(cursor.getString(1));
                credits.setTransactionValue(Integer.parseInt(cursor.getString(2)));
                credits.setTransactionStatus(cursor.getString(3));
                credits.setTransactionNote(cursor.getString(4));
                // Adding credit to list
                creditList.add(credits);
            } while (cursor.moveToNext());
        }
 
        // return credits list
        return creditList;
    }
    
    // Getting Distinct Credit names
    public List<Credits> getDistinctCreditNames() {
        List<Credits> creditsList = new ArrayList<Credits>();
        // Select All Query
        String selectQuery = "SELECT distinct name FROM " + TABLE_NAME;
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Credits credits = new Credits();
//                contact.setID(Integer.parseInt(cursor.getString(0)));
                credits.setName(cursor.getString(0));
//                contact.setPhoneNumber(cursor.getString(2));
                Log.e("test", "name: "+cursor.getString(0));
                // Adding credits to list
                creditsList.add(credits);
            } while (cursor.moveToNext());
        }
 
        // return credits name list
        return creditsList;
    }
 
    // Updating single Transaction
    public int updateContact(Credits credits) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, credits.getName());
        values.put(KEY_TRANSACTION_VALUE, credits.getTransactionValue());
        values.put(KEY_TRANSACTION_STATUS, credits.getTransactionStatus());
        values.put(KEY_TRANSACTION_NOTE, credits.getTransactionNote());
 
        // updating row
        return db.update(TABLE_NAME, values, KEY_ID + " = ?",
                new String[] { String.valueOf(credits.getID()) });
    }
 
    // Deleting single transaction
    public void deleteContact(Credits credits) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_ID + " = ?",
                new String[] { String.valueOf(credits.getID()) });
        db.close();
    }
 
    // Getting Transactions Count
    public int getContactsCount() {
        int count = 0;
        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        if(cursor != null && !cursor.isClosed()){
            count = cursor.getCount();
            cursor.close();
        }

        return count;
    }

    //Getting the user balance
    public int getUserBalance(){
        int sum = 0;
        String selectQuery = "SELECT  * FROM " + TABLE_NAME+" WHERE "+KEY_TRANSACTION_STATUS+" = \"notcleared\"";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Credits credits = new Credits();
                credits.setID(Integer.parseInt(cursor.getString(0)));
                credits.setName(cursor.getString(1));
                credits.setTransactionValue(Integer.parseInt(cursor.getString(2)));
                credits.setTransactionStatus(cursor.getString(3));
                credits.setTransactionNote(cursor.getString(4));
                // Adding credit to list
                sum += Integer.parseInt(cursor.getString(2));
            } while (cursor.moveToNext());
        }

        return sum;
    }

    // Getting Distinct Credits
    public List<Credits> getDistinctCreditsSum() {
        List<Credits> distinctCreditsSum = new ArrayList<Credits>();
        String selectQuery = "SELECT distinct name FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Log.e("test", "name: "+cursor.getString(0));

                int distinctSum = 0;
                String distinctCreditsQuery = "SELECT  * FROM " + TABLE_NAME+" WHERE "+KEY_NAME +"=\""+ cursor.getString(0)+"\"";
                SQLiteDatabase distinct_db = this.getWritableDatabase();
                Cursor distinct_cursor = distinct_db.rawQuery(distinctCreditsQuery, null);
                if (distinct_cursor.moveToFirst()) {
                    do {
                        distinctSum += Integer.parseInt(distinct_cursor.getString(2));
                    } while (distinct_cursor.moveToNext());
                }

                Credits credits = new Credits();
                credits.setName(cursor.getString(0));
                credits.setTransactionValue(distinctSum);
                distinctCreditsSum.add(credits);

            } while (cursor.moveToNext());


        }

        return distinctCreditsSum;
    }

    public List<Credits> getDistinctCredits(String name) {
        List<Credits> distinctCredits = new ArrayList<Credits>();
        String distinctCreditsQuery = "SELECT  * FROM " + TABLE_NAME+" WHERE "+KEY_NAME +"=\""+ name +"\"";
        SQLiteDatabase distinct_db = this.getWritableDatabase();
        Cursor distinct_cursor = distinct_db.rawQuery(distinctCreditsQuery, null);
        if (distinct_cursor.moveToFirst()) {
            do {
                Credits credits = new Credits();
                credits.setName(distinct_cursor.getString(1));
                credits.setTransactionValue(Integer.parseInt(distinct_cursor.getString(2)));
                distinctCredits.add(credits);
            } while (distinct_cursor.moveToNext());
        }
        return distinctCredits;
    }


 
}
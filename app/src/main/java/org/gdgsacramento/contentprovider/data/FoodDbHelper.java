package org.gdgsacramento.contentprovider.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.gdgsacramento.contentprovider.data.FoodContract.FoodEntry;

/**
 * Created by tamsler on 12/29/14.
 */
public class FoodDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "food.db";

    public FoodDbHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_FOOD_TABLE = "CREATE TABLE " + FoodEntry.TABLE_NAME + " (" +
                FoodEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                FoodEntry.COLUMN_FOOD_NAME + " TEXT UNIQUE NOT NULL," +
                FoodEntry.COLUMN_FOOD_DESCRIPTION + " TEXT" +
                ");";

        db.execSQL(SQL_CREATE_FOOD_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + FoodEntry.TABLE_NAME);
        onCreate(db);
    }
}

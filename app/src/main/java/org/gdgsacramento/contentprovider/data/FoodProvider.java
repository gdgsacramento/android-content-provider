package org.gdgsacramento.contentprovider.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import org.gdgsacramento.contentprovider.data.FoodContract.FoodEntry;

/**
 * Created by tamsler on 12/29/14.
 */
public class FoodProvider extends ContentProvider {

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private FoodDbHelper mFoodDbHelper;
    private static final int FOOD = 100;
    private static final SQLiteQueryBuilder sFoodQueryBuilder;

    static {

        sFoodQueryBuilder = new SQLiteQueryBuilder();
        sFoodQueryBuilder.setTables(FoodEntry.TABLE_NAME);
    }

    @Override
    public boolean onCreate() {

        mFoodDbHelper = new FoodDbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        Cursor retCursor;

        switch(sUriMatcher.match(uri)) {

            case FOOD:
                retCursor = mFoodDbHelper.getReadableDatabase().query(
                        FoodEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        retCursor.setNotificationUri(getContext().getContentResolver(), uri);

        return retCursor;
    }

    @Override
    public String getType(Uri uri) {

        final int match = sUriMatcher.match(uri);

        switch(match) {

            case FOOD:
                return FoodEntry.CONTENT_TYPE;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        final SQLiteDatabase db = mFoodDbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri = null;

        switch(match) {

            case FOOD:
                long _id = db.insertWithOnConflict(FoodEntry.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_IGNORE);

                if(_id > 0) {

                    returnUri = FoodEntry.buildFoodUri(_id);
                }
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if(null != returnUri) {

            getContext().getContentResolver().notifyChange(uri, null);
        }

        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        final SQLiteDatabase db = mFoodDbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;

        switch(match) {

            case FOOD:
                rowsDeleted = db.delete(FoodEntry.TABLE_NAME, selection, selectionArgs);
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        // NOTE: null deletes all rows
        if (selection == null || rowsDeleted != 0) {

            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        final SQLiteDatabase db = mFoodDbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsUpdated;

        switch(match) {

            case FOOD:

                rowsUpdated = db.update(FoodEntry.TABLE_NAME, values, selection, selectionArgs);
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (rowsUpdated != 0) {

            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsUpdated;
    }

    private static UriMatcher buildUriMatcher() {

        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = FoodContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, FoodContract.PATH_FOOD, FOOD);

        return matcher;
    }
}

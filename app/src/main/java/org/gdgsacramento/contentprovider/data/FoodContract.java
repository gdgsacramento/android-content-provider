package org.gdgsacramento.contentprovider.data;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by tamsler on 12/29/14.
 */
public class FoodContract {

    public static final String CONTENT_AUTHORITY = "org.gdgsacramento.contentprovider";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_FOOD = "food";

    public static final class FoodEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_FOOD).build();

        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_FOOD;

        // Table name
        public static final String TABLE_NAME = "food";

        // Table columns
        public static final String COLUMN_FOOD_ID = FoodEntry._ID;
        public static final String COLUMN_FOOD_NAME = "name";
        public static final String COLUMN_FOOD_DESCRIPTION = "DESCRIPTION";

        // URI
        public static Uri buildFoodUri(long id) {

            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static String getIdFromFoodUri(Uri uri) {

            return uri.getPathSegments().get(1);
        }
    }
}

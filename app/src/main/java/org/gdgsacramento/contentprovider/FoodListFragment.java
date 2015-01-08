package org.gdgsacramento.contentprovider;

import android.app.Fragment;
import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import org.gdgsacramento.contentprovider.data.FoodContract.FoodEntry;

/**
 * Created by tamsler on 12/29/14.
 */
public class FoodListFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private CursorAdapter mCursorAdapter;

    private static final int FOOD_LOADER_ID = 0;

    private static final String[] FOOD_COLUMNS = {
            FoodEntry.TABLE_NAME + "." + FoodEntry.COLUMN_FOOD_ID,
            FoodEntry.COLUMN_FOOD_NAME,
            FoodEntry.COLUMN_FOOD_DESCRIPTION
    };

    public static final int COL_FOOD_ID = 0;
    public static final int COL_FOOD_NAME = 1;
    public static final int COL_FOOD_DESCRIPTION = 2;

    public static FoodListFragment newInstance() {

        FoodListFragment fragment = new FoodListFragment();
        return fragment;
    }

    public FoodListFragment() { }

    @Override
    public void onResume() {

        super.onResume();

        getLoaderManager().restartLoader(FOOD_LOADER_ID, null, this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        mCursorAdapter = new CursorAdapter(getActivity(), null, 0) {

            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {

                View view = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false);

                return view;
            }

            @Override
            public void bindView(View view, Context context, Cursor cursor) {

                String foodName = cursor.getString(FoodListFragment.COL_FOOD_NAME);
                TextView textView = (TextView) view.findViewById(android.R.id.text1);
                textView.setText(foodName);
            }
        };

        setListAdapter(mCursorAdapter);

        getLoaderManager().initLoader(FOOD_LOADER_ID, null, this);
    }

    // LoaderManager
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        return new CursorLoader(
                getActivity(),
                FoodEntry.CONTENT_URI,
                FOOD_COLUMNS,
                null,
                null,
                FoodEntry.COLUMN_FOOD_NAME + " ASC"
        );
    }

    // LoaderManager
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        mCursorAdapter.swapCursor(data);
    }

    // LoaderManager
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

        mCursorAdapter.swapCursor(null);
    }
}

package org.gdgsacramento.contentprovider;

import android.app.Activity;
import android.content.ContentValues;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import org.gdgsacramento.contentprovider.data.FoodContract.FoodEntry;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        if(savedInstanceState == null) {

            getFragmentManager().beginTransaction()
                    .add(R.id.container, FoodListFragment.newInstance())
                    .commit();
        }

        ContentValues foodValues1 = new ContentValues();
        foodValues1.put(FoodEntry.COLUMN_FOOD_NAME, "Apple");
        foodValues1.put(FoodEntry.COLUMN_FOOD_DESCRIPTION, "green");
        getContentResolver().insert(FoodEntry.CONTENT_URI, foodValues1);

        ContentValues foodValue2 = new ContentValues();
        foodValue2.put(FoodEntry.COLUMN_FOOD_NAME, "Banana");
        foodValue2.put(FoodEntry.COLUMN_FOOD_DESCRIPTION, "yellow");
        getContentResolver().insert(FoodEntry.CONTENT_URI, foodValue2);

        ContentValues foodValue3 = new ContentValues();
        foodValue3.put(FoodEntry.COLUMN_FOOD_NAME, "Tomato");
        foodValue3.put(FoodEntry.COLUMN_FOOD_DESCRIPTION, "red");
        getContentResolver().insert(FoodEntry.CONTENT_URI, foodValue3);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.action_settings) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

package ie.food.activities;

import ie.food.R;
import ie.food.fragments.FoodFragment;
import ie.food.main.FoodRaterApp;
import ie.food.notes.MealActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

public class Base extends AppCompatActivity {

    public FoodRaterApp app;
    public Bundle activityInfo;
    public FoodFragment foodFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (FoodRaterApp) getApplication();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    public void menuHome(MenuItem m) {
        startActivity(new Intent(this, Home.class));
    }

    public void menuInfo(MenuItem m) {
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.appAbout))
                .setMessage(getString(R.string.appDesc)
                        + "\n\n"
                        + getString(R.string.appMoreInfo))
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }


    public void menuHelp(MenuItem m)
    {

    }

    public void menuNote(MenuItem m)
    {
        startActivity(new Intent(this, MealActivity.class));
    }
    //        startActivity(new Intent(this, JournalActivity.class));
}


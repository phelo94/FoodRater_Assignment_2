package ie.food.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import ie.food.R;
import ie.food.journal.JournalActivity;
import ie.food.notes.MealActivity;
import ie.food.registration.ProfileActivity;

public class Help extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_journal, menu);

        return true;

    }
    public void menuHome(MenuItem m) {
        startActivity(new Intent(this, Home.class));
    }
    public void menuJournal(MenuItem m) {
        startActivity(new Intent(this, JournalActivity.class));

    }
    public void menuNote(MenuItem m) {
        startActivity(new Intent(this, MealActivity.class));

    }
    public void menuProfile(MenuItem m) {
        startActivity(new Intent(this, ProfileActivity.class));

    }

}

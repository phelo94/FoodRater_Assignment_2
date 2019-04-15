package ie.food.idea;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import ie.food.R;
import ie.food.activities.Home;
import ie.food.image.ImageActivity;
import ie.food.journal.JournalListActivity;
import ie.food.notes.MealActivity;
import ie.food.registration.ProfileActivity;

import static ie.food.idea.IdeaActivity.EXTRA_CREATOR;
import static ie.food.idea.IdeaActivity.EXTRA_LIKES;
import static ie.food.idea.IdeaActivity.EXTRA_URL;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        String imageUrl = intent.getStringExtra(EXTRA_URL);
        String creatorName = intent.getStringExtra(EXTRA_CREATOR);
        int likeCount = intent.getIntExtra(EXTRA_LIKES, 0);

        ImageView imageView = findViewById(R.id.image_view_detail);
        TextView textViewCreator = findViewById(R.id.text_view_creator_detail);
        TextView textViewLikes = findViewById(R.id.text_view_like_detail);

        Picasso.get().load(imageUrl).fit().centerInside().into(imageView);
        textViewCreator.setText(creatorName);
        textViewLikes.setText("Likes: " + likeCount);
    }

    //new code down
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

    public void menuNote(MenuItem m) {
        startActivity(new Intent(this, MealActivity.class));
        //startActivity(new Intent(this, JournalActivity.class));

    }

    public void menuProfile(MenuItem m) {
        startActivity(new Intent(this, ProfileActivity.class));

    }

    public void menuJournal(MenuItem m) {
        startActivity(new Intent(this, JournalListActivity.class));

    }

    public void menuImage(MenuItem m) {
        startActivity(new Intent(this, ImageActivity.class));

    }

    public void menuIdea(MenuItem m) {
        startActivity(new Intent(this, IdeaActivity.class));

    }

//new code up

}
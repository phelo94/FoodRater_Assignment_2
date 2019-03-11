package ie.food.notes;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import es.dmoral.toasty.Toasty;
import ie.food.R;
import ie.food.activities.Home;
import ie.food.adapters.MealAdapter;
import ie.food.journal.JournalListActivity;
import ie.food.registration.LoginActivity;
import ie.food.registration.ProfileActivity;

public class MealActivity extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference notebookRef = db.collection("Notebook");

    private MealAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal);

        FloatingActionButton buttonAddNote = findViewById(R.id.button_add_note);
        buttonAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MealActivity.this, NewMealActivity.class));
            }
        });

        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        Query query = notebookRef.orderBy("priority", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<Meal> options = new FirestoreRecyclerOptions.Builder<Meal>()
                .setQuery(query, Meal.class)
                .build();

        adapter = new MealAdapter(options);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }


            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                adapter.deleteItem(viewHolder.getAdapterPosition());

                Toasty.error(MealActivity.this, "Meal Removed", Toast.LENGTH_SHORT, true).show();
                finish();
            }
        }).attachToRecyclerView(recyclerView);


        //Toast.makeText(MainActivity.this,
          //      "Position: " + position + " ID: " + id, Toast.LENGTH_SHORT).show();


    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_note, menu);

        return true;

    }
    public void menuHome(MenuItem m) {
        startActivity(new Intent(this, Home.class));
    }
    public void menuJournal(MenuItem m) {
        startActivity(new Intent(this, JournalListActivity.class));

    }
    public void menuNote(MenuItem m) {
        startActivity(new Intent(this, MealActivity.class));
    }

    public void menuProfile(MenuItem m) {
        startActivity(new Intent(this, ProfileActivity.class));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menuLogout:
                //case R.id.menuHome:

                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(this, LoginActivity.class));

                // startActivity(new Intent(this, Home.class));

                break;
        }

        return true;
    }
}


//Toasty.error(this, "Meal Removed", Toast.LENGTH_SHORT, true).show();
//        finish();
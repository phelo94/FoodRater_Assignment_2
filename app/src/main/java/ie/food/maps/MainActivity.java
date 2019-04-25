package ie.food.maps;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.type.LatLng;

import java.io.IOException;
import java.util.List;

import ie.food.R;
import ie.food.activities.Home;
import ie.food.journal.JournalListActivity;
import ie.food.notes.MealActivity;
import ie.food.registration.LoginActivity;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback {

    LatLng latLng;
    GoogleMap map;
    SupportMapFragment mapFragment;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchView = findViewById(R.id.sv_location);
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String location = searchView.getQuery().toString();
                List<Address> addressList = null;

                if (location !=null || !location.equals("")){
                    Geocoder geocoder = new Geocoder(MainActivity.this);
                    try{
                        addressList = geocoder.getFromLocationName(location,1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Address address = addressList.get(0);
                   // LatLng latLng = new LatLng(address.getLatitude(),address.getLongitude());
                    //map.addMarker(new MarkerOptions().position(latLng).title(location));
                    //map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
                }

                return false;

            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        mapFragment.getMapAsync((OnMapReadyCallback) this);

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
    }



    // down
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

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

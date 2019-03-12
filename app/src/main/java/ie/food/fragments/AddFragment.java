package ie.food.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;
import ie.food.R;
import ie.food.activities.Home;
import ie.food.main.FoodRaterApp;
import ie.food.models.Food;

public class AddFragment extends Fragment {

    private String foodName, foodShop;
    private double foodPrice, ratingValue;
    private EditText name, shop, prices;
    private RatingBar ratingBar;
    private Button saveButton;
    private FoodRaterApp app;

    public AddFragment() {
        // Required empty public constructor
    }

    public static AddFragment newInstance() {
        AddFragment fragment = new AddFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        app = (FoodRaterApp) getActivity().getApplication();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.add_fragment, container, false);
        getActivity().setTitle(R.string.addAFoodLbl);
        name = v.findViewById(R.id.addNameET);
        shop =  v.findViewById(R.id.addShopET);
        prices =  v.findViewById(R.id.addPriceET);
        ratingBar =  v.findViewById(R.id.addRatingBar);
        saveButton = v.findViewById(R.id.addFoodButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFood();
            }
        });

        return v;
    }

    public void addFood() {

        foodName = name.getText().toString();
        foodShop = shop.getText().toString();
        try {
            foodPrice = Double.parseDouble(prices.getText().toString());
        } catch (NumberFormatException e) {
            foodPrice = 0.0;
        }
        ratingValue = ratingBar.getRating();

        if ((foodName.length() > 0) && (foodShop.length() > 0)
                && (prices.length() > 0)) {
            Food c = new Food(foodName, foodShop, ratingValue,
                    foodPrice, false);

            app.foodList.add(c);
            startActivity(new Intent(this.getActivity(), Home.class));
        } else
            /*Toast.makeText(
                    this.getActivity(),
                    "You must Enter Something for "
                            + "\'Name\', \'Shop\' and \'Price\'",
                    Toast.LENGTH_SHORT).show();
                    */

        Toasty.warning( getActivity(),"You must Enter Something for \"\n" +
                        "                            + \"\\'Name\\', \\'Shop\\' and \\'Price\\'",
                Toast.LENGTH_LONG, true).show();
    }
}
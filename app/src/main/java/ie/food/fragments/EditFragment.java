package ie.food.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.muddzdev.styleabletoastlibrary.StyleableToast;

import es.dmoral.toasty.Toasty;
import ie.food.R;
import ie.food.main.FoodRaterApp;
import ie.food.models.Food;

public class EditFragment extends Fragment {

    public boolean isFavourite;
    public Food aFood;
    public ImageView editFavourite;
    private EditText name, shop, price;
    private RatingBar ratingBar;
    public FoodRaterApp app;

    private OnFragmentInteractionListener mListener;

    public EditFragment() {
        // Required empty public constructor
    }

    public static EditFragment newInstance(Bundle foodBundle) {
        EditFragment fragment = new EditFragment();
        fragment.setArguments(foodBundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        app = (FoodRaterApp) getActivity().getApplication();

        if(getArguments() != null)
            aFood = getFoodObject(getArguments().getString("foodId"));
    }

    private Food getFoodObject(String id) {

        for (Food c : app.foodList)
            if (c.foodId.equalsIgnoreCase(id))
                return c;

        return null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.edit_fragment, container, false);

        ((TextView)v.findViewById(R.id.editTitleTV)).setText(aFood.foodName);

        name = v.findViewById(R.id.editNameET);
        shop = v.findViewById(R.id.editShopET);
        price = v.findViewById(R.id.editPriceET);
        ratingBar = v.findViewById(R.id.editRatingBar);
        editFavourite = v.findViewById(R.id.editFavourite);

        name.setText(aFood.foodName);
        shop.setText(aFood.shop);
        price.setText(""+ aFood.price);
        ratingBar.setRating((float) aFood.rating);

        if (aFood.favourite == true) {
            editFavourite.setImageResource(R.drawable.ic_favorite_red_on);
            isFavourite = true;
        } else {
            editFavourite.setImageResource(R.drawable.ic_favorite_off);
            isFavourite = false;
        }
        return v;
    }

    public void saveFood(View v) {
        if (mListener != null) {
            String foodName = name.getText().toString();
            String foodShop = shop.getText().toString();
            String foodPriceStr = price.getText().toString();
            double ratingValue = ratingBar.getRating();

            double foodPrice;
            try {
                foodPrice = Double.parseDouble(foodPriceStr);
            } catch (NumberFormatException e)
            {            foodPrice = 0.0;        }

            if ((foodName.length() > 0) && (foodShop.length() > 0) && (foodPriceStr.length() > 0)) {
                aFood.foodName = foodName;
                aFood.shop = foodShop;
                aFood.price = foodPrice;
                aFood.rating = ratingValue;

                if (getActivity().getSupportFragmentManager().getBackStackEntryCount() > 0) {
                    getFragmentManager().popBackStack();
                    return;
                }
            }
        } else
            StyleableToast.makeText(getActivity(), "You must Enter Something for Name and Shop",R.style.exampleToast).show();
    }

    public void toggle(View v) {

        if (isFavourite) {
            aFood.favourite = false;
            Toasty.error(getActivity(), "Removed From Favourites", Toast.LENGTH_SHORT).show();
            isFavourite = false;
            editFavourite.setImageResource(R.drawable.ic_favorite_off);
        } else {
            aFood.favourite = true;
           // StyleableToast.makeText(getActivity(), "Added",R.style.exampleToast).show();

            Toasty.success( getActivity(),"Added To Favourites",
                    Toast.LENGTH_SHORT, true).show();

            isFavourite = true;
            editFavourite.setImageResource(R.drawable.ic_favorite_red_on);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void toggle(View v);
        void saveFood(View v);
    }
}

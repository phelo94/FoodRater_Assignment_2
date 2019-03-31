package ie.food.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;

import ie.food.R;
import ie.food.models.Food;

public class FoodItem {
    public View view;

    public FoodItem(Context context, ViewGroup parent,
                    View.OnClickListener deleteListener, Food food)
    {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.foodcard, parent, false);
        view.setTag(food.foodId);

        updateControls(food);

        ImageView imgDelete = view.findViewById(R.id.rowDeleteImg);
        imgDelete.setTag(food);
        imgDelete.setOnClickListener(deleteListener);
    }

    private void updateControls(Food food) {
        ((TextView) view.findViewById(R.id.rowFoodName)).setText(food.foodName);

        ((TextView) view.findViewById(R.id.rowFoodShop)).setText(food.shop);
        ((TextView) view.findViewById(R.id.rowFoodDate)).setText(food.date);
        ((TextView) view.findViewById(R.id.rowRating)).setText(food.rating + " *");
        ((TextView) view.findViewById(R.id.rowPrice)).setText("€" +
                new DecimalFormat("0.00").format(food.price));

        ImageView imgIcon = view.findViewById(R.id.rowFavouriteImg);

        if (food.favourite == true)
            imgIcon.setImageResource(R.drawable.ic_favorite_red_on);
        else
            imgIcon.setImageResource(R.drawable.ic_favorite_off);


    }
}
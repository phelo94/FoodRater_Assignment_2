package ie.food.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import ie.food.R;
import ie.food.models.Food;

public class FoodListAdapter extends ArrayAdapter<Food>
{
    private Context context;
    private View.OnClickListener deleteListener;
    public List<Food> foodList;

    public FoodListAdapter(Context context, View.OnClickListener deleteListener, List<Food> foodList)
    {
        super(context, R.layout.foodrow, foodList);

        this.context = context;
        this.deleteListener = deleteListener;
        this.foodList = foodList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FoodItem item = new FoodItem(context, parent,
                                         deleteListener, foodList.get(position));
        return item.view;
    }

    @Override
    public int getCount() {
        return foodList.size();
    }

    @Override
    public Food getItem(int position) {
        return foodList.get(position);
    }
}

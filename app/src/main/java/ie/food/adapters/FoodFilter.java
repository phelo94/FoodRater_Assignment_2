package ie.food.adapters;

import android.util.Log;
import android.widget.Filter;

import java.util.ArrayList;
import java.util.List;

import ie.food.models.Food;

public class FoodFilter extends Filter {
	public List<Food> originalFoodList;
	public String filterText;
	public FoodListAdapter adapter;

	public FoodFilter(List<Food> originalFoodList, String filterText,
					  FoodListAdapter adapter) {
		super();
		this.originalFoodList = originalFoodList;
		this.filterText = filterText;
		this.adapter = adapter;
	}

	public void setFilter(String filterText) {
		this.filterText = filterText;
	}

	@Override
	protected FilterResults performFiltering(CharSequence prefix) {
		FilterResults results = new FilterResults();

		List<Food> newFoods;
		String foodName;

		if (prefix == null || prefix.length() == 0) {
			newFoods = new ArrayList<>();
			if (filterText.equals("all")) {
				results.values = originalFoodList;
				results.count = originalFoodList.size();
			} else {
				if (filterText.equals("favourites")) {
					for (Food c : originalFoodList)
						if (c.favourite)
							newFoods.add(c);
					}
					results.values = newFoods;
					results.count = newFoods.size();
			}
		} else {
			String prefixString = prefix.toString().toLowerCase();
			newFoods = new ArrayList<>();

			for (Food c : originalFoodList) {
				foodName = c.foodName.toLowerCase();
				if (foodName.contains(prefixString)) {
					if (filterText.equals("all")) {
						newFoods.add(c);
					} else if (c.favourite) {
						newFoods.add(c);
					}}}
			results.values = newFoods;
			results.count = newFoods.size();
		}
		return results;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void publishResults(CharSequence prefix, FilterResults results) {

		adapter.foodList = (ArrayList<Food>) results.values;

		if (results.count >= 0)
			adapter.notifyDataSetChanged();
		else {
			adapter.notifyDataSetInvalidated();
			adapter.foodList = originalFoodList;
		}
		Log.v("foodrater", "publishResults : " + adapter.foodList);
	}
}

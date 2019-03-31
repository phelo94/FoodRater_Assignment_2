package ie.food.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

import es.dmoral.toasty.Toasty;
import ie.food.R;
import ie.food.activities.Base;
import ie.food.adapters.FoodFilter;
import ie.food.adapters.FoodListAdapter;
import ie.food.models.Food;

public class FoodFragment extends Fragment implements
        AdapterView.OnItemClickListener,
        View.OnClickListener,
        AbsListView.MultiChoiceModeListener
{
    public Base activity;
    public static FoodListAdapter listAdapter;
    public ListView listView;
    public FoodFilter foodFilter;
    public boolean favourites = false;

    public FoodFragment() {
       //notes say i need to keep constructor empty
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Bundle activityInfo = new Bundle(); // Creates a new Bundle object
        activityInfo.putString("foodId", (String) view.getTag());

        Fragment fragment = EditFragment.newInstance(activityInfo);
        getActivity().setTitle(R.string.editFoodLbl);

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.homeFrame, fragment)
                .addToBackStack(null)
                .commit();
    }


    public static FoodFragment newInstance() {
        FoodFragment fragment = new FoodFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        this.activity = (Base) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.home_fragment, parent, false);
        getActivity().setTitle(R.string.recentlyViewedLbl);
        listAdapter = new FoodListAdapter(activity, this, activity.app.foodList);
        foodFilter = new FoodFilter(activity.app.foodList,"all",listAdapter);

        if (favourites) {
            getActivity().setTitle(R.string.favouritesFoodLbl);
            foodFilter.setFilter("favourites");
            foodFilter.filter(null);
            listAdapter.notifyDataSetChanged();
        }
       // setRandomFoodItem();

        listView = v.findViewById(R.id.homeList);

        setListView(v);

        return v;
    }

    public void setListView(View view)
    {
        listView.setAdapter (listAdapter);
        listView.setOnItemClickListener(this);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(this);
        listView.setEmptyView(view.findViewById(R.id.emptyList));
    }

    @Override
    public void onStart()
    {
        super.onStart();
    }

    @Override
    public void onClick(View view)
    {
        if (view.getTag() instanceof Food)
        {
            onFoodDelete((Food) view.getTag());
        }
    }

    public void onFoodDelete(final Food food)
    {
        String stringName = food.foodName;
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage("Are you sure you want to Delete the \'Food\' " + stringName + "?");
        builder.setCancelable(false);

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id)
            {
                activity.app.foodList.remove(food); // remove from our list
                listAdapter.foodList.remove(food); // update adapters data
                setRandomFoodItem();
                listAdapter.notifyDataSetChanged(); // refresh adapter
                Toasty.error(getActivity(), "Removed From Takeaway", Toast.LENGTH_SHORT).show();
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id)
            {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    /* ************ MultiChoiceModeListener methods (begin) *********** */
    @Override
    public boolean onCreateActionMode(ActionMode actionMode, Menu menu)
    {
        MenuInflater inflater = actionMode.getMenuInflater();
        inflater.inflate(R.menu.delete_list_context, menu);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem)
    {
        switch (menuItem.getItemId())
        {
            case R.id.menu_item_delete_food:
                deleteFoodItems(actionMode);
                return true;
            default:
                return false;
        }
    }

    public void deleteFoodItems(ActionMode actionMode)
    {
        for (int i = listAdapter.getCount() -1 ; i >= 0; i--)
        {
            if (listView.isItemChecked(i))
            {
                activity.app.foodList.remove(listAdapter.getItem(i));
                if (favourites)
                   listAdapter.foodList.remove(listAdapter.getItem(i));
            }
        }
        setRandomFoodItem();
        listAdapter.notifyDataSetChanged();

        actionMode.finish();
    }

    public void setRandomFoodItem() {

        ArrayList<Food> foodList = new ArrayList<>();

        for(Food c : activity.app.foodList)
            if (c.favourite)
                foodList.add(c);

        if (favourites)
            if( !foodList.isEmpty()) {
                Food randomFood = foodList.get(new Random()
                            .nextInt(foodList.size()));

                ((TextView) getActivity().findViewById(R.id.favouriteFoodName)).setText(randomFood.foodName);
                ((TextView) getActivity().findViewById(R.id.favouriteFoodShop)).setText(randomFood.shop);
                ((TextView) getActivity().findViewById(R.id.favouriteFoodDate)).setText(randomFood.date);
                ((TextView) getActivity().findViewById(R.id.favouriteFoodPrice)).setText("€ " + randomFood.price);
                ((TextView) getActivity().findViewById(R.id.favouriteFoodRating)).setText(randomFood.rating + " *");
            }
            else {
                ((TextView) getActivity().findViewById(R.id.favouriteFoodName)).setText("N/A");
                ((TextView) getActivity().findViewById(R.id.favouriteFoodShop)).setText("N/A");
                ((TextView) getActivity().findViewById(R.id.favouriteFoodDate)).setText("N/A");
                ((TextView) getActivity().findViewById(R.id.favouriteFoodPrice)).setText("N/A");
                ((TextView) getActivity().findViewById(R.id.favouriteFoodRating)).setText("N/A");
            }
    }

    @Override
    public void onDestroyActionMode(ActionMode actionMode)
    {}

    @Override
    public void onItemCheckedStateChanged(ActionMode actionMode, int i, long l, boolean b) {
    }


}

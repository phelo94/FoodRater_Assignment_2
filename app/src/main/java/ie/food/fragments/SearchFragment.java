package ie.food.fragments;


import android.content.Context;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Spinner;

import ie.food.R;

public class SearchFragment extends FoodFragment
        implements AdapterView.OnItemSelectedListener {

    String selected;
    SearchView searchView;

    public SearchFragment() {
        // Required empty public constructor
    }

    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.search_fragment, container, false);
        getActivity().setTitle(R.string.searchFoodItemsLbl);
        listView = v.findViewById(R.id.searchList); //Bind to the list on our Search layout
        setListView(v);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter
                .createFromResource(getActivity(), R.array.foodTypes,
                        android.R.layout.simple_spinner_item);

        spinnerAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinner = v.findViewById(R.id.searchSpinner);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(this);

        searchView = v.findViewById(R.id.searchView);
        searchView.setQueryHint("Search your Food Items Here");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                foodFilter.filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                foodFilter.filter(newText);
                return false;
            }
        });

        return v;
    }

    @Override
    public void onAttach(Context c) { super.onAttach(c); }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void checkSelected(String selected)
    {
        if (selected != null) {
            if (selected.equals("All Types")) {
                foodFilter.setFilter("all");
            } else if (selected.equals("Favourites")) {
                foodFilter.setFilter("favourites");
            }

            String filterText = ((SearchView)activity
                    .findViewById(R.id.searchView)).getQuery().toString();

            if(filterText.length() > 0)
                foodFilter.filter(filterText);
            else
                foodFilter.filter("");
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selected = parent.getItemAtPosition(position).toString();
        checkSelected(selected);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) { }

    @Override
    public void deleteFoodItems(ActionMode actionMode) {
        super.deleteFoodItems(actionMode);
        checkSelected(selected);
    }

}

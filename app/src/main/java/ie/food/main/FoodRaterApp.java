package ie.food.main;

import ie.food.models.Food;

import java.util.ArrayList;
import java.util.List;
import android.app.Application;
import android.util.Log;

public class FoodRaterApp extends Application
{
    public List <Food> foodList = new ArrayList<>();

    @Override
    public void onCreate()
    {
        super.onCreate();
        Log.v("foodrater", "FoodRater App Started");
    }
}
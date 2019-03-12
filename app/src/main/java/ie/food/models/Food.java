package ie.food.models;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class Food implements Serializable
{
	public String foodId;
	public String foodName;
	public String shop;
	public String foodDates;
	public double rating;
	public double price;

	public boolean favourite;
//	public Date date;



	public Food() {}

	public Food(String name, String shop, String dates, double rating, double price, boolean fav)
	{
		this.foodId = UUID.randomUUID().toString();
		this.foodName = name;
		this.shop = shop;
		this.foodDates = dates;
		this.rating = rating;
		this.price = price;
		this.favourite = fav;
		//this.date = date;

	}

	@Override
	public String toString() {
		return foodId + " " + foodName + ", " + shop + ", " + foodDates + ", " + rating
				+ ", " + price + ", fav =" + favourite;
	}
}

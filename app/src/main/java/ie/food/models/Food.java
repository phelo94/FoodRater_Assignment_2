package ie.food.models;

import java.io.Serializable;
import java.util.UUID;

public class Food implements Serializable
{
	public String foodId;
	public String foodName;
	public String shop;
	public double rating;
	public double price;
	public boolean favourite;


	public Food() {}

	public Food(String name, String shop, double rating, double price, boolean fav)
	{
		this.foodId = UUID.randomUUID().toString();
		this.foodName = name;
		this.shop = shop;
		this.rating = rating;
		this.price = price;
		this.favourite = fav;
	}

	@Override
	public String toString() {
		return foodId + " " + foodName + ", " + shop + ", " + rating
				+ ", " + price + ", fav =" + favourite;
	}
}

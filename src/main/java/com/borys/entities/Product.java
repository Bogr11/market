package com.borys.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Product {
	@Id
	private String id;
	private String title;
	private double price;
	private double discountFor2Products;
	private double priceAfterDiscount;
	private boolean isDiscounted;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getDiscountFor2Products() {
		return discountFor2Products;
	}

	public void setDiscountFor2Products(double discountFor2Products) {
		this.discountFor2Products = discountFor2Products;
	}

	public double getPriceAfterDiscount() {
		return priceAfterDiscount;
	}

	public void setPriceAfterDiscount(double priceAfterDiscount) {
		this.priceAfterDiscount = priceAfterDiscount;
	}

	public boolean isDiscounted() {
		return isDiscounted;
	}

	public void setDiscounted(boolean discounted) {
		isDiscounted = discounted;
	}
}
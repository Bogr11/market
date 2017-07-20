package com.borys.entities.forms;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class ProductRegisterForm {
	@NotNull
	private String title;
	@NotNull
	private int price;
	@NotNull
	@Min(0)
	@Max(100)
	private int discountFor2Products;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getDiscountFor2Products() {
		return discountFor2Products;
	}

	public void setDiscountFor2Products(int discountFor2Products) {
		this.discountFor2Products = discountFor2Products;
	}
}
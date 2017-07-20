package com.borys.entities;

public class BindingForm {
	private String discountProductTitle;
	private String freeProductTitle;
	private int discountProductNumber;
	private int freeProductNumber;

	public String getDiscountProductTitle() {
		return discountProductTitle;
	}

	public void setDiscountProductTitle(String discountProductTitle) {
		this.discountProductTitle = discountProductTitle;
	}

	public String getFreeProductTitle() {
		return freeProductTitle;
	}

	public void setFreeProductTitle(String freeProductTitle) {
		this.freeProductTitle = freeProductTitle;
	}

	public int getDiscountProductNumber() {
		return discountProductNumber;
	}

	public void setDiscountProductNumber(int discountProductNumber) {
		this.discountProductNumber = discountProductNumber;
	}

	public int getFreeProductNumber() {
		return freeProductNumber;
	}

	public void setFreeProductNumber(int freeProductNumber) {
		this.freeProductNumber = freeProductNumber;
	}
}
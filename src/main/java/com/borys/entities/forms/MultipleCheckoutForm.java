package com.borys.entities.forms;

import java.util.List;

public class MultipleCheckoutForm {
	private List<ProductQuantityForm> checkoutProductList;

	public List<ProductQuantityForm> getCheckoutProductList() {
		return checkoutProductList;
	}

	public void setCheckoutProductList(List<ProductQuantityForm> checkoutProductList) {
		this.checkoutProductList = checkoutProductList;
	}
}
package com.borys.strategies;

import com.borys.entities.Product;
import com.borys.entities.forms.MultipleCheckoutForm;

import java.util.List;

public interface CheckoutStrategy {
	List<Product> getFinalPrice(MultipleCheckoutForm multipleCheckoutForm);
}
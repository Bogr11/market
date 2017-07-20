package com.borys.services;

import com.borys.entities.Product;
import com.borys.entities.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeService {

	@Autowired
	private MathService mathService;

	public Recipe generateRecipe(List<Product> products) {
		Recipe recipe = new Recipe();
		recipe.setGrandTotal(getMathService().trimToTwoDec(getGrandTotal(products)));
		recipe.setProductList(products);
		return recipe;
	}

	public double getGrandTotal(List<Product> products) {
		return products.stream().mapToDouble(Product::getPriceAfterDiscount).sum();
	}

	public MathService getMathService() {
		return mathService;
	}

	public void setMathService(MathService mathService) {
		this.mathService = mathService;
	}
}
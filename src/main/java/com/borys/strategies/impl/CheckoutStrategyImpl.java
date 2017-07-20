package com.borys.strategies.impl;

import com.borys.entities.BindingForm;
import com.borys.services.BindingFormService;
import com.borys.services.ProductService;
import com.borys.entities.Product;
import com.borys.entities.forms.MultipleCheckoutForm;
import com.borys.strategies.CheckoutStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CheckoutStrategyImpl implements CheckoutStrategy {

	@Autowired
	private ProductService productService;

	@Autowired
	private BindingFormService bindingFormService;

	@Override
	public List<Product> getFinalPrice(MultipleCheckoutForm multipleCheckoutForm) {
		List<Product> products = getProductService().convertFormToProductList(multipleCheckoutForm);
		int productTypes = multipleCheckoutForm.getCheckoutProductList().size();

		if (productTypes > 1)
			return getFinalPriceForMultipleProductTypes(products, multipleCheckoutForm, productTypes);
		else
			return getFinalPriceForSingleProductType(products);
	}

	private List<Product> getFinalPriceForSingleProductType(List<Product> singleTypeProducts) {
		int quantity = getNotDiscountedProductsQuantity(singleTypeProducts);

		if (ifBindingExists() && (singleTypeProducts.size() >= getBindingForm().getDiscountProductNumber())) {
			getProductService().addBindedProducts(singleTypeProducts, getBindingForm());
		}

		if (quantity  >= 3 ) {
			getProductService().setPriceAndDiscountFor3Products(singleTypeProducts, 0);
		}

		quantity = getNotDiscountedProductsQuantity(singleTypeProducts);

		if (quantity == 2) {
			getProductService().setPriceAndDiscountFor2Products(singleTypeProducts);
		}

		return singleTypeProducts;
	}

	private List<Product> getFinalPriceForMultipleProductTypes(List<Product> products, MultipleCheckoutForm multipleCheckoutForm, int productTypes) {
		List<Product> multipleTypeProducts = new ArrayList<>();

		for (int i = 0; i < productTypes; i++) {
			List<Product> similarTypeProducts = getProductService().getProductsOfSimilarType(products, multipleCheckoutForm, i);
			List<Product> result = getFinalPriceForSingleProductType(similarTypeProducts);
			multipleTypeProducts.addAll(result);
		}

		int quantity = getNotDiscountedProductsQuantity(multipleTypeProducts);

		if (quantity >= 3) {
			for (int i = 0; i < quantity / 3; i++) {
				getProductService().changePriceOfTheCheapest(multipleTypeProducts, 0);
			}
		}

		return multipleTypeProducts;
	}

	public int getNotDiscountedProductsQuantity(List<Product> products) {
		return getProductService().getNotDiscountedProducts(products).size();
	}

	public boolean ifBindingExists() {
		return getBindingFormService().isExists();
	}

	public BindingForm getBindingForm() {
		return getBindingFormService().getBindingForm();
	}

	public BindingFormService getBindingFormService() {
		return bindingFormService;
	}

	public void setBindingFormService(BindingFormService bindingFormService) {
		this.bindingFormService = bindingFormService;
	}

	public ProductService getProductService() {
		return productService;
	}

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}
}
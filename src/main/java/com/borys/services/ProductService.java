package com.borys.services;

import com.borys.dao.ProductRepository;
import com.borys.entities.BindingForm;
import com.borys.entities.Product;
import com.borys.entities.forms.MultipleCheckoutForm;
import com.borys.entities.forms.ProductQuantityForm;
import com.borys.entities.forms.ProductRegisterForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private MathService mathService;

	//Repository cooperation methods

	public void save(Product product) {
		getProductRepository().save(product);
	}

	public Product getProductByTitle(String productTitle) {
		return getProductRepository().findByTitle(productTitle);
	}

	public boolean isProductExists(ProductRegisterForm productRegisterForm) {
		return (getProductRepository().findByTitle(productRegisterForm.getTitle()) != null);
	}

	public List<Product> getAllProducts() {
		return getProductRepository().findAll();
	}

	public List<Product> convertFormToProductList(MultipleCheckoutForm multipleCheckoutForm) {
		List<Product> products = new ArrayList<>();

		for (int i = 0; i < multipleCheckoutForm.getCheckoutProductList().size(); i++) {
			int quantity = multipleCheckoutForm.getCheckoutProductList().get(i).getQuantity();
			String productTitle = multipleCheckoutForm.getCheckoutProductList().get(i).getProductTitle();

			for (int j = 0; j < quantity; j++) {
				Product product = createProductOfSameType(getProductByTitle(productTitle));
				product.setDiscounted(false);
				product.setPriceAfterDiscount(product.getPrice());
				products.add(product);
			}
		}

		return products;
	}

	public List<Product> addBindedProducts(List<Product> products, BindingForm bindingForm) {
		List<Product> discountProducts = getByTitle(products, bindingForm.getDiscountProductTitle());
		int coef = getCoef(discountProducts, bindingForm);
		int freeProducts = bindingForm.getFreeProductNumber() * coef;
		Product freeProduct = getProductByTitle(bindingForm.getFreeProductTitle());
		for (int i = 0; i < freeProducts; i++) {
			Product product = createProductOfSameType(freeProduct);
			product.setPriceAfterDiscount(0);
			product.setDiscounted(true);
			products.add(product);
		}
		return products;
	}

	public List<Product> getProductsOfSimilarType(List<Product> products, MultipleCheckoutForm multipleCheckoutForm, int iterator) {
		return getByTitle(products, multipleCheckoutForm.getCheckoutProductList().get(iterator).getProductTitle());
	}

	public List<Product> getByTitle(List<Product> products, String title) {
		return products.stream().filter(product -> title.equals(product.getTitle())).collect(Collectors.toList());
	}

	public List<Product> getNotDiscountedProducts(List<Product> products) {
		return products.stream().filter(product -> !product.isDiscounted()).collect(Collectors.toList());
	}

	public Product convertFormToProduct(ProductRegisterForm productRegisterForm) {
		Product product = new Product();
		product.setTitle(productRegisterForm.getTitle());
		product.setPrice(productRegisterForm.getPrice());
		product.setDiscountFor2Products((double) productRegisterForm.getDiscountFor2Products() / 100);
		return product;
	}

	public Product getCheapestProduct(List<Product> comparedProducts) {
		return getNotDiscountedProducts(comparedProducts).stream().min(Comparator.comparing(Product::getPrice)).orElseThrow(NoSuchElementException::new);
	}

	public Product createProductOfSameType(Product product) {
		Product newProduct = new Product();
		newProduct.setTitle(product.getTitle());
		newProduct.setPrice(product.getPrice());
		newProduct.setDiscountFor2Products(product.getDiscountFor2Products());
		newProduct.setPriceAfterDiscount(product.getPriceAfterDiscount());
		newProduct.setDiscounted(product.isDiscounted());
		return newProduct;
	}

	public void changePriceOfTheCheapest(List<Product> products, int price) {
		Product cheapestProduct = getCheapestProduct(getNotDiscountedProducts(products));
		cheapestProduct.setPriceAfterDiscount(price);
		cheapestProduct.setDiscounted(true);
	}

	public void setPriceAndDiscountFor3Products(List<Product> products, double price) {
		int length = products.size();
		int modifierLength = length - length % 3;
		for (int i = 2; i < length; i += 3) {
			products.get(i).setPriceAfterDiscount(price);
		}
		for (int i = 0; i < modifierLength; i++) {
			products.get(i).setDiscounted(true);
		}
	}

	public void setPriceAndDiscountFor2Products(List<Product> products) {
		setSpecialPrice(getNotDiscountedProducts(products));
		setDiscounted(getNotDiscountedProducts(products));
	}

	public void setSpecialPrice(List<Product> products) {
		products.forEach(product -> product.setPriceAfterDiscount(getSpecialPrice(product)));
	}

	public void setDiscounted(List<Product> products) {
		products.forEach(product -> product.setDiscounted(true));
	}

	public MultipleCheckoutForm trimFormToSize(MultipleCheckoutForm multipleCheckoutForm) {
		List<ProductQuantityForm> productQuantityFormList = new ArrayList<>();
		for (ProductQuantityForm form : multipleCheckoutForm.getCheckoutProductList()) {
			if (form.getQuantity() > 0) {
				productQuantityFormList.add(form);
			}
		}
		multipleCheckoutForm.setCheckoutProductList(productQuantityFormList);
		return multipleCheckoutForm;
	}

	public int getCoef(List<Product> products, BindingForm bindingForm) {
		return products.size() / bindingForm.getDiscountProductNumber();
	}

	public double getSpecialPrice(Product product) {
		return getMathService().trimToTwoDec(product.getPrice() - product.getPrice() * product.getDiscountFor2Products());
	}

	public MathService getMathService() {
		return mathService;
	}

	public void setMathService(MathService mathService) {
		this.mathService = mathService;
	}

	public ProductRepository getProductRepository() {
		return productRepository;
	}

	public void setProductRepository(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}
}
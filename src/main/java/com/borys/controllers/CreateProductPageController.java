package com.borys.controllers;

import com.borys.entities.forms.ProductRegisterForm;
import com.borys.services.ProductService;
import com.borys.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

import static com.borys.controllers.constants.ControllerConstants.PRODUCT_CREATE_PAGE;

@Controller
@RequestMapping(value = "/create")
public class CreateProductPageController {

	@Autowired
	private ProductService productService;

	@RequestMapping(method = RequestMethod.GET)
	public String show(Model model, ProductRegisterForm productRegisterForm) {
		model.addAttribute("productRegisterForm", productRegisterForm);
		return PRODUCT_CREATE_PAGE;
	}

	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody
	String create(@ModelAttribute("productRegisterForm") @Valid ProductRegisterForm productRegisterForm, BindingResult result) {
		if (result.hasErrors())
			return "Product has not been saved. Title, price or discount input fields are empty. Discount field should be between 0 and 100";

		if(isProductExists(productRegisterForm)) {
			return "Product with title " + productRegisterForm.getTitle() + " exists in the system";
		}

		Product product = getProductService().convertFormToProduct(productRegisterForm);
		getProductService().save(product);
		return "Product type " + product.getTitle() + " is created";
	}

	public boolean isProductExists(ProductRegisterForm productRegisterForm) {
		return getProductService().isProductExists(productRegisterForm);
	}

	public ProductService getProductService() {
		return productService;
	}

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}
}
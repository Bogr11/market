package com.borys.controllers;

import com.borys.entities.BindingForm;
import com.borys.entities.Product;
import com.borys.entities.Recipe;
import com.borys.entities.forms.MultipleCheckoutForm;
import com.borys.services.BindingFormService;
import com.borys.services.ProductService;
import com.borys.services.RecipeService;
import com.borys.strategies.CheckoutStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.borys.controllers.constants.ControllerConstants.MULTIPLE_CHECKOUT_PAGE;
import static com.borys.controllers.constants.ControllerConstants.SUMMARY_CHECKOUT_PAGE;

@Controller
@RequestMapping(value = "/multiple-checkout")
public class ProductCheckoutController {

	@Autowired
	private ProductService productService;

	@Autowired
	private RecipeService recipeService;

	@Autowired
	private BindingFormService bindingFormService;

	@Autowired
	private CheckoutStrategy checkoutStrategy;

	@RequestMapping(method = RequestMethod.GET)
	public String show(Model model, MultipleCheckoutForm multipleCheckoutForm, BindingForm bindingForm) {
		model.addAttribute("multipleCheckoutForm", multipleCheckoutForm);
		model.addAttribute("products", getAllProducts());
		model.addAttribute("bindingForm", bindingForm);
		return MULTIPLE_CHECKOUT_PAGE;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String multipleProductCheckout(@ModelAttribute("multipleCheckoutForm") MultipleCheckoutForm multipleCheckoutForm, Model model) {
		List<Product> purchasedProducts = getRecipe(multipleCheckoutForm);
		Recipe recipe = generateRecipe(purchasedProducts);
		model.addAttribute("recipe", recipe);
		return SUMMARY_CHECKOUT_PAGE;
	}

	@RequestMapping(value = "/bind", method = RequestMethod.POST)
	public @ResponseBody
	String bind(@ModelAttribute("bindingForm") BindingForm bindingForm) {
		if (getBindingFormService().verify(bindingForm)) {
			getBindingFormService().save(bindingForm);
			return "Products are binded";
		}

		return "Error. Products cannot be the same";
	}

	@RequestMapping(value = "/unbind", method = RequestMethod.GET)
	public String unbind() {
		getBindingFormService().deleteAll();
		return "redirect:/multiple-checkout";
	}

	public MultipleCheckoutForm trimForm(MultipleCheckoutForm multipleCheckoutForm) {
		return getProductService().trimFormToSize(multipleCheckoutForm);
	}

	public List<Product> getRecipe(MultipleCheckoutForm multipleCheckoutForm) {
		return getCheckoutStrategy().getFinalPrice(trimForm(multipleCheckoutForm));
	}

	public Recipe generateRecipe(List<Product> products) {
		return getRecipeService().generateRecipe(products);
	}

	public List<Product> getAllProducts() {
		return getProductService().getAllProducts();
	}

	public ProductService getProductService() {
		return productService;
	}

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	public RecipeService getRecipeService() {
		return recipeService;
	}

	public void setRecipeService(RecipeService recipeService) {
		this.recipeService = recipeService;
	}

	public BindingFormService getBindingFormService() {
		return bindingFormService;
	}

	public void setBindingFormService(BindingFormService bindingFormService) {
		this.bindingFormService = bindingFormService;
	}

	public CheckoutStrategy getCheckoutStrategy() {
		return checkoutStrategy;
	}

	public void setCheckoutStrategy(CheckoutStrategy checkoutStrategy) {
		this.checkoutStrategy = checkoutStrategy;
	}
}
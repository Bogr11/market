package com.borys.services;

import com.borys.dao.BindingFormRepository;
import com.borys.entities.BindingForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BindingFormService {

	@Autowired
	BindingFormRepository bindingFormRepository;

	public void save(BindingForm bindingForm) {
		getBindingFormRepository().deleteAll();
		getBindingFormRepository().save(bindingForm);
	}

	public void deleteAll() {
		getBindingFormRepository().deleteAll();
	}

	public boolean isExists() {
		return getBindingFormRepository().findAll().size() > 0;
	}

	public BindingForm getBindingForm() {
		return getBindingFormRepository().findAll().get(0);
	}

	public boolean verify(BindingForm bindingForm) {
		return !bindingForm.getDiscountProductTitle().equals(bindingForm.getFreeProductTitle());
	}

	public BindingFormRepository getBindingFormRepository() {
		return bindingFormRepository;
	}

	public void setBindingFormRepository(BindingFormRepository bindingFormRepository) {
		this.bindingFormRepository = bindingFormRepository;
	}
}
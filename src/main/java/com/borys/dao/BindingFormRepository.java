package com.borys.dao;

import com.borys.entities.BindingForm;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BindingFormRepository extends MongoRepository<BindingForm, String>{
}
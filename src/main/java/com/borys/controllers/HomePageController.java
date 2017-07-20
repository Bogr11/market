package com.borys.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import static com.borys.controllers.constants.ControllerConstants.HOMEPAGE;

@Controller
@RequestMapping(value = "/", method = RequestMethod.GET)
public class HomePageController {

	@RequestMapping(method = RequestMethod.GET)
	public String show() {
		return HOMEPAGE;
	}
}
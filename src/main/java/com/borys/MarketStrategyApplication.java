package com.borys;

import com.borys.strategies.CheckoutStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MarketStrategyApplication {

	public static void main(String[] args) {
		SpringApplication.run(MarketStrategyApplication.class, args);
	}
}
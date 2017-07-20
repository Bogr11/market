package com.borys.services;

import org.springframework.stereotype.Service;

@Service
public class MathService {
	public double trimToTwoDec(double value) {
		return Math.round(value * 100.0) / 100.0;
	}
}
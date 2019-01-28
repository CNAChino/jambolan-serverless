package com.three55.jambolanapi.utils;

import java.util.function.Predicate;

public class Validator {
	
	public  static Predicate<Object> requiredNotNull() {
		return o -> o != null;
	}

	public static void validateParam(Object o, Predicate<Object> p, String msg) {
		if (!p.test(o)) {
			throw new IllegalArgumentException(msg);
		}
	}

	public static Predicate<Object> requiredNull() {
		return o -> o == null;
	}

	public static Predicate<Object> countryValid() {
		return o -> o != null && CountryCode.getByName(o.toString()) != null;				
	}
	
}

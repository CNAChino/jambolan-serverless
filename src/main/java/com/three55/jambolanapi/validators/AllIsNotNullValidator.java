package com.three55.jambolanapi.validators;

import java.util.List;

public class AllIsNotNullValidator {
	public static boolean validate(List<Object> l) {
		return l.stream().allMatch(o -> o != null);
	}
}

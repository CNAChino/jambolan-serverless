package com.three55.jambolanapi.utils;

import java.util.List;
import java.util.Optional;

import com.three55.jambolanapi.exceptions.DaoException;

public class SingleItemExtractorFromList<T> {
	public static <T> Optional<T> get(List<T> list) {
		if (list != null) {
			if (list.size() > 1) {
				// TODO LOG ERROR
				throw new IllegalArgumentException("SingleItemExtractorFromList failed. expecting 1 result got " 
						+ list.size() );
			}
			if (list.size() == 1) {
				return Optional.ofNullable(list.get(0));
			} else {
				return Optional.ofNullable(null);
			}
		} else {
			throw new IllegalArgumentException("SingleItemExtractorFromList failed. null list");
		}
	}
}

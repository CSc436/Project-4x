package com.shared.model.control;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Tools {
	
	private static Set<Long> ids = new HashSet<Long>();
	private static Random gen = new Random();

	public static long generateUniqueId() {
		while (true) {
			long temp = gen.nextLong();
			if(!ids.contains(temp)) {
				ids.add(temp);
				return temp;
			}
		}
	}

}
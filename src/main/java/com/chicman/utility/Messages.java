package com.chicman.utility;

import com.vaadin.ui.UI;

import java.util.ResourceBundle;
import java.util.TreeSet;

public class Messages {
	
	private static final TreeSet<String> baseNames = new TreeSet<>();
	
	public static void addBundle(String baseName) {
		baseNames.add(baseName);
	}
	
	public static String get(String key) {
		return baseNames.stream()
				.map(baseName -> ResourceBundle.getBundle(baseName, UI.getCurrent().getLocale()))
				.filter(bundle -> bundle.containsKey(key))
				.map(bundle -> bundle.getString(key))
				.findFirst().orElse(null);
	}
}

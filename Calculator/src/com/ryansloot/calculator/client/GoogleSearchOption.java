package com.ryansloot.calculator.client;

import com.google.gwt.user.client.Window;

public class GoogleSearchOption extends Calculator {

	private String searchEntry = "";

	public boolean checkIfSearch(String text) {
		String textInBox = text.trim();
		for (int i = 0; i < textInBox.length() - 1; i++) {
			if (Character.isDigit(textInBox.charAt(i))
					|| (textInBox.charAt(i) == '+'
							|| textInBox.charAt(i) == '-'
							|| textInBox.charAt(i) == '*' || textInBox
							.charAt(i) == '/')) {
				continue;
			} else if (Character.isLetter(textInBox.charAt(i))
					|| textInBox.charAt(i) == ' ')
				return true;
		}
		return false;
	}

	public GoogleSearchOption() {

	}

	public void search(String entry) {
		Window.open("http://google.com#q=" + entry, "_blank", null);
		searchEntry = entry;
	}

	public String getSeachEntry() {
		return "Searching: " + searchEntry;
	}
}

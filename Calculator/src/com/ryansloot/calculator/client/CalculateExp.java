package com.ryansloot.calculator.client;

//import java.util.ArrayList;

import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.Window;

/**
 * Used in association with Calculator class to evaluate the expressions given
 * and return them to the textbox.
 * 
 * @author Ryan Sloot
 * 
 */
public class CalculateExp extends Calculator {
	private final String MATH_SYMBOLS = "+-/*";

	/**
	 * blank constructor
	 */
	public CalculateExp() {

	}

	/**
	 * calculates expression based on parameters entered.
	 * 
	 * @param firstExp
	 *            the first expression in the equation.
	 * @param secondExp
	 *            the second expression in the equation.
	 * @param modifier
	 *            addition subtraction division or multiplication.
	 * @return the answer.
	 */
	public String calculate(String firstExp, String secondExp, String modifier) {
		// String equation = Calculator.getNumberText();
		// arraylist to store chars for expressions
		// ArrayList<Character> expr1 = new ArrayList<Character>();
		// ArrayList<Character> expr2 = new ArrayList<Character>();
		// char modifier = '+';
		// boolean expr1Found = false, expr2Found = false;
		// // loop through text from textbox to get equation to evaluate
		// for (int i = 0; i < equation.length(); i++) {
		// if (equation.charAt(i) == '+' || equation.charAt(i) == '-'
		// || equation.charAt(i) == '/' || equation.charAt(i) == '*') {
		// modifier = equation.charAt(i);
		// expr1Found = true;
		// continue;
		// }
		// while (expr1Found && !expr2Found) {
		// expr2.add(equation.charAt(i));
		// if (i == equation.length() - 1)
		// expr2Found = true;
		// }
		// if (expr2Found)
		// break;
		// expr1.add(equation.charAt(i));
		// }
		// store expressions as floats and calculate the expression
		double firstEx = Double.parseDouble(firstExp);
		double secondEx = Double.parseDouble(secondExp);
		if (firstExp.equals("600613")) {
			goToGoogle();
			return "Google...";
		}
		if (modifier.equals("+")) {
			double addNum = firstEx + secondEx;
			return numFormatter(addNum);
		} else if (modifier.equals("-")) {
			double subNum = firstEx - secondEx;
			return numFormatter(subNum);
		} else if (modifier.equals("/")) {
			double divNum = (double) firstEx / secondEx;
			return numFormatter(divNum);
		} else if (modifier.equals("*")) {
			double multNum = (double) firstEx * secondEx;
			return numFormatter(multNum);
		} else {
			clearText();
			throw new RuntimeException("INVALID ENTRY");
		}

	}

	// // remove excess from arraylist toString method.
	// private String removeStuff(String al) {
	// return al.toString().replace("[", "").replace("]", "")
	// .replace(", ", "");
	// }

	private void goToGoogle() {
		// Auto-generated method stub
		Window.open("http://www.google.com", "_blank", "");
		// try
		// {
		// URL google = new URL("http://www.google.com");
		// URLConnection urlConn = google.openConnection();
		// urlConn.connect();
		// }
		// catch (IOException e)
		// {
		// e.printStackTrace();
		// }
	}

	public boolean isValidExpression(String text) {
		String s = text + "";
		boolean first = false;
		for (int i = 0; i < s.length(); i++) {
			if (MATH_SYMBOLS.contains(s.charAt(i) + "") && !first) {
				s.replace(s.charAt(i), ' ');
				first = true;
				continue;
			}
			if (MATH_SYMBOLS.contains(s.charAt(i) + "") && first) {
				return false;
			}
		}
		return true;
	}

	/**
	 * format the answer from the calculate method
	 * 
	 * @param num
	 *            the number to be formatted.
	 * @return a formatted number in the form of a String.
	 */
	private String numFormatter(double num) {
		String formattedNum = NumberFormat.getFormat("#,###.0000").format(num);
		return formattedNum;
	}
}

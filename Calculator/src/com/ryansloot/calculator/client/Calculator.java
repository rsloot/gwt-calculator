package com.ryansloot.calculator.client;

import java.util.ArrayList;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;

/**
 * A simple calculator that uses only the buttons for input. Implemented using
 * google web tools.
 * 
 * @author Ryan Sloot
 * 
 */
public class Calculator implements EntryPoint {

	// to be used.
	private HorizontalPanel topNumberRow = new HorizontalPanel();
	private HorizontalPanel secondNumberRow = new HorizontalPanel();
	private HorizontalPanel thirdNumberRow = new HorizontalPanel();
	private HorizontalPanel bottomPanel = new HorizontalPanel();
	private HorizontalPanel equalsPanel = new HorizontalPanel();
	private TextBox numberText = new TextBox();
	private FlowPanel mainPanel = new FlowPanel();
	private Button one = new Button("1");
	private Button two = new Button("2");
	private Button three = new Button("3");
	private Button four = new Button("4");
	private Button five = new Button("5");
	private Button six = new Button("6");
	private Button seven = new Button("7");
	private Button eight = new Button("8");
	private Button nine = new Button("9");
	private Button zero = new Button("0");
	private Button decimal = new Button(".");
	private Button plus = new Button("+");
	private Button minus = new Button("-");
	private Button divide = new Button("/");
	private Button multiply = new Button("*");
	private Button equals = new Button("=");
	String firstEx = "";
	String secondEx = "";
	String modifier = "";
	boolean modFound = false;
	boolean helpMe = false;

	private ArrayList<Button> numberButtons = new ArrayList<Button>();
	private ArrayList<Button> functionButtons = new ArrayList<Button>();

	private boolean calculated = false;

	/**
	 * load all buttons into the calculator and connect them to the html. click
	 * handler for clear and equals.
	 * 
	 */
	public void onModuleLoad() {
		functionButtons.add(minus);
		functionButtons.add(plus);
		functionButtons.add(divide);
		functionButtons.add(multiply);
		// functionButtons.add(equals);

		numberButtons.add(one);
		numberButtons.add(two);
		numberButtons.add(three);
		numberButtons.add(four);
		numberButtons.add(five);
		numberButtons.add(six);
		numberButtons.add(seven);
		numberButtons.add(eight);
		numberButtons.add(nine);
		numberButtons.add(zero);

		// add number buttons to top row of calculator
		topNumberRow.add(seven);
		topNumberRow.add(eight);
		topNumberRow.add(nine);
		topNumberRow.add(plus);
		// add number buttons to second Row of calculator
		secondNumberRow.add(four);
		secondNumberRow.add(five);
		secondNumberRow.add(six);
		secondNumberRow.add(minus);
		// add number buttons to third row of calculator
		thirdNumberRow.add(one);
		thirdNumberRow.add(two);
		thirdNumberRow.add(three);
		thirdNumberRow.add(divide);
		// add last row to calculator
		bottomPanel.add(zero);
		bottomPanel.add(decimal);
		bottomPanel.add(multiply);
		equalsPanel.add(equals); // last row
		// style the buttons in css
		addStyleToButtons();

		// clear button
		Button clear = new Button("C");
		clear.addStyleDependentName("clear");

		// add everything to mainPanel
		mainPanel.add(numberText);
		mainPanel.add(clear);
		mainPanel.add(topNumberRow);
		mainPanel.add(secondNumberRow);
		mainPanel.add(thirdNumberRow);
		mainPanel.add(bottomPanel);
		mainPanel.add(equalsPanel);

		// connect to html
		RootPanel.get("calculator").add(mainPanel);

		// listen for mouse click on clear button
		clear.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				clearText();
			}
		});
		// check for mouse click on any of the number buttons or function
		// buttons
		for (Button b : functionButtons)
			clickHandle(b);
		for (Button b : numberButtons)
			clickHandle(b);
		clickHandle(decimal);

		final CalculateExp calcExp = new CalculateExp();
		// add click handler for equals and if enter is pushed also calculate
		equals.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				setExpressions();
				setNumberText(calcExp.calculate(firstEx, secondEx, modifier));
				calculated = true;
				updateText();
			}
		});
		// add key handler for enter key
		final GoogleSearchOption gso = new GoogleSearchOption();
		numberText.addKeyDownHandler(new KeyDownHandler() {
			@Override
			public void onKeyDown(KeyDownEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					if (gso.checkIfSearch(getNumberText())) {
						gso.search(getNumberText());
						setNumberText(gso.getSeachEntry());
						calculated = true;
						updateText();
					} else
						setExpressions();
					setNumberText(calcExp
							.calculate(firstEx, secondEx, modifier));
					calculated = true;
					updateText();
				}
			}
		});

	}

	/**
	 * add the css to the buttons
	 */
	private void addStyleToButtons() {
		for (Button b : functionButtons) {
			b.addStyleDependentName("one");
		}
		for (Button b : numberButtons) {
			if (!b.getTitle().equals("zero"))
				b.addStyleDependentName("one");
		}
		decimal.addStyleDependentName("one");
		zero.addStyleDependentName("zero");
		equals.addStyleDependentName("equals");
	}

	/**
	 * clear the textbox. set buttons that may have been disabled to enabled.
	 */
	public void clearText() {
		// clear text box and re-enable all buttons
		setNumberText("");
		decimal.setEnabled(true);
		enableMathButtons();
		calculated = true;
		updateText();
	}

	/**
	 * click handler for all the buttons besides equals and clear
	 * 
	 * @param b
	 *            the button that is being clicked
	 */
	private void clickHandle(final Button b) {
		b.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				printButtonValue(b);
			}
		});
	}

	/**
	 * print and store the value of the button that was pressed and store the
	 * first expression, math modifier, and second expression in variables.
	 * 
	 * @param b
	 *            the button that was pressed
	 */
	public void printButtonValue(Button b) {
		updateText();
		String currentText = getNumberText();
		setNumberText(currentText + b.getText());
		// disable decimal button once pressed
		if (b.getText().equals(".")) {
			decimal.setEnabled(false);
		}
		// if (numberText.getText().endsWith(".")) {
		// for (Button button : functionButtons)
		// button.setEnabled(false);
		//
		// } else
		// for (Button button : functionButtons)
		// button.setEnabled(true);
		// disable function buttons if one was already pressed
		if (b.getText().equals("+") || b.getText().equals("-")
				|| b.getText().equals("/") || b.getText().equals("*")) {
			// modifier = b.getText();// .substring(getNumberText().length() -
			// 2,
			// // getNumberText().length() - 1);
			// // System.out.println(modifier);
			// modFound = true;
			decimal.setEnabled(true);
			disableMathButtons();
		}
		// if (!modFound)
		// firstEx += b.getText();
		// else if (modFound && helpMe)
		// secondEx += b.getText();
		// else if (modFound)
		// helpMe = true;
	}

	/**
	 * update the textbox if there was an expression that was calculated. reset
	 * the expression and modifier variables and the booleans associated with
	 * them.
	 */
	protected void updateText() {
		if (calculated) {
			// if(getNumberText().equals("Google...") ||
			// getNumberText().substring(0, 2).equals("Se"))
			// {clearText();}
			// clearText();
			enableMathButtons();
			calculated = false;
			firstEx = "";
			secondEx = "";
			modifier = "";
			modFound = false;
			helpMe = false;
		} else
			;
	}

	/**
	 * get the text in the textbox.
	 * 
	 * @return the text in the textbox.
	 */
	public String getNumberText() {
		return numberText.getText();
	}

	/**
	 * set the text box to a value.
	 * 
	 * @param string
	 *            the value to set to the text box.
	 */
	public void setNumberText(String string) {
		numberText.setText(string);
	}

	public void setExpressions() {
		CalculateExp calculateExp = new CalculateExp();
		GoogleSearchOption gso = new GoogleSearchOption();
		if (!calculateExp.isValidExpression(getNumberText())) {
			gso.search(getNumberText());
		} else {
			updateText();
			String textExpressions = getNumberText();
			for (int i = 0; i < textExpressions.length(); i++) {
				if (textExpressions.charAt(i) == '+'
						|| textExpressions.charAt(i) == '-'
						|| textExpressions.charAt(i) == '*'
						|| textExpressions.charAt(i) == '/') {
					modFound = true;
					modifier = textExpressions.charAt(i) + "";
				}
				if (!modFound)
					firstEx += textExpressions.charAt(i) + "";
				else if (modFound && helpMe)
					secondEx += textExpressions.charAt(i) + "";
				else if (modFound) {
					helpMe = true;
				}
			}
		}
	}

	public void disableMathButtons() {
		plus.setEnabled(false);
		minus.setEnabled(false);
		divide.setEnabled(false);
		multiply.setEnabled(false);
	}

	public void enableMathButtons() {
		plus.setEnabled(true);
		minus.setEnabled(true);
		divide.setEnabled(true);
		multiply.setEnabled(true);
	}
}
package com.newcircle.testablecalculator.test;

import java.util.HashMap;
import java.util.Map;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;

import com.robotium.solo.Solo;

public class RobotiumCalculator extends BaseTest {
	private static final int DIV_IDX = 6;
	private static final int MUL_IDX = 10;
	private static final int MINUS_IDX = 14;
	private static final int PLUS_IDX = 18;
	private static final int DOT_IDX = 15;

	Map<String, Button> mDigitButtons = new HashMap<String, Button>();

	private Solo mSolo;

	public RobotiumCalculator(Solo solo) {
		mSolo = solo;

		// We use a for loop to search for all the digit buttons.
		// If we search when we have text inside the buttons (firstNumber or secondNumber),
		// we will choose the buttons instead of the digits, so we need to do this before we
		// "corrupt" the view.
		for(int i = 0; i <= 9; i++) {
			String iStr = String.valueOf(i);
			mDigitButtons.put(iStr, mSolo.getButton(iStr));
		}
		mDigitButtons.put(".", mSolo.getButton(DOT_IDX));
	}

	public void calcByEditText(Double x, Double y, OP op, String expectedResults) {
		mSolo.clearEditText(0); // The first (index 0) edit box in the view
		mSolo.enterText(0,  String.valueOf(x) + opToString(op) + String.valueOf(y));

		mSolo.sleep(1000);

		ActivityInstrumentationTestCase2.assertTrue("Problem asserting calcEditText", mSolo.searchText(expectedResults));
	}

	public void calcByClickingDigits(Double x, Double y, OP op,  String expectedResults) {
		// We Make sure that the EditBox is clear
		mSolo.clearEditText(0);

		// We now that the first number is also the first button - Index 0
		mSolo.clickOnButton(0); // 0 is index - not id
		// We search the CLR button by text
		mSolo.clickOnButton("CLR");
		clickThis(x);

		// We now that the second number is also the second button - Index 1
		mSolo.clickOnButton(1);
		mSolo.clickOnButton("CLR");
		clickThis(y);

		switch(op) {
		case DIV:
			mSolo.clickOnView(mSolo.getButton(DIV_IDX));  // We used trial and error to pre-find the index for DIV
			break;
		case MINUS:
			mSolo.clickOnView(mSolo.getButton(MINUS_IDX)); // We used trial and error to pre-find the index for MINUS
			break;
		case MUL:			
			mSolo.clickOnView(mSolo.getButton(MUL_IDX)); // We used trial and error to pre-find the index for MUL
			break;
		case PLUS:
			mSolo.clickOnView(mSolo.getButton(PLUS_IDX)); // We used trial and error to pre-find the index for PLUS
			break;
		default:
			break;
		}   	

		mSolo.sleep(100);

		ActivityInstrumentationTestCase2.assertTrue("Problem asserting calcByClickingDigits", mSolo.searchText(expectedResults));
	}	

	private void clickThis(Double num) {
		String numStr = String.valueOf(num);
		for(int i = 0; i < numStr.length(); i++) {
			mSolo.clickOnView(mDigitButtons.get("" + numStr.charAt(i)));
		}
	}

}

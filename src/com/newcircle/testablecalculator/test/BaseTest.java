package com.newcircle.testablecalculator.test;

import java.util.Random;

public class BaseTest {
	Random mRandom =new Random();

	public enum OP {
		PLUS,
		MINUS,
		MUL,
		DIV
	}
	
	protected String opToString(OP op) {
		switch(op) {
		case DIV:
			return "/";
		case MINUS:
			return "-";
		case MUL:
			return "*";
		case PLUS:
			return "+";
		default:
			return "";
		}
	}

	public Double calcGoldenModel(Double x, Double y, OP op) {
		switch(op) {
		case DIV:
			return x / y;
		case MINUS:
			return x - y;
		case MUL:
			return x * y;
		case PLUS:
			return x + y;
		default:
			return mRandom.nextDouble();
		}   	
	}
}

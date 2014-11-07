package com.newcircle.testablecalculator.test;

import java.util.Random;

import android.annotation.SuppressLint;
import android.test.ActivityInstrumentationTestCase2;

import com.newcircle.testablecalculator.test.BaseTest.OP;
import com.robotium.solo.Solo;

@SuppressWarnings("rawtypes")
public class TestCalculator extends ActivityInstrumentationTestCase2 {
	private static final String LAUNCHER_ACTIVITY_FULL_CLASSNAME = "com.newcircle.testablecalculator.Calculator";

	private static final Double MIN_RANDOM_D = 0.0;
	private static final Double MAX_RANDOM_D = 100000.0;

	private static final Integer MIN_RANDOM_I = 0;
	private static final Integer MAX_RANDOM_I = 100000;
		
	private static Class launcherActivityClass;
	private Solo mSolo;
	private RobotiumCalculator mRobotiumCalculator;

	
	Random mRandom = new Random();

	static {
		try {
			launcherActivityClass = Class.forName(LAUNCHER_ACTIVITY_FULL_CLASSNAME);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	public TestCalculator() {
		super(launcherActivityClass);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		mSolo = new Solo(getInstrumentation(), getActivity());
		mRobotiumCalculator = new RobotiumCalculator(mSolo);
	}
	
	public void testBasicDouble() {
		singleOP(getRandomDouble(), getRandomDouble(), OP.DIV);

		singleOP(getRandomDouble(), getRandomDouble(), OP.MUL);

		singleOP(getRandomDouble(), getRandomDouble(), OP.PLUS);

		singleOP(getRandomDouble(), getRandomDouble(), OP.MINUS);
	}

	public void testBasicInteger() {
		singleOP(getRandomInteger(), getRandomInteger(), OP.DIV);

		singleOP(getRandomInteger(), getRandomInteger(), OP.MUL);

		singleOP(getRandomInteger(), getRandomInteger(), OP.PLUS);

		singleOP(getRandomInteger(), getRandomInteger(), OP.MINUS);
	}
	
	private Double getRandomDouble() {
		return MIN_RANDOM_D + (MAX_RANDOM_D - MIN_RANDOM_D)*mRandom.nextDouble();
	}

	private Integer getRandomInteger() {
		return MIN_RANDOM_I +  mRandom.nextInt(MAX_RANDOM_I-MIN_RANDOM_I);
	}
	@SuppressLint("DefaultLocale")
	public static String fmt(double d)
	{
		if(d == (long)d)
			return Long.toString((long)d);
		else 
			return String.format("%.4f", d);
	}
	
	private void singleOP(Double x, Double y, OP op) {
		String expResults = fmt(mRobotiumCalculator.calcGoldenModel(x, y, op));
		
		mRobotiumCalculator.calcByEditText(x, y, op, expResults);
		mRobotiumCalculator.calcByClickingDigits(x,y,op, expResults);
	}

	private void singleOP(Integer x, Integer y, OP op) {
		singleOP((double)x, (double)y, op);
	}
	
	@Override
	protected void tearDown() throws Exception{
		mSolo.finishOpenedActivities();
	}
}

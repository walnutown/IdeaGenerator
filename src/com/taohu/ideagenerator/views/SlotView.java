package com.taohu.ideagenerator.views;

import java.util.Random;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.taohu.ideagenerator.R;

public class SlotView extends LinearLayout {
	TextView label;
	final static int BLUE = 0;
	final static int BLUE_LIGHT = 1;
	final static int BLUE_DARK = 2;
	final static int RED = 3;
	final static int RED_LIGHT = 4;
	final static int RED_DARK = 5;
	final static int YELLOW = 6;
	final static int YELLOW_LIGHT = 7;
	final static int YELLOW_DARK = 8;
	final static int GREEN = 9;
	final static int GREEN_LIGHT = 10;
	final static int GREEN_DARK = 11;
	final static int PURPLE = 12;
	final static int PURPLE_LIGHT = 13;
	final static int PURPLE_DARK = 14;

	public SlotView(Context context) {
		super(context);
	}

	public SlotView(Context context, String str) {
		this(context);
		createView(context, str);
	}

	@SuppressWarnings("deprecation")
	private void createView(Context context, String str) {
		LayoutInflater.from(context).inflate(R.layout.view_slot, this, true);
		label = (TextView) findViewById(R.id.text);
		label.setText(str);
		ColorDrawable cd = new ColorDrawable(getRandomColor());
		label.setBackgroundDrawable(cd);
	}

	private int getRandomColor() {
		int[][] colors = new int[][] { { BLUE, BLUE_LIGHT, BLUE_DARK }, { RED, RED_LIGHT, RED_DARK },
				{ YELLOW, YELLOW_LIGHT, YELLOW_DARK }, { GREEN, GREEN_LIGHT, GREEN_DARK },
				{ PURPLE, PURPLE_LIGHT, PURPLE_DARK }, };
		Random random = new Random(System.currentTimeMillis());
		int mainColor = random.nextInt(5);
		int strength = random.nextInt(3);
		int color = colors[mainColor][strength];
		int value = 0;
		switch (color) {
		case BLUE:
			value = R.color.blue;
			break;
		case BLUE_DARK:
			value = R.color.blue_dark;
			break;
		case BLUE_LIGHT:
			value = R.color.blue_light;
			break;
		case RED:
			value = R.color.red;
			break;
		case RED_DARK:
			value = R.color.red_dark;
			break;
		case RED_LIGHT:
			value = R.color.red_light;
			break;
		case YELLOW:
			value = R.color.yellow;
			break;
		case YELLOW_DARK:
			value = R.color.yellow_dark;
			break;
		case YELLOW_LIGHT:
			value = R.color.yellow_light;
			break;
		case PURPLE:
			value = R.color.purple;
			break;
		case PURPLE_DARK:
			value = R.color.purple_dark;
			break;
		case PURPLE_LIGHT:
			value = R.color.purple_light;
			break;
		case GREEN:
			value = R.color.green;
			break;
		case GREEN_DARK:
			value = R.color.green_dark;
			break;
		case GREEN_LIGHT:
			value = R.color.green_light;
			break;
		}
		return getResources().getColor(value);
	}

}

package com.taohu.ideagenerator.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.taohu.ideagenerator.R;
import com.taohu.ideagenerator.model.Combination;
import com.taohu.ideagenerator.model.Item;

public class SavedCombinationView extends LinearLayout {
	private TextView txtDesire;
	private TextView txtIndustry;
	private TextView txtTech;
	private TextView txtTime;
	
	public SavedCombinationView(Context context) {
		super(context);
		LayoutInflater.from(context).inflate(R.layout.view_savedcombination, this, true);
	}
	
	public void resetView(Combination combination){	
		txtDesire = (TextView) findViewById(R.id.txt_desire);
		txtIndustry = (TextView) findViewById(R.id.txt_industry);
		txtTech = (TextView) findViewById(R.id.txt_tech);
		txtTime = (TextView) findViewById(R.id.txt_time);
		Item desire = combination.items.get(0), industry = combination.items.get(1), tech = combination.items.get(2);
		txtDesire.setBackgroundColor(desire.color);
		txtDesire.setText(desire.value);
		txtIndustry.setBackgroundColor(industry.color);
		txtIndustry.setText(industry.value);
		txtTech.setBackgroundColor(tech.color);
		txtTech.setText(tech.value);
		txtTime.setText(combination.timestamp);
	}
	
}

package com.taohu.ideagenerator;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.taohu.ideagenerator.views.SlotViewFlipper;

public class MainFragment extends Fragment{
	SlotViewFlipper mViewFlipper1;
	SlotViewFlipper mViewFlipper2;
	SlotViewFlipper mViewFlipper3;
	String[] desires;
	String[] industries;
	String[] techs;
	
	final static int MAIN = 1;
	
	public MainFragment(){
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_main, container, false);
		mViewFlipper1 = (SlotViewFlipper) rootView.findViewById(R.id.viewflipper1);
		mViewFlipper2 = (SlotViewFlipper) rootView.findViewById(R.id.viewflipper2);
		mViewFlipper3 = (SlotViewFlipper) rootView.findViewById(R.id.viewflipper3);
		//mViewFlipper1.setFlipInterval(100);
		//mViewFlipper2.setFlipInterval(200);
		//mViewFlipper3.setFlipInterval(50);
		
		
		
		desires = getResources().getStringArray(R.array.desires_array);
		industries = getResources().getStringArray(R.array.industries_array);
		techs = getResources().getStringArray(R.array.techs_array);
		mViewFlipper1.init(desires);
		mViewFlipper2.init(industries);
		mViewFlipper3.init(techs);
//		for (String desire : desires) 
//			mViewFlipper1.addView(new SlotView(getActivity(), desire));
//		for (String industry : industries)
//			mViewFlipper2.addView(new SlotView(getActivity(), industry));
//		for (String tech : techs)
//			mViewFlipper3.addView(new SlotView(getActivity(), tech));
		return rootView;
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		((MainActivity) activity).onSectionAttached(MAIN);
	}
	
}
package com.taohu.ideagenerator;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ViewFlipper;

import com.taohu.ideagenerator.views.SlotView;

public class MainFragment extends Fragment{
	ViewFlipper mViewFlipper1;
	ViewFlipper mViewFlipper2;
	ViewFlipper mViewFlipper3;
	String[] desires;
	String[] industries;
	String[] techs;
	Button mBtnGen;
	boolean isRolling;
	final static int MAIN = 1;
	
	public MainFragment(){
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_main, container, false);
		mViewFlipper1 = (ViewFlipper) rootView.findViewById(R.id.viewflipper1);
		mViewFlipper2 = (ViewFlipper) rootView.findViewById(R.id.viewflipper2);
		mViewFlipper3 = (ViewFlipper) rootView.findViewById(R.id.viewflipper3);
		mViewFlipper1.setFlipInterval(100);
		mViewFlipper2.setFlipInterval(200);
		mViewFlipper3.setFlipInterval(50);
		mBtnGen = (Button) rootView.findViewById(R.id.btn_start);
		mBtnGen.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (isRolling) {
					mViewFlipper1.stopFlipping();
					mViewFlipper2.stopFlipping();
					mViewFlipper3.stopFlipping();
					isRolling = false;
				} else {
					mViewFlipper1.startFlipping();
					mViewFlipper2.startFlipping();
					mViewFlipper3.startFlipping();
					isRolling = true;
				}
			}
		});
		desires = getResources().getStringArray(R.array.desires_array);
		industries = getResources().getStringArray(R.array.industries_array);
		techs = getResources().getStringArray(R.array.techs_array);
		for (String desire : desires) 
			mViewFlipper1.addView(new SlotView(getActivity(), desire));
		for (String industry : industries)
			mViewFlipper2.addView(new SlotView(getActivity(), industry));
		for (String tech : techs)
			mViewFlipper3.addView(new SlotView(getActivity(), tech));
		return rootView;
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		((MainActivity) activity).onSectionAttached(MAIN);
	}
}

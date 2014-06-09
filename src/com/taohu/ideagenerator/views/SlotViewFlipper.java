package com.taohu.ideagenerator.views;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector.OnGestureListener;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ViewFlipper;

public class SlotViewFlipper extends ViewFlipper implements OnGestureListener {
	String[] values;
	private int mSpeed;
	private int mCount;
	private boolean isRolling;
	private Runnable rollUp;
	private Runnable rollDown;
	private Context context;
	private GestureDetector mDetector;

	public SlotViewFlipper(Context context) {
		super(context);
		this.context = context;
	}

	public SlotViewFlipper(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}

	public void init(String[] values) {
		mSpeed = 500;
		mCount = 10;
		isRolling = false;
		this.values = values;
		mDetector = new GestureDetector(this);
		for (String value : values)
			addView(new SlotView(context, value));
		rollUp = new Runnable() {
			@Override
			public void run() {
				up();
				if (mCount < 1) {
					isRolling = false;
					return;
				}
				new Handler().postDelayed(rollUp, mSpeed);
			}
		};
		rollDown = new Runnable() {
			@Override
			public void run() {
				down();
				Log.d("Touch", "count: " + mCount);
				if (mCount < 1) {
					isRolling = false;
					return;
				}
				new Handler().postDelayed(rollDown, mSpeed);
			}
		};
		
		new Handler().postDelayed(rollDown, mSpeed);
	}

	// @Override
	// public void showNext() {
	// // TODO Auto-generated method stub
	// super.showNext();
	// }
	// @Override
	// public void showPrevious() {
	// // TODO Auto-generated method stub
	// super.showPrevious();
	// }
	
	 @Override
	    public boolean onTouchEvent(MotionEvent me) {
	        return mDetector.onTouchEvent(me);
	    }

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
		isRolling = true;
		Log.d("Touch", "onFling()");
		mCount = (int) Math.abs(velocityY);
		if (velocityY > 0) {
			new Handler().postDelayed(rollDown, mSpeed);
		} else {
			new Handler().postDelayed(rollUp, mSpeed);
		}
		return true;
	}

	private void up() {
		mCount--;
		Animation inFromBottom = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 1.0f, Animation.RELATIVE_TO_PARENT,
				0.0f);
		inFromBottom.setInterpolator(new AccelerateInterpolator());
		inFromBottom.setDuration(mSpeed);
		Animation outToTop = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT,
				0.0f, Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, -1.0f);
		outToTop.setInterpolator(new AccelerateInterpolator());
		outToTop.setDuration(mSpeed);
		clearAnimation();
		setInAnimation(inFromBottom);
		setOutAnimation(outToTop);
		if (getDisplayedChild() == 0)
			setDisplayedChild(values.length - 1);
		else
			showPrevious();
	}

	private void down() {
		mCount--;
		Animation outToBottom = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT,
				1.0f);
		outToBottom.setInterpolator(new AccelerateInterpolator());
		outToBottom.setDuration(mSpeed);
		Animation inFromTop = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT,
				0.0f, Animation.RELATIVE_TO_PARENT, -1.0f, Animation.RELATIVE_TO_PARENT, 0.0f);
		inFromTop.setInterpolator(new AccelerateInterpolator());
		inFromTop.setDuration(mSpeed);
		clearAnimation();
		setInAnimation(inFromTop);
		setOutAnimation(outToBottom);
		if (getDisplayedChild() == 0)
			setDisplayedChild(values.length - 1);
		else
			showPrevious();
	}

	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

}
